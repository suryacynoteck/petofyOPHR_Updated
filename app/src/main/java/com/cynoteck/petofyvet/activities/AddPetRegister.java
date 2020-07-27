package com.cynoteck.petofyvet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class AddPetRegister extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    AppCompatSpinner add_pet_type,add_pet_sex,add_pet_breed,add_pet_color,add_pet_size;
    TextInputLayout pet_name_layout,pet_parent_name_layout,pet_contact_number_layout,pet_description_layout,
                    pet_address_layout;
    TextInputEditText pet_name,pet_parent_name,pet_contact_number,pet_description,pet_address;
    ImageView pet_profile_image,service_cat_img_one,service_cat_img_two,service_cat_img_three,service_cat_img_four,
            service_cat_img_five;
    Button pet_submit;
    String strPetName="",strPetParentName="",strPetContactNumber="",strPetDescription="",strPetAdress="",
            strSpnerItemPetNm="",getStrSpnerItemPetNmId="";
    Methods methods;
    ArrayList<String> petType;

    HashMap<String,String> petTypeHashMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_register);
        init();
        methods=new Methods(this);
        if(methods.isInternetOn())
        {
            petType();
        }
        else
        {
            methods.DialogInternet();
        }

    }

    private void petType() {
        methods.showCustomProgressBarDialog(this);
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(Config.token), "GetPetTypes");
    }

    private void init() {
        //Spinner
        add_pet_type=findViewById(R.id.add_pet_type);
        add_pet_sex=findViewById(R.id.add_pet_sex);
        add_pet_breed=findViewById(R.id.add_pet_breed);
        add_pet_color=findViewById(R.id.add_pet_color);
        add_pet_size=findViewById(R.id.add_pet_size);

        //TextInputLayout
        pet_name_layout=findViewById(R.id.pet_name_layout);
        pet_parent_name_layout=findViewById(R.id.pet_parent_name_layout);
        pet_contact_number_layout=findViewById(R.id.pet_contact_number_layout);
        pet_description_layout=findViewById(R.id.pet_description_layout);
        pet_address_layout=findViewById(R.id.pet_address_layout);

        //TextInputEditText
        pet_name=findViewById(R.id.pet_name);
        pet_parent_name=findViewById(R.id.pet_parent_name);
        pet_contact_number=findViewById(R.id.pet_contact_number);
        pet_description=findViewById(R.id.pet_description);
        pet_address=findViewById(R.id.pet_address);

        //ImageView
        pet_profile_image=findViewById(R.id.pet_profile_image);
        service_cat_img_one=findViewById(R.id.service_cat_img_one);
        service_cat_img_two=findViewById(R.id.service_cat_img_two);
        service_cat_img_three=findViewById(R.id.service_cat_img_three);
        service_cat_img_four=findViewById(R.id.service_cat_img_four);
        service_cat_img_five=findViewById(R.id.service_cat_img_five);

        //Button
        pet_submit=findViewById(R.id.pet_submit);
        pet_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pet_submit:
                strPetName= pet_name.getText().toString().trim();
                strPetParentName = pet_parent_name.getText().toString().trim();
                strPetContactNumber = pet_contact_number.getText().toString().trim();
                strPetDescription = pet_description.getText().toString().trim();
                strPetAdress = pet_address.getText().toString().trim();

                if(strPetName.isEmpty())
                {
                    pet_name.setError("Enter Pet Name");
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                }
                else if(strPetParentName.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError("Enter Parent Name");
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                }
                else if(strPetContactNumber.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError("Enter Contact Number");
                    pet_description.setError(null);
                    pet_address.setError(null);
                }
                else if(strPetDescription.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError("Enter Description");
                    pet_address.setError(null);
                }
                else if(strPetAdress.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError("Enter Pet Address");
                }
            break;

        }

    }

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();
        switch (key)
        {
            case "GetPetTypes":
                try {
                    Log.d("GetPetTypes",arg0.body().toString());
                    PetTypeResponse petTypeResponse = (PetTypeResponse) arg0.body();
                    int responseCode = Integer.parseInt(petTypeResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petType=new ArrayList<>();
                        petType.add("Select Pet Type");
                        Log.d("lalal",""+petTypeResponse.getData().size());
                        for(int i=0; i<petTypeResponse.getData().size(); i++){
                            Log.d("petttt",""+petTypeResponse.getData().get(i).getPetType1());
                            petType.add(petTypeResponse.getData().get(i).getPetType1());
                            petTypeHashMap.put(petTypeResponse.getData().get(i).getPetType1(),petTypeResponse.getData().get(i).getId());
                        }
                        setPetTypeSpinner();

                    }else if (responseCode==614){
                        Toast.makeText(this, petTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
        }

    }

    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type.setAdapter(aa);
        add_pet_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnerItemPetNm=item;
                Log.d("spnerType",""+strSpnerItemPetNm);
                getStrSpnerItemPetNmId=petTypeHashMap.get(strSpnerItemPetNm);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}