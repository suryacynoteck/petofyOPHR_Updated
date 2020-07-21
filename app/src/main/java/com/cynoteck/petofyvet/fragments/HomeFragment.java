package com.cynoteck.petofyvet.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.DashBoardActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    Context context;
    View view;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }
}
