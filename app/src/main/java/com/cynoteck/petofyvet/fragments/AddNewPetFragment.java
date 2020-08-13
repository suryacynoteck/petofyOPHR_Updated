package com.cynoteck.petofyvet.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.PetDetailsActivity;
import com.cynoteck.petofyvet.adapters.NewEntrysAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.response.recentEntrys.PetClinicVisitList;
import com.cynoteck.petofyvet.response.recentEntrys.RecentEntrysResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.cynoteck.petofyvet.utils.NewEntryListClickListener;
import com.cynoteck.petofyvet.utils.StaffListClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Response;

public class AddNewPetFragment extends Fragment implements ApiResponse,View.OnClickListener, StaffListClickListener, NewEntryListClickListener, TextWatcher {
    Methods methods;
    RecyclerView all_new_entry_list;
    ShimmerFrameLayout shimmer_view_new_entry;
    NewEntrysAdapter newEntrysAdapter;
    ImageView new_pet_search,back_arrow_IV_new_entry;
    List<PetClinicVisitList> petClinicVisitLists;
    RelativeLayout search_boxRL;
    EditText search_box_add_new;
    Dialog add_staff_dialog, prescription_dialog;
    TextView staff_headline_TV,parent_name,specilist,email,for_a,age,sex,date,prnt_nm,temparature,symptoms,diagnosis,
             remarks,nxt_visit;
    Button crrete_pdf,cancel;
    WebView webview;
    LinearLayout addNewEntry;
    View view;

