package com.cynoteck.petofyvet.activities;

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
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.updateRequest.getValue.updateParams;
import com.cynoteck.petofyvet.params.updateRequest.getValue.updateRequest;
import com.cynoteck.petofyvet.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.CityResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.CountryResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetServiceResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.StateResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.UserResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
    TextInputEditText first_name_updt,last_name_updt,email_updt,phone_updt,address_updt,
            postal_code_updt,website_updt,social_media_url_updt,registration_num_updt,
            vet_qualification_updt;
    Spinner country_spnr_updt,state_spnr_updt,city_spnr_updt;
    Button update_profile,select_Category,select_service_Category;
    TextView pet_category_updt,service_category_updt;
    ImageView category_img_one,category_img_two,service_cat_img_one,service_cat_img_two,
            service_cat_img_three,service_cat_img_four,service_cat_img_five,cancel ;

    TextInputLayout first_nm_layout,last_nm_layout,emil_layout,phone_number_layout,address_layout,postal_code_layout,
            website_layout,social_media_url_layout,vet_registration_layout,vet_qualification_layout;

    int slctCatOneImage=0,slctCatTwoImage=0,slctServcOneImage=0,slctServcTwoImage=0,slctServcThreeImage=0,
            slctServcfourImage=0,slctServcFiveImage=0,spnrCountry=0,spnrState=0,spnrCity=0;

    String imagename,strFirstNm="",strLstNm="",strEmlUpdt="",strPhUpdt="",strAddrsUpdt="",strPostlUpdt="",
            strWbUpdt="",strSoclMdUelUpdt="",strRegistNumUpdt="",strVetQulafctnUpdt="",strPetCatUpdt="",
            strSrvcCatUpdt="",strContrySpnr="",strStateSpnr="",strCitySpnr="",strCountryId="",strStringCityId="",
            strStateId="",strCatId="",strSrvsCatId="",strCatUrl1="",strCatUrl2="",strSrvsUrl1="",strSrvsUrl2,
            strSrvsUrl3,strSrvsUrl4,strSrvsUrl5;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        methods = new Methods(this);
        init();
        setValueFromSharePref();
        requestMultiplePermissions();
        if(methods.isInternetOn())
        {
            getState();
            getCountry();
            getCity();
            petType();
            petServiceType();

        }
        else
        {
            methods.DialogInternet();
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

    private void getCity(){
        ApiService<CityResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getCityApi(), "GetCity");
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
        //TextInputLayout
        first_nm_layout=findViewById(R.id.first_nm_layout);
        last_nm_layout=findViewById(R.id.last_nm_layout);
        emil_layout=findViewById(R.id.emil_layout);
        phone_number_layout=findViewById(R.id.phone_number_layout);
        address_layout=findViewById(R.id.address_layout);
        postal_code_layout=findViewById(R.id.postal_code_layout);
        website_layout=findViewById(R.id.website_layout);
        social_media_url_layout=findViewById(R.id.social_media_url_layout);
        vet_registration_layout=findViewById(R.id.vet_registration_layout);
        vet_qualification_layout=findViewById(R.id.vet_qualification_layout);


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

        //Spinner
        country_spnr_updt=findViewById(R.id.country_spnr_updt);
        state_spnr_updt=findViewById(R.id.state_spnr_updt);
        city_spnr_updt=findViewById(R.id.city_spnr_updt);

        //Text View
        pet_category_updt=findViewById(R.id.pet_category_updt);
        service_category_updt=findViewById(R.id.service_category_updt);

        //Image View
        category_img_one=findViewById(R.id.category_img_one);
        category_img_two=findViewById(R.id.category_img_two);
        service_cat_img_one=findViewById(R.id.service_cat_img_one);
        service_cat_img_two=findViewById(R.id.service_cat_img_two);
        service_cat_img_three=findViewById(R.id.service_cat_img_three);
        service_cat_img_four=findViewById(R.id.service_cat_img_four);
        service_cat_img_five=findViewById(R.id.service_cat_img_five);
        cancel=findViewById(R.id.cancel);

        category_img_one.setOnClickListener(this);
        category_img_two.setOnClickListener(this);
        service_cat_img_one.setOnClickListener(this);
        service_cat_img_two.setOnClickListener(this);
        service_cat_img_three.setOnClickListener(this);
        service_cat_img_four.setOnClickListener(this);
        service_cat_img_five.setOnClickListener(this);
        cancel.setOnClickListener(this);

        //Button
        update_profile=findViewById(R.id.update_profile);
        select_Category=findViewById(R.id.select_Category);
        select_service_Category=findViewById(R.id.select_service_Category);

        update_profile.setOnClickListener(this);
        select_Category.setOnClickListener(this);
        select_service_Category.setOnClickListener(this);

    }

    private void setValueFromSharePref() {
        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        String email = sharedPreferences.getString("email", "");
        if(!email.equals("null"))
            email_updt.setText(email);
        String firstName=sharedPreferences.getString("firstName", "");
        if(!firstName.equals("null"))
            first_name_updt.setText(firstName);
        String lastName=sharedPreferences.getString("lastName", "");
        if(!lastName.equals("null"))
            last_name_updt.setText(lastName);
        String phoneNumber=sharedPreferences.getString("phoneNumber", "");
        if(!phoneNumber.equals("null"))
            phone_updt.setText(phoneNumber);
        String address=sharedPreferences.getString("address","");
        if(!address.equals("null"))
            address_updt.setText(address);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

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
                strPetCatUpdt = pet_category_updt.getText().toString().trim();
                strSrvcCatUpdt = service_category_updt.getText().toString().trim();



                if(strFirstNm.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError("Name is empty");
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strLstNm.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError("Last Name is empty");
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strEmlUpdt.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError("Email Id is Empty");
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if (!strEmlUpdt.matches(emailPattern))
                {
                    emil_layout.setError("Invalid Email");
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strPhUpdt.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError("Phone number is empty");
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strAddrsUpdt.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError("Address is empty");
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strPostlUpdt.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError("Postal code is empty");
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strRegistNumUpdt.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError("Registration no empty");
                    vet_qualification_layout.setError(null);
                }
                else if(strVetQulafctnUpdt.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError("Qualification empty");
                }
                else if(strPetCatUpdt.isEmpty()||(strPetCatUpdt.equals("Set Category")))
                {
                    pet_category_updt.setError("Set Category");
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strSrvcCatUpdt.isEmpty()||(strSrvcCatUpdt.equals("Set Services")))
                {
                    service_category_updt.setError("Set Services");
                    pet_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strCitySpnr.isEmpty()||(strCitySpnr.equals("Select City")))
                {
                    Toast.makeText(this, "Select City!!", Toast.LENGTH_SHORT).show();
                }
                else if(strStateSpnr.isEmpty()||(strStateSpnr.equals("Select State")))
                {
                    Toast.makeText(this, "Select State!!", Toast.LENGTH_SHORT).show();
                }
                else if(strContrySpnr.isEmpty()||(strContrySpnr.equals("Select Country")))
                {
                    Toast.makeText(this, "Select Country!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //All data update api call from here.
                    Log.d("hhjshhjsh",""+methods.removeLastElement(strCatId));
                    Log.d("hhjshhjshhhhhh",""+methods.removeLastElement(strSrvsCatId));
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
                    service_category_updt.setError(null);
                    pet_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError(null);
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                    updateParams updateParams = new updateParams();
                    updateRequest data = new updateRequest();
                    data.setId(Config.user_id);
                    data.setName(strFirstNm+" "+strLstNm);
                    data.setFirstName(strFirstNm);
                    data.setLastName(strLstNm);
                    data.setPassword("pass@123");
                    data.setConfirmPassword("pass@123");
                    data.setCompany("null");
                    data.setDescription("null");
                    data.setPhone(strPhUpdt);
                    data.setPhone2("null");
                    data.setEmail(strEmlUpdt);
                    data.setAddress(strAddrsUpdt);
                    data.setAddress2("null");
                    data.setWebsite(strWbUpdt);
                    data.setSocialMediaUrl(strSoclMdUelUpdt);
                    data.setCityId(strStringCityId);
                    data.setStateId(strStateId);
                    data.setCountryId(strCountryId);
                    data.setPostalCode(strPostlUpdt);
                    data.setSelectedPetTypeIds(methods.removeLastElement(strCatId));
                    data.setSelectedServiceTypeIds(methods.removeLastElement(strSrvsCatId));
                    data.setSelectedCountryId(strCountryId);
                    data.setProfileImageUrl(strCatUrl1);
                    data.setServiceImageUrl("null");
                    data.setServiceImages("null");
                    data.setFirstServiceImageUrl(strSrvsUrl1);
                    data.setSecondServiceImageUrl(strSrvsUrl2);
                    data.setThirdServiceImageUrl(strSrvsUrl3);
                    data.setFourthServiceImageUrl(strSrvsUrl4);
                    data.setFifthServiceImageUrl(strSrvsUrl5);
                    data.setCoverImageUrl(strCatUrl2);
                    data.setIsVeterinarian("yes");
                    data.setProviderUserId("null");
                    data.setUserEmail(strEmlUpdt);
                    data.setVetQualifications(strVetQulafctnUpdt);
                    data.setVetRegistrationNumber(strRegistNumUpdt);
                    updateParams.setUpdateRequest(data);
                    if(methods.isInternetOn())
                    {
                        updateUser(updateParams);
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
                        }
                        //Log.d("Selected_item_category",""+item);
                        if(item.equals(""))
                        {
                            pet_category_updt.setText("Set Category");
                        }
                        else {
                            pet_category_updt.setText(item);
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
                            pet_category_updt.setText("Set Category");
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
                        }
                        Log.d("Selected_item_category",""+item);
                        if(item.equals("")){
                            service_category_updt.setText("Set Services");
                        }
                        else {
                            service_category_updt.setText(item);
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
                            service_category_updt.setText("Set Sevices");
                        }
                    }
                });

                AlertDialog mDialogg=mBuilderr.create();
                mDialogg.show();
                break;

            case R.id.category_img_one:
                showPictureDialog();
                slctCatOneImage=1;
                break;
            case R.id.category_img_two:
                showPictureDialog();
                slctCatTwoImage=1;
                break;
            case R.id.service_cat_img_one:
                showPictureDialog();
                slctServcOneImage=1;
                break;
            case R.id.service_cat_img_two:
                showPictureDialog();
                slctServcTwoImage=1;
                break;
            case R.id.service_cat_img_three:
                showPictureDialog();
                slctServcThreeImage=1;
                break;
            case R.id.service_cat_img_four:
                showPictureDialog();
                slctServcfourImage=1;
                break;
            case R.id.service_cat_img_five:
                showPictureDialog();
                slctServcFiveImage=1;
                break;
            case R.id.cancel:
                startActivity(new Intent(UpdateProfileActivity.this,DashBoardActivity.class));
                finish();
        }

    }

    private void updateUser(updateParams updateParams) {
        methods.showCustomProgressBarDialog(this);
        ApiService<UserResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().updateUser(Config.token,updateParams), "UpdateVeterinarian");
        Log.e("DATALOG","checkUpdate=> "+updateParams);

    }

    private void showPictureDialog() {
        final Dialog dialog = new Dialog(this);
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
                if(slctCatOneImage==1){
                    slctCatOneImage=0;
                }
                if(slctCatTwoImage==1){
                    slctCatTwoImage=0;
                }
                if(slctServcOneImage==1){
                    slctServcOneImage=0;
                }
                if(slctServcTwoImage==1){
                    slctServcTwoImage=0;
                }
                if(slctServcThreeImage==1){
                    slctServcThreeImage=0;
                }
                if(slctServcfourImage==1){
                    slctServcfourImage=0;
                }
                if(slctServcFiveImage==1){
                    slctServcFiveImage=0;
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
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    if(slctCatOneImage==1){
                        category_img_one.setImageBitmap(bitmap);
                        slctCatOneImage=0;
                        saveImage(bitmap);
                    }
                    if(slctCatTwoImage==1){
                        category_img_two.setImageBitmap(bitmap);
                        slctCatTwoImage=0;
                        saveImage(bitmap);
                    }
                    if(slctServcOneImage==1){
                        service_cat_img_one.setImageBitmap(bitmap);
                        slctServcOneImage=0;
                        saveImage(bitmap);
                    }
                    if(slctServcTwoImage==1){
                        service_cat_img_two.setImageBitmap(bitmap);
                        slctServcTwoImage=0;
                        saveImage(bitmap);
                    }
                    if(slctServcThreeImage==1){
                        service_cat_img_three.setImageBitmap(bitmap);
                        slctServcThreeImage=0;
                        saveImage(bitmap);
                    }
                    if(slctServcfourImage==1){
                        service_cat_img_four.setImageBitmap(bitmap);
                        slctServcfourImage=0;
                        saveImage(bitmap);
                    }
                    if(slctServcFiveImage==1){
                        service_cat_img_five.setImageBitmap(bitmap);
                        slctServcFiveImage=0;
                        saveImage(bitmap);
                    }



                } catch (IOException e) {
                    e.printStackTrace();
                    if(slctCatOneImage==1){
                        slctCatOneImage=0;
                    }
                    if(slctCatTwoImage==1){
                        slctCatTwoImage=0;
                    }
                    if(slctServcOneImage==1){
                        slctServcOneImage=0;
                    }
                    if(slctServcTwoImage==1){
                        slctServcTwoImage=0;
                    }
                    if(slctServcThreeImage==1){
                        slctServcThreeImage=0;
                    }
                    if(slctServcfourImage==1){
                        slctServcfourImage=0;
                    }
                    if(slctServcFiveImage==1){
                        slctServcFiveImage=0;
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
                if(slctCatOneImage==1){
                    category_img_one.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctCatTwoImage==1){
                    category_img_two.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcOneImage==1){
                    service_cat_img_one.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcTwoImage==1){
                    service_cat_img_two.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcThreeImage==1){
                    service_cat_img_three.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcfourImage==1){
                    service_cat_img_four.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
                if(slctServcFiveImage==1){
                    service_cat_img_five.setImageBitmap(thumbnail);
                    saveImage(thumbnail);
                }
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(UpdateProfileActivity.this.getContentResolver(), data.getData());

                    if(slctCatOneImage==1){
                        category_img_one.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctCatTwoImage==1){
                        category_img_two.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcOneImage==1){
                        service_cat_img_one.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcTwoImage==1){
                        service_cat_img_two.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcThreeImage==1){
                        service_cat_img_three.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcfourImage==1){
                        service_cat_img_four.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                    if(slctServcFiveImage==1){
                        service_cat_img_five.setImageBitmap(bitmap);
                        saveImage(bitmap);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if(slctCatOneImage==1){
                        slctCatOneImage=0;
                    }
                    if(slctCatTwoImage==1){
                        slctCatTwoImage=0;
                    }
                    if(slctServcOneImage==1){
                        slctServcOneImage=0;
                    }
                    if(slctServcTwoImage==1){
                        slctServcTwoImage=0;
                    }
                    if(slctServcThreeImage==1){
                        slctServcThreeImage=0;
                    }
                    if(slctServcfourImage==1){
                        slctServcfourImage=0;
                    }
                    if(slctServcFiveImage==1){
                        slctServcFiveImage=0;
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
            if(slctCatOneImage==1){
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
            if(slctCatTwoImage==1){
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
            if(slctServcOneImage==1){
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
            if(slctServcTwoImage==1){
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
            if(slctServcThreeImage==1){
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
            if(slctServcfourImage==1){
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
            if(slctServcFiveImage==1){
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
                            Toast.makeText(UpdateProfileActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
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
        switch (key)
        {
            case "GetState":
                try {
                    Log.d("getState",response.body().toString());
                    StateResponse stateResponse = (StateResponse) response.body();
                    int responseCode = Integer.parseInt(stateResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        state=new ArrayList<>();
                        state.add("Select State");
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
                    CountryResponse stateResponse = (CountryResponse) response.body();
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
                        Toast.makeText(UpdateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
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
                    Log.d("UpdateVeterinarian",response.body().toString());
                    UserResponse petServiceResponse = (UserResponse) response.body();
                    int responseCode = Integer.parseInt(petServiceResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(UpdateProfileActivity.this, "Success", Toast.LENGTH_SHORT).show();
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

            case "UploadDocument":
                try {
                    Log.d("UploadDocument",response.body().toString());
                    ImageResponse imageResponse = (ImageResponse) response.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        if(slctCatOneImage==1){
                            strCatUrl1=imageResponse.getData().getDocumentUrl();
                            slctCatOneImage=0;
                        }
                        if(slctCatTwoImage==1){
                            strCatUrl2=imageResponse.getData().getDocumentUrl();
                            slctCatTwoImage=0;
                        }
                        if(slctServcOneImage==1){
                            strSrvsUrl1=imageResponse.getData().getDocumentUrl();
                            slctServcOneImage=0;
                        }
                        if(slctServcTwoImage==1){
                            strSrvsUrl2=imageResponse.getData().getDocumentUrl();
                            slctServcTwoImage=0;
                        }
                        if(slctServcThreeImage==1){
                            strSrvsUrl3=imageResponse.getData().getDocumentUrl();
                            slctServcThreeImage=0;
                        }
                        if(slctServcfourImage==1){
                            strSrvsUrl4=imageResponse.getData().getDocumentUrl();
                            slctServcfourImage=0;
                        }
                        if(slctServcFiveImage==1){
                            strSrvsUrl5=imageResponse.getData().getDocumentUrl();
                            slctServcFiveImage=0;
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
    public void onError(Throwable t, String key) {methods.customProgressDismiss();}

    private void setCountrySpinner() {
        ArrayAdapter aa = new ArrayAdapter(UpdateProfileActivity.this,android.R.layout.simple_spinner_item,countery);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        country_spnr_updt.setAdapter(aa);
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
        state_spnr_updt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strStateSpnr=item;
                Log.d("spnrState",""+strStateSpnr);
                strStateId=stateHasmap.get(strStateSpnr);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                finishAffinity();
                System.exit(0);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
    }
}