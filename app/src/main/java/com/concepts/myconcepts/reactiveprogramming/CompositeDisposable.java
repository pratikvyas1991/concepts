package com.concepts.myconcepts.reactiveprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.concepts.myconcepts.R;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by tasol on 28/4/18.
 *
 * Composite disposable can maintain list of subscriptions in a pool and can dispose them all at once
 * Usually we call compositeDisposable.clear() any where
 *
 */

public class CompositeDisposable extends Activity {
    private static final String TAG = "@#$@#$CompositeDisp:";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_act);
        //Observer
        DisposableObserver<String> animalObserver =getAnimalObserver();
    }

    private DisposableObserver<String> getAnimalObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.v(TAG," onNext : ");
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        };
    }
}
