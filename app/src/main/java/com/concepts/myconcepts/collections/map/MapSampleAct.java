package com.concepts.myconcepts.collections.map;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tasol on 4/7/18.
 * It is not part of collection so it has different characteristics when compared to collections
 * A map contains values on the basis of key i.e. key and value pair.
 * Each key and value pair is known as an entry.
 * Map contains only unique keys.
 * Map is useful if you have to search, update or delete elements on the basis of key.
 *
 * TreeMap :  1) This implementation uses a red-black tree as the underlying data structure
 *            2) A TreeMap is sorted according to the natural ordering of its keys, or by a Comparator provided at creation time.
 *            3) Here duplicate values are not allowed but It accepts the last value and removes the first value if key are same.
 */

public class MapSampleAct extends Activity {
    Button mBtnOptimize;
    Map<String,String> studentMap = new HashMap<>();
    Map<String,String> studentLinkedMap =new LinkedHashMap<>();
    private static final String TAGMAP = "%%%Map";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        mBtnOptimize = (Button)findViewById(R.id.mBtnOptimize);

        populateIT();

        mBtnOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAGMAP," Hashmap");

                for(Map.Entry<String,String> entry : studentMap.entrySet()){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    Log.v(TAGMAP," Key "+key+" Value : "+value);
                }

                Log.v(TAGMAP," Linked Hashmap");

                for(Map.Entry<String,String> entry: studentLinkedMap.entrySet()){
                    String key = entry.getKey();
                    String value  = entry.getValue();
                    Log.v(TAGMAP," Key "+key+" Value : "+value);
                }

                // Tree Map sort Based on the key so if you  have an key value pair and want a sorted order just do it like below
                TreeMap<String,String> treeMap = new TreeMap<>(studentLinkedMap);

                Log.v(TAGMAP," Tree Hashmap");

                for(Map.Entry<String,String> entry: treeMap.entrySet()){
                    String key = entry.getKey();
                    String value  = entry.getValue();
                    Log.v(TAGMAP," Key "+key+" Value : "+value);
                }
            }
        });
    }

    private void populateIT() {
        studentMap.put("1","Amit");
        studentMap.put("2","Bajrangi");
        studentMap.put("3","Chiranjeevi");

        studentLinkedMap.put("Adam","Adam");
        studentLinkedMap.put("Messi","Messi");
        studentLinkedMap.put("Bale","Bale");
        studentLinkedMap.put("Bale","Bhai");


    }
}
