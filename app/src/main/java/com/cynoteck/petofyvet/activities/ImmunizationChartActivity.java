package com.cynoteck.petofyvet.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.ImmunizationChartAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.immunizationRequest.ImmunizationParams;
import com.cynoteck.petofyvet.params.immunizationRequest.ImmunizationRequest;
import com.cynoteck.petofyvet.response.CheckTrueFalseStatus;
import com.cynoteck.petofyvet.response.immunizationListResponse.ImmunizationResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.ImmunizationOnclick;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

public class ImmunizationChartActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse, ImmunizationOnclick {
    LinearLayout create_new_immu;
    AppCompatSpinner pet_type_ACS;
    RecyclerView immue_list_RV;
    ImageView back_arrow_IV;
    Methods methods;
    ImmunizationChartAdapter immunizationChartAdapter;
    ImmunizationResponse immunizationResponse;
    int deletePostion;
    ArrayList<String> petType = new ArrayList<>();
    HashMap<String,String> petTypeHashMap=new HashMap<>();
    String strSpnerItemPetNm="Dog",getStrSpnerItemPetNmId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immunization_chart);
        methods = new Methods(this);
        initization();
        if (methods.isInternetOn()) {
            petType();
            getImmunizationList("1");
        } else {
            methods.DialogInternet();
        }


    }

    private void petType() {
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(Config.token), "GetPetTypes");

    }

    private void getImmunizationList(String getStrSpnerItemPetNmId) {
        ImmunizationParams immunizationParams = new ImmunizationParams();
        immunizationParams.setEncryptedId(getStrSpnerItemPetNmId);
        ImmunizationRequest immunizationRequest = new ImmunizationRequest();
        immunizationRequest.setData(immunizationParams);
        ApiService<ImmunizationResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getImmunizationList(Config.token, immunizationRequest), "ImmunizationList");
        Log.d("Immuniztaion", "parameter" + immunizationRequest);
    }

    private void initization() {
        create_new_immu = findViewById(R.id.create_new_immu);
        pet_type_ACS = findViewById(R.id.pet_type);
        immue_list_RV = findViewById(R.id.immue_list_RV);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);

        create_new_immu.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                strSpnerItemPetNm =data.getStringExtra("petCat");
                getImmunizationList(data.getStringExtra("petCatId"));
            }
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.create_new_immu:
                Intent intent = new Intent(this,AddEditImmunizationActivity.class);
                intent.putExtra("type","add");
                startActivityForResult(intent,1);

                break;

            case R.id.back_arrow_IV:
                onBackPressed();
                break;

        }

    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "ImmunizationList":
                try {
                    immunizationResponse = (ImmunizationResponse) arg0.body();
                    Log.d("ImmunizationList", immunizationResponse.toString());
                    int responseCode = Integer.parseInt(immunizationResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        immue_list_RV.setLayoutManager(linearLayoutManager);
                        immunizationChartAdapter = new ImmunizationChartAdapter(this, immunizationResponse.getData().getImmunizationScheduleScheduleList(), this);
                        immue_list_RV.setAdapter(immunizationChartAdapter);
//                        immunizationChartAdapter.notifyDataSetChanged();
//                        shimmer_view_container.setVisibility(View.GONE);
////                        shimmer_view_container.stopShimmerAnimation();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, immunizationResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "DeleteImmunization":
                try {
                    CheckTrueFalseStatus checkTrueFalseStatus = (CheckTrueFalseStatus) arg0.body();
                    Log.d("DeleteImmunization", checkTrueFalseStatus.toString());
                    int responseCode = Integer.parseInt(checkTrueFalseStatus.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        if (checkTrueFalseStatus.getData().equals("true")){
                            immunizationResponse.getData().getImmunizationScheduleScheduleList().remove(deletePostion);
                            immunizationChartAdapter.notifyItemRemoved(deletePostion);
                            immunizationChartAdapter.notifyItemRangeChanged(deletePostion,immunizationResponse.getData().getImmunizationScheduleScheduleList().size());
                        }
                    } else if (responseCode == 614) {
                        Toast.makeText(this, immunizationResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetTypes":
                try {
                    Log.d("GetPetTypes",arg0.body().toString());
                    PetTypeResponse petTypeResponse = (PetTypeResponse) arg0.body();
                    int responseCode = Integer.parseInt(petTypeResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Log.d("lalal",""+petTypeResponse.getData().size());
                        for(int i=0; i<petTypeResponse.getData().size(); i++){
                            Log.d("petttt",""+petTypeResponse.getData().get(i).getPetType1());
                            petType.add(petTypeResponse.getData().get(i).getPetType1());
                            petTypeHashMap.put(petTypeResponse.getData().get(i).getPetType1(),petTypeResponse.getData().get(i).getId());
                        }
                        Log.e("aaa",petTypeHashMap.toString());
                        setPetTypeSpinner();

                    }else if (responseCode==614){
                        Toast.makeText(this, petTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
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

    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        pet_type_ACS.setAdapter(aa);
        if (!strSpnerItemPetNm.equals("")) {
            int spinnerPosition = aa.getPosition(strSpnerItemPetNm);
            pet_type_ACS.setSelection(spinnerPosition);
        }
        pet_type_ACS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                strSpnerItemPetNm=item;
                Log.d("spnerType",""+strSpnerItemPetNm);
                getStrSpnerItemPetNmId=petTypeHashMap.get(strSpnerItemPetNm);
                getImmunizationList(getStrSpnerItemPetNmId);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }



    @Override
        public void onError (Throwable t, String key){

        }

    @Override
    public void onDeleteButton(final int position) {
        deletePostion = position;
        Log.d("Add Anotheer Veterian","vet");
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Are you sure?");
        alertDialog.setMessage("Do You Want to Delete This Schedule ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (methods.isInternetOn()) {
                            deleteImmunization(position);
                        } else {
                            methods.DialogInternet();
                        }
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        alertDialog.show();


    }

    private void deleteImmunization(int position) {
        ImmunizationParams immunizationParams = new ImmunizationParams();
        immunizationParams.setEncryptedId(immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getId());
        ImmunizationRequest immunizationRequest = new ImmunizationRequest();
        immunizationRequest.setData(immunizationParams);
        ApiService<CheckTrueFalseStatus> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().deleteImmunizationSchedule(Config.token, immunizationRequest), "DeleteImmunization");
        Log.d("DeleteImmuniztaion", "parameter" + immunizationRequest);

    }

    @Override
    public void onEditButton(int position) {
        Intent intent = new Intent(this,AddEditImmunizationActivity.class);
        intent.putExtra("type","edit");
        intent.putExtra("id",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getId());
        intent.putExtra("petType",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getPetCategoryName());
        intent.putExtra("sNo",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getSerialNumber());
        intent.putExtra("ageUnit","Day");
        intent.putExtra("minage",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getMinimunAge());
        intent.putExtra("maxAge",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getMaximunAge());
        intent.putExtra("vaccineName",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getRecommendedVaccinations());
        intent.putExtra("boosterOne",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getBoosterOne());
        intent.putExtra("gapBoosterOne",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getBoosterOneDaysGap());
        intent.putExtra("boosterTwo",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getBoosterTwo());
        intent.putExtra("gapBoosterTwo",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getBoosterTwoDaysGap());
        intent.putExtra("isPeriodicVaccine",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getIsPeriodicVaccine());
        intent.putExtra("vaccinePeriod",immunizationResponse.getData().getImmunizationScheduleScheduleList().get(position).getVaccinationPeriodText());

        startActivityForResult(intent,1);


    }
}
