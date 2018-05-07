package com.concepts.myconcepts.encryption;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.concepts.myconcepts.R;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

/**
 * Created by tasol on 7/5/18.
 * Here we have used the library to encrypt and decrypt the string value
 * Url :https://github.com/scottyab/AESCrypt-Android
 */

public class EncrypttionActivity extends Activity {
    TextView tvEncryptDecrypt;
    EditText etMessage;
    Button btnEncrypt,btnDecrypt;
    String message;
    String password = "password";
    String encryptedMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encrypt_act);

        etMessage = (EditText) findViewById(R.id.etMessage);
        tvEncryptDecrypt = (TextView)findViewById(R.id.tvEncryptDecrypt);

        btnEncrypt = (Button)findViewById(R.id.btnEncrypt);
        btnDecrypt = (Button)findViewById(R.id.btnDecrypt);



        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    message = etMessage.getText().toString();
                    encryptedMsg = AESCrypt.encrypt(password, message);
                    tvEncryptDecrypt.setText(encryptedMsg);
                    etMessage.setText("");
                    Toast.makeText(EncrypttionActivity.this,"Click Decrypt for Decryption ",Toast.LENGTH_SHORT).show();

                }catch (GeneralSecurityException e){
                    //handle error
                }
            }
        });

        btnDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String messageAfterDecrypt = AESCrypt.decrypt(password, encryptedMsg);
                    tvEncryptDecrypt.setText(messageAfterDecrypt);
                    Toast.makeText(EncrypttionActivity.this,"Decryption Ready",Toast.LENGTH_SHORT).show();
                }catch (GeneralSecurityException e){
                    //handle error - could be due to incorrect password or tampered encryptedMsg
                }
            }
        });
    }
}
