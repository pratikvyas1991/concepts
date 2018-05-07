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
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by tasol on 28/4/18.
 * The Operator is used to operate on the data before data is received by the observers
 * We use filter operator .it filter the data using conditional operation "in this case"
 */

public class SampleOperator extends Activity {
    private static final String TAG="@#$@#$SampleOperator";
    private Disposable disposable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_act);

        //Observer

        Observer<String> animalObserver  = getAnimalObserver();

        //Observable

        Observable<String> animalObservable = getAnimalObservable();

        //subscription

        animalObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.toLowerCase().startsWith("d");
                    }
                })
                .subscribeWith(animalObserver);

    }

    private Observable<String> getAnimalObservable() {
        return Observable.just("Ant","Bat","Dear","Donkey","Tiger","Cheetah");
    }

    private Observer<String> getAnimalObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.v(TAG," OnSubscribed : ");
                disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.v(TAG," OnNext : "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.v(TAG," OnError : "+e);
            }

            @Override
            public void onComplete() {
                Log.v(TAG," OnComplete : All items are emitted ");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
