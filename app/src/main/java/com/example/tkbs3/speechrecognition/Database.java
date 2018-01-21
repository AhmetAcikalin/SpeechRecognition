package com.example.tkbs3.speechrecognition;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.tkbs3.speechrecognition.Memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emine on 28.12.2017.
 */
public class Database extends SQLiteOpenHelper {
	private static final String LOG = "DatabaseHelper";
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "SpeechMemory";
	private static final String TABLE_MEMORY = "memory";

	private static final String M_ID="word_id";
	private static final String M_WORD= "word_name";
	private static final String M_ANSWER="word_answer";
	private static final String CREATE_TABLE_MEMORY = "CREATE TABLE " + TABLE_MEMORY
			+ "(" + M_ID + " INTEGER PRIMARY KEY , " +M_WORD+" TEXT, " +M_ANSWER + " TEXT)";

	public Database(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(CREATE_TABLE_MEMORY);

	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int i, int i1) {
		db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_MEMORY));
		// create new tables
		onCreate(db);
	}
	public long createMemory(Memory memory) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(M_WORD, memory.getWord_name());
		values.put(M_ANSWER, memory.getWord_answer());
		// insert row
		long lecturer_id = db.insert(TABLE_MEMORY, null, values);
		return lecturer_id;
	}
	public boolean deleteMemory(int memory_id){
		SQLiteDatabase db = this.getReadableDatabase();
		return db.delete(TABLE_MEMORY,M_ID+" = "+memory_id,null) > 0;
	}
	public void updateMemory(int memory_id, String memory_name, String memory_answer)
	{
		try
		{
			SQLiteDatabase db1=this.getWritableDatabase();
			ContentValues cv1=new ContentValues();
			cv1.put(M_WORD,memory_name);
			cv1.put(M_ANSWER,memory_answer);
			String[] sutunlar={M_ID,M_WORD,M_ANSWER};
			Cursor okunanlar=db1.query(TABLE_MEMORY, sutunlar, M_ID+"=?", new String[] {String.valueOf(memory_id)}, null, null, null);
			if(okunanlar.moveToNext()){
				db1.update(TABLE_MEMORY, cv1, M_ID+" = ?", new String[]{String.valueOf(memory_id)});
			}
			else
			db1.close();
		}
		catch(Exception e)
		{

		}
	}




	public List<String> getAllMemories()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor okunanlar=db.rawQuery("select * from "+TABLE_MEMORY,null);
		List<String> memories = new ArrayList<String>();
		while(okunanlar.moveToNext()) // şarta uyan veriler okundu.
		{
			memories.add(Integer.parseInt(okunanlar.getString(okunanlar.getColumnIndex(M_ID)))+"."+okunanlar.getString(okunanlar.getColumnIndex(M_WORD))+";"+okunanlar.getString(okunanlar.getColumnIndex(M_ANSWER)));
		}

		db.close();
		return memories;
	}

}

