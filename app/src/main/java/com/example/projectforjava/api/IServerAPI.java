package com.example.projectforjava.api;

import com.example.projectforjava.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IServerAPI {

    @POST("/register")
    Call<String> registerUser(@Body User user);

    @GET("/login")
    Call<Boolean> loginUser(@Body User user);

    @DELETE("/delete")
    Call<Boolean> deleteUser(@Body User user);
}
