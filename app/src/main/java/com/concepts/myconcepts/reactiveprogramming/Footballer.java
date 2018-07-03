package com.concepts.myconcepts.reactiveprogramming;

/**
 * Created by tasol on 3/7/18.
 */

public class Footballer {
    String id;
    String name;
    String country;

    public Footballer() {
    }

    public Footballer(String id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
