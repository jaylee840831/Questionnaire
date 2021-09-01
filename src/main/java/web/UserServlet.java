package web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.http.Part;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtilsFileNewerTestCase;
import org.apache.commons.io.IOUtils;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.util.Base64Utils;

import dao.AB_DAO;
import dao.Experiment_DAO;
import model.ABTest;
import model.ExperimentTest;

/**
 * Servlet implementation class ABTestServlet
 */
@WebServlet("/")
@MultipartConfig
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AB_DAO AB_DAO;
	private Experiment_DAO experiment_DAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
       this.AB_DAO=new AB_DAO();
       this.experiment_DAO=new Experiment_DAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action=request.getServletPath();
		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;
		case "/insert":
			try {
				insertABTest(request, response);	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/delete":
			try {
				deleteABTest(request, response);	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/edit":
			try {
				showEditForm(request, response);	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/update":
			try {
				updateABTest(request, response);	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case "/update_delete":
			try {
				showUpdateDeleteForm(request, response);	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;	
		case "/success":
			try {
				successSubmit(request, response);	
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;	
		default:
			try {
				listABTest(request, response);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		this.doGet(request, response);
	}
	
	private void showNewForm(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		RequestDispatcher dispatcher=request.getRequestDispatcher("user_form.jsp");
		dispatcher.forward(request, response);
	}
	private void showEditForm(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException,ServletException{
		int id=Integer.parseInt(request.getParameter("id"));
		String test_name=request.getParameter("test_name");
		ABTest existingABTest=AB_DAO.selectQuestion(id);
		ExperimentTest experimentTest=experiment_DAO.selectQuestion(id);
		RequestDispatcher dispatcher=request.getRequestDispatcher("user_form2.jsp");
		request.setAttribute("test_name",test_name);
		request.setAttribute("ab_test", existingABTest);
		request.setAttribute("experiment_test", experimentTest);
		dispatcher.forward(request, response);
	}
	private void showUpdateDeleteForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		ArrayList<ABTest>listABTests=AB_DAO.selectAllQuestions();
		ArrayList<ExperimentTest>experimentTests=experiment_DAO.selectAllQuestions();
		request.setAttribute("listABTest", listABTests);
		request.setAttribute("experimentTest", experimentTests);
		RequestDispatcher dispatcher=request.getRequestDispatcher("update_delete.jsp");
		dispatcher.forward(request, response);
	}
	private void insertABTest(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{	
		String question=request.getParameter("question");
		String test_name;
		String data_name;
		InputStream test_a;
		InputStream test_b;
		
		if(question==null || question.equals("") || question.equals("test1")) {
			test_name=request.getParameter("test_name");
			Part filePart=request.getPart("test_a");
			test_a=filePart.getInputStream();
			Part filePart2=request.getPart("test_b");
			test_b=filePart2.getInputStream();
			
			ABTest newABTest=new ABTest(test_name,test_a,"",0,test_b,"",0);
			AB_DAO.insertQuestion(newABTest);
		}
		else if(question.equals("test2")) {
			test_name=request.getParameter("test_name");
			data_name=request.getParameter("data_name");
		    Part filPart=request.getPart("test_a");
		    test_a=filPart.getInputStream();
		    ExperimentTest experimentTest=new ExperimentTest(test_name,data_name,0,0,0,0,0,test_a,"");
		    experiment_DAO.insertQuestion(experimentTest);
		}
		response.sendRedirect("list");
	}
	private void deleteABTest(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException {
		int id=Integer.parseInt(request.getParameter("id"));
		String test_name=request.getParameter("test_name");
		if(test_name.equals("test1")) {
			AB_DAO.deleteQuestion(id);
		}
		else if(test_name.equals("test2")) {
			experiment_DAO.deleteQuestion(id);
		}
		response.sendRedirect("list");
	}
	private void updateABTest(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException, ServletException{
		String question=request.getParameter("question");
		int id=Integer.parseInt(request.getParameter("id"));
		String test_name=request.getParameter("test_name");
		
		if(question.equals("test1")) {
			id=Integer.parseInt(request.getParameter("id"));
			test_name=request.getParameter("test_name");
			Part filePart=request.getPart("test_a");
			InputStream test_a=filePart.getInputStream();
			Part filePart2=request.getPart("test_b");
			InputStream test_b=filePart2.getInputStream();
			
			int a_vote=0;
			int b_vote=0;
			String current_a_audio="";
			String current_b_audio="";
			ABTest abTest;
			
			ArrayList<ABTest>listABTests=AB_DAO.selectAllQuestions();
			for(int i=0;i<listABTests.size();i++) {
				if(id==listABTests.get(i).getId()) {
					a_vote=listABTests.get(i).getTest_a_vote();
					b_vote=listABTests.get(i).getTest_b_vote();
					current_a_audio=listABTests.get(i).getTest_a2();
					current_b_audio=listABTests.get(i).getTest_b2();
				}
			}
			
			
			if(filePart.getSize()!=0 && filePart2.getSize()!=0){
				abTest=new ABTest(id,test_name,test_a,"",a_vote,test_b,"",b_vote);
				AB_DAO.updateQuestion(abTest);
			}
			else {
				abTest=new ABTest(id,test_name,test_a,current_a_audio,a_vote,test_b,current_b_audio,b_vote);
				AB_DAO.updateQuestion(abTest,current_a_audio,current_b_audio);
			}	
		}
		else if(question.equals("test2")) {
			id=Integer.parseInt(request.getParameter("id"));
			test_name=request.getParameter("test_name");
			String data_name=request.getParameter("data_name");
			Part filePart=request.getPart("test_a");
			InputStream test_a=filePart.getInputStream();
			
			int score1=0;
			int score2=0;
			int score3=0;
			int score4=0;
			int score5=0;
			String current_audio="";
			ExperimentTest experimentTest;
			
			ArrayList<ExperimentTest>list=experiment_DAO.selectAllQuestions();
			for(int i=0;i<list.size();i++) {
				if(id==list.get(i).getId()) {
					score1=list.get(i).getScore1();
					score2=list.get(i).getScore2();
					score3=list.get(i).getScore3();
					score4=list.get(i).getScore4();
					score5=list.get(i).getScore5();
					current_audio=list.get(i).getaudio2();
				}
			}
			
			if(filePart.getSize()!=0){
				experimentTest=new ExperimentTest(id,test_name,data_name,score1,score2,score3,score4,score5,test_a,"");
				experiment_DAO.updateQuestion(experimentTest);
			}
			else {
				experimentTest=new ExperimentTest(id,test_name,data_name,score1,score2,score3,score4,score5,test_a,current_audio);
				experiment_DAO.updateQuestion(experimentTest, current_audio);
			}
		}
		
		response.sendRedirect("list");
		
	}
	private void successSubmit(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException, SQLException {
		String s=request.getParameter("vote_result");
		String[] s2=s.split(",");
		String answer=s2[0];
		String testType=s2[1];
		boolean success;
		char[] vote_result = new char[answer.length()];
		if(!answer.equals("")) {
			if(testType.equals("test1")) {
				vote_result = new char[answer.length()];
				for (int i = 0; i < answer.length(); i++) {
					vote_result[i] = answer.charAt(i);
				}
				AB_DAO.voteResult(vote_result);
			}
			else if(testType.equals("test2")){
				vote_result = new char[answer.length()];
				for (int i = 0; i < answer.length(); i++) {
					vote_result[i] = answer.charAt(i);
				}
				experiment_DAO.voteResult(vote_result);
			}
			success=true;
		}
		else {
			success=false;
		}
		request.setAttribute("success", success);
		RequestDispatcher dispatcher=request.getRequestDispatcher("success_submit.jsp");
		dispatcher.forward(request, response);
	}
	private void listABTest(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException,ServletException{
		ArrayList<ABTest>listABTests=AB_DAO.selectAllQuestions();
		ArrayList<ExperimentTest>experimentTest=experiment_DAO.selectAllQuestions();
		
		request.setAttribute("listABTest", listABTests);
		request.setAttribute("experimentTest", experimentTest);
		RequestDispatcher dispatcher=request.getRequestDispatcher("user_list.jsp");
		dispatcher.forward(request, response);
	}
}
