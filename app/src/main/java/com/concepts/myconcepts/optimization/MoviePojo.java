package com.concepts.myconcepts.optimization;

/**
 * Created by tasol on 22/6/18.
 */

public class MoviePojo {
    String name;
    String timeStamp;
    String small;

    public MoviePojo(String name, String timeStamp, String small) {
        this.name = name;
        this.timeStamp = timeStamp;
        this.small = small;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
