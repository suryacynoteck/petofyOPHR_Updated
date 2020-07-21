package com.cynoteck.petofyvet.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.loginparams.Loginparams;
import com.cynoteck.petofyvet.response.loginRegisterResponse.LoginRegisterResponse;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {
    TextInputEditText first_name_updt,last_name_updt,email_updt,phone_updt,address_updt,
                      postal_code_updt,website_updt,social_media_url_updt,registration_num_updt,
                      vet_qualification_updt;
    Spinner country_spnr_updt,state_spnr_updt,city_spnr_updt,pet_category_updt,service_category_updt;
    Button update_profile;
    View view;

    String[] country = new String[]{"Select Country", "India"};
    String[] city = new String[]{"Select City","Uttarakhand", "Uttar Pradesh", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Kolkata"};
    String[] state = new String[]{"Select State","West Bengal", "Maharastra"};
    String[] petCategory = new String[]{"Select Category","Dog", "Cat"};
    String[] serviceCategory = new String[]{"Select Service Category","Consultaion", "Grooming","Hostel","Training","Products"};


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        setCountrySpinner();
        setCitySpinner();
        setStateSpinner();
        setCategory();
        setServiceCategory();
        return view;
    }




    private void init() {
        //TextInputEditText
        first_name_updt=view.findViewById(R.id.first_name_updt);
        last_name_updt=view.findViewById(R.id.last_name_updt);
        email_updt=view.findViewById(R.id.email_updt);
        phone_updt=view.findViewById(R.id.phone_updt);
        address_updt=view.findViewById(R.id.address_updt);
        postal_code_updt=view.findViewById(R.id.postal_code_updt);
        website_updt=view.findViewById(R.id.website_updt);
        social_media_url_updt=view.findViewById(R.id.social_media_url_updt);
        registration_num_updt=view.findViewById(R.id.registration_num_updt);
        vet_qualification_updt=view.findViewById(R.id.vet_qualification_updt);

        //Spinner
        country_spnr_updt=view.findViewById(R.id.country_spnr_updt);
        state_spnr_updt=view.findViewById(R.id.state_spnr_updt);
        city_spnr_updt=view.findViewById(R.id.city_spnr_updt);
        pet_category_updt=view.findViewById(R.id.pet_category_updt);
        service_category_updt=view.findViewById(R.id.service_category_updt);

        country_spnr_updt.setOnItemSelectedListener(this);
        state_spnr_updt.setOnItemSelectedListener(this);
        city_spnr_updt.setOnItemSelectedListener(this);
        pet_category_updt.setOnItemSelectedListener(this);
        service_category_updt.setOnItemSelectedListener(this);

        //Button
        update_profile=view.findViewById(R.id.update_profile);
        update_profile.setOnClickListener(this);

    }

    private void setCountrySpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        country_spnr_updt.setAdapter(aa);
    }

    private void setCitySpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,city);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        city_spnr_updt.setAdapter(aa);

    }

    private void setStateSpinner() {

        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,state);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        state_spnr_updt.setAdapter(aa);
    }

    private void setCategory() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petCategory);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        pet_category_updt.setAdapter(aa);
    }

    private void setServiceCategory() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,serviceCategory);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        service_category_updt.setAdapter(aa);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        String selectItem=item;
        Log.d("SelectItem",""+selectItem);
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.update_profile:
                break;

        }
    }






}
