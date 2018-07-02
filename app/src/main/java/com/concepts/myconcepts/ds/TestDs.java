package com.concepts.myconcepts.ds;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.ds.arrays.ReverseArrayString;
import com.concepts.myconcepts.ds.arrays.ReverseArraySum;

/**
 * Created by tasol on 27/6/18.
 */

public class TestDs extends Activity {
    Button mBtnSimple;
    private static String TAGDS = "%%%%DS : ";
    ReverseArrayString reverseArrayString;
    ReverseArraySum arraySum;
    String[] arr = {"1","2","3","4","5","6","7"};

    String[] tempArr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds);
        mBtnSimple = (Button)findViewById(R.id.mBtnSimple);

        reverseArrayString =new ReverseArrayString();

        arraySum =new ReverseArraySum();

        mBtnSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tempArr = reverseArrayString.reverseArray(arr);
//                for (int i = 0; i < tempArr.length; i++) {
////                    Log.v(TAGDS," Arr Length "+tempArr[i]);
//                }
                arraySum.isArraySumExist(arr,10);
            }
        });
    }

}
