package com.cynoteck.petofyvet.fragments;

import android.content.Intent;
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
import com.cynoteck.petofyvet.activities.SelectPetReportsActivity;
import com.cynoteck.petofyvet.adapters.ReportsAdapter;
import com.cynoteck.petofyvet.adapters.petRegisterAdapter.RegisterRecyclerViewClickListener;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataParams;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetPetListResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.PetList;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReportsFragment extends Fragment implements ApiResponse,RegisterRecyclerViewClickListener {
    View view;
    Methods methods;
    CardView materialCardView;
    RecyclerView petList_RV;
    ReportsAdapter reportsAdapter;
    private ArrayList<PetList> categoryRecordArrayList;
    private ShimmerFrameLayout mShimmerViewContainer;


    public ReportsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_reports, container, false);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        materialCardView = view.findViewById(R.id.toolbar);
        petList_RV=view.findViewById(R.id.petList_RV);
        methods = new Methods(getContext());
        getPetList();

        return view;
    }

    private void getPetList() {
        methods.showCustomProgressBarDialog(getActivity());
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber("0");
        getPetDataParams.setPageSize("0");
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetList");
        Log.e("DATALOG","check1=> "+getPetDataRequest);


    }



    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        switch (key){

            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) response.body();
                    Log.d("DATALOG", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        petList_RV.setLayoutManager(linearLayoutManager);
                        reportsAdapter  = new ReportsAdapter(getContext(),getPetListResponse.getData().getPetList(),this);
                        categoryRecordArrayList = getPetListResponse.getData().getPetList();
                        petList_RV.setAdapter(reportsAdapter);
                        reportsAdapter.notifyDataSetChanged();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        mShimmerViewContainer.stopShimmerAnimation();

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


    @Override
    public void onProductClick(int position) {
        categoryRecordArrayList.get(position).getPetUniqueId();
        Intent selectReportsIntent = new Intent(getActivity().getApplication(), SelectPetReportsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id",categoryRecordArrayList.get(position).getId());
        data.putString("pet_name",categoryRecordArrayList.get(position).getPetName());
        data.putString("pet_unique_id",categoryRecordArrayList.get(position).getPetUniqueId());
        data.putString("pet_sex",categoryRecordArrayList.get(position).getPetSex());
        data.putString("pet_owner_name",categoryRecordArrayList.get(position).getPetParentName());
        data.putString("pet_owner_contact",categoryRecordArrayList.get(position).getContactNumber());

        selectReportsIntent.putExtras(data);
        startActivity(selectReportsIntent);
        getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

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
