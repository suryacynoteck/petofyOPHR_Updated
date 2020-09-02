package com.cynoteck.petofyvet.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointementFragment extends Fragment {
    View view;
    RecyclerView date_day_RV;

    public AppointementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_appointement, container, false);

        date_day_RV = view.findViewById(R.id.date_day_RV);


        return view;

    }
}
