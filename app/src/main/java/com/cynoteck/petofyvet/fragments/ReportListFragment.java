package com.cynoteck.petofyvet.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.AddXRayDeatilsActivity;
import com.cynoteck.petofyvet.activities.HospitalizationDetailsActivity;
import com.cynoteck.petofyvet.activities.LabTestReportDeatilsActivity;
import com.cynoteck.petofyvet.activities.ViewReportsDeatilsActivity;
import com.cynoteck.petofyvet.activities.XRayReportDeatilsActivity;
import com.cynoteck.petofyvet.adapters.HospitalizationReportsAdapter;
import com.cynoteck.petofyvet.adapters.LabTestReportsAdapter;
import com.cynoteck.petofyvet.adapters.ReportsTypeAdapter;
import com.cynoteck.petofyvet.adapters.TestAndXRayAdpater;
import com.cynoteck.petofyvet.adapters.UpdateClinicVisitAdapter;
import com.cynoteck.petofyvet.adapters.UpdateHospitalizationAdapter;
import com.cynoteck.petofyvet.adapters.UpdateLabTestAdpater;
import com.cynoteck.petofyvet.adapters.UpdateXRayAdpater;
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
import com.cynoteck.petofyvet.utils.ViewAndUpdateClickListener;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class ReportListFragment extends Fragment implements ApiResponse, ViewAndUpdateClickListener {
    String pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id ,report_type_id,type,button_type;

    RecyclerView routine_report_RV;
    View view;
    ImageView empty_IV;
    public ArrayList<PetClinicVisitList> petClinicVisitListArrayList;
    private ArrayList<PetTestsAndXrayList> petTestsAndXrayLists;
    private ArrayList<PetLabWorkList> petLabWorkLists;
    private ArrayList<PetHospitalizationsList> petHospitalizationsLists;

    ReportsTypeAdapter reportsTypeAdapter;
    TestAndXRayAdpater testAndXRayAdpater;
    LabTestReportsAdapter labTestReportsAdapter;
    HospitalizationReportsAdapter hospitalizationReportsAdapter;
    UpdateClinicVisitAdapter updateClinicVisitAdapter;
    UpdateXRayAdpater updateXRayAdpater;
    UpdateLabTestAdpater updateLabTestAdpater;
    UpdateHospitalizationAdapter updateHospitalizationAdapter;
    

    public ReportListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_report_list, container, false);
        Bundle extras = this.getArguments();
        report_type_id = extras.getString("reports_id");
        pet_id = extras.getString("pet_id");
        pet_owner_contact = extras.getString("pet_owner_contact");
        pet_owner_name = extras.getString("pet_owner_name");
        pet_sex = extras.getString("pet_sex");
        pet_name = extras.getString("pet_name");
        pet_unique_id = extras.getString("pet_unique_id");
        type=extras.getString("type");
        button_type=extras.getString("button_type");

        routine_report_RV = view.findViewById(R.id.routine_report_RV);
        empty_IV=view.findViewById(R.id.empty_IV);


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
        visitTypeData.setPetId(pet_id.substring(0,pet_id.length()-2));
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
        visitTypeData.setPetId(pet_id.substring(0,pet_id.length()-2));
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
        visitTypeData.setPetId(pet_id.substring(0,pet_id.length()-2));
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
        visitTypeData.setPetId(pet_id.substring(0,pet_id.length()-2));
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
                        if (petServiceResponse.getData().getPetClinicVisitList().isEmpty()){
                            empty_IV.setVisibility(View.VISIBLE);
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petClinicVisitListArrayList = petServiceResponse.getData().getPetClinicVisitList();
                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);

                        if (button_type.equals("view")){
                            reportsTypeAdapter = new ReportsTypeAdapter(getContext(), petServiceResponse.getData().getPetClinicVisitList(), this);
                            routine_report_RV.setAdapter(reportsTypeAdapter);
                            reportsTypeAdapter.notifyDataSetChanged();
                        }else if (button_type.equals("update")){
                            updateClinicVisitAdapter = new UpdateClinicVisitAdapter(getContext(), petServiceResponse.getData().getPetClinicVisitList(), this);
                            routine_report_RV.setAdapter(updateClinicVisitAdapter);
                            updateClinicVisitAdapter.notifyDataSetChanged();
                        }

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
                        Toast.makeText(getContext(), "GetPetTestAndXRay", Toast.LENGTH_SHORT).show();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petTestsAndXrayLists = getPetTestAndXRayResponse.getData().getPetTestsAndXrayList();
                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);
                        if (getPetTestAndXRayResponse.getData().getPetTestsAndXrayList().isEmpty()){
                            empty_IV.setVisibility(View.VISIBLE);
                        }else if (button_type.equals("view")){
                            testAndXRayAdpater = new TestAndXRayAdpater(getContext(), getPetTestAndXRayResponse.getData().getPetTestsAndXrayList(), this);
                            routine_report_RV.setAdapter(testAndXRayAdpater);
                            testAndXRayAdpater.notifyDataSetChanged();
                        }else if (button_type.equals("update")){
                            Toast.makeText(getContext(), "update", Toast.LENGTH_SHORT).show();
                            updateXRayAdpater = new UpdateXRayAdpater(getContext(), getPetTestAndXRayResponse.getData().getPetTestsAndXrayList(), this);
                            routine_report_RV.setAdapter(updateXRayAdpater);
                            updateXRayAdpater.notifyDataSetChanged();
                        }

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
                        Toast.makeText(getContext(), "GetLabTest", Toast.LENGTH_SHORT).show();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petLabWorkLists = petLabWorkResponse.getData().getPetLabWorkList();
                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);
                        if (petLabWorkResponse.getData().getPetLabWorkList().isEmpty()){
                            empty_IV.setVisibility(View.VISIBLE);
                        }else if (button_type.equals("view")){
                            labTestReportsAdapter = new LabTestReportsAdapter(getContext(), petLabWorkResponse.getData().getPetLabWorkList(), this);
                            routine_report_RV.setAdapter(labTestReportsAdapter);
                            labTestReportsAdapter.notifyDataSetChanged();
                        }else if (button_type.equals("update")){
                            Toast.makeText(getContext(), "update", Toast.LENGTH_SHORT).show();
                            updateLabTestAdpater = new UpdateLabTestAdpater(getContext(), petLabWorkResponse.getData().getPetLabWorkList(), this);
                            routine_report_RV.setAdapter(updateLabTestAdpater);
                            updateLabTestAdpater.notifyDataSetChanged();
                        }




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
                        Toast.makeText(getContext(), "GetHospitalization", Toast.LENGTH_SHORT).show();
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petHospitalizationsLists = getPetHospitalizationResponse.getData().getPetHospitalizationsList();
                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);
                        if (getPetHospitalizationResponse.getData().getPetHospitalizationsList().isEmpty()) {
                            empty_IV.setVisibility(View.VISIBLE);
                        }else if (button_type.equals("view")){
                            hospitalizationReportsAdapter = new HospitalizationReportsAdapter(getContext(), getPetHospitalizationResponse.getData().getPetHospitalizationsList(), this);
                            routine_report_RV.setAdapter(hospitalizationReportsAdapter);
                            hospitalizationReportsAdapter.notifyDataSetChanged();

                        }else if (button_type.equals("update")){
                            Toast.makeText(getContext(), "update", Toast.LENGTH_SHORT).show();
                            updateHospitalizationAdapter = new UpdateHospitalizationAdapter(getContext(), getPetHospitalizationResponse.getData().getPetHospitalizationsList(), this);
                            routine_report_RV.setAdapter(updateHospitalizationAdapter);
                            updateHospitalizationAdapter.notifyDataSetChanged();

                        }

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
    public void onViewXrayClick(int position) {
        Toast.makeText(getContext(), ""+petTestsAndXrayLists.get(position).getId(), Toast.LENGTH_SHORT).show();
        Intent labIntent = new Intent(getContext(), XRayReportDeatilsActivity.class);
        labIntent.putExtra("pet_id",pet_id);
        labIntent.putExtra("pet_name",pet_name);
        labIntent.putExtra("pet_unique_id",pet_unique_id);
        labIntent.putExtra("pet_sex",pet_sex);
        labIntent.putExtra("pet_owner_name",pet_owner_name);
        labIntent.putExtra("pet_owner_contact",pet_owner_contact);
        labIntent.putExtra("id",petTestsAndXrayLists.get(position).getId());
        labIntent.putExtras(labIntent);
        startActivity(labIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    @Override
    public void onViewLabTestReportsClick(int position) {
        Toast.makeText(getContext(), ""+petLabWorkLists.get(position).getId(), Toast.LENGTH_SHORT).show();

        Intent labIntent = new Intent(getContext(), LabTestReportDeatilsActivity.class);
        labIntent.putExtra("pet_id",pet_id);
        labIntent.putExtra("pet_name",pet_name);
        labIntent.putExtra("pet_unique_id",pet_unique_id);
        labIntent.putExtra("pet_sex",pet_sex);
        labIntent.putExtra("pet_owner_name",pet_owner_name);
        labIntent.putExtra("pet_owner_contact",pet_owner_contact);
        labIntent.putExtra("id",petLabWorkLists.get(position).getId());

        labIntent.putExtras(labIntent);
        startActivity(labIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

    }

    @Override
    public void onViewHospitalizationClick(int position) {
        Toast.makeText(getContext(), ""+petHospitalizationsLists.get(position).getId(), Toast.LENGTH_SHORT).show();
        Intent labIntent = new Intent(getContext(), HospitalizationDetailsActivity.class);
        labIntent.putExtra("pet_id",pet_id);
        labIntent.putExtra("pet_name",pet_name);
        labIntent.putExtra("pet_unique_id",pet_unique_id);
        labIntent.putExtra("pet_sex",pet_sex);
        labIntent.putExtra("pet_owner_name",pet_owner_name);
        labIntent.putExtra("pet_owner_contact",pet_owner_contact);
        labIntent.putExtra("id",petHospitalizationsLists.get(position).getId());
        labIntent.putExtras(labIntent);
        startActivity(labIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    @Override
    public void onUpdateXrayClick(int position) {
        Toast.makeText(getContext(), ""+petTestsAndXrayLists.get(position).getId(), Toast.LENGTH_SHORT).show();
        Intent labIntent = new Intent(getContext(), AddXRayDeatilsActivity.class);
        Bundle data = new Bundle();
        data.putString("type","update");
        data.putString("pet_id",pet_id);
        data.putString("pet_parent",pet_owner_name);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_unique_id",pet_unique_id);
        labIntent.putExtras(data);
        startActivity(labIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    @Override
    public void onUpdateLabTestReportsClick(int position) {


    }

    @Override
    public void onUpdateHospitalizationClick(int position) {

    }


    @Override
    public void onResume() {
        super.onResume();
        if (Config.type.equals("list")){
            Config.type ="";
            getPetClinicVisit();
        }else if (Config.type.equals("XRay")){
            Config.type ="";
            getXrayReport();
        }else if (Config.type.equals("Lab")){
            Config.type ="";
            getLabTestReport();
        }else if (Config.type.equals("Hospitalization")){
            Config.type ="";
            getHospitalizationReport();
        }
    }

    @Override
    public void onViewClick(int position) {
        Toast.makeText(getContext(), ""+petClinicVisitListArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
        Intent viewReportsDeatilsActivityIntent = new Intent(getActivity().getApplication(), ViewReportsDeatilsActivity.class);
        viewReportsDeatilsActivityIntent.putExtra("clinic_id",petClinicVisitListArrayList.get(position).getId());
        viewReportsDeatilsActivityIntent.putExtra("pet_id",pet_id);
        viewReportsDeatilsActivityIntent.putExtra("pet_name",pet_name);
        viewReportsDeatilsActivityIntent.putExtra("pet_unique_id",pet_unique_id);
        viewReportsDeatilsActivityIntent.putExtra("pet_sex",pet_sex);
        viewReportsDeatilsActivityIntent.putExtra("pet_owner_name",pet_owner_name);
        viewReportsDeatilsActivityIntent.putExtra("pet_owner_contact",pet_owner_contact);
        viewReportsDeatilsActivityIntent.putExtras(viewReportsDeatilsActivityIntent);
        startActivity(viewReportsDeatilsActivityIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
    }

    @Override
    public void onUpdateClick(int position) {
        Toast.makeText(getContext(), "Upadte", Toast.LENGTH_SHORT).show();
    }
}
