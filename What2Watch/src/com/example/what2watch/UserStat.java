package com.example.what2watch;

public class UserStat {
	User user = null;
	int timeView = 0;
	String favorieteGenre = null;
	String favorieteActor = null;
	String favorieteDirector = null;
	
	public UserStat(User user){
		this.user = user;
	}
	public void setTimeView(int timeView){
		this.timeView=timeView;
		
	}
	public void setFavoriterGenre(String genre){
		this.favorieteGenre=genre;
	}
	public void setFavorieteActor(String actor){
		this.favorieteActor = actor;
		
	}
	public void setFavorieteDirector(String director){
		this.favorieteDirector=director;
	}
	public int getTimeView(){
		return timeView;
	}
	public String getFavorieteGenre(){
		return this.favorieteGenre;
	}
	public String getFavorieteDirector(){
		return this.favorieteDirector;
	}
	public String getFavorieteActor(){
		return this.favorieteActor;
	}
}
