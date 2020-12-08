package com.cynoteck.petofyvet.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.AddNewPetActivity;
import com.cynoteck.petofyvet.activities.PetDetailsActivity;
import com.cynoteck.petofyvet.adapters.NewEntrysAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addParamRequest.AddPetRequset;
import com.cynoteck.petofyvet.params.checkpetInVetRegister.InPetRegisterRequest;
import com.cynoteck.petofyvet.params.checkpetInVetRegister.InPetregisterParams;
import com.cynoteck.petofyvet.params.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyvet.params.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyvet.params.immunizationRequest.ImmunizationParams;
import com.cynoteck.petofyvet.params.immunizationRequest.ImmunizationRequest;
import com.cynoteck.petofyvet.params.newPetEntryParams.NewPetParams;
import com.cynoteck.petofyvet.params.newPetEntryParams.NewPetRequest;
import com.cynoteck.petofyvet.params.otpRequest.SendOtpParameter;
import com.cynoteck.petofyvet.params.otpRequest.SendOtpRequest;
import com.cynoteck.petofyvet.params.petBreedRequest.BreedParams;
import com.cynoteck.petofyvet.params.petBreedRequest.BreedRequest;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataParams;
import com.cynoteck.petofyvet.params.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyvet.params.updateRequest.updateParamRequest.UpdatePetParam;
import com.cynoteck.petofyvet.params.updateRequest.updateParamRequest.UpdatePetRequest;
import com.cynoteck.petofyvet.response.InPetVeterian.InPetVeterianResponse;
import com.cynoteck.petofyvet.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofyvet.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofyvet.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofyvet.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofyvet.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofyvet.response.getImmunizationReport.PetImmunizationRecordResponse;
import com.cynoteck.petofyvet.response.getPetDetailsResponse.GetPetResponse;
import com.cynoteck.petofyvet.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyvet.response.newPetResponse.NewPetRegisterResponse;
import com.cynoteck.petofyvet.response.otpResponse.OtpResponse;
import com.cynoteck.petofyvet.response.recentEntrys.PetClinicVisitList;
import com.cynoteck.petofyvet.response.recentEntrys.RecentEntrysResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.cynoteck.petofyvet.utils.NewEntryListClickListener;
import com.cynoteck.petofyvet.utils.StaffListClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import retrofit2.Response;

public class AddNewPetFragment extends Fragment implements ApiResponse,View.OnClickListener, StaffListClickListener, NewEntryListClickListener, TextWatcher {
    Methods methods;
    RecyclerView all_new_entry_list;
    ShimmerFrameLayout shimmer_view_new_entry;
    NewEntrysAdapter newEntrysAdapter;
    ImageView new_pet_search,back_arrow_IV_new_entry;
    List<PetClinicVisitList> petClinicVisitLists=null;
    ArrayList<String> petUniueId=null;
    RelativeLayout search_boxRL;
    AutoCompleteTextView search_box_add_new;
    Dialog add_new_entrys_dialog, prescription_dialog, editPetDilog, otpDialog;
    TextView staff_headline_TV,parent_name,specilist,email,for_a,age,sex,date,prnt_nm,temparature,symptoms,diagnosis,
            remarks,nxt_visit,peto_reg_number_dialog,calenderTextView_dialog,
            peto_reg_number_edit_dialog,calenderTextView_edit_dialog,cancelMobileDialog,cancelOtpDialog;
    AppCompatSpinner add_pet_type_edit_dialog,
            add_pet_age_edit_dialog,add_pet_sex_edit_dialog,add_pet_breed_edit_dialog,add_pet_color_edit_dialog,add_pet_size_edit_dialog;
    TextInputLayout mobile_numberTL,otp_TL;
    TextInputEditText  pet_parent_mobile_number,
            pet_parent_otp;
    Button crrete_pdf,cancel,save_changes_dialog,cancel_dialog,save_changes_edit_dialog,
            cancel_edit_dialog,submit_parent_mob_number,submit_parent_otp;
    EditText pet_name_edit_dialog,pet_parent_name_edit_dialog,pet_contact_number_edit_dialog;
    WebView webview;
    LinearLayout addNewEntry;
    String petUniqueId="",getStrSpnerItemPetNmId="",strSpnrBreedId="",strSpnrAgeId="",strSpnrColorId="",
            strSpneSizeId="",strSpnrSexId="",strSpnerItemPetType="",strSpnrBreed="",strSpnrAge="",strSpnrSex="",
            currentDateandTime="",petIdGetForUpdate="",strResponseOtp="",petId="",petParentContactNumber="";
    View view;

    AppCompatSpinner add_pet_type, add_pet_age_dialog ,add_pet_sex_dialog,add_pet_breed_dialog, add_pet_color_dialog,add_pet_size_dialog;
    EditText pet_name_ET,pet_parent_name_ET, pet_contact_number_ET;


