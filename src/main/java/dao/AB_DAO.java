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

import model.ABTest;

public class AB_DAO {

//	private String jdbcURL="jdbc:mysql://localhost:3306/user_crud?useSSL=false";
//	private String jdbcUsername="root";
//	private String jdbcPassword="840831";
	private Connection connection;
	private DBSource dbsource;
	
	//增刪查改(CRUD)sql語法
	private static final String INSERT_USERS_SQL=
			"INSERT INTO ab_test"+
	        " (test_name,test_a,test_a_vote,test_b,test_b_vote) VALUES "+
	        " (?,?,?,?,?);";
	private static final String SELECT_USER_BY_ID="select id,test_name,test_a,test_a_vote,test_b,test_b_vote from ab_test where id=?";
	private static final String SELECT_ALL_USERS="select * from ab_test";
	private static final String DELETE_USERS_SQL="delete from ab_test where id=?;";
	private static final String UPDATE_USERS_SQL="update ab_test set test_name=?,test_a=?,test_a_vote=?,test_b=?,test_b_vote=? where id=?;";
	private static final String UPDATE_PLUS_A_SQL="update ab_test set test_a_vote = test_a_vote + 1 where id=?;";
	private static final String UPDATE_PLUS_B_SQL="update ab_test set test_b_vote = test_b_vote + 1 where id=?;";
	
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
	
	public AB_DAO() {
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
	public void insertQuestion(ABTest question) {
		try (
				PreparedStatement preparedstatement=this.connection.prepareStatement(INSERT_USERS_SQL);){
			
			preparedstatement.setString(1, question.getTest_name());
			preparedstatement.setBinaryStream(2, question.getTest_a());
			preparedstatement.setInt(3, question.getTest_a_vote());
			preparedstatement.setBinaryStream(4, question.getTest_b());
			preparedstatement.setInt(5, question.getTest_b_vote());
			preparedstatement.executeUpdate();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//update question (方法重載)
	public boolean updateQuestion(ABTest question)throws SQLException, FileNotFoundException {
		boolean rowUpdated;
		try (
				PreparedStatement statement=this.connection.prepareStatement(UPDATE_USERS_SQL);){
			statement.setString(1, question.getTest_name());
			statement.setBinaryStream(2, question.getTest_a());
			statement.setInt(3, question.getTest_a_vote());
			statement.setBinaryStream(4, question.getTest_b());
			statement.setInt(5, question.getTest_b_vote());
			statement.setInt(6, question.getId());
			
			rowUpdated=statement.executeUpdate()>0;
		}
		return rowUpdated;
	}
	//update question (方法重載)
		public boolean updateQuestion(ABTest question,String current_a_audio,String current_b_audio)throws SQLException, FileNotFoundException {
			boolean rowUpdated;
			try (
					PreparedStatement statement=this.connection.prepareStatement(UPDATE_USERS_SQL);
					){
				statement.setString(1, question.getTest_name());
				statement.setBinaryStream(2, base64ToBinaryStream(current_a_audio));
				statement.setInt(3, question.getTest_a_vote());
				statement.setBinaryStream(4, base64ToBinaryStream(current_b_audio));
				statement.setInt(5, question.getTest_b_vote());
				statement.setInt(6, question.getId());
				
				rowUpdated=statement.executeUpdate()>0;
			}
			return rowUpdated;
		}
	//select question by id
	public ABTest selectQuestion(int id) throws IOException {
		ABTest question=null;
		try(
				PreparedStatement preparedstatement=this.connection.prepareStatement(SELECT_USER_BY_ID);){
			preparedstatement.setInt(1, id);
			System.out.println(preparedstatement);
			ResultSet rs=preparedstatement.executeQuery();
			
			while(rs.next()) {
				String test_name=rs.getString("test_name");
				InputStream test_a=rs.getBinaryStream("test_a");
				int a_vote=rs.getInt("test_a_vote");
				InputStream test_b=rs.getBinaryStream("test_b");
				int b_vote=rs.getInt("test_b_vote");
				
				question=new ABTest(id,test_name,test_a,readFiles(test_a),a_vote,test_b,readFiles(test_b),b_vote);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return question;
	}
	//select all questions
	public ArrayList<ABTest> selectAllQuestions() throws IOException {
		ArrayList<ABTest>questions=new ArrayList<ABTest>();
		try(
				PreparedStatement preparedstatement=this.connection.prepareStatement(SELECT_ALL_USERS);){
			System.out.println(preparedstatement);
			ResultSet rs=preparedstatement.executeQuery();
			
			while(rs.next()) {
				int id=rs.getInt("id");
				String test_name=rs.getString("test_name");
				InputStream test_a=rs.getBinaryStream("test_a");
				int a_vote=rs.getInt("test_a_vote");
				InputStream test_b=rs.getBinaryStream("test_b");
				int b_vote=rs.getInt("test_b_vote");
				
				questions.add(new ABTest(id,test_name,test_a,readFiles(test_a),a_vote,test_b,readFiles(test_b),b_vote));
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
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
		ArrayList<ABTest>listABTests=selectAllQuestions();
		char[] chararray=vote_result;
		Character ch;
		PreparedStatement preparedstatement;
		boolean rowUpdated;
		for(int i=0;i<listABTests.size();i++)
		{ 
			ch=chararray[i];
			int id=listABTests.get(i).getId();
			 if(ch.equals('a')) {
				 preparedstatement=this.connection.prepareStatement(UPDATE_PLUS_A_SQL); 
			 }
			 else {
				 preparedstatement=this.connection.prepareStatement(UPDATE_PLUS_B_SQL); 
			 }
			 
			 preparedstatement.setInt(1, id);
			 rowUpdated=preparedstatement.executeUpdate()>0;
		}
	}
}
