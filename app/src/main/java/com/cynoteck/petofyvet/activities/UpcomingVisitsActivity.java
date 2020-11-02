package com.cynoteck.petofyvet.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.MyAdapter;
import com.cynoteck.petofyvet.adapters.UpcomingVisitsAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.upcommingVisitsRequest.UpcommingVisitsParams;
import com.cynoteck.petofyvet.params.upcommingVisitsRequest.UpcommingVisitsRequest;
import com.cynoteck.petofyvet.response.upcommingVisitsResponse.UpcommingVisitsResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

import retrofit2.Response;

public class UpcomingVisitsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    /*RecyclerView upcomingVisits_RV;
    UpcomingVisitsAdapter upcomingVisitsAdapter;
    TextView lastVisitDt, nextVisitDt;
    ImageView back_arrow_IV, search_upcoming_IV;
    DatePickerDialog picker;
    String lastDate, nextDate;
    UpcommingVisitsResponse upcommingVisitsResponse;
    ProgressBar progressBar;
    */

    Methods methods;
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView back_arrow_IV;
    private int[] tabIcons = {
            R.drawable.update_prof_logo,
            R.drawable.calendar,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_visits);
        methods = new Methods(this);
        
        initization();

    }

    private void initization() {
       /* progressBar = findViewById(R.id.progressBar);
        upcomingVisits_RV = findViewById(R.id.upcomingVisits_RV);
        lastVisitDt = findViewById(R.id.lastVisitDt);
        nextVisitDt = findViewById(R.id.nextVisitDt);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        search_upcoming_IV = findViewById(R.id.search_upcoming_IV);
        
        back_arrow_IV.setOnClickListener(this);
        lastVisitDt.setOnClickListener(this);
        nextVisitDt.setOnClickListener(this);
        search_upcoming_IV.setOnClickListener(this);*/

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);

        back_arrow_IV.setOnClickListener(this);

        tabLayout.addTab(tabLayout.newTab().setText("Clinic Visit"));
        tabLayout.addTab(tabLayout.newTab().setText("Online Appointment"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FA43B3F4"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#FA43B3F4"));

        setupTabIcons();
        
        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
}

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_IV:
                onBackPressed();
                break;
            /*case R.id.lastVisitDt:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String displayValue = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                                lastVisitDt.setText(Config.changeDateFormat(displayValue));
                            }
                        }, year, month, day);
                picker.show();
                break;
            case R.id.nextVisitDt:
                final Calendar cldrForNext = Calendar.getInstance();
                int dayForNext = cldrForNext.get(Calendar.DAY_OF_MONTH);
                int monthForNext = cldrForNext.get(Calendar.MONTH);
                int yearForNext = cldrForNext.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                String displayValue = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                                nextVisitDt.setText(Config.changeDateFormat(displayValue));
                            }
                        }, yearForNext, monthForNext, dayForNext);
                picker.show();
                break;

            case R.id.search_upcoming_IV:
                lastDate = lastVisitDt.getText().toString().trim();
                nextDate = nextVisitDt.getText().toString().trim();

                if (lastDate.isEmpty()){
                    Toast.makeText(this, "Select From Date", Toast.LENGTH_SHORT).show();
                }else if (nextDate.isEmpty()){
                    Toast.makeText(this, "Select To Date", Toast.LENGTH_SHORT).show();
                }else {
                    upcomingVisits_RV.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    UpcommingVisitsParams upcommingVisitsParams = new UpcommingVisitsParams();
                    upcommingVisitsParams.setFromDate(lastDate);
                    upcommingVisitsParams.setTodate(nextDate);
                    UpcommingVisitsRequest upcommingVisitsRequest = new UpcommingVisitsRequest();
                    upcommingVisitsRequest.setData(upcommingVisitsParams);

                    ApiService<UpcommingVisitsResponse> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().getUpcomingVisits(Config.token, upcommingVisitsRequest), "UpcomingVisits");
                    Log.d("UpcomingVisits==>", "" + upcommingVisitsRequest);
                    
                }
                break;*/

        }
    }

    @Override
    public void onResponse(Response arg0, String key) {
       /* switch (key){
            case "UpcomingVisits":
                try {
                    upcommingVisitsResponse = (UpcommingVisitsResponse) arg0.body();
                    Log.d("UpcomingVisits", upcommingVisitsResponse.toString());
                    int responseCode = Integer.parseInt(upcommingVisitsResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        progressBar.setVisibility(View.GONE);
                        upcomingVisits_RV.setVisibility(View.VISIBLE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        upcomingVisits_RV.setLayoutManager(linearLayoutManager);
                        upcomingVisitsAdapter = new UpcomingVisitsAdapter(this, upcommingVisitsResponse.getData());
                        upcomingVisits_RV.setAdapter(upcomingVisitsAdapter);
                    } else if (responseCode == 614) {
                        Toast.makeText(this, upcommingVisitsResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


        }*/

    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
