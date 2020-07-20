package com.cynoteck.petofyvet.api;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

   private static final String BASE_URL = "https://petofyapi.azurewebsites.net/api/User/";


    private ApiClient() {
    }

    private static ApiInterface mApiInterface;

    //service before login
    public static ApiInterface getApiInterface() {
        return (mApiInterface == null) ? setApiInterface() : mApiInterface;
    }

    private static ApiInterface setApiInterface() {
        String mAuthToken = "";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder mBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        if (!TextUtils.isEmpty(mAuthToken)) {
            final String finalMAuthToken = mAuthToken;
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    Request original = chain.request();

                    Request.Builder builder = original.newBuilder()
                            .header("Authorization", finalMAuthToken);

                    Request request = builder.build();
                    return chain.proceed(request);
                }
            };

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
                mBuilder.client(httpClient.build());
                mApiInterface = mBuilder.build().create(ApiInterface.class);
            }
        } else
            mApiInterface = mBuilder.build().create(ApiInterface.class);

        return mApiInterface;
    }
}
