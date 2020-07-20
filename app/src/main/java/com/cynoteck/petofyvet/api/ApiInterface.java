package com.cynoteck.petofyvet.api;

import com.cynoteck.petofyvet.params.loginparams.Loginparams;
import com.cynoteck.petofyvet.params.registerparams.Registerparams;
import com.cynoteck.petofyvet.response.RegisterResponse.RegisterResponse;
import com.cynoteck.petofyvet.response.loginresponse.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("Login")
    Call<LoginResponse> loginApi(@Body Loginparams loginparams);
    @POST("Registration")
    Call<RegisterResponse> registerApi(@Body Registerparams registerparams);

}


