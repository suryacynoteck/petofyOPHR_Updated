package com.cynoteck.petofyvet.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.AddPetRegister;
import com.cynoteck.petofyvet.activities.GetPetDetailsActivity;
import com.cynoteck.petofyvet.adapters.RegisterPetAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataParams;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetPetListResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.PetList;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.cynoteck.petofyvet.utils.RegisterRecyclerViewClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.StringTokenizer;

import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetRegisterFragment extends Fragment implements  ApiResponse, RegisterRecyclerViewClickListener,View.OnClickListener{
    View view;
    Context context;
    Methods methods;
    CardView materialCardView;
    RecyclerView register_pet_RV;
    ImageView register_add_TV;
    private ArrayList<PetList> categoryRecordArrayList;
    RegisterPetAdapter registerPetAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public PetRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_pet_register, container, false);
        init();
        getPetList();

        return view;
    }

    private void init() {
        methods = new Methods(context);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);

        materialCardView = view.findViewById(R.id.toolbar);
        register_pet_RV=view.findViewById(R.id.register_pet_RV);
        register_add_TV=view.findViewById(R.id.register_add_TV);
        methods = new Methods(getContext());
        register_add_TV.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_add_TV:
                startActivity(new Intent(getActivity(),AddPetRegister.class));
                break;
        }

    }

    private void getPetList() {
 //       methods.showCustomProgressBarDialog(getContext());
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber("1");//0
        getPetDataParams.setPageSize("2");//0
        getPetDataParams.setSearch_Data("");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetList");
        Log.e("DATALOG","check1=> "+getPetDataRequest);


    }


    @Override
    public void onResponse(Response response, String key) {
//        methods.customProgressDismiss();
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
                        register_pet_RV.setLayoutManager(linearLayoutManager);
                        registerPetAdapter  = new RegisterPetAdapter(getContext(),getPetListResponse.getData().getPetList(),this);
                        register_pet_RV.setAdapter(registerPetAdapter);
                        registerPetAdapter.notifyDataSetChanged();
                        categoryRecordArrayList = getPetListResponse.getData().getPetList();
                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);

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
        //methods.customProgressDismiss();

    }

    @Override
    public void onProductClick(int position) {
        Log.d("positionssss",""+position);
        Toast.makeText(getActivity(), categoryRecordArrayList.get(position).getId(), Toast.LENGTH_SHORT).show();
        Log.d("pet_id",""+categoryRecordArrayList.get(position).getId());

        StringTokenizer tokens = new StringTokenizer(categoryRecordArrayList.get(position).getId(), ".");
        String first = tokens.nextToken();// this will contain "Fruit"

        Intent intent=new Intent(getActivity(), GetPetDetailsActivity.class);
        intent.putExtra("pet_id",first);
        startActivity(intent);
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