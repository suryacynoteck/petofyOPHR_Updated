package com.cynoteck.petofyvet.fragments;

import android.Manifest;
import android.app.AlertDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.response.updateProfileResponse.CityResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.CountryResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetServiceResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.StateResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener,ApiResponse  {
    TextInputEditText first_name_updt,last_name_updt,email_updt,phone_updt,address_updt,
            postal_code_updt,website_updt,social_media_url_updt,registration_num_updt,
            vet_qualification_updt;
    Spinner country_spnr_updt,state_spnr_updt,city_spnr_updt;
    Button update_profile,select_Category,select_service_Category;
    TextView pet_category_updt,service_category_updt;
    ImageView category_img_one,category_img_two,service_cat_img_one,service_cat_img_two,
            service_cat_img_three,service_cat_img_four,service_cat_img_five ;

    TextInputLayout first_nm_layout,last_nm_layout,emil_layout,phone_number_layout,address_layout,postal_code_layout,
            website_layout,social_media_url_layout,vet_registration_layout,vet_qualification_layout;

    int slctCatOneImage=0,slctCatTwoImage=0,slctServcOneImage=0,slctServcTwoImage=0,slctServcThreeImage=0,
            slctServcfourImage=0,slctServcFiveImage=0,spnrCountry=0,spnrState=0,spnrCity=0;

    String imagename,strFirstNm="",strLstNm="",strEmlUpdt="",strPhUpdt="",strAddrsUpdt="",strPostlUpdt="",
            strWbUpdt="",strSoclMdUelUpdt="",strRegistNumUpdt="",strVetQulafctnUpdt="",strPetCatUpdt="",
            strSrvcCatUpdt="",strContrySpnr="",strStateSpnr="",strCitySpnr="";
    Uri fileUri;
    Methods methods;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    View view;

    private static final String IMAGE_DIRECTORY = "/Picture";
    private int GALLERY = 1, CAMERA = 2;
    File file = null;
    Bitmap bitmap, thumbnail;
    String capImage;


    ArrayList<String>state;
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

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        methods = new Methods(getActivity());
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

        return view;
    }

    private void getState() {
        methods.showCustomProgressBarDialog(getContext());
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
        first_nm_layout=view.findViewById(R.id.first_nm_layout);
        last_nm_layout=view.findViewById(R.id.last_nm_layout);
        emil_layout=view.findViewById(R.id.emil_layout);
        phone_number_layout=view.findViewById(R.id.phone_number_layout);
        address_layout=view.findViewById(R.id.address_layout);
        postal_code_layout=view.findViewById(R.id.postal_code_layout);
        website_layout=view.findViewById(R.id.website_layout);
        social_media_url_layout=view.findViewById(R.id.social_media_url_layout);
        vet_registration_layout=view.findViewById(R.id.vet_registration_layout);
        vet_qualification_layout=view.findViewById(R.id.vet_qualification_layout);


        //TextInputEditText
        first_name_updt=view.findViewById(R.id.first_name_updt);
        last_name_updt=view.findViewById(R.id.last_name_updt);
        email_updt=view.findViewById(R.id.email_updt);
        phone_updt=view.findViewById(R.id.phone_updt);
        address_updt=view.findViewById(R.id.address_updt);
        postal_code_updt=view.findViewById(R.id.postal_code_updt);
        website_updt=view.findViewById(R.id.website_updt);
        social_media_url_updt=view.findViewById(R.id.social_media_url_updt);
        registration_num_updt=view.findViewById(R.id.registration_num_updt);
        vet_qualification_updt=view.findViewById(R.id.vet_qualification_updt);

        //Spinner
        country_spnr_updt=view.findViewById(R.id.country_spnr_updt);
        state_spnr_updt=view.findViewById(R.id.state_spnr_updt);
        city_spnr_updt=view.findViewById(R.id.city_spnr_updt);

        //Text View
        pet_category_updt=view.findViewById(R.id.pet_category_updt);
        service_category_updt=view.findViewById(R.id.service_category_updt);

        //Image View
        category_img_one=view.findViewById(R.id.category_img_one);
        category_img_two=view.findViewById(R.id.category_img_two);
        service_cat_img_one=view.findViewById(R.id.service_cat_img_one);
        service_cat_img_two=view.findViewById(R.id.service_cat_img_two);
        service_cat_img_three=view.findViewById(R.id.service_cat_img_three);
        service_cat_img_four=view.findViewById(R.id.service_cat_img_four);
        service_cat_img_five=view.findViewById(R.id.service_cat_img_five);

        category_img_one.setOnClickListener(this);
        category_img_two.setOnClickListener(this);
        service_cat_img_one.setOnClickListener(this);
        service_cat_img_two.setOnClickListener(this);
        service_cat_img_three.setOnClickListener(this);
        service_cat_img_four.setOnClickListener(this);
        service_cat_img_five.setOnClickListener(this);

        //Button
        update_profile=view.findViewById(R.id.update_profile);
        select_Category=view.findViewById(R.id.select_Category);
        select_service_Category=view.findViewById(R.id.select_service_Category);

        update_profile.setOnClickListener(this);
        select_Category.setOnClickListener(this);
        select_service_Category.setOnClickListener(this);

    }

    private void setValueFromSharePref() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdetails", 0);
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
                else if(strWbUpdt.isEmpty())
                {
                    pet_category_updt.setError(null);
                    service_category_updt.setError(null);
                    first_nm_layout.setError(null);
                    last_nm_layout.setError(null);
                    emil_layout.setError(null);
                    phone_number_layout.setError(null);
                    address_layout.setError(null);
                    postal_code_layout.setError(null);
                    website_layout.setError("Website empty");
                    social_media_url_layout.setError(null);
                    vet_registration_layout.setError(null);
                    vet_qualification_layout.setError(null);
                }
                else if(strSoclMdUelUpdt.isEmpty())
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
                    social_media_url_layout.setError("Social Media URL empty");
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
                    pet_category_updt.setError("Email is empty");
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
                    Toast.makeText(getActivity(), "Select City!!", Toast.LENGTH_SHORT).show();
                }
                else if(strStateSpnr.isEmpty()||(strStateSpnr.equals("Select State")))
                {
                    Toast.makeText(getActivity(), "Select State!!", Toast.LENGTH_SHORT).show();
                }
                else if(strContrySpnr.isEmpty()||(strContrySpnr.equals("Select Country")))
                {
                    Toast.makeText(getActivity(), "Select Country!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //All data update api call from here.
                    Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.select_Category:
                AlertDialog.Builder mBuilder=new AlertDialog.Builder(getActivity());
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
                            if(i != muserItem.size()-1);{
                                item=item+", ";
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
                        }
                    }
                });

                AlertDialog mDialog=mBuilder.create();
                mDialog.show();
                break;

            case R.id.select_service_Category:
                AlertDialog.Builder mBuilderr=new AlertDialog.Builder(getActivity());
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
                            if(i != muserItemService.size()-1);{
                                item=item+", ";
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
        }

    }

    private void showPictureDialog() {
        final AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera",
                "Cancel Dialog"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                        }
                    }
                });
        pictureDialog.show();
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
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
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
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {

            if (data.getData() == null)
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl",""+thumbnail);
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(slctCatOneImage==1){
                category_img_one.setImageBitmap(thumbnail);
                slctCatOneImage=0;
                saveImage(thumbnail);
            }
            if(slctCatTwoImage==1){
                category_img_two.setImageBitmap(thumbnail);
                slctCatTwoImage=0;
                saveImage(thumbnail);
            }
            if(slctServcOneImage==1){
                service_cat_img_one.setImageBitmap(thumbnail);
                slctServcOneImage=0;
                saveImage(thumbnail);
            }
            if(slctServcTwoImage==1){
                service_cat_img_two.setImageBitmap(thumbnail);
                slctServcTwoImage=0;
                saveImage(thumbnail);
            }
            if(slctServcThreeImage==1){
                service_cat_img_three.setImageBitmap(thumbnail);
                slctServcThreeImage=0;
                saveImage(thumbnail);
            }
            if(slctServcfourImage==1){
                service_cat_img_four.setImageBitmap(thumbnail);
                slctServcfourImage=0;
                saveImage(thumbnail);
            }
            if(slctServcFiveImage==1){
                service_cat_img_five.setImageBitmap(thumbnail);
                slctServcFiveImage=0;
                saveImage(thumbnail);
            }
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
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
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(getActivity())
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
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
                        }
                        setStateSpinner();

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), stateResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        }
                        setCountrySpinner();

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), stateResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        }
                        setCitySpinner();

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), cityResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        }
                        chkItems=new boolean[petCategory.length];

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), petTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        serviceCategory=new String[petServiceResponse.getData().size()];
                        Log.d("lalal",""+petServiceResponse.getData().size());
                        for(int i=0; i<petServiceResponse.getData().size(); i++){
                            Log.d("petttt",""+petServiceResponse.getData().get(i).getServiceType1());
                            serviceCategory[i] = petServiceResponse.getData().get(i).getServiceType1();
                        }
                        chkItemsSevice=new boolean[serviceCategory.length];

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), petServiceResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
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
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,countery);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        country_spnr_updt.setAdapter(aa);
        country_spnr_updt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strContrySpnr=item;
                Log.d("spnrCountry",""+strContrySpnr);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setStateSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        state_spnr_updt.setAdapter(aa);
        state_spnr_updt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strStateSpnr=item;
                Log.d("spnrState",""+strStateSpnr);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setCitySpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,city);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        city_spnr_updt.setAdapter(aa);
        city_spnr_updt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strCitySpnr=item;
                Log.d("spnrCity",""+strCitySpnr);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
