package de.vogella.android.sqlite.first;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	public static final String TABLE_COMMENTS = "comments";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_COMMENT = "comment";
	
	private static final String DATABASE_NAME = "commments.db";
	private static final int DATABASE_VERSION = 1;
	
	//Database creation sql statement
	private static final String DATABASE_CREATE = "create table"
			+ TABLE_COMMENTS + "(" + COLUMN_ID
		    + " integer primary key autoincrement, " + COLUMN_COMMENT
		    + " text not null);";
	
	public MySQLiteHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	//Run the database creation statement
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(MySQLiteHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    //Drop all tables
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
	    //Query select 'drop table' || name || ';' 
	    onCreate(db);
	}
}
