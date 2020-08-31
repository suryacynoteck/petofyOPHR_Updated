package com.cynoteck.petofyvet.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.fragments.AppointementFragment;
import com.cynoteck.petofyvet.fragments.HomeFragment;
import com.cynoteck.petofyvet.fragments.PetRegisterFragment;
import com.cynoteck.petofyvet.fragments.ProfileFragment;
import com.cynoteck.petofyvet.response.updateProfileResponse.UserResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {


    private RelativeLayout homeRL,profileRL,petregisterRL,appointmentRL;
    public ImageView icHome, icProfile, icPetRegister, icAppointment;
    boolean doubleBackToExitPressedOnce = false;
    boolean exit = false;
    Methods methods;
    String IsVeterinarian="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        init();
        methods = new Methods(this);
        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        Config.token = sharedPreferences.getString("token", "");
        Log.e("token",Config.token);
        Config.user_id=sharedPreferences.getString("userId", "");
        Config.user_Veterian_phone=sharedPreferences.getString("phoneNumber", "");
        Config.user_Veterian_emial=sharedPreferences.getString("email", "");
        Config.user_Veterian_name=sharedPreferences.getString("firstName", "")+" "+sharedPreferences.getString("lastName", "");
        Config.user_Veterian_address=sharedPreferences.getString("address", "");

        if(methods.isInternetOn())
        {
             //getUserDetails();
        }
        else
        {
            methods.DialogInternet();
        }

        if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.content_frame, homeFragment);
            ft.commit();
            icHome.setImageResource(R.drawable.home_green_icon);
        }

    }


    private void init() {
        homeRL = findViewById(R.id.homeRL);
        profileRL = findViewById(R.id.profileRL);
        petregisterRL=findViewById(R.id.petRegisterRL);
        appointmentRL=findViewById(R.id.appointmentRL);

        icHome=findViewById(R.id.icHome);
        icProfile = findViewById(R.id.icProfile);
        icPetRegister=findViewById(R.id.icPetRegister);
        icAppointment=findViewById(R.id.icAppointment);

        homeRL.setOnClickListener(this);
        profileRL.setOnClickListener(this);
        petregisterRL.setOnClickListener(this);
        appointmentRL.setOnClickListener(this);


    }

    private void getUserDetails() {
       // methods.showCustomProgressBarDialog(this);
        ApiService<UserResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getUserDetailsApi(Config.token), "GetUserDetails");

    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key)
        {
            case "GetUserDetails":
                try {
                    methods.customProgressDismiss();
                    Log.d("GetUserDetails",response.body().toString());
                    UserResponse userResponse = (UserResponse) response.body();
                    int responseCode = Integer.parseInt(userResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        methods.customProgressDismiss();
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        IsVeterinarian=userResponse.getData().getIsVeterinarian();
                        Log.d("IsVeterinarian",""+userResponse.getData().getIsVeterinarian());
                        if(IsVeterinarian.equals("false")){
                            startActivity(new Intent(DashBoardActivity.this,UpdateProfileActivity.class));
                            finish();
                        }
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
        Log.e("error",t.getMessage());
        Log.e("errrrr",t.getLocalizedMessage());

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.homeRL:
                Config.count = 1;
                icHome.setImageResource(R.drawable.home_green_icon);
                icProfile.setImageResource(R.drawable.profile_normal_icon);
                icPetRegister.setImageResource(R.drawable.pet_normal_icon);
                icAppointment.setImageResource(R.drawable.appointment_normal_icon);
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, homeFragment);
                ft.commit();

                break;

            case R.id.profileRL:
                Config.count = 0;
                icHome.setImageResource(R.drawable.home_normal_icon);
                icProfile.setImageResource(R.drawable.profile_green_icon);
                icPetRegister.setImageResource(R.drawable.pet_normal_icon);
                icAppointment.setImageResource(R.drawable.appointment_normal_icon);
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentTransaction ftProfile = getSupportFragmentManager().beginTransaction();
                ftProfile.replace(R.id.content_frame, profileFragment);
                ftProfile.commit();
                break;

            case R.id.petRegisterRL:
                Config.count = 0;
                icHome.setImageResource(R.drawable.home_normal_icon);
                icProfile.setImageResource(R.drawable.profile_normal_icon);
                icPetRegister.setImageResource(R.drawable.pet_green_icon);
                icAppointment.setImageResource(R.drawable.appointment_normal_icon);
                PetRegisterFragment petRegisterFragment = new PetRegisterFragment();
                FragmentTransaction ftPetRegister = getSupportFragmentManager().beginTransaction();
                ftPetRegister.replace(R.id.content_frame, petRegisterFragment);
                ftPetRegister.commit();
                break;

            case R.id.appointmentRL:
                Config.count = 0;
                icHome.setImageResource(R.drawable.home_normal_icon);
                icProfile.setImageResource(R.drawable.profile_normal_icon);
                icPetRegister.setImageResource(R.drawable.pet_normal_icon);
                icAppointment.setImageResource(R.drawable.appointment_green_icon);
                AppointementFragment appointementFragment = new AppointementFragment();
                FragmentTransaction ftAppointment = getSupportFragmentManager().beginTransaction();
                ftAppointment.replace(R.id.content_frame, appointementFragment);
                ftAppointment.commit();
                break;

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        Log.e("count", String.valueOf(Config.count));
        Log.e("exit", String.valueOf(exit));
        if (Config.count == 1) {
            if (exit) {
                super.onBackPressed();
                finishAffinity();
                System.exit(0);
                return;
            } else {
                Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    exit =  false;
                    }
                }, 2000);
            }
        }else {
            Config.count=1;
            HomeFragment homeFragment = new HomeFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, homeFragment);
            ft.commit();
            getSupportFragmentManager().popBackStack();
            icHome.setImageResource(R.drawable.home_green_icon);
            icProfile.setImageResource(R.drawable.profile_normal_icon);
            icPetRegister.setImageResource(R.drawable.pet_normal_icon);
            icAppointment.setImageResource(R.drawable.appointment_normal_icon);
        }
    }

}
