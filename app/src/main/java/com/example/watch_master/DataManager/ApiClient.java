package com.example.watch_master.DataManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://api.tvmaze.com/";
    private static final String BASE_URL2 = "https://api.themoviedb.org/";
    private static Retrofit retrofitTv;
    private static Retrofit retrofitMovie;

    public static Retrofit getRetrofitInstanceTv() {
        if (retrofitTv == null) {
            retrofitTv = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitTv;
    }

    public static Retrofit getRetrofitInstanceMovie() {
        if (retrofitMovie == null) {
            retrofitMovie = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitMovie;
    }
}
