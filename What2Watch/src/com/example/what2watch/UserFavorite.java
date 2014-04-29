package com.example.what2watch;

public class UserFavorite {
	int rating = 0;
	boolean wtwstate = false;
	int nbrOfView = 0;
	Movie movie = null;
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public boolean isWtwstate() {
		return wtwstate;
	}
	public void setWtwstate(boolean wtwstate) {
		this.wtwstate = wtwstate;
	}
	public int getNbrOfView() {
		return nbrOfView;
	}
	public void setNbrOfView(int nbrOfView) {
		this.nbrOfView = nbrOfView;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
}
