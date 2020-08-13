package com.cynoteck.petofyvet.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.utils.Methods;

import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener, ApiResponse {
    RecyclerView pet_list_RV;
    Context context;
    View view;
    LinearLayout add_new_visit;
    ImageView reports_IV;
    ConstraintLayout mainHome;
    Methods methods;
    CardView reports_CV, all_staff_CV;

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
        reports_CV=view.findViewById(R.id.reports_CV);
        all_staff_CV = view.findViewById(R.id.staff_CV);
        mainHome=view.findViewById(R.id.mainHome);
        pet_list_RV=view.findViewById(R.id.pet_id_TV);
        add_new_visit=view.findViewById(R.id.add_new_visit);
        reports_CV.setOnClickListener(this);
        all_staff_CV.setOnClickListener(this);
        add_new_visit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reports_CV:

                ReportsFragment profileFragment = new ReportsFragment();
                replaceFragment(profileFragment);

                break;

            case R.id.staff_CV:

                AllStaffFragment allStaffFragment = new AllStaffFragment();
                replaceFragment(allStaffFragment);

                break;

            case R.id.add_new_visit:

            AddNewPetFragment addNewPetFragment = new AddNewPetFragment();
            replaceFragment(addNewPetFragment);

            break;
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
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