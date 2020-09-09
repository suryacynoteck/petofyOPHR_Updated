package com.cynoteck.petofyvet.api;

import com.cynoteck.petofyvet.params.addHospitalization.AddHospitalizationRequest;
import com.cynoteck.petofyvet.params.addLabRequest.AddLabRequest;
import com.cynoteck.petofyvet.params.addParamRequest.AddPetRequset;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicRequest;
import com.cynoteck.petofyvet.params.addTestXRayParams.AddTestXRayRequest;
import com.cynoteck.petofyvet.params.allStaffRequest.AddStaffRequest;
import com.cynoteck.petofyvet.params.allStaffRequest.ChangeStaffStatusRequest;
import com.cynoteck.petofyvet.params.allStaffRequest.StaffDeatilsRequest;
import com.cynoteck.petofyvet.params.allStaffRequest.UpdateStaffRequest;
import com.cynoteck.petofyvet.params.appointmentParams.AppointmentsStatusRequest;
import com.cynoteck.petofyvet.params.appointmentParams.CreateAppointRequest;
import com.cynoteck.petofyvet.params.appointmentParams.UpdateAppointmentRequest;
import com.cynoteck.petofyvet.params.assignAndRemovePermission.AssignRemovePermissionRequest;
import com.cynoteck.petofyvet.params.changePassRequest.ChangePassRequest;
import com.cynoteck.petofyvet.params.checkpetInVetRegister.InPetRegisterRequest;
import com.cynoteck.petofyvet.params.forgetPassRequest.ForgetPassRequest;
import com.cynoteck.petofyvet.params.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyvet.params.loginRequest.Loginparams;
import com.cynoteck.petofyvet.params.newPetEntryParams.NewPetRequest;
import com.cynoteck.petofyvet.params.otpRequest.SendOtpRequest;
import com.cynoteck.petofyvet.params.petBreedRequest.BreedParams;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVisitDetailsRequest;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyvet.params.petReportsRequest.VisitTypeRequest;
import com.cynoteck.petofyvet.params.registerRequest.Registerparams;
import com.cynoteck.petofyvet.params.searchRemarksParameter.SearchRemaksRequest;
import com.cynoteck.petofyvet.params.staffPermission.StaffPermissionRequest;
import com.cynoteck.petofyvet.params.updateClinicVisitsParams.UpdateClinicReportsRequest;
import com.cynoteck.petofyvet.params.updateHospitalizationParams.UpdateHospitalizationRequest;
import com.cynoteck.petofyvet.params.updateLapTestParams.UpdateLabTestRequest;
import com.cynoteck.petofyvet.params.updateRequest.getValue.UpdateParams;
import com.cynoteck.petofyvet.params.updateRequest.updateParamRequest.UpdatePetRequest;
import com.cynoteck.petofyvet.params.updateXRayParams.UpdateXrayRequest;
import com.cynoteck.petofyvet.params.workingHoursParameter.WorkingHoursParameter;
import com.cynoteck.petofyvet.response.InPetVeterian.InPetVeterianResponse;
import com.cynoteck.petofyvet.response.addHospitalizationResponse.AddhospitalizationResposee;
import com.cynoteck.petofyvet.response.addLabWorkResponse.AddLabWorkResponse;
import com.cynoteck.petofyvet.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofyvet.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofyvet.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyvet.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofyvet.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofyvet.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofyvet.response.addPet.uniqueIdResponse.UniqueResponse;
import com.cynoteck.petofyvet.response.addPetClinicresponse.AddpetClinicResponse;
import com.cynoteck.petofyvet.response.addTestAndXRayResponse.AddTestXRayResponse;
import com.cynoteck.petofyvet.response.appointmentResponse.AppointmentDetailsResponse;
import com.cynoteck.petofyvet.response.appointmentResponse.CreateAppointmentResponse;
import com.cynoteck.petofyvet.response.appointmentResponse.GetAppointmentResponse;
import com.cynoteck.petofyvet.response.clinicVisist.ClinicVisitResponse;
import com.cynoteck.petofyvet.response.forgetAndChangePassResponse.PasswordResponse;
import com.cynoteck.petofyvet.response.getAppointmentsStatusResponse.AppointmentStatusResponse;
import com.cynoteck.petofyvet.response.getLabTestReportResponse.getLabTestReportDetailsResponse.GetLabTestReportDeatilsResponse;
import com.cynoteck.petofyvet.response.getLabTestReportResponse.getPetLabWorkListResponse.PetLabWorkResponse;
import com.cynoteck.petofyvet.response.getPetDetailsResponse.GetPetResponse;
import com.cynoteck.petofyvet.response.getPetHospitalizationResponse.getHospitalizationDeatilsResponse.GetHospitalizationDeatilsResponse;
import com.cynoteck.petofyvet.response.getPetHospitalizationResponse.getHospitalizationListResponse.GetPetHospitalizationResponse;
import com.cynoteck.petofyvet.response.getPetIdCardResponse.PetIdCardResponse;
import com.cynoteck.petofyvet.response.getPetParentResponse.GetPetParentResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.AddUpdateDeleteClinicVisitResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetReportsTypeResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getClinicVisitDetails.GetClinicVisitsDetailsResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getPetClinicVisitsListsResponse.GetPetClinicVisitListResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyvet.response.getStaffResponse.GetAllStaffResponse;
import com.cynoteck.petofyvet.response.getStaffResponse.GetStaffDetailsResponse;
import com.cynoteck.petofyvet.response.getStaffResponse.GetStaffStatusResponse;
import com.cynoteck.petofyvet.response.getStaffResponse.GetUpdateStaffResponse;
import com.cynoteck.petofyvet.response.getWorkingHoursResponse.WorkingHoursResponse;
import com.cynoteck.petofyvet.response.getXRayReports.getPetTestAndXRayResponse.GetPetTestAndXRayResponse;
import com.cynoteck.petofyvet.response.getXRayReports.getXRayReportDetailsResponse.GetXRayReportDeatilsResponse;
import com.cynoteck.petofyvet.response.hospitalTypeListResponse.HospitalAddmissionTypeResp;
import com.cynoteck.petofyvet.response.labTyperesponse.LabTypeResponse;
import com.cynoteck.petofyvet.response.loginRegisterResponse.LoginRegisterResponse;
import com.cynoteck.petofyvet.response.newPetResponse.NewPetRegisterResponse;
import com.cynoteck.petofyvet.response.otpResponse.OtpResponse;
import com.cynoteck.petofyvet.response.recentEntrys.RecentEntrysResponse;
import com.cynoteck.petofyvet.response.saveWorkingReponse.SaveWorkingHoursResponse;
import com.cynoteck.petofyvet.response.searchRemaks.SearchRemaksResponse;
import com.cynoteck.petofyvet.response.staffPermissionListResponse.StaffPermissionResponse;
import com.cynoteck.petofyvet.response.testResponse.XrayTestResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.CityResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.CountryResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetServiceResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.StateResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.UserResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    Call<GetPetListResponse> getPetList(@Header("Authorization") String auth, @Body PetDataRequest getPetDataRequest);

    @POST("user/forgotpassword")
    Call<PasswordResponse> getPasswordResponse(@Body ForgetPassRequest forgetPassRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/UpdateVeterinarian")
    Call<PasswordResponse> getPasswordResponse(@Header("Authorization") String auth, @Body ChangePassRequest changePassRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/UpdateVeterinarian")
    Call<UserResponse> updateUser(@Header("Authorization") String auth, @Body UpdateParams registerparams);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("User/GetUserDetails")
    Call<UserResponse> getUserDetailsApi(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pet/GeneratePetUniqueId")
    Call<UniqueResponse> getGeneratePetUniqueId(@Header("Authorization") String auth);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetBreed")
    Call<BreedCatRespose> getGetPetBreedApi(@Header("Authorization") String auth, @Body BreedParams breedParams);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetAge")
    Call<PetAgeValueResponse> getGetPetAgeApi(@Header("Authorization") String auth, @Body BreedParams breedParams);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetColor")
    Call<PetColorValueResponse> getGetPetColorApi(@Header("Authorization") String auth, @Body BreedParams breedParams);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetSize")
    Call<PetSizeValueResponse> getGetPetSizeApi(@Header("Authorization") String auth, @Body BreedParams breedParams);



    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/AddPet")
    Call<AddPetValueResponse> addNewPet(@Header("Authorization") String auth,
                                        @Body AddPetRequset addPetRequset);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/UpdatePetDetails")
    Call<AddPetValueResponse> updatePetDetails(@Header("Authorization") String auth,
                                               @Body UpdatePetRequest addPetRequset);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetDetail")
    Call<GetPetResponse> getPetDetails(@Header("Authorization") String auth,
                                       @Body GetPetListRequest addPetRequset);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pet/GetPetIdentityCard")
    Call<GetPetResponse> getPetIdentyCard(@Header("Authorization") String auth,
                                       @Body GetPetListRequest addPetRequset);

    @Multipart
    @POST("document/UploadDocument")
    Call<ImageResponse> uploadImages(@Header("Authorization") String auth,
                                      @Part MultipartBody.Part file);


    //reports section.........................................


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("report/GetVisitTypes")
    Call<GetReportsTypeResponse> getReportsType(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetClinicVisits")
    Call<GetPetClinicVisitListResponse> getPetClinicVisits(@Header("Authorization") String auth, @Body VisitTypeRequest visitTypeRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetClinicVisit")
    Call<GetClinicVisitsDetailsResponse> getClinicVisitDetails(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetTestAndXRay")
    Call<GetPetTestAndXRayResponse> getPetTestAndXRay(@Header("Authorization") String auth, @Body VisitTypeRequest visitTypeRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetLabWork")
    Call<PetLabWorkResponse> getPetLabWorkResponse(@Header("Authorization") String auth, @Body VisitTypeRequest visitTypeRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("report/GetPetHospitalization")
    Call<GetPetHospitalizationResponse> getPetHospitalization(@Header("Authorization") String auth, @Body VisitTypeRequest visitTypeRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetTestXRay")
    Call<GetXRayReportDeatilsResponse> getTestXRayDetails(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetPetLabWork")
    Call<GetLabTestReportDeatilsResponse> getPetLabWorkDetails(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetPetHospitalization")
    Call<GetHospitalizationDeatilsResponse> getPetHospitalizationDetails(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/DeleteClinicVisit")
    Call<AddUpdateDeleteClinicVisitResponse> deleteClinicVisit(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/DeleteTestXRay")
    Call<AddUpdateDeleteClinicVisitResponse> deleteTestXRay(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/DeletePetHospitalization")
    Call<AddUpdateDeleteClinicVisitResponse> deletePetHospitalization(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/DeletePetLabWork")
    Call<AddUpdateDeleteClinicVisitResponse> deleteLabTestWork(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);


    //All Staff Section .....................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("staff/AddStaff")
    Call<GetStaffDetailsResponse> addNewStaff(@Header("Authorization") String auth, @Body AddStaffRequest addStaffRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("staff/UpdateStaff")
    Call<GetUpdateStaffResponse> updateStaff(@Header("Authorization") String auth, @Body UpdateStaffRequest updateStaffRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("staff/GetStaffList")
    Call<GetAllStaffResponse> getAllStaff(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("staff/GetStaff")
    Call<GetStaffDetailsResponse> getStaffDeatils(@Header("Authorization") String auth, @Body StaffDeatilsRequest staffDeatilsRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("staff/ChangeStatus")
    Call<GetStaffStatusResponse> getStaffStatus(@Header("Authorization") String auth, @Body ChangeStaffStatusRequest changeStaffStatusRequest);


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetRecentClinicVisits")
    Call<RecentEntrysResponse> getRecentClientcVisits(@Header("Authorization") String auth);

    //CHECK IF A PET EXIST PET IN VET REGISTER........................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/CheckPetInVetRegister")
    Call<InPetVeterianResponse> checkPetInVetRegister(@Header("Authorization") String auth, @Body InPetRegisterRequest inPetRegisterRequest);

    //SEND OTP TO USER..................................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("common/SendOtp")
    Call<OtpResponse> senOtp(@Header("Authorization") String auth, @Body SendOtpRequest inPetRegisterRequest);

    //ADD A PET TO VET REGISTER..........................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/AddPetToRegister")
    Call<NewPetRegisterResponse> addPetToRegister(@Header("Authorization") String auth, @Body NewPetRequest newPetRequest);

    //GET ROUTINE FOLLOW-UP TYPE LIST (CLINIC VISIT)......................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pethealthrecord/GetClinicVisitRoutineFollowupTypes")
    Call<ClinicVisitResponse> getClinicVisit(@Header("Authorization") String auth);

    //SEARCH TREATMENT & REMARKS..........................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/SearchTreatmentRemarks")
    Call<SearchRemaksResponse> getSearchTreatmentRemarks(@Header("Authorization") String auth, @Body SearchRemaksRequest searchRemaksRequest);

    //ADD CLINIC VISIT.....................................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/AddClinicVisit")
    Call<AddpetClinicResponse> addClinicVisit(@Header("Authorization") String auth, @Body AddPetClinicRequest addPetClinicRequest);

    //Update CLINIC VISIT...................................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/UpdateClinicVisit")
    Call<AddpetClinicResponse> updateClinicVisit(@Header("Authorization") String auth, @Body UpdateClinicReportsRequest updateClinicReportsRequest);

    //HOSPITAL TYPE.........................................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pethealthrecord/GetHospitalTypeList")
    Call<HospitalAddmissionTypeResp> getHospitalTypeList(@Header("Authorization") String auth);

    //GET LAB TYPE..........................................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pethealthrecord/GetLabTypeList")
    Call<LabTypeResponse> getLabTypeList(@Header("Authorization") String auth);

    //GET TYPE OF TEST LIST (TEST X-RAY)....................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pethealthrecord/GetTestTypeList")
    Call<XrayTestResponse> getTestTypeList(@Header("Authorization") String auth);

    //Get Last Prescrition...................................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/GetLastPrescription")
    Call<GetClinicVisitsDetailsResponse> getLastPrescription(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest petClinicVisitDetailsRequest);

    //ADD PET LAB WORK.......................................................................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/AddPetLabWork")
    Call<AddLabWorkResponse> addPetLabWork(@Header("Authorization") String auth, @Body AddLabRequest addLabRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/UpdateLabWork")
    Call<AddLabWorkResponse> updatePetLabWork(@Header("Authorization") String auth, @Body UpdateLabTestRequest updateLabTestRequest);

    //Add Pet Test And Xray

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/AddTestXRay")
    Call<AddTestXRayResponse> addTestXRay(@Header("Authorization") String auth, @Body AddTestXRayRequest addTestXRayRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/UpdateTestXRay")
    Call<AddTestXRayResponse> updateTestXRay(@Header("Authorization") String auth, @Body UpdateXrayRequest updateXrayRequest);

    //ADD PET HOSPITALIZATION


       @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/AddPetHospitalization")
    Call<AddhospitalizationResposee> addPetHospitalization(@Header("Authorization") String auth, @Body AddHospitalizationRequest addHospitalizationRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("pethealthrecord/UpdateHospitalization")
    Call<AddhospitalizationResposee> updatePetHospitalization(@Header("Authorization") String auth, @Body UpdateHospitalizationRequest updateHospitalizationRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST(" pet/GetPetIdentityCard")
    Call<PetIdCardResponse> getPetIdCard(@Header("Authorization") String auth, @Body PetClinicVisitDetailsRequest idCardRequest);


    //APPOINTMENTS......................

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/getappointment")
    Call<GetAppointmentResponse> getAppointment(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/ApproveRejectAppointment")
    Call<AppointmentStatusResponse> appointmentApproveReject(@Header("Authorization") String auth, @Body AppointmentsStatusRequest appointmentsStatusRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/CreateAppointment")
    Call<CreateAppointmentResponse> createAppointment(@Header("Authorization") String auth, @Body CreateAppointRequest createAppointRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/UpdateAppointment")
    Call<CreateAppointmentResponse> updateAppointment(@Header("Authorization") String auth, @Body UpdateAppointmentRequest updateAppointmentRequest);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/GetPetParentList")
    Call<GetPetParentResponse> getPetParentList(@Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("appointment/GetAppointmentById")
    Call<AppointmentDetailsResponse> getAppointmentsDetails(@Header("Authorization") String auth,
                                                            @Body GetPetListRequest addPetRequset);

    //TODO=================GET OPERATING HOURS===========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/GetOperatingHours")
    Call<WorkingHoursResponse> getOperatingHours(@Header("Authorization") String auth);

    //TODO==================SAVE OPERATING HOURS===========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("user/SaveOperatingHours")
    Call<SaveWorkingHoursResponse> saveOperatingHours(@Header("Authorization") String auth, @Body WorkingHoursParameter workingHoursParameter);

    //TODO===================GetStaffPermissionList====================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Staff/GetStaffPermissionList")
    Call<StaffPermissionResponse> getStaffPermissionList(@Header("Authorization") String auth, @Body StaffPermissionRequest staffPermissionRequest);

    //TODO===================AssignPermission==========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Staff/AssignPermission")
    Call<StaffPermissionResponse> assignPermission(@Header("Authorization") String auth, @Body AssignRemovePermissionRequest assignRemovePermissionRequest);

    //TODO====================RemovePermission==========================

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Staff/RemovePermission")
    Call<StaffPermissionResponse> removePermission(@Header("Authorization") String auth, @Body AssignRemovePermissionRequest assignRemovePermissionRequest);


}