    DatePickerDialog picker;
    boolean getRecententrys=false;
    ArrayList<String> petAge;
    ArrayList<String> petSex;
    /*  ArrayList<String> petType;
        ArrayList<String> petBreed;
        ArrayList<String> petColor;
        ArrayList<String> petSize;
    HashMap<String,String> petTypeHashMap=new HashMap<>();
    HashMap<String,String> petBreedHashMap=new HashMap<>();
    HashMap<String,String> petAgeHashMap=new HashMap<>();
    HashMap<String,String> petColorHashMap=new HashMap<>();
    HashMap<String,String> petSizeHashMap=new HashMap<>();*/
    HashMap<String,String> petSexHashMap=new HashMap<>();
    HashMap<String,String> petExistingSearch;

    RecentEntrysResponse getPetListResponse;
    String petNameImmun="",petSeximmun="",petAgeImmun="",petParentImmun="";
    public AddNewPetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        addNewEntry.setEnabled(false);
        back_arrow_IV_new_entry.setOnClickListener(this);
        search_box_add_new.addTextChangedListener(this);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        petSex=new ArrayList<>();
        petSex.add("Pet Sex");
        petSex.add("Male");
        petSex.add("Female");

        petSexHashMap.put("Pet Sex","0");
        petSexHashMap.put("Male","1");
        petSexHashMap.put("Female","2");

        currentDateAndTime();

        if (methods.isInternetOn()){
            getPetNewList();
            getPetList();
        }else {

            methods.DialogInternet();
        }

