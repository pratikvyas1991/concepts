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
 * This is the basic demo for RXJava
 * <p>
 * Observable : The entity that emits the data
 * <p>
 * Observer :  The entity that receives the data emitted by the observer
 * <p>
 * Note : There can be multiple observer that can be assigned to single observer
 * <p>
 * Subscription : The bonding between observer and obsevable is called the subscription
 * <p>
 * Operator/Transformation : Operators modifies the data emitted by observable before received by observer
 * <p>
 * Schedulers :   Schedulers decides the thread where the observable emits the data and also the thread on which the observer recieves the data i.e background thread ,main thread etc
 */

public class SampleBasicIntro extends Activity {
    private static final String TAG = "@#$@#$SampleActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_act);


        //Observer
        Observer<String> animalObserver = getAnimalObserver();

        //Observable
        Observable<String> animalObservable = getAnimalObservable();

        //Observer subscribing to observable
        animalObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(animalObserver);


    }

    private Observable<String> getAnimalObservable() {
        return Observable.just("Tiger", "Cow", "Horse");
    }

    private Observer<String> getAnimalObserver() {

        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(TAG, " onSubscribed");
            }

            @Override
            public void onNext(String s) {
                Log.v(TAG, " onNext : " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.v(TAG, " onError " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.v(TAG, " onComplete All Items Are emitted");
            }
        };
    }
}
