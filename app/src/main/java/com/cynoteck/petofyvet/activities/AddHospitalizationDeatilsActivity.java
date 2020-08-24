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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Response;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addHospitalization.AddHospitalizationParam;
import com.cynoteck.petofyvet.params.addHospitalization.AddHospitalizationRequest;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicParam;
import com.cynoteck.petofyvet.params.addPetClinicParamRequest.AddPetClinicRequest;
import com.cynoteck.petofyvet.response.addHospitalizationResponse.AddhospitalizationResposee;
import com.cynoteck.petofyvet.response.clinicVisist.ClinicVisitResponse;
import com.cynoteck.petofyvet.response.hospitalTypeListResponse.HospitalAddmissionTypeResp;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddHospitalizationDeatilsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    EditText veterian_name_ET,veterian_phone_ET,hospital_name_ET,hospital_phone_ET,reson_of_hospitalization_ET,result_ET;
    Spinner hospital_type_spinner;
    TextView calenderTextView_admission_date,calenderTextView_discharge_date_TV;
    Button save_BT;

    Methods methods;

    ArrayList<String> hospitalTypeArrayList;

    HashMap<String,String> hospitalTypeHashmap=new HashMap<>();

    DatePickerDialog picker;

    String hospitalizationId="",pet_id="",pet_name="",pet_owner_name="",pet_sex="",pet_unique_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospitalization_deatils);
        init();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        veterian_name_ET=findViewById(R.id.veterian_name_ET);
        veterian_phone_ET=findViewById(R.id.veterian_phone_ET);
        hospital_name_ET=findViewById(R.id.hospital_name_ET);
        hospital_phone_ET=findViewById(R.id.hospital_phone_ET);
        reson_of_hospitalization_ET=findViewById(R.id.reson_of_hospitalization_ET);
        result_ET=findViewById(R.id.result_ET);
        hospital_type_spinner=findViewById(R.id.hospital_type_spinner);
        calenderTextView_admission_date=findViewById(R.id.calenderTextView_admission_date);
        calenderTextView_discharge_date_TV=findViewById(R.id.calenderTextView_discharge_date_TV);
        save_BT=findViewById(R.id.save_BT);

        calenderTextView_admission_date.setOnClickListener(this);
        calenderTextView_discharge_date_TV.setOnClickListener(this);
        save_BT.setOnClickListener(this);

        if (extras != null) {
            pet_id = extras.getString("pet_id");
            pet_name = extras.getString("pet_name");
            pet_owner_name = extras.getString("pet_owner_name");
            pet_sex = extras.getString("pet_sex");
            pet_unique_id = extras.getString("pet_unique_id");
        }

        methods=new Methods(this);

        if (methods.isInternetOn()){
            getHospitalTypeList();
        }else {
            methods.DialogInternet();
        }


    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.calenderTextView_admission_date:
                final Calendar cldrFolwUpDt = Calendar.getInstance();
                int dayFolwUpDt = cldrFolwUpDt.get(Calendar.DAY_OF_MONTH);
                int monthFolwUpDt = cldrFolwUpDt.get(Calendar.MONTH);
                int yearFolwUpDt = cldrFolwUpDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_admission_date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayFolwUpDt, monthFolwUpDt, yearFolwUpDt);
                picker.show();
                break;
            case R.id.calenderTextView_discharge_date_TV:
                final Calendar cldrDeschrgDt = Calendar.getInstance();
                int dayDeschrgDt = cldrDeschrgDt.get(Calendar.DAY_OF_MONTH);
                int monthDeschrgDt = cldrDeschrgDt.get(Calendar.MONTH);
                int yearDeschrgDt = cldrDeschrgDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_discharge_date_TV.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayDeschrgDt, monthDeschrgDt, yearDeschrgDt);
                picker.show();
                break;
            case R.id.save_BT:
                String strRequstVeterian=veterian_name_ET.getText().toString();
                String strPhoneNumber=veterian_phone_ET.getText().toString();
                String strHospitalName=hospital_name_ET.getText().toString();
                String strHospitalphoneNumber=hospital_phone_ET.getText().toString();
                String strResonsOfHospitalization=reson_of_hospitalization_ET.getText().toString();
                String strResult=result_ET.getText().toString();
                String strHospitalAdmissionDt=calenderTextView_admission_date.getText().toString();
                String strHospitalDischargeDt=calenderTextView_discharge_date_TV.getText().toString();

                if(strRequstVeterian.isEmpty()){
                    veterian_name_ET.setError("Enter Veterinarian Name");
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError(null);
                }
                else if(strHospitalName.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError("Enter Hospital Name");
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError(null);
                }
                else if(strResonsOfHospitalization.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError("Reason of Hospitalization");
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError(null);
                }
                else if(strHospitalAdmissionDt.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError("Enter Admission Date");
                    calenderTextView_discharge_date_TV.setError(null);
                }
                else if(strHospitalDischargeDt.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError("Enter discharge Date");
                }
                else if(hospitalizationId.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    hospital_name_ET.setError(null);
                    reson_of_hospitalization_ET.setError(null);
                    calenderTextView_admission_date.setError(null);
                    calenderTextView_discharge_date_TV.setError(null);
                    Toast.makeText(this, "Select Hospitalization type ", Toast.LENGTH_SHORT).show();
                }
                else{
                    AddHospitalizationParam addHospitalizationParam=new AddHospitalizationParam();
                    addHospitalizationParam.setPetId(pet_id);
                    addHospitalizationParam.setRequestingVeterinarian(strRequstVeterian);
                    addHospitalizationParam.setVeterinarianPhone(strPhoneNumber);
                    addHospitalizationParam.setHospitalizationTypeId(hospitalizationId);
                    addHospitalizationParam.setAdmissionDate(strHospitalAdmissionDt);
                    addHospitalizationParam.setDischargeDate(strHospitalDischargeDt);
                    addHospitalizationParam.setHospitalName(strHospitalName);
                    addHospitalizationParam.setHospitalPhone(strHospitalphoneNumber);
                    addHospitalizationParam.setAddress("");
                    addHospitalizationParam.setCountryId("");
                    addHospitalizationParam.setStateId("");
                    addHospitalizationParam.setCityId("");
                    addHospitalizationParam.setZipCode("");
                    addHospitalizationParam.setReasonForHospitalization(strResonsOfHospitalization);
                    addHospitalizationParam.setDiagnosisTreatmentProcedure(strResult);
                    addHospitalizationParam.setDocuments("");
                    AddHospitalizationRequest addHospitalizationRequest=new AddHospitalizationRequest();
                    addHospitalizationRequest.setData(addHospitalizationParam);

                    if (methods.isInternetOn()){
                        addHospitalization(addHospitalizationRequest);
                    }else {
                        methods.DialogInternet();
                    }

                }

                break;

        }

    }

    private void addHospitalization(AddHospitalizationRequest addHospitalizationRequest) {
        ApiService<AddhospitalizationResposee> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addPetHospitalization(Config.token,addHospitalizationRequest), "AddPetHospitalization");
        Log.e("AddPetHospitalParam","===>"+addHospitalizationRequest);

    }

    private void getHospitalTypeList() {
        ApiService<HospitalAddmissionTypeResp> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getHospitalTypeList(Config.token), "getHospitalTypeList");
    }


    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "getHospitalTypeList":
                try {
                    HospitalAddmissionTypeResp hospitalAddmissionTypeResponse = (HospitalAddmissionTypeResp) arg0.body();
                    Log.d("getHospitalTypeList", hospitalAddmissionTypeResponse.toString());
                    int responseCode = Integer.parseInt(hospitalAddmissionTypeResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        hospitalTypeArrayList = new ArrayList<>();
                        hospitalTypeArrayList.add("Select Hospital Type");
                        for (int i = 0; i < hospitalAddmissionTypeResponse.getData().size(); i++) {
                            hospitalTypeArrayList.add(hospitalAddmissionTypeResponse.getData().get(i).getHospitalization());
                            hospitalTypeHashmap.put(hospitalAddmissionTypeResponse.getData().get(i).getHospitalization(), hospitalAddmissionTypeResponse.getData().get(i).getId());
                        }
                        setSpinnerHospitalizationType();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, hospitalAddmissionTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "AddPetHospitalization":
                try {
                    AddhospitalizationResposee addhospitalizationResposee = (AddhospitalizationResposee) arg0.body();
                    Log.d("AddPetHospitalization", addhospitalizationResposee.toString());
                    int responseCode = Integer.parseInt(addhospitalizationResposee.getResponse().getResponseCode());

                    if (responseCode == 109) {

                    } else if (responseCode == 614) {
                        Toast.makeText(this, addhospitalizationResposee.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    private void setSpinnerHospitalizationType() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,hospitalTypeArrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        hospital_type_spinner.setAdapter(aa);
        hospital_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                hospitalizationId=hospitalTypeHashmap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
