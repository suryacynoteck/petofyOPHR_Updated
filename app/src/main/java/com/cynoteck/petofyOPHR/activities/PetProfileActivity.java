package com.cynoteck.petofyOPHR.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.fragments.AllStaffFragment;
import com.cynoteck.petofyOPHR.fragments.AppointementFragment;
import com.cynoteck.petofyOPHR.params.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyOPHR.params.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyOPHR.response.getPetDetailsResponse.GetPetResponse;
import com.cynoteck.petofyOPHR.response.loginRegisterResponse.UserPermissionMasterList;
import com.cynoteck.petofyOPHR.response.staffPermissionListResponse.CheckStaffPermissionResponse;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.Methods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Response;

public class PetProfileActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {
    Methods methods;
    String petId="",imagerl="";
    ImageView pet_profile_image_IV, image_one,image_two,image_three,image_four,image_five,edit_image,back_arrow_IV;
    TextView address_head,pet_name_TV, pet_sex_TV,pet_parent_TV,pet_id_TV,pet_deatils_TV,phone_one,pet_email_id_TV,phone_two,address_line_one_TV,address_line_two_TV;
    GetPetResponse getPetResponse;
    boolean reloadData=false;
    SharedPreferences sharedPreferences;
    String permissionId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        methods = new Methods(this);
        Bundle extras = getIntent().getExtras();
        petId = extras.getString("pet_id");

