package com.cynoteck.petofyvet.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.ReportsTypeAdapter;
import com.cynoteck.petofyvet.utils.RegisterRecyclerViewClickListener;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataParams;
import com.cynoteck.petofyvet.params.petReportsRequest.VisitTypeData;
import com.cynoteck.petofyvet.params.petReportsRequest.VisitTypeRequest;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetPetClinicVisitListResponse;
import com.cynoteck.petofyvet.utils.Config;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class ReportListFragment extends Fragment implements ApiResponse, RegisterRecyclerViewClickListener {
    String pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id ,report_type_id;

    RecyclerView routine_report_RV;
    View view;
    ReportsTypeAdapter reportsTypeAdapter;
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


        routine_report_RV = view.findViewById(R.id.routine_report_RV);

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



        return view;

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
                        routine_report_RV.setLayoutManager(linearLayoutManager);
                        routine_report_RV.setNestedScrollingEnabled(false);
                        reportsTypeAdapter  = new ReportsTypeAdapter(getContext(),petServiceResponse.getData().getPetClinicVisitList(),this);
                        routine_report_RV.setAdapter(reportsTypeAdapter);
                        reportsTypeAdapter.notifyDataSetChanged();
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

    }
}
