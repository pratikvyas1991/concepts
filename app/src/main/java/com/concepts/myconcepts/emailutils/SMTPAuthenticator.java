package com.concepts.myconcepts.emailutils;

import android.content.Context;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by tasol on 7/5/18.
 */

public class SMTPAuthenticator extends Authenticator {
    String username;
    String password;
    public SMTPAuthenticator(String un,String pass) {
        username = un;
        password = pass;
    }
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
//        String username = "tasol2018@gmail.com";
//        String password = "tasolglobal@2018";
        if ((username != null) && (username.length() > 0) && (password != null)
                && (password.length() > 0)) {

            return new PasswordAuthentication(username, password);
        }

        return null;
    }
}
