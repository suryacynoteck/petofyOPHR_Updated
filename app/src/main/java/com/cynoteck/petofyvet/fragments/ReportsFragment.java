package com.cynoteck.petofyvet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.getPetList.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyvet.params.getPetList.getPetRequest.GetPetDataParams;
import com.cynoteck.petofyvet.params.getPetList.getPetRequest.GetPetDataRequest;
import com.cynoteck.petofyvet.utils.Methods;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements ApiResponse {
    View view;
    Methods methods;
    CardView materialCardView;

    public ReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reports, container, false);

        materialCardView = view.findViewById(R.id.toolbar);
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
        service.get( this, ApiClient.getApiInterface().getPetList("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjVkMGJkNmQ0LTIzNjQtNGU1Ny04Yzk1LTA3MzZlYTgwMDIyMSIsIm5iZiI6MTU5NTUxMjk2MywiZXhwIjoxNTk1NTU2MTYzLCJpYXQiOjE1OTU1MTI5NjN9.iCYulYZKvfxyL-gigO1Gz4hj-QPrJ2E46l7rl9pbtW8",getPetDataRequest), "GetPetList");
        Log.e("DATALOG","check1=> "+getPetDataRequest);


    }

    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        Log.e("sdjhfgsjkdfgsdfj","slfhksdfgsighf");
        switch (key){
            case "GetPetList":
                try {
                    Log.d("DATALOG",response.body().toString());

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
