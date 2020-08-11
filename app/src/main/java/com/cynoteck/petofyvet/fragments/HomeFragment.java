package com.cynoteck.petofyvet.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.AddNewPet;
import com.cynoteck.petofyvet.activities.CommonActivity;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.utils.Methods;

import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener, ApiResponse {
    RecyclerView pet_list_RV;
    Context context;
    View view;
    ImageView reports_IV,appointments;
    Button add_new_visit;
    RelativeLayout mainHome;
    Methods methods;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        init();
        return view;
    }



    private void init() {
        methods = new Methods(context);

        reports_IV = view.findViewById(R.id.reports_IV);
        mainHome=view.findViewById(R.id.mainHome);
        appointments=view.findViewById(R.id.appointments);
        pet_list_RV=view.findViewById(R.id.pet_id_TV);
        add_new_visit=view.findViewById(R.id.add_new_visit);
        reports_IV.setOnClickListener(this);
        appointments.setOnClickListener(this);
        add_new_visit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reports_IV:
                mainHome.setVisibility(View.GONE);
                ReportsFragment profileFragment = new ReportsFragment();
                replaceFragment(profileFragment);
                break;
            case R.id.appointments:
                startActivity(new Intent(getActivity(), CommonActivity.class));
                break;
            case R.id.add_new_visit:
                startActivity(new Intent(getActivity(), AddNewPet.class));
                break;
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();


    }

    @Override
    public void onError(Throwable t, String key) {

    }
}