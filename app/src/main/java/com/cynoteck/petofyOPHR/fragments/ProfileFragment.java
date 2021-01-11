package com.cynoteck.petofyOPHR.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.activities.ChangePasswordActivity;
import com.cynoteck.petofyOPHR.activities.LoginActivity;
import com.cynoteck.petofyOPHR.activities.OperatingHoursActivity;
import com.cynoteck.petofyOPHR.activities.SettingActivity;
import com.cynoteck.petofyOPHR.activities.ViewFullProfileVetActivity;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyOPHR.params.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyOPHR.response.onlineAppointmentOnOff.OnlineAppointmentResponse;
import com.cynoteck.petofyOPHR.utils.Config;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, ApiResponse {

    TextView tv,heder,vet_name_TV,vet_email_TV,vet_study_TV,vet_id_TV;
    ImageView vet_profile_pic;
    SwitchCompat online_switch;
    View view;
    RelativeLayout veterian_full_profile_layout,operating_hrs_layout,setings_layout,logout_layout,changePass_layout;
    String status;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize();
        getVetInfo();
        switchOnline();

        return view;
    }

    private void getVetInfo() {
        try {
            Glide.with(this)
                    .load(new URL(Config.user_Veterian_url))
                    .into(vet_profile_pic);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        vet_name_TV.setText(Config.user_Veterian_name);
        vet_email_TV.setText(Config.user_Veterian_emial);
        vet_study_TV.setText(Config.user_Veterian_study);
        vet_id_TV.setText(Config.user_Veterian_id);


    }

    private void switchOnline() {
        online_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    status = "1";
                    onlineAppoint(status);
                }else {
                    status = "0";
                    onlineAppoint(status);

                }
            }
        });
    }

    private void onlineAppoint(String value) {
        GetPetListParams getPetListParams = new GetPetListParams();
        getPetListParams.setId(value);
        GetPetListRequest getPetListRequest = new GetPetListRequest();
        getPetListRequest.setData(getPetListParams);
        ApiService<OnlineAppointmentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().onlineAppointmentOnOff(Config.token,getPetListRequest), "OnlineAppoint");
        Log.e("onlineAppointment==>",""+getPetListRequest);

    }

    public  void initialize()
    {
        veterian_full_profile_layout=view.findViewById(R.id.veterian_full_profile_layout);
        operating_hrs_layout=view.findViewById(R.id.operating_hrs_layout);
        setings_layout=view.findViewById(R.id.setings_layout);
        logout_layout=view.findViewById(R.id.logout_layout);
        changePass_layout=view.findViewById(R.id.changePass_layout);
        vet_email_TV = view.findViewById(R.id.vet_email_TV);
        vet_name_TV = view.findViewById(R.id.vet_name_TV);
        vet_study_TV = view.findViewById(R.id.vet_study_TV);
        vet_id_TV = view.findViewById(R.id.vet_id_TV);
        vet_profile_pic = view.findViewById(R.id.vet_profile_pic);
        online_switch = view.findViewById(R.id.online_switch);

        veterian_full_profile_layout.setOnClickListener(this);
        operating_hrs_layout.setOnClickListener(this);
        setings_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        changePass_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.changePass_layout:

                Intent changePass = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(changePass);
                break;
            case R.id.veterian_full_profile_layout:
                Intent intent = new Intent(getContext(), ViewFullProfileVetActivity.class);
                startActivity(intent);
                break;

            case R.id.operating_hrs_layout:
                startActivity(new Intent(getActivity(), OperatingHoursActivity.class));
                break;

            case R.id.setings_layout:
                Intent setting = new Intent(getContext(), SettingActivity.class);
                startActivity(setting);

                break;

            case R.id.logout_layout:
                SharedPreferences preferences =getActivity().getSharedPreferences("userdetails",0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Config.user_Veterian_online.equals("true")){
            online_switch.setChecked(true);
        }else {
            online_switch.setChecked(false);
        }    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key){
            case "OnlineAppoint":
                try {
                    Log.d("UploadDocument",response.body().toString());
                    OnlineAppointmentResponse onlineAppointmentResponse = (OnlineAppointmentResponse) response.body();
                    int responseCode = Integer.parseInt(onlineAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if (status.equals("1")){
                            Toast.makeText(getContext(), "Enable Online Appointment", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdetails", 0);
                            SharedPreferences.Editor login_editor;
                            login_editor = sharedPreferences.edit();
                            login_editor.putString("onlineAppoint", "true");
                            login_editor.commit();
                            Config.user_Veterian_online ="true";

                        }else {
                            Toast.makeText(getContext(), "Disable Online Appointment", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userdetails", 0);
                            SharedPreferences.Editor login_editor;
                            login_editor = sharedPreferences.edit();
                            login_editor.putString("onlineAppoint", "false");
                            login_editor.commit();
                            Config.user_Veterian_online ="false";

                        }
                    }else if (responseCode==614){
                        Toast.makeText(getContext(), onlineAppointmentResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Please Try Again !", Toast.LENGTH_SHORT).show();
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

    }
}
