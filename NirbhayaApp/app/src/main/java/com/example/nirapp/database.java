package com.example.nirapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class database extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "nirbhaya";
	// private static final String TABLE_REGISTER = "register";
	private static final String TABLE_GCONTACT = "green";
	private static final String TABLE_YCONTACT = "yellow";
	private static final String TABLE_RCONTACT = "red";
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	// private static final String KEY_PASSWORD = "password";
	// private static final String KEY_ADDRESS = "address";
	// private static final String KEY_PH_NO = "phone_number";
	Context con;

	public database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		 * String CREATE_TABLE = "CREATE TABLE " + TABLE_REGISTER + "(" + KEY_ID
		 * + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PASSWORD +
		 * " TEXT," + KEY_ADDRESS + " TEXT," + KEY_PH_NO + " TEXT" + ")";
		 * db.execSQL(CREATE_TABLE);
		 */
		String CREATE_TABLEG = "CREATE TABLE " + TABLE_GCONTACT + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
		db.execSQL(CREATE_TABLEG);

		String CREATE_TABLEY = "CREATE TABLE " + TABLE_YCONTACT + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
		db.execSQL(CREATE_TABLEY);

		String CREATE_TABLER = "CREATE TABLE " + TABLE_RCONTACT + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT)";
		db.execSQL(CREATE_TABLER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_GCONTACT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_YCONTACT);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RCONTACT);
		onCreate(db);
	}

	/*
	 * public void addData(nir_data data) {
	 * 
	 * SQLiteDatabase db = this.getWritableDatabase();
	 * 
	 * ContentValues values = new ContentValues(); values.put(KEY_NAME,
	 * data.getName()); values.put(KEY_PASSWORD, data.getPassword());
	 * values.put(KEY_ADDRESS, data.getAdrs()); values.put(KEY_PH_NO,
	 * data.getPhoneNumber());
	 * 
	 * db.insert(TABLE_REGISTER, null, values); //db.close();
	 * 
	 * 
	 * }
	 */

	/*
	 * public Cursor getUserdetail(String uname,String pas) throws SQLException
	 * {
	 * 
	 * SQLiteDatabase db=this.getWritableDatabase(); Cursor mCursor =
	 * db.query(true, TABLE_REGISTER, new String[]{ KEY_ID, KEY_NAME,
	 * KEY_PASSWORD, KEY_ADDRESS, KEY_PH_NO }, KEY_NAME + "='" + uname+"'" ,
	 * null, null, null, null, null );
	 * 
	 * if (mCursor != null) {
	 * 
	 * mCursor.moveToFirst(); } return mCursor; }
	 */

	public void addContact(nir_data data, String sig) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		if (sig.equals("green")) {
			values.put(KEY_NAME, data.getName1());
			db.insert(TABLE_GCONTACT, null, values);
		}
		if (sig.equals("yellow")) {
			values.put(KEY_NAME, data.getName1());
			db.insert(TABLE_YCONTACT, null, values);
		}
		if (sig.equals("red")) {
			values.put(KEY_NAME, data.getName1());
			db.insert(TABLE_RCONTACT, null, values);
		}
	}

	public Cursor getContact(String sig) throws SQLException {

		String table = null;
		SQLiteDatabase db = this.getWritableDatabase();
		if (sig.equals("green")) {
			table = TABLE_GCONTACT;
		}
		if (sig.equals("yellow")) {
			table = TABLE_YCONTACT;
		}
		if (sig.equals("red")) {
			table = TABLE_RCONTACT;
		}
		Cursor mCursor = db.query(true, table,
				new String[] { KEY_ID, KEY_NAME }, null, null, null, null,
				null, null);

		if (mCursor != null) {

			mCursor.moveToFirst();
		}
		return mCursor;
	}

	public void deletContact(String id, String sig) {
		SQLiteDatabase db = this.getWritableDatabase();
		Log.v("id", id);
		if (sig.equals("green")) {
			db.delete(TABLE_GCONTACT, KEY_ID + " = ?", new String[] { id });
			db.close();
		}
		if (sig.equals("yellow")) {
			db.delete(TABLE_YCONTACT, KEY_ID + " = ?", new String[] { id });
			db.close();
		}
		if (sig.equals("red")) {
			db.delete(TABLE_RCONTACT, KEY_ID + " = ?", new String[] { id });
			db.close();
		}
	}
}
