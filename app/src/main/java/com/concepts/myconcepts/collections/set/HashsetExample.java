package com.concepts.myconcepts.collections.set;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.collections.FootballerSimple;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tasol on 4/7/18.
 *
 * Collection(Interface) : Root interface with basic methods like add(), remove(),
     contains(), isEmpty(), addAll(), ... etc.

 * Set(Interface) : Doesn't allow duplicates. Example implementations of Set
 interface are HashSet (Hashing based).

 Hashset (Class) : Java HashSet class is used to create a collection that uses a hash table for storage.
 It inherits the AbstractSet class and implements Set interface.
 HashSet stores the elements by using a mechanism called hashing.
 HashSet contains unique elements only.

 */

public class HashsetExample extends Activity {
    Button mBtnOptimize;
    private String TAGHASH = "%%%%Hashset";

    HashSet<String> set =new HashSet<>();
    HashSet<FootballerSimple> footBllSet =new HashSet<FootballerSimple>();

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

                for(FootballerSimple row : footBllSet){
                    Log.v(TAGHASH,"Set Name : "+row.getName());
                }

                //Second Method
//                Iterator<String> it = set.iterator();
//                if(set.contains("Ramos"))
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

        footBllSet.add(new FootballerSimple("Messi","ARG",50));
        footBllSet.add(new FootballerSimple("Neymar","BRZ",53));
        footBllSet.add(new FootballerSimple("Ronaldo","POR",51));
        footBllSet.add(new FootballerSimple("Messi","ARG",50));
        footBllSet.add(new FootballerSimple("Sergio Ramos","ESP",54));
    }
}
