package com.cynoteck.petofyvet.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.AllVisitsAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.getMyVisitPetRecordRequest.GetMyVisistPetRecordParams;
import com.cynoteck.petofyvet.params.getMyVisitPetRecordRequest.GetMyVisistPetRecordRequest;
import com.cynoteck.petofyvet.response.getMyVisitedPetRecordResponse.GetMyVisitPetRecordResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetReportsTypeResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Response;

public class AllVisitsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {
    AllVisitsAdapter allVisitsAdapter;
    LinearLayout search_visits;
    TextView lastVisitDt, nextVisitDt;
    AppCompatSpinner nature_of_visit_spinner;
    ImageView back_arrow_IV;
    Methods methods;
    ArrayList<String> natureOfVisitList;
    HashMap<String,String> natureOfVisitHashMap=new HashMap<>();
    String strNatureOfVist="",natureOfVisit="";
    DatePickerDialog picker;
    RecyclerView all_visits_RV;
    ProgressBar progressBar;
    String lastDate, nextDate;
    GetMyVisitPetRecordResponse getMyVisitPetRecordResponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_visits);
        methods = new Methods(this);

        initization();
        if (methods.isInternetOn()){
            getVisitTypes();
        }else {
            methods.DialogInternet();
        }
    }

    private void initization() {
        nature_of_visit_spinner = findViewById(R.id.nature_of_visit_spinner);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        all_visits_RV = findViewById(R.id.all_visits_RV);
        search_visits = findViewById(R.id.search_visits);
        lastVisitDt = findViewById(R.id.lastVisitDt);
        nextVisitDt = findViewById(R.id.nextVisitDt);
        progressBar = findViewById(R.id.progressBar);


        back_arrow_IV.setOnClickListener(this);
        search_visits.setOnClickListener(this);
        lastVisitDt.setOnClickListener(this);
        nextVisitDt.setOnClickListener(this);


    }

    private void getVisitTypes() {
        ApiService<GetReportsTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getReportsType(Config.token), "GetReportsType");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.lastVisitDt:
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


            case R.id.search_visits:
                lastDate = lastVisitDt.getText().toString().trim();
                nextDate = nextVisitDt.getText().toString().trim();
                if (natureOfVisit.equals("Select Visit")){
                    Toast.makeText(this, "Select Visit Type", Toast.LENGTH_SHORT).show();
                }else if (lastDate.isEmpty()){
                    Toast.makeText(this, "Select From Date", Toast.LENGTH_SHORT).show();
                }else if (nextDate.isEmpty()){
                    Toast.makeText(this, "Select To Date", Toast.LENGTH_SHORT).show();
                }else {

                    GetMyVisistPetRecordParams getMyVisistPetRecordParams = new GetMyVisistPetRecordParams();
                    getMyVisistPetRecordParams.setFromDate(lastDate);
                    getMyVisistPetRecordParams.setTodate(nextDate);
                    getMyVisistPetRecordParams.setPageNumber("1");
                    getMyVisistPetRecordParams.setPageSize("10");
                    getMyVisistPetRecordParams.setNatureOfVisiteId(strNatureOfVist);
                    GetMyVisistPetRecordRequest getMyVisistPetRecordRequest = new GetMyVisistPetRecordRequest();
                    getMyVisistPetRecordRequest.setData(getMyVisistPetRecordParams);

                    if (methods.isInternetOn()){
                        progressBar.setVisibility(View.VISIBLE);
                        all_visits_RV.setVisibility(View.GONE);
                        ApiService<GetMyVisitPetRecordResponse> service = new ApiService<>();
                        service.get(this, ApiClient.getApiInterface().getMyVisitedPetRecord(Config.token, getMyVisistPetRecordRequest), "MyVisits");
                        Log.d("MyVisits==>", "" + getMyVisistPetRecordRequest);
                    }else {
                        methods.DialogInternet();
                    }

                }

                break;


            case R.id.back_arrow_IV:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onResponse(Response arg0, String key) {

        switch (key){
            case "GetReportsType":
                try {
                    Log.d("GetPetServiceTypes",arg0.body().toString());
                    GetReportsTypeResponse petServiceResponse = (GetReportsTypeResponse) arg0.body();
                    int responseCode = Integer.parseInt(petServiceResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        natureOfVisitList=new ArrayList<>();
                        natureOfVisitList.add("Select Visit");
                        for(int i=0;i<petServiceResponse.getData().size();i++)
                        {
                            natureOfVisitList.add(petServiceResponse.getData().get(i).getNature());
                            natureOfVisitHashMap.put(petServiceResponse.getData().get(i).getNature(),petServiceResponse.getData().get(i).getId());
                        }
                        setSpinnerNatureofVisit();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "MyVisits":
                try {
                    getMyVisitPetRecordResponse = (GetMyVisitPetRecordResponse) arg0.body();
                    Log.d("MyVisits", getMyVisitPetRecordResponse.toString());
                    int responseCode = Integer.parseInt(getMyVisitPetRecordResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        progressBar.setVisibility(View.GONE);
                        all_visits_RV.setVisibility(View.VISIBLE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                        all_visits_RV.setLayoutManager(linearLayoutManager);
                        allVisitsAdapter = new AllVisitsAdapter(this, getMyVisitPetRecordResponse.getData().getPetClinicVisitList());
                        all_visits_RV.setAdapter(allVisitsAdapter);
                    } else if (responseCode == 614) {
                        Toast.makeText(this, getMyVisitPetRecordResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    private void setSpinnerNatureofVisit() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,natureOfVisitList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        nature_of_visit_spinner.setAdapter(aa);
        if (!strNatureOfVist.equals("")) {
            int spinnerPosition = aa.getPosition(strNatureOfVist);
            nature_of_visit_spinner.setSelection(spinnerPosition);
        }
        nature_of_visit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                natureOfVisit=item;
                strNatureOfVist=natureOfVisitHashMap.get(natureOfVisit);
                Log.d("Spinner",""+item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
