package com.concepts.myconcepts.ds.arrays;

import android.util.Log;

/**
 * Created by tasol on 28/6/18.
 */

public class ReverseArrayString {
    public String[] reverseArray(String[] input){
        String[] outPut = new String[input.length];
        Log.v("%%%"," "+input.length);
        for (int i = input.length-1; i > 0; i--) {
            int temp = input.length-i;
            Log.v("%%%","Temp "+temp);
            Log.v("%%%","i "+i);

//            outPut[input.length-i] = input[i];
        }
        return outPut;
    }
}
