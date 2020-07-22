package com.cynoteck.petofyvet.api;

import com.cynoteck.petofyvet.params.loginparams.Loginparams;
import com.cynoteck.petofyvet.params.registerparams.Registerparams;
import com.cynoteck.petofyvet.response.loginRegisterResponse.CityResponse;
import com.cynoteck.petofyvet.response.loginRegisterResponse.CountryResponse;
import com.cynoteck.petofyvet.response.loginRegisterResponse.LoginRegisterResponse;
import com.cynoteck.petofyvet.response.loginRegisterResponse.StateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("User/Login")
    Call<LoginRegisterResponse> loginApi(@Body Loginparams loginparams);
    @POST("User/Registration")
    Call<LoginRegisterResponse> registerApi(@Body Registerparams registerparams);
    @GET("common/GetState")
    Call<StateResponse> getStateApi();
    @GET("common/GetCountry")
    Call<CountryResponse>getCountryApi();
    @GET("common/GetCity")
    Call<CityResponse> getCityApi();

}


