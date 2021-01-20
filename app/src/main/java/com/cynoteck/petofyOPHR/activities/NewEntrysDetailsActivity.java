package com.cynoteck.petofyOPHR.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.fragments.NewEntrysListFragment;
import com.cynoteck.petofyOPHR.fragments.ReportListFragment;
import com.cynoteck.petofyOPHR.params.immunizationRequest.ImmunizationParams;
import com.cynoteck.petofyOPHR.params.immunizationRequest.ImmunizationRequest;
import com.cynoteck.petofyOPHR.response.getImmunizationReport.PetImmunizationRecordResponse;
import com.cynoteck.petofyOPHR.response.loginRegisterResponse.UserPermissionMasterList;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.Methods;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;

import retrofit2.Response;

public class NewEntrysDetailsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    ImageView back_arrow_IV;
    String pet_unique_id, pet_name,pet_sex,pet_age,pet_DOB,pet_owner_name,pet_owner_contact,pet_id ,
            report_type_id,button_text,button_type,pet_encrypted_id;
    Bundle data = new Bundle();
    TextView pet_name_TV,pet_sex_TV,pet_id_TV,pet_owner_name_TV,pet_owner_phone_no_TV,
            reports_headline_TV,add_text_button;
    LinearLayout addPrescriptionButton;
    SharedPreferences sharedPreferences;
    Methods methods;
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entrys_details);
        sharedPreferences = getSharedPreferences("userdetails", 0);
        methods = new Methods(this);

        Bundle extras = getIntent().getExtras();
        report_type_id = extras.getString("reports_id");
        pet_id = extras.getString("pet_id");
        Log.d("akakkakakak",""+pet_id);
        pet_owner_contact = extras.getString("pet_owner_contact");
        pet_owner_name = extras.getString("pet_parent");
        pet_sex = extras.getString("pet_sex");
        pet_age = extras.getString(pet_age);
        pet_name = extras.getString("pet_name");
        button_type=extras.getString("button_type");
        pet_DOB=extras.getString("pet_DOB");
        pet_encrypted_id=extras.getString("pet_encrypted_id");

        Log.e("jajajjaj",""+pet_DOB+" "+pet_encrypted_id);
        pet_unique_id = extras.getString("pet_unique_id");
        button_text = extras.getString("add_button_text");


        reports_headline_TV = findViewById(R.id.reports_headline_TV);
        back_arrow_IV =findViewById(R.id.back_arrow_IV);
        pet_name_TV = findViewById(R.id.pet_name_TV);
        pet_sex_TV = findViewById(R.id.pet_sex_TV);
        pet_id_TV = findViewById(R.id.pet_id_TV);
        add_text_button = findViewById(R.id.add_text_button);
        pet_owner_name_TV = findViewById(R.id.pet_owner_name_TV);
        pet_owner_phone_no_TV = findViewById(R.id.pet_owner_phone_no_TV);
        addPrescriptionButton = findViewById(R.id.addPrescriptionButton);
        webview = findViewById(R.id.webview);


        addPrescriptionButton.setOnClickListener(this);

        if((button_text.equals("RecentVisit"))||(button_text.equals("petHistory")))
            addPrescriptionButton.setVisibility(View.GONE);

        add_text_button.setText(button_text);
        pet_name_TV.setText(pet_name+"("+pet_sex+")");
        pet_owner_name_TV.setText(pet_owner_name);
        pet_id_TV.setText(pet_unique_id);
        pet_owner_name_TV.setText(pet_owner_name);
        pet_name_TV.setText(pet_name);

        data.putString("pet_id",pet_id);
        data.putString("pet_name",pet_name);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_owner_name",pet_owner_name);
        data.putString("pet_owner_contact",pet_owner_contact);
        data.putString("pet_encrypted_id",pet_encrypted_id);
        data.putString("pet_DOB",pet_DOB);


        switch (report_type_id){

            case "1.0":

                reports_headline_TV.setText("ROUTINE REPORT");
                data.putString("reports_id","1");
                data.putString("type","list");

                NewEntrysListFragment fragment1 = new NewEntrysListFragment();
                fragment1.setArguments(data);
                FragmentTransaction selectPetReportsFragmentFT = getSupportFragmentManager().beginTransaction();
                selectPetReportsFragmentFT.replace(R.id.report_type_frame, fragment1);
                selectPetReportsFragmentFT.commit();

                break;

            case "2.0":

                reports_headline_TV.setText("HEALTH PROBLEM");
                data.putString("reports_id","2");
                data.putString("type","list");

                NewEntrysListFragment fragment2 = new NewEntrysListFragment();
                fragment2.setArguments(data);
                FragmentTransaction fragment2FT = getSupportFragmentManager().beginTransaction();
                fragment2FT.replace(R.id.report_type_frame, fragment2);
                fragment2FT.commit();

                break;


            case "4.0":

                reports_headline_TV.setText("IMMUNIZATION");
                data.putString("reports_id","4");
                data.putString("type","list");

                NewEntrysListFragment fragment3 = new NewEntrysListFragment();
                fragment3.setArguments(data);
                FragmentTransaction fragment3FT = getSupportFragmentManager().beginTransaction();
                fragment3FT.replace(R.id.report_type_frame, fragment3);
                fragment3FT.commit();

                methods.showCustomProgressBarDialog(this);
                ImmunizationParams immunizationParams = new ImmunizationParams();
                immunizationParams.setEncryptedId(pet_encrypted_id);
                ImmunizationRequest immunizationRequest = new ImmunizationRequest();
                immunizationRequest.setData(immunizationParams);

                ApiService<PetImmunizationRecordResponse> service = new ApiService<>();
                service.get(this, ApiClient.getApiInterface().viewPetVaccination(Config.token,immunizationRequest), "GetImmunization");
                Log.d("GetImmunization",immunizationRequest.toString());

                break;


            case "5.0":

                reports_headline_TV.setText("Deworming");
                data.putString("reports_id","5");
                data.putString("type","list");

                NewEntrysListFragment fragment4 = new NewEntrysListFragment();
                fragment4.setArguments(data);
                FragmentTransaction fragment4FT = getSupportFragmentManager().beginTransaction();
                fragment4FT.replace(R.id.report_type_frame, fragment4);
                fragment4FT.commit();


                break;


            case "6.0":

                reports_headline_TV.setText("OTHER REPORT");
                data.putString("reports_id","6");
                data.putString("type","list");

                NewEntrysListFragment fragment5 = new NewEntrysListFragment();
                fragment5.setArguments(data);
                FragmentTransaction fragment5FT = getSupportFragmentManager().beginTransaction();
                fragment5FT.replace(R.id.report_type_frame, fragment5);
                fragment5FT.commit();

                break;


            case "7.0":

                if (button_type.equals("update")){
                    data.putString("button_type",button_type);
                }else if (button_type.equals("view")){
                    data.putString("button_type","view");
                }
                reports_headline_TV.setText("Test/X-Ray Report");
                data.putString("reports_id","7");
                data.putString("type","XRay");

                ReportListFragment fragment6 = new ReportListFragment();
                fragment6.setArguments(data);
                FragmentTransaction fragment6FT = getSupportFragmentManager().beginTransaction();
                fragment6FT.replace(R.id.report_type_frame, fragment6);
                fragment6FT.commit();
                break;

            case "8.0":
                if (button_type.equals("update")){
                    data.putString("button_type",button_type);
                }else if (button_type.equals("view")){
                    data.putString("button_type","view");
                }
                reports_headline_TV.setText("Lab Tests");
                data.putString("reports_id","8");
                data.putString("type","LabTest");

                ReportListFragment fragment7 = new ReportListFragment();
                fragment7.setArguments(data);
                FragmentTransaction fragment7FT = getSupportFragmentManager().beginTransaction();
                fragment7FT.replace(R.id.report_type_frame, fragment7);
                fragment7FT.commit();

                break;

            case "9.0":
                if (button_type.equals("update")){
                    data.putString("button_type",button_type);
                }else if (button_type.equals("view")){
                    data.putString("button_type","view");
                }
                reports_headline_TV.setText("Hospitalization & Surgeries");
                data.putString("reports_id","9");
                data.putString("type","Hospitalization");

                ReportListFragment fragment8 = new ReportListFragment();
                fragment8.setArguments(data);
                FragmentTransaction fragment8FT = getSupportFragmentManager().beginTransaction();
                fragment8FT.replace(R.id.report_type_frame, fragment8);
                fragment8FT.commit();

                break;

            case "10.0":
                if (button_type.equals("update")){
                    data.putString("button_type",button_type);
                }else if (button_type.equals("view")){
                    data.putString("button_type","view");
                }
                reports_headline_TV.setText("Clinic Visit Report");
                data.putString("reports_id","10");
                data.putString("type","ClinicVisitReport");

                ReportListFragment fragment9 = new ReportListFragment();
                fragment9.setArguments(data);
                FragmentTransaction fragment9FT = getSupportFragmentManager().beginTransaction();
                fragment9FT.replace(R.id.report_type_frame, fragment9);
                fragment9FT.commit();

                break;

            case "11.0":

                reports_headline_TV.setText("Recent Visit");
                data.putString("reports_id","11");
                data.putString("type","RecentVisit");
                NewEntrysListFragment fragment11 = new NewEntrysListFragment();
                fragment11.setArguments(data);
                FragmentTransaction fragment11FT = getSupportFragmentManager().beginTransaction();
                fragment11FT.replace(R.id.report_type_frame, fragment11);
                fragment11FT.commit();

                break;

            case "12.0":
                reports_headline_TV.setText("Recent Visit");
                data.putString("reports_id","12");
                data.putString("type","petHistory");
                NewEntrysListFragment fragment12 = new NewEntrysListFragment();
                fragment12.setArguments(data);
                FragmentTransaction fragment12FT = getSupportFragmentManager().beginTransaction();
                fragment12FT.replace(R.id.report_type_frame, fragment12);
                fragment12FT.commit();
                break;



        }

        back_arrow_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addPrescriptionButton:
                if(button_text.equals("Test/X-rays"))
                {
                    TestXrayDialog();
                }
                else if(button_text.equals("Lab Work"))
                {
                    LabWorkDialog();
                }
                else if(button_text.equals("Hospitalization"))
                {
                    HospitalizationDialog();
                }
                else if(button_text.equals("Clinic Visits"))
                    clinicDialog();
                break;

        }
    }

    public void clinicDialog()
    {

        String userTYpe = sharedPreferences.getString("user_type", "");
        if (userTYpe.equals("Vet Staff")){
            Gson gson = new Gson();
            String json = sharedPreferences.getString("userPermission", null);
            Type type = new TypeToken<ArrayList<UserPermissionMasterList>>() {}.getType();
            ArrayList<UserPermissionMasterList> arrayList = gson.fromJson(json, type);
            Log.e("ArrayList",arrayList.toString());
            Log.d("UserType",userTYpe);
            if (arrayList.get(5).getIsSelected().equals("true")) {
                Intent petDetailsIntent = new Intent(this.getApplication(), AddClinicActivity.class);
                Bundle data = new Bundle();
                data.putString("pet_id",pet_id);
                data.putString("pet_name",pet_name);
                data.putString("pet_parent",pet_owner_name);
                data.putString("pet_sex",pet_sex);
                data.putString("pet_age",pet_age);
                data.putString("pet_unique_id",pet_unique_id);
                data.putString("pet_DOB",pet_DOB);
                data.putString("pet_encrypted_id",pet_encrypted_id);
                data.putString("nature_of_visit","");
                data.putString("visit_dt","");
                data.putString("visit_description","");
                data.putString("remarks","");
                data.putString("visit_weight","");
                data.putString("visit_temparature","");
                data.putString("dt_of_illness","");
                data.putString("pet_diognosis","");
                data.putString("next_dt","");
                data.putString("appointment","");
                data.putString("appoint_link", "");
                data.putString("toolbar_name","ADD CLINIC VISITS");
                petDetailsIntent.putExtras(data);
                startActivity(petDetailsIntent);

            }else {
                Toast.makeText(this, "Permission not allowed!!", Toast.LENGTH_SHORT).show();
            }
        }else if (userTYpe.equals("Veterinarian")){
            Intent petDetailsIntent = new Intent(this.getApplication(), AddClinicActivity.class);
            Bundle data = new Bundle();
            data.putString("pet_id",pet_id);
            data.putString("pet_name",pet_name);
            data.putString("pet_parent",pet_owner_name);
            data.putString("pet_sex",pet_sex);
            data.putString("pet_age",pet_age);
            data.putString("pet_unique_id",pet_unique_id);
            data.putString("pet_DOB",pet_DOB);
            data.putString("pet_encrypted_id",pet_encrypted_id);
            data.putString("nature_of_visit","");
            data.putString("visit_dt","");
            data.putString("visit_description","");
            data.putString("remarks","");
            data.putString("visit_weight","");
            data.putString("visit_temparature","");
            data.putString("dt_of_illness","");
            data.putString("pet_diognosis","");
            data.putString("next_dt","");
            data.putString("appointment","");
            data.putString("appoint_link", "");
            data.putString("toolbar_name","ADD CLINIC VISITS");
            petDetailsIntent.putExtras(data);
            startActivityForResult(petDetailsIntent,1);

        }

    }

    public void TestXrayDialog()
    {
        Intent xRayIntent = new Intent(this,AddXRayDeatilsActivity.class);
        Bundle data = new Bundle();
        data.putString("type","add");
        data.putString("pet_id",pet_id);
        data.putString("pet_parent",pet_owner_name);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("type","Add Test/X-rays");
        xRayIntent.putExtras(data);
        startActivity(xRayIntent);
    }

    public void LabWorkDialog()
    {
        Intent labWorkIntent = new Intent(this,AddLabWorkDeatilsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id",pet_id);
        data.putString("pet_parent",pet_owner_name);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("lab_type","");
        data.putString("type","Add");

        labWorkIntent.putExtras(data);
        startActivity(labWorkIntent);
    }

    public void HospitalizationDialog()
    {
        Intent hospitalIntent = new Intent(this,AddHospitalizationDeatilsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id",pet_id);
        data.putString("pet_parent",pet_owner_name);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_age",pet_age);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("type","Add");
        hospitalIntent.putExtras(data);
        startActivity(hospitalIntent);
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "GetImmunization":
                try {
                    Log.d("GetImmunization",arg0.body().toString());
                    PetImmunizationRecordResponse immunizationRecordResponse = (PetImmunizationRecordResponse) arg0.body();
//                    methods.customProgressDismiss();
                    int responseCode = Integer.parseInt(immunizationRecordResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if (immunizationRecordResponse.getData().getPetImmunizationDetailModels().isEmpty()){
                            // methods.customProgressDismiss();
                            Toast.makeText(this, "No Record Found !", Toast.LENGTH_SHORT).show();
                        }else {
                            ArrayList<String> immunizationDate = new ArrayList<>();
                            ArrayList<String> vaccineClass = new ArrayList<>();
                            ArrayList<String> nextDueDate = new ArrayList<>();
                            ArrayList<String> vaccineType = new ArrayList<>();

                            ArrayList<String> immunizationDatePending = new ArrayList<>();
                            ArrayList<String> vaccineClassPending = new ArrayList<>();
                            ArrayList<String> nextDueDatePending = new ArrayList<>();
                            ArrayList<String> vaccineTypePending = new ArrayList<>();

                            for (int i = 0; i < immunizationRecordResponse.getData().getPetPendingVaccinations().size(); i++) {
                                if(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getIsVaccinated().equals("true"))
                                {
                                    immunizationDate.add(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getVaccinationDate());
                                    vaccineClass.add(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getVaccineName());
                                    nextDueDate.add(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getNextVaccinationDate().substring(0, immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getNextVaccinationDate().length() - 9));
                                    vaccineType.add(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getVaccineType());

                                }
                                else
                                {
                                    immunizationDatePending.add(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getVaccinationDate());
                                    vaccineClassPending.add(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getVaccineName());
                                    nextDueDatePending.add(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getNextVaccinationDate().substring(0, immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getNextVaccinationDate().length() - 9));
                                    vaccineTypePending.add(immunizationRecordResponse.getData().getPetPendingVaccinations().get(i).getVaccineType());

                                }
                            }
                            final JSONArray date = new JSONArray(immunizationDate);
                            final JSONArray vaccine = new JSONArray(vaccineClass);
                            final JSONArray nextDate = new JSONArray(nextDueDate);
                            final JSONArray vType = new JSONArray(vaccineType);

                            Log.d("jsjsjjsjs",""+date.length());

                            final JSONArray datePending = new JSONArray(immunizationDatePending);
                            final JSONArray vaccinePending = new JSONArray(vaccineClassPending);
                            final JSONArray nextDatePending = new JSONArray(nextDueDatePending);
                            final JSONArray vTypePending = new JSONArray(vaccineTypePending);

                            Log.d("jsjsjjsjs",""+datePending.length());

                            Log.e("aaaaaa", vaccineClass.toString());
                            Log.e("aaaaaa", vaccine.toString());
                            methods.customProgressDismiss();
                            String immunizationSet = methods.immunizationPdfGenarator(pet_name, pet_age, pet_sex, pet_owner_name, Config.user_verterian_reg_no, vType, vaccine, nextDate, vTypePending, vaccinePending, nextDatePending);
                            WebSettings webSettings = webview.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            webview.loadDataWithBaseURL(null, immunizationSet, "text/html", "utf-8", null);
                            new Handler().postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void run() {
                                    Context context = NewEntrysDetailsActivity.this;
                                    PrintManager printManager = (PrintManager)NewEntrysDetailsActivity.this.getSystemService(context.PRINT_SERVICE);
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
        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}