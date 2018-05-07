package com.concepts.myconcepts.usecases.usercrud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.concepts.myconcepts.R;

/**
 * Created by tasol on 7/5/18.
 */

public class UserSplash extends Activity {
    Button btnLogin,btnRegistration;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_crud_splash_act);
        btnRegistration = (Button)findViewById(R.id.btnRegistration);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserSplash.this,UserRegistration.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(UserSplash.this,UserLogin.class);
                startActivity(intent);
            }
        });
    }
}
