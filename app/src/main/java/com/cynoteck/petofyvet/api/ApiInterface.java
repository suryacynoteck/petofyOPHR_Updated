package com.cynoteck.petofyvet.api;

import com.cynoteck.petofyvet.params.loginparams.Loginparams;
import com.cynoteck.petofyvet.params.registerparams.Registerparams;
import com.cynoteck.petofyvet.response.loginRegisterResponse.LoginRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("Login")
    Call<LoginRegisterResponse> loginApi(@Body Loginparams loginparams);
    @POST("Registration")
    Call<LoginRegisterResponse> registerApi(@Body Registerparams registerparams);

}


