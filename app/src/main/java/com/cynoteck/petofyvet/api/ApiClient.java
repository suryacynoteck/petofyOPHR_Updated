package com.cynoteck.petofyvet.api;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static final String BASE_URL = "https://petofyapi.azurewebsites.net/api/";
    private ApiClient() {
    }
    private static ApiInterface mApiInterface;
    //service before login
    public static ApiInterface getApiInterface() {
        return (mApiInterface == null) ? setApiInterface() : mApiInterface;
    }
    private static ApiInterface setApiInterface() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit.Builder mBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        mApiInterface = mBuilder.build().create(ApiInterface.class);
        return mApiInterface;
    }


}
