package com.cynoteck.petofyvet.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.NewEntrysAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.response.recentEntrys.PetClinicVisitList;
import com.cynoteck.petofyvet.response.recentEntrys.RecentEntrysResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.cynoteck.petofyvet.utils.NewEntryListClickListener;
import com.cynoteck.petofyvet.utils.StaffListClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class AddNewPetFragment extends Fragment implements ApiResponse,View.OnClickListener, StaffListClickListener, NewEntryListClickListener, TextWatcher {
    Methods methods;
    RecyclerView all_new_entry_list;
    ShimmerFrameLayout shimmer_view_new_entry;
    NewEntrysAdapter newEntrysAdapter;
    ImageView new_pet_search;
    List<PetClinicVisitList> petClinicVisitLists;
    RelativeLayout search_boxRL;
    EditText search_box_add_new;
    TextView staff_headline_TV;
    View view;

    public AddNewPetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.activity_add_new_pet, container, false);
        init();
        return view;
    }

    private void init() {
        methods = new Methods(getActivity());
        all_new_entry_list =view.findViewById(R.id.all_new_entry_list);
        shimmer_view_new_entry = view.findViewById(R.id.shimmer_view_new_entry);
        new_pet_search = view.findViewById(R.id.new_pet_search);
        search_boxRL = view.findViewById(R.id.search_boxRL);
        search_box_add_new = view.findViewById(R.id.search_box_add_new);
        staff_headline_TV = view.findViewById(R.id.staff_headline_TV);
        new_pet_search.setOnClickListener(this);
        if (methods.isInternetOn()){
            getPetNewList();

        }else {

            methods.DialogInternet();
        }
    }

    private void getPetNewList() {
        shimmer_view_new_entry.startShimmerAnimation();
        String input= Config.token.trim();
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
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        all_new_entry_list.setLayoutManager(linearLayoutManager);
                        newEntrysAdapter  = new NewEntrysAdapter(getActivity(),getPetListResponse.getData().getPetClinicVisitList(),this);
                        all_new_entry_list.setAdapter(newEntrysAdapter);
                        petClinicVisitLists = getPetListResponse.getData().getPetClinicVisitList();
                        newEntrysAdapter.notifyDataSetChanged();
                        shimmer_view_new_entry.setVisibility(View.GONE);
                        shimmer_view_new_entry.stopShimmerAnimation();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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
                InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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