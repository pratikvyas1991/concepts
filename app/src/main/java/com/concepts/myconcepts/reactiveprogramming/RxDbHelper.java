package com.concepts.myconcepts.reactiveprogramming;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tasol on 3/7/18.
 */

public class RxDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "conceptDB";
    private static final int DATABASE_VERSION = 1;
    String TAGRXDB = "@@@RxHelper";
    Context context;

    public RxDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String users = "CREATE TABLE IF NOT EXISTS footballer (id TEXT PRIMARY KEY,name TEXT,country TEXT)";
        db.execSQL(users);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPlayer(String id,String name,String country){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("name", name);
        contentValues.put("country", country);
        long flag = database.insert("footballer", null, contentValues);
        Log.v(TAGRXDB, "player Record Added val : "+flag);
        database.close();
    }

    Callable<List<Footballer>> getPlayers(){
        return new Callable<List<Footballer>>() {
            @Override
            public List<Footballer> call() throws Exception {
                List<Footballer> list =new ArrayList<>();
                String qry = "SELECT * FROM footballer";
                SQLiteDatabase db = getWritableDatabase();
                Cursor cursor = db.rawQuery(qry,null);
                cursor.moveToFirst();
                if(cursor.moveToFirst()){
                    do{
                        Footballer footballer =new Footballer();
                        footballer.setId(cursor.getString(cursor.getColumnIndex("id")));
                        footballer.setName(cursor.getString(cursor.getColumnIndex("name")));
                        footballer.setCountry(cursor.getString(cursor.getColumnIndex("country")));
                        list.add(footballer);
                    }while (cursor.moveToNext());
                }
                return list;
            }
        };
    }


    private static <T> Observable<T> makeObservable(final Callable<T> func){
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                emitter.onNext(func.call());
            }
        });
    }

    public Observable<List<Footballer>> getRxFootballer(){
        return makeObservable(getPlayers()).subscribeOn(Schedulers.computation())
                .map(new Function<List<Footballer>, List<Footballer>>() {
                    @Override
                    public List<Footballer> apply(List<Footballer> footballers) throws Exception {
                        Collections.sort(footballers,new FootballerComparator());
                        return footballers;
                    }
                });
    }

    public  class FootballerComparator implements Comparator<Footballer> {
        @Override
        public int compare(Footballer participant1, Footballer participant2) {
            //Ascending Order
//            int result = participant1.getName().compareTo(participant2.getName());
            //Descending Order
            String str2 = participant2.getName().toUpperCase();
            String str1 = participant1.getName().toUpperCase();
            int result = str2.compareTo(str1);
            return result;
        }
    }
}
