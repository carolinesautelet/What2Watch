package com.example.what2watch;

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
		name = name;
		firstname = firstname;
		age = age;
		question = question;
		answer = answer;
		password = password;
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
	
}
