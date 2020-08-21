package com.cynoteck.petofyvet.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.ViewReportsDeatilsActivity;
import com.cynoteck.petofyvet.adapters.HospitalizationReportsAdapter;
import com.cynoteck.petofyvet.adapters.LabTestReportsAdapter;
import com.cynoteck.petofyvet.adapters.ReportsTypeAdapter;
import com.cynoteck.petofyvet.adapters.TestAndXRayAdpater;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataParams;
import com.cynoteck.petofyvet.params.petReportsRequest.VisitTypeData;
import com.cynoteck.petofyvet.params.petReportsRequest.VisitTypeRequest;
import com.cynoteck.petofyvet.response.getLabTestReportResponse.getPetLabWorkListResponse.PetLabWorkList;
import com.cynoteck.petofyvet.response.getLabTestReportResponse.getPetLabWorkListResponse.PetLabWorkResponse;
import com.cynoteck.petofyvet.response.getPetHospitalizationResponse.getHospitalizationListResponse.GetPetHospitalizationResponse;
import com.cynoteck.petofyvet.response.getPetHospitalizationResponse.getHospitalizationListResponse.PetHospitalizationsList;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getPetClinicVisitsListsResponse.GetPetClinicVisitListResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getPetClinicVisitsListsResponse.PetClinicVisitList;
import com.cynoteck.petofyvet.response.getXRayReports.getPetTestAndXRayResponse.GetPetTestAndXRayResponse;
import com.cynoteck.petofyvet.response.getXRayReports.getPetTestAndXRayResponse.PetTestsAndXrayList;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.OnClickStaicLists;
import com.cynoteck.petofyvet.utils.RegisterRecyclerViewClickListener;

import java.util.ArrayList;

public class NewEntrysListFragment extends Fragment implements ApiResponse, RegisterRecyclerViewClickListener, OnClickStaicLists {
    String pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id ,report_type_id,type;

    RecyclerView routine_report_RV;
    View view;
    private ArrayList<PetClinicVisitList> petClinicVisitListArrayList;
    private ArrayList<PetTestsAndXrayList> petTestsAndXrayLists;
    private ArrayList<PetLabWorkList> petLabWorkLists;
    private ArrayList<PetHospitalizationsList> petHospitalizationsLists;

    ReportsTypeAdapter reportsTypeAdapter;
    TestAndXRayAdpater testAndXRayAdpater;
    LabTestReportsAdapter labTestReportsAdapter;
    HospitalizationReportsAdapter hospitalizationReportsAdapter;

