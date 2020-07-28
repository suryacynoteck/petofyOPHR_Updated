package com.cynoteck.petofyvet.api;

import com.cynoteck.petofyvet.params.changePassRequest.ChangePassRequest;
import com.cynoteck.petofyvet.params.forgetPassRequest.ForgetPassRequest;
import com.cynoteck.petofyvet.params.getPetRequest.GetPetDataRequest;
import com.cynoteck.petofyvet.params.loginparams.Loginparams;
import com.cynoteck.petofyvet.params.registerparams.Registerparams;
import com.cynoteck.petofyvet.params.updatePerams.updateParams;
import com.cynoteck.petofyvet.response.forgetAndChangePassResponse.PasswordResponse;
import com.cynoteck.petofyvet.response.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyvet.response.loginRegisterResponse.LoginRegisterResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.CityResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.CountryResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetServiceResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.StateResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
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

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("common/GetPetTypes")
    Call<PetTypeResponse> petTypeApi(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("common/GetServiceTypes")
    Call<PetServiceResponse> petServiceTypeApi(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetList")
    Call<GetPetListResponse> getPetList(@Header("Authorization") String auth, @Body GetPetDataRequest getPetDataRequest);


    @POST("user/forgotpassword")
    Call<PasswordResponse> getPasswordResponse(@Body ForgetPassRequest forgetPassRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/UpdateVeterinarian")
    Call<PasswordResponse> getPasswordResponse(@Header("Authorization") String auth, @Body ChangePassRequest changePassRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/UpdateVeterinarian")
    Call<UserResponse> updateUser(@Header("Authorization") String auth, @Body updateParams registerparams);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("User/GetUserDetails")
    Call<UserResponse> getUserDetailsApi(@Header("Authorization") String auth);


}


