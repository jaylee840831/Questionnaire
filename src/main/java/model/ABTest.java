package model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ABTest {

	private int id;
	private String test_name;
	private InputStream test_a;
	private String test_a2;
	private int test_a_vote;
	private InputStream test_b;
	private String test_b2;
	private int test_b_vote;
	
	public ABTest(int id, String test_name, InputStream test_a,String test_a_2 ,int test_a_vote, InputStream test_b,String test_b2 ,int test_b_vote) {
		super();
		this.id = id;
		this.test_name = test_name;
		this.test_a = test_a;
		this.test_a2=test_a_2;
		this.test_a_vote = test_a_vote;
		this.test_b = test_b;
		this.test_b2=test_b2;
		this.test_b_vote = test_b_vote;
	}
	
	public ABTest(String test_name, InputStream test_a,String test_a_2 ,int test_a_vote, InputStream test_b,String test_b2 ,int test_b_vote) {
		super();
		this.test_name = test_name;
		this.test_a = test_a;
		this.test_a2=test_a_2;
		this.test_a_vote = test_a_vote;
		this.test_b = test_b;
		this.test_b2=test_b2;
		this.test_b_vote = test_b_vote;
	}
	
	public int getId() {
		return id;
	}
	public String getTest_name() {
		return test_name;
	}
	public InputStream getTest_a() {
		return test_a;
	}
	public int getTest_a_vote() {
		return test_a_vote;
	}
	public InputStream getTest_b() {
		return test_b;
	}
	public int getTest_b_vote() {
		return test_b_vote;
	}
	public String getTest_a2() {
		return test_a2;
	}

	public String getTest_b2() {
		return test_b2;
	}
	
	
	
	
}
