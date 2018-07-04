package com.concepts.myconcepts.collections;

/**
 * Created by tasol on 4/7/18.
 */

public class FootballerSimple {
    // In Order to use pojo as unique override hashcode and equals method as below
    // and the uniqueness is decided by the hashcode return value condition
    String name;
    String country;
    int goals;

    public FootballerSimple() {
    }

    public FootballerSimple(String name, String country, int goals) {
        this.name = name;
        this.country = country;
        this.goals = goals;
    }

    @Override
    public int hashCode() {
        return name.length();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass()!=obj.getClass()){
            return false;
        }
        return true;
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

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }
}
