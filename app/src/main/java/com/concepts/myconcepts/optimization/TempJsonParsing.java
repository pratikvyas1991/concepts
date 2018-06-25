package com.concepts.myconcepts.optimization;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.concepts.myconcepts.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tasol on 23/6/18.
 */

public class TempJsonParsing extends Activity {
    private String TAGTEMP = "%%%%TempParsing";
    HashMap<String,JSONArray> hashMap =new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        populateFixedPathfromJson();
    }

    private void populateFixedPathfromJson(){
        InputStream inputStream = getResources().openRawResource(R.raw.effect);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject object =new JSONObject(byteArrayOutputStream.toString());
            JSONArray data =null;
            JSONObject studentDat = null;
            JSONObject timeTable =null;
            JSONObject day =null;
            if(object!=null){
                data = object.getJSONArray("data");
                if(data!=null){
                    for (int i = 0; i < data.length(); i++) {
                        studentDat = data.getJSONObject(i);
                    }
                }
            }
            if(studentDat!=null){
                timeTable = studentDat.getJSONObject("timeTable");
                if(timeTable!=null){
                    for (int i = 0; i < timeTable.length(); i++) {
                        String flg = String.valueOf(i+1);
                        day = timeTable.getJSONObject(flg);
                        if(day.has("sub")){
                            JSONArray sub = day.getJSONArray("sub");
                            String dayKey = day.getString("dayName");
                            if(sub!=null){
                                hashMap.put(dayKey,sub);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        printHash();
    }

    void printHash(){
        if(hashMap!=null){
            for(Map.Entry<String,JSONArray> row : hashMap.entrySet()){
                String key = row.getKey();
                JSONArray val = row.getValue();
                if(val!=null){
                    for (int i = 0; i < val.length(); i++) {
                        try {
                            JSONObject obj = val.getJSONObject(i);
                            Log.v(TAGTEMP," Day : "+key+" Day "+obj.getString("subjectId")+" Start"+obj.getString("start"));
                        }catch (Exception je){
                            je.printStackTrace();
                        }

                    }
                }
            }
        }
    }
}
