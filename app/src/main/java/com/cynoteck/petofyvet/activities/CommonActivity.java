package com.cynoteck.petofyvet.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyvet.R;

public class CommonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

/*        Bundle extras = getIntent().getExtras();
        String fragment = extras.getString("fragment");

        switch (fragment){

            case "selectPetReportsFragment":

                SelectPetReportsFragment selectPetReportsFragment = new SelectPetReportsFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.common_activity_frame, selectPetReportsFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
        }*/
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }
}
