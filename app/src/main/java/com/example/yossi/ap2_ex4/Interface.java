package com.example.yossi.ap2_ex4;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface Interface {

    @FormUrlEncoded
    @POST("/NewUser")
    void postNewUser(@Field("username") String username,
                     @Field("password") String password,
                     @Field("name") String name,
                     @Field("email") String email,
                     @Field("icon") String icon,
                     Callback<ResultResponse> serverResponseCallback);


    //This method is used for "POST"
    @FormUrlEncoded
    @POST("/login")
    void postData(@Field("method") String method,
                  @Field("username") String username,
                  @Field("password") String password,
                  Callback<ResultResponse> serverResponseCallback);


    //This method is used for "GET"
    @GET("/")
    void getData(@Query("method") String method,
                 @Query("username") String username,
                 @Query("password") String password,
                 Callback<ServerResponse> serverResponseCallback);
}