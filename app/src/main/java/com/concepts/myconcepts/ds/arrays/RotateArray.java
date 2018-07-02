package com.concepts.myconcepts.ds.arrays;

import android.util.Log;

/**
 * Created by tasol on 27/6/18.
 */

public class RotateArray {

    public int[] rotateMe(int[] arr, int order) {
        if (arr == null || order < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }

        for (int i = 0; i < order; i++) {
            for (int j = arr.length - 1; j > 0; j--) {
                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
        }
        return arr;
    }

}
