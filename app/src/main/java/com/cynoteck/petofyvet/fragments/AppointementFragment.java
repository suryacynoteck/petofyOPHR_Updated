package com.cynoteck.petofyvet.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.DateListAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.response.appointmentResponse.GetAppointmentDates;
import com.cynoteck.petofyvet.response.appointmentResponse.GetAppointmentResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointementFragment extends Fragment implements ApiResponse {
    View view;
    RecyclerView date_day_RV;
    DateListAdapter dateListAdapter;
    ArrayList<GetAppointmentDates> getAppointmentDates;
    Methods methods;
    private ShimmerFrameLayout mShimmerViewContainer;

    public AppointementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_appointement, container, false);

        methods = new Methods(getContext());
        date_day_RV = view.findViewById(R.id.date_day_RV);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        if (methods.isInternetOn()){
            getAppointment();
        }else {
            methods.DialogInternet();
        }


        return view;

    }

    private void getAppointment() {
        ApiService<GetAppointmentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getAppointment(Config.token), "GetAppointment");

    }

    @Override
    public void onResponse(Response arg0, String key) {
        Log.d("Response", arg0.toString());

        switch (key){

            case  "GetAppointment":
                try {
                    GetAppointmentResponse  getAppointmentResponse  = (GetAppointmentResponse) arg0.body();
                    Log.d("GetLabTypeList", getAppointmentResponse.toString());
                    int responseCode = Integer.parseInt(getAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        mShimmerViewContainer.setVisibility(View.GONE);
                        mShimmerViewContainer.stopShimmerAnimation();
                        dateListAdapter= new DateListAdapter(Arrays.asList(getAppointmentResponse.getData()),getContext());
                        date_day_RV.setHasFixedSize(true);
                        date_day_RV.setLayoutManager(new LinearLayoutManager(getContext()));
                        date_day_RV.setAdapter(dateListAdapter);

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


    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();

    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }

}
