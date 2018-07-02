package com.concepts.myconcepts.databaseutils;

import android.content.Context;

import com.concepts.myconcepts.usecases.usercrud.User;

/**
 * Created by tasol on 27/6/18.
 */

public class DbSingleton {
    private static Context mContext;
    private static UserDatabaseHandler userDatabaseHandler;
    private static DbSingleton dbSingleton;

    public static DbSingleton getInstance(Context context) {
        mContext = context;
        if (dbSingleton == null) {
            // We have used synchronized to make It thread safe
            synchronized (DbSingleton.class) {
                dbSingleton = new DbSingleton();
                if (userDatabaseHandler == null) {
                    userDatabaseHandler = new UserDatabaseHandler(mContext);
                }
            }
        }
        return dbSingleton;
    }

    public void addUsers(String userID, String email, String name, String encryptedMsg) {
        userDatabaseHandler.addUsers(userID, email, name, encryptedMsg);
    }

    public boolean checkUserExist(String email) {
        return userDatabaseHandler.checkEmailExist(email);
    }

    public User getUserDetail(String email) {
        return userDatabaseHandler.getUSerDetail(email);
    }

}
