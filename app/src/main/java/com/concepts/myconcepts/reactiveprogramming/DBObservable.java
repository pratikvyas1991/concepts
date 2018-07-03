package com.concepts.myconcepts.reactiveprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tasol on 3/7/18.
 */

public class DBObservable extends Activity {
    Button mBtnOptimize, mBtnSimple;
    private static final String TAGSINGLE = "%%%%SingleObservable";
    RxDbHelper rxDbHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);
        mBtnOptimize = (Button) findViewById(R.id.mBtnOptimize);
        mBtnSimple = (Button) findViewById(R.id.mBtnSimple);


        rxDbHelper =new RxDbHelper(DBObservable.this);


//        rxDbHelper.addPlayer("1","Messi","Argentina");
//        rxDbHelper.addPlayer("2","Neymar Jr","Brazil");
//        rxDbHelper.addPlayer("3","Ronaldo","Portugal");
//        rxDbHelper.addPlayer("4","Muller","Germany");
//        rxDbHelper.addPlayer("5","Ramos","Spain");
//        rxDbHelper.addPlayer("6","Pogba","France");


        mBtnOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRx2();
            }
        });
    }




    private void startRx2(){
//        .filter(new Predicate<List<Footballer>>() {
//            @Override
//            public boolean test(List<Footballer> footballers) throws Exception {
//                List<Footballer> list =new ArrayList<>();
//                for(Footballer row : footballers){
//                    if(row.getName().contains("M")){
//                        return true;
//                    }else {
//                        return false;
//                    }
//                }
//                return false;
//            }
//        })
        Log.v(TAGSINGLE," startRxOriginal Called ");
        try {
            rxDbHelper.getRxFootballer()
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(getObserverFootballer());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private Observer<List<Footballer>> getObserverFootballer(){
        return new Observer<List<Footballer>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(TAGSINGLE," onSubscribed");
            }

            @Override
            public void onNext(List<Footballer> footballers) {
                for(Footballer footballer:footballers){
//                    Log.v(TAGSINGLE," onNext");
                    Log.v(TAGSINGLE," Name  : "+footballer.getName()+" Country  : "+footballer.getCountry());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.v(TAGSINGLE," onError");
            }

            @Override
            public void onComplete() {
                Log.v(TAGSINGLE," onComplete");
            }
        };
    }
}