        pet_profile_image_IV=findViewById(R.id.pet_profile_image_IV);
        image_one=findViewById(R.id.image_one);
        image_two=findViewById(R.id.image_two);
        image_three=findViewById(R.id.image_three);
        image_four=findViewById(R.id.image_four);
        image_five=findViewById(R.id.image_five);
        pet_name_TV=findViewById(R.id.pet_name_TV);
        pet_sex_TV=findViewById(R.id.pet_sex_TV);
        pet_parent_TV=findViewById(R.id.pet_parent_TV);
        pet_id_TV=findViewById(R.id.pet_id_TV);
        pet_deatils_TV=findViewById(R.id.pet_deatils_TV);
        phone_one=findViewById(R.id.phone_one);
        address_head=findViewById(R.id.address_head);
        pet_email_id_TV=findViewById(R.id.pet_email_id_TV);
        phone_two=findViewById(R.id.phone_two);
        address_line_one_TV=findViewById(R.id.address_line_one_TV);
        address_line_two_TV=findViewById(R.id.address_line_two_TV);
        edit_image=findViewById(R.id.edit_image);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);

        edit_image.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);

        GetPetListParams getPetListParams = new GetPetListParams();
        getPetListParams.setId(petId);
        GetPetListRequest getPetListRequest = new GetPetListRequest();
        getPetListRequest.setData(getPetListParams);
        sharedPreferences = getSharedPreferences("userdetails", 0);

        if(methods.isInternetOn())
        {
            getPetlistData(getPetListRequest);
        }
        else
        {
            methods.DialogInternet();
        }

    }

    private void getPetlistData(GetPetListRequest getPetListRequest) {
        reloadData=true;
        methods.showCustomProgressBarDialog(this);
        ApiService<GetPetResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetDetails(Config.token,getPetListRequest), "GetPetDetail");
        Log.e("DATALOG","check1=> "+methods.getRequestJson(getPetListRequest));

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.edit_image:
                String userTYpe = sharedPreferences.getString("user_type", "");
                if (userTYpe.equals("Vet Staff")){
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("userPermission", null);
                    Type type = new TypeToken<ArrayList<UserPermissionMasterList>>() {}.getType();
                    ArrayList<UserPermissionMasterList> arrayList = gson.fromJson(json, type);
                    Log.e("ArrayList",arrayList.toString());
                    Log.d("UserType",userTYpe);
                    permissionId = arrayList.get(1).getPermissionCode();
                    methods.showCustomProgressBarDialog(this);
                    String url  = "user/CheckStaffPermission/"+permissionId;
                    Log.e("URL",url);
                    ApiService<CheckStaffPermissionResponse> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().getCheckStaffPermission(Config.token,url), "CheckPermission");
                }else if (userTYpe.equals("Veterinarian")){
                    Intent intent=new Intent(this, GetPetDetailsActivity.class);
                    intent.putExtra("pet_id",petId);
                    intent.putExtra("pet_category",getPetResponse.getData().getPetCategory());
                    intent.putExtra("pet_name",getPetResponse.getData().getPetName());
                    intent.putExtra("pet_sex",getPetResponse.getData().getPetSex());
                    intent.putExtra("pet_DOB",getPetResponse.getData().getDateOfBirth());
                    intent.putExtra("pet_age",getPetResponse.getData().getPetAge());
                    intent.putExtra("pet_size",getPetResponse.getData().getPetSize());
                    intent.putExtra("pet_breed",getPetResponse.getData().getPetBreed());
                    intent.putExtra("pet_color",getPetResponse.getData().getPetColor());
                    intent.putExtra("pet_parent",getPetResponse.getData().getPetParentName());
                    intent.putExtra("pet_parent_contact",getPetResponse.getData().getContactNumber());
                    intent.putExtra("image_url",getPetResponse.getData().getPetProfileImageUrl());
                    startActivityForResult(intent,1);
                }

                break;

        }

    }

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();
        reloadData=false;
        switch (key) {
            case "CheckPermission":
                try {
                    methods.customProgressDismiss();
                    CheckStaffPermissionResponse checkStaffPermissionResponse = (CheckStaffPermissionResponse) arg0.body();
                    Log.d("GetPetList", checkStaffPermissionResponse.toString());
                    int responseCode = Integer.parseInt(checkStaffPermissionResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        if (checkStaffPermissionResponse.getData().equals("true")){
                            if (permissionId.equals("2")){
                                Intent intent=new Intent(this, GetPetDetailsActivity.class);
                                intent.putExtra("pet_id",petId);
                                intent.putExtra("pet_category",getPetResponse.getData().getPetCategory());
                                intent.putExtra("pet_name",getPetResponse.getData().getPetName());
                                intent.putExtra("pet_sex",getPetResponse.getData().getPetSex());
                                intent.putExtra("pet_DOB",getPetResponse.getData().getDateOfBirth());
                                intent.putExtra("pet_age",getPetResponse.getData().getPetAge());
                                intent.putExtra("pet_size",getPetResponse.getData().getPetSize());
                                intent.putExtra("pet_breed",getPetResponse.getData().getPetBreed());
                                intent.putExtra("pet_color",getPetResponse.getData().getPetColor());
                                intent.putExtra("pet_parent",getPetResponse.getData().getPetParentName());
                                intent.putExtra("pet_parent_contact",getPetResponse.getData().getContactNumber());
                                intent.putExtra("image_url",getPetResponse.getData().getPetProfileImageUrl());
                                startActivityForResult(intent,1);
                            }
                        }else {
                            Toast.makeText(this, "Permission not Granted!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;


            case "GetPetDetail":
                try {
                    methods.customProgressDismiss();
                    Log.d("GetPetDetail", arg0.body().toString());
                    getPetResponse = (GetPetResponse) arg0.body();
                    int responseCode = Integer.parseInt(getPetResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                       // Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        pet_name_TV.setText(getPetResponse.getData().getPetName().substring(0, 1).toUpperCase() + getPetResponse.getData().getPetName().substring(1));
                        pet_parent_TV.setText(getPetResponse.getData().getPetParentName());
                        phone_one.setText(getPetResponse.getData().getContactNumber());
                        pet_sex_TV.setText(getPetResponse.getData().getPetSex());
                        pet_id_TV.setText(getPetResponse.getData().getPetUniqueId());
                        pet_deatils_TV.setText(getPetResponse.getData().getDescription());
//                        pet_email_id_TV.setText(getPetResponse.getData().getpetpa);
                        if (getPetResponse.getData().getAddress()==null){
                            address_line_one_TV.setVisibility(View.GONE);
                            address_head.setVisibility(View.GONE);
                        }else {
                            address_line_one_TV.setText(getPetResponse.getData().getAddress());
                        }
                        setImages();

                    } else if (responseCode == 614) {
                        Toast.makeText(this, getPetResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void setImages() {
        if(!getPetResponse.getData().getPetProfileImageUrl().equals(""))
        {
            Glide.with(this)
                    .load(getPetResponse.getData().getPetProfileImageUrl())
                    .placeholder(R.drawable.petofy)
                    .into(pet_profile_image_IV);
        }
//        if (getPetResponse.getData().getFirstServiceImageUrl().isEmpty()){
//            image_one.setVisibility(View.INVISIBLE);
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getFirstServiceImageUrl())
//                    .into(image_one);
//        }
//        if (getPetResponse.getData().getSecondServiceImageUrl().isEmpty()){
//            image_two.setVisibility(View.INVISIBLE);
//
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getSecondServiceImageUrl())
//                    .into(image_two);
//        }if (getPetResponse.getData().getThirdServiceImageUrl().isEmpty()){
//            image_three.setVisibility(View.INVISIBLE);
//
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getThirdServiceImageUrl())
//                    .into(image_three);
//        }if (getPetResponse.getData().getFourthServiceImageUrl().isEmpty()){
//            image_four.setVisibility(View.INVISIBLE);
//
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getFourthServiceImageUrl())
//                    .into(image_four);
//        }if (getPetResponse.getData().getFifthServiceImageUrl().isEmpty()){
//            image_five.setVisibility(View.INVISIBLE);
//        }else {
//            Glide.with(this)
//                    .load(getPetResponse.getData().getFifthServiceImageUrl())
//                    .into(image_five);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){
            if (resultCode==RESULT_OK){
                GetPetListParams getPetListParams = new GetPetListParams();
                getPetListParams.setId(petId);
                GetPetListRequest getPetListRequest = new GetPetListRequest();
                getPetListRequest.setData(getPetListParams);
                if(methods.isInternetOn())
                {
                    getPetlistData(getPetListRequest);
                }
                else
                {
                    methods.DialogInternet();
                }
            }
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(reloadData==false)
        {
            GetPetListParams getPetListParams = new GetPetListParams();
            getPetListParams.setId(petId);
            GetPetListRequest getPetListRequest = new GetPetListRequest();
            getPetListRequest.setData(getPetListParams);
            if(methods.isInternetOn())
            {
                getPetlistData(getPetListRequest);
            }
            else
            {
                methods.DialogInternet();
            }
        }

    }
}
