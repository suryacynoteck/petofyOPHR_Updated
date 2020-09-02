
package com.cynoteck.petofyvet.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVisitDetailsRequest;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVistsDetailsParams;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getClinicVisitDetails.GetClinicVisitsDetailsResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

public class PetDetailsActivity extends AppCompatActivity implements View.OnClickListener, ApiResponse {
    String pet_id,pet_name,patent_name,pet_bread,pet_unique_id="",pet_sex="",pet_age="";
    TextView pet_nameTV, pet_parentNameTV,pet_id_TV;
    ImageView back_arrow_IV,view_clinicVisits_arrow,view_xrayReport_arrow,view_labTestReport_arrow,view_Hospitalization_arrow,last_prescription_arrow,recent_visits_arrow,print_id_card_arrow,view_history_arrow;
    RelativeLayout clinic_test,xray_test,lab_test_report,hospitalization_sugeries,last_prescription,print_id_card,view_history;
    Methods methods;
    WebView webview;

    ArrayList<String>nextVisitList=new ArrayList<>();

    HashMap<String,String>nextVisitHas=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        methods = new Methods(this);
        Bundle extras = getIntent().getExtras();
        pet_id=extras.getString("pet_id");
        pet_name=extras.getString("pet_name");
        patent_name=extras.getString("pet_parent");
        pet_unique_id=extras.getString("pet_unique_id");
        pet_sex=extras.getString("pet_sex");
        pet_age=extras.getString("pet_age");

        pet_nameTV = findViewById(R.id.pet_name_TV);
        pet_parentNameTV = findViewById(R.id.pet_owner_name_TV);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        pet_id_TV=findViewById(R.id.pet_id_TV);
        view_Hospitalization_arrow=findViewById(R.id.view_Hospitalization_arrow);
        hospitalization_sugeries=findViewById(R.id.hospitalization_sugeries);
        clinic_test=findViewById(R.id.clinic_test);
        recent_visits_arrow=findViewById(R.id.recent_visits_arrow);
        print_id_card_arrow=findViewById(R.id.print_id_card_arrow);
        print_id_card=findViewById(R.id.print_id_card);
        view_clinicVisits_arrow=findViewById(R.id.view_clinicVisits_arrow);
        last_prescription=findViewById(R.id.last_prescription);
        view_history_arrow=findViewById(R.id.view_history_arrow);
        view_history=findViewById(R.id.view_history);
        view_xrayReport_arrow=findViewById(R.id.view_xrayReport_arrow);
        xray_test=findViewById(R.id.xray_test);
        view_labTestReport_arrow=findViewById(R.id.view_labTestReport_arrow);
        lab_test_report=findViewById(R.id.lab_test_report);
        last_prescription_arrow=findViewById(R.id.last_prescription_arrow);
        webview=findViewById(R.id.webview);

        view_clinicVisits_arrow.setOnClickListener(this);
        view_labTestReport_arrow.setOnClickListener(this);
        lab_test_report.setOnClickListener(this);
        view_Hospitalization_arrow.setOnClickListener(this);
        hospitalization_sugeries.setOnClickListener(this);
        clinic_test.setOnClickListener(this);
        recent_visits_arrow.setOnClickListener(this);
        print_id_card_arrow.setOnClickListener(this);
        print_id_card.setOnClickListener(this);
        view_history_arrow.setOnClickListener(this);
        view_history.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        last_prescription.setOnClickListener(this);
        view_xrayReport_arrow.setOnClickListener(this);
        xray_test.setOnClickListener(this);

