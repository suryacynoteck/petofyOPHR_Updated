package com.cynoteck.petofyvet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.ReportsAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.getPetRequest.GetPetDataParams;
import com.cynoteck.petofyvet.params.getPetRequest.GetPetDataRequest;
import com.cynoteck.petofyvet.response.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements ApiResponse {
    View view;
    Methods methods;
    CardView materialCardView;
    RecyclerView petList_RV;
    ReportsAdapter reportsAdapter;

    public ReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reports, container, false);

        materialCardView = view.findViewById(R.id.toolbar);
        petList_RV=view.findViewById(R.id.petList_RV);
        methods = new Methods(getContext());
        getPetList();

        return view;
    }

    private void getPetList() {
        methods.showCustomProgressBarDialog(getContext());
        GetPetDataParams getPetDataParams = new GetPetDataParams();
        getPetDataParams.setPageNumber("1");
        getPetDataParams.setPageSize("2");
        getPetDataParams.setSearch_Data("");
        GetPetDataRequest getPetDataRequest = new GetPetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetList");
        Log.e("DATALOG","check1=> "+getPetDataRequest);


    }

    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        Log.e("sdjhfgsjkdfgsdfj","slfhksdfgsighf");
        switch (key){
            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(0).getPetUniqueId()));
                    Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(1).getPetUniqueId()));
                    Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(2).getPetUniqueId()));
                    Log.d("DATALOG", String.valueOf(getPetListResponse.getData().getPetList().get(3).getPetUniqueId()));


                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petList_RV.setLayoutManager(linearLayoutManager);
                        reportsAdapter  = new ReportsAdapter(getContext(),getPetListResponse.getData().getPetList());
                        petList_RV.setAdapter(reportsAdapter);
                        reportsAdapter.notifyDataSetChanged();
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
        methods.customProgressDismiss();

    }
}
