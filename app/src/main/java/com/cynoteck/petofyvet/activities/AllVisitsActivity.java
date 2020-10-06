package com.cynoteck.petofyvet.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import com.cynoteck.petofyvet.params.immunizationRequest.ImmunizationParams;
import com.cynoteck.petofyvet.params.immunizationRequest.ImmunizationRequest;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVisitDetailsRequest;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVistsDetailsParams;
import com.cynoteck.petofyvet.response.getImmunizationReport.PetImmunizationRecordResponse;
import com.cynoteck.petofyvet.response.getMyVisitedPetRecordResponse.GetMyVisitPetRecordResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetReportsTypeResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getClinicVisitDetails.GetClinicVisitsDetailsResponse;
import com.cynoteck.petofyvet.utils.AllVisitsDateWieseOnClick;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Response;

public class AllVisitsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener, AllVisitsDateWieseOnClick {
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
    String petNameImmun="",petSeximmun="",petAgeImmun="",petParentImmun="";
    WebView webview;


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
        webview = findViewById(R.id.webview);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

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
                        allVisitsAdapter = new AllVisitsAdapter(this, getMyVisitPetRecordResponse.getData().getPetClinicVisitList(),this);
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

            case "GetImmunization":
                try {
                    methods.customProgressDismiss();
                    Log.d("GetImmunization",arg0.body().toString());
                    PetImmunizationRecordResponse immunizationRecordResponse = (PetImmunizationRecordResponse) arg0.body();
                    int responseCode = Integer.parseInt(immunizationRecordResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if (immunizationRecordResponse.getData().getPetImmunizationDetailModels().isEmpty()){
                            Toast.makeText(this, "No Record Found !", Toast.LENGTH_SHORT).show();
                        }else {
                            ArrayList<String> immunizationDate = new ArrayList<>();
                            ArrayList<String> vaccineClass = new ArrayList<>();
                            ArrayList<String> nextDueDate = new ArrayList<>();

                            for (int i = 0; i < immunizationRecordResponse.getData().getPetImmunizationDetailModels().size(); i++) {
                                immunizationDate.add(immunizationRecordResponse.getData().getPetImmunizationDetailModels().get(i).getImmunizationDate().substring(0, immunizationRecordResponse.getData().getPetImmunizationDetailModels().get(i).getImmunizationDate().length() - 11));
                                vaccineClass.add(immunizationRecordResponse.getData().getPetImmunizationDetailModels().get(i).getVaccine());
                                nextDueDate.add(immunizationRecordResponse.getData().getPetImmunizationDetailModels().get(i).getNextDueDate());
                            }
                            JSONArray date = new JSONArray(immunizationDate);
                            JSONArray vaccine = new JSONArray(vaccineClass);
                            JSONArray nextDate = new JSONArray(nextDueDate);
                            Log.e("aaaaaa", vaccineClass.toString());
                            Log.e("aaaaaa", vaccine.toString());
                            String immunizationSet = methods.immunizationPdfGenarator(petNameImmun, petAgeImmun, petSeximmun, petParentImmun, "4564564644465", date, vaccine, nextDate);

                            webview.loadDataWithBaseURL(null, immunizationSet, "text/html", "utf-8", null);
                            new Handler().postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void run() {
                                    Context context = AllVisitsActivity.this;
                                    PrintManager printManager = (PrintManager) getSystemService(context.PRINT_SERVICE);
                                    PrintDocumentAdapter adapter = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        adapter = webview.createPrintDocumentAdapter();
                                    }
                                    String JobName = getString(R.string.app_name) + "Document";
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        PrintJob printJob = printManager.print(JobName, adapter, new PrintAttributes.Builder().build());
                                    }
                                }
                            }, 3000);

                        }

                    }else if (responseCode==614){
                        Toast.makeText(this, immunizationRecordResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetClinicVisitDetails":
                try {
                    Log.d("ResponseClinicVisit", arg0.body().toString());
                    GetClinicVisitsDetailsResponse getClinicVisitsDetailsResponse = (GetClinicVisitsDetailsResponse) arg0.body();
                    int responseCode = Integer.parseInt(getClinicVisitsDetailsResponse.getResponse().getResponseCode());
                    Log.d("ajajjaja",""+responseCode);
                    if (responseCode == 109) {
                        lastPrescriptionPdf(getClinicVisitsDetailsResponse.getData().getVeterinarianDetails().getName(),
                                getClinicVisitsDetailsResponse.getData().getVeterinarianDetails().getEmail(),
                                getClinicVisitsDetailsResponse.getData().getVeterinarianDetails().getVetQualifications(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getPetName(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getPetAge(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getPetSex(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getVisitDate(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getPetParentName(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getTemperature(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getDiagnosisProcedure(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getTreatmentRemarks(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getFollowUpDate(),
                                getClinicVisitsDetailsResponse.getData().getVeterinarianDetails().getVetRegistrationNumber(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getDescription(),
                                getClinicVisitsDetailsResponse.getData().getVeterinarianDetails().getAddress()
                        );
                    }
                    else
                    {
                        methods.customProgressDismiss();
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

    @Override
    public void onPrecriptionButton(int position) {
        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(getMyVisitPetRecordResponse.getData().getPetClinicVisitList().get(position).getPetId().substring(0,getMyVisitPetRecordResponse.getData().getPetClinicVisitList().get(position).getPetId().length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("petClinicVisitDetail",petClinicVisitDetailsRequest.toString());
        ApiService<GetClinicVisitsDetailsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getLastPrescription(Config.token,petClinicVisitDetailsRequest), "GetPetClinicVisitDetails");
        methods.showCustomProgressBarDialog(this);
    }

    @Override
    public void onImmunizationButton(int position) {
        methods.showCustomProgressBarDialog(this);
//        Toast.makeText(this, "Immization", Toast.LENGTH_SHORT).show();
        petNameImmun = getMyVisitPetRecordResponse.getData().getPetClinicVisitList().get(position).getPetName();
        petSeximmun = getMyVisitPetRecordResponse.getData().getPetClinicVisitList().get(position).getPetSex();
        petAgeImmun = getMyVisitPetRecordResponse.getData().getPetClinicVisitList().get(position).getPetAge();
        petParentImmun = getMyVisitPetRecordResponse.getData().getPetClinicVisitList().get(position).getPetParentName();

        ImmunizationParams immunizationParams = new ImmunizationParams();
        immunizationParams.setEncryptedId(getMyVisitPetRecordResponse.getData().getPetClinicVisitList().get(position).getEncryptedId());
//        immunizationParams.setEncryptedId(getPetListResponse.getData().getPetClinicVisitList().get(position).getEncryptedId());
        ImmunizationRequest immunizationRequest = new ImmunizationRequest();
        immunizationRequest.setData(immunizationParams);

        ApiService<PetImmunizationRecordResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().viewPetVaccination(Config.token,immunizationRequest), "GetImmunization");
        Log.d("GetImmunization",immunizationRequest.toString());

    }
    public void lastPrescriptionPdf(String veterian, String strEmail,String qualification,String strForA, String strAge,String strSex, String strDate,String pet_parent, String strTemparature, String strDiagnosis,String strRemark, String strNxtVisit,String registration_number, String Symptons,String address)
    {
        String str=methods.pdfGenarator(strForA,strAge,strSex,pet_parent,strTemparature,Symptons,strDiagnosis,strRemark,strNxtVisit,registration_number);
        webview.loadDataWithBaseURL(null,str,"text/html","utf-8",null);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                methods.customProgressDismiss();
                Context context=AllVisitsActivity.this;
                PrintManager printManager=(PrintManager)getSystemService(context.PRINT_SERVICE);
                PrintDocumentAdapter adapter=null;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                    adapter=webview.createPrintDocumentAdapter();
                }
                String JobName=getString(R.string.app_name) +"Document";
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                    PrintJob printJob=printManager.print(JobName,adapter,new PrintAttributes.Builder().build());
                }
            }
        }, 3000);
    }
}
