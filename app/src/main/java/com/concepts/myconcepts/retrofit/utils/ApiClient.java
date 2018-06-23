package com.concepts.myconcepts.retrofit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tasol on 9/5/18.
 */

public class ApiClient {

    public static final String BASE_URL = "https://api.androidhive.info/json/";
//    public static final String BASE_URL = "http://devvb.ultimatefreehost.in/saloon/";
//    public static final String BASE_URL = "http://tasolglobal.com/dev/heritage_walk/public/";
//    public static final String BASE_URL = "https://github.com/";
//    public static final String BASE_URL = "http://192.168.5.155/schoolsystem/";


    private static Retrofit retrofit = null;

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
