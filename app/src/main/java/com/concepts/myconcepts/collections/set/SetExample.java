package com.concepts.myconcepts.collections.set;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;

import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * Created by tasol on 4/7/18.
 */

public class SetExample extends Activity {
    Button mBtnOptimize;
    LinkedHashSet<FootballerSet> lSet =new LinkedHashSet<>();
    private static final String TAGSET = "%%%SET";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);
        mBtnOptimize = (Button)findViewById(R.id.mBtnOptimize);

        populateIT();

        mBtnOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(TAGSET,"Before  : ");

                for(FootballerSet set : lSet){
                    Log.v(TAGSET,"LinkedHashSet Name  : "+set.getName()+" Goals "+set.getGoals());
                }

                Log.v(TAGSET,"After  Name : ");

                TreeSet<FootballerSet> tSet = FootballerSet.getSortedNames(lSet,"asc");

                for(FootballerSet set : tSet){
                    Log.v(TAGSET,"LinkedHashSet Name  : "+set.getName());
                }

                FootballerSet row = FootballerSet.getFirstElement(tSet);


                Log.v(TAGSET," First Element : "+row.getName());
            }
        });
    }

    private void populateIT() {
        lSet.add(new FootballerSet("Messi","ARG",50));
        lSet.add(new FootballerSet("Zaltan","SWE",12));
        lSet.add(new FootballerSet("Neymar","BRZ",51));
        lSet.add(new FootballerSet("Messi","ARGR",51));
        lSet.add(new FootballerSet("Messi","ARG",50));
    }
}
