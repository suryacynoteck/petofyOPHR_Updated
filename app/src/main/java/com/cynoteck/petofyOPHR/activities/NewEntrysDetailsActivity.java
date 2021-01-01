package com.cynoteck.petofyOPHR.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.fragments.NewEntrysListFragment;
import com.cynoteck.petofyOPHR.fragments.ReportListFragment;

public class NewEntrysDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView back_arrow_IV;
    String pet_unique_id, pet_name,pet_sex,pet_age,pet_DOB,pet_owner_name,pet_owner_contact,pet_id ,
            report_type_id,button_text,button_type,pet_encrypted_id;
    Bundle data = new Bundle();
    TextView pet_name_TV,pet_sex_TV,pet_id_TV,pet_owner_name_TV,pet_owner_phone_no_TV,
            reports_headline_TV,add_text_button;
    LinearLayout addPrescriptionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entrys_details);

        Bundle extras = getIntent().getExtras();
        report_type_id = extras.getString("reports_id");
        pet_id = extras.getString("pet_id");
        Log.d("akakkakakak",""+pet_id);
        pet_owner_contact = extras.getString("pet_owner_contact");
        pet_owner_name = extras.getString("pet_parent");
        pet_sex = extras.getString("pet_sex");
        pet_age = extras.getString(pet_age);
        pet_name = extras.getString("pet_name");
        button_type=extras.getString("button_type");
        pet_DOB=extras.getString("pet_DOB");
        pet_encrypted_id=extras.getString("pet_encrypted_id");

        Log.e("jajajjaj",""+pet_DOB+" "+pet_encrypted_id);
        pet_unique_id = extras.getString("pet_unique_id");
        button_text = extras.getString("add_button_text");


        reports_headline_TV = findViewById(R.id.reports_headline_TV);
        back_arrow_IV =findViewById(R.id.back_arrow_IV);
        pet_name_TV = findViewById(R.id.pet_name_TV);
        pet_sex_TV = findViewById(R.id.pet_sex_TV);
        pet_id_TV = findViewById(R.id.pet_id_TV);
        add_text_button = findViewById(R.id.add_text_button);
        pet_owner_name_TV = findViewById(R.id.pet_owner_name_TV);
        pet_owner_phone_no_TV = findViewById(R.id.pet_owner_phone_no_TV);
        addPrescriptionButton = findViewById(R.id.addPrescriptionButton);


        addPrescriptionButton.setOnClickListener(this);

        if((button_text.equals("RecentVisit"))||(button_text.equals("petHistory")))
            addPrescriptionButton.setVisibility(View.GONE);

        add_text_button.setText(button_text);
        pet_name_TV.setText(pet_name+"("+pet_sex+")");
        pet_owner_name_TV.setText(pet_owner_name);
        pet_id_TV.setText(pet_unique_id);
        pet_owner_name_TV.setText(pet_owner_name);
        pet_name_TV.setText(pet_name);

        data.putString("pet_id",pet_id);
        data.putString("pet_name",pet_name);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_owner_name",pet_owner_name);
        data.putString("pet_owner_contact",pet_owner_contact);
        data.putString("pet_encrypted_id",pet_encrypted_id);
        data.putString("pet_DOB",pet_DOB);


        switch (report_type_id){

            case "1.0":

                reports_headline_TV.setText("ROUTINE REPORT");
                data.putString("reports_id","1");
                data.putString("type","list");

                NewEntrysListFragment fragment1 = new NewEntrysListFragment();
                fragment1.setArguments(data);
                FragmentTransaction selectPetReportsFragmentFT = getSupportFragmentManager().beginTransaction();
                selectPetReportsFragmentFT.replace(R.id.report_type_frame, fragment1);
                selectPetReportsFragmentFT.commit();

                break;

            case "2.0":

                reports_headline_TV.setText("HEALTH PROBLEM");
                data.putString("reports_id","2");
                data.putString("type","list");

                NewEntrysListFragment fragment2 = new NewEntrysListFragment();
                fragment2.setArguments(data);
                FragmentTransaction fragment2FT = getSupportFragmentManager().beginTransaction();
                fragment2FT.replace(R.id.report_type_frame, fragment2);
                fragment2FT.commit();

                break;


            case "4.0":

                reports_headline_TV.setText("IMMUNIZATION");
                data.putString("reports_id","4");
                data.putString("type","list");

                NewEntrysListFragment fragment3 = new NewEntrysListFragment();
                fragment3.setArguments(data);
                FragmentTransaction fragment3FT = getSupportFragmentManager().beginTransaction();
                fragment3FT.replace(R.id.report_type_frame, fragment3);
                fragment3FT.commit();

                break;


            case "5.0":

                reports_headline_TV.setText("Deworming");
                data.putString("reports_id","5");
                data.putString("type","list");

                NewEntrysListFragment fragment4 = new NewEntrysListFragment();
                fragment4.setArguments(data);
                FragmentTransaction fragment4FT = getSupportFragmentManager().beginTransaction();
                fragment4FT.replace(R.id.report_type_frame, fragment4);
                fragment4FT.commit();


                break;


            case "6.0":

                reports_headline_TV.setText("OTHER REPORT");
                data.putString("reports_id","6");
                data.putString("type","list");

                NewEntrysListFragment fragment5 = new NewEntrysListFragment();
                fragment5.setArguments(data);
                FragmentTransaction fragment5FT = getSupportFragmentManager().beginTransaction();
                fragment5FT.replace(R.id.report_type_frame, fragment5);
                fragment5FT.commit();

                break;


            case "7.0":

                if (button_type.equals("update")){
                    data.putString("button_type",button_type);
                }else if (button_type.equals("view")){
                    data.putString("button_type","view");
                }
                reports_headline_TV.setText("Test/X-Ray Report");
                data.putString("reports_id","7");
                data.putString("type","XRay");

                ReportListFragment fragment6 = new ReportListFragment();
                fragment6.setArguments(data);
                FragmentTransaction fragment6FT = getSupportFragmentManager().beginTransaction();
                fragment6FT.replace(R.id.report_type_frame, fragment6);
                fragment6FT.commit();
                break;

            case "8.0":
                if (button_type.equals("update")){
                    data.putString("button_type",button_type);
                }else if (button_type.equals("view")){
                    data.putString("button_type","view");
                }
                reports_headline_TV.setText("Lab Tests");
                data.putString("reports_id","8");
                data.putString("type","LabTest");

                ReportListFragment fragment7 = new ReportListFragment();
                fragment7.setArguments(data);
                FragmentTransaction fragment7FT = getSupportFragmentManager().beginTransaction();
                fragment7FT.replace(R.id.report_type_frame, fragment7);
                fragment7FT.commit();

                break;

            case "9.0":
                if (button_type.equals("update")){
                    data.putString("button_type",button_type);
                }else if (button_type.equals("view")){
                    data.putString("button_type","view");
                }
                reports_headline_TV.setText("Hospitalization & Surgeries");
                data.putString("reports_id","9");
                data.putString("type","Hospitalization");

                ReportListFragment fragment8 = new ReportListFragment();
                fragment8.setArguments(data);
                FragmentTransaction fragment8FT = getSupportFragmentManager().beginTransaction();
                fragment8FT.replace(R.id.report_type_frame, fragment8);
                fragment8FT.commit();

                break;

            case "10.0":
                if (button_type.equals("update")){
                    data.putString("button_type",button_type);
                }else if (button_type.equals("view")){
                    data.putString("button_type","view");
                }
                reports_headline_TV.setText("Clinic Visit Report");
                data.putString("reports_id","10");
                data.putString("type","ClinicVisitReport");

                ReportListFragment fragment9 = new ReportListFragment();
                fragment9.setArguments(data);
                FragmentTransaction fragment9FT = getSupportFragmentManager().beginTransaction();
                fragment9FT.replace(R.id.report_type_frame, fragment9);
                fragment9FT.commit();

                break;

            case "11.0":

                reports_headline_TV.setText("Recent Visit");
                data.putString("reports_id","11");
                data.putString("type","RecentVisit");
                NewEntrysListFragment fragment11 = new NewEntrysListFragment();
                fragment11.setArguments(data);
                FragmentTransaction fragment11FT = getSupportFragmentManager().beginTransaction();
                fragment11FT.replace(R.id.report_type_frame, fragment11);
                fragment11FT.commit();

                break;

            case "12.0":
                reports_headline_TV.setText("Recent Visit");
                data.putString("reports_id","12");
                data.putString("type","petHistory");
                NewEntrysListFragment fragment12 = new NewEntrysListFragment();
                fragment12.setArguments(data);
                FragmentTransaction fragment12FT = getSupportFragmentManager().beginTransaction();
                fragment12FT.replace(R.id.report_type_frame, fragment12);
                fragment12FT.commit();
                break;



        }

        back_arrow_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addPrescriptionButton:
                if(button_text.equals("Test/X-rays"))
                {
                    TestXrayDialog();
                }
                else if(button_text.equals("Lab Work"))
                {
                    LabWorkDialog();
                }
                else if(button_text.equals("Hospitalization"))
                {
                    HospitalizationDialog();
                }
                else if(button_text.equals("Clinic Visits"))
                    clinicDialog();
                break;

        }
    }

    public void clinicDialog()
    {
        Intent petDetailsIntent = new Intent(this.getApplication(), AddClinicActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id",pet_id);
        data.putString("pet_name",pet_name);
        data.putString("pet_parent",pet_owner_name);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("pet_DOB",pet_DOB);
        data.putString("pet_encrypted_id",pet_encrypted_id);
        data.putString("nature_of_visit","");
        data.putString("visit_dt","");
        data.putString("visit_description","");
        data.putString("remarks","");
        data.putString("visit_weight","");
        data.putString("visit_temparature","");
        data.putString("dt_of_illness","");
        data.putString("pet_diognosis","");
        data.putString("next_dt","");
        data.putString("appointment","");
        data.putString("appoint_link", "");
        data.putString("toolbar_name","ADD CLINIC VISITS");
        petDetailsIntent.putExtras(data);
        startActivity(petDetailsIntent);

    }

    public void TestXrayDialog()
    {
        Intent xRayIntent = new Intent(this,AddXRayDeatilsActivity.class);
        Bundle data = new Bundle();
        data.putString("type","add");
        data.putString("pet_id",pet_id);
        data.putString("pet_parent",pet_owner_name);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("type","Add Test/X-rays");
        xRayIntent.putExtras(data);
        startActivity(xRayIntent);
    }

    public void LabWorkDialog()
    {
        Intent labWorkIntent = new Intent(this,AddLabWorkDeatilsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id",pet_id);
        data.putString("pet_parent",pet_owner_name);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("lab_type","");
        data.putString("type","Add");

        labWorkIntent.putExtras(data);
        startActivity(labWorkIntent);
    }

    public void HospitalizationDialog()
    {
        Intent hospitalIntent = new Intent(this,AddHospitalizationDeatilsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id",pet_id);
        data.putString("pet_parent",pet_owner_name);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("type","Add");
        hospitalIntent.putExtras(data);
        startActivity(hospitalIntent);
    }
}