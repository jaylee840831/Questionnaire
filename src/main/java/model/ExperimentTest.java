package model;

import java.io.InputStream;

public class ExperimentTest {

	private int id;
	private String test_name;
	private String data_name;
	private int score1;
	private int score2;
	private int score3;
	private int score4;
	private int score5;
	private InputStream audio;
	private String audio2;
	
	public ExperimentTest(int id,String test_name, String data_name, int score1, int score2, int score3, int score4,
			int score5, InputStream audio, String audio2) {
		super();
		this.id=id;
		this.test_name = test_name;
		this.data_name = data_name;
		this.score1 = score1;
		this.score2 = score2;
		this.score3 = score3;
		this.score4 = score4;
		this.score5 = score5;
		this.audio = audio;
		this.audio2 = audio2;
	}
	public ExperimentTest(String test_name, String data_name, int score1, int score2, int score3, int score4,
			int score5, InputStream audio, String audio2) {
		super();
		this.test_name = test_name;
		this.data_name = data_name;
		this.score1 = score1;
		this.score2 = score2;
		this.score3 = score3;
		this.score4 = score4;
		this.score5 = score5;
		this.audio = audio;
		this.audio2 = audio2;
	}
	public int getId() {
		return id;
	}
	public String getTest_name() {
		return test_name;
	}
	public String getData_name() {
		return data_name;
	}
	public int getScore1() {
		return score1;
	}
	public int getScore2() {
		return score2;
	}
	public int getScore3() {
		return score3;
	}
	public int getScore4() {
		return score4;
	}
	public int getScore5() {
		return score5;
	}
	public InputStream getaudio() {
		return audio;
	}
	public String getaudio2() {
		return audio2;
	}
	
}
