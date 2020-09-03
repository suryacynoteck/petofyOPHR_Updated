package com.cynoteck.petofyvet.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.cynoteck.petofyvet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    View view;
   TextView tv,heder;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
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

}
