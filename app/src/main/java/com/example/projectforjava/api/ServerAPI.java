package com.example.projectforjava.api;

import com.example.projectforjava.models.User;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerAPI {
    private static final String BASE_URL = "http://192.168.137.218:8080"; // Замените на ваш URL
    private static ServerAPI instance;
    private OkHttpClient client;
    private Retrofit retrofit;
    private IServerAPI serverAPI;

    public ServerAPI(){
        client = new OkHttpClient.Builder().build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        serverAPI = retrofit.create(IServerAPI.class);
    }

    public static ServerAPI getInstance() {
        if (instance == null) {
            instance = new ServerAPI();
        }
        return instance;
    }

    public IServerAPI getServerAPI() {
        return serverAPI;
    }

    public void registerUser(User user, Callback<String> callback) {
        Call<String> call = serverAPI.registerUser(user);
        call.enqueue(callback);
    }

    public void loginUser(User user, Callback<Boolean> callback) {
        Call<Boolean> call = serverAPI.loginUser(user);
        call.enqueue(callback);
    }

    public void deleteUser(User user, Callback<Boolean> callback) {
        Call<Boolean> call = serverAPI.deleteUser(user);
        call.enqueue(callback);
    }
}
