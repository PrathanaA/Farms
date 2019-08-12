package com.FarmPe.Farmer.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "PHONE_NO";
    public static final String COL_3 = "PASSWORD";
    public static final String COL_4 = "CHECKED";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,PHONE_NO TEXT,PASSWORD TEXT,CHECKED INTEGER)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }


    public boolean insertData(String name,String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,pass);
        contentValues.put(COL_4,1);

      //  System.out.println("sssssssssssss123456789"+name);
      //  System.out.println("sssssssssssss98765432"+pass);
        long result = db.insert(TABLE_NAME,null ,contentValues);

        System.out.println("insertttttt "+result);

        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(String phone) {
        Cursor res = null;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String Query = "select " + COL_3 + " from " + TABLE_NAME + " where " + COL_2 + " = " + phone;
            System.out.println("qquueerryy"+Query);
            res = db.rawQuery(Query, null);
        }catch (Exception e){
            
        }
        return res;
    }


 /*   public boolean updateData(String phone,String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
       // contentValues.put(COL_1,id);
        contentValues.put(COL_2,phone);
        contentValues.put(COL_3,pass);
        long result= db.update(TABLE_NAME, contentValues, "NAME = ?",new String[] { phone });
        System.out.println("lllllllllllllllllllllllllllll"+result);

        return true;
    }*/
    public boolean updateContact (String phone, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, phone);
        contentValues.put(COL_3, pass);

        long result= db.update(TABLE_NAME, contentValues, "PHONE_NO = ? ", new String[] { phone } );

        return true;
    }


    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "PHONE_NO = ?",new String[] {id});
    }


    public boolean isEmailExists(String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,// Selecting Table
                new String[]{COL_3},//Selecting columns want to query
                COL_2 + "=?",
                new String[]{phone},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }
}
