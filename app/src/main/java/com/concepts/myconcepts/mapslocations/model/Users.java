package com.concepts.myconcepts.mapslocations.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.io.Serializable;

/**
 * Created by tasol on 23/3/18.
 */

public class Users implements Serializable,ClusterItem {
    String userID;
    String userName;
    double latitude;
    double longitude;


    public Users() {
    }

    public Users(String userID, String userName, double latitude, double longitude) {
        this.userID = userID;
        this.userName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public LatLng getPosition() {
        return new LatLng(latitude,longitude);
    }

    @Override
    public String getTitle() {
        return userName;
    }

    @Override
    public String getSnippet() {
        return userName;
    }

}
