package de.vogella.android.sqlite.first;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Comments {
	
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  //NOTE: pulls strings from MySQLiteHelper. My implementation reverses this so that MySQL.. pulls from Table DAO
	  private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
	      MySQLiteHelper.COLUMN_COMMENT };
	  
	  public Comments(Context context){
		  dbHelper = new MySQLiteHelper(context);
	  }
	  
	  //open database
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }
	  //close connection
	  public void close() {
		    dbHelper.close();
	  }
	  
	  public Comment createComment(String comment) {
		  	//helper class for storing row values as key value pairs
		    ContentValues values = new ContentValues();
		    //says add comment to my set of values to be inserted
		    values.put(MySQLiteHelper.COLUMN_COMMENT, comment);
		    //returns id of newly inserted row (-1 if error)
		    //NOTE: I might change this because I don't need auto increment, 
		    //my ids are being pulled by JSON and are already defined.
		    long insertId = database.insert(MySQLiteHelper.TABLE_COMMENTS, null,
		        values);
		    //query returns cursor/ Notice the where clause
		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Comment newComment = cursorToComment(cursor);
		    cursor.close();
		    return newComment;
	  }
	  
	  public void deleteComment(Comment comment) {
		    long id = comment.getId();
		    System.out.println("Comment deleted with id: " + id);
		    database.delete(MySQLiteHelper.TABLE_COMMENTS, MySQLiteHelper.COLUMN_ID
		        + " = " + id, null);
	  }
	  
	  public List<Comment> getAllComments() {
		    List<Comment> comments = new ArrayList<Comment>();

		    Cursor cursor = database.query(MySQLiteHelper.TABLE_COMMENTS,
		        allColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Comment comment = cursorToComment(cursor);
		      comments.add(comment);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return comments;
	  }
	  
	  private Comment cursorToComment(Cursor cursor) {
		    Comment comment = new Comment();
		    comment.setId(cursor.getLong(0));
		    comment.setComment(cursor.getString(1));
		    return comment;
	  }

}
