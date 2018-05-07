package com.concepts.myconcepts.databaseutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.concepts.myconcepts.usecases.usercrud.User;

/**
 * Created by tasol on 17/1/18.
 */

public class UserDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "conceptDB";
    private static final int DATABASE_VERSION = 1;
    private String TABLE_NAME = null;
    String TAG = "@@@TTT";
    Context context;
    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String users = "CREATE TABLE IF NOT EXISTS users (user_id TEXT PRIMARY KEY,email TEXT UNIQUE,name TEXT,password TEXT)";
        db.execSQL(users);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public int countAllRows(String tableName) {
        String selectQuery = "SELECT * FROM " + tableName;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor.getCount();
    }

    public void deleteTableData(String tableName){
        //Adds the destinations
        SQLiteDatabase database = this.getWritableDatabase();
        String qur = "DELETE FROM "+tableName;
        database.execSQL(qur);
    }


    //SURVEYOUR CRUD

    public void addUsers(String surveyor_id, String email, String name, String password) {
        //Adds the destinations
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id", surveyor_id);
        contentValues.put("email", email);
        contentValues.put("name", name);
        contentValues.put("password", password);
        database.insert("users", null, contentValues);
        Log.v(TAG, "user Record Added");
        database.close();
    }

    public User getUSerDetail(String email) {
        String selectQuery = "SELECT * FROM users WHERE email ='"+email+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        User detailsF = null;
        if (cursor.moveToFirst()) {
            do {
                detailsF = new User();
                detailsF.setUserID(cursor.getString(cursor.getColumnIndex("user_id")));
                detailsF.setUserEmail(cursor.getString(cursor.getColumnIndex("email")));
                detailsF.setName(cursor.getString(cursor.getColumnIndex("name")));
                detailsF.setPassword(cursor.getString(cursor.getColumnIndex("password")));

            } while (cursor.moveToNext());
        }
        return detailsF;
    }

    public String checkUserExist(String email) {
        String password = "";
        String selectQuery = "SELECT password FROM users WHERE email ='"+email+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                password = cursor.getString(cursor.getColumnIndex("password"));

            } while (cursor.moveToNext());
        }
        return password;
    }

    public boolean checkEmailExist(String email) {
        String selectQuery = "SELECT * FROM users WHERE email ='"+email+"'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount() == 1){
            return true;
        }else {
            return false;
        }

    }

}
