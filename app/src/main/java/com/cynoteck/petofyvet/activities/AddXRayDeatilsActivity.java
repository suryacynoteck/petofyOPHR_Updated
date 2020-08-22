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
import com.cynoteck.petofyvet.response.hospitalTypeListResponse.HospitalAddmissionTypeResp;
import com.cynoteck.petofyvet.response.testResponse.XrayTestResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddXRayDeatilsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    TextView peto_edit_reg_number_dialog,calenderTextViewtestdate,folow_up_dt_view;
    AppCompatSpinner nature_of_visit_spinner;
    EditText description_ET;
    Button save_BT;

    Methods methods;

    ArrayList<String> testTypeList;

    HashMap<String,String> testTypehas=new HashMap<>();

    String testTypeId="";

    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_x_ray_deatils);
        init();
    }

    private void init() {
        peto_edit_reg_number_dialog=findViewById(R.id.peto_edit_reg_number_dialog);
        calenderTextViewtestdate=findViewById(R.id.calenderTextViewtestdate);
        folow_up_dt_view=findViewById(R.id.folow_up_dt_view);
        nature_of_visit_spinner=findViewById(R.id.nature_of_visit_spinner);
        description_ET=findViewById(R.id.description_ET);
        save_BT=findViewById(R.id.save_BT);

        save_BT.setOnClickListener(this);
        calenderTextViewtestdate.setOnClickListener(this);
        folow_up_dt_view.setOnClickListener(this);

        methods=new Methods(this);

        if (methods.isInternetOn()){
            getTestTypeList();
        }else {
            methods.DialogInternet();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.folow_up_dt_view:
                final Calendar cldrFolwUpDt = Calendar.getInstance();
                int dayFolwUpDt = cldrFolwUpDt.get(Calendar.DAY_OF_MONTH);
                int monthFolwUpDt = cldrFolwUpDt.get(Calendar.MONTH);
                int yearFolwUpDt = cldrFolwUpDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                folow_up_dt_view.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayFolwUpDt, monthFolwUpDt, yearFolwUpDt);
                picker.show();
                break;
            case R.id.calenderTextViewtestdate:
                final Calendar cldrDeschrgDt = Calendar.getInstance();
                int dayDeschrgDt = cldrDeschrgDt.get(Calendar.DAY_OF_MONTH);
                int monthDeschrgDt = cldrDeschrgDt.get(Calendar.MONTH);
                int yearDeschrgDt = cldrDeschrgDt.get(Calendar.YEAR);
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextViewtestdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, dayDeschrgDt, monthDeschrgDt, yearDeschrgDt);
                picker.show();
                break;
            case R.id.save_BT:
                String strDescription=description_ET.getText().toString();
                String strPetUniqueId=peto_edit_reg_number_dialog.getText().toString();
                String strDt=calenderTextViewtestdate.getText().toString();
                String strFolowUpDt=folow_up_dt_view.getText().toString();

                if(strDescription.isEmpty()){
                    description_ET.setError("Enter Results");
                    calenderTextViewtestdate.setError(null);
                }
                else if(strDt.isEmpty())
                {
                    description_ET.setError(null);
                    calenderTextViewtestdate.setError("Enter test Date");
                }
                else if(testTypeId.isEmpty())
                {
                    description_ET.setError(null);
                    calenderTextViewtestdate.setError(null);
                    Toast.makeText(this, "Select Test type ", Toast.LENGTH_SHORT).show();
                }
                else{
                    description_ET.setError(null);
                    calenderTextViewtestdate.setError(null);
                }

                break;

        }

    }

    private void getTestTypeList() {
        ApiService<XrayTestResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getTestTypeList(Config.token), "GetTestTypeList");
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "GetTestTypeList":
                try {
                    XrayTestResponse xrayTestResponse = (XrayTestResponse) arg0.body();
                    Log.d("GetTestTypeList", xrayTestResponse.toString());
                    int responseCode = Integer.parseInt(xrayTestResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                        testTypeList = new ArrayList<>();
                        testTypeList.add("Select Test Type");
                        for (int i = 0; i < xrayTestResponse.getData().size(); i++) {
                            testTypeList.add(xrayTestResponse.getData().get(i).getTestType());
                            testTypehas.put(xrayTestResponse.getData().get(i).getTestType(), xrayTestResponse.getData().get(i).getId());
                        }
                        setSpinnerTestType();
                    } else if (responseCode == 614) {
                        Toast.makeText(this, xrayTestResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }

    }

    private void setSpinnerTestType() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,testTypeList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        nature_of_visit_spinner.setAdapter(aa);
        nature_of_visit_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                testTypeId=testTypehas.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    public void onError(Throwable t, String key) {

    }
}
