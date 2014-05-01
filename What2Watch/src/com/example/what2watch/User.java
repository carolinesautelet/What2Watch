package com.example.what2watch;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import android.os.Parcel;
import android.os.Parcelable;
public class User implements Parcelable{
	private String name = null;
	private String firstname = null;
	private int age = 0;
	private String login = null;
	private String question = null;
	private String answer = null;
	private String password = null;
	private UserFavorite[] favorites = null;

	
	public User(String login,String name, String firstname , int age,String password, String question, String answer){
		this.name = name;
		this.firstname = firstname;
		this.age = age;
		this.question = question;
		this.answer = answer;
		this.password = password;
		this.login = login;
		
	};
	public User(String login,String name, String firstname , int age,String password){
		this.name = name;
		this.firstname = firstname;
		this.age = age;
		this.password = password;
		this.login = login;
	
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

	public UserFavorite[] getFavorites() {
		return favorites;
	}
	public void setFavorites(UserFavorite[] favorites) {
		this.favorites = favorites;
	}
	
	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(firstname);
		dest.writeString(name);
		dest.writeString(login);
		dest.writeString(password);
		dest.writeInt(age);
	}
	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>()
			{
			    @Override
			    public User createFromParcel(Parcel source)
			    {
			        return new User(source);
			    }

			    @Override
			    public User[] newArray(int size)
			    {
				return new User[size];
			    }
			};

	public User(Parcel in) {
				this.firstname = in.readString();
				this.name = in.readString();
				this.login = in.readString();
				this.password =in.readString();
				this.age = in.readInt();
			}
	
}
