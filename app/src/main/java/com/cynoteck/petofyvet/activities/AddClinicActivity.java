package com.cynoteck.petofyvet.activities;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.ImmunazationVaccineAdopter;
import com.cynoteck.petofyvet.adapters.VaccineTypeAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addImmunizationClinic.ImmunizationAddClinicModel;
import com.cynoteck.petofyvet.params.addImmunizationClinic.ImmunizationClinicData;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicParam;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicRequest;
import com.cynoteck.petofyvet.params.getVaccinationDetails.GetVaccinationModelParameter;
import com.cynoteck.petofyvet.params.getVaccinationDetails.GetVaccinationRequest;
import com.cynoteck.petofyvet.params.immunizationRequest.ImmunizationParameter;
import com.cynoteck.petofyvet.params.immunizationRequest.ImmunizationRequestt;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVisitDetailsRequest;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVistsDetailsParams;
import com.cynoteck.petofyvet.params.searchRemarksParameter.SearchRemaksParametr;
import com.cynoteck.petofyvet.params.searchRemarksParameter.SearchRemaksRequest;
import com.cynoteck.petofyvet.params.updateClinicVisitsParams.UpdateClinicReportsParams;
import com.cynoteck.petofyvet.params.updateClinicVisitsParams.UpdateClinicReportsRequest;
import com.cynoteck.petofyvet.params.vaccinationSaveParams.VaccinationParameter;
import com.cynoteck.petofyvet.params.vaccinationSaveParams.VaccinationRequest;
import com.cynoteck.petofyvet.response.addImmunizationClinicResponse.ImmunizationAddResponse;
import com.cynoteck.petofyvet.response.addPet.imageUpload.ImageResponse;
import com.cynoteck.petofyvet.response.addPetClinicresponse.AddpetClinicResponse;
import com.cynoteck.petofyvet.response.clinicVisist.ClinicVisitResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.GetReportsTypeResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getClinicVisitDetails.GetClinicVisitsDetailsResponse;
import com.cynoteck.petofyvet.response.getVaccinationResponse.GetVaccineResponse;
import com.cynoteck.petofyvet.response.getVaccinationResponse.GetVaccineResponseModel;
import com.cynoteck.petofyvet.response.immunizationVaccineType.ImmunizationVaccineResponse;
import com.cynoteck.petofyvet.response.saveImmunizationData.SaveImmunizationResponse;
import com.cynoteck.petofyvet.response.searchRemaks.SearchRemaksResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.ImmunizationOnclickListener;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class AddClinicActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse,ImmunizationOnclickListener {

    ImageView back_arrow_IV;
    String report_id="",visitIdString="",pet_age="",strNatureOfVist="",pet_DOB="",pet_encrypted_id="",strDocumentUrl="",visitId="",natureOfVisit="",pet_id="",
            pet_name="",pet_owner_name="",pet_sex="",pet_unique_id="",veterian_name="",descrisption="",strPetAge="",
            Remarks="",visitDate="",dtOfOnset="",flowUpDt="",weight="",temparature="",diagnosis="",strNextVisitDate="",
            strVacine="",strDewormerName="",strDewormerDose="",strToolbarName="",cocatVal=null,strVaccineType="",strVaccineName="";
    Bundle data = new Bundle();
    TextView folow_up_dt_view,ilness_onset, Dewormer_name_ET,Dewormer_name_TV,Dewormer_ET,Dewormer_TV,clinicFolow_up_dt_view,clinic_head_line,
            clinicCalenderTextViewVisitDt,clinicIlness_onset,date_of_illness_TV,upload_documents,clinic_peto_edit_reg_number_dialog;
    ImageView document_name,clinic_back_arrow_IV;
    LinearLayout addPrescriptionButton,vaccine_layout;
    EditText clinicVeterian_name_ET,clinicCescription_ET,remaks_ET,
            weight_ET,clinicTemparature_ET,clinicDiagnosis_ET;
    AppCompatSpinner clinicNature_of_visit_spinner,clinicNext_visit_spinner,vaccine_type,vaccine_name;
    LinearLayout clinicDocument_layout,treatment_remarks_LL;
    MultiAutoCompleteTextView clinicTreatment_remarks_MT;
    Button add_immunization_data,clinicSave_clinic_data;
    WebView webview;

    Methods methods;
    RecyclerView immunization_data;
    ArrayList<String> nextVisitList;
    ArrayList<String> natureOfVisitList;
    ArrayList<String> remarksSearchList;
    ArrayList<String> vaccineTypeList;
    ArrayList<String> vaccineNameList;
    ArrayList<HashMap<String,String>> vaccinationModels;
    HashMap<String,String> vaccinationationModelHash;
    ImmunazationVaccineAdopter hospitalizationReportsAdapter;
    VaccineTypeAdapter vaccineTypeAdapter;
    HashMap<String,String> nextVisitHas=new HashMap<>();
    HashMap<String,String> natureOfVisitHashMap=new HashMap<>();
    ArrayList<String>VaccineList=new ArrayList<String>();
    DatePickerDialog picker;
    TextView description_TV,weight_TV,temparature_TV,diagnosis_TV,treatment_remaks_TV,pet_age_TV;

    private static final String IMAGE_DIRECTORY = "/Picture";
    private int GALLERY = 1, CAMERA = 2;
    File file = null;
    Dialog dialog,vaccineDialog,vaccineDetailsDialog;
    Bitmap bitmap, thumbnail;
    int backPressVal=0;
    private ArrayList<GetVaccineResponseModel> getVaccineResponseModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_clinic);
        //setContentView(R.layout.test_layout);

        init();
        requestMultiplePermissions();
        //vaccineDetailsDialog();
    }

    private void init() {
        methods = new Methods(this);
        Bundle extras = getIntent().getExtras();
        clinic_peto_edit_reg_number_dialog = findViewById(R.id.clinic_peto_edit_reg_number_dialog);
        clinic_head_line = findViewById(R.id.clinic_head_line);
        clinicVeterian_name_ET = findViewById(R.id.veterian_name_ET);
        clinicNature_of_visit_spinner = findViewById(R.id.nature_of_visit_spinner);
        clinicCalenderTextViewVisitDt = findViewById(R.id.calenderTextViewVisitDt);
        clinicCescription_ET = findViewById(R.id.description_ET);
        date_of_illness_TV = findViewById(R.id.date_of_illness_TV);
        document_name = findViewById(R.id.document_name);
        remaks_ET = findViewById(R.id.remaks_TV);
        upload_documents = findViewById(R.id.upload_documents);
        weight_TV = findViewById(R.id.weight_TV);
        weight_ET = findViewById(R.id.weight_ET);
        ilness_onset=findViewById(R.id.ilness_onset);
        folow_up_dt_view=findViewById(R.id.folow_up_dt_view);
        Dewormer_TV = findViewById(R.id.Dewormer_TV);
        Dewormer_ET = findViewById(R.id.Dewormer_ET);
        diagnosis_TV = findViewById(R.id.diagnosis_TV);
        treatment_remaks_TV = findViewById(R.id.treatment_remaks_TV);
        Dewormer_name_TV = findViewById(R.id.Dewormer_name_TV);
        Dewormer_name_ET = findViewById(R.id.Dewormer_name_ET);
        clinicTemparature_ET = findViewById(R.id.temparature_ET);
        clinicIlness_onset = findViewById(R.id.ilness_onset);
        clinicDiagnosis_ET = findViewById(R.id.diagnosis_ET);
        clinicTreatment_remarks_MT = findViewById(R.id.clinicTreatment_remarks_MT);
        clinicNext_visit_spinner = findViewById(R.id.next_visit_spinner);
        clinicFolow_up_dt_view = findViewById(R.id.folow_up_dt_view);
        clinicDocument_layout = findViewById(R.id.document_layout);
        treatment_remarks_LL = findViewById(R.id.treatment_remarks_LL);
        clinicSave_clinic_data = findViewById(R.id.save_clinic_data);
        pet_age_TV = findViewById(R.id.pet_age_TV);
        clinic_back_arrow_IV = findViewById(R.id.clinic_back_arrow_IV);
        vaccine_layout = findViewById(R.id.vaccine_layout);
        add_immunization_data = findViewById(R.id.add_immunization_data);
        immunization_data = findViewById(R.id.immunization_data);
        description_TV = findViewById(R.id.description_TV);
        temparature_TV = findViewById(R.id.temparature_TV);
        webview = findViewById(R.id.webview);

        clinicCalenderTextViewVisitDt.setOnClickListener(this);
        clinicIlness_onset.setOnClickListener(this);
        clinicFolow_up_dt_view.setOnClickListener(this);
        clinicSave_clinic_data.setOnClickListener(this);
        upload_documents.setOnClickListener(this);
        clinic_back_arrow_IV.setOnClickListener(this);
        add_immunization_data.setOnClickListener(this);


        clinicTreatment_remarks_MT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("kkakakkakak","beforeTextChanged"+new String(charSequence.toString()));
                Log.d("kkakakkakak","beforeTextChangedLength"+charSequence.length());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("kkakakkakak","onTextChanged"+new String(charSequence.toString()));
                Log.d("kkakakkakak","onTextChangedLength"+charSequence.length());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("kkakakkakak","afterTextChanged"+new String(editable.toString()));
                String value=editable.toString();
                SearchRemaksParametr searchRemaksParametr=new SearchRemaksParametr();
                searchRemaksParametr.setPrefix(value);
                SearchRemaksRequest searchRemaksRequest=new SearchRemaksRequest();
                searchRemaksRequest.setAddPetParams(searchRemaksParametr);
                if (methods.isInternetOn()){
                    getTreatmentRemaks(searchRemaksRequest);
                }else {
                    methods.DialogInternet();
                }

            }
        });



        //8583896504(Suresh Das).

        if (extras != null) {
            report_id = extras.getString("report_id");
            pet_id = extras.getString("pet_id");
            pet_name = extras.getString("pet_name");
            pet_owner_name = extras.getString("pet_owner_name");
            pet_sex = extras.getString("pet_sex");
            pet_age = extras.getString("pet_age");
            pet_unique_id = extras.getString("pet_unique_id");
            strNatureOfVist = extras.getString("nature_of_visit");
            visitDate= extras.getString("visit_dt");
            descrisption = extras.getString("visit_description");
            weight = extras.getString("visit_weight");
            temparature = extras.getString("visit_temparature");
            dtOfOnset = extras.getString("dt_of_illness");
            diagnosis = extras.getString("pet_diognosis");
            flowUpDt= extras.getString("next_dt");
            pet_DOB= extras.getString("pet_DOB");
            pet_encrypted_id= extras.getString("pet_encrypted_id");
            strToolbarName= extras.getString("toolbar_name");

            if(strToolbarName.equals("Update Clinic"))
                clinicSave_clinic_data.setText("UPDATE");
            else
                clinicSave_clinic_data.setText("SUBMIT");

            clinic_head_line.setText(strToolbarName);
            clinic_peto_edit_reg_number_dialog.setText(pet_unique_id);
            clinicVeterian_name_ET.setText(Config.user_Veterian_name);

            if(visitDate.equals("Update Clinic"))
                clinicCalenderTextViewVisitDt.setText(visitDate);
            else
                clinicCalenderTextViewVisitDt.setText(Config.currentDate());

            if(!descrisption.equals(""))
                clinicCescription_ET.setText(descrisption);

            if(!weight.equals(""))
                weight_ET.setText(weight);

            if(!temparature.equals(""))
                clinicTemparature_ET.setText(temparature);

            if(!dtOfOnset.equals(""))
                clinicIlness_onset.setText(dtOfOnset);

            if(!diagnosis.equals(""))
                clinicDiagnosis_ET.setText(diagnosis);

            if(!flowUpDt.equals(""))
                clinicFolow_up_dt_view.setText(flowUpDt);
        }


        if (methods.isInternetOn()){
            getClientVisit();
            getVisitTypes();
            getImmunizationData();
            getVaccinationDetails();

        }else {

            methods.DialogInternet();
        }

    }

    private void getTreatmentRemaks(SearchRemaksRequest searchRemaksRequest) {
        ApiService<SearchRemaksResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getSearchTreatmentRemarks(Config.token,searchRemaksRequest), "SearchTreatmentRemarks");
        Log.d("SearchTreatmentRemarks","parameter"+searchRemaksRequest);
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
                final Calendar cldrIll = Calendar.getInstance();
                int dayIll = cldrIll.get(Calendar.DAY_OF_MONTH);
                int monthIll = cldrIll.get(Calendar.MONTH);
                int yearIll = cldrIll.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                ilness_onset.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, yearIll, monthIll, dayIll);
                picker.show();
                break;
            case R.id.folow_up_dt_view:
                final Calendar cldrNext = Calendar.getInstance();
                int dayNext = cldrNext.get(Calendar.DAY_OF_MONTH);
                int monthNext = cldrNext.get(Calendar.MONTH);
                int yearNext = cldrNext.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                folow_up_dt_view.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, yearNext, monthNext, dayNext);
                picker.show();
                break;
            case R.id.save_clinic_data:
                veterian_name=clinicVeterian_name_ET.getText().toString();
                descrisption=clinicCescription_ET.getText().toString();
                Remarks=remaks_ET.getText().toString();
                visitDate=clinicCalenderTextViewVisitDt.getText().toString();
                dtOfOnset=clinicIlness_onset.getText().toString();
                flowUpDt=clinicFolow_up_dt_view.getText().toString();
                weight=weight_ET.getText().toString();
                temparature=clinicTemparature_ET.getText().toString();
                diagnosis=clinicDiagnosis_ET.getText().toString();
                strDewormerName=Dewormer_ET.getText().toString();
                strDewormerDose=Dewormer_name_ET.getText().toString();
                 if(natureOfVisit.equals("Immunization"))
                    {
                        if((natureOfVisit.equals("Immunization"))&&(VaccineList.size()<1)){
                            clinicVeterian_name_ET.setError(null);
                            clinicCescription_ET.setError(null);
                            remaks_ET.setError(null);
                            clinicDiagnosis_ET.setError(null);
                            Dewormer_name_ET.setError(null);
                            Dewormer_ET.setError(null);
                            Toast.makeText(this, "Add Vaccine Type and Name", Toast.LENGTH_LONG).show();
                        }
                    else
                    {
                        vaccinationModels=new ArrayList<HashMap<String, String>>();
                        for(int i=0;i<VaccineList.size();i++){
                            vaccinationationModelHash=new HashMap<>();
                            StringTokenizer st = new StringTokenizer(VaccineList.get(i), ",");
                            String brandType = st.nextToken();
                            String vaccine_name = st.nextToken();
                            vaccinationationModelHash.put("id",String.valueOf(i));
                            vaccinationationModelHash.put("vaccineType",brandType);
                            vaccinationationModelHash.put("brandName",vaccine_name);
                            vaccinationationModelHash.put("vaccine",null);
                            vaccinationationModelHash.put("vaccineDose",null);
                            vaccinationationModelHash.put("immunizationDate",null);
                            vaccinationModels.add(vaccinationationModelHash);
                        }
                        Gson gson = new GsonBuilder().create();
                        JsonArray myCustomArray = gson.toJsonTree(vaccinationModels).getAsJsonArray();
                        Log.d("nannananna",""+myCustomArray);

                        ImmunizationAddClinicModel immunizationAddClinicModel=new ImmunizationAddClinicModel();
                        immunizationAddClinicModel.setId("0.0");
                        immunizationAddClinicModel.setEncryptedId(null);
                        immunizationAddClinicModel.setEncryptedPetId(null);
                        immunizationAddClinicModel.setPetId(pet_id.substring(0,pet_id.length()-2));
                        immunizationAddClinicModel.setPetName(pet_name);
                        immunizationAddClinicModel.setPetUniqueId(pet_unique_id);
                        immunizationAddClinicModel.setPetParentName(pet_owner_name);
                        immunizationAddClinicModel.setContactNumber(null);
                        immunizationAddClinicModel.setPetDOB(null);
                        immunizationAddClinicModel.setPetAge(null);
                        immunizationAddClinicModel.setPetAge(pet_age);
                        immunizationAddClinicModel.setPetSex(pet_sex);
                        immunizationAddClinicModel.setVeterinarian(Config.user_Veterian_name);
                        immunizationAddClinicModel.setVisitDate(clinicCalenderTextViewVisitDt.getText().toString());
                        immunizationAddClinicModel.setNatureOfVisitId(strNatureOfVist.substring(0,strNatureOfVist.length()-2));
                        immunizationAddClinicModel.setNatureOfVisitList(null);
                        immunizationAddClinicModel.setVaccine(null);
                        immunizationAddClinicModel.setOptionalVaccine(null);
                        immunizationAddClinicModel.setDescription("description");
                        immunizationAddClinicModel.setHistory(null);
                        immunizationAddClinicModel.setSymptoms(null);
                        immunizationAddClinicModel.setWeightLbs(null);
                        immunizationAddClinicModel.setWeightOz(null);
                        immunizationAddClinicModel.setTemperature(null);
                        immunizationAddClinicModel.setDateOfOnset(null);
                        immunizationAddClinicModel.setDateOfOnset(null);
                        immunizationAddClinicModel.setDewormerName(null);
                        immunizationAddClinicModel.setDewormerDose(null);
                        immunizationAddClinicModel.setTreatmentRemarks(null);
                        immunizationAddClinicModel.setDiagnosisProcedure(null);
                        immunizationAddClinicModel.setFollowUpId(null);
                        immunizationAddClinicModel.setFollowUpList(null);
                        immunizationAddClinicModel.setFollowUpDate(folow_up_dt_view.getText().toString());
                        immunizationAddClinicModel.setDocuments(strDocumentUrl);
                        immunizationAddClinicModel.setCreatedOn(null);
                        immunizationAddClinicModel.setCreatedBy(null);
                        immunizationAddClinicModel.setCreatedBy(null);
                        immunizationAddClinicModel.setVeterinarianUserId(null);
                        immunizationAddClinicModel.setNavStatus("0");
                        immunizationAddClinicModel.setAppointmentId("0");
                        immunizationAddClinicModel.setNatureOfVisit(null);
                        immunizationAddClinicModel.setPet(null);
                        immunizationAddClinicModel.setCreatedByUser(null);
                        immunizationAddClinicModel.setFollowUp(null);
                        immunizationAddClinicModel.setPetAgeList(null);
                        immunizationAddClinicModel.setVaccinationModels(myCustomArray);

                        ImmunizationClinicData immunizationClinicData=new ImmunizationClinicData();
                        immunizationClinicData.setAddPetParams(immunizationAddClinicModel);

                        if (methods.isInternetOn()){
                            addImmuzationData(immunizationClinicData);
                        }else {

                            methods.DialogInternet();
                        }

//                    Toast.makeText(this, "Successfully Immunization add", Toast.LENGTH_SHORT).show();

                    }

                }
                 else{
                     if(veterian_name.isEmpty()){
                         clinicVeterian_name_ET.setError("Enter Veterinarian Name");
                         clinicCescription_ET.setError(null);
                         remaks_ET.setError(null);
                         clinicDiagnosis_ET.setError(null);
                         Dewormer_name_ET.setError(null);
                         Dewormer_ET.setError(null);
                     }
                     else if(descrisption.isEmpty()){
                         clinicVeterian_name_ET.setError(null);
                         clinicCescription_ET.setError("Enter Description");
                         remaks_ET.setError(null);
                         clinicDiagnosis_ET.setError(null);
                         Dewormer_name_ET.setError(null);
                         Dewormer_ET.setError(null);
                     }
                     else if(Remarks.isEmpty()){
                         clinicVeterian_name_ET.setError(null);
                         clinicCescription_ET.setError(null);
                         remaks_ET.setError("Enter Remarks");
                         clinicDiagnosis_ET.setError(null);
                         Dewormer_name_ET.setError(null);
                         Dewormer_ET.setError(null);
                     }
                     else if(diagnosis.isEmpty()){
                         clinicVeterian_name_ET.setError(null);
                         clinicCescription_ET.setError(null);
                         remaks_ET.setError(null);
                         clinicDiagnosis_ET.setError("Enter Diagnosis");
                         Dewormer_name_ET.setError(null);
                         Dewormer_ET.setError(null);
                     }
                     else if(natureOfVisit.isEmpty()||(natureOfVisit.equals("Select Visit"))){
                         clinicVeterian_name_ET.setError(null);
                         clinicCescription_ET.setError(null);
                         remaks_ET.setError(null);
                         clinicDiagnosis_ET.setError(null);
                         Dewormer_name_ET.setError(null);
                         Dewormer_ET.setError(null);
                         Toast.makeText(this, "Select Nature of Visit", Toast.LENGTH_SHORT).show();
                     }

                     else if((natureOfVisit.equals("Deworming"))&&(strDewormerName.isEmpty()))
                     {
                         clinicVeterian_name_ET.setError(null);
                         clinicCescription_ET.setError(null);
                         remaks_ET.setError(null);
                         clinicDiagnosis_ET.setError(null);
                         Dewormer_name_ET.setError(null);
                         Dewormer_ET.setError("Enter Dewormer Name");
                     }
                     else if((natureOfVisit.equals("Deworming"))&&(strDewormerDose.isEmpty()))
                     {
                         clinicVeterian_name_ET.setError(null);
                         clinicCescription_ET.setError(null);
                         remaks_ET.setError(null);
                         clinicDiagnosis_ET.setError(null);
                         Dewormer_name_ET.setError(null);
                         Dewormer_ET.setError("Enter Dose");
                     }
                     else
                     {
                         methods.showCustomProgressBarDialog(this);
                         clinicVeterian_name_ET.setError(null);
                         clinicCescription_ET.setError(null);
                         remaks_ET.setError(null);
                         clinicDiagnosis_ET.setError(null);
                         Dewormer_name_ET.setError(null);
                         Dewormer_ET.setError(null);


                         if(strToolbarName.equals("Update Clinic")){
                             UpdateClinicReportsParams updateClinicReportsParams =new UpdateClinicReportsParams();
                             updateClinicReportsParams.setId(report_id);
                             updateClinicReportsParams.setPetId(pet_id);
                             updateClinicReportsParams.setVeterinarian(veterian_name);
                             updateClinicReportsParams.setVisitDate(visitDate);
                             updateClinicReportsParams.setNatureOfVisitId(strNatureOfVist);
                             updateClinicReportsParams.setVaccine(strVacine);
                             updateClinicReportsParams.setDescription(descrisption);
                             updateClinicReportsParams.setWeightLbs(weight);
                             updateClinicReportsParams.setWeightOz(weight);
                             updateClinicReportsParams.setTemperature(temparature);
                             updateClinicReportsParams.setDateOfOnset(dtOfOnset);
                             updateClinicReportsParams.setDewormerName(strDewormerName);
                             updateClinicReportsParams.setTreatmentRemarks(Remarks);
                             updateClinicReportsParams.setDiagnosisProcedure(diagnosis);
                             updateClinicReportsParams.setFollowUpId("");
                             updateClinicReportsParams.setFollowUpDate(flowUpDt);
                             updateClinicReportsParams.setDocuments(strDocumentUrl);
                             UpdateClinicReportsRequest updateClinicReportsRequest =new UpdateClinicReportsRequest();
                             updateClinicReportsRequest.setAddPetParams(updateClinicReportsParams);
                             if (methods.isInternetOn()){
                                 updateClinic(updateClinicReportsRequest);
                             }else {

                                 methods.DialogInternet();
                             }

                         }

                         else{
                             if (methods.isInternetOn()){
                                 AddPetClinicParam addPetClinicParam=new AddPetClinicParam();
                                 addPetClinicParam.setPetId(pet_id);
                                 addPetClinicParam.setVeterinarian(veterian_name);
                                 addPetClinicParam.setVisitDate(visitDate);
                                 addPetClinicParam.setNatureOfVisitId(strNatureOfVist);
                                 addPetClinicParam.setVaccine(strVacine);
                                 addPetClinicParam.setDescription(descrisption);
                                 addPetClinicParam.setWeightLbs(weight);
                                 addPetClinicParam.setWeightOz(weight);
                                 addPetClinicParam.setTemperature(temparature);
                                 addPetClinicParam.setDateOfOnset(dtOfOnset);
                                 addPetClinicParam.setDewormerName(strDewormerName);
                                 addPetClinicParam.setTreatmentRemarks(Remarks);
                                 addPetClinicParam.setDiagnosisProcedure(diagnosis);
                                 addPetClinicParam.setFollowUpId("");
                                 addPetClinicParam.setFollowUpDate(flowUpDt);
                                 addPetClinicParam.setDocuments(strDocumentUrl);
                                 AddPetClinicRequest addPetClinicRequest=new AddPetClinicRequest();
                                 addPetClinicRequest.setAddPetParams(addPetClinicParam);
                                 addPetClinicData(addPetClinicRequest);
                             }else {

                                 methods.DialogInternet();
                             }
                         }

                     }
                 }
                break;
            case R.id.upload_documents:
                showPictureDialog();
                break;
            case R.id.clinic_back_arrow_IV:
                onBackPressed();
                break;
            case R.id.add_immunization_data:
                showVaccineDialog();
                break;

        }
    }

    private void showVaccineDialog() {

        vaccineDialog = new Dialog(this);
        vaccineDialog.setContentView(R.layout.vaccination_add_layout);

        vaccine_type = (AppCompatSpinner) vaccineDialog.findViewById(R.id.vaccine_type);
        vaccine_name = (AppCompatSpinner) vaccineDialog.findViewById(R.id.vaccine_name);
        Button add_vaccine_data = (Button) vaccineDialog.findViewById(R.id.add_vaccine_data);
        Button add_vaccine_cancel = (Button) vaccineDialog.findViewById(R.id.add_vaccine_cancel);

        setVaccineTypeSpinner();
        setVaccineNameSpinner();

        add_vaccine_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("vaccinlist_before",""+VaccineList.size());
                if(strVaccineType.equals("Select Vaccine Type")||strVaccineName.equals("Select Vaccine Name"))
                {
                    Toast.makeText(AddClinicActivity.this, "Add Vaccine Data", Toast.LENGTH_SHORT).show();
                }
                else
                { VaccineList.add(strVaccineType+", "+strVaccineName);
                    Log.e("vaccinlist_after",""+VaccineList.size());
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddClinicActivity.this);
                    immunization_data.setLayoutManager(linearLayoutManager);
                    immunization_data.setNestedScrollingEnabled(false);
                    if(VaccineList.size()>0){
                        hospitalizationReportsAdapter = new ImmunazationVaccineAdopter(AddClinicActivity.this,AddClinicActivity.this, VaccineList);
                        immunization_data.setAdapter(hospitalizationReportsAdapter);
                        hospitalizationReportsAdapter.notifyDataSetChanged();
                        vaccineDialog.dismiss();
                    }
                    else
                    {
                        vaccineDialog.dismiss();
                    }
                }



            }
        });

        add_vaccine_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaccineDialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = vaccineDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        vaccineDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        vaccineDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        vaccineDialog.show();
    }

    private void vaccineDetailsDialog()
    {
        vaccineDetailsDialog = new Dialog(this);
        vaccineDetailsDialog.setContentView(R.layout.test_layout);

        TextView pet_details=vaccineDetailsDialog.findViewById(R.id.pet_details);
        ImageView clinic_back_arrow_IV=vaccineDetailsDialog.findViewById(R.id.clinic_back_arrow_IV);
        RecyclerView vaccine_type_name_list=vaccineDetailsDialog.findViewById(R.id.vaccine_type_name_list);
        RecyclerView pereodic_list=vaccineDetailsDialog.findViewById(R.id.pereodic_list);
        RecyclerView immunization_history_list=vaccineDetailsDialog.findViewById(R.id.immunization_history_list);
        pereodic_list.setVisibility(View.GONE);
        pet_details.setText("Pet Name : "+pet_name+"( "+pet_unique_id+") Age :"+ strPetAge+" Days");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddClinicActivity.this);
        vaccine_type_name_list.setLayoutManager(linearLayoutManager);
        vaccine_type_name_list.setNestedScrollingEnabled(false);
        vaccineTypeAdapter = new VaccineTypeAdapter(AddClinicActivity.this, getVaccineResponseModels,AddClinicActivity.this);
        vaccine_type_name_list.setAdapter(vaccineTypeAdapter);
        vaccineTypeAdapter.notifyDataSetChanged();

        clinic_back_arrow_IV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaccineDetailsDialog.dismiss();
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = vaccineDetailsDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        vaccineDetailsDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        vaccineDetailsDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        vaccineDetailsDialog.show();
    }

    private void setVaccineTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,vaccineTypeList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        vaccine_type.setAdapter(aa);
        vaccine_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("vaccineType",""+item);
                strVaccineType=item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setVaccineNameSpinner(){
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,vaccineNameList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        vaccine_name.setAdapter(aa);
        vaccine_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("vaccineName",""+item);
                strVaccineName=item;
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }


    private void showPictureDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);

        TextView select_camera = (TextView) dialog.findViewById(R.id.select_camera);
        TextView select_gallery = (TextView) dialog.findViewById(R.id.select_gallery);
        TextView cancel_dialog = (TextView) dialog.findViewById(R.id.cancel_dialog);

        select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhotoFromCamera();
            }
        });

        select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePhotoFromGallary();
            }
        });

        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void choosePhotoFromGallary() {


        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);

    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        dialog.dismiss();
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {

                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    document_name.setImageBitmap(bitmap);
                    saveImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(AddClinicActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        }
        else if (requestCode == CAMERA) {

            if (data.getData() == null)
            {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Log.e("jghl",""+thumbnail);
                document_name.setImageBitmap(thumbnail);
                saveImage(thumbnail);
                Toast.makeText(AddClinicActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            }

            else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(AddClinicActivity.this.getContentResolver(), data.getData());
                    document_name.setImageBitmap(bitmap);
                    saveImage(bitmap);
                    Toast.makeText(AddClinicActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            file = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".png");
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{file.getPath()},
                    new String[]{"image/png"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + file.getAbsolutePath());
            UploadImages(file);
            return file.getAbsolutePath();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void UploadImages(File absolutePath) {
        MultipartBody.Part userDpFilePart = null;
        if (absolutePath != null) {
            RequestBody userDpFile = RequestBody.create(MediaType.parse("image/*"), absolutePath);
            userDpFilePart = MultipartBody.Part.createFormData("file", absolutePath.getName(), userDpFile);
        }

        ApiService<ImageResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().uploadImages(Config.token,userDpFilePart), "UploadDocument");
        Log.e("DATALOG","check1=> "+service);
        methods.showCustomProgressBarDialog(this);
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(AddClinicActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(AddClinicActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void getImmunizationData() {
        ImmunizationParameter immunizationParameter=new ImmunizationParameter();
        immunizationParameter.setCategoryId("1");
        immunizationParameter.setPetDOB(pet_DOB);
        immunizationParameter.setEncryptedId(pet_encrypted_id);
        ImmunizationRequestt immunizationRequestt=new ImmunizationRequestt();
        immunizationRequestt.setImmunizationData(immunizationParameter);
        ApiService<ImmunizationVaccineResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getRecommendedVaccine(Config.token,immunizationRequestt), "GetRecommendedVaccine");
        Log.e("DATALOG",""+immunizationRequestt);
    }

    private void getVisitTypes() {
        ApiService<GetReportsTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getReportsType(Config.token), "GetReportsType");
    }

    private void addImmuzationData(ImmunizationClinicData immunizationClinicData){
        ApiService<ImmunizationAddResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().addPetVaccination(Config.token,immunizationClinicData), "AddPetVaccination");
        Log.e("DATALOG_AddPetVaccin",""+immunizationClinicData);
        methods.showCustomProgressBarDialog(this);
    }


    private void addPetClinicData(AddPetClinicRequest addPetClinicRequest) {
        ApiService<AddpetClinicResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addClinicVisit(Config.token,addPetClinicRequest), "AddClinicVisit");
        Log.d("AddClinicData==>",""+addPetClinicRequest);
    }

    private void updateClinic(UpdateClinicReportsRequest updateClinicReportsRequest) {
        ApiService<AddpetClinicResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().updateClinicVisit(Config.token,updateClinicReportsRequest), "UpdateClinicVisit");
        Log.d("UpdateClinicData==>",""+updateClinicReportsRequest);
    }
    private void getclinicVisitsReportDetails() {
        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(pet_id.substring(0,pet_id.length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("petClinicVisitDetail",petClinicVisitDetailsRequest.toString());
        ApiService<GetClinicVisitsDetailsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getLastPrescription(Config.token,petClinicVisitDetailsRequest), "GetPetClinicVisitDetails");
        methods.showCustomProgressBarDialog(this);
    }

    private void getVaccinationDetails() {
        GetVaccinationModelParameter getVaccinationModelParameter=new GetVaccinationModelParameter();
        //getVaccinationModelParameter.setEncryptedId(pet_encrypted_id);
        getVaccinationModelParameter.setEncryptedId("MTQ=");
        GetVaccinationRequest getVaccinationRequest=new GetVaccinationRequest();
        getVaccinationRequest.setData(getVaccinationModelParameter);
        ApiService<GetVaccineResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getVaccinationScheduleChart(Config.token,getVaccinationRequest), "GetVaccinationScheduleChart");
        Log.e("GetVaccinSchedule==>",""+getVaccinationRequest);
    }

    private void saveVaccineData(VaccinationRequest vaccinationRequest) {
        ApiService<SaveImmunizationResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().saveImmunizationDetails(Config.token,vaccinationRequest), "SaveImmunizationDetails");
        Log.e("GetVaccinSchedule==>",""+vaccinationRequest);
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
            case "UploadDocument":
                try {
                    Log.d("UploadDocument",arg0.body().toString());
                    ImageResponse imageResponse = (ImageResponse) arg0.body();
                    int responseCode = Integer.parseInt(imageResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        strDocumentUrl=imageResponse.getData().getDocumentUrl();
                        Toast.makeText(this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                        methods.customProgressDismiss();
                    }else if (responseCode==614){
                        Toast.makeText(this, imageResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                        methods.customProgressDismiss();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                        methods.customProgressDismiss();
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
                        Toast.makeText(this, "Add Data Successfully", Toast.LENGTH_SHORT).show();
                        Config.type="list";
                        getclinicVisitsReportDetails();
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

            case "AddPetVaccination":
                try {
                    methods.customProgressDismiss();
                    Log.d("ammaamamma","aaalaa.,.");
                    ImmunizationAddResponse immunizationAddResponse = (ImmunizationAddResponse) arg0.body();
                    Log.d("AddPetVaccination", immunizationAddResponse.toString());
                    int responseCode = Integer.parseInt(immunizationAddResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        Toast.makeText(this, "Add Data Successfully", Toast.LENGTH_SHORT).show();
                        Config.type="list";
                        onBackPressed();
                    }
                    else if (responseCode==614){
                        Toast.makeText(this, immunizationAddResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
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
            case "UpdateClinicVisit":
                try {
                    methods.customProgressDismiss();
                    AddpetClinicResponse addpetClinicResponse = (AddpetClinicResponse) arg0.body();
                    Log.d("UpdateClinicVisit", addpetClinicResponse.toString());
                    int responseCode = Integer.parseInt(addpetClinicResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        Toast.makeText(this, "Update Data Successfully", Toast.LENGTH_SHORT).show();
                        Config.type="list";
                        getclinicVisitsReportDetails();
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
            case "SearchTreatmentRemarks":
                try {
                    SearchRemaksResponse searchRemaksResponse = (SearchRemaksResponse) arg0.body();
                    Log.d("SearchTreatmentRemarks", searchRemaksResponse.toString());
                    int responseCode = Integer.parseInt(searchRemaksResponse.getResponse().getResponseCode());


                    if (responseCode== 109){
                        /*Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();*/
                        remarksSearchList=new ArrayList<>();
                        for(int i=0;i<searchRemaksResponse.getData().size();i++)
                        {
                            remarksSearchList.add(searchRemaksResponse.getData().get(i).getRemarks());
                        }
                        ArrayAdapter<String> randomArray = new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1, remarksSearchList);
                        clinicTreatment_remarks_MT.setAdapter(randomArray);
                        clinicTreatment_remarks_MT.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                        clinicTreatment_remarks_MT.setOnItemClickListener(onItemClickListener);
                        randomArray.notifyDataSetChanged();
                    }
                    else if (responseCode==614){
                        Toast.makeText(this, searchRemaksResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
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
            case "GetPetClinicVisitDetails":
                try {
                    Log.d("ResponseClinicVisit", arg0.body().toString());
                    GetClinicVisitsDetailsResponse getClinicVisitsDetailsResponse = (GetClinicVisitsDetailsResponse) arg0.body();
                    int responseCode = Integer.parseInt(getClinicVisitsDetailsResponse.getResponse().getResponseCode());
                    Log.d("ajajjaja",""+responseCode);
                    Config.type="list";
                    if (responseCode == 109) {
                        String str=methods.pdfGenarator(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getPetName(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getPetAge(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getPetSex(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getPetParentName(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getTemperature(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getDescription(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getDiagnosisProcedure(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getTreatmentRemarks(),
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getFollowUpDate(),
                                getClinicVisitsDetailsResponse.getData().getVeterinarianDetails().getVetRegistrationNumber());
                        webview.loadDataWithBaseURL(null,str,"text/html","utf-8",null);
                        backPressVal=1;
                        new Handler().postDelayed(new Runnable(){
                            @Override
                            public void run() {
                                methods.customProgressDismiss();
                                Context context=AddClinicActivity.this;
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
                    else
                    {
                        methods.customProgressDismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetRecommendedVaccine":
                try {
                    Log.d("GetRecommendedVaccine", arg0.body().toString());
                    ImmunizationVaccineResponse immunizationVaccineResponse = (ImmunizationVaccineResponse) arg0.body();
                    int responseCode = Integer.parseInt(immunizationVaccineResponse.getResponse().getResponseCode());
                    vaccineTypeList=new ArrayList<>();
                    vaccineTypeList.add("Select Vaccine Type");
                    /*vaccineTypeList.add("Primary");
                    vaccineTypeList.add("BoosterOne");
                    vaccineTypeList.add("BoosterTwo");
                    vaccineTypeList.add("Periodic");*/
                    vaccineNameList=new ArrayList<>();
                    vaccineNameList.add("Select Vaccine Name");
                   /* vaccineNameList.add("abc");
                    vaccineNameList.add("Puppy DP");
                    vaccineNameList.add("CCV");
                    vaccineNameList.add("DHLPPA");
                    vaccineNameList.add("CKC");*/
                    if (responseCode == 109) {
                        strNextVisitDate=immunizationVaccineResponse.getData().getNextVisitDate();
                        strPetAge=immunizationVaccineResponse.getData().getAge();

                       if(immunizationVaccineResponse.getData().getVaccineTypeList().size()>0){
                           for(int i=0;i<immunizationVaccineResponse.getData().getVaccineTypeList().size();i++)
                           {
                               vaccineTypeList.add(immunizationVaccineResponse.getData().getVaccineTypeList().get(i).getValue());
                           }
                       }

                       if(immunizationVaccineResponse.getData().getPrimaryVaccine().size()>0)
                       {
                           for(int j=0; j<immunizationVaccineResponse.getData().getPrimaryVaccine().size();j++){
                               vaccineNameList.add(immunizationVaccineResponse.getData().getPrimaryVaccine().get(j));
                           }
                       }

                    }
                    else
                    {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetVaccinationScheduleChart":
                try {
                    Log.d("GetVaccinationSchedul", arg0.body().toString());
                    GetVaccineResponse getVaccineResponse = (GetVaccineResponse) arg0.body();
                    int responseCode = Integer.parseInt(getVaccineResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Log.e("amamamammamamam","Vaccine Detailsss"+getVaccineResponse.getData().size());
                        getVaccineResponseModels = getVaccineResponse.getData();
                        Log.d("knananannan",""+getVaccineResponse.getData().get(1).getVaccinationSchedule().getMaximunAge());

                    }
                    else
                    {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case "SaveImmunizationDetails":
                try {
                    Log.d("SaveImmunizationDetails", arg0.body().toString());
                    SaveImmunizationResponse getVaccineResponse = (SaveImmunizationResponse) arg0.body();
                    int responseCode = Integer.parseInt(getVaccineResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show();
                        vaccineDetailsDialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(this, "Not Save", Toast.LENGTH_SHORT).show();
                        vaccineDetailsDialog.dismiss();
                    }
                } catch (Exception e) {
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
                visitIdString=item;
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
        if (!strNatureOfVist.equals("")) {
            int spinnerPosition = aa.getPosition(strNatureOfVist);
            clinicNature_of_visit_spinner.setSelection(spinnerPosition);
        }
        clinicNature_of_visit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                natureOfVisit=item;
                strNatureOfVist=natureOfVisitHashMap.get(natureOfVisit);
                if(natureOfVisit.equals("Immunization"))
                {
                    date_of_illness_TV.setVisibility(View.GONE);
                    clinicIlness_onset.setVisibility(View.GONE);
                    description_TV.setVisibility(View.GONE);
                    clinicCescription_ET.setVisibility(View.GONE);
                    weight_TV.setVisibility(View.GONE);
                    weight_ET.setVisibility(View.GONE);
                    temparature_TV.setVisibility(View.GONE);
                    clinicTemparature_ET.setVisibility(View.GONE);
                    diagnosis_TV.setVisibility(View.GONE);
                    clinicDiagnosis_ET.setVisibility(View.GONE);
                    treatment_remaks_TV.setVisibility(View.GONE);
                    treatment_remarks_LL.setVisibility(View.GONE);
                    pet_age_TV.setVisibility(View.VISIBLE);
                    pet_age_TV.setText(strPetAge+" Days");
                    folow_up_dt_view.setText(strNextVisitDate);
                    vaccine_layout.setVisibility(View.VISIBLE);
                    vaccineDetailsDialog();
                }
                else if(natureOfVisit.equals("Deworming"))
                {
                    date_of_illness_TV.setVisibility(View.GONE);
                    clinicIlness_onset.setVisibility(View.GONE);
                    pet_age_TV.setVisibility(View.GONE);
                    //vaccine_TV.setVisibility(View.GONE);
                    vaccine_layout.setVisibility(View.GONE);
              //      vacine_ET.setVisibility(View.GONE);
                    Dewormer_TV.setVisibility(View.VISIBLE);
                    Dewormer_ET.setVisibility(View.VISIBLE);
                    Dewormer_name_TV.setVisibility(View.VISIBLE);
                    Dewormer_name_ET.setVisibility(View.VISIBLE);
                    folow_up_dt_view.setText("");

                }
                else{
                    date_of_illness_TV.setVisibility(View.VISIBLE);
                    clinicIlness_onset.setVisibility(View.VISIBLE);
                    date_of_illness_TV.setVisibility(View.VISIBLE);
                    clinicIlness_onset.setVisibility(View.VISIBLE);
                    description_TV.setVisibility(View.VISIBLE);
                    clinicCescription_ET.setVisibility(View.VISIBLE);
                    weight_TV.setVisibility(View.VISIBLE);
                    weight_ET.setVisibility(View.VISIBLE);
                    temparature_TV.setVisibility(View.VISIBLE);
                    clinicTemparature_ET.setVisibility(View.VISIBLE);
                    diagnosis_TV.setVisibility(View.VISIBLE);
                    clinicDiagnosis_ET.setVisibility(View.VISIBLE);
                    treatment_remaks_TV.setVisibility(View.VISIBLE);
                    treatment_remarks_LL.setVisibility(View.VISIBLE);
                    folow_up_dt_view.setText("");
                   // vaccine_TV.setVisibility(View.GONE);
                    vaccine_layout.setVisibility(View.GONE);
                    pet_age_TV.setVisibility(View.GONE);
//                    vacine_ET.setVisibility(View.GONE);
                    Dewormer_TV.setVisibility(View.GONE);
                    Dewormer_ET.setVisibility(View.GONE);
                    Dewormer_name_TV.setVisibility(View.GONE);
                    Dewormer_name_ET.setVisibility(View.GONE);

                }
                Log.d("Spinner",""+item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(AddClinicActivity.this,
                            "Clicked item from auto completion list "
                                    + adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                    if(clinicTreatment_remarks_MT.getText().toString().isEmpty())
                    {

                    }
                    else{
                        String val=String.valueOf(adapterView.getItemAtPosition(i));

                        if(cocatVal==null)
                            cocatVal=val;
                        else
                            cocatVal=cocatVal+","+val;
                        remaks_ET.setText(cocatVal);
                        clearSearch();
                    }
                }
            };

    private void clearSearch() {
        clinicTreatment_remarks_MT.getText().clear();
        InputMethodManager imm1 = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(clinicTreatment_remarks_MT.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(backPressVal==1)
            onBackPressed();
    }

    @Override
    public void onItemClick(int position) {
        VaccineList.remove(position);
        Log.e("vaccinlist_after",""+VaccineList.size());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AddClinicActivity.this);
        immunization_data.setLayoutManager(linearLayoutManager);
        immunization_data.setNestedScrollingEnabled(false);
            hospitalizationReportsAdapter = new ImmunazationVaccineAdopter(AddClinicActivity.this,AddClinicActivity.this, VaccineList);
            immunization_data.setAdapter(hospitalizationReportsAdapter);
            hospitalizationReportsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClickImmunizationPrimary(int position) {
        Log.e("vaccinlist_primery","");
        VaccinationParameter vaccinationParameter=new VaccinationParameter();
        vaccinationParameter.setEncryptedId(pet_encrypted_id);
        vaccinationParameter.setVaccinationScheduleId(getVaccineResponseModels.get(position).getVaccinationSchedule().getId());
        vaccinationParameter.setVaccine(getVaccineResponseModels.get(position).getVaccinationSchedule().getPrimaryVaccine());
        vaccinationParameter.setVaccineType("Primary");
        VaccinationRequest vaccinationRequest=new VaccinationRequest();
        vaccinationRequest.setData(vaccinationParameter);
        saveVaccineData(vaccinationRequest);

    }
    @Override
    public void onItemClickImmunizationBoosterOne(int position) {
        Log.e("vaccinlist_booster_one","");
        VaccinationParameter vaccinationParameter=new VaccinationParameter();
        vaccinationParameter.setEncryptedId(pet_encrypted_id);
        vaccinationParameter.setVaccinationScheduleId(getVaccineResponseModels.get(position).getVaccinationSchedule().getId());
        vaccinationParameter.setVaccine(getVaccineResponseModels.get(position).getVaccinationSchedule().getPrimaryVaccine());
        vaccinationParameter.setVaccineType("Booster One");
        VaccinationRequest vaccinationRequest=new VaccinationRequest();
        vaccinationRequest.setData(vaccinationParameter);
        saveVaccineData(vaccinationRequest);
    }



    @Override
    public void onItemClickImmunizationBoosterTwo(int position) {
        Log.e("vaccinlist_Booster_two","");
        VaccinationParameter vaccinationParameter=new VaccinationParameter();
        vaccinationParameter.setEncryptedId(pet_encrypted_id);
        vaccinationParameter.setVaccinationScheduleId(getVaccineResponseModels.get(position).getVaccinationSchedule().getId());
        vaccinationParameter.setVaccine(getVaccineResponseModels.get(position).getVaccinationSchedule().getPrimaryVaccine());
        vaccinationParameter.setVaccineType("Booster Two");
        VaccinationRequest vaccinationRequest=new VaccinationRequest();
        vaccinationRequest.setData(vaccinationParameter);
        saveVaccineData(vaccinationRequest);
    }
}