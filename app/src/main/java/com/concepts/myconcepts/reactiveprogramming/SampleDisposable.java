package com.concepts.myconcepts.reactiveprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;


import com.concepts.myconcepts.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tasol on 28/4/18.
 * Disposable :  Disposable is used to disposed the subscription  when an Observer no longer want to listen to the observable
 *
 */

public class SampleDisposable extends Activity {
    public static final String TAG = "@#$@$#SampleDisposable";
    private Disposable disposable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_act);

        //Observer
        Observer<String> animalObserver  = getAnimalObserver();

        //Observable
        Observable<String> animalObservable  = getAnimalObservable();


        animalObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(animalObserver);
    }

    private Observable<String> getAnimalObservable() {
        return Observable.just("Ant","Tiger","Cow");
    }

    private Observer<String> getAnimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(TAG,"onSubscribed  ");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.v(TAG,"onNext "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.v(TAG,"onError   "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.v(TAG,"onComplete all items are emitted  ");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
