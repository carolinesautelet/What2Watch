package com.example.what2watch;

import java.io.IOException; 
import java.util.HashSet;
import java.util.Set;

import android.content.ContentValues;
import android.content.Context; 
import android.database.Cursor; 
import android.database.SQLException; 
import android.database.sqlite.SQLiteDatabase; 
import android.util.Log; 
 
public class dbAdapter  
{ 
    protected static final String TAG = "DataAdapter"; 
 
    private final Context mContext; 
    private SQLiteDatabase mDb; 
    private DataBaseHelper mDbHelper; 
 
    public dbAdapter(Context context)  
    { 
        this.mContext = context; 
        mDbHelper = new DataBaseHelper(mContext); 
    } 
 
    public dbAdapter createDatabase() throws SQLException  
    { 
        try  
        { 
            mDbHelper.createDataBase(); 
        }  
        catch (IOException mIOException)  
        { 
            Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase"); 
            throw new Error("UnableToCreateDatabase"); 
        } 
        return this; 
    } 
 
    public dbAdapter open() throws SQLException  
    { 
        try  
        { 
            mDbHelper.openDataBase(); 
            mDbHelper.close(); 
            mDb = mDbHelper.getReadableDatabase(); 
        }  
        catch (SQLException mSQLException)  
        { 
            Log.e(TAG, "open >>"+ mSQLException.toString()); 
            throw mSQLException; 
        } 
        return this; 
    } 
 
    public void close()  
    { 
        mDbHelper.close(); 
    } 
 
     public Cursor getTestData() 
     { 
         try 
         { 
             String sql ="SELECT rowid, Title, Year FROm Movie"; 
 
             Cursor mCur = mDb.rawQuery(sql, null); 
             if (mCur!=null) 
             { 
                mCur.moveToNext(); 
             } 
             return mCur; 
         } 
         catch (SQLException mSQLException)  
         { 
             Log.e(TAG, "getTestData >>"+ mSQLException.toString()); 
             throw mSQLException; 
         } 
     }
     
     public Cursor execSQL(String sqlRequest, String[] ArgsforRequest)
     {
    	 try
    	 {		 
    		 Cursor mCur = mDb.rawQuery(sqlRequest, ArgsforRequest);

    		/* if (mCur!=null)
    		 {
    			 mCur.moveToNext();
    		 }*/
    		 return mCur;
    	 }
    	 catch (SQLException mSQLException)
    	 { 
             Log.e(TAG, "getTestData >>"+ mSQLException.toString()); 
             throw mSQLException; 
         } 
     }
     public void execSQLInsert(String sqlRequest,String[] argsforRequest){
    	 mDb.execSQL(sqlRequest,argsforRequest);
     }
     
     public boolean addToDatabase(String table_name, String[] OrderedColumn, String[] OrderedValues)
     {
    	ContentValues cv = new ContentValues();
    	 try
    	 {
    		 for(int i=0; i<OrderedValues.length; i++) {
    			 cv.put(OrderedColumn[i],OrderedValues[i]);
    		 }
    		     		 
    		 mDb.insert(table_name, null, cv);
    		 return true;
    	 }
    	 catch(Exception ex)
    	 {
    		 return false;
    	 }
     }
     
     public String getStringFromRequest(String sqlRequest, String[] ArgsforRequest, String ColumnName)
     {
    	 Cursor cursor;
    	 cursor = execSQL(sqlRequest, ArgsforRequest);
    	 try {
    		 cursor.moveToFirst();
    		 return cursor.getString(cursor.getColumnIndex(ColumnName));
    	 } catch (Exception ex) {
    		 return "";
    	 }
    		
     }

     public Set<String> getAllData(String labelColumn, String table) {
   	  Set<String> set = new HashSet<String>();
   	  String selectQuery = "select " + labelColumn + " from " + table;
   	  Cursor cursor = null;
   	  cursor = mDb.rawQuery(selectQuery, null);
   	
   	  if (cursor.moveToFirst()) {
   	   do {
   	   set.add(cursor.getString(0));
   	   } while (cursor.moveToNext());
   	  }
   	  return set;
   	 }
    
     public Set<String> getAllDataCDT(String labelColumn, String table , String columncdt , String cdt){
   	 Set<String> set = new HashSet<String>();
  	  	String selectQuery = "SELECT " + labelColumn + " FROM " + table + " WHERE " + columncdt + " = ?";
  	 Cursor cursor = null;
  	 String[] args = {cdt};
	  cursor = mDb.rawQuery(selectQuery, args);
	
	  if (cursor.moveToFirst()) {
	   do {
	   set.add(cursor.getString(0));
	   } while (cursor.moveToNext());
	  }
	  return set;
    }
     public Set<String> getAllDataSingle(String request,String[] args){
    	 Set<String> set = new HashSet<String>();
    	 Cursor cursor = null;
    	 cursor = mDb.rawQuery(request, args);
    	 if (cursor.moveToFirst()) {
    		   do {
    		   set.add(cursor.getString(1));
    		   } while (cursor.moveToNext());
    		  }
    		  return set;
     }
     
     
     public void update(String table, ContentValues values, String whereClause, String[] whereArgs){
    	 mDbHelper.update(table, values, whereClause, whereArgs);
    	 
     }
} 

