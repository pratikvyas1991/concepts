package com.concepts.myconcepts.retrofit.utils;

import com.concepts.myconcepts.retrofit.model.DestinationList;
import com.concepts.myconcepts.retrofit.model.StudentData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by tasol on 9/5/18.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("webservice")
    Call<DestinationList> getAllDestinations(@Field("reqObject") String taskData);

    @FormUrlEncoded
    @POST("webservice")
    Call<StudentData> getStudentData(@Field("reqObject") String taskData);

    @GET
    @Streaming
    Call<ResponseBody> downloadPhoto(@Url String url);

    @GET("uploads/destination/1/1521436725_haveli-mansion-ahmedabad.jpg")
    @Streaming
    Call<ResponseBody> downloadFile();

    @Streaming
    @GET
    Call<ResponseBody> downloadFileByUrl(@Url String fileUrl);
}
