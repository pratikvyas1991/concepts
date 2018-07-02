package com.concepts.myconcepts.ds.arrays;

import android.nfc.Tag;
import android.util.Log;

/**
 * Created by tasol on 2/7/18.
 * Defination  :
 * Given an array that is sorted and then rotated around an unknown point.
 * Find if array has a pair with given sum ‘x’.
 * It may be assumed that all elements in array are distinct.
 */

public class ReverseArraySum {
    public boolean isArraySumExist(String[] input, int checksum){
        boolean isSumExist = false;
        int sum = checksum;
        for (int i = 0; i < input.length; i++) {
            for (int j = i+1; j < input.length; j++) {
                if(input[j]!=input[i]){
//                    Log.v("%%%SUM : ","Pairs : ("+input[i]+","+input[j]+") ");
                    int summ = Integer.parseInt(input[j])+Integer.parseInt(input[i]);
                    if(summ == checksum){
                        Log.v("%%%SUM : ","SUM : "+summ+" Pair ("+input[i]+","+input[j]+") ");
                    }
                }
            }
        }
        return isSumExist;
    }
}
