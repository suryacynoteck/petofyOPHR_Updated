package com.cynoteck.petofyvet.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.AllVisitsActivity;
import com.cynoteck.petofyvet.activities.UpcomingVisitsActivity;
import com.cynoteck.petofyvet.utils.Config;


public class ReportSelectionFragment extends Fragment implements View.OnClickListener{

    Button allPatentReport,visistRegister,upcomingVisits;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_report_selection, container, false);
        init();
        return view;
    }

    public void init()
    {
        allPatentReport=view.findViewById(R.id.allPatentReport);
        visistRegister=view.findViewById(R.id.visistRegister);
        upcomingVisits=view.findViewById(R.id.upcomingVisits);

        allPatentReport.setOnClickListener(this);
        visistRegister.setOnClickListener(this);
        upcomingVisits.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.allPatentReport:
                ReportsFragment profileFragment = new ReportsFragment();
                replaceFragment(profileFragment);
                break;
            case R.id.visistRegister:
                startActivity(new Intent(getActivity(), AllVisitsActivity.class));
                break;
            case R.id.upcomingVisits:
                startActivity(new Intent(getActivity(), UpcomingVisitsActivity.class));
                break;
        }


    }

    private void replaceFragment(Fragment fragment) {
        Config.count = 3;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}