package com.concepts.myconcepts.batch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tasol on 11/5/18.
 */

public class HomeACtivity extends Activity {
    Button btnBatch,btnPrint;
    List<String> listStudents = new ArrayList<>();
    private static final String TAGBATCH="%%%%%%Batch:";
    private List<List<String>> myBatch =new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch);
        btnBatch = (Button)findViewById(R.id.btnBatch);
        btnPrint= (Button)findViewById(R.id.btnPrint);
        populateSutdents();

        btnBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBatch(2);
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listStudents!=null){
                    for (int i = 0; i < listStudents.size(); i++) {
                        Log.v(TAGBATCH," Name : "+listStudents.get(i));
                    }
                }
            }
        });
    }

    private void createBatch(int batchSize) {
        if(listStudents!=null){
           myBatch =  chopped(listStudents,batchSize);
        }

        if(myBatch!=null && myBatch.size()>0){
            for (int i = 0; i < myBatch.size(); i++) {
                Log.v(TAGBATCH," Batch No : "+(i+1));
                List<String> row = myBatch.get(i);
                if(row!=null && row.size()>0){
                    for (int j = 0; j < row.size(); j++) {
                        Log.v(TAGBATCH," Name : "+row.get(j));
                    }
                }
            }
        }
        Log.v(TAGBATCH," Batch Size : "+myBatch.size());
    }

    private void populateSutdents() {
        listStudents.add("StdA");
        listStudents.add("StdB");
        listStudents.add("StdC");
        listStudents.add("StdD");
        listStudents.add("StdE");
        listStudents.add("StdF");
        listStudents.add("StdG");
        listStudents.add("StdH");
        listStudents.add("StdI");
        listStudents.add("StdJ");
    }

    static <T> List<List<T>> chopped(List<T> list, final int L) {
        List<List<T>> parts = new ArrayList<List<T>>();
        final int N = list.size();
        for (int i = 0; i < N; i += L) {
            parts.add(new ArrayList<T>(
                    list.subList(i, Math.min(N, i + L)))
            );
        }
        return parts;
    }

}