    public NewEntrysListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_new_entrys_list, container, false);

        Bundle extras = this.getArguments();
        report_type_id = extras.getString("reports_id");
        pet_id = extras.getString("pet_id");
        pet_owner_contact = extras.getString("pet_owner_contact");
        pet_owner_name = extras.getString("pet_owner_name");
        pet_sex = extras.getString("pet_sex");
        pet_name = extras.getString("pet_name");
        pet_unique_id = extras.getString("pet_unique_id");
        type=extras.getString("type");

        routine_report_RV = view.findViewById(R.id.routine_report_RV);

        switch (type){

            case "list":
                getPetClinicVisit();
                break;

            case "XRay":
                getXrayReport();
                break;

            case "LabTest":
                getLabTestReport();
                break;

            case "Hospitalization":
                getHospitalizationReport();
                break;
        }

        return view;
    }

    private void getHospitalizationReport() {
        VisitTypeRequest visitTypeRequest = new VisitTypeRequest();
        PetDataParams petDataParams = new PetDataParams();
        petDataParams.setPageNumber("1");
        petDataParams.setPageSize("10000");
        petDataParams.setSearch_Data("");
        VisitTypeData visitTypeData = new VisitTypeData();
        visitTypeData.setVisitType(report_type_id);
        visitTypeData.setPetId(pet_id);
        visitTypeRequest.setHeader(petDataParams);
        visitTypeRequest.setData(visitTypeData);
        Log.d("HospitalizationRequest",visitTypeRequest.toString());


        ApiService<GetPetHospitalizationResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetHospitalization(Config.token,visitTypeRequest), "GetHospitalization");


    }

    private void getLabTestReport() {

        VisitTypeRequest visitTypeRequest = new VisitTypeRequest();
        PetDataParams petDataParams = new PetDataParams();
        petDataParams.setPageNumber("1");
        petDataParams.setPageSize("10000");
        petDataParams.setSearch_Data("");
        VisitTypeData visitTypeData = new VisitTypeData();
        visitTypeData.setVisitType(report_type_id);
        visitTypeData.setPetId(pet_id);
        visitTypeRequest.setHeader(petDataParams);
        visitTypeRequest.setData(visitTypeData);
        Log.d("LabTestRequest",visitTypeRequest.toString());


        ApiService<PetLabWorkResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetLabWorkResponse(Config.token,visitTypeRequest), "GetLabTest");


    }

    private void getPetClinicVisit() {

        VisitTypeRequest visitTypeRequest = new VisitTypeRequest();
        PetDataParams petDataParams = new PetDataParams();
        petDataParams.setPageNumber("1");
        petDataParams.setPageSize("10000");
        petDataParams.setSearch_Data("");
        VisitTypeData visitTypeData = new VisitTypeData();
        visitTypeData.setVisitType(report_type_id);
        visitTypeData.setPetId(pet_id);
        visitTypeRequest.setHeader(petDataParams);
        visitTypeRequest.setData(visitTypeData);
        Log.d("visitTypeRequest",visitTypeRequest.toString());


        ApiService<GetPetClinicVisitListResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getPetClinicVisits(Config.token,visitTypeRequest), "GetPetClinicVisit");



    }

    private void getXrayReport() {


        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber("1");
        getPetDataParams.setPageSize("10000");
        getPetDataParams.setSearch_Data("");
        VisitTypeData visitTypeData = new VisitTypeData();
        visitTypeData.setPetId(pet_id);
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
            case "GetPetClinicVisit":
                try {
                    Log.d("ResponseClinicVisit",response.body().toString());
                    GetPetClinicVisitListResponse petServiceResponse = (GetPetClinicVisitListResponse) response.body();
                    int responseCode = Integer.parseInt(petServiceResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petClinicVisitListArrayList = petServiceResponse.getData().getPetClinicVisitList();
                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);
                        reportsTypeAdapter  = new ReportsTypeAdapter(getContext(),petServiceResponse.getData().getPetClinicVisitList(),this);
                        routine_report_RV.setAdapter(reportsTypeAdapter);
                        reportsTypeAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetTestAndXRay":
                try {
                    Log.d("GetPetTestAndXRay","GetPetTestAndXRay=> "+(response.body()));
                    GetPetTestAndXRayResponse getPetTestAndXRayResponse = (GetPetTestAndXRayResponse) response.body();
                    int responseCode = Integer.parseInt(getPetTestAndXRayResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petTestsAndXrayLists = getPetTestAndXRayResponse.getData().getPetTestsAndXrayList();

                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);
                        testAndXRayAdpater   = new TestAndXRayAdpater(getContext(),getPetTestAndXRayResponse.getData().getPetTestsAndXrayList(),this);
                        routine_report_RV.setAdapter(testAndXRayAdpater);
                        testAndXRayAdpater.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetLabTest":
                try {
                    Log.d("GetLabTest","GetLabTest=> "+(response.body()));
                    PetLabWorkResponse petLabWorkResponse = (PetLabWorkResponse) response.body();
                    int responseCode = Integer.parseInt(petLabWorkResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petLabWorkLists = petLabWorkResponse.getData().getPetLabWorkList();

                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);
                        labTestReportsAdapter   = new LabTestReportsAdapter(getContext(),petLabWorkResponse.getData().getPetLabWorkList(),this);
                        routine_report_RV.setAdapter(labTestReportsAdapter);
                        labTestReportsAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;


            case "GetHospitalization":
                try {
                    Log.d("GetHospitalization","GetHospitalization=> "+(response.body()));
                    GetPetHospitalizationResponse getPetHospitalizationResponse = (GetPetHospitalizationResponse) response.body();
                    int responseCode = Integer.parseInt(getPetHospitalizationResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petHospitalizationsLists = getPetHospitalizationResponse.getData().getPetHospitalizationsList();

                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);
                        hospitalizationReportsAdapter   = new HospitalizationReportsAdapter(getContext(),getPetHospitalizationResponse.getData().getPetHospitalizationsList(),this);
                        routine_report_RV.setAdapter(hospitalizationReportsAdapter);
                        hospitalizationReportsAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Sucess", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(getContext(), ""+petClinicVisitListArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
        Intent viewReportsDeatilsActivityIntent = new Intent(getActivity().getApplication(), ViewReportsDeatilsActivity.class);
        Bundle data = new Bundle();
        data.putString("clinic_id",petClinicVisitListArrayList.get(position).getId());

        viewReportsDeatilsActivityIntent.putExtras(data);
        startActivity(viewReportsDeatilsActivityIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

    }


    @Override
    public void onViewXrayClick(int position) {
        Toast.makeText(getContext(), ""+petTestsAndXrayLists.get(position).getId(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onViewLabTestReportsClick(int position) {
        Toast.makeText(getContext(), ""+petLabWorkLists.get(position).getId(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onViewHospitalizationClick(int position) {
        Toast.makeText(getContext(), ""+petHospitalizationsLists.get(position).getId(), Toast.LENGTH_SHORT).show();


    }
}