        search_box_add_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                //... your stuff
                Log.d("akkakaka",""+petUniueId.get(position));
                String value=petExistingSearch.get(search_box_add_new.getText().toString());
                Log.d("kakakka",""+value);
                StringTokenizer st = new StringTokenizer(value, ",");
                String PetUniqueId = st.nextToken();
                String PetName = st.nextToken();
                String PetParentName = st.nextToken();
                String PetSex = st.nextToken();
                String PetAge = st.nextToken();
                String Id = st.nextToken();
                String pet_DOB = st.nextToken();
                String pet_encrypted_id = st.nextToken();
                Log.d("ppppp",""+PetUniqueId+" "+PetName+" "+PetParentName+" "+PetSex+" "+petAge+" "+Id+" "+pet_DOB+" "+pet_encrypted_id);
                Intent petDetailsIntent = new Intent(getActivity().getApplication(), PetDetailsActivity.class);
                Bundle data = new Bundle();
                data.putString("pet_id",Id);
                data.putString("pet_name",PetName);
                data.putString("pet_parent",PetParentName);
                data.putString("pet_sex",PetSex);
                data.putString("pet_age",PetAge);
                data.putString("pet_unique_id",PetUniqueId);
                data.putString("pet_DOB",pet_DOB);
                data.putString("pet_encrypted_id",pet_encrypted_id);
                petDetailsIntent.putExtras(data);
                startActivity(petDetailsIntent);
                clearSearch();

            }
        });
    }

    private void currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy h:mm:ss a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Log.d("currentDateandTime",""+currentDateandTime);
    }

    private void getPetNewList() {
        getRecententrys=true;
        shimmer_view_new_entry.startShimmerAnimation();
        String input= Config.token.trim();
        Log.d("lalalall",""+input);
        ApiService<RecentEntrysResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getRecentClientcVisits(input), "GetRecentClinicVisits");
    }

   /* private void addPetData(AddPetRequset addPetRequset) {
        ApiService<AddPetValueResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addNewPet(Config.token,addPetRequset), "AddPet");
        Log.e("DATALOG","check1=> "+addPetRequset);

    }

    private void getPetlistData(GetPetListRequest getPetListRequest) {
        ApiService<GetPetResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetDetails(Config.token,getPetListRequest), "GetPetDetail");
        Log.e("DATALOG","check1=> "+getPetListRequest);
    }*/

    private void updatePetDetails(UpdatePetRequest addPetRequset) {
        methods.showCustomProgressBarDialog(getActivity());
        ApiService<AddPetValueResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().updatePetDetails(Config.token,addPetRequset), "UpdatePetDetails");
        Log.e("DATALOG","check1=> "+addPetRequset);

    }
    private void chkVetInregister(InPetRegisterRequest inPetRegisterRequest) {
        ApiService<InPetVeterianResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().checkPetInVetRegister(Config.token,inPetRegisterRequest), "CheckPetInVetRegister");
        Log.e("DATALOG","check1=> "+inPetRegisterRequest);
    }

    private void sendotpUsingMobileNumber(SendOtpRequest sendOtpRequest) {
        ApiService<OtpResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().senOtp(Config.token,sendOtpRequest), "SendOtp");
        Log.e("DATALOG","check1=> "+sendOtpRequest);
    }

    private void addNewRegisterPet(NewPetRequest newPetRequest) {
        ApiService<NewPetRegisterResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addPetToRegister(Config.token,newPetRequest), "AddPetToRegister");
        Log.e("DATALOG","check1=> "+newPetRequest);
    }

    private void getPetList() {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(1);
        getPetDataParams.setPageSize(5);
        getPetDataParams.setSearch_Data("0");
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetList");
        Log.e("DATALOG","check1=> "+getPetDataRequest);


    }

    @Override
    public void onResponse(Response arg0, String key) {
        Log.d("kkakakak",""+key+" response: "+arg0);
        switch (key){
            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) arg0.body();
                    Log.d("GetPetList", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        petUniueId=new ArrayList<>();
                        petExistingSearch=new HashMap<>();
                        for(int i=0;i<getPetListResponse.getData().getPetList().size();i++){
                            petUniueId.add(getPetListResponse.getData().getPetList().get(i).getPetUniqueId()+":- "
                                    +getPetListResponse.getData().getPetList().get(i).getPetName()+"("+getPetListResponse.getData().getPetList().get(i).getPetSex()+","+getPetListResponse.getData().getPetList().get(i).getPetParentName()+")");

                            petExistingSearch.put(getPetListResponse.getData().getPetList().get(i).getPetUniqueId()+":- "
                                            +getPetListResponse.getData().getPetList().get(i).getPetName()+"("+getPetListResponse.getData().getPetList().get(i).getPetSex()+","+getPetListResponse.getData().getPetList().get(i).getPetParentName()+")",
                                             getPetListResponse.getData().getPetList().get(i).getPetUniqueId()+","
                                            +getPetListResponse.getData().getPetList().get(i).getPetName()+","
                                            +getPetListResponse.getData().getPetList().get(i).getPetParentName()+","
                                            +getPetListResponse.getData().getPetList().get(i).getPetSex()+","
                                            +getPetListResponse.getData().getPetList().get(i).getPetAge()+","
                                            +getPetListResponse.getData().getPetList().get(i).getId()+","
                                            +getPetListResponse.getData().getPetList().get(i).getDateOfBirth()+","
                                            +getPetListResponse.getData().getPetList().get(i).getEncryptedId());
                        }

                        Log.d("jajajajjaja",""+petUniueId.size()+" \n"+ petUniueId.toString());
                        Log.d("lllllllllll",""+petExistingSearch.size()+" \n"+ petExistingSearch);

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getActivity(),android.R.layout.simple_list_item_1,petUniueId);
                        search_box_add_new.setThreshold(1);//will start working from first character
                        search_box_add_new.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                        search_box_add_new.setTextColor(Color.BLACK);
                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                break;
            case "GetRecentClinicVisits":
                try {
                    getPetListResponse = (RecentEntrysResponse) arg0.body();
                    Log.d("GetRecentClinicVisits", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        new_pet_search.setVisibility(View.VISIBLE);
                        all_new_entry_list.setVisibility(View.VISIBLE);
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
                        getRecententrys=false;
                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                break;
            case "AddPet":
                try {
                    Log.d("AddPet",arg0.body().toString());
                    AddPetValueResponse addPetValueResponse = (AddPetValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(addPetValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        add_new_entrys_dialog.dismiss();
                        Toast.makeText(getActivity(), "Sucessss", Toast.LENGTH_SHORT).show();
                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), addPetValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetDetail":
                try {
                    Log.d("GetPetDetail", arg0.body().toString());
                    GetPetResponse getPetResponse = (GetPetResponse) arg0.body();
                    int responseCode = Integer.parseInt(getPetResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        pet_name_edit_dialog.setText(getPetResponse.getData().getPetParentName());
                        pet_contact_number_edit_dialog.setText(getPetResponse.getData().getContactNumber());
                        pet_parent_name_edit_dialog.setText(getPetResponse.getData().getPetParentName());
                        calenderTextView_edit_dialog.setText(getPetResponse.getData().getDateOfBirth());

                    } else if (responseCode == 614) {
                        Toast.makeText(getActivity(), getPetResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "UpdatePetDetails":
                try {
                    Log.d("UpdatePetDetails",arg0.body().toString());
                    AddPetValueResponse addPetValueResponse = (AddPetValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(addPetValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(getActivity(), "Sucessss", Toast.LENGTH_SHORT).show();
                        methods.customProgressDismiss();
                        editPetDilog.dismiss();
                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), addPetValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "CheckPetInVetRegister":
                try {
                    Log.d("CheckPetInVetRegister",arg0.body().toString());
                    InPetVeterianResponse inPetVeterianResponse = (InPetVeterianResponse) arg0.body();
                    int responseCode = Integer.parseInt(inPetVeterianResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if(!inPetVeterianResponse.getData().getPetId().equals("0"))
                        {
                            petId=inPetVeterianResponse.getData().getPetId();
                            petParentContactNumber=inPetVeterianResponse.getData().getPetParentContactNumber();
                            String actualNumber=petParentContactNumber.replaceAll("-", "");
                            SendOtpRequest sendOtpRequest = new SendOtpRequest();
                            SendOtpParameter data = new SendOtpParameter();
                            data.setPhoneNumber(actualNumber);
                            data.setEmailId("");
                            sendOtpRequest.setData(data);
                            if (methods.isInternetOn()) {
                                sendotpUsingMobileNumber(sendOtpRequest);
                            } else {
                                methods.DialogInternet();
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Invalid pet unique Id", Toast.LENGTH_SHORT).show();
                        }

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), inPetVeterianResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "SendOtp":
                try {
                    Log.d("SendOtp",arg0.body().toString());
                    OtpResponse otpResponse = (OtpResponse) arg0.body();
                    int responseCode = Integer.parseInt(otpResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if(otpResponse.getData().getSuccess().equals("true"))
                        {
                            strResponseOtp=otpResponse.getData().getOtp();
                            otpDialog();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), otpResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "AddPetToRegister":
                try {
                    Log.d("AddPetToRegister",arg0.body().toString());
                    NewPetRegisterResponse newPetRegisterResponse = (NewPetRegisterResponse) arg0.body();
                    int responseCode = Integer.parseInt(newPetRegisterResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        otpDialog.dismiss();
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        String sexName="";
                        if(newPetRegisterResponse.getData().getSexId().equals("2.0"))
                            sexName="Female";
                        else
                            sexName="Male";
                        getPetList();
                        Intent petDetailsIntent = new Intent(getActivity().getApplication(), PetDetailsActivity.class);
                        Bundle data = new Bundle();
                        data.putString("pet_id",petId);
                        data.putString("pet_name",newPetRegisterResponse.getData().getPetName());
                        data.putString("pet_sex",sexName);
                        data.putString("pet_parent",newPetRegisterResponse.getData().getPetParentName());
                        data.putString("pet_age","puppy");
                        data.putString("pet_unique_id",newPetRegisterResponse.getData().getPetUniqueId());
                        data.putString("pet_DOB",newPetRegisterResponse.getData().getDateOfBirth());
                        data.putString("pet_encrypted_id","");
                        petDetailsIntent.putExtras(data);
                        startActivity(petDetailsIntent);
                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), newPetRegisterResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetImmunization":
                try {

                    Log.d("GetImmunization",arg0.body().toString());
                    PetImmunizationRecordResponse immunizationRecordResponse = (PetImmunizationRecordResponse) arg0.body();
                    int responseCode = Integer.parseInt(immunizationRecordResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if (immunizationRecordResponse.getData().getPetImmunizationDetailModels().isEmpty()){
                            methods.customProgressDismiss();
                            Toast.makeText(getContext(), "No Record Found !", Toast.LENGTH_SHORT).show();
                        }else {
                            ArrayList<String> immunizationDate = new ArrayList<>();
                            ArrayList<String> vaccineClass = new ArrayList<>();
                            ArrayList<String> nextDueDate = new ArrayList<>();

                            for (int i = 0; i < immunizationRecordResponse.getData().getPetImmunizationDetailModels().size(); i++) {
                                immunizationDate.add(immunizationRecordResponse.getData().getPetImmunizationDetailModels().get(i).getImmunizationDate().substring(0, immunizationRecordResponse.getData().getPetImmunizationDetailModels().get(i).getImmunizationDate().length() - 11));
                                vaccineClass.add(immunizationRecordResponse.getData().getPetImmunizationDetailModels().get(i).getVaccine());
                                nextDueDate.add(immunizationRecordResponse.getData().getPetImmunizationDetailModels().get(i).getNextDueDate());
                            }
                            JSONArray date = new JSONArray(immunizationDate);
                            JSONArray vaccine = new JSONArray(vaccineClass);
                            JSONArray nextDate = new JSONArray(nextDueDate);
                            Log.e("aaaaaa", vaccineClass.toString());
                            Log.e("aaaaaa", vaccine.toString());
                            methods.customProgressDismiss();
                            String immunizationSet = methods.immunizationPdfGenarator(petNameImmun, petAgeImmun, petSeximmun, petParentImmun, "4564564644465", date, vaccine, nextDate);

                            webview.loadDataWithBaseURL(null, immunizationSet, "text/html", "utf-8", null);
                            new Handler().postDelayed(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void run() {
                                    Context context = getContext();
                                    PrintManager printManager = (PrintManager) getContext().getSystemService(context.PRINT_SERVICE);
                                    PrintDocumentAdapter adapter = null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        adapter = webview.createPrintDocumentAdapter();
                                    }
                                    String JobName = getString(R.string.app_name) + "Document";
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        PrintJob printJob = printManager.print(JobName, adapter, new PrintAttributes.Builder().build());
                                    }
                                }
                            }, 3000);

                        }

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), immunizationRecordResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        addNewEntry.setEnabled(true);

    }


/*
    public void editPrescriptionDialog(String petSex, String petId, String petName, String petParentName,String petAge)
    {
        strSpnrSex=petSex;
        strSpnrAge=petAge;

        editPetDilog = new Dialog(getContext());
        editPetDilog.setContentView(R.layout.edit_pet_layout);
        petIdGetForUpdate=petId;

        add_pet_type_edit_dialog=editPetDilog.findViewById(R.id.add_edit_pet_type);
        add_pet_age_edit_dialog=editPetDilog.findViewById(R.id.add_edit_pet_age_dialog);
        add_pet_sex_edit_dialog=editPetDilog.findViewById(R.id.add_edit_pet_sex_dialog);
        add_pet_breed_edit_dialog=editPetDilog.findViewById(R.id.add_edit_pet_breed_dialog);
        add_pet_color_edit_dialog=editPetDilog.findViewById(R.id.add_edit_pet_color_dialog);
        add_pet_size_edit_dialog=editPetDilog.findViewById(R.id.add_edit_pet_size_dialog);
        pet_name_edit_dialog=editPetDilog.findViewById(R.id.pet_edit_name_ET);
        pet_parent_name_edit_dialog=editPetDilog.findViewById(R.id.pet_edit_parent_name_ET);
        pet_contact_number_edit_dialog=editPetDilog.findViewById(R.id.pet_edit_contact_number);
        peto_reg_number_edit_dialog=editPetDilog.findViewById(R.id.peto_edit_reg_number_dialog);
        calenderTextView_edit_dialog=editPetDilog.findViewById(R.id.calenderTextView_edit_dialog);
        save_changes_edit_dialog=editPetDilog.findViewById(R.id.save_edit_changes_dialog);
        cancel_edit_dialog=editPetDilog.findViewById(R.id.cancel_edit_dialog);

        pet_name_edit_dialog.setText(petName);

        StringTokenizer st = new StringTokenizer(petParentName, "(");
        String[] parts = petParentName.split("\\(");
        String onlyPetParentname = parts[0];
        String onlyPhoneNumber = parts[1];
        pet_parent_name_edit_dialog.setText(onlyPetParentname);
        String number=onlyPhoneNumber.substring(0, onlyPhoneNumber.length()-1).replaceAll("[\\D]", "");
        pet_contact_number_edit_dialog.setText(number);



        calenderTextView_edit_dialog.setOnClickListener(this);
        save_changes_edit_dialog.setOnClickListener(this);
        cancel_edit_dialog.setOnClickListener(this);
        StringTokenizer tokens = new StringTokenizer(petId, ".");
        String first = tokens.nextToken();
        GetPetListParams getPetListParams = new GetPetListParams();
        getPetListParams.setId(first);
        GetPetListRequest getPetListRequest = new GetPetListRequest();
        getPetListRequest.setData(getPetListParams);

        Log.d("kkaakakak",""+getStrSpnerItemPetNmId);

        if(getStrSpnerItemPetNmId.equals("")){
            setSpinnerEditPetSex();
            setPetTypeEditSpinner();
        }




        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = editPetDilog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        editPetDilog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        editPetDilog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        editPetDilog.show();

    }*/


    public void otpDialog()
    {
        otpDialog=new Dialog(getContext());
        otpDialog.setContentView(R.layout.otp_layout);

        otp_TL=otpDialog.findViewById(R.id.otp_TL);
        pet_parent_otp=otpDialog.findViewById(R.id.pet_parent_otp);
        submit_parent_otp=otpDialog.findViewById(R.id.submit_parent_otp);
        cancelOtpDialog=otpDialog.findViewById(R.id.cancelOtpDialog);

        submit_parent_otp.setOnClickListener(this);
        cancelOtpDialog.setOnClickListener(this);



        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = otpDialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        otpDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        otpDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        otpDialog.show();

    }

/*
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


        parent_name.setText(veterian);
        //specilist.setText(strSpecialist);
        email.setText(strEmail);
        for_a.setText("For A: "+strForA);
        age.setText("Age: "+strAge);
        sex.setText("Sex: "+strSex);
        date.setText("date: "+strDate);
        prnt_nm.setText("Pet Parent Name: "+strParntNm);
        temparature.setText("Temparature(F): "+strTemparature+"\u2109");
        //symptoms.setText(strSymptoms);
        diagnosis.setText("Diagnosis: "+strDiagnosis);
        remarks.setText("Treatment Remarks: "+strRemark);
        nxt_visit.setText("Next Visit: "+strNxtVisit);


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
*/


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
        data.putString("pet_id",petClinicVisitLists.get(position).getPetId());
        data.putString("pet_name",petClinicVisitLists.get(position).getPetName());
        data.putString("pet_parent",petClinicVisitLists.get(position).getPetParentName());
        data.putString("pet_sex",petClinicVisitLists.get(position).getPetSex());
        data.putString("pet_age",petClinicVisitLists.get(position).getPetAge());
        data.putString("pet_unique_id",petClinicVisitLists.get(position).getPetUniqueId());
        data.putString("pet_DOB",petClinicVisitLists.get(position).getPetDOB());
        data.putString("pet_encrypted_id",petClinicVisitLists.get(position).getEncryptedPetId());
        petDetailsIntent.putExtras(data);
        startActivity(petDetailsIntent);



    }

    @Override
    public void onProductPrescriptionClick(int position) {
        methods.showCustomProgressBarDialog(getActivity());
        createPdf(petClinicVisitLists.get(position).getVeterinarian(),petClinicVisitLists.get(position).getCreatedByUser().getProviderPhoneNumber(),petClinicVisitLists.get(position).getCreatedByUser().getCustomerEmail(),petClinicVisitLists.get(position).getPetName(),petClinicVisitLists.get(position).getPetAge(),petClinicVisitLists.get(position).getPetSex(),petClinicVisitLists.get(position).getDateOfOnset(),petClinicVisitLists.get(position).getPetParentName(),petClinicVisitLists.get(position).getTemperature(),petClinicVisitLists.get(position).getDiagnosisProcedure(),petClinicVisitLists.get(position).getTreatmentRemarks(),petClinicVisitLists.get(position).getFollowUpDate(),petClinicVisitLists.get(position).getDescription());

//        showEditStaffDialog(petClinicVisitLists.get(position).getVeterinarian(),petClinicVisitLists.get(position).getCreatedByUser().getEmail(),petClinicVisitLists.get(position).getPetName(),petClinicVisitLists.get(position).getPetAge(),petClinicVisitLists.get(position).getPetSex(),petClinicVisitLists.get(position).getDateOfOnset(),petClinicVisitLists.get(position).getPetParentName(),petClinicVisitLists.get(position).getTemperature(),petClinicVisitLists.get(position).getDiagnosisProcedure(),petClinicVisitLists.get(position).getTreatmentRemarks(),petClinicVisitLists.get(position).getFollowUpDate());
    }


    @Override
    public void onProductDownloadClick(int position) {
        methods.showCustomProgressBarDialog(getContext());
//        Toast.makeText(getContext(), "Immization", Toast.LENGTH_SHORT).show();
        petNameImmun = getPetListResponse.getData().getPetClinicVisitList().get(position).getPetName();
        petSeximmun = getPetListResponse.getData().getPetClinicVisitList().get(position).getPetSex();
        petAgeImmun = getPetListResponse.getData().getPetClinicVisitList().get(position).getPetAge();
        petParentImmun = getPetListResponse.getData().getPetClinicVisitList().get(position).getPetParentName();

        ImmunizationParams immunizationParams = new ImmunizationParams();
        immunizationParams.setEncryptedId(getPetListResponse.getData().getPetClinicVisitList().get(position).getEncryptedPetId());
        ImmunizationRequest immunizationRequest = new ImmunizationRequest();
        immunizationRequest.setData(immunizationParams);

        ApiService<JsonObject> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().viewPetVaccination(Config.token,immunizationRequest), "GetImmunization");
        Log.d("GetImmunization",immunizationRequest.toString());


    }

    @Override
    public void onProductEditClick(int position) {
        /*editPrescriptionDialog(petClinicVisitLists.get(position).getPetSex(),
                               petClinicVisitLists.get(position).getPetId(),
                               petClinicVisitLists.get(position).getPetName(),
                               petClinicVisitLists.get(position).getPetParentName(),
                               petClinicVisitLists.get(position).getPetAge());*/
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_pet_search:
                Log.d("hahahhahah",""+petUniueId.contains(search_box_add_new.getText().toString()));
                if(search_box_add_new.getText().toString().isEmpty()){
                    search_boxRL.setVisibility(View.VISIBLE);
                    staff_headline_TV.setVisibility(View.GONE);
                    back_arrow_IV_new_entry.setVisibility(View.VISIBLE);
                }
                else{
                    String petoUniqueIdSplit = search_box_add_new.getText().toString().substring(0,4);
                    Log.d("petoUniqueIdSplit",""+petoUniqueIdSplit);
                    if(petoUniqueIdSplit.equals("PETO"))
                    {
                        if(petUniueId.contains(search_box_add_new.getText().toString())==true)
                        {
                            search_boxRL.setVisibility(View.VISIBLE);
                            staff_headline_TV.setVisibility(View.GONE);
                            InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm1.hideSoftInputFromWindow(search_box_add_new.getWindowToken(), 0);
                            back_arrow_IV_new_entry.setVisibility(View.VISIBLE);
                            String value=petExistingSearch.get(search_box_add_new.getText().toString());
                            Log.d("kakakka",""+value);
                            StringTokenizer st = new StringTokenizer(value, ",");
                            String PetUniqueId = st.nextToken();
                            String PetName = st.nextToken();
                            String PetParentName = st.nextToken();
                            String PetSex = st.nextToken();
                            String petAge = st.nextToken();
                            String Id = st.nextToken();
                            String pet_DOB = st.nextToken();
                            String pet_encrypted_id = st.nextToken();
                            Log.d("ppppp",""+PetUniqueId+" "+PetName+" "+PetParentName+" "+PetSex+" "+petAge+" "+Id+" "+pet_DOB+" "+pet_encrypted_id);
                            Intent petDetailsIntent = new Intent(getActivity().getApplication(), PetDetailsActivity.class);
                            Bundle data = new Bundle();
                            data.putString("pet_id",Id);
                            data.putString("pet_name",PetName);
                            data.putString("pet_parent",PetParentName);
                            data.putString("pet_sex",PetSex);
                            data.putString("pet_age",petAge);
                            data.putString("pet_unique_id",PetUniqueId);
                            data.putString("pet_DOB",pet_DOB);
                            data.putString("pet_encrypted_id",pet_encrypted_id);
                            petDetailsIntent.putExtras(data);
                            startActivity(petDetailsIntent);
                        }
                        else
                        {
                            Log.d("Add Anotheer Veterian","vet");
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Are you sure?");
                            alertDialog.setMessage("This pet is not registered with you. Do you want to add ?");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            InPetRegisterRequest inPetRegisterRequest = new InPetRegisterRequest();
                                            InPetregisterParams inPetregisterParams = new InPetregisterParams();
                                            Log.d("kkakakka",""+search_box_add_new.getText().toString());
                                            inPetregisterParams.setUniqueId(search_box_add_new.getText().toString());
                                            inPetRegisterRequest.setData(inPetregisterParams);
                                            if (methods.isInternetOn()) {
                                                chkVetInregister(inPetRegisterRequest);
                                                clearSearch();
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
                        }
                    }
                    else{
                        search_box_add_new.requestFocus();
                        search_boxRL.setVisibility(View.VISIBLE);
                        staff_headline_TV.setVisibility(View.GONE);
                        InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm1.showSoftInput(search_box_add_new, InputMethodManager.SHOW_IMPLICIT);
                        back_arrow_IV_new_entry.setVisibility(View.VISIBLE);
                    }

                }
                break;
            case R.id.back_arrow_IV_new_entry:
                clearSearch();
                break;

            case R.id.addNewEntry:
                Intent addNewPetIntent = new Intent(getContext(), AddNewPetActivity.class);
                addNewPetIntent.putExtra("appointment","");
                startActivity(addNewPetIntent);
                break;
            case R.id.cancel:
                prescription_dialog.dismiss();
                break;
            case R.id.calenderTextView_edit_dialog:
                final Calendar cldrEdit = Calendar.getInstance();
                int dayEdit = cldrEdit.get(Calendar.DAY_OF_MONTH);
                int monthEdit = cldrEdit.get(Calendar.MONTH);
                int yearEdit = cldrEdit.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_edit_dialog.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, yearEdit, monthEdit, dayEdit);
                picker.show();
                break;

            case R.id.save_edit_changes_dialog:
                String strPetEditName= pet_name_edit_dialog.getText().toString().trim();
                String strPetEditParentName = pet_parent_name_edit_dialog.getText().toString().trim();
                String strPetEditContactNumber = pet_contact_number_edit_dialog.getText().toString().trim();
                String strPetEditBirthDay = calenderTextView_edit_dialog.getText().toString().trim();

                if(strPetEditName.isEmpty())
                {
                    pet_name_edit_dialog.setError("Enter Pet Name");
                    pet_parent_name_edit_dialog.setError(null);
                    pet_contact_number_edit_dialog.setError(null);
                    calenderTextView_edit_dialog.setError(null);
                }
                else if(strPetEditParentName.isEmpty())
                {
                    pet_name_edit_dialog.setError(null);
                    pet_parent_name_edit_dialog.setError("Enter Parent Name");
                    pet_contact_number_edit_dialog.setError(null);
                    calenderTextView_edit_dialog.setError(null);
                }
                else if(strPetEditContactNumber.isEmpty())
                {
                    pet_name_edit_dialog.setError(null);
                    pet_parent_name_edit_dialog.setError(null);
                    pet_contact_number_edit_dialog.setError("Enter Contact Number");
                    calenderTextView_edit_dialog.setError(null);
                }
                else if(strPetEditBirthDay.isEmpty())
                {
                    pet_name_edit_dialog.setError(null);
                    pet_parent_name_edit_dialog.setError(null);
                    pet_contact_number_edit_dialog.setError(null);
                    calenderTextView_edit_dialog.setError("Pet YOB");
                }
                //pet size and color.
                else if(strSpnerItemPetType.isEmpty()||(strSpnerItemPetType.equals("Select Pet Type")))
                {
                    Toast.makeText(getActivity(), "Select Type!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrBreed.isEmpty()||(strSpnrBreed.equals("Pet Breed")))
                {
                    Toast.makeText(getActivity(), "Select Breed!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrAge.isEmpty()||(strSpnrAge.equals("Select Pet Age")))
                {
                    Toast.makeText(getActivity(), "Select Pet Age!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrSex.isEmpty()||(strSpnrSex.equals("Pet Sex")))
                {
                    Toast.makeText(getActivity(), "Select Pet Sex!!", Toast.LENGTH_SHORT).show();
                }
                else {
                    pet_name_edit_dialog.setError(null);
                    pet_parent_name_edit_dialog.setError(null);
                    pet_contact_number_edit_dialog.setError(null);
                    calenderTextView_edit_dialog.setError(null);

                    UpdatePetRequest updatePetRequest = new UpdatePetRequest();
                    UpdatePetParam data = new UpdatePetParam();
                    data.setId(petIdGetForUpdate.substring(0,petIdGetForUpdate.length()-2));
                    data.setPetCategoryId(getStrSpnerItemPetNmId);
                    data.setPetSexId(strSpnrSexId);
                    data.setPetAgeId(strSpnrAgeId);
                    data.setPetSizeId(strSpneSizeId);
                    data.setPetColorId(strSpnrColorId);
                    data.setPetBreedId(strSpnrBreedId);
                    data.setPetName(strPetEditName);
                    data.setPetParentName(strPetEditParentName);
                    data.setContactNumber(strPetEditContactNumber);
                    data.setAddress("");
                    data.setDescription("");
                    data.setCreateDate(currentDateandTime);
                    data.setDateOfBirth(strPetEditBirthDay);

                    data.setPetProfileImageUrl("");
                    data.setFirstServiceImageUrl("");
                    data.setSecondServiceImageUrl("");
                    data.setThirdServiceImageUrl("");
                    data.setFourthServiceImageUrl("");
                    data.setFifthServiceImageUrl("");
                    updatePetRequest.setAddPetParams(data);
                    if (methods.isInternetOn()) {
                        updatePetDetails(updatePetRequest);
                    } else {
                        methods.DialogInternet();
                    }
                }
                break;
            case R.id.cancel_edit_dialog:
                editPetDilog.dismiss();
                break;

            case R.id.submit_parent_mob_number:
                String StrmobileNumber=pet_parent_mobile_number.getText().toString();
                if(StrmobileNumber.isEmpty()){
                    pet_parent_mobile_number.setError("Enter A valid Mobile Number");
                }
                else{
                }
                break;
            case R.id.submit_parent_otp:
                String otp=pet_parent_otp.getText().toString();
                if(otp.isEmpty())
                {
                    pet_parent_otp.setError("Enter Correct OTP");
                }
                else if(!otp.equals(strResponseOtp))
                {
                    pet_parent_otp.setError("Enter Wrong OTP");
                }
                else
                {
                    pet_parent_otp.setError(null);
                    NewPetRequest newPetRequest = new NewPetRequest();
                    NewPetParams data = new NewPetParams();
                    data.setId(petId);
                    newPetRequest.setData(data);
                    if (methods.isInternetOn()) {
                        addNewRegisterPet(newPetRequest);
                    } else {
                        methods.DialogInternet();
                    }
                }
                break;

            case R.id.cancelOtpDialog:
                otpDialog.dismiss();
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

    public void createPdf(String veterian,String phoneNumber, String strEmail, String strForA, String strAge,String strSex, String strDate,String strParntNm, String strTemparature, String strDiagnosis,String strRemark, String strNxtVisit, String description)
    {
        String str=methods.pdfGenarator(strForA,strAge,strSex,strParntNm,strTemparature,
                description,strDiagnosis,strRemark,strNxtVisit,"");
        webview.loadDataWithBaseURL(null,str,"text/html","utf-8",null);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                methods.customProgressDismiss();
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
        }, 3000);

    }

    @Override
    public void onResume() {
        super.onResume();
        getPetList();
        if(getRecententrys==false)
        {
            new_pet_search.setVisibility(View.GONE);
            all_new_entry_list.setVisibility(View.GONE);
            shimmer_view_new_entry.setVisibility(View.VISIBLE);
            shimmer_view_new_entry.startShimmerAnimation();
            getPetNewList();
        }

    }
}