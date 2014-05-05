package com.example.what2watch;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;

public class UserStat_Result {

	private Context mcontext;
	private User muser;

	private int nbrFilm;
	private float nbrHour;
	private int nbrRate;
	private boolean badgeAquired[];
	
	private dbAdapter db;
	private Cursor cursor;
	
	
	
	public UserStat_Result(Context context, User user){
		this.mcontext = context;
		this.muser = user;
		
		db = new dbAdapter(mcontext);         
		db.createDatabase();    
		db.open();
	
		//nbrFilm
		cursor = db.execSQL("SELECT rowid as _id, ID FROM NumberOfView WHERE Login=?", new String[] {user.getLogin()});
		this.setNbrFilm(cursor.getCount());
	
		//nbrHour
    	cursor = db.execSQL("SELECT SUM(Duration) FROM Movie M, NumberOfView N WHERE Login = ? and N.ID = M.ID", new String[] {user.getLogin()});
    	cursor.moveToFirst();
    	this.setNbrHour((float) cursor.getInt(0)/60);
    	
    	//nbrRate
    	cursor = db.execSQL("SELECT ID FROM Rating  WHERE Login = ?", new String[] {user.getLogin()});
		this.setNbrRate(cursor.getCount());
		
		//MostDir

		//MostAct

		//MostGenre
		
		//badges
		setBadgeAquired(checkForBadge(user.getLogin()));
		
		db.close();
	}


	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private boolean[] checkForBadge(String UserLogin)
	{
		boolean[] badges = new boolean[4];

		String requete_badge = "SELECT M.rowid as _id, Title, M.ID FROM Movie M, NumberOfView N WHERE Login = ? and N.ID = M.ID and M.Title like ?";
		String requete_disney = "SELECT M.rowid as _id, Title, M.ID FROM Movie M, Genre G, NumberOfView N WHERE GenreName like \"disney\" and N.Login=? and M.ID=N.ID and M.ID=G.ID GROUP BY Title";
		ArrayList args = new ArrayList();
		args.add(new String[] {UserLogin, "Pirates of the Caribbean%"});
		args.add(new String[] {UserLogin, "Star Wars: Épisode%"});
		args.add(new String[] {UserLogin, "The Lord of the Rings%"});

		db.open(); 
		for(int i=0; i<3; i++){
			cursor = db.execSQL(requete_badge,(String[]) args.get(i));
			if(cursor.getCount()>=2){
				badges[i] = true;
			}
			else{
				badges[i] = false;
			}
		}
		cursor = db.execSQL(requete_disney, new String[] {UserLogin});
		if(cursor.getCount()>=2){
			badges[3] = true;
		}
		else{
			badges[3] = false;
		}	

		db.close();
		return badges;
	}





	public int getNbrFilm() {
		return nbrFilm;
	}

	public void setNbrFilm(int nbrFilm) {
		this.nbrFilm = nbrFilm;
	}

	public float getNbrHour() {
		return nbrHour;
	}

	public void setNbrHour(float nbrHour) {
		this.nbrHour = nbrHour;
	}

	public int getNbrRate() {
		return nbrRate;
	}

	public void setNbrRate(int nbrRate) {
		this.nbrRate = nbrRate;
	}

	public boolean[] getBadgeAquired() {
		return badgeAquired;
	}

	public void setBadgeAquired(boolean badgeAquired[]) {
		this.badgeAquired = badgeAquired;
	}


}
