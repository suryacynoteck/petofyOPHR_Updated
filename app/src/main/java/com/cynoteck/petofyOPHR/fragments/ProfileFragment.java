package com.cynoteck.petofyOPHR.fragments;


import android.content.Context;
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
import com.cynoteck.petofyOPHR.activities.AddNewPetActivity;
import com.cynoteck.petofyOPHR.activities.ChangePasswordActivity;
import com.cynoteck.petofyOPHR.activities.GetAllBankAccountsActivity;
import com.cynoteck.petofyOPHR.activities.ImmunizationChartActivity;
import com.cynoteck.petofyOPHR.activities.LoginActivity;
import com.cynoteck.petofyOPHR.activities.OperatingHoursActivity;
import com.cynoteck.petofyOPHR.activities.SettingActivity;
import com.cynoteck.petofyOPHR.activities.ViewFullProfileVetActivity;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.appointmentParams.AppointmentStatusParams;
import com.cynoteck.petofyOPHR.params.appointmentParams.AppointmentsStatusRequest;
import com.cynoteck.petofyOPHR.params.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyOPHR.params.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyOPHR.response.loginRegisterResponse.UserPermissionMasterList;
import com.cynoteck.petofyOPHR.response.onlineAppointmentOnOff.OnlineAppointmentResponse;
import com.cynoteck.petofyOPHR.response.staffPermissionListResponse.CheckStaffPermissionResponse;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.Methods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener, ApiResponse {

    TextView tv,heder,vet_name_TV,vet_email_TV,vet_study_TV,vet_id_TV;
    ImageView vet_profile_pic;
    SwitchCompat online_switch;
    View view;
    RelativeLayout veterian_full_profile_layout,operating_hrs_layout,setings_layout,logout_layout,changePass_layout,immunization_master_layout,bank_accounts_layout;
    String status;
    SharedPreferences sharedPreferences;
    String userTYpe="";
    String permissionId="";
    Methods methods;
    Context context;
    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userdetails", 0);
        methods = new Methods(context);

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
        AppointmentStatusParams getPetListParams = new AppointmentStatusParams();
        getPetListParams.setId(value);
        AppointmentsStatusRequest getPetListRequest = new AppointmentsStatusRequest();
        getPetListRequest.setData(getPetListParams);
        ApiService<OnlineAppointmentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().onlineAppointmentOnOff(Config.token,getPetListRequest), "OnlineAppoint");
        Log.e("onlineAppointment==>",""+methods.getRequestJson(getPetListRequest));

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
        immunization_master_layout=view.findViewById(R.id.immunization_master_layout);
        bank_accounts_layout=view.findViewById(R.id.bank_accounts_layout);

        immunization_master_layout.setOnClickListener(this);
        veterian_full_profile_layout.setOnClickListener(this);
        operating_hrs_layout.setOnClickListener(this);
        setings_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
        changePass_layout.setOnClickListener(this);
        bank_accounts_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.bank_accounts_layout:
                Intent  bank_accounts_intent = new Intent(getContext(), GetAllBankAccountsActivity.class);
                startActivity(bank_accounts_intent);
                break;
            case R.id.immunization_master_layout:
                userTYpe = sharedPreferences.getString("user_type", "");
                if (userTYpe.equals("Vet Staff")){
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("userPermission", null);
                    Type type = new TypeToken<ArrayList<UserPermissionMasterList>>() {}.getType();
                    ArrayList<UserPermissionMasterList> arrayList = gson.fromJson(json, type);
                    Log.e("ArrayList",arrayList.toString());
                    Log.d("UserType",userTYpe);
                    permissionId = "18";
                    methods.showCustomProgressBarDialog(getContext());
                    String url  = "user/CheckStaffPermission/"+permissionId;
                    Log.e("URL",url);
                    ApiService<CheckStaffPermissionResponse> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().getCheckStaffPermission(Config.token,url), "CheckPermission");
                }else if (userTYpe.equals("Veterinarian")){
                    Intent immunization_master_intent = new Intent(getContext(), ImmunizationChartActivity.class);
                    startActivity(immunization_master_intent);

                }

                break;
            case R.id.changePass_layout:

                Intent changePass_intent = new Intent(getContext(), ChangePasswordActivity.class);
                startActivity(changePass_intent);
                break;
            case R.id.veterian_full_profile_layout:
                Intent veterian_full_profile_intent = new Intent(getContext(), ViewFullProfileVetActivity.class);
                startActivity(veterian_full_profile_intent);
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
//                            Toast.makeText(getContext(), "Enable Online Appointment", Toast.LENGTH_SHORT).show();
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

            case "CheckPermission":
                try {
                    methods.customProgressDismiss();
                    CheckStaffPermissionResponse checkStaffPermissionResponse = (CheckStaffPermissionResponse) response.body();
                    Log.d("GetPetList", checkStaffPermissionResponse.toString());
                    int responseCode = Integer.parseInt(checkStaffPermissionResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        if (checkStaffPermissionResponse.getData().equals("true")){
                            if (permissionId.equals("18")) {
                                Intent immunization_master_intent = new Intent(getContext(), ImmunizationChartActivity.class);
                                startActivity(immunization_master_intent);
                            }
                        }else {
                            Toast.makeText(getContext(), "Permission not Granted!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
