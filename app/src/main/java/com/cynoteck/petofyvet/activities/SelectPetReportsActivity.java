package com.cynoteck.petofyvet.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.VisitTypesAdapter;
import com.cynoteck.petofyvet.adapters.petRegisterAdapter.RegisterRecyclerViewClickListener;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataParams;
import com.cynoteck.petofyvet.params.petReportsRequest.VisitTypeData;
import com.cynoteck.petofyvet.params.petReportsRequest.VisitTypeRequest;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetPetTestAndXRayResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetReportsTypeResponse;
import com.cynoteck.petofyvet.utils.Config;

import retrofit2.Response;
public class SelectPetReportsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener, RegisterRecyclerViewClickListener {
    String pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id;
    ImageView back_arrow_IV;
    TextView pet_name_TV,pet_sex_TV,pet_id_TV,pet_owner_name_TV,pet_owner_phone_no_TV;
    VisitTypesAdapter visitTypesAdapter;
    RecyclerView reports_types_RV;
    RelativeLayout reports_list_RL;
    ImageView view_xrayReport_arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_pet_reports_activity);

        init();
        setDeatils();
        getVisitTypes();



    }

    private void setDeatils() {
        pet_name_TV.setText(pet_name);
        pet_sex_TV.setText("("+pet_sex+")");
        pet_owner_name_TV.setText(pet_owner_name);
        pet_id_TV.setText(pet_unique_id);
//        pet_owner_phone_no_TV.setText("("+pet_owner_contact+")");

    }

    private void init() {

        Bundle extras = getIntent().getExtras();
        pet_id=extras.getString("pet_id");
        pet_unique_id = extras.getString("pet_unique_id");
        pet_name =extras.getString("pet_name");
        pet_sex =extras.getString("pet_sex");
        pet_owner_name =extras.getString("pet_owner_name");
        reports_types_RV=findViewById(R.id.reports_types_RV);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        pet_name_TV = findViewById(R.id.pet_name_TV);
        pet_sex_TV = findViewById(R.id.pet_sex_TV);
        pet_id_TV = findViewById(R.id.pet_id_TV);
        pet_owner_name_TV = findViewById(R.id.pet_owner_name_TV);
        pet_owner_phone_no_TV = findViewById(R.id.pet_owner_phone_no_TV);
        reports_list_RL=findViewById(R.id.reports_list_RL);
        view_xrayReport_arrow=findViewById(R.id.view_xrayReport_arrow);

        back_arrow_IV.setOnClickListener(this);
        view_xrayReport_arrow.setOnClickListener(this);

    }

    private void getVisitTypes() {
        ApiService<GetReportsTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getReportsType(Config.token), "GetReportsType");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.view_xrayReport_arrow:
                getXrayReport();
                


                break;
        }
    }

    private void getXrayReport() {

        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber("1");
        getPetDataParams.setPageSize("2");
        getPetDataParams.setSearch_Data("");
        VisitTypeData visitTypeData = new VisitTypeData();
        visitTypeData.setPetId(pet_id);
        visitTypeData.setVisitType("2");
        VisitTypeRequest visitTypeRequest = new VisitTypeRequest();
        visitTypeRequest.setHeader(getPetDataParams);
        visitTypeRequest.setData(visitTypeData);


        ApiService<GetPetTestAndXRayResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetTestAndXRay(Config.token,visitTypeRequest), "GetPetTestAndXRay");
        Log.e("DATALOG","GetPetTestAndXRay_Request=> "+visitTypeRequest);


    }
    @Override
    public void onResponse(Response response, String key) {
        switch (key){
            case "GetReportsType":
                try {
                    Log.d("GetPetServiceTypes",response.body().toString());
                    GetReportsTypeResponse petServiceResponse = (GetReportsTypeResponse) response.body();
                    int responseCode = Integer.parseInt(petServiceResponse.getResponse().getResponseCode());
                    if (responseCode== 109){

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        reports_types_RV.setLayoutManager(linearLayoutManager);
                        reports_types_RV.setNestedScrollingEnabled(false);
                        visitTypesAdapter  = new VisitTypesAdapter(SelectPetReportsActivity.this,petServiceResponse.getData(),this);
//                        categoryRecordArrayList = getPetListResponse.getData().getPetList();
                        reports_types_RV.setAdapter(visitTypesAdapter);
                        visitTypesAdapter.notifyDataSetChanged();
                        reports_list_RL.setVisibility(View.VISIBLE);
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;


            case "GetPetTestAndXRay":
                try {
                    Log.d("DATALOG","GetPetTestAndXRay=> "+(response.code()));
                    GetPetTestAndXRayResponse getPetTestAndXRayResponse = (GetPetTestAndXRayResponse) response.body();
                    int responseCode = Integer.parseInt(getPetTestAndXRayResponse.getResponse().getResponseCode());
                    if (responseCode== 109){

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
    public void onProductClick(int position) {

    }
}
