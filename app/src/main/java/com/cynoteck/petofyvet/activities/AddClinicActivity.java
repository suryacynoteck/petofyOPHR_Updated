package com.cynoteck.petofyvet.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicParam;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicRequest;
import com.cynoteck.petofyvet.response.addPetClinicresponse.AddpetClinicResponse;
import com.cynoteck.petofyvet.response.clinicVisist.ClinicVisitResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetReportsTypeResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Response;

public class AddClinicActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    ImageView back_arrow_IV;
    String pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id ,report_type_id,button_text,
            visitId="",natureOfVisit="";
    Bundle data = new Bundle();
    TextView Dewormer_name_ET,Dewormer_name_TV,Dewormer_ET,Dewormer_TV,vaccine_TV,clinicFolow_up_dt_view,
             clinicCalenderTextViewVisitDt,clinicIlness_onset,date_of_illness_TV;
    LinearLayout addPrescriptionButton;
    EditText clinicVeterian_name_ET,clinicCescription_ET,clinicTreatment_remarks_ET,
            weight_ET,clinicTemparature_ET,clinicDiagnosis_ET,vacine_ET;
    AppCompatSpinner clinicNature_of_visit_spinner,clinicNext_visit_spinner;
    LinearLayout clinicDocument_layout;
    Button clinicCancel_clinic_add_dialog,clinicSave_clinic_data;
    Methods methods;

    ArrayList<String> nextVisitList;
    ArrayList<String> natureOfVisitList;

    HashMap<String,String> nextVisitHas=new HashMap<>();
    HashMap<String,String> natureOfVisitHashMap=new HashMap<>();

    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);

        init();
    }

    private void init() {
        methods = new Methods(this);
        Bundle extras = getIntent().getExtras();
        clinicVeterian_name_ET = findViewById(R.id.veterian_name_ET);
        clinicNature_of_visit_spinner = findViewById(R.id.nature_of_visit_spinner);
        clinicCalenderTextViewVisitDt = findViewById(R.id.calenderTextViewVisitDt);
        clinicCescription_ET = findViewById(R.id.description_ET);
        date_of_illness_TV = findViewById(R.id.date_of_illness_TV);
        weight_ET = findViewById(R.id.weight_ET);
        vaccine_TV = findViewById(R.id.vaccine_TV);
        vacine_ET = findViewById(R.id.vacine_ET);
        Dewormer_TV = findViewById(R.id.Dewormer_TV);
        Dewormer_ET = findViewById(R.id.Dewormer_ET);
        Dewormer_name_TV = findViewById(R.id.Dewormer_name_TV);
        Dewormer_name_ET = findViewById(R.id.Dewormer_name_ET);
        clinicTemparature_ET = findViewById(R.id.temparature_ET);
        clinicIlness_onset = findViewById(R.id.ilness_onset);
        clinicDiagnosis_ET = findViewById(R.id.diagnosis_ET);
        clinicTreatment_remarks_ET = findViewById(R.id.treatment_remarks_ET);
        clinicNext_visit_spinner = findViewById(R.id.next_visit_spinner);
        clinicFolow_up_dt_view = findViewById(R.id.folow_up_dt_view);
        clinicDocument_layout = findViewById(R.id.document_layout);
        clinicSave_clinic_data = findViewById(R.id.save_clinic_data);

        clinicCalenderTextViewVisitDt.setOnClickListener(this);
        clinicIlness_onset.setOnClickListener(this);
        clinicFolow_up_dt_view.setOnClickListener(this);
        clinicSave_clinic_data.setOnClickListener(this);

        if (methods.isInternetOn()){
            getClientVisit();
            getVisitTypes();
        }else {

            methods.DialogInternet();
        }

      }

    private void getClientVisit() {
        ApiService<ClinicVisitResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getClinicVisit(Config.token), "GetClinicVisitRoutineFollowupTypes");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calenderTextViewVisitDt:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                clinicCalenderTextViewVisitDt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;
            case R.id.ilness_onset:
                final Calendar cldrIlness = Calendar.getInstance();
                int dayIlnes = cldrIlness.get(Calendar.DAY_OF_MONTH);
                int monthIlnes = cldrIlness.get(Calendar.MONTH);
                int yearIlnes = cldrIlness.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                clinicIlness_onset.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayIlnes, monthIlnes, yearIlnes);
                picker.show();
                break;
            case R.id.folow_up_dt_view:
                final Calendar cldrFolwUpDt = Calendar.getInstance();
                int dayFolwUpDt = cldrFolwUpDt.get(Calendar.DAY_OF_MONTH);
                int monthFolwUpDt = cldrFolwUpDt.get(Calendar.MONTH);
                int yearFolwUpDt = cldrFolwUpDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                clinicFolow_up_dt_view.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayFolwUpDt, monthFolwUpDt, yearFolwUpDt);
                picker.show();
                break;
            case R.id.save_clinic_data:
                String veterian_name=clinicVeterian_name_ET.getText().toString();
                String descrisption=clinicCescription_ET.getText().toString();
                String Remarks=clinicTreatment_remarks_ET.getText().toString();
                String weight=weight_ET.getText().toString();
                String temparature=clinicTemparature_ET.getText().toString();
                String diagnosis=clinicDiagnosis_ET.getText().toString();
                String strVacine=vacine_ET.getText().toString();
                String strDewormerName=Dewormer_ET.getText().toString();
                String strDewormerDose=Dewormer_name_ET.getText().toString();
                if(veterian_name.isEmpty()){
                    clinicVeterian_name_ET.setError("Enter Veterinarian Name");
                    clinicCescription_ET.setError(null);
                    clinicTreatment_remarks_ET.setError(null);
                    clinicDiagnosis_ET.setError(null);
                    vacine_ET.setError(null);
                    Dewormer_name_ET.setError(null);
                    Dewormer_ET.setError(null);
                }
                else if(descrisption.isEmpty()){
                    clinicVeterian_name_ET.setError(null);
                    clinicCescription_ET.setError("Enter Description");
                    clinicTreatment_remarks_ET.setError(null);
                    clinicDiagnosis_ET.setError(null);
                    vacine_ET.setError(null);
                    Dewormer_name_ET.setError(null);
                    Dewormer_ET.setError(null);
                }
                else if(Remarks.isEmpty()){
                    clinicVeterian_name_ET.setError(null);
                    clinicCescription_ET.setError(null);
                    clinicTreatment_remarks_ET.setError("Enter Remarks");
                    clinicDiagnosis_ET.setError(null);
                    vacine_ET.setError(null);
                    Dewormer_name_ET.setError(null);
                    Dewormer_ET.setError(null);
                }
                else if(diagnosis.isEmpty()){
                    clinicVeterian_name_ET.setError(null);
                    clinicCescription_ET.setError(null);
                    clinicTreatment_remarks_ET.setError(null);
                    clinicDiagnosis_ET.setError("Enter Diagnosis");
                    vacine_ET.setError(null);
                    Dewormer_name_ET.setError(null);
                    Dewormer_ET.setError(null);
                }
                else if(natureOfVisit.isEmpty()||(natureOfVisit.equals("Select Visit"))){
                    clinicVeterian_name_ET.setError(null);
                    clinicCescription_ET.setError(null);
                    clinicTreatment_remarks_ET.setError(null);
                    clinicDiagnosis_ET.setError(null);
                    vacine_ET.setError(null);
                    Dewormer_name_ET.setError(null);
                    Dewormer_ET.setError(null);
                    Toast.makeText(this, "Select Nature of Visit", Toast.LENGTH_SHORT).show();
                }
                else if((natureOfVisit.equals("Immunization"))&&(strVacine.isEmpty()))
                    {
                        clinicVeterian_name_ET.setError(null);
                        clinicCescription_ET.setError(null);
                        clinicTreatment_remarks_ET.setError(null);
                        clinicDiagnosis_ET.setError(null);
                        vacine_ET.setError("Enter Vaccine name");
                        Dewormer_name_ET.setError(null);
                        Dewormer_ET.setError(null);
                    }
                    else if((natureOfVisit.equals("Deworming"))&&(strDewormerName.isEmpty()))
                    {
                        clinicVeterian_name_ET.setError(null);
                        clinicCescription_ET.setError(null);
                        clinicTreatment_remarks_ET.setError(null);
                        clinicDiagnosis_ET.setError(null);
                        Dewormer_name_ET.setError(null);
                        vacine_ET.setError(null);
                        Dewormer_ET.setError("Enter Dewormer Name");
                    }
                    else if((natureOfVisit.equals("Deworming"))&&(strDewormerDose.isEmpty()))
                    {
                        clinicVeterian_name_ET.setError(null);
                        clinicCescription_ET.setError(null);
                        clinicTreatment_remarks_ET.setError(null);
                        clinicDiagnosis_ET.setError(null);
                        vacine_ET.setError(null);
                        Dewormer_name_ET.setError(null);
                        Dewormer_ET.setError("Enter Dose");
                    }
                    else
                    {
                        clinicVeterian_name_ET.setError(null);
                        clinicCescription_ET.setError(null);
                        clinicTreatment_remarks_ET.setError(null);
                        clinicDiagnosis_ET.setError(null);
                        vacine_ET.setError(null);
                        Dewormer_name_ET.setError(null);
                        Dewormer_ET.setError(null);
                        AddPetClinicParam addPetClinicParam=new AddPetClinicParam();
                        addPetClinicParam.setPetId("");
                        addPetClinicParam.setVeterinarian("");
                        addPetClinicParam.setVisitDate("");
                        addPetClinicParam.setNatureOfVisitId("");
                        addPetClinicParam.setVaccine("");
                        addPetClinicParam.setDescription("");
                        addPetClinicParam.setWeightLbs("");
                        addPetClinicParam.setWeightOz("");
                        addPetClinicParam.setTemperature("");
                        addPetClinicParam.setDateOfOnset("");
                        addPetClinicParam.setDewormerName("");
                        addPetClinicParam.setTreatmentRemarks("");
                        addPetClinicParam.setDiagnosisProcedure("");
                        addPetClinicParam.setFollowUpId("");
                        addPetClinicParam.setFollowUpDate("");
                        addPetClinicParam.setDocuments("");
                        AddPetClinicRequest addPetClinicRequest=new AddPetClinicRequest();
                        addPetClinicRequest.setAddPetParams(addPetClinicParam);
                        if (methods.isInternetOn()){
                            addPetClinicData(addPetClinicRequest);
                        }else {

                            methods.DialogInternet();
                        }
                    }
                break;

        }
    }

    private void getVisitTypes() {
        ApiService<GetReportsTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getReportsType(Config.token), "GetReportsType");
    }

    private void addPetClinicData(AddPetClinicRequest addPetClinicRequest) {
        ApiService<AddpetClinicResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addClinicVisit(Config.token,addPetClinicRequest), "AddClinicVisit");
        Log.d("DIALOG==>",""+addPetClinicRequest);
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
                        nextVisitList=new ArrayList<>();
                        nextVisitList.add("Select Visit");
                        for(int i=0;i<clinicVisitResponse.getData().size();i++)
                        {
                            nextVisitList.add(clinicVisitResponse.getData().get(i).getFollowUpTitle());
                            nextVisitHas.put(clinicVisitResponse.getData().get(i).getFollowUpTitle(),clinicVisitResponse.getData().get(i).getId());
                        }
                        setSpinnerNextClinicVisit();
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
            case "AddClinicVisit":
                try {
                    AddpetClinicResponse addpetClinicResponse = (AddpetClinicResponse) arg0.body();
                    Log.d("AddClinicVisit", addpetClinicResponse.toString());
                    int responseCode = Integer.parseInt(addpetClinicResponse.getResponse().getResponseCode());

                    if (responseCode== 109){

                    }
                    else if (responseCode==614){
                        Toast.makeText(this, addpetClinicResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
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

    private void setSpinnerNatureofVisit() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,natureOfVisitList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        clinicNature_of_visit_spinner.setAdapter(aa);
        clinicNature_of_visit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                natureOfVisit=item;
                if(natureOfVisit.equals("Immunization"))
                {
                    date_of_illness_TV.setVisibility(View.GONE);
                    clinicIlness_onset.setVisibility(View.GONE);
                    vaccine_TV.setVisibility(View.VISIBLE);
                    vacine_ET.setVisibility(View.VISIBLE);
                }
                else if(natureOfVisit.equals("Deworming"))
                {
                    date_of_illness_TV.setVisibility(View.GONE);
                    clinicIlness_onset.setVisibility(View.GONE);
                    vaccine_TV.setVisibility(View.GONE);
                    vacine_ET.setVisibility(View.GONE);
                    Dewormer_TV.setVisibility(View.VISIBLE);
                    Dewormer_ET.setVisibility(View.VISIBLE);
                    Dewormer_name_TV.setVisibility(View.VISIBLE);
                    Dewormer_name_ET.setVisibility(View.VISIBLE);

                }
                else{
                    date_of_illness_TV.setVisibility(View.VISIBLE);
                    clinicIlness_onset.setVisibility(View.VISIBLE);
                    vaccine_TV.setVisibility(View.GONE);
                    vacine_ET.setVisibility(View.GONE);
                    Dewormer_TV.setVisibility(View.GONE);
                    Dewormer_ET.setVisibility(View.GONE);
                    Dewormer_name_TV.setVisibility(View.GONE);
                    Dewormer_name_ET.setVisibility(View.GONE);

                }
                Log.d("spnerType",""+item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}