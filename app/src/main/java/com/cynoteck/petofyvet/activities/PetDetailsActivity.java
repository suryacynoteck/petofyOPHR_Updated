
package com.cynoteck.petofyvet.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiResponse;

import retrofit2.Response;

public class PetDetailsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    String pet_id,pet_name,patent_name,pet_bread;
    TextView pet_nameTV, pet_parentNameTV;
    ImageView back_arrow_IV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        Bundle extras = getIntent().getExtras();
        pet_id=extras.getString("pet_id");
        pet_name=extras.getString("pet_name");
        patent_name=extras.getString("pet_parent");

        pet_nameTV = findViewById(R.id.pet_nameTV);
        pet_parentNameTV = findViewById(R.id.pet_parentNameTV);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        back_arrow_IV.setOnClickListener(this);

        pet_nameTV.setText(pet_name);
        pet_parentNameTV.setText(patent_name);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
