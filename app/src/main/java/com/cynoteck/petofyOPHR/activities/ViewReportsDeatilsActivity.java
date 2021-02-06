
package com.cynoteck.petofyOPHR.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.petReportsRequest.PetClinicVisitDetailsRequest;
import com.cynoteck.petofyOPHR.params.petReportsRequest.PetClinicVistsDetailsParams;
import com.cynoteck.petofyOPHR.response.getPetReportsResponse.AddUpdateDeleteClinicVisitResponse;
import com.cynoteck.petofyOPHR.response.getPetReportsResponse.getClinicVisitDetails.GetClinicVisitsDetailsResponse;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.Methods;

import retrofit2.Response;

public class ViewReportsDeatilsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    String clinic_id, type;
    TextView vet_name_textView,visit_date_textView,nature_ofVist_textView,weight_textView,temperature_textView,vaccine_textView,symptoms_textView,diagnosis_textView,remarks_textView;
    ImageView back_arrow_IV;
    Button deleteReport_BT;
    String pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id ,report_type_id;
    TextView pet_name_TV,pet_sex_TV,pet_id_TV,pet_owner_name_TV,pet_owner_phone_no_TV;
    Methods methods;
    ProgressBar progressBar;
    CardView card_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_deatils);
        methods = new Methods(this);

        init();
        setdataInfields();
        getclinicVisitsReportDetails();

    }

    private void setdataInfields() {

        pet_name_TV.setText(pet_name);
        pet_sex_TV.setText("(" +pet_sex+ ")");
        pet_id_TV.setText(pet_unique_id);
        pet_owner_name_TV.setText(pet_owner_name);
        pet_owner_phone_no_TV.setText(pet_owner_contact);

    }

    private void getclinicVisitsReportDetails() {

        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(clinic_id.substring(0,clinic_id.length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("petClinicVisitDetail",petClinicVisitDetailsRequest.toString());
        ApiService<GetClinicVisitsDetailsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getClinicVisitDetails(Config.token,petClinicVisitDetailsRequest), "GetPetClinicVisitDetails");

    }

    private void init() {

        Intent extras = getIntent();
        clinic_id = extras.getExtras().getString("clinic_id");
        pet_owner_contact = extras.getExtras().getString("pet_owner_contact");
        pet_owner_name = extras.getExtras().getString("pet_owner_name");
        pet_sex = extras.getExtras().getString("pet_sex");
        pet_name = extras.getExtras().getString("pet_name");
        pet_unique_id = extras.getExtras().getString("pet_unique_id");
        report_type_id=extras.getExtras().getString("id");

        card_view = findViewById(R.id.card_view);
        progressBar = findViewById(R.id.progressBar);
        deleteReport_BT=findViewById(R.id.deleteReport_BT);
        vet_name_textView=findViewById(R.id.vet_name_textView);
        visit_date_textView=findViewById(R.id.visit_date_textView);
        nature_ofVist_textView=findViewById(R.id.nature_ofVist_textView);
        weight_textView=findViewById(R.id.weight_textView);
        temperature_textView=findViewById(R.id.temperature_textView);
        vaccine_textView=findViewById(R.id.vaccine_textView);
        symptoms_textView=findViewById(R.id.symptoms_textView);
        diagnosis_textView=findViewById(R.id.diagnosis_textView);
        remarks_textView=findViewById(R.id.remarks_textView);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);

        pet_name_TV = findViewById(R.id.pet_name_TV);
        pet_sex_TV = findViewById(R.id.pet_sex_TV);
        pet_id_TV = findViewById(R.id.pet_id_TV);
        pet_owner_name_TV = findViewById(R.id.pet_owner_name_TV);
        pet_owner_phone_no_TV = findViewById(R.id.pet_owner_phone_no_TV);
        back_arrow_IV.setOnClickListener(this);
        deleteReport_BT.setOnClickListener(this);

    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key){
            case "GetPetClinicVisitDetails":
                try {
                    Log.d("ResponseClinicVisit",response.body().toString());
                    GetClinicVisitsDetailsResponse getClinicVisitsDetailsResponse = (GetClinicVisitsDetailsResponse) response.body();
                    int responseCode = Integer.parseInt(getClinicVisitsDetailsResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        deleteReport_BT.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        card_view.setVisibility(View.VISIBLE);
                        vet_name_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getVeterinarian());
                        visit_date_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getVisitDate());
                        nature_ofVist_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getNatureOfVisit().getNature());
                        weight_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getWeightLbs());
                        temperature_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getTemperature());
                        vaccine_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getVaccine());
                        symptoms_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getDescription());
                        diagnosis_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getDiagnosisProcedure());
                        remarks_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getTreatmentRemarks());

                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

                case "DeletePetClinicVisitDetails":
                    try {
                        Log.d("DeleteClinicVisit",response.body().toString());
                        AddUpdateDeleteClinicVisitResponse addUpdateDeleteClinicVisitResponse = (AddUpdateDeleteClinicVisitResponse) response.body();
                        int responseCode = Integer.parseInt(addUpdateDeleteClinicVisitResponse.getResponse().getResponseCode());
                        if (responseCode== 109){
                            Config.type = "list";
                            onBackPressed();
                            Toast.makeText(this, "Report deleted successfully!", Toast.LENGTH_SHORT).show();
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
        Log.e("error",t.getLocalizedMessage());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.deleteReport_BT:
                Log.d("Add Anotheer Veterian","vet");
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("Do You Want to Delete This Report ?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (methods.isInternetOn()) {
                                    deletClinicVisitDetails();
                                } else {
                                    methods.DialogInternet();
                                }
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog.show();
                break;
        }

    }

    private void deletClinicVisitDetails() {

        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(clinic_id.substring(0,clinic_id.length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("DeleteClinicVisit",petClinicVisitDetailsRequest.toString());
        ApiService<AddUpdateDeleteClinicVisitResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().deleteClinicVisit(Config.token,petClinicVisitDetailsRequest), "DeletePetClinicVisitDetails");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
