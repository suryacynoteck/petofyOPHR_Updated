package com.cynoteck.petofyvet.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.utils.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    View view;
   TextView tv,heder,vet_name_TV,vet_email_TV,vet_study_TV,vet_id_TV;
   ImageView vet_profile_pic;
   SwitchCompat online_switch;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        vet_email_TV = view.findViewById(R.id.vet_email_TV);
        vet_name_TV = view.findViewById(R.id.vet_name_TV);
        vet_study_TV = view.findViewById(R.id.vet_study_TV);
        vet_id_TV = view.findViewById(R.id.vet_id_TV);
        vet_profile_pic = view.findViewById(R.id.vet_profile_pic);
        online_switch = view.findViewById(R.id.online_switch);


        Glide.with(this)
                .load(Config.user_Veterian_url)
                .into(vet_profile_pic);
        vet_name_TV.setText(Config.user_Veterian_name);
        vet_email_TV.setText(Config.user_Veterian_emial);
        vet_study_TV.setText(Config.user_Veterian_study);
        vet_id_TV.setText(Config.user_Veterian_id);

        if (Config.user_Veterian_online.equals("true")){
            online_switch.setChecked(true);
        }

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
