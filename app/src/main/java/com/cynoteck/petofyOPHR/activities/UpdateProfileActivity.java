package com.cynoteck.petofyOPHR.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.updateRequest.getValue.UpdateParams;
import com.cynoteck.petofyOPHR.params.updateRequest.getValue.UpdateRequest;
import com.cynoteck.petofyOPHR.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyOPHR.response.updateProfileResponse.CityResponse;
import com.cynoteck.petofyOPHR.response.updateProfileResponse.CountryResponse;
import com.cynoteck.petofyOPHR.response.updateProfileResponse.PetServiceResponse;
import com.cynoteck.petofyOPHR.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyOPHR.response.updateProfileResponse.StateResponse;
import com.cynoteck.petofyOPHR.response.updateProfileResponse.UserResponse;
import com.cynoteck.petofyOPHR.response.updateVetDetailsresponse.UpdateVetResponse;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.Methods;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    EditText first_name_updt,last_name_updt,email_updt,phone_updt,address_updt,online_charges_ET,
            postal_code_updt,website_updt,social_media_url_updt,registration_num_updt,clinicCode_updt,
            vet_qualification_updt,description_updt,clinic_name_updt;
    AppCompatSpinner country_spnr_updt,state_spnr_updt,city_spnr_updt;
    Button update_profile;
    TextView select_Category,select_service_Category;
    ImageView back_arrow, category_img_one,category_img_two,service_cat_img_one,service_cat_img_two,logout,
            service_cat_img_three,service_cat_img_four,service_cat_img_five ;
    CheckBox online_CB;
    Dialog dialog;
    String slctCatOneImage="",slctCatTwoImage="",slctServcOneImage="",slctServcTwoImage="",slctServcThreeImage="",
            slctServcfourImage="",slctServcFiveImage="",id="",password="",activityType="",strVetDese="",strClinicName="",strIsVet="",strIsActive="";
    int spnrCountry=0,spnrState=0,spnrCity=0;
    CountryResponse stateResponse;
    Uri fileUri;
    Methods methods;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    View view;

    private static final String IMAGE_DIRECTORY = "/Picture";
    private int GALLERY = 1, CAMERA = 2;
    File catfile1 = null;
    File catfile2 = null;
    File srvcfile1 = null;
    File srvcfile2 = null;
    File srvcfile3 = null;
    File srvcfile4 = null;
    File srvcfile5 = null;
    Bitmap bitmap, thumbnail;
    String capImage;
    boolean doubleBackToExitPressedOnce = false;

    ArrayList<String> state;
    ArrayList<String>countery;
    ArrayList<String>city;


    String[] petCategory;
    //String[] serviceCategory = new String[]{"Consultaion", "Grooming","Hostel","Training","Products"};
    String[] serviceCategory;

    //String[] listItems;
    boolean[] chkItems;
    ArrayList<Integer> muserItem=new ArrayList<>();

    boolean[] chkItemsSevice;
    ArrayList<Integer> muserItemService=new ArrayList<>();

    HashMap<String,String>cityHasmap=new HashMap<>();
    HashMap<String,String>stateHasmap=new HashMap<>();
    HashMap<String,String>countryHasmap=new HashMap<>();
    HashMap<String,String>categoryHasmap=new HashMap<>();
    HashMap<String,String>servcCatHasmap=new HashMap<>();

    ArrayList<String>listCategoryId=new ArrayList<>();
    ArrayList<String>listServiceCatId=new ArrayList<>();

    String imagename="",strFirstNm="",strLstNm="",strEmlUpdt="",strPhUpdt="",strAddrsUpdt="",strPostlUpdt="",
            strWbUpdt="",strSoclMdUelUpdt="",strRegistNumUpdt="",strVetQulafctnUpdt="",strPetCatUpdt="",
            strSrvcCatUpdt="",strContrySpnr="",strStateSpnr="",strCitySpnr="",strCountryId="",strStringCityId="",
            strStateId="",strCatId="",strSrvsCatId="",strCatUrl1="",strCatUrl2="",strSrvsUrl1="",strSrvsUrl2="",
            strSrvsUrl3="",strSrvsUrl4="",strSrvsUrl5="",strClinicCode="",intentService="",intentType="",strOnlineCharges="10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        methods = new Methods(this);

        Intent intent = getIntent();
        activityType = intent.getStringExtra("activityName");
        id = intent.getStringExtra("id");
        strIsActive=intent.getStringExtra("isActive");
        strIsVet=intent.getStringExtra("isVeterinarian");
        password = intent.getStringExtra("password");
        strFirstNm = intent.getStringExtra("firstName");
        strLstNm = intent.getStringExtra("lastName");
        strEmlUpdt = intent.getStringExtra("email");
        strPhUpdt = intent.getStringExtra("phone");
        strAddrsUpdt = intent.getStringExtra("address");
        strPostlUpdt = intent.getStringExtra("pincode");
        strWbUpdt = intent.getStringExtra("website");
        strSoclMdUelUpdt = intent.getStringExtra("socialMedia");
        strRegistNumUpdt = intent.getStringExtra("vetRegNo");
        strVetQulafctnUpdt = intent.getStringExtra("vetStudy");
        strPetCatUpdt = intent.getStringExtra("category");
        strSrvcCatUpdt = intent.getStringExtra("service");
        strVetDese = intent.getStringExtra("description");
        strClinicName=intent.getStringExtra("clinicName");

        if(intent.getStringExtra("country")!=null)
            strContrySpnr = intent.getStringExtra("country");
        if(intent.getStringExtra("state")!=null)
            strStateSpnr = intent.getStringExtra("state");
        if(intent.getStringExtra("city")!=null)
            strCitySpnr = intent.getStringExtra("city");

        strSrvsUrl1 = intent.getStringExtra("serviceImage1");
        strSrvsUrl2 = intent.getStringExtra("serviceImage2");
        strSrvsUrl3 = intent.getStringExtra("serviceImage3");
        strSrvsUrl4 = intent.getStringExtra("serviceImage4");
        strSrvsUrl5 = intent.getStringExtra("serviceImage5");
        intentService = intent.getStringExtra("petService");
        intentType = intent.getStringExtra("petType");
        strCatId = intent.getStringExtra("petTypeValue");
        strSrvsCatId = intent.getStringExtra("petTypeValue");
        strOnlineCharges = intent.getStringExtra("onlineConsultationCharges");
        strClinicCode = intent.getStringExtra("clinicCode");


        init();

        if(activityType.equals("Edit"))
        {
            logout.setVisibility(View.GONE);
        }

        if((strOnlineCharges!=null))
        {
            if((strOnlineCharges!=null)||(!strOnlineCharges.equals("")||(!strOnlineCharges.equals("0"))))
            {
                online_CB.setChecked(true);
                online_charges_ET.setVisibility(View.VISIBLE);
                online_charges_ET.setText(strOnlineCharges);
            }
        }

        if((strClinicCode!=null))
        {
            if((!strClinicCode.equals("")))
                clinicCode_updt.setText(strClinicCode);
        }


        //setImages();
        setValueFromSharePref();
        requestMultiplePermissions();
        if(methods.isInternetOn())
        {
            getState();
            getCountry();
            petType();
            petServiceType();

        }
        else
        {
            methods.DialogInternet();
        }

    }
    private void setImages() {
        if (strSrvsUrl1.isEmpty()){
        }else {
            Glide.with(this)
                    .load(strSrvsUrl1)
                    .into(service_cat_img_one);
        }
        if (strSrvsUrl2.isEmpty()){

        }else {
            Glide.with(this)
                    .load(strSrvsUrl2)
                    .into(service_cat_img_two);
        }if (strSrvsUrl3.isEmpty()){

        }else {
            Glide.with(this)
                    .load(strSrvsUrl3)
                    .into(service_cat_img_three);
        }if (strSrvsUrl4.isEmpty()){

        }else {
            Glide.with(this)
                    .load(strSrvsUrl4)
                    .into(service_cat_img_four);
        }if (strSrvsUrl5.isEmpty()){
        }else {
            Glide.with(this)
                    .load(strSrvsUrl5)
                    .into(service_cat_img_five);
        }
    }

    private void getState() {
        methods.showCustomProgressBarDialog(this);
        ApiService<StateResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getStateApi(), "GetState");

    }

    private void getCountry() {
        ApiService<CountryResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getCountryApi(), "GetCountry");

    }

    private void getCity(String stateId){
        ApiService<CityResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getCityApi(stateId), "GetCity");
    }
    private void petType() {
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(Config.token), "GetPetTypes");
    }

    private void petServiceType() {
        ApiService<PetServiceResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petServiceTypeApi(Config.token), "GetServiceTypes");

    }

    private void init() {
        //TextInputEditText
        first_name_updt=findViewById(R.id.first_name_updt);
        last_name_updt=findViewById(R.id.last_name_updt);
        email_updt=findViewById(R.id.email_updt);
        phone_updt=findViewById(R.id.phone_updt);
        address_updt=findViewById(R.id.address_updt);
        postal_code_updt=findViewById(R.id.postal_code_updt);
        website_updt=findViewById(R.id.website_updt);
        social_media_url_updt=findViewById(R.id.social_media_url_updt);
        registration_num_updt=findViewById(R.id.registration_num_updt);
        vet_qualification_updt=findViewById(R.id.vet_qualification_updt);
        clinicCode_updt=findViewById(R.id.clinicCode_updt);
        online_charges_ET=findViewById(R.id.online_charges_ET);
        description_updt=findViewById(R.id.description_updt);
        clinic_name_updt=findViewById(R.id.clinic_name_updt);


        //Spinner
        country_spnr_updt=findViewById(R.id.country_spnr_updt);
        state_spnr_updt=findViewById(R.id.state_spnr_updt);
        city_spnr_updt=findViewById(R.id.city_spnr_updt);

        //Image View
        logout=findViewById(R.id.logout);
        category_img_one=findViewById(R.id.category_img_one);
        category_img_two=findViewById(R.id.category_img_two);
        service_cat_img_one=findViewById(R.id.service_cat_img_one);
        service_cat_img_two=findViewById(R.id.service_cat_img_two);
        service_cat_img_three=findViewById(R.id.service_cat_img_three);
        service_cat_img_four=findViewById(R.id.service_cat_img_four);
        service_cat_img_five=findViewById(R.id.service_cat_img_five);
        back_arrow = findViewById(R.id.back_arrow);

        //Check Box
        online_CB=findViewById(R.id.online_CB);

        logout.setOnClickListener(this);
        category_img_one.setOnClickListener(this);
        category_img_two.setOnClickListener(this);
        service_cat_img_one.setOnClickListener(this);
        service_cat_img_two.setOnClickListener(this);
        service_cat_img_three.setOnClickListener(this);
        service_cat_img_four.setOnClickListener(this);
        service_cat_img_five.setOnClickListener(this);
        back_arrow.setOnClickListener(this);
        online_CB.setOnClickListener(this);

        //Button
        update_profile=findViewById(R.id.update_profile);
        select_Category=findViewById(R.id.select_Category);
        select_service_Category=findViewById(R.id.select_service_Category);

        update_profile.setOnClickListener(this);
        select_Category.setOnClickListener(this);
        select_service_Category.setOnClickListener(this);

        select_service_Category.setText(intentService);
        select_Category.setText(intentType);

    }

    private void setValueFromSharePref() {
        postal_code_updt.setText(strPostlUpdt);
        website_updt.setText(strWbUpdt);
        social_media_url_updt.setText(strSoclMdUelUpdt);
        email_updt.setText(strEmlUpdt);
        first_name_updt.setText(strFirstNm);
        last_name_updt.setText(strLstNm);
        phone_updt.setText(strPhUpdt);
        address_updt.setText(strAddrsUpdt);
        vet_qualification_updt.setText(strVetQulafctnUpdt);
        registration_num_updt.setText(strRegistNumUpdt);
        description_updt.setText(strVetDese);
        clinic_name_updt.setText(strClinicName);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_arrow:
                onBackPressed();
                break;
            case R.id.online_CB:
                if(((CompoundButton) view).isChecked()){
                    online_charges_ET.setVisibility(View.VISIBLE);
                } else {
                    online_charges_ET.setVisibility(View.GONE);
                }
                break;
            case R.id.update_profile:
                strFirstNm= first_name_updt.getText().toString().trim();
                strLstNm = last_name_updt.getText().toString().trim();
                strEmlUpdt = email_updt.getText().toString().trim();
                strPhUpdt = phone_updt.getText().toString().trim();
                strAddrsUpdt = address_updt.getText().toString().trim();
                strPostlUpdt = postal_code_updt.getText().toString().trim();
                strWbUpdt = website_updt.getText().toString().trim();
                strSoclMdUelUpdt = social_media_url_updt.getText().toString().trim();
                strRegistNumUpdt = registration_num_updt.getText().toString().trim();
                strVetQulafctnUpdt = vet_qualification_updt.getText().toString().trim();
                strPetCatUpdt = select_Category.getText().toString().trim();
                strSrvcCatUpdt = select_service_Category.getText().toString().trim();
                strClinicCode = clinicCode_updt.getText().toString().trim();
                strVetDese=description_updt.getText().toString();
                strClinicName=clinic_name_updt.getText().toString();

                if(online_CB.isChecked()==true)
                strOnlineCharges=online_charges_ET.getText().toString();
                else
                strOnlineCharges="10";

                if(strFirstNm.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError("Name is empty");
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strLstNm.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError("Last Name is empty");
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strEmlUpdt.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError("Email Id is Empty");
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if (!strEmlUpdt.matches(emailPattern))
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    email_updt.setError("Invalid Email");
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strPhUpdt.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError("Phone number is empty");
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strAddrsUpdt.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError("Address is empty");
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strPostlUpdt.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError("Postal code is empty");
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strRegistNumUpdt.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError("Registration no empty");
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strVetQulafctnUpdt.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    vet_qualification_updt.setError("Qualification empty");
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strClinicCode.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    online_charges_ET.setError(null);
                    vet_qualification_updt.setError(null);
                    clinicCode_updt.setError("Enter Your Clinic Code");
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }

//                else if(strVetDese.isEmpty())
//                {
//                    select_Category.setError(null);
//                    select_service_Category.setError(null);
//                    first_name_updt.setError(null);
//                    last_name_updt.setError(null);
//                    email_updt.setError(null);
//                    phone_updt.setError(null);
//                    address_updt.setError(null);
//                    postal_code_updt.setError(null);
//                    website_updt.setError(null);
//                    social_media_url_updt.setError(null);
//                    registration_num_updt.setError(null);
//                    online_charges_ET.setError(null);
//                    vet_qualification_updt.setError(null);
//                    clinicCode_updt.setError(null);
//                    description_updt.setError("null");
//                    clinic_name_updt.setError(null);
//                }

                else if(strClinicName.isEmpty())
                {
                    select_Category.setError(null);
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    online_charges_ET.setError(null);
                    vet_qualification_updt.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError("Enter Clinic Name");
                }
                else if(strPetCatUpdt.isEmpty()||(strPetCatUpdt.equals("Set Category")))
                {
                    select_Category.setError("Set Category");
                    select_service_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if(strSrvcCatUpdt.isEmpty()||(strSrvcCatUpdt.equals("Set Services")))
                {
                    select_service_Category.setError("Set Services");
                    select_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    online_charges_ET.setError(null);
                    clinicCode_updt.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);
                }
                else if((strCitySpnr.equals("Select City")))
                {
                    Toast.makeText(this, "Select City!!", Toast.LENGTH_SHORT).show();
                }
                else if((strStateSpnr.equals("Select State")))
                {
                    Toast.makeText(this, "Select State!!", Toast.LENGTH_SHORT).show();
                }
                else if((strContrySpnr.equals("Select Country")))
                {
                    Toast.makeText(this, "Select Country!!", Toast.LENGTH_SHORT).show();
                }
                else if (strCatId.isEmpty()){
                    Toast.makeText(this, "Select Pet Type !", Toast.LENGTH_SHORT).show();
                }
                else if (strSrvsCatId.isEmpty()){
                    Toast.makeText(this, "Select Service Type !", Toast.LENGTH_SHORT).show();
                }
                else if((online_CB.isChecked()==true)&&(strOnlineCharges.isEmpty()))
                {
                        Toast.makeText(this, "Enter Consultant Charges", Toast.LENGTH_SHORT).show();
                        select_service_Category.setError(null);
                        select_Category.setError(null);
                        first_name_updt.setError(null);
                        last_name_updt.setError(null);
                        email_updt.setError(null);
                        phone_updt.setError(null);
                        address_updt.setError(null);
                        postal_code_updt.setError(null);
                        website_updt.setError(null);
                        social_media_url_updt.setError(null);
                        registration_num_updt.setError(null);
                        vet_qualification_updt.setError(null);
                        clinicCode_updt.setError(null);
                        online_charges_ET.setError("Enter Charges");
                        description_updt.setError(null);
                        clinic_name_updt.setError(null);
                }
                else
                {
                    //All data update api call from here.
                    select_service_Category.setError(null);
                    select_Category.setError(null);
                    first_name_updt.setError(null);
                    last_name_updt.setError(null);
                    email_updt.setError(null);
                    phone_updt.setError(null);
                    address_updt.setError(null);
                    postal_code_updt.setError(null);
                    website_updt.setError(null);
                    social_media_url_updt.setError(null);
                    registration_num_updt.setError(null);
                    vet_qualification_updt.setError(null);
                    clinicCode_updt.setError(null);
                    online_charges_ET.setError(null);
                    description_updt.setError(null);
                    clinic_name_updt.setError(null);

                    UpdateParams data = new UpdateParams();
                    data.setId(id);
                    data.setFirstName(strFirstNm.trim());
                    data.setLastName(strLstNm.trim());
                    data.setName(strFirstNm+" "+strLstNm);
                    data.setPassword("password");
                    data.setConfirmPassword("password");
                    data.setCompany(strClinicName);
                    data.setDescription(strVetDese);
                    data.setPhone(strPhUpdt);
                    data.setPhone2("");
                    data.setEmail(strEmlUpdt);
                    data.setAddress(strAddrsUpdt);
                    data.setAddress2("");
                    data.setWebsite(strWbUpdt);
                    data.setSocialMediaUrl(strSoclMdUelUpdt);
                    data.setCityId(strStringCityId);
                    data.setStateId(strStateId);
                    data.setCountryId(strCountryId);
                    data.setPostalCode(strPostlUpdt);
                    data.setSelectedPetTypeIds(strCatId);
                    data.setSelectedServiceTypeIds(strSrvsCatId);
                    data.setProfileImageUrl(Config.user_Veterian_url);
                    data.setServiceImageUrl("");
                    data.setFirstServiceImageUrl(strSrvsUrl1);
                    data.setSecondServiceImageUrl(strCatUrl2);
                    data.setThirdServiceImageUrl(strSrvsUrl3);
                    data.setFourthServiceImageUrl(strSrvsUrl4);
                    data.setFifthServiceImageUrl(strSrvsUrl5);
                    data.setCoverImageUrl("");
                    data.setIsVeterinarian("true");
                    data.setVetQualifications(strVetQulafctnUpdt);
                    data.setVetRegistrationNumber(strRegistNumUpdt);
                    data.setOnlineConsultationCharges(strOnlineCharges);
                    data.setClinicCode(strClinicCode);
                    if (strIsActive.equals("true")){
                        data.setIsActive("true");
                    }else {
                        data.setIsActive("false");
                    }

//                    data.setId(id);
//                    data.setFirstName("aviral");
//                    data.setLastName("rana");
//                    data.setName("aviral rana");
//                    data.setPassword(password);
//                    data.setConfirmPassword(password);
//                    data.setCompany("");
//                    data.setDescription("");
//                    data.setPhone("999-788-7756");
//                    data.setPhone2("999-788-7756");
//                    data.setEmail(strEmlUpdt);
//                    data.setAddress(strAddrsUpdt);
//                    data.setAddress2("");
//                    data.setWebsite(strWbUpdt);
//                    data.setSocialMediaUrl(strSoclMdUelUpdt);
//                    data.setCityId(strStringCityId);
//                    data.setStateId(strStateId);
//                    data.setCountryId(strCountryId);
//                    data.setPostalCode(strPostlUpdt);
//                    data.setSelectedPetTypeIds("1,2");
//                    data.setSelectedServiceTypeIds("1,3");
//                    data.setProfileImageUrl(Config.user_Veterian_url);
//                    data.setServiceImageUrl("");
//                    data.setFirstServiceImageUrl(strSrvsUrl1);
//                    data.setSecondServiceImageUrl(strSrvsUrl2);
//                    data.setThirdServiceImageUrl(strSrvsUrl3);
//                    data.setFourthServiceImageUrl(strSrvsUrl4);
//                    data.setFifthServiceImageUrl(strSrvsUrl5);
//                    data.setCoverImageUrl(strCatUrl2);
//                    data.setIsVeterinarian("1");
//                    data.setVetQualifications(strVetQulafctnUpdt);
//                    data.setVetRegistrationNumber(strRegistNumUpdt);

                    final UpdateRequest updateRequest = new UpdateRequest();
                    updateRequest.setData(data);
                    if(methods.isInternetOn())
                    {

                        androidx.appcompat.app.AlertDialog.Builder confirmationDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
                        confirmationDialog.setTitle("Confirmation!!");
                        confirmationDialog.setMessage("Are you want to update your profile..");
                        confirmationDialog.setCancelable(false);

                        confirmationDialog.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        updateUser(updateRequest);
                                    }
                                });
                        confirmationDialog.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        androidx.appcompat.app.AlertDialog alert11 = confirmationDialog.create();
                        alert11.show();
                    }
                    else
                    {
                        methods.DialogInternet();
                    }
                }
                break;

            case R.id.select_Category:
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(this);
                mBuilder.setTitle("Items available in a shop");
                mBuilder.setMultiChoiceItems(petCategory, chkItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked)
                        {
                            if(!muserItem.contains(position)){
                                muserItem.add(position);
                            }
                        }
                        else if(muserItem.contains(position)){
                            muserItem.remove(position);
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item="";
                        for(int i = 0;i<muserItem.size();i++){
                            item=item + petCategory[muserItem.get(i)];
                            strCatId=strCatId + categoryHasmap.get(petCategory[muserItem.get(i)]);
                            if(i != muserItem.size()-1);{
                                item=item+", ";
                                strCatId=strCatId+",";
                            }
                        }strCatId =methods.removeLastElement(strCatId);
                    Log.e("hhhhhh",strCatId);
                        //Log.d("Selected_item_category",""+item);
                        if(item.equals(""))
                        {
                            select_Category.setText("Set Category");
                        }
                        else {
                            select_Category.setText(item);
                        }
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i=0;i<chkItems.length;i++){
                            chkItems[i]=false;
                            muserItem.clear();
                            listCategoryId.clear();
                            select_Category.setText("Set Category");
                        }
                    }
                });

                AlertDialog mDialog=mBuilder.create();
                mDialog.show();
                break;

            case R.id.select_service_Category:
                AlertDialog.Builder mBuilderr=new AlertDialog.Builder(this);
                mBuilderr.setTitle("Items available in a shop");
                mBuilderr.setMultiChoiceItems(serviceCategory, chkItemsSevice, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked)
                        {
                            if(!muserItemService.contains(position)){
                                muserItemService.add(position);
                            }
                        }
                        else if(muserItemService.contains(position)){
                            muserItemService.remove(position);
                        }
                    }
                });

                mBuilderr.setCancelable(false);
                mBuilderr.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item="";
                        for(int i = 0;i<muserItemService.size();i++){
                            item=item + serviceCategory[muserItemService.get(i)];
                            strSrvsCatId=strSrvsCatId + servcCatHasmap.get(serviceCategory[muserItemService.get(i)]);
                            if(i != muserItemService.size()-1);{
                                item=item+", ";
                                strSrvsCatId=strSrvsCatId+",";
                            }
                        }strSrvsCatId =methods.removeLastElement(strSrvsCatId);
                        Log.d("Selected_item_category",""+item);
                        if(item.equals("")){
                            select_service_Category.setText("Set Services");
                        }
                        else {
                            select_service_Category.setText(item);
                        }
                    }
                });

                mBuilderr.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilderr.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for(int i=0;i<chkItemsSevice.length;i++){
                            chkItemsSevice[i]=false;
                            muserItemService.clear();
                            listServiceCatId.clear();
                            select_service_Category.setText("Set Sevices");
                        }
                    }
                });

                AlertDialog mDialogg=mBuilderr.create();
                mDialogg.show();
                break;

            case R.id.category_img_one:
                slctCatOneImage="1";
                showPictureDialog();
                break;
            case R.id.category_img_two:
                slctCatTwoImage="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_one:
                slctServcOneImage="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_two:
                slctServcTwoImage="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_three:
                slctServcThreeImage="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_four:
                slctServcfourImage="1";
                showPictureDialog();
                break;
            case R.id.service_cat_img_five:
                slctServcFiveImage="1";
                showPictureDialog();
                break;
            case R.id.logout:
                SharedPreferences preferences =this.getSharedPreferences("userdetails",0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }

    }

    private void updateUser(UpdateRequest updateRequest) {
        methods.showCustomProgressBarDialog(this);
        ApiService<UpdateVetResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().updateUser(Config.token,updateRequest), "UpdateVeterinarian");
        Log.e("DATALOG","checkUpdate=> "+updateRequest);

        Gson gson = new Gson();
        String update = gson.toJson(updateRequest);
        Log.e("DATALOGJSON","checkUpdate=> "+update);

    }

    private void showPictureDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        TextView select_camera = (TextView) dialog.findViewById(R.id.select_camera);
        TextView select_gallery = (TextView) dialog.findViewById(R.id.select_gallery);
        TextView cancel_dialog = (TextView) dialog.findViewById(R.id.cancel_dialog);

        select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallary();
            }
        });

        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(slctCatOneImage.equals("1")){
                    slctCatOneImage="0";
                }
                if(slctCatTwoImage.equals("1")){
                    slctCatTwoImage="0";
                }
                if(slctServcOneImage.equals("1")){
                    slctServcOneImage="0";
                }
                if(slctServcTwoImage.equals("1")){
                    slctServcTwoImage="0";
                }
                if(slctServcThreeImage.equals("1")){
                    slctServcThreeImage="0";
                }
                if(slctServcfourImage.equals("1")){
                    slctServcfourImage="0";
                }
                if(slctServcFiveImage.equals("1")){
                    slctServcFiveImage="0";
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void choosePhotoFromGallary() {


        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(intent, CAMERA);

    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    if(slctCatOneImage.equals("1")){
                        category_img_one.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctCatTwoImage.equals("1")){
                        category_img_two.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcOneImage.equals("1")){
                        service_cat_img_one.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcTwoImage.equals("1")){
                        service_cat_img_two.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcThreeImage.equals("1")){
                        service_cat_img_three.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcfourImage.equals("1")){
                        service_cat_img_four.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcFiveImage.equals("1")){
                        service_cat_img_five.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    if(slctCatOneImage.equals("1")){
                        slctCatOneImage="0";
                    }
                    if(slctCatTwoImage.equals("1")){
                        slctCatTwoImage="0";
                    }
                    if(slctServcOneImage.equals("1")){
                        slctServcOneImage="0";
                    }
                    if(slctServcTwoImage.equals("1")){
                        slctServcTwoImage="0";
                    }
                    if(slctServcThreeImage.equals("1")){
                        slctServcThreeImage="0";
                    }
                    if(slctServcfourImage.equals("1")){
                        slctServcfourImage="0";
                    }
                    if(slctServcFiveImage.equals("1")){
                        slctServcFiveImage="0";
                    }
                    Toast.makeText(UpdateProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {

            if (data.getData() == null)
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl",""+thumbnail);
                if(slctCatOneImage.equals("1")){
                    category_img_one.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctCatTwoImage.equals("1")){
                    category_img_two.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcOneImage.equals("1")){
                    service_cat_img_one.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcTwoImage.equals("1")){
                    service_cat_img_two.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcThreeImage.equals("1")){
                    service_cat_img_three.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcfourImage.equals("1")){
                    service_cat_img_four.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcFiveImage.equals("1")){
                    service_cat_img_five.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(UpdateProfileActivity.this.getContentResolver(), data.getData());

                    if(slctCatOneImage.equals("1")){
                        category_img_one.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctCatTwoImage.equals("1")){
                        category_img_two.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcOneImage.equals("1")){
                        service_cat_img_one.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcTwoImage.equals("1")){
                        service_cat_img_two.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcThreeImage.equals("1")){
                        service_cat_img_three.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcfourImage.equals("1")){
                        service_cat_img_four.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcFiveImage.equals("1")){
                        service_cat_img_five.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(slctCatOneImage.equals("1")){
                        slctCatOneImage="0";
                    }
                    if(slctCatTwoImage.equals("1")){
                        slctCatTwoImage="0";
                    }
                    if(slctServcOneImage.equals("1")){
                        slctServcOneImage="0";
                    }
                    if(slctServcTwoImage.equals("1")){
                        slctServcTwoImage="0";
                    }
                    if(slctServcThreeImage.equals("1")){
                        slctServcThreeImage="0";
                    }
                    if(slctServcfourImage.equals("1")){
                        slctServcfourImage="0";
                    }
                    if(slctServcFiveImage.equals("1")){
                        slctServcFiveImage="0";
                    }
                    Toast.makeText(UpdateProfileActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(UpdateProfileActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }

        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            if(slctCatOneImage.equals("1")){
                catfile1 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                catfile1.createNewFile();
                FileOutputStream fo = new FileOutputStream(catfile1);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{catfile1.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + catfile1.getAbsolutePath());
                UploadImages(catfile1);
                return catfile1.getAbsolutePath();
            }
            if(slctCatTwoImage.equals("1")){
                catfile2 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                catfile2.createNewFile();
                FileOutputStream fo = new FileOutputStream(catfile2);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{catfile2.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + catfile2.getAbsolutePath());
                UploadImages(catfile2);
                return catfile2.getAbsolutePath();
            }
            if(slctServcOneImage.equals("1")){
                srvcfile1 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                srvcfile1.createNewFile();
                FileOutputStream fo = new FileOutputStream(srvcfile1);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{srvcfile1.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + srvcfile1.getAbsolutePath());
                UploadImages(srvcfile1);
                return srvcfile1.getAbsolutePath();
            }
            if(slctServcTwoImage.equals("1")){
                srvcfile2 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                srvcfile2.createNewFile();
                FileOutputStream fo = new FileOutputStream(srvcfile2);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{srvcfile2.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + srvcfile2.getAbsolutePath());
                UploadImages(srvcfile2);
                return srvcfile2.getAbsolutePath();
            }
            if(slctServcThreeImage.equals("1")){
                srvcfile3 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                srvcfile3.createNewFile();
                FileOutputStream fo = new FileOutputStream(srvcfile3);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{srvcfile3.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + srvcfile3.getAbsolutePath());
                UploadImages(srvcfile3);
                return srvcfile3.getAbsolutePath();
            }
            if(slctServcfourImage.equals("1")){
                srvcfile4 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                srvcfile4.createNewFile();
                FileOutputStream fo = new FileOutputStream(srvcfile4);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{srvcfile4.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + srvcfile4.getAbsolutePath());
                UploadImages(srvcfile4);
                return srvcfile4.getAbsolutePath();
            }
            if(slctServcFiveImage.equals("1")){
                srvcfile5 = new File(wallpaperDirectory, Calendar.getInstance()
                        .getTimeInMillis() + ".jpg");
                srvcfile5.createNewFile();
                FileOutputStream fo = new FileOutputStream(srvcfile5);
                fo.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{srvcfile5.getPath()},
                        new String[]{"image/jpeg"}, null);
                fo.close();
                Log.d("TAG", "File Saved::---&gt;" + srvcfile5.getAbsolutePath());
                UploadImages(srvcfile5);
                return srvcfile5.getAbsolutePath();
            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void UploadImages(File absolutePath) {
        MultipartBody.Part userDpFilePart = null;
        if (absolutePath != null) {
            RequestBody userDpFile = RequestBody.create(MediaType.parse("image/*"), absolutePath);
            userDpFilePart = MultipartBody.Part.createFormData("file", absolutePath.getName(), userDpFile);
        }

        ApiService<ImageResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().uploadImages(Config.token,userDpFilePart), "UploadDocument");
        Log.e("DATALOG","check1=> "+service);

    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(UpdateProfileActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        Log.d("kkdkkd",""+key);
        switch (key)
        {

            case "GetUserDetails":
                try {
                    Log.d("GetUserDetails",response.body().toString());
                    UserResponse userResponse = (UserResponse) response.body();
                    int responseCode = Integer.parseInt(userResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                       // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

                        last_name_updt.setText(userResponse.getData().getLastName());
                        email_updt.setText(userResponse.getData().getEmail());
                        phone_updt.setText(userResponse.getData().getPhone());
                        address_updt.setText(userResponse.getData().getAddress());
                        postal_code_updt.setText(userResponse.getData().getPostalCode());
                        clinic_name_updt.setText(userResponse.getData().getCompany());
                        description_updt.setText(userResponse.getData().getDescription());


                    }else if (responseCode==614){
                        Toast.makeText(this, userResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            case "GetState":
                try {
                    Log.d("getState",response.body().toString());
                    StateResponse stateResponse = (StateResponse) response.body();
                    int responseCode = Integer.parseInt(stateResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        state=new ArrayList<>();
                        state.add("Select State");
                        stateHasmap.put("Select State","0.0");
                        for(int i=0; i<stateResponse.getData().size(); i++){
                            Log.d("kakakka",""+stateResponse.getData().get(i).getStateName());
                            state.add(stateResponse.getData().get(i).getStateName());
                            stateHasmap.put(stateResponse.getData().get(i).getStateName(),stateResponse.getData().get(i).getId());
                        }
                        setStateSpinner();

                    }else if (responseCode==614){
                        Toast.makeText(UpdateProfileActivity.this, stateResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateProfileActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetCountry":
                try {
                    Log.d("GetCountry",response.body().toString());
                    stateResponse = (CountryResponse) response.body();
                    int responseCode = Integer.parseInt(stateResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        countery=new ArrayList<>();
                        countery.add("Select Country");
                        for(int i=0; i<stateResponse.getData().size(); i++){
                            Log.d("kakakka",""+stateResponse.getData().get(i).getCountryName());
                            countery.add(stateResponse.getData().get(i).getCountryName());
                            countryHasmap.put(stateResponse.getData().get(i).getCountryName(),stateResponse.getData().get(i).getId());
                        }
                        setCountrySpinner();

                    }else if (responseCode==614){
                        Toast.makeText(UpdateProfileActivity.this, stateResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateProfileActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetCity":
                try {
                    Log.d("GetCity",response.body().toString());
                    CityResponse cityResponse = (CityResponse) response.body();
                    int responseCode = Integer.parseInt(cityResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        city=new ArrayList<>();
                        city.add("Select City");
                        Log.d("lalal",""+cityResponse.getData().size());
                        for(int i=0; i<cityResponse.getData().size(); i++){
                            Log.d("kakakkajj",""+cityResponse.getData().get(i).getCity1());
                            city.add(cityResponse.getData().get(i).getCity1());
                            cityHasmap.put(cityResponse.getData().get(i).getCity1(),cityResponse.getData().get(i).getId());
                        }
                        setCitySpinner();

                    }else if (responseCode==614){
                        Toast.makeText(UpdateProfileActivity.this, cityResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateProfileActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetTypes":
                try {
                    Log.d("GetPetTypes",response.body().toString());
                    PetTypeResponse petTypeResponse = (PetTypeResponse) response.body();
                    int responseCode = Integer.parseInt(petTypeResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petCategory=new String[petTypeResponse.getData().size()];
                        Log.d("lalal",""+petTypeResponse.getData().size());
                        for(int i=0; i<petTypeResponse.getData().size(); i++){
                            Log.d("petttt",""+petTypeResponse.getData().get(i).getPetType1());
                            petCategory[i] = petTypeResponse.getData().get(i).getPetType1();
                            categoryHasmap.put(petTypeResponse.getData().get(i).getPetType1(),petTypeResponse.getData().get(i).getId());
                        }
                        chkItems=new boolean[petCategory.length];
                        Log.d("CheckBox chkItems",chkItems.toString());
                        Log.d("CheckBox petCategory ",petCategory.toString());
                        Log.d("CheckBox Hasmap",categoryHasmap.toString());


                    }else if (responseCode==614){
                        Toast.makeText(UpdateProfileActivity.this, petTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateProfileActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetServiceTypes":
                try {
                    Log.d("GetPetServiceTypes",response.body().toString());
                    PetServiceResponse petServiceResponse = (PetServiceResponse) response.body();
                    int responseCode = Integer.parseInt(petServiceResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
//                        Toast.makeText(UpdateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        serviceCategory=new String[petServiceResponse.getData().size()];
                        Log.d("lalal",""+petServiceResponse.getData().size());
                        for(int i=0; i<petServiceResponse.getData().size(); i++){
                            Log.d("petttt",""+petServiceResponse.getData().get(i).getServiceType1());
                            serviceCategory[i] = petServiceResponse.getData().get(i).getServiceType1();
                            servcCatHasmap.put(petServiceResponse.getData().get(i).getServiceType1(),petServiceResponse.getData().get(i).getId());
                        }
                        chkItemsSevice=new boolean[serviceCategory.length];

                    }else if (responseCode==614){
                        Toast.makeText(UpdateProfileActivity.this, petServiceResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateProfileActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "UpdateVeterinarian":
                try {
                    Log.e("update", String.valueOf(response.code()));
                    Log.e("ttttt", response.body().toString());
                    UpdateVetResponse userResponse = (UpdateVetResponse) response.body();
                    int responseCode = Integer.parseInt(userResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(UpdateProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                        if (activityType.equals("Update")){
                            setResult(RESULT_OK);
                            finish();
                        }else if (activityType.equals("Edit")){
                            setResult(RESULT_OK);
                            finish();
                        }
                    }else if (responseCode==614){
                        Toast.makeText(UpdateProfileActivity.this, userResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UpdateProfileActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                    Log.e("error",e.getMessage());
                    Log.e("error",e.getLocalizedMessage());

                }
                break;

            case "UploadDocument":
                try {
                    Log.d("UploadDocument",response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        if(slctCatOneImage.equals("1")){
                            strCatUrl1=imageResponse.getData().getDocumentUrl();
                            slctCatOneImage="0";
                        }
                        if(slctCatTwoImage.equals("1")){
                            strCatUrl2=imageResponse.getData().getDocumentUrl();
                            slctCatTwoImage="0";
                        }
                        if(slctServcOneImage.equals("1")){
                            strSrvsUrl1=imageResponse.getData().getDocumentUrl();
                            slctServcOneImage="0";
                        }
                        if(slctServcTwoImage.equals("1")){
                            strSrvsUrl2=imageResponse.getData().getDocumentUrl();
                            slctServcTwoImage="0";
                        }
                        if(slctServcThreeImage.equals("1")){
                            strSrvsUrl3=imageResponse.getData().getDocumentUrl();
                            slctServcThreeImage="0";
                        }
                        if(slctServcfourImage.equals("1")){
                            strSrvsUrl4=imageResponse.getData().getDocumentUrl();
                            slctServcfourImage="0";
                        }
                        if(slctServcFiveImage.equals("1")){
                            strSrvsUrl5=imageResponse.getData().getDocumentUrl();
                            slctServcFiveImage="0";
                        }

                    }else if (responseCode==614){
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {
        methods.customProgressDismiss();
        Log.e("error",t.getLocalizedMessage());
    }

    private void setCountrySpinner() {
        ArrayAdapter aa = new ArrayAdapter(UpdateProfileActivity.this,android.R.layout.simple_spinner_item,countery);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        country_spnr_updt.setAdapter(aa);
        if (!strContrySpnr.equals("")) {
            int spinnerPosition = aa.getPosition(strContrySpnr);
            country_spnr_updt.setSelection(spinnerPosition);
        }
        country_spnr_updt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strContrySpnr=item;
                Log.d("spnrCountry",""+strContrySpnr);
                strCountryId=countryHasmap.get(strContrySpnr);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setStateSpinner() {
        ArrayAdapter aa = new ArrayAdapter(UpdateProfileActivity.this,android.R.layout.simple_spinner_item,state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        state_spnr_updt.setAdapter(aa);
        if (!strStateSpnr.equals("")) {
            int spinnerPosition = aa.getPosition(strStateSpnr);
            state_spnr_updt.setSelection(spinnerPosition);
        }
        state_spnr_updt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strStateSpnr=item;
                strStateId=stateHasmap.get(strStateSpnr);
//                Log.d("spnrState",""+strStateSpnr+"ID=>>>"+strStateId);

                if (strStateId.equals(null)){

                }else {
                    Log.d("spnrState",""+strStateSpnr+" ID=>>>"+strStateId);
                    Log.d("spnrState",""+strStateSpnr+  " ID=>>>"+strStateId.substring(0,strStateId.length()-1));
                    String stateId= strStateId.substring(0,strStateId.length()-2);
                    String url  = "common/GetCity/"+stateId;
                    Log.e("URL",url);
                    getCity(url);
                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setCitySpinner() {
        ArrayAdapter aa = new ArrayAdapter(UpdateProfileActivity.this,android.R.layout.simple_spinner_item,city);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner

        city_spnr_updt.setAdapter(aa);
        if (!strCitySpnr.equals("")) {
            int spinnerPosition = aa.getPosition(strCitySpnr);
            city_spnr_updt.setSelection(spinnerPosition);
        }
        city_spnr_updt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strCitySpnr=item;
                Log.d("spnrCity",""+strCitySpnr);
                strStringCityId=cityHasmap.get(strCitySpnr);

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}