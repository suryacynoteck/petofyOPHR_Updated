package com.cynoteck.petofyvet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyvet.R;
import com.google.android.material.card.MaterialCardView;

public class AddNewPetActivity extends AppCompatActivity {

    MaterialCardView materialCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pet2);
        materialCardView = findViewById(R.id.toolbar);
    }
}
