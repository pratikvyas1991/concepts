package com.concepts.myconcepts.collections.set;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.TreeSet;

/**
 * Created by tasol on 4/7/18.
 */

public class FootballerSet {
    // In Order to use pojo as unique override hashcode and equals method as below
    // and the uniqueness is decided by the hashcode return value condition
    String name;
    String country;
    int goals;

    public FootballerSet() {
    }

    public FootballerSet(String name, String country, int goals) {
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
        // here we are comparing name any field can be compared ,generally a unique column is subjected to this
        FootballerSet set = (FootballerSet) obj;
        boolean retVal = false;
        if (name.compareTo(set.getName()) == 0) {
            // when two values are same compare to return 0 ,their respective difference otherwise
            retVal = true;
        } else {
            retVal = false;
        }
        return retVal;
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

    public static TreeSet<FootballerSet> getSortedNames(LinkedHashSet<FootballerSet> hashSet, String order) {
        // Method to get sorted Name based on the below classes SortedNameDESC and SortedNameASC
        TreeSet<FootballerSet> tSet;
        if (order.equalsIgnoreCase("desc")) {
            tSet = new TreeSet<>(new SortByNameDESC());
        } else {
            tSet = new TreeSet<>(new SortNameASC());
        }
        tSet.addAll(hashSet);
        return tSet;
    }

    public static TreeSet<FootballerSet> getSortedGoals(LinkedHashSet<FootballerSet> hashSet, String order) {
        TreeSet<FootballerSet> tSet;
        if (order.equalsIgnoreCase("desc")) {
            tSet = new TreeSet<>(new SortGoalDESC());
        } else {
            tSet = new TreeSet<>(new SortByGoalASC());
        }
        tSet.addAll(hashSet);
        return tSet;
    }


    public static class SortByNameDESC implements Comparator<FootballerSet> {

        @Override
        public int compare(FootballerSet o1, FootballerSet o2) {
            // Descending Order
            return o2.getName().compareTo(o1.getName());
        }
    }

    public static class SortNameASC implements Comparator<FootballerSet> {
        @Override
        public int compare(FootballerSet o1, FootballerSet o2) {
            //Ascending Order
            return o1.getName().compareTo(o2.getName());
        }
    }

    public static class SortByGoalASC implements Comparator<FootballerSet> {

        @Override
        public int compare(FootballerSet o1, FootballerSet o2) {
            //Ascending Order
            return o1.getGoals() - o2.getGoals();
        }
    }

    public static class SortGoalDESC implements Comparator<FootballerSet> {

        @Override
        public int compare(FootballerSet o1, FootballerSet o2) {
            //Descending Order
            return o2.getGoals() - o1.getGoals();
        }
    }

    public static <T> T getFirstElement(final Iterable<T> element) {
        // This will yield the first element of an Iterator
        return element.iterator().next();
    }
}
