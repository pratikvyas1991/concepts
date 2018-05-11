package com.concepts.myconcepts.usecases.usercrud;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.concepts.myconcepts.R;
import com.concepts.myconcepts.databaseutils.UserDatabaseHandler;
import com.concepts.myconcepts.emailutils.SendMail;
import com.concepts.myconcepts.validations.ValidationUtils;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by tasol on 7/5/18.
 */

public class UserRegistration extends Activity {
    Button btnRegister;
    EditText etEmail,etName,etPassword,etConfirmPassword;
    CharSequence email,password;
    ValidationUtils validationUtils;
    UserDatabaseHandler userDatabaseHandler;

    String encPassCode = "password";
    String encryptedMsg;
    private String TAGUSER = "@#$@#$User";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_crud_register_act);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        etConfirmPassword = (EditText)findViewById(R.id.etConfirmPassword);
        etPassword = (EditText)findViewById(R.id.etPassword);
        etName = (EditText)findViewById(R.id.etName);
        etEmail = (EditText)findViewById(R.id.etEmail);
        validationUtils =new ValidationUtils(UserRegistration.this);
        userDatabaseHandler =new UserDatabaseHandler(UserRegistration.this);


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
                    if(userDatabaseHandler.checkEmailExist(email.toString())){
                        etEmail.setError("Email Already Exist");
                        etEmail.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }else {
                        etEmail.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    }
                }else {
                    etEmail.setError("Invalid Email");
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTo(etEmail.getText().toString());
//                checkValidations();
            }
        });


    }

    private void checkValidations() {
        boolean isAllowed  = false;
        password = etPassword.getText().toString();

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



        if(password.equals(etConfirmPassword.getText().toString())){
            etConfirmPassword.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            isAllowed = true;
        }else {
            etConfirmPassword.setError("Failure:Confirmpassword should be same as passwrod");
            isAllowed = false;
        }

        if(!TextUtils.isEmpty(etName.getText().toString())){
            etName.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
            isAllowed = true;
        }else {
            etName.setError("Failure:Cannot be left Empty");
            isAllowed = false;
        }


        if(isAllowed){
            try {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                encryptedMsg = AESCrypt.encrypt(encPassCode, password.toString());
                int userID = userDatabaseHandler.countAllRows("users")+1;
                userDatabaseHandler.addUsers(String.valueOf(userID),email,name,encryptedMsg);
//                sendTo(email);
                Toast.makeText(UserRegistration.this," User Added Sucessfully",Toast.LENGTH_SHORT).show();
                Log.v(TAGUSER,"ID: "+userID+" Name : "+name+" Email : "+email+" Pass : "+encryptedMsg);
                Intent intent =new Intent(UserRegistration.this,UserSplash.class);
                startActivity(intent);
                finish();

            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            Toast.makeText(UserRegistration.this," Sucess!!",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(UserRegistration.this," Failure!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendTo(String email) {
        SendMail mail =new SendMail(UserRegistration.this,email,"Test Email ");
        mail.execute("");
    }
}