    public AddNewPetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.activity_add_new_pet, container, false);
        init();
        return view;
    }

    private void init() {
        methods = new Methods(getActivity());
        all_new_entry_list =view.findViewById(R.id.all_new_entry_list);
        shimmer_view_new_entry = view.findViewById(R.id.shimmer_view_new_entry);
        new_pet_search = view.findViewById(R.id.new_pet_search);
        search_boxRL = view.findViewById(R.id.search_boxRL);
        search_box_add_new = view.findViewById(R.id.search_box_add_new);
        staff_headline_TV = view.findViewById(R.id.staff_headline_TV);
        back_arrow_IV_new_entry = view.findViewById(R.id.back_arrow_IV_new_entry);
        addNewEntry = view.findViewById(R.id.addNewEntry);
        webview = view.findViewById(R.id.webview);
        new_pet_search.setOnClickListener(this);
        addNewEntry.setOnClickListener(this);
        back_arrow_IV_new_entry.setOnClickListener(this);
        search_box_add_new.addTextChangedListener(this);
        if (methods.isInternetOn()){
            getPetNewList();

        }else {

            methods.DialogInternet();
        }
    }

    private void getPetNewList() {
        shimmer_view_new_entry.startShimmerAnimation();
        String input= Config.token.trim();
        Log.d("lalalall",""+input);
        ApiService<RecentEntrysResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getRecentClientcVisits(input), "GetRecentClinicVisits");
    }
    @Override
    public void onResponse(Response arg0, String key) {
        Log.d("kkakakak",""+key+" response: "+arg0);
        switch (key){
            case "GetRecentClinicVisits":
                try {
                    RecentEntrysResponse getPetListResponse = (RecentEntrysResponse) arg0.body();
                    Log.d("GetRecentClinicVisits", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        new_pet_search.setVisibility(View.VISIBLE);
                        Log.e("lallalal",""+getPetListResponse.getData().getPetClinicVisitList().size());
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        all_new_entry_list.setLayoutManager(linearLayoutManager);
                        newEntrysAdapter  = new NewEntrysAdapter(getActivity(),getPetListResponse.getData().getPetClinicVisitList(),this);
                        all_new_entry_list.setAdapter(newEntrysAdapter);
                        petClinicVisitLists = getPetListResponse.getData().getPetClinicVisitList();
                        newEntrysAdapter.notifyDataSetChanged();
                        shimmer_view_new_entry.setVisibility(View.GONE);
                        shimmer_view_new_entry.stopShimmerAnimation();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
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

        Log.d("error",t.getLocalizedMessage());
    }

    @Override
    public void onViewDetailsClick(int position) {

    }

    @Override
    public void onStausClick(int position, Button button) {

    }

    @Override
    public void onProductClick(int position) {
        Toast.makeText(getContext(), ""+petClinicVisitLists.get(position).getPetName(), Toast.LENGTH_SHORT).show();

        Intent petDetailsIntent = new Intent(getActivity().getApplication(), PetDetailsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id",petClinicVisitLists.get(position).getId().toString());
        data.putString("pet_name",petClinicVisitLists.get(position).getPetName()+"("+petClinicVisitLists.get(position).getPetSex()+")");
        data.putString("pet_parent",petClinicVisitLists.get(position).getPetParentName());
        petDetailsIntent.putExtras(data);
        startActivity(petDetailsIntent);



    }

    @Override
    public void onProductPrescriptionClick(int position) {
        showEditStaffDialog(petClinicVisitLists.get(position).getVeterinarian(),petClinicVisitLists.get(position).getCreatedByUser().getEmail(),petClinicVisitLists.get(position).getPetName(),petClinicVisitLists.get(position).getPetAge(),petClinicVisitLists.get(position).getPetSex(),petClinicVisitLists.get(position).getDateOfOnset(),petClinicVisitLists.get(position).getPetParentName(),petClinicVisitLists.get(position).getTemperature(),petClinicVisitLists.get(position).getDiagnosisProcedure(),petClinicVisitLists.get(position).getTreatmentRemarks(),petClinicVisitLists.get(position).getFollowUpDate());
    }

    private void showEditStaffDialog(final String veterian, final String strEmail, final String strForA, final String strAge, final String strSex, final String strDate, final String strParntNm, final String strTemparature, final String strDiagnosis, final String strRemark, final String strNxtVisit) {

        prescription_dialog = new Dialog(getContext());
        prescription_dialog.setContentView(R.layout.precription_layout);

        parent_name=prescription_dialog.findViewById(R.id.parent_name);
        specilist=prescription_dialog.findViewById(R.id.specilist);
        email=prescription_dialog.findViewById(R.id.email);
        for_a=prescription_dialog.findViewById(R.id.for_a);
        age=prescription_dialog.findViewById(R.id.age);
        sex=prescription_dialog.findViewById(R.id.sex);
        date=prescription_dialog.findViewById(R.id.date);
        prnt_nm=prescription_dialog.findViewById(R.id.prnt_nm);
        temparature=prescription_dialog.findViewById(R.id.temparature);
        symptoms=prescription_dialog.findViewById(R.id.symptoms);
        diagnosis=prescription_dialog.findViewById(R.id.diagnosis);
        remarks=prescription_dialog.findViewById(R.id.remarks);
        nxt_visit=prescription_dialog.findViewById(R.id.nxt_visit);

        crrete_pdf=prescription_dialog.findViewById(R.id.crrete_pdf);
        cancel=prescription_dialog.findViewById(R.id.cancel);


        cancel.setOnClickListener(this);

       /* crrete_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPdf(veterian,  strEmail,  strForA,  strAge, strSex, strDate, strParntNm, strTemparature, strDiagnosis, strRemark, strNxtVisit);
            }
        });*/

        parent_name.setText(veterian);
        //specilist.setText(strSpecialist);
        email.setText(strEmail);
        for_a.setText(strForA);
        age.setText(strAge);
        sex.setText(strSex);
        date.setText(strDate);
        prnt_nm.setText(strParntNm);
        temparature.setText(strTemparature);
        //symptoms.setText(strSymptoms);
        diagnosis.setText(strDiagnosis);
        remarks.setText(strRemark);
        nxt_visit.setText(strNxtVisit);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = prescription_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        prescription_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        prescription_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        prescription_dialog.show();

    }


    @Override
    public void onProductDownloadClick(int position) {
        createPdf(petClinicVisitLists.get(position).getVeterinarian(),petClinicVisitLists.get(position).getCreatedByUser().getEmail(),petClinicVisitLists.get(position).getPetName(),petClinicVisitLists.get(position).getPetAge(),petClinicVisitLists.get(position).getPetSex(),petClinicVisitLists.get(position).getDateOfOnset(),petClinicVisitLists.get(position).getPetParentName(),petClinicVisitLists.get(position).getTemperature(),petClinicVisitLists.get(position).getDiagnosisProcedure(),petClinicVisitLists.get(position).getTreatmentRemarks(),petClinicVisitLists.get(position).getFollowUpDate());
    }

    @Override
    public void onProductEditClick(int position) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_pet_search:
                search_boxRL.setVisibility(View.VISIBLE);
                staff_headline_TV.setVisibility(View.GONE);
                InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(search_box_add_new.getWindowToken(), 0);
                back_arrow_IV_new_entry.setVisibility(View.VISIBLE);
                break;
            case R.id.back_arrow_IV_new_entry:
                clearSearch();
                break;
            case R.id.addNewEntry:
                break;
            case R.id.cancel:
                prescription_dialog.dismiss();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        newEntrysAdapter.getFilter().filter(s.toString());

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void clearSearch() {
        search_box_add_new.getText().clear();
        search_boxRL.setVisibility(View.GONE);
        back_arrow_IV_new_entry.setVisibility(View.GONE);
        staff_headline_TV.setVisibility(View.VISIBLE);
        InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(search_box_add_new.getWindowToken(), 0);
    }

    public void createPdf(String veterian, String strEmail, String strForA, String strAge,String strSex, String strDate,String strParntNm, String strTemparature, String strDiagnosis,String strRemark, String strNxtVisit)
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
                "                        MBBS,MVS \n" +
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
                "                    <b>Date: <?=date('d/m/Y')?></b>\n" +
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
        webview.loadDataWithBaseURL(null,str,"text/html","utf-8",null);
        Context context=getActivity();
        PrintManager printManager=(PrintManager)getActivity().getSystemService(context.PRINT_SERVICE);
        PrintDocumentAdapter adapter=null;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            adapter=webview.createPrintDocumentAdapter();
        }
        String JobName=getString(R.string.app_name) +"Document";
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            PrintJob printJob=printManager.print(JobName,adapter,new PrintAttributes.Builder().build());
        }
    }

}