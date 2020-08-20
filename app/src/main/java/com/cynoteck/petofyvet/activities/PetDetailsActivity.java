
package com.cynoteck.petofyvet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiResponse;

import retrofit2.Response;

public class PetDetailsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    String pet_id,pet_name,patent_name,pet_bread,pet_unique_id="",pet_sex="";
    TextView pet_nameTV, pet_parentNameTV;
    ImageView back_arrow_IV;
    RelativeLayout test_xray,manage_pet_lab_work,hospitalization_surgeries,recent_visits,print_id_card,
                  hospitalization_surgery,view_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        Bundle extras = getIntent().getExtras();
        pet_id=extras.getString("pet_id");
        pet_name=extras.getString("pet_name");
        patent_name=extras.getString("pet_parent");
        pet_unique_id=extras.getString("pet_unique_id");
        pet_sex=extras.getString("pet_sex");

        pet_nameTV = findViewById(R.id.pet_nameTV);
        pet_parentNameTV = findViewById(R.id.pet_parentNameTV);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        test_xray=findViewById(R.id.test_xray);
        manage_pet_lab_work=findViewById(R.id.manage_pet_lab_work);
        hospitalization_surgeries=findViewById(R.id.hospitalization_surgeries);
        recent_visits=findViewById(R.id.recent_visits);
        print_id_card=findViewById(R.id.print_id_card);
        hospitalization_surgery=findViewById(R.id.hospitalization_surgery);
        view_history=findViewById(R.id.view_history);

        test_xray.setOnClickListener(this);
        manage_pet_lab_work.setOnClickListener(this);
        hospitalization_surgeries.setOnClickListener(this);
        recent_visits.setOnClickListener(this);
        print_id_card.setOnClickListener(this);
        hospitalization_surgery.setOnClickListener(this);
        view_history.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);

        pet_nameTV.setText(pet_name);
        pet_parentNameTV.setText(patent_name);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_xray:
                Intent petDetailsXray = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataXray = new Bundle();
                dataXray.putString("pet_id",pet_id);
                dataXray.putString("pet_name",pet_name);
                dataXray.putString("pet_parent",patent_name);
                dataXray.putString("pet_unique_id",pet_unique_id);
                dataXray.putString("reports_id","7.0");
                dataXray.putString("pet_sex",pet_sex);
                petDetailsXray.putExtras(dataXray);
                startActivity(petDetailsXray);
                break;
            case R.id.manage_pet_lab_work:
                Intent petDetailsLabWork = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataLabwork = new Bundle();
                dataLabwork.putString("pet_id",pet_id);
                dataLabwork.putString("pet_name",pet_name);
                dataLabwork.putString("pet_parent",patent_name);
                dataLabwork.putString("pet_unique_id",pet_unique_id);
                dataLabwork.putString("reports_id","8.0");
                dataLabwork.putString("pet_sex",pet_sex);
                petDetailsLabWork.putExtras(dataLabwork);
                startActivity(petDetailsLabWork);
                break;
            case R.id.hospitalization_surgeries:
                Intent petDetailsHospitalization = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataLabworkHospitalization = new Bundle();
                dataLabworkHospitalization.putString("pet_id",pet_id);
                dataLabworkHospitalization.putString("pet_name",pet_name);
                dataLabworkHospitalization.putString("pet_parent",patent_name);
                dataLabworkHospitalization.putString("pet_unique_id",pet_unique_id);
                dataLabworkHospitalization.putString("reports_id","9.0");
                dataLabworkHospitalization.putString("pet_sex",pet_sex);
                petDetailsHospitalization.putExtras(dataLabworkHospitalization);
                startActivity(petDetailsHospitalization);
                break;
            case R.id.view_history:

                break;
            case R.id.recent_visits:
                Intent petDetailsLabVisits = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataLabworkVisits = new Bundle();
                dataLabworkVisits.putString("pet_id",pet_id);
                dataLabworkVisits.putString("pet_name",pet_name);
                dataLabworkVisits.putString("pet_parent",patent_name);
                dataLabworkVisits.putString("pet_unique_id",pet_unique_id);
                dataLabworkVisits.putString("reports_id","10.0");
                dataLabworkVisits.putString("pet_sex",pet_sex);
                petDetailsLabVisits.putExtras(dataLabworkVisits);
                startActivity(petDetailsLabVisits);
                break;
            case R.id.print_id_card:
                break;
            case R.id.hospitalization_surgery:
                break;
            case R.id.back_arrow_IV:
                onBackPressed();
                break;
        }

    }

    @Override
    public void onResponse(Response arg0, String key) {

    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
