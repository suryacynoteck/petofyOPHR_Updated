package com.cynoteck.petofyvet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.ReportsAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetPetListResponse;
import com.cynoteck.petofyvet.response.recentEntrys.RecentEntrysResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

public class AddNewPetActivity extends AppCompatActivity implements ApiResponse {

    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pet);
        methods = new Methods(this);

        if (methods.isInternetOn()){
            getPetNewList();

        }else {

            methods.DialogInternet();
        }
    }

    private void getPetNewList() {
        ApiService<RecentEntrysResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getRecentClientcVisits(Config.token), "GetRecentClinicVisits");
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "GetRecentClinicVisits":
                try {
                    RecentEntrysResponse getPetListResponse = (RecentEntrysResponse) arg0.body();
                    Log.d("GetRecentClinicVisits", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception e) {


                    e.printStackTrace();
                }

                break;

        }
        
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}