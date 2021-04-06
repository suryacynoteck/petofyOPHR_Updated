package com.cynoteck.petofyOPHR.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyOPHR.params.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyOPHR.response.onlineAppointmentOnOff.OnlineAppointmentResponse;
import com.cynoteck.petofyOPHR.utils.Config;
import com.google.android.material.card.MaterialCardView;

import retrofit2.Response;

public class SettingActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {
    MaterialCardView back_arrow_CV;
    TextView two_fact_auth_TV,immunization_chart_TV,bank_account_details_TV;
    SwitchCompat two_fact_auth_SC;
    String status="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        back_arrow_CV = findViewById(R.id.back_arrow_CV);
        two_fact_auth_SC =findViewById(R.id.two_fact_auth_SC);
        two_fact_auth_TV=findViewById(R.id.two_fact_auth_TV);
        immunization_chart_TV = findViewById(R.id.immunization_chart_TV);
        bank_account_details_TV=findViewById(R.id.bank_account_details_TV);
        immunization_chart_TV.setOnClickListener(this);
        bank_account_details_TV.setOnClickListener(this);
        back_arrow_CV.setOnClickListener(this);
        setTwoFactData();
        switchOnline();

    }

    private void switchOnline() {
        two_fact_auth_SC.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    status = "1";
                    enableDisable(status);
                }else {
                    status = "0";
                    enableDisable(status);

                }
            }
        });
    }

    private void enableDisable(String status) {
        GetPetListParams getPetListParams = new GetPetListParams();
        getPetListParams.setId(status);
        GetPetListRequest getPetListRequest = new GetPetListRequest();
        getPetListRequest.setData(getPetListParams);
        ApiService<OnlineAppointmentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().enableDisableTowFactorAuth(Config.token,getPetListRequest), "EnableDisable");

    }

    private void setTwoFactData() {
        if (Config.two_fact_auth_status.equals("true")){
            two_fact_auth_SC.setChecked(true);
            two_fact_auth_TV.setText("Disable Two Step Authentication");
        }else {
            two_fact_auth_SC.setChecked(false);
            two_fact_auth_TV.setText("Enable Two Step Authentication");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTwoFactData();
    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key){
            case "EnableDisable":
                try {
                    Log.d("EnableDisable",response.body().toString());
                    OnlineAppointmentResponse onlineAppointmentResponse = (OnlineAppointmentResponse) response.body();
                    int responseCode = Integer.parseInt(onlineAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if (status.equals("1")){
                            Toast.makeText(this, "Two Step Authentication Enable", Toast.LENGTH_SHORT).show();
                            two_fact_auth_TV.setText("Disable Two Step Authentication");
                            SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
                            SharedPreferences.Editor login_editor;
                            login_editor = sharedPreferences.edit();
                            login_editor.putString("twoFactAuth", "true");
                            login_editor.commit();
                            Config.two_fact_auth_status ="true";
                        }else {
                            Toast.makeText(this, "Two Step Authentication Disable", Toast.LENGTH_SHORT).show();
                            two_fact_auth_TV.setText("Enable Two Step Authentication");
                            SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
                            SharedPreferences.Editor login_editor;
                            login_editor = sharedPreferences.edit();
                            login_editor.putString("twoFactAuth", "false");
                            login_editor.commit();
                            Config.two_fact_auth_status ="false";
                        }
                    }else if (responseCode==614){
                        Toast.makeText(this, onlineAppointmentResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_CV:
            onBackPressed();

            break;

            case R.id.immunization_chart_TV:
                Intent intent = new Intent(this,ImmunizationChartActivity.class);
                startActivity(intent);
                break;

            case R.id.bank_account_details_TV:
                Intent  intent1 = new Intent(this,GetAllBankAccountsActivity.class);
                startActivity(intent1);
        }
    }
}
