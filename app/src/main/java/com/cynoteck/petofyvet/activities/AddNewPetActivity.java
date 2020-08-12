package com.cynoteck.petofyvet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Response;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.AllStaffAdapter;
import com.cynoteck.petofyvet.adapters.NewEntrysAdapter;
import com.cynoteck.petofyvet.adapters.ReportsAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetPetListResponse;
import com.cynoteck.petofyvet.response.getStaffResponse.GetAllStaffData;
import com.cynoteck.petofyvet.response.recentEntrys.PetClinicVisitList;
import com.cynoteck.petofyvet.response.recentEntrys.RecentEntrysResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.cynoteck.petofyvet.utils.NewEntryListClickListener;
import com.cynoteck.petofyvet.utils.StaffListClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class AddNewPetActivity extends AppCompatActivity implements ApiResponse,View.OnClickListener,StaffListClickListener, NewEntryListClickListener, TextWatcher {

    Methods methods;
    RecyclerView all_new_entry_list;
    ShimmerFrameLayout shimmer_view_new_entry;
    NewEntrysAdapter newEntrysAdapter;
    ImageView new_pet_search;
    List<PetClinicVisitList> petClinicVisitLists;
    RelativeLayout search_boxRL;
    EditText search_box_add_new;
    TextView staff_headline_TV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pet);
        init();
    }

    private void init() {
        methods = new Methods(this);
        all_new_entry_list = findViewById(R.id.all_new_entry_list);
        shimmer_view_new_entry = findViewById(R.id.shimmer_view_new_entry);
        new_pet_search = findViewById(R.id.new_pet_search);
        search_boxRL = findViewById(R.id.search_boxRL);
        search_box_add_new = findViewById(R.id.search_box_add_new);
        staff_headline_TV = findViewById(R.id.staff_headline_TV);
        new_pet_search.setOnClickListener(this);
        if (methods.isInternetOn()){
            getPetNewList();

        }else {

            methods.DialogInternet();
        }
    }

    private void getPetNewList() {
        shimmer_view_new_entry.startShimmerAnimation();
        String input=Config.token.trim();
        Log.d("lalalall",""+input);
        ApiService<RecentEntrysResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getRecentClientcVisits(Config.token), "GetRecentClinicVisits");
    }

    @Override
    public void onResponse(Response arg0, String key) {
        Log.d("kkakakak",""+key+" response: "+arg0);
        switch (key){
            case "GetRecentClinicVisits":
                try {
                    RecentEntrysResponse getPetListResponse = (RecentEntrysResponse) arg0.body();
                    Log.d("GetRecentClinicVisits", getPetListResponse.toString());
                    int responseCode =getPetListResponse.getResponse().getResponseCode();

                    if (responseCode== 109){
                        Log.e("lallalal",""+getPetListResponse.getData().getPetClinicVisitList().size());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        all_new_entry_list.setLayoutManager(linearLayoutManager);
                        newEntrysAdapter  = new NewEntrysAdapter(this,getPetListResponse.getData().getPetClinicVisitList(),this);
                        all_new_entry_list.setAdapter(newEntrysAdapter);
                        petClinicVisitLists = getPetListResponse.getData().getPetClinicVisitList();
                        newEntrysAdapter.notifyDataSetChanged();
                        shimmer_view_new_entry.setVisibility(View.GONE);
                        shimmer_view_new_entry.stopShimmerAnimation();
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

    @Override
    public void onViewDetailsClick(int position) {

    }

    @Override
    public void onStausClick(int position, Button button) {

    }

    @Override
    public void onProductClick(int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_pet_search:
                search_boxRL.setVisibility(View.VISIBLE);
                staff_headline_TV.setVisibility(View.GONE);
                InputMethodManager imm1 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(search_box_add_new.getWindowToken(), 0);
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        newEntrysAdapter.getFilter().filter(s.toString());

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}