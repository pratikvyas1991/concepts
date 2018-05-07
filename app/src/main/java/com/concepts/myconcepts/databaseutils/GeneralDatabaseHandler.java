package com.concepts.myconcepts.databaseutils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tasol on 17/1/18.
 */

public class GeneralDatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "conceptDB";
    private static final int DATABASE_VERSION = 1;
    private String TABLE_NAME = null;
    String TAG = "@@@TTT";
    Context context;
    public GeneralDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String patients = "CREATE TABLE IF NOT EXISTS patients (patient_id TEXT PRIMARY KEY,firstName TEXT,lastName TEXT,fullName TEXT,dotproviderid TEXT,gender TEXT,dob TEXT,latitude TEXT,longitude TEXT,address TEXT,distance TEXT,isdiagnosed TEXT,responseStatus TEXT)";
        db.execSQL(patients);
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

    public void addSurveyor(String surveyor_id, String name, String email, String firstname, String lastname, String displayname) {
        //Adds the destinations
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("surveyor_id", surveyor_id);
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("displayname", displayname);
        database.insert("dotsurveyor", null, contentValues);
        Log.v(TAG, "dotsurveyor Record Added");
        database.close();
    }

//    public Surveyor getSurveyorDetail(String surveyorId) {
//        String selectQuery = "SELECT * FROM dotsurveyor WHERE surveyor_id ="+surveyorId;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToFirst();
//        Surveyor detailsF = null;
//        if (cursor.moveToFirst()) {
//            do {
//                detailsF = new Surveyor();
//                detailsF.setSurveyorID(cursor.getString(cursor.getColumnIndex("surveyor_id")));
//                detailsF.setSurveyorName(cursor.getString(cursor.getColumnIndex("name")));
//                detailsF.setSurveyorEmail(cursor.getString(cursor.getColumnIndex("email")));
//                detailsF.setSurveyorFirstName(cursor.getString(cursor.getColumnIndex("firstname")));
//                detailsF.setSurveyorLastName(cursor.getString(cursor.getColumnIndex("lastname")));
//                detailsF.setSurveyorDisplayName(cursor.getString(cursor.getColumnIndex("displayname")));
//
//            } while (cursor.moveToNext());
//        }
//        return detailsF;
//    }
//
//    public int checkSurveyorExist(String surveyor_id) {
//        String selectQuery = "SELECT * FROM dotsurveyor WHERE surveyor_id ="+surveyor_id;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        return cursor.getCount();
//    }
//
//    //Surveyed Patient CRUD
//
//    public void addSuryedPatients(String patient_id, String isdiagnosed, String responseStatus, String checkresponseStatus) {
//        //Adds the destinations
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("patient_id", patient_id);
//        contentValues.put("isdiagnosed", isdiagnosed);
//        contentValues.put("responseStatus", responseStatus);
//        contentValues.put("checkresponseStatus", checkresponseStatus);
//
//        database.insert("surveyedpatients", null, contentValues);
//        Log.v(TAG, "Patient Record Added");
//        database.close();
//    }
//
//    public int checkSuryedPatientExist(String patient_id) {
//        String selectQuery = "SELECT * FROM surveyedpatients WHERE patient_id ='"+patient_id+"'";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        return cursor.getCount();
//    }
//
//    public ArrayList<PatietsMap> getAllSuryedPatients() {
//        ArrayList<PatietsMap> allRec = new ArrayList<>();
//        allRec.clear();
//        String selectQuery = "SELECT * FROM surveyedpatients";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToFirst();
//        PatietsMap detailsF;
//        if (cursor.moveToFirst()) {
//            do {
//                detailsF = new PatietsMap();
//                String lat="",lon="";
//                detailsF.setPatientID(cursor.getString(cursor.getColumnIndex("patient_id")));
//                detailsF.setResponseStatus(cursor.getString(cursor.getColumnIndex("responseStatus")));
//                detailsF.setAddress(cursor.getString(cursor.getColumnIndex("checkresponseStatus")));
//
//                String isDiaog = (cursor.getString(cursor.getColumnIndex("isdiagnosed")));
//                if(isDiaog!=null && isDiaog.length()>0){
//                    if(isDiaog.equalsIgnoreCase("true")){
//                        detailsF.isDiagnossed = true;
//                    }else {
//                        detailsF.isDiagnossed = false;
//                    }
//                }else {
//                    detailsF.isDiagnossed = false;
//                }
//                allRec.add(detailsF);
//            } while (cursor.moveToNext());
//        }
//        return allRec;
//    }
//
//
//    //Patient CRUD
//
//
//    public void addPatients(String patient_id, String firstname, String lastName, String fullName, String dotproviderid, String gender, String dob, String latitude, String longitude, String address, String distance, String isdiagnosed, String responseStatus) {
//        //Adds the destinations
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("patient_id", patient_id);
//        contentValues.put("firstName", firstname);
//        contentValues.put("lastName", lastName);
//        contentValues.put("fullName", fullName);
//        contentValues.put("dotproviderid", dotproviderid);
//        contentValues.put("gender", gender);
//        contentValues.put("dob", dob);
//        contentValues.put("latitude", latitude);
//        contentValues.put("longitude", longitude);
//        contentValues.put("address", address);
//        contentValues.put("distance", distance);
//        contentValues.put("isdiagnosed", isdiagnosed);
//        contentValues.put("responseStatus", responseStatus);
//        database.insert("patients", null, contentValues);
//        Log.v(TAG, "Patient Record Added");
//        database.close();
//    }
//
//
//
//    public void updatePatient(String patient_id, String name, String gender, String dob, String latitude, String longitude, String address, String email, String distance, String isdiagnosed, String responseStatus) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("name", name);
//        contentValues.put("gender", gender);
//        contentValues.put("dob", dob);
//        contentValues.put("latitude", latitude);
//        contentValues.put("longitude", longitude);
//        contentValues.put("address", address);
//        contentValues.put("email", email);
//        contentValues.put("distance", distance);
//        contentValues.put("isdiagnosed", isdiagnosed);
//        contentValues.put("responseStatus", responseStatus);
//        database.update("patients", contentValues,"patient_id="+patient_id,null);
//        Log.v(TAG, "Patient Record Updated");
//        database.close();
//    }
//
//    public void updatePatientStatus(String patient_id, String status, String responseStatus) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("isdiagnosed", status);
//        contentValues.put("isdiagnosed", responseStatus);
//        database.update("patients", contentValues,"patient_id="+patient_id,null);
//        Log.v(TAG, "Patient Record Updated");
//        database.close();
//    }
//
//    public int checkPatientExist(String patient_id) {
//        String selectQuery = "SELECT * FROM patients WHERE patient_id ="+patient_id;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        return cursor.getCount();
//    }
//
//    public ArrayList<PatietsMap> getAllPatients() {
//        ArrayList<PatietsMap> allRec = new ArrayList<>();
//        allRec.clear();
//        String selectQuery = "SELECT * FROM patients";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToFirst();
//        PatietsMap detailsF;
//        if (cursor.moveToFirst()) {
//            do {
//                detailsF = new PatietsMap();
//                String lat="",lon="";
//                detailsF.setPatientID(cursor.getString(cursor.getColumnIndex("patient_id")));
//                detailsF.setPatientFirstName(cursor.getString(cursor.getColumnIndex("firstName")));
//                detailsF.setPatientLastName(cursor.getString(cursor.getColumnIndex("lastName")));
//                detailsF.setPatientFullName(cursor.getString(cursor.getColumnIndex("fullName")));
//                detailsF.setDotProviderID(cursor.getString(cursor.getColumnIndex("dotproviderid")));
//                detailsF.setGender(cursor.getString(cursor.getColumnIndex("gender")));
//                detailsF.setDateofBirth(cursor.getString(cursor.getColumnIndex("dob")));
//                detailsF.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex("latitude"))));
//                detailsF.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex("longitude"))));
//                detailsF.setAddress(cursor.getString(cursor.getColumnIndex("address")));
//                detailsF.setDistance(Float.parseFloat(cursor.getString(cursor.getColumnIndex("distance"))));
//                detailsF.setResponseStatus(cursor.getString(cursor.getColumnIndex("responseStatus")));
//                String isDiaog = (cursor.getString(cursor.getColumnIndex("isdiagnosed")));
//                lat = cursor.getString(cursor.getColumnIndex("latitude"));
//                lon = cursor.getString(cursor.getColumnIndex("longitude"));
//                if(isDiaog!=null && isDiaog.length()>0){
//                    if(isDiaog.equalsIgnoreCase("true")){
//                        detailsF.isDiagnossed = true;
//                    }else {
//                        detailsF.isDiagnossed = false;
//                    }
//                }else {
//                    detailsF.isDiagnossed = false;
//                }
//                allRec.add(detailsF);
//            } while (cursor.moveToNext());
//        }
//        return allRec;
//    }
//
//    public ArrayList<PatietsMap> getAllPatientsMap() {
//        ArrayList<PatietsMap> allRec = new ArrayList<>();
//        allRec.clear();
//        String selectQuery = "SELECT * FROM patients";
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToFirst();
//        PatietsMap detailsF;
//        if (cursor.moveToFirst()) {
//            do {
//                detailsF = new PatietsMap();
//                String lat="",lon="";
//                detailsF.setPatientID(cursor.getString(cursor.getColumnIndex("patient_id")));
//                detailsF.setPatientFirstName(cursor.getString(cursor.getColumnIndex("firstName")));
//                detailsF.setPatientLastName(cursor.getString(cursor.getColumnIndex("lastName")));
//                detailsF.setPatientFullName(cursor.getString(cursor.getColumnIndex("fullName")));
//                detailsF.setDotProviderID(cursor.getString(cursor.getColumnIndex("dotproviderid")));
//                detailsF.setGender(cursor.getString(cursor.getColumnIndex("gender")));
//                detailsF.setDateofBirth(cursor.getString(cursor.getColumnIndex("dob")));
//                detailsF.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex("latitude"))));
//                detailsF.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex("longitude"))));
//                detailsF.setAddress(cursor.getString(cursor.getColumnIndex("address")));
//                detailsF.setDistance(Float.parseFloat(cursor.getString(cursor.getColumnIndex("distance"))));
//                detailsF.setResponseStatus(cursor.getString(cursor.getColumnIndex("responseStatusString")));
//
//                String isDiaog = (cursor.getString(cursor.getColumnIndex("isdiagnosed")));
//
//                lat = cursor.getString(cursor.getColumnIndex("latitude"));
//                lon = cursor.getString(cursor.getColumnIndex("longitude"));
//                if(isDiaog!=null && isDiaog.length()>0){
//                    if(isDiaog.equalsIgnoreCase("true")){
//                        detailsF.isDiagnossed = true;
//                    }else {
//                        detailsF.isDiagnossed = false;
//                    }
//                }else {
//                    detailsF.isDiagnossed = false;
//                }
//                if(lat!=null && lat.length()>1){
//                    allRec.add(detailsF);
//                }
//            } while (cursor.moveToNext());
//        }
//        return allRec;
//    }
//
//    public PatietsMap getSinglePatientDetail(String patientID) {
//        String selectQuery = "SELECT * FROM patients WHERE tour_id ="+patientID;
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        cursor.moveToFirst();
//        PatietsMap detailsF = null;
//        if (cursor.moveToFirst()) {
//            do {
//                detailsF = new PatietsMap();
//                detailsF.setPatientID(cursor.getString(cursor.getColumnIndex("patient_id")));
//                detailsF.setPatientFirstName(cursor.getString(cursor.getColumnIndex("firstName")));
//                detailsF.setPatientLastName(cursor.getString(cursor.getColumnIndex("lastName")));
//                detailsF.setPatientFullName(cursor.getString(cursor.getColumnIndex("fullName")));
//                detailsF.setDotProviderID(cursor.getString(cursor.getColumnIndex("dotproviderid")));
//                detailsF.setGender(cursor.getString(cursor.getColumnIndex("gender")));
//                detailsF.setDateofBirth(cursor.getString(cursor.getColumnIndex("dob")));
//                detailsF.setLatitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex("latitude"))));
//                detailsF.setLongitude(Double.parseDouble(cursor.getString(cursor.getColumnIndex("longitude"))));
//                detailsF.setAddress(cursor.getString(cursor.getColumnIndex("address")));
//                detailsF.setDistance(Float.parseFloat(cursor.getString(cursor.getColumnIndex("distance"))));
//                detailsF.setResponseStatus(cursor.getString(cursor.getColumnIndex("setResponseStatus")));
//                String isDiaog = (cursor.getString(cursor.getColumnIndex("isdiagnosed")));
//                if(isDiaog!=null && isDiaog.length()>0){
//                    if(isDiaog.equalsIgnoreCase("1")){
//                        detailsF.isDiagnossed = true;
//                    }else {
//                        detailsF.isDiagnossed = false;
//                    }
//                }else {
//                    detailsF.isDiagnossed = false;
//                }
//            } while (cursor.moveToNext());
//        }
//        return detailsF;
//    }
}
