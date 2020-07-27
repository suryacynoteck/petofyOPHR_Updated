package com.cynoteck.petofyvet.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Response;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.AddPetRegister;
import com.cynoteck.petofyvet.adapters.petRegisterAdapter.RegisterPetAdapter;
import com.cynoteck.petofyvet.adapters.petRegisterAdapter.RegisterRecyclerViewClickListener;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.getPetList.getPetRequest.GetPetDataParams;
import com.cynoteck.petofyvet.params.getPetList.getPetRequest.GetPetDataRequest;
import com.cynoteck.petofyvet.response.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyvet.response.getPetListResponse.PetList;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetRegisterFragment extends Fragment implements  ApiResponse, RegisterRecyclerViewClickListener,View.OnClickListener{
    View view;
    Context context;
    Methods methods;
    CardView materialCardView;
    RecyclerView register_pet_RV;
    TextView register_add_TV;
    private ArrayList<PetList> categoryRecordArrayList;
    RegisterPetAdapter registerPetAdapter;

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
                        register_pet_RV.setLayoutManager(linearLayoutManager);
                        registerPetAdapter  = new RegisterPetAdapter(getContext(),getPetListResponse.getData().getPetList(),this);
                        categoryRecordArrayList = getPetListResponse.getData().getPetList();
                        register_pet_RV.setAdapter(registerPetAdapter);
                        registerPetAdapter.notifyDataSetChanged();
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
     Log.d("positionssss",""+position);
        Toast.makeText(getActivity(), categoryRecordArrayList.get(position).getPetUniqueId(), Toast.LENGTH_SHORT).show();
        categoryRecordArrayList.get(position).getPetUniqueId();
        Log.d("ajajjaj",""+categoryRecordArrayList.get(position).getPetUniqueId());
    }


}
