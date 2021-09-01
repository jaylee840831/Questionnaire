package dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets; 
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.io.*;

import org.apache.commons.io.IOUtils;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.core.style.ToStringCreator;

import model.ExperimentTest;

public class Experiment_DAO {

//	private String jdbcURL="jdbc:mysql://localhost:3306/user_crud?useSSL=false";
//	private String jdbcUsername="root";
//	private String jdbcPassword="840831";
	private Connection connection;
	private DBSource dbsource;
	
	//增刪查改(CRUD)sql語法
	private static final String INSERT_USERS_SQL=
			"INSERT INTO experiment"+
	        " (test_name,data_name,score1,score2,score3,score4,score5,audio) VALUES "+
	        " (?,?,?,?,?,?,?,?);";
	private static final String SELECT_USER_BY_ID="select id,test_name,data_name,score1,score2,score3,score4,score5,audio from experiment where id=?";
	private static final String SELECT_ALL_USERS="select * from experiment";
	private static final String DELETE_USERS_SQL="delete from experiment where id=?;";
	private static final String UPDATE_USERS_SQL="update experiment set test_name=?,data_name=?,score1=?,score2=?,score3=?,score4=?,score5=?,audio=? where id=?;";
	private static final String UPDATE_PLUS_1_SQL="update experiment set score1 = score1 + 1 where id=?;";
	private static final String UPDATE_PLUS_2_SQL="update experiment set score2 = score2 + 1 where id=?;";
	private static final String UPDATE_PLUS_3_SQL="update experiment set score3 = score3 + 1 where id=?;";
	private static final String UPDATE_PLUS_4_SQL="update experiment set score4 = score4 + 1 where id=?;";
	private static final String UPDATE_PLUS_5_SQL="update experiment set score5 = score5 + 1 where id=?;";
	
	//connect mysql
//	protected Connection getConnection() {
//		Connection connection=null;
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			connection=DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
//		}
//		catch(SQLException e) {
//			e.printStackTrace();
//		}
//		catch(ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//		return connection;
//	}
	
