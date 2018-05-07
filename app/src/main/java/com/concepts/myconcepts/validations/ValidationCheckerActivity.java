package com.concepts.myconcepts.validations;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.concepts.myconcepts.R;


/**
 * Created by tasol on 7/5/18.
 */

public class ValidationCheckerActivity extends Activity {
    EditText etMessage;
    TextView tvResult;
    Button btnPassword,btnEmail,btnMobile;
    ValidationUtils validationUtils;
    CharSequence message;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validation_checker_act);
        etMessage = (EditText)findViewById(R.id.etMessage);
        tvResult = (TextView)findViewById(R.id.tvResult);
        btnEmail = (Button)findViewById(R.id.btnEmail);
        btnPassword = (Button)findViewById(R.id.btnPassword);
        btnMobile= (Button)findViewById(R.id.btnMobile);

        validationUtils =new ValidationUtils(ValidationCheckerActivity.this);

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = etMessage.getText().toString();
                if(validationUtils.isValidEmail(message)){
                    tvResult.setText("Sucess");
                    tvResult.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                }else {
                    tvResult.setText("Failure");
                    tvResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }
        });

        btnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = etMessage.getText().toString();

                if(validationUtils.isPasswordLengthAllowed(message)){
                    if(validationUtils.isValidPassword(message)){
                        tvResult.setText("Sucess");
                        tvResult.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    }else {
                        tvResult.setText("Failure:atleast 1 (number,Capital,Small,Special char)");
                        tvResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                }else {
                    tvResult.setText("Failure:Length Between 4 -16");
                    tvResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }


            }
        });

        btnMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = etMessage.getText().toString();

                if(validationUtils.isMobileLengthAllowed(message)){
                    if(validationUtils.isValidMobile(message)){
                        tvResult.setText("Sucess:Valid Mobile Number");
                        tvResult.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                    }else {
                        tvResult.setText("Failure:Invalid Mobile Number");
                        tvResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                }else {
                    tvResult.setText("Failure:Length Must be 10");
                    tvResult.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }

            }
        });


    }
}
