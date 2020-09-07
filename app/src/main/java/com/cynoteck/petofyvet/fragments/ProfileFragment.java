package com.cynoteck.petofyvet.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.LoginActivity;
import com.cynoteck.petofyvet.activities.OperatingHoursActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView tv,heder;
    RelativeLayout veterian_full_profile_layout,operating_hrs_layout,setings_layout,logout_layout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize();
//        heder=view.findViewById(R.id.heder);
//        heder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences preferences =getActivity().getSharedPreferences("userdetails",0);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.clear();
//                editor.apply();
//                startActivity(new Intent(getActivity(), LoginActivity.class));
//                getActivity().finish();
//            }
//        });
        return view;
    }

    public  void initialize()
    {
        veterian_full_profile_layout=view.findViewById(R.id.veterian_full_profile_layout);
        operating_hrs_layout=view.findViewById(R.id.operating_hrs_layout);
        setings_layout=view.findViewById(R.id.setings_layout);
        logout_layout=view.findViewById(R.id.logout_layout);

        veterian_full_profile_layout.setOnClickListener(this);
        operating_hrs_layout.setOnClickListener(this);
        setings_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.veterian_full_profile_layout:
                break;

            case R.id.operating_hrs_layout:
                startActivity(new Intent(getActivity(), OperatingHoursActivity.class));
                break;

            case R.id.setings_layout:

                break;

            case R.id.logout_layout:
                SharedPreferences preferences =getActivity().getSharedPreferences("userdetails",0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

        }
    }
}
