package com.concepts.myconcepts.emailutils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import static com.google.android.gms.internal.zzahg.runOnUiThread;

/**
 * Created by tasol on 7/5/18.
 */

public class SendMail extends AsyncTask<String, Integer, Void> {
    Context mContext;
    String systemEmail = "tasol2018@gmail.com";
    String systemPass = "tasolglobal@2018";
    String toEmail="";
    String toSubject="";
    public SendMail(Context mContext,String toEmail,String toSubject) {
        this.mContext = mContext;
        this.toEmail = toEmail;
        this.toSubject = toSubject;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Mail m = new Mail(systemEmail,systemPass);
        //Array of the Reciever
        String[] toArr = { toEmail};
        m.setTo(toArr);
        m.setFrom(systemEmail);
        m.setSubject(toSubject);
        m.setBody("Hello There,This email was sent for test purpose");

        try {
            String msg="";
            if(m.send()) {
                msg="Email was sent successfully.";
            } else {
                msg="Email was not sent.";
            }

            final String finalMsg = msg;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext, finalMsg, Toast.LENGTH_LONG).show();
                }
            });
        } catch(Exception e) {
            Log.e("MailApp", "Could not send email", e);
        }
        return null;
    }
}
