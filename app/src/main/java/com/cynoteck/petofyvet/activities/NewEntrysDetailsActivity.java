package com.cynoteck.petofyvet.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.fragments.NewEntrysListFragment;
import com.cynoteck.petofyvet.fragments.ReportListFragment;
import com.cynoteck.petofyvet.params.addParamRequest.AddPetParams;
import com.cynoteck.petofyvet.params.addParamRequest.AddPetRequset;
import com.cynoteck.petofyvet.response.clinicVisist.ClinicVisitResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class NewEntrysDetailsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    ImageView back_arrow_IV;
    String pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id ,report_type_id,button_text,visitId="";
    Bundle data = new Bundle();
    TextView pet_name_TV,pet_sex_TV,pet_id_TV,pet_owner_name_TV,pet_owner_phone_no_TV,clinicFolow_up_dt_view,
            reports_headline_TV,add_text_button,clinicCalenderTextViewVisitDt,clinicIlness_onset;
    LinearLayout addPrescriptionButton;
    Dialog clinicDialog;
    EditText clinicVeterian_name_ET,clinicCescription_ET,clinicTreatment_remarks_ET,
            clinicAdd_edit_pet_age_dialog,clinicTemparature_ET,clinicDiagnosis_ET;
    AppCompatSpinner clinicNature_of_visit_spinner,clinicNext_visit_spinner;
    LinearLayout clinicDocument_layout;
    Button clinicCancel_clinic_add_dialog,clinicSave_clinic_data;
    Methods methods;

    ArrayList<String> nextVisitList=new ArrayList<>();

    HashMap<String,String> nextVisitHas=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entrys_details);
        methods = new Methods(this);
        Bundle extras = getIntent().getExtras();
        report_type_id = extras.getString("reports_id");
        pet_id = extras.getString("pet_id");
        pet_owner_contact = extras.getString("pet_owner_contact");
        pet_owner_name = extras.getString("pet_owner_name");
        pet_sex = extras.getString("pet_sex");
        pet_name = extras.getString("pet_name");
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


        addPrescriptionButton.setOnClickListener(this);

        add_text_button.setText(button_text);
        pet_sex_TV.setText("("+pet_sex+")");
        pet_owner_name_TV.setText(pet_owner_name);
        pet_id_TV.setText(pet_unique_id);

        data.putString("pet_id",pet_id);
        data.putString("pet_name",pet_name);
        data.putString("pet_unique_id",pet_unique_id);
        data.putString("pet_sex",pet_sex);
        data.putString("pet_owner_name",pet_owner_name);
        data.putString("pet_owner_contact",pet_owner_contact);

        if (methods.isInternetOn()){
            getClientVisit();
        }else {

            methods.DialogInternet();
        }

        switch (report_type_id){

            case "1.0":

                reports_headline_TV.setText("Routine Report");
                data.putString("reports_id","1");
                data.putString("type","list");

                NewEntrysListFragment fragment1 = new NewEntrysListFragment();
                fragment1.setArguments(data);
                FragmentTransaction selectPetReportsFragmentFT = getSupportFragmentManager().beginTransaction();
                selectPetReportsFragmentFT.replace(R.id.report_type_frame, fragment1);
                selectPetReportsFragmentFT.commit();

                break;

            case "2.0":

                reports_headline_TV.setText("Health problem");
                data.putString("reports_id","2");
                data.putString("type","list");

                NewEntrysListFragment fragment2 = new NewEntrysListFragment();
                fragment2.setArguments(data);
                FragmentTransaction fragment2FT = getSupportFragmentManager().beginTransaction();
                fragment2FT.replace(R.id.report_type_frame, fragment2);
                fragment2FT.commit();

                break;


            case "4.0":

                reports_headline_TV.setText("Immunization Report");
                data.putString("reports_id","4");
                data.putString("type","list");

                NewEntrysListFragment fragment3 = new NewEntrysListFragment();
                fragment3.setArguments(data);
                FragmentTransaction fragment3FT = getSupportFragmentManager().beginTransaction();
                fragment3FT.replace(R.id.report_type_frame, fragment3);
                fragment3FT.commit();

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

                reports_headline_TV.setText("Other Report");
                data.putString("reports_id","6");
                data.putString("type","list");

                NewEntrysListFragment fragment5 = new NewEntrysListFragment();
                fragment5.setArguments(data);
                FragmentTransaction fragment5FT = getSupportFragmentManager().beginTransaction();
                fragment5FT.replace(R.id.report_type_frame, fragment5);
                fragment5FT.commit();

                break;


            case "7.0":

                reports_headline_TV.setText("Test/X-Ray Report");
                data.putString("reports_id","7");
                data.putString("type","XRay");
                NewEntrysListFragment fragment6 = new NewEntrysListFragment();
                fragment6.setArguments(data);
                FragmentTransaction fragment6FT = getSupportFragmentManager().beginTransaction();
                fragment6FT.replace(R.id.report_type_frame, fragment6);
                fragment6FT.commit();

                break;

            case "8.0":

                reports_headline_TV.setText("Lab Tests");
                data.putString("reports_id","8");
                data.putString("type","LabTest");
                NewEntrysListFragment fragment7 = new NewEntrysListFragment();
                fragment7.setArguments(data);
                FragmentTransaction fragment7FT = getSupportFragmentManager().beginTransaction();
                fragment7FT.replace(R.id.report_type_frame, fragment7);
                fragment7FT.commit();

                break;

            case "9.0":

                reports_headline_TV.setText("Hospitalization & Surgeries");
                data.putString("reports_id","9");
                data.putString("type","Hospitalization");
                NewEntrysListFragment fragment8 = new NewEntrysListFragment();
                fragment8.setArguments(data);
                FragmentTransaction fragment8FT = getSupportFragmentManager().beginTransaction();
                fragment8FT.replace(R.id.report_type_frame, fragment8);
                fragment8FT.commit();

                break;

            case "10.0":

                reports_headline_TV.setText("Clinic Visit Report");
                data.putString("reports_id","10");
                data.putString("type","ClinicVisitReport");
                NewEntrysListFragment fragment9 = new NewEntrysListFragment();
                fragment9.setArguments(data);
                FragmentTransaction fragment9FT = getSupportFragmentManager().beginTransaction();
                fragment9FT.replace(R.id.report_type_frame, fragment9);
                fragment9FT.commit();

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
                    TestXrayDialog();
                else if(button_text.equals("Lab Work"))
                    LabWorkDialog();
                else if(button_text.equals("Hospitalization"))
                    HospitalizationDialog();
                else if(button_text.equals("Clinic_visits"))
                    clinicDialog();
                break;

        }
    }

    public void clinicDialog()
    {
        clinicDialog=new Dialog(this);
        clinicDialog.setContentView(R.layout.add_clinic_visits_dialog);

        clinicVeterian_name_ET = clinicDialog.findViewById(R.id.veterian_name_ET);
        clinicNature_of_visit_spinner = clinicDialog.findViewById(R.id.nature_of_visit_spinner);
        clinicCalenderTextViewVisitDt = clinicDialog.findViewById(R.id.calenderTextViewVisitDt);
        clinicCescription_ET = clinicDialog.findViewById(R.id.description_ET);
        clinicAdd_edit_pet_age_dialog = clinicDialog.findViewById(R.id.add_edit_pet_age_dialog);
        clinicTemparature_ET = clinicDialog.findViewById(R.id.temparature_ET);
        clinicIlness_onset = clinicDialog.findViewById(R.id.ilness_onset);
        clinicDiagnosis_ET = clinicDialog.findViewById(R.id.diagnosis_ET);
        clinicTreatment_remarks_ET = clinicDialog.findViewById(R.id.treatment_remarks_ET);
        clinicNext_visit_spinner = clinicDialog.findViewById(R.id.next_visit_spinner);
        clinicFolow_up_dt_view = clinicDialog.findViewById(R.id.folow_up_dt_view);
        clinicDocument_layout = clinicDialog.findViewById(R.id.document_layout);
        clinicCancel_clinic_add_dialog = clinicDialog.findViewById(R.id.cancel_clinic_add_dialog);
        clinicSave_clinic_data = clinicDialog.findViewById(R.id.save_clinic_data);

        clinicCalenderTextViewVisitDt.setOnClickListener(this);
        clinicIlness_onset.setOnClickListener(this);
        clinicFolow_up_dt_view.setOnClickListener(this);
        clinicSave_clinic_data.setOnClickListener(this);
        clinicCancel_clinic_add_dialog.setOnClickListener(this);

        setSpinnerNextClinicVisit();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = clinicDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        clinicDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        clinicDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        clinicDialog.show();

    }

    public void TestXrayDialog()
    {

    }

    public void LabWorkDialog()
    {

    }

    public void HospitalizationDialog()
    {

    }

    private void getClientVisit() {
        ApiService<ClinicVisitResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getClinicVisit(Config.token), "GetClinicVisitRoutineFollowupTypes");
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "GetClinicVisitRoutineFollowupTypes":
                try {
                    ClinicVisitResponse clinicVisitResponse = (ClinicVisitResponse) arg0.body();
                    Log.d("GetClinicVisit", clinicVisitResponse.toString());
                    int responseCode = Integer.parseInt(clinicVisitResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        nextVisitList.add("Select Visit");
                        for(int i=0;i<clinicVisitResponse.getData().size();i++)
                        {
                            nextVisitList.add(clinicVisitResponse.getData().get(i).getFollowUpTitle());
                            nextVisitHas.put(clinicVisitResponse.getData().get(i).getFollowUpTitle(),clinicVisitResponse.getData().get(i).getId());
                        }
                    }
                    else if (responseCode==614){
                        Toast.makeText(this, clinicVisitResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
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

    private void setSpinnerNextClinicVisit() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,nextVisitList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        clinicNext_visit_spinner.setAdapter(aa);
        clinicNext_visit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                visitId=nextVisitHas.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}