        pet_nameTV.setText(pet_name+"("+pet_sex+")");
        pet_parentNameTV.setText(patent_name);
        pet_id_TV.setText(pet_unique_id);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xray_test:
//                Intent staticReportsIntent = new Intent(this, ReportsCommonActivity.class);
//                Bundle staticReportsData = new Bundle();
//                staticReportsData.putString("pet_id",pet_id);
//                staticReportsData.putString("pet_name",pet_name);
//                staticReportsData.putString("pet_unique_id",pet_unique_id);
//                staticReportsData.putString("pet_sex",pet_sex);
//                staticReportsData.putString("pet_parent",patent_name);
//                staticReportsData.putString("pet_owner_contact","");
//                staticReportsData.putString("reports_id","7.0");
//                staticReportsData.putString("button_type","update");
//                staticReportsIntent.putExtras(staticReportsData);
//                startActivity(staticReportsIntent);
//                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
                Intent petDetailsXray = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataXray = new Bundle();
                dataXray.putString("pet_id",pet_id);
                dataXray.putString("pet_name",pet_name);
                dataXray.putString("pet_parent",patent_name);
                dataXray.putString("pet_unique_id",pet_unique_id);
                dataXray.putString("reports_id","7.0");
                dataXray.putString("pet_sex",pet_sex);
                dataXray.putString("pet_age",pet_age);
                dataXray.putString("add_button_text","Test/X-rays");
                dataXray.putString("button_type","update");
                petDetailsXray.putExtras(dataXray);
                startActivity(petDetailsXray);
                break;
            case R.id.lab_test_report:
                Intent petDetailsLabWork = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataLabwork = new Bundle();
                dataLabwork.putString("pet_id",pet_id);
                dataLabwork.putString("pet_name",pet_name);
                dataLabwork.putString("pet_parent",patent_name);
                dataLabwork.putString("pet_unique_id",pet_unique_id);
                dataLabwork.putString("reports_id","8.0");
                dataLabwork.putString("pet_sex",pet_sex);
                dataLabwork.putString("pet_age",pet_age);
                dataLabwork.putString("button_type","update");
                dataLabwork.putString("add_button_text","Lab Work");
                petDetailsLabWork.putExtras(dataLabwork);
                startActivity(petDetailsLabWork);
                break;
            case R.id.hospitalization_sugeries:
                Intent petDetailsHospitalization = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataLabworkHospitalization = new Bundle();
                dataLabworkHospitalization.putString("pet_id",pet_id);
                dataLabworkHospitalization.putString("pet_name",pet_name);
                dataLabworkHospitalization.putString("pet_parent",patent_name);
                dataLabworkHospitalization.putString("pet_unique_id",pet_unique_id);
                dataLabworkHospitalization.putString("reports_id","9.0");
                dataLabworkHospitalization.putString("pet_sex",pet_sex);
                dataLabworkHospitalization.putString("pet_age",pet_age);
                dataLabworkHospitalization.putString("button_type","update");
                dataLabworkHospitalization.putString("add_button_text","Hospitalization");
                petDetailsHospitalization.putExtras(dataLabworkHospitalization);
                startActivity(petDetailsHospitalization);
                break;
            case R.id.view_history:
                Intent petHistory = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataHistry = new Bundle();
                dataHistry.putString("pet_id",pet_id.substring(0,pet_id.length()-2));
                dataHistry.putString("pet_name",pet_name);
                dataHistry.putString("pet_parent",patent_name);
                dataHistry.putString("pet_unique_id",pet_unique_id);
                dataHistry.putString("reports_id","12.0");
                dataHistry.putString("pet_sex",pet_sex);
                dataHistry.putString("pet_age",pet_age);
                dataHistry.putString("add_button_text","petHistory");
                petHistory.putExtras(dataHistry);
                startActivity(petHistory);
                break;
            case R.id.recent_visits_arrow:
                Intent petDetailsLabVisits = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataLabworkVisits = new Bundle();
                dataLabworkVisits.putString("pet_id",pet_id);
                dataLabworkVisits.putString("pet_name",pet_name);
                dataLabworkVisits.putString("pet_parent",patent_name);
                dataLabworkVisits.putString("pet_unique_id",pet_unique_id);
                dataLabworkVisits.putString("reports_id","11.0");
                dataLabworkVisits.putString("pet_sex",pet_sex);
                dataLabworkVisits.putString("pet_age",pet_age);
                dataLabworkVisits.putString("add_button_text","RecentVisit");
                petDetailsLabVisits.putExtras(dataLabworkVisits);
                startActivity(petDetailsLabVisits);
                break;
            case R.id.print_id_card:
                Intent intent = new Intent(this,PetIdCardActivity.class);
                Bundle dataLabworkIdCard = new Bundle();
                dataLabworkIdCard.putString("id",pet_id);
                intent.putExtras(dataLabworkIdCard);
                startActivity(intent);
                break;
            case R.id.clinic_test:
                Intent petDetailsClinicVisits = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataClinicVisits = new Bundle();
                dataClinicVisits.putString("pet_id",pet_id);
                dataClinicVisits.putString("pet_name",pet_name);
                dataClinicVisits.putString("pet_parent",patent_name);
                dataClinicVisits.putString("pet_unique_id",pet_unique_id);
                dataClinicVisits.putString("reports_id","1.0");
                dataClinicVisits.putString("pet_sex",pet_sex);
                dataClinicVisits.putString("pet_age",pet_age);
                dataClinicVisits.putString("add_button_text","Clinic_visits");
                petDetailsClinicVisits.putExtras(dataClinicVisits);
                startActivity(petDetailsClinicVisits);
                break;
            case R.id.last_prescription:
                if(methods.isInternetOn())
                {
                    getclinicVisitsReportDetails();

                }
                else
                {
                    methods.DialogInternet();
                }
                break;
            case R.id.back_arrow_IV:
                onBackPressed();
                Config.backCall = "hitUnique";
                break;
        }

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

    @Override
    public void onResponse(Response arg0, String key) {
        Log.d("kkakakak",""+key+" response: "+arg0);
        switch (key) {
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

    public void lastPrescriptionPdf(String veterian, String strEmail,String qualification,String strForA, String strAge,String strSex, String strDate,String pet_parent, String strTemparature, String strDiagnosis,String strRemark, String strNxtVisit,String registration_number, String Symptons,String address)
    {
        String str=methods.pdfGenarator(strForA,strAge,strSex,pet_parent,strTemparature,Symptons,strDiagnosis,strRemark,strNxtVisit,registration_number);
        webview.loadDataWithBaseURL(null,str,"text/html","utf-8",null);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                methods.customProgressDismiss();
                Context context=PetDetailsActivity.this;
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

    @Override
    public void onError(Throwable t, String key) {

    }
}
