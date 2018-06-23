package com.concepts.myconcepts.service;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by tasol on 16/5/18.
 */

public class MySimpleService extends Service {
    private static final String TAGSERVICE = "%%%%MySimpleService";
    int count= 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAGSERVICE," Service Started !!!");

        CountDownTimer countDownTimer =new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAGSERVICE," Millis "+String.valueOf(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Log.v(TAGSERVICE," Finished ");
                stopSelf();
            }
        }.start();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v(TAGSERVICE," Service DEstroyed !!!");
    }
}
