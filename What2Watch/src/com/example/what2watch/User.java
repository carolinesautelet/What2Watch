package com.example.what2watch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class User {
	private UserStat stat = null;
	private String name = null;
	private String firstname = null;
	private int age = 0;
	private String login = null;
	private String question = null;
	private String answer = null;
	private String password = null;

	
	public User(String login,String name, String firstname , int age,String password, String question, String answer){
		this.name = name;
		this.firstname = firstname;
		this.age = age;
		this.question = question;
		this.answer = answer;
		this.password = password;
		this.stat = new UserStat(this);
	};
	public String getName(){
		return name;
	}
	public String getFirstName(){
		return firstname;
	}
	public String getQuestion(){
		return question;
	}
	public String getAnswer(){
		return answer;
	}
	public String getPassword(){
		return password;
	}
	public int getAge(){
		return age;
	}
	public String getLogin(){
		return login;
	}
	void setName(String name){
		this.name = name;
	}
	void setFirstName(String firstname){
		this.firstname = firstname;
	}
	void setLogin(String login){
		this.login = login;
	}
	void setAge(int age){
		this.age=age;
	}
	void setQuestion(String question){
		this.question = question;
	}
	void setAnswer(String answer){
		this.answer = answer;
	}
	void setPassword(String password){
		this.password = password;
	}
	boolean changePassword(String old,String toset){
		if(old.equals(this.password)){
			this.setPassword(toset);
			return true;
		}
		return false;
	}
	
	
}
