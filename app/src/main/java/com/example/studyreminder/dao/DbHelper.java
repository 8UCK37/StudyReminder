package com.example.studyreminder.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Questions";




    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create Table QuestionDetails(id INTEGER primary key AUTOINCREMENT, topic TXT, question TXT)");
      }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists QuestionDetails");
        onCreate(sqLiteDatabase);
    }

    //insert primary data of user


    public Boolean insertQuestionData(String topic,String question)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("topic", topic);
        contentValues.put("question",question );

        long result=DB.insert("QuestionDetails", null, contentValues);
            if (result == -1) {
                return false;
            } else {
                return true;
            }

    }
    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from QuestionDetails", null);
        return cursor;
    }

}