	public Experiment_DAO() {
         try {
//        	this.dbsource = new SimpleDBSource();
//        	this.connection = dbsource.getConnection();
        	 
        	this.dbsource = new BasicDBSource("D://eclipse-workspace/audio_questionnaire/jdbc2.properties");
            this.connection = dbsource.getConnection();
        	
        	  if(!this.connection.isClosed()) {
                  System.out.println("資料庫連接已開啟…");
              }
        	  
		}  catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	//binary stream encode to base64
	public String readFiles(InputStream inputStream) throws IOException{
		 byte[] bytes = IOUtils.toByteArray(inputStream);
		 String encoded = Base64.getEncoder().encodeToString(bytes);
		 return encoded;
	}
	
	//base64 decode to binary stream
	public InputStream base64ToBinaryStream(String audio){
		byte[] decodedBytes = Base64.getDecoder().decode(audio.getBytes());
		InputStream is = new ByteArrayInputStream(decodedBytes);
		return is;
	}
	
	//insert question
	public void insertQuestion(ExperimentTest experiment) {
		try (
				PreparedStatement preparedstatement=this.connection.prepareStatement(INSERT_USERS_SQL);){
			
			preparedstatement.setString(1, experiment.getTest_name());
			preparedstatement.setString(2, experiment.getData_name());
			preparedstatement.setInt(3, experiment.getScore1());
			preparedstatement.setInt(4, experiment.getScore2());
			preparedstatement.setInt(5, experiment.getScore3());
			preparedstatement.setInt(6, experiment.getScore4());
			preparedstatement.setInt(7, experiment.getScore5());
			preparedstatement.setBinaryStream(8,experiment.getaudio() );
			preparedstatement.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//update question (方法重載)
	public boolean updateQuestion(ExperimentTest experiment)throws SQLException, FileNotFoundException {
		boolean rowUpdated;
		try (
				PreparedStatement statement=this.connection.prepareStatement(UPDATE_USERS_SQL);){
			statement.setString(1, experiment.getTest_name());
			statement.setString(2, experiment.getData_name());
			statement.setInt(3, experiment.getScore1());
			statement.setInt(4, experiment.getScore2());
			statement.setInt(5, experiment.getScore3());
			statement.setInt(6, experiment.getScore4());
			statement.setInt(7, experiment.getScore5());
			statement.setBinaryStream(8,experiment.getaudio());
			statement.setInt(9, experiment.getId());
			
			rowUpdated=statement.executeUpdate()>0;
		}
		return rowUpdated;
	}
	//update question (方法重載)
		public boolean updateQuestion(ExperimentTest experiment,String current_audio)throws SQLException, FileNotFoundException {
			boolean rowUpdated;
			try (
					PreparedStatement statement=this.connection.prepareStatement(UPDATE_USERS_SQL);
					){
				statement.setString(1, experiment.getTest_name());
				statement.setString(2, experiment.getData_name());
				statement.setInt(3, experiment.getScore1());
				statement.setInt(4, experiment.getScore2());
				statement.setInt(5, experiment.getScore3());
				statement.setInt(6, experiment.getScore4());
				statement.setInt(7, experiment.getScore5());
				statement.setBinaryStream(8,base64ToBinaryStream(current_audio));
				statement.setInt(9, experiment.getId());
				
				rowUpdated=statement.executeUpdate()>0;
			}
			return rowUpdated;
		}
	//select question by id
	public ExperimentTest selectQuestion(int id) throws IOException {
		ExperimentTest question=null;
		try(
				PreparedStatement preparedstatement=this.connection.prepareStatement(SELECT_USER_BY_ID);){
			preparedstatement.setInt(1, id);
			System.out.println(preparedstatement);
			ResultSet rs=preparedstatement.executeQuery();
			
			while(rs.next()) {
				String test_name=rs.getString("test_name");
				String data_name=rs.getString("data_name");
				int score1=rs.getInt("score1");
				int score2=rs.getInt("score2");
				int score3=rs.getInt("score3");
				int score4=rs.getInt("score4");
				int score5=rs.getInt("score5");
				InputStream audio=rs.getBinaryStream("audio");
				
				question=new ExperimentTest(id,test_name,data_name,score1,score2,score3,score4,score5,audio,readFiles(audio));
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return question;
	}
	//select all questions
	public ArrayList<ExperimentTest> selectAllQuestions() throws IOException {
		ArrayList<ExperimentTest>questions=new ArrayList<ExperimentTest>();
		try(
				PreparedStatement preparedstatement=this.connection.prepareStatement(SELECT_ALL_USERS);){
			System.out.println(preparedstatement);
			ResultSet rs=preparedstatement.executeQuery();
			
			while(rs.next()) {
				int id=rs.getInt("id");
				String test_name=rs.getString("test_name");
				String data_name=rs.getString("data_name");
				int score1=rs.getInt("score1");
				int score2=rs.getInt("score2");
				int score3=rs.getInt("score3");
				int score4=rs.getInt("score4");
				int score5=rs.getInt("score5");
				InputStream audio=rs.getBinaryStream("audio");
				
				questions.add(new ExperimentTest(id,test_name,data_name,score1,score2,score3,score4,score5,audio,readFiles(audio)));
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return questions;
	}
	//delete question
	public boolean deleteQuestion(int id)throws SQLException{
		boolean rowDeleted;
		try(
				PreparedStatement statement=this.connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted=statement.executeUpdate()>0;
		}
		return rowDeleted;
	}
	//insert question's vote result
	public void voteResult(char[]vote_result) throws IOException, SQLException {
		ArrayList<ExperimentTest>listABTests=selectAllQuestions();
		char[] chararray=vote_result;
		Character ch;
		PreparedStatement preparedstatement;
		boolean rowUpdated;
		for(int i=0;i<listABTests.size();i++)
		{ 
			ch=chararray[i];
			int id=listABTests.get(i).getId();
			 if(ch.equals('1')) {
				 preparedstatement=this.connection.prepareStatement(UPDATE_PLUS_1_SQL); 
			 }
			 else if(ch.equals('2')){
				 preparedstatement=this.connection.prepareStatement(UPDATE_PLUS_2_SQL); 
			 }
			 else if(ch.equals('3')){
				 preparedstatement=this.connection.prepareStatement(UPDATE_PLUS_3_SQL); 
			 }
			 else if(ch.equals('4')){
				 preparedstatement=this.connection.prepareStatement(UPDATE_PLUS_4_SQL); 
			 }
			 else{
				 preparedstatement=this.connection.prepareStatement(UPDATE_PLUS_5_SQL); 
			 }
			 
			 preparedstatement.setInt(1, id);
			 rowUpdated=preparedstatement.executeUpdate()>0;
		}
	}
}
