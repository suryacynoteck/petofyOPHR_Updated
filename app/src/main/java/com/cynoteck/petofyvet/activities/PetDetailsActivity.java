
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
    String pet_id,pet_name,patent_name,pet_bread,pet_unique_id="",pet_sex="";
    TextView pet_nameTV, pet_parentNameTV,pet_id_TV;
    ImageView back_arrow_IV,view_clinicVisits_arrow,view_xrayReport_arrow,view_labTestReport_arrow,view_Hospitalization_arrow,last_prescription_arrow,recent_visits_arrow,print_id_card_arrow,view_history_arrow;
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

        pet_nameTV = findViewById(R.id.pet_name_TV);
        pet_parentNameTV = findViewById(R.id.pet_owner_name_TV);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        pet_id_TV=findViewById(R.id.pet_id_TV);
        view_Hospitalization_arrow=findViewById(R.id.view_Hospitalization_arrow);
        recent_visits_arrow=findViewById(R.id.recent_visits_arrow);
        print_id_card_arrow=findViewById(R.id.print_id_card_arrow);
        view_clinicVisits_arrow=findViewById(R.id.view_clinicVisits_arrow);
        view_history_arrow=findViewById(R.id.view_history_arrow);
        view_xrayReport_arrow=findViewById(R.id.view_xrayReport_arrow);
        view_labTestReport_arrow=findViewById(R.id.view_labTestReport_arrow);
        last_prescription_arrow=findViewById(R.id.last_prescription_arrow);
        webview=findViewById(R.id.webview);

        view_clinicVisits_arrow.setOnClickListener(this);
        view_labTestReport_arrow.setOnClickListener(this);
        view_Hospitalization_arrow.setOnClickListener(this);
        recent_visits_arrow.setOnClickListener(this);
        print_id_card_arrow.setOnClickListener(this);
        view_history_arrow.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        last_prescription_arrow.setOnClickListener(this);
        view_xrayReport_arrow.setOnClickListener(this);

        pet_nameTV.setText(pet_name+"("+pet_sex+")");
        pet_parentNameTV.setText(patent_name);
        pet_id_TV.setText(pet_unique_id);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_xrayReport_arrow:
                Intent petDetailsXray = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataXray = new Bundle();
                dataXray.putString("pet_id",pet_id);
                dataXray.putString("pet_name",pet_name);
                dataXray.putString("pet_parent",patent_name);
                dataXray.putString("pet_unique_id",pet_unique_id);
                dataXray.putString("reports_id","7.0");
                dataXray.putString("pet_sex",pet_sex);
                dataXray.putString("add_button_text","Test/X-rays");
                petDetailsXray.putExtras(dataXray);
                startActivity(petDetailsXray);
                break;
            case R.id.view_labTestReport_arrow:
                Intent petDetailsLabWork = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataLabwork = new Bundle();
                dataLabwork.putString("pet_id",pet_id);
                dataLabwork.putString("pet_name",pet_name);
                dataLabwork.putString("pet_parent",patent_name);
                dataLabwork.putString("pet_unique_id",pet_unique_id);
                dataLabwork.putString("reports_id","8.0");
                dataLabwork.putString("pet_sex",pet_sex);
                dataLabwork.putString("add_button_text","Lab Work");
                petDetailsLabWork.putExtras(dataLabwork);
                startActivity(petDetailsLabWork);
                break;
            case R.id.view_Hospitalization_arrow:
                Intent petDetailsHospitalization = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataLabworkHospitalization = new Bundle();
                dataLabworkHospitalization.putString("pet_id",pet_id);
                dataLabworkHospitalization.putString("pet_name",pet_name);
                dataLabworkHospitalization.putString("pet_parent",patent_name);
                dataLabworkHospitalization.putString("pet_unique_id",pet_unique_id);
                dataLabworkHospitalization.putString("reports_id","9.0");
                dataLabworkHospitalization.putString("pet_sex",pet_sex);
                dataLabworkHospitalization.putString("add_button_text","Hospitalization");
                petDetailsHospitalization.putExtras(dataLabworkHospitalization);
                startActivity(petDetailsHospitalization);
                break;
            case R.id.view_history_arrow:
                Intent petHistory = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataHistry = new Bundle();
                dataHistry.putString("pet_id",pet_id);
                dataHistry.putString("pet_name",pet_name);
                dataHistry.putString("pet_parent",patent_name);
                dataHistry.putString("pet_unique_id",pet_unique_id);
                dataHistry.putString("reports_id","12.0");
                dataHistry.putString("pet_sex",pet_sex);
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
                dataLabworkVisits.putString("add_button_text","RecentVisit");
                petDetailsLabVisits.putExtras(dataLabworkVisits);
                startActivity(petDetailsLabVisits);
                break;
            case R.id.print_id_card_arrow:
                Intent intent = new Intent(this,PetIdCardActivity.class);
                Bundle dataLabworkIdCard = new Bundle();
                dataLabworkIdCard.putString("id",pet_id);
                intent.putExtras(dataLabworkIdCard);
                startActivity(intent);
                break;
            case R.id.view_clinicVisits_arrow:
                Intent petDetailsClinicVisits = new Intent(this, NewEntrysDetailsActivity.class);
                Bundle dataClinicVisits = new Bundle();
                dataClinicVisits.putString("pet_id",pet_id);
                dataClinicVisits.putString("pet_name",pet_name);
                dataClinicVisits.putString("pet_parent",patent_name);
                dataClinicVisits.putString("pet_unique_id",pet_unique_id);
                dataClinicVisits.putString("reports_id","1.0");
                dataClinicVisits.putString("pet_sex",pet_sex);
                dataClinicVisits.putString("add_button_text","Clinic_visits");
                petDetailsClinicVisits.putExtras(dataClinicVisits);
                startActivity(petDetailsClinicVisits);
                break;
            case R.id.last_prescription_arrow:
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
        petClinicVistsDetailsParams.setId(pet_id);
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("petClinicVisitDetail",petClinicVisitDetailsRequest.toString());
        ApiService<GetClinicVisitsDetailsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getLastPrescription(Config.token,petClinicVisitDetailsRequest), "GetPetClinicVisitDetails");

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
                                getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getFollowUpDate()
                        );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    public void lastPrescriptionPdf(String veterian, String strEmail,String qualification,String strForA, String strAge,String strSex, String strDate,String strParntNm, String strTemparature, String strDiagnosis,String strRemark, String strNxtVisit)
    {
        String care="Aviral Care";
        String pet_parent="Pramod Rana";
        String Symptons="Problems";
        String address="Dehradun";
        String registration_number="VET-00987";
        String str="<!DOCTYPE html>\n"+
                "<html>\n" +
                "<head>\n" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "<script src=\"//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>\n" +
                "    \n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css\">\n" +
                "\n" +
                "    <title>Invioce</title>\n" +
                "</head>\n" +
                "<style type=\"text/css\">\n" +
                "    .invoice-title h2, .invoice-title h3 {\n" +
                "        display: inline-block;\n" +
                "    }\n" +
                "\n" +
                "    .table > tbody > tr > .no-line {\n" +
                "        border-top: none;\n" +
                "    }\n" +
                "\n" +
                "    .table > thead > tr > .no-line {\n" +
                "        border-bottom: none;\n" +
                "    }\n" +
                "\n" +
                "    .table > tbody > tr > .thick-line {\n" +
                "        border-top: 2px solid;\n" +
                "    }\n" +
                "    @page {\n" +
                "      size: A4;\n" +
                "      margin: 15px;\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <p><?=date('d/m/Y')?></p> \n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-xs-12\">\n" +
                "            <div class=\"invoice-title \">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"col-lg-12 col-md-12 col-xs-12\" style=\"font-size: 25px;font-family: cizel;\">\n" +
                "                       <b>"+veterian+"</b> \n" +
                "                    </div>\n" +
                "                    <div class=\"col-lg-12 col-md-12 col-xs-12\" style=\"font-size: 20px; margin-bottom: 20px;\">\n" +
                "                        "+qualification+"\n" +
                "                    </div>\n" +
                "                    <div class=\"col-lg-6 col-md-6 col-xs-6\" style=\"font-size: 20px; \" >\n" +care+
                "                       \n" +
                "                    </div>\n" +
                "                    <div class=\"col-lg-6 col-md-6 col-xs-6 text-right\" style=\"font-size: 20px;\">\n" +
                "                       <b> Mobile :"+strParntNm+" </b>\n" +
                "                    </div>\n" +
                "                    \n" +
                "                    <div class=\"col-lg-6 col-md-6 col-xs-6\" style=\"font-size: 17px;\">\n" +
                "                       <b> Email: "+strEmail+" </b>\n" +
                "                    </div>\n" +
                "                    <div class=\"col-lg-6 col-md-6 col-xs-6 text-right\" style=\"font-size: 20px;\">\n" +
                "                       <b> "+strParntNm+"</b>\n" +
                "                    </div>\n" +
                "                 \n" +
                "                    \n" +
                "                </div>\n" +
                "               \n" +
                "                \n" +
                "            </div>\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-md-12\" style=\"border: 1px solid black;\"></div>\n" +
                "            </div>\n" +
                "            <div class=\"row\">\n" +
                "                <div class=\"col-xs-3\" style=\"font-size: 20px;\">\n" +
                "                    <b>For a: "+strForA+"</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-3\" style=\"font-size: 20px;\">\n" +
                "                    <b>Age: "+strAge+"</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-3\" style=\"font-size: 20px;\">\n" +
                "                    <b>Sex: "+strSex+"</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-3\" style=\"font-size: 20px; margin-bottom: 25px;\">\n" +
                "                    <b>Date:"+strDate+" <?=date('d/m/Y')?></b>\n" +
                "                </div>\n" +
                "\n" +
                "\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 25px;\">\n" +
                "                    <b>Pet Parant Name:"+pet_parent+"</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 10px;\">\n" +
                "                    <b>Temparature(F): "+strTemparature+"</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 25px;\">\n" +
                "                    <b>Symptons:</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"margin-bottom: 10px;\">\n" +
                "                    <p>"+Symptons+"</p>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 25px;\">\n" +
                "                    <b>Diagnosis:</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 10px;\">\n" +
                "                    <p>"+strDiagnosis+"</p>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 25px;\">\n" +
                "                    <b>Treatment Remarks:</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 10px;\">\n" +
                "                    <p>"+strRemark+"</p>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 25px;\">\n" +
                "                    <b>Next Visit:</b>\n" +
                "                </div>\n" +
                "                <div class=\"col-xs-12\" style=\"font-size: 20px; margin-bottom: 10px;\">\n" +
                "                    <p>"+strNxtVisit+"</p>\n" +
                "                </div>\n" +
                "\n" +
                "            </div><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>\n" +
                "            <div class=\"col-md-12\" style=\"border: 1px solid black;\"></div>\n" +
                "            <div class=\"col-md-12\" style=\"font-size: 25px; text-align: center;\">Address: "+address+", Registration Number: "+registration_number+"</div>\n" +
                "            \n" +
                "        </div>\n" +
                "\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "</div>\n" +
                "<script type=\"text/javascript\">\n" +
                "    $(function(){\n" +
                "        window.print();\n" +
                "        window.close();\n" +
                "    });\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
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
