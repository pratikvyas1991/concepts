package com.concepts.myconcepts.reactiveprogramming;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;

import io.reactivex.Observable;


/**
 * Created by tasol on 3/7/18.
 */

public class MathObservable extends Activity {
    Button mBtnOptimize;
    private static final String TAGSINGLE = "%%%%SingleObservable";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        mBtnOptimize = (Button) findViewById(R.id.mBtnOptimize);

        mBtnOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    private Observable getObservable(){
        return Observable.just(1,2,3,4,5);
    }

}
