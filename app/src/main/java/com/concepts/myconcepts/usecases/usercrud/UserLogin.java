package com.concepts.myconcepts.usecases.usercrud;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.databaseutils.UserDatabaseHandler;
import com.concepts.myconcepts.validations.ValidationUtils;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by tasol on 7/5/18.
 */

public class UserLogin extends Activity {
    Button btnLogin;
    EditText etEmail,etPassword;
    ValidationUtils validationUtils;
    UserDatabaseHandler userDatabaseHandler;
    CharSequence email,password;
    String encPassCode = "password";
    String encryptedMsg;
    String decryptMsg;
    private String TAGLOGIN = "@#$@#$UserLogin";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_crud_login_act);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etEmail = (EditText)findViewById(R.id.etEmail);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        validationUtils =new ValidationUtils(UserLogin.this);
        userDatabaseHandler =new UserDatabaseHandler(UserLogin.this);

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                email = s.toString();
                if(validationUtils.isValidEmail(email)){
                }else {
                    etEmail.setError("Invalid Email");
                }
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidations();
            }
        });

    }

    private void checkValidations() {
        boolean isAllowed  = false;
        password = etPassword.getText().toString();
        encryptedMsg = userDatabaseHandler.checkUserExist(etEmail.getText().toString());
        try {
            decryptMsg = AESCrypt.decrypt(encPassCode, encryptedMsg);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        if(userDatabaseHandler.checkEmailExist(email.toString())){
            etEmail.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            isAllowed = true;
        }else {
            etEmail.setError("Email Not Exist");
            isAllowed = false;
        }

        if(validationUtils.isPasswordLengthAllowed(password)){
            if(validationUtils.isValidPassword(password)){
                etPassword.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                isAllowed = true;
            }else {
                etPassword.setError("Failure:atleast 1 (number,Capital,Small,Special char)");
                isAllowed = false;
            }
        }else {
            etPassword.setError("Failure:Length Between 4 -16");
            isAllowed = false;
        }

        if(decryptMsg.equalsIgnoreCase(etPassword.getText().toString())){
            isAllowed = true;
        }else {
            isAllowed = false;
        }


        if(isAllowed){
            User user = userDatabaseHandler.getUSerDetail(etEmail.getText().toString());
            Log.v(TAGLOGIN," ID : "+user.getUserID()+" Name : "+user.getName());
            Toast.makeText(UserLogin.this," Sucess!!",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(UserLogin.this," Failure!!",Toast.LENGTH_SHORT).show();
        }
    }
}
