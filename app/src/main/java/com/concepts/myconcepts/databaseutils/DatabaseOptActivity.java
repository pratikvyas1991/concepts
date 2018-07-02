package com.concepts.myconcepts.databaseutils;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.usecases.usercrud.User;

/**
 * Created by tasol on 27/6/18.
 */

public class DatabaseOptActivity extends Activity {
    Button mBtnGetData;
    DbSingleton dbSingleton;
    String userID,email,name,encryptedMsg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databaseopt);
        mBtnGetData = (Button)findViewById(R.id.mBtnGetData);
        dbSingleton = DbSingleton.getInstance(DatabaseOptActivity.this);

        userID = "1";
        email = "abc@gmail.com";
        name = "abc";
        encryptedMsg = "def";


        if(!dbSingleton.checkUserExist(email)){
            dbSingleton.addUsers(userID,email,name,encryptedMsg);
            Toast.makeText(DatabaseOptActivity.this,"Added Sucessfully ",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(DatabaseOptActivity.this,"Already Exist",Toast.LENGTH_SHORT).show();
        }

        mBtnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbSingleton.checkUserExist(email)){
                    User user = dbSingleton.getUserDetail(email);
                    Log.v("%%%%"," Name : "+user.getName());
                }else {
                    Toast.makeText(DatabaseOptActivity.this,"User Not Exist",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
