package com.example.maopao.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQL extends SQLiteOpenHelper {

	private static final String chat = "create table chat(" 
			+ "id integer primary key," 
			+ "tag integer,"
			+ "content text,"
			+ "mark integer)";
	private Context context;

	public SQL(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Toast.makeText(context, "succeed create user", Toast.LENGTH_SHORT).show();
		db.execSQL(chat);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
