package com.concepts.myconcepts.collections.set;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Created by tasol on 4/7/18.
 *
 * Collection(Interface) : Root interface with basic methods like add(), remove(),
     contains(), isEmpty(), addAll(), ... etc.

 * Set(Interface) : Doesn't allow duplicates. Example implementations of Set
 interface are HashSet (Hashing based).

 LinkedHashset (Class) : Java LinkedHashSet class is a Hash table and Linked list implementation
 of the set interface.
 It inherits HashSet class and implements Set interface

 1) Contains unique elements only like HashSet.
 2) Provides all optional set operations, and permits null elements.
 3) Maintains insertion order.(Compared to Hashset)

 */

public class LinkedHashsetExample extends Activity {
    Button mBtnOptimize;
    private String TAGHASH = "%%%%Hashset";

    HashSet<String> set =new HashSet<>();
    LinkedHashSet<String> linkedSet =new LinkedHashSet<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        mBtnOptimize = (Button)findViewById(R.id.mBtnOptimize);


        poplateIt();

        mBtnOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First Method
                for(String str : set){
                    Log.v(TAGHASH," Name : "+str);
                }
                for(String str : linkedSet){
                    Log.v(TAGHASH," LS Name : "+str);
                }

                //Second Method
//                Iterator<String> it = set.iterator();
//                while (it.hasNext()){
//                    Log.v(TAGHASH," Name : "+it.next());
//                }

            }
        });
    }

    private void poplateIt() {
        set.add("Messi");
        set.add("Neymar");
        set.add("Neymar");
        set.add("Ronaldo");

        linkedSet.add("Messi");
        linkedSet.add("Neymar");
        linkedSet.add("Neymar");
        linkedSet.add("Ronaldo");


    }
}
