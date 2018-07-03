package com.concepts.myconcepts.reactiveprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tasol on 3/7/18.
 */

public class SingleObservable extends Activity {
    Button mBtnOptimize, mBtnSimple;
    private static final String TAGSINGLE = "%%%%SingleObservable";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);
        mBtnOptimize = (Button) findViewById(R.id.mBtnOptimize);
        mBtnSimple = (Button) findViewById(R.id.mBtnSimple);
        mBtnOptimize.setVisibility(View.GONE);

        mBtnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRx();
            }
        });
    }

    private void startRx(){
        Log.v(TAGSINGLE," startRx Called ");
        getObservable().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(getObserver());
    }

    private Observable<String> getObservable(){
        return Observable.just("Germany","Argentina","Portugal","Spain");
    }

    private Observer<String> getObserver(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(TAGSINGLE," onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.v(TAGSINGLE," onNext : "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.v(TAGSINGLE," onError : ");
            }

            @Override
            public void onComplete() {
                Log.v(TAGSINGLE," onComplete : ");
            }
        };
    }
}
