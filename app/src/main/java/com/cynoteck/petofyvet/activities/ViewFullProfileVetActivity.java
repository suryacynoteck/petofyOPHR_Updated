package com.cynoteck.petofyvet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.response.updateProfileResponse.UserResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import retrofit2.Response;

public class ViewFullProfileVetActivity extends AppCompatActivity implements ApiResponse , View.OnClickListener {

    ImageView vet_image_CIV;
    TextView vet_name_TV, vet_office_TV,vet_study_TV, vet_id_TV,vet_details_TV,phone_one,phone_two,vet_email_id_TV,address_line_one_TV,address_line_two_TV,link_one_TV,link_two_TV;
    ImageView image_one, image_two, image_three, image_four,image_five,edit_image;
    SwitchCompat online_switch;
    Methods methods;
    UserResponse userResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_profile_vet);
        methods = new Methods(this);
        inilization();
        setVetInfo();
        switchOnline();
        if (methods.isInternetOn()){
            getUserDetails();
        }else {
            methods.DialogInternet();
        }

    }

    private void switchOnline() {
        online_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Toast.makeText(ViewFullProfileVetActivity.this, "Online", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(ViewFullProfileVetActivity.this, "Offline", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void setVetInfo() {
        Glide.with(this)
                .load(Config.user_Veterian_url)
                .into(vet_image_CIV);
        vet_name_TV.setText(Config.user_Veterian_name);
        vet_email_id_TV.setText(Config.user_Veterian_emial);
        vet_study_TV.setText(Config.user_Veterian_study);
        vet_id_TV.setText(Config.user_Veterian_id);
        if (Config.user_Veterian_online.equals("true")){
            online_switch.setChecked(true);
        }else {
            online_switch.setChecked(false);
        }
    }

    private void getUserDetails() {
        methods.showCustomProgressBarDialog(this);
        ApiService<UserResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getUserDetailsApi(Config.token), "GetUserDetails");
        Log.d("request","getDeatisl");
    }

    private void inilization() {

        vet_image_CIV = findViewById(R.id.vet_image_CIV);
        vet_name_TV = findViewById(R.id.vet_name_TV);
        vet_office_TV = findViewById(R.id.vet_office_TV);
        vet_study_TV = findViewById(R.id.vet_study_TV);
        vet_details_TV = findViewById(R.id.vet_details_TV);
        vet_id_TV = findViewById(R.id.vet_id_TV);
        phone_one = findViewById(R.id.phone_one);
        phone_two = findViewById(R.id.phone_two);
        vet_email_id_TV = findViewById(R.id.vet_email_id_TV);
        address_line_one_TV = findViewById(R.id.address_line_one_TV);
        address_line_two_TV = findViewById(R.id.address_line_two_TV);
        link_one_TV = findViewById(R.id.link_one_TV);
        link_two_TV = findViewById(R.id.link_two_TV);
        image_one = findViewById(R.id.image_one);
        image_two = findViewById(R.id.image_two);
        image_two = findViewById(R.id.image_two);
        image_three = findViewById(R.id.image_three);
        image_four = findViewById(R.id.image_four);
        image_five = findViewById(R.id.image_five);
        online_switch = findViewById(R.id.online_switch);
        edit_image=findViewById(R.id.edit_image);

        edit_image.setOnClickListener(this);
        vet_image_CIV.setOnClickListener(this);
        image_one.setOnClickListener(this);
        image_two.setOnClickListener(this);
        image_three.setOnClickListener(this);
        image_four.setOnClickListener(this);
        image_five.setOnClickListener(this);


    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key){

            case "GetUserDetails":
                try {
                    methods.customProgressDismiss();
                    Log.d("GetUserDetails",response.body().toString());
                    userResponse = (UserResponse) response.body();
                    int responseCode = Integer.parseInt(userResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        vet_office_TV.setText("Office :"+ userResponse.getData().getCompany());
                        vet_details_TV.setText(userResponse.getData().getDescription());
                        phone_one.setText(userResponse.getData().getPhone());
                        if (userResponse.getData().getPhone2().isEmpty()){
                            phone_two.setVisibility(View.GONE);
                        }else {
                            phone_two.setText(userResponse.getData().getPhone2());
                        }
                        link_one_TV.setText(userResponse.getData().getSocialMediaUrl());
                        address_line_one_TV.setText(userResponse.getData().getAddress());
                        address_line_two_TV.setText(userResponse.getData().getAddress2()+", "+userResponse.getData().getPostalCode());
                        edit_image.setVisibility(View.VISIBLE);
                    }else if (responseCode==614){
                        Toast.makeText(this, userResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

        }

    }

    @Override
    public void onError(Throwable t, String key) {
        Log.e("error",t.getLocalizedMessage());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.vet_image_CIV:



                break;

            case R.id.image_one:


                break;

            case R.id.image_two:


                break;

            case R.id.image_three:


                break;

            case R.id.image_four:


                break;

            case R.id.image_five:


                break;

            case R.id.edit_image:

                Intent intent = new Intent(this,UpdateProfileActivity.class);
                intent.putExtra("firstName",userResponse.getData().getFirstName());
                intent.putExtra("lastName",userResponse.getData().getLastName());
                intent.putExtra("email",userResponse.getData().getEmail());
                intent.putExtra("phone",userResponse.getData().getPhone());
                intent.putExtra("address",userResponse.getData().getAddress());
                intent.putExtra("country",userResponse.getData().getCountryId());
                intent.putExtra("state",userResponse.getData().getStateId());
                intent.putExtra("city",userResponse.getData().getCityId());
                intent.putExtra("pincode",userResponse.getData().getPostalCode());
                intent.putExtra("website",userResponse.getData().getWebsite());
                intent.putExtra("socialMedia",userResponse.getData().getSocialMediaUrl());
                intent.putExtra("vetRegNo",userResponse.getData().getVetRegistrationNumber());
                intent.putExtra("vetStudy",userResponse.getData().getVetQualifications());
                intent.putExtra("category",userResponse.getData().getCategories());
                intent.putExtra("service",userResponse.getData().getServices());
//                intent.putExtra("image1",userResponse.getData().());
//                intent.putExtra("image2",userResponse.getData().getFirstName());
                intent.putExtra("serviceImage1",userResponse.getData().getFirstServiceImageUrl());
                intent.putExtra("serviceImage2",userResponse.getData().getSecondServiceImageUrl());
                intent.putExtra("serviceImage3",userResponse.getData().getThirdServiceImageUrl());
                intent.putExtra("serviceImage4",userResponse.getData().getFourthServiceImageUrl());
                intent.putExtra("serviceImage5",userResponse.getData().getFirstServiceImageUrl());



                startActivity(intent);
                break;

        }
    }
}
