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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import retrofit2.Response;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addLabRequest.AddLabParams;
import com.cynoteck.petofyvet.params.addLabRequest.AddLabRequest;
import com.cynoteck.petofyvet.response.addLabWorkResponse.AddLabWorkResponse;
import com.cynoteck.petofyvet.response.labTyperesponse.LabTypeResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddLabWorkDeatilsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    EditText veterian_name_ET,veterian_phone_ET,lab_name_ET,lab_phone_ET,test_name_ET,reson_of_visit_ET,result_ET;
    AppCompatSpinner labType_spinner;
    TextView calenderTextViewVisitDt;
    Button save_BT;

    Methods methods;

    ArrayList<String>labTypeArrayList;

    HashMap<String,String>labTypeHash=new HashMap<>();

    DatePickerDialog picker;

    String LabTypeId="",pet_id="",pet_name="",pet_owner_name="",pet_sex="",pet_unique_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lab_work_deatils);
        init();

    }

    private void init() {
        methods=new Methods(this);
        Bundle extras = getIntent().getExtras();
        veterian_name_ET=findViewById(R.id.veterian_name_ET);
        veterian_phone_ET=findViewById(R.id.veterian_phone_ET);
        lab_name_ET=findViewById(R.id.lab_name_ET);
        lab_phone_ET=findViewById(R.id.lab_phone_ET);
        test_name_ET=findViewById(R.id.test_name_ET);
        reson_of_visit_ET=findViewById(R.id.reson_of_visit_ET);
        result_ET=findViewById(R.id.result_ET);
        labType_spinner=findViewById(R.id.labType_spinner);
        calenderTextViewVisitDt=findViewById(R.id.calenderTextViewVisitDt);
        save_BT=findViewById(R.id.save_BT);

        calenderTextViewVisitDt.setOnClickListener(this);
        save_BT.setOnClickListener(this);

        if (extras != null) {
            pet_id = extras.getString("pet_id");
            pet_name = extras.getString("pet_name");
            pet_owner_name = extras.getString("pet_owner_name");
            pet_sex = extras.getString("pet_sex");
            pet_unique_id = extras.getString("pet_unique_id");
        }

        if (methods.isInternetOn()){
            getLabTypeList();
        }else {
            methods.DialogInternet();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.calenderTextViewVisitDt:
                final Calendar cldrFolwUpDt = Calendar.getInstance();
                int dayFolwUpDt = cldrFolwUpDt.get(Calendar.DAY_OF_MONTH);
                int monthFolwUpDt = cldrFolwUpDt.get(Calendar.MONTH);
                int yearFolwUpDt = cldrFolwUpDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextViewVisitDt.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayFolwUpDt, monthFolwUpDt, yearFolwUpDt);
                picker.show();
                break;
            case R.id.save_BT:
                String strRequstVeterian=veterian_name_ET.getText().toString();
                String strPhoneNumber=veterian_phone_ET.getText().toString();
                String strLabName=lab_name_ET.getText().toString();
                String strLabphoneNumber=lab_phone_ET.getText().toString();
                String strNameofTest=test_name_ET.getText().toString();
                String strReasonOfVisit=reson_of_visit_ET.getText().toString();
                String strRsult=result_ET.getText().toString();
                String strLabVisitDt=calenderTextViewVisitDt.getText().toString();

                if(strRequstVeterian.isEmpty()){
                    veterian_name_ET.setError("Enter Veterinarian Name");
                    veterian_phone_ET.setError(null);
                    lab_name_ET.setError(null);
                    test_name_ET.setError(null);
                    reson_of_visit_ET.setError(null);
                    result_ET.setError(null);
                }
                else if(strPhoneNumber.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    veterian_phone_ET.setError("Enter Contact Number");
                    lab_name_ET.setError(null);
                    test_name_ET.setError(null);
                    reson_of_visit_ET.setError(null);
                    result_ET.setError(null);
                }
                else if(strLabName.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    veterian_phone_ET.setError(null);
                    lab_name_ET.setError("Enter Lab Name");
                    test_name_ET.setError(null);
                    reson_of_visit_ET.setError(null);
                    result_ET.setError(null);
                }
                else if(strNameofTest.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    veterian_phone_ET.setError(null);
                    lab_name_ET.setError(null);
                    test_name_ET.setError("Name of Test");
                    reson_of_visit_ET.setError(null);
                    result_ET.setError(null);
                }
                else if(strReasonOfVisit.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    veterian_phone_ET.setError(null);
                    lab_name_ET.setError(null);
                    test_name_ET.setError(null);
                    reson_of_visit_ET.setError("Reason of Test");
                    result_ET.setError(null);
                }
                else if(strRsult.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    veterian_phone_ET.setError(null);
                    lab_name_ET.setError(null);
                    test_name_ET.setError(null);
                    reson_of_visit_ET.setError(null);
                    result_ET.setError("Result");
                }
                else if(strLabVisitDt.isEmpty())
                {
                    veterian_name_ET.setError(null);
                    veterian_phone_ET.setError(null);
                    lab_name_ET.setError(null);
                    test_name_ET.setError(null);
                    reson_of_visit_ET.setError(null);
                    result_ET.setError(null);
                    Toast.makeText(this, "Select Lab type ", Toast.LENGTH_SHORT).show();
                }
                else{
                    veterian_name_ET.setError(null);
                    veterian_phone_ET.setError(null);
                    lab_name_ET.setError(null);
                    test_name_ET.setError(null);
                    reson_of_visit_ET.setError(null);
                    result_ET.setError(null);
                    AddLabParams addLabParams=new AddLabParams();
                    addLabParams.setPetId(pet_id);
                    addLabParams.setRequestingVeterinarian(strRequstVeterian);
                    addLabParams.setVeterinarianPhone(strPhoneNumber);
                    addLabParams.setVisitDate(strLabVisitDt);
                    addLabParams.setLabTypeId(LabTypeId);
                    addLabParams.setNameOfLab(strLabName);
                    addLabParams.setLabPhone(strLabphoneNumber);
                    addLabParams.setAddress1("");
                    addLabParams.setAddress2("");
                    addLabParams.setCountryId("");
                    addLabParams.setStateId("");
                    addLabParams.setCityId("");
                    addLabParams.setZipCode("");
                    addLabParams.setTestName(strNameofTest);
                    addLabParams.setReasonOfTest(strReasonOfVisit);
                    addLabParams.setResults(strRsult);
                    addLabParams.setDocuments("");
                    AddLabRequest addLabRequest=new AddLabRequest();
                    addLabRequest.setAddPetParams(addLabParams);
                    if(methods.isInternetOn())
                    {
                        addPetLabTestReport(addLabRequest);
                    }
                    else
                    {
                        methods.DialogInternet();
                    }

                }

                break;

        }

    }

    private void getLabTypeList() {
        ApiService<LabTypeResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getLabTypeList(Config.token), "GetLabTypeList");
       }

    private void addPetLabTestReport(AddLabRequest addLabRequest) {
        ApiService<AddLabWorkResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addPetLabWork(Config.token,addLabRequest), "AddPetLabWork");
        Log.d("addPetLabParams",""+addLabRequest);
    }

    @Override
    public void onResponse(Response arg0, String key) {

        switch (key) {
            case "GetLabTypeList":
                try {
                    LabTypeResponse labTypeResponse = (LabTypeResponse) arg0.body();
                    Log.d("GetLabTypeList", labTypeResponse.toString());
                    int responseCode = Integer.parseInt(labTypeResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        labTypeArrayList = new ArrayList<>();
                        labTypeArrayList.add("Select Lab Type");
                        for (int i = 0; i < labTypeResponse.getData().size(); i++) {
                            labTypeArrayList.add(labTypeResponse.getData().get(i).getLab());
                            labTypeHash.put(labTypeResponse.getData().get(i).getLab(), labTypeResponse.getData().get(i).getId());
                        }
                        setSpinneerLabType();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, labTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "AddPetLabWork":
                try {
                    AddLabWorkResponse addLabWorkResponse = (AddLabWorkResponse) arg0.body();
                    Log.d("AddPetLabWork", addLabWorkResponse.toString());
                    int responseCode = Integer.parseInt(addLabWorkResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, addLabWorkResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    private void setSpinneerLabType() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,labTypeArrayList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        labType_spinner.setAdapter(aa);
        labType_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                LabTypeId=labTypeHash.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
