package com.concepts.myconcepts.usecases.usercrud;

/**
 * Created by tasol on 7/5/18.
 */

public class User {
    String userID;
    String userEmail;
    String name;
    String password;

    public User() {
    }

    public User(String userID, String userEmail, String name, String password) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.name = name;
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
