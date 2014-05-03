package com.example.what2watch;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	private int nbrWTW = 0;
	private List<Movie> wantToWatch = null;
	private Context context = null;
	public User(){
		
	}
	public User(Context context, String login){
		this.context = context;
		this.login = login;
		dbAdapter db = new dbAdapter(context);
		db.createDatabase();
		db.open();
		Cursor data = db.execSQL("SELECT rowid as _id,Name ,FirstName , Password , Age FROM User WHERE Login = ?",new String[] {login});
		if(data.moveToFirst()){
			this.name = data.getString(1);
			this.firstname = data.getString(2);
			this.age = data.getInt(4);
			this.password = data.getString(3);
		}
		Cursor wTw = db.execSQL("SELECT rowid as _id, ID FROM WantToWatch WHERE Login = ?",new String[] {login});
		if(wTw.moveToFirst()){
			wantToWatch.add(new Movie(context,wTw.getString(1),null));
			int i =1;
			nbrWTW = i;
			while(wTw.moveToNext()){
				wantToWatch.add(new Movie(context,wTw.getString(1),null));
				i++;
				nbrWTW++;
			}
		}
		db.close();
	} 
	public User(Context context,String login,String name, String firstname , int age,String password, String question, String answer){
		this.name = name;
		this.firstname = firstname;
		this.age = age;
		this.question = question;
		this.answer = answer;
		this.password = password;
		this.login = login;
		this.context = context;

	};
	public User(Context context,String login,String name, String firstname , int age,String password){
		this.name = name;
		this.firstname = firstname;
		this.age = age;
		this.password = password;
		this.login = login;
		this.context=context;

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
		dbAdapter db = new dbAdapter(this.context);
		db.createDatabase();
		db.open();
		ContentValues cv = new ContentValues();
		cv.put("Name",name);
		db.update("User", cv, "Login = ?", new String[] {this.getLogin()});
		this.name = name;
		db.close();
	}
	void setFirstName(String firstname){
		dbAdapter db = new dbAdapter(this.context);
		db.createDatabase();
		db.open();
		ContentValues cv = new ContentValues();
		cv.put("FirstName",firstname);
		db.update("User", cv, "Login = ?", new String[] {this.getLogin()});
		this.firstname = firstname;
		db.close();
	}
	void setLogin(String login){
		this.login = login;
	}
	void setAge(String age){
		dbAdapter db = new dbAdapter(this.context);
		db.createDatabase();
		db.open();
		ContentValues cv = new ContentValues();
		cv.put("Age",Integer.parseInt(age));
		db.update("User", cv, "Login = ?", new String[] {this.getLogin()});
		this.age = Integer.parseInt(age);
		db.close();
	}
	void setQuestion(String question){
		this.question = question;
	}
	void setAnswer(String answer){
		this.answer = answer;
	}
	
	boolean setPassword(String newPswd){
		if(this.getPassword().equals(newPswd)){
			dbAdapter db = new dbAdapter(this.context);
			db.createDatabase();
			db.open();
			ContentValues cv = new ContentValues();
			cv.put("Password",newPswd);
			db.update("User", cv, "Login = ?", new String[] {this.getLogin()});
			this.password = newPswd;
			db.close();
			return true;
		}
		return false;
	}
	public void addUserTodatabase(){
		dbAdapter db = new dbAdapter(context);
		db.createDatabase();
		db.open();
		db.addToDatabase("User", new String[] {"Login" , "FirstName" , "Name" ,"Password","Age"}, new String[] {this.getLogin(),this.getFirstName(),this.getName(),this.getPassword(),Integer.toString(this.getAge())});
		db.close();
	}
	public boolean checkLogin(String login){
		dbAdapter db = new dbAdapter(context);
		db.createDatabase();
		db.open();
		Cursor data = db.execSQL("SELECT Login FROM User WHERE Login = ?", new String[] {login});
		db.close();
		return data.moveToFirst();
	}
	public int getNbrWTW() {
		return nbrWTW;
	}
	public void setNbrWTW(int nbrWTW) {
		this.nbrWTW = nbrWTW;
	}
	public List<Movie> getWantToWatch() {
		dbAdapter db = new dbAdapter(context);
		db.createDatabase();
		db.open();
		Cursor wTw = db.execSQL("SELECT rowid as _id, ID FROM WantToWatch WHERE Login = ?",new String[] {login});
		if(wTw.moveToFirst()){
			wantToWatch.add(new Movie(context,wTw.getString(1),this));
			int i =1;
			nbrWTW = i;
			while(wTw.moveToNext()){
				wantToWatch.add(new Movie(context,wTw.getString(1),this));
				i++;
				nbrWTW++;
			}
		}
		db.close();
		return wantToWatch;
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
