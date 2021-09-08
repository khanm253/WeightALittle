package com.example.weighttracker;

import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Weights.db";
    public static final String TABLE_NAME = "Weights_Table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "WEIGHT";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,DATE TEXT,WEIGHT REAL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertdata(String date, String weight){

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_2,date);
        cv.put(COL_3,weight);
        long result = db.insert(TABLE_NAME,null,cv);
        if(result==-1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getallData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cr = db.rawQuery("select * from "+ TABLE_NAME,null);
        return cr;

    }

    public boolean UpdateData(String id,String date, String weight){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1,id);
        cv.put(COL_2,date);
        cv.put(COL_3,weight);
        db.update(TABLE_NAME,cv,"ID = ?",new String[] { id });
        return true;
    }

    public int deleteData(String id){
        SQLiteDatabase db = this.getReadableDatabase();
           return  db.delete(TABLE_NAME,"ID = ?",new String[] { id });

    }
}
