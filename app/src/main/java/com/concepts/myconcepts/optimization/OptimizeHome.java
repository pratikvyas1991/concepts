package com.concepts.myconcepts.optimization;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.concepts.myconcepts.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tasol on 22/6/18.
 */

public class OptimizeHome extends Activity {
    Button mBtnOptimize, mBtnSimple;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimize);
        mBtnSimple = (Button) findViewById(R.id.mBtnSimple);
        mBtnOptimize = (Button) findViewById(R.id.mBtnOptimize);

        mBtnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("@@@OPT: Simple", " method called : ");
                simpleIntMapExample();
            }
        });

        mBtnOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < optimizedStrMapExample().size(); i++) {
                    builder.setLength(0);
                    builder.append(optimizedStrMapExample().get(i).toString());
                    Log.v("@@@OPT: Optimized", " method called val "+builder.toString());
                }
            }
        });
    }

    private String simpleStringExample() {
        //Method uses Simple string to append the world in string
        String string = "Hello";
        for (int i = 0; i < 10000; i++) {
            string+=" world";
        }
        return string;
    }

    private StringBuilder optimizeStringExample(){
        //Method uses Optimized String builder  to append the world in string
        // the performance is improved as string is immutable and String builder is mutable it make much difference
        StringBuilder builder =new StringBuilder("Hello");
        for (int i = 0; i < 10000; i++) {
            builder.append(" world");
        }
        return  builder;
    }

    private void simpleIntMapExample(){
        //This method uses the collection map
        Map<Integer,Integer> map =new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            map.put(i,i);
        }
    }

    private void optimizedIntMapExample(){
        //This method uses the collection called SparseIntArray
        SparseIntArray sparseIntArray =new SparseIntArray();
        for (int i = 0; i < 10000; i++) {
            sparseIntArray.put(i,i);
        }
    }

    private SparseArray<String> optimizedStrMapExample(){
        SparseArray<String> sparseArray =new SparseArray<>();
        sparseArray.put(0,"antman");
        sparseArray.put(1,"captianAmerica");
        sparseArray.put(2,"Thor");
        sparseArray.put(3,"Ironman");
        return sparseArray;
    }

}
