package com.concepts.myconcepts.validations;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tasol on 7/5/18.
 */

public class ValidationUtils {
    Context context;

    public ValidationUtils(Context context) {
        this.context = context;
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean isValidPassword(final CharSequence password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean isPasswordLengthAllowed(final CharSequence password) {
        boolean result = false;
        int pass = password.length();
        if (pass < 4 || pass > 16) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    public boolean isValidMobile(CharSequence phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    public boolean isMobileLengthAllowed(final CharSequence password) {
        boolean result = false;
        int pass = password.length();
        if (pass !=10) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

}
