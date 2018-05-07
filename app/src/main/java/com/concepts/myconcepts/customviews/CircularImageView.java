package com.concepts.myconcepts.customviews;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.concepts.myconcepts.R;


/**
 * Created by tasol on 28/4/18.
 */

public class CircularImageView extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circular_image_view);
    }
}
