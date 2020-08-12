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

public class AddNewPetActivity extends AppCompatActivity {

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

    }






}