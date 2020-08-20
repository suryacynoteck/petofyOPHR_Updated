package com.cynoteck.petofyvet.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.PetDetailsActivity;
import com.cynoteck.petofyvet.adapters.NewEntrysAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addParamRequest.AddPetParams;
import com.cynoteck.petofyvet.params.addParamRequest.AddPetRequset;
import com.cynoteck.petofyvet.params.checkpetInVetRegister.InPetRegisterRequest;
import com.cynoteck.petofyvet.params.checkpetInVetRegister.InPetregisterParams;
import com.cynoteck.petofyvet.params.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyvet.params.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyvet.params.newPetEntryParams.NewPetParams;
import com.cynoteck.petofyvet.params.newPetEntryParams.NewPetRequest;
import com.cynoteck.petofyvet.params.otpRequest.SendOtpParameter;
import com.cynoteck.petofyvet.params.otpRequest.SendOtpRequest;
import com.cynoteck.petofyvet.params.petBreedRequest.BreedParams;
import com.cynoteck.petofyvet.params.petBreedRequest.BreedRequest;
import com.cynoteck.petofyvet.params.updateRequest.updateParamRequest.UpdatePetParam;
import com.cynoteck.petofyvet.params.updateRequest.updateParamRequest.UpdatePetRequest;
import com.cynoteck.petofyvet.response.InPetVeterian.InPetVeterianResponse;
import com.cynoteck.petofyvet.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofyvet.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofyvet.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofyvet.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofyvet.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofyvet.response.addPet.uniqueIdResponse.UniqueResponse;
import com.cynoteck.petofyvet.response.getPetDetailsResponse.GetPetResponse;
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
    EditText search_box_add_new;
    Dialog add_new_entrys_dialog, prescription_dialog, editPetDilog, addNewPetMobileNumber, otpDialog;
    TextView staff_headline_TV,parent_name,specilist,email,for_a,age,sex,date,prnt_nm,temparature,symptoms,diagnosis,
            remarks,nxt_visit,peto_reg_number_dialog,calenderTextView_dialog,
            peto_reg_number_edit_dialog,calenderTextView_edit_dialog,cancelMobileDialog,cancelOtpDialog;
    AppCompatSpinner add_pet_type_edit_dialog,
            add_pet_age_edit_dialog,add_pet_sex_edit_dialog,add_pet_breed_edit_dialog,add_pet_color_edit_dialog,add_pet_size_edit_dialog;
    TextInputLayout pet_name_layout_dialog,pet_parent_name_layout_dialog,pet_contact_number_layout_dialog,pet_name_layout_edit_dialog,
            pet_parent_name_layout_edit_dialog,pet_contact_number_edit_layout_dialog,mobile_numberTL,otp_TL;
    TextInputEditText pet_name_edit_dialog, pet_parent_mobile_number,
            pet_parent_name_edit_dialog,pet_contact_number_edit_dialog,pet_parent_otp;
    Button crrete_pdf,cancel,save_changes_dialog,cancel_dialog,save_changes_edit_dialog,
            cancel_edit_dialog,submit_parent_mob_number,submit_parent_otp;
    WebView webview;
    LinearLayout addNewEntry;
    String petUniqueId="",getStrSpnerItemPetNmId="",strSpnrBreedId="",strSpnrAgeId="",strSpnrColorId="",
            strSpneSizeId="",strSpnrSexId="",strSpnerItemPetType="",strSpnrBreed="",strSpnrAge="",strSpnrSex="",
            currentDateandTime="",petIdGetForUpdate="",strResponseOtp="",petId="";
    View view;

    AppCompatSpinner add_pet_type, add_pet_age_dialog ,add_pet_sex_dialog,add_pet_breed_dialog, add_pet_color_dialog,add_pet_size_dialog;
    EditText pet_name_ET,pet_parent_name_ET, pet_contact_number_ET;


    DatePickerDialog picker;
    ArrayList<String> petType;
    ArrayList<String> petBreed;
    ArrayList<String> petAge;
    ArrayList<String> petColor;
    ArrayList<String> petSize;
    ArrayList<String> petSex;

    HashMap<String,String> petTypeHashMap=new HashMap<>();
    HashMap<String,String> petBreedHashMap=new HashMap<>();
    HashMap<String,String> petAgeHashMap=new HashMap<>();
    HashMap<String,String> petColorHashMap=new HashMap<>();
    HashMap<String,String> petSizeHashMap=new HashMap<>();
    HashMap<String,String> petSexHashMap=new HashMap<>();

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
            petType();
            genaretePetUniqueKey();
            getPetBreed();
            getPetAge();
            getPetColor();
            getPetSize();

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    if(petClinicVisitLists==null)
                        getPetNewList();
                }
            }, 10000);

        }else {

            methods.DialogInternet();
        }
    }

    private void currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy h:mm:ss a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Log.d("currentDateandTime",""+currentDateandTime);
    }

    private void getPetNewList() {
        shimmer_view_new_entry.startShimmerAnimation();
        String input= Config.token.trim();
        Log.d("lalalall",""+input);
        ApiService<RecentEntrysResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getRecentClientcVisits(input), "GetRecentClinicVisits");
    }

    private void petType() {
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(Config.token), "GetPetTypes");
    }

    private void genaretePetUniqueKey() {
        ApiService<UniqueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGeneratePetUniqueId(Config.token), "GeneratePetUniqueId");
    }

    private void getPetBreed() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("true");
        breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<BreedCatRespose> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetBreedApi(Config.token,breedParams), "GetPetBreed");
    }

    private void getPetAge() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("true");
        breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetAgeValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetAgeApi(Config.token,breedParams), "GetPetAge");
    }

    private void getPetColor() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("true");
        breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetColorValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetColorApi(Config.token,breedParams), "GetPetColor");
    }

    private void getPetSize() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("true");
        breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetSizeValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetSizeApi(Config.token,breedParams), "GetPetSize");
    }

    private void addPetData(AddPetRequset addPetRequset) {
        ApiService<AddPetValueResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addNewPet(Config.token,addPetRequset), "AddPet");
        Log.e("DATALOG","check1=> "+addPetRequset);

    }

    private void getPetlistData(GetPetListRequest getPetListRequest) {
        ApiService<GetPetResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetDetails(Config.token,getPetListRequest), "GetPetDetail");
        Log.e("DATALOG","check1=> "+getPetListRequest);
    }

    private void updatePetDetails(UpdatePetRequest addPetRequset) {
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
                        petUniueId=new ArrayList<>();
                        for(int i=0;i<petClinicVisitLists.size();i++){
                            petUniueId.add(petClinicVisitLists.get(i).getPetUniqueId());
                        }
                    }

                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                break;

            case "GetPetTypes":
                try {
                    Log.d("GetPetTypes",arg0.body().toString());
                    PetTypeResponse petTypeResponse = (PetTypeResponse) arg0.body();
                    int responseCode = Integer.parseInt(petTypeResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petType=new ArrayList<>();
                        petType.add("Select Pet Type");
                        Log.d("lalal",""+petTypeResponse.getData().size());
                        for(int i=0; i<petTypeResponse.getData().size(); i++){
                            Log.d("petttt",""+petTypeResponse.getData().get(i).getPetType1());
                            petType.add(petTypeResponse.getData().get(i).getPetType1());
                            petTypeHashMap.put(petTypeResponse.getData().get(i).getPetType1(),petTypeResponse.getData().get(i).getId());
                        }


                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), petTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

            case "GeneratePetUniqueId":
                try {
                    Log.d("GeneratePetUniqueId",arg0.body().toString());
                    UniqueResponse uniqueResponse = (UniqueResponse) arg0.body();
                    int responseCode = Integer.parseInt(uniqueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petUniqueId=uniqueResponse.getData().getPetUniqueId();
                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), uniqueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetBreed":
                try {
                    Log.d("GetPetBreed",arg0.body().toString());
                    BreedCatRespose breedCatRespose = (BreedCatRespose) arg0.body();
                    int responseCode = Integer.parseInt(breedCatRespose.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petBreed=new ArrayList<>();
                        petBreed.add("Pet Breed");
                        Log.d("lalal",""+breedCatRespose.getData().size());
                        for(int i=0; i<breedCatRespose.getData().size(); i++){
                            Log.d("petttt",""+breedCatRespose.getData().get(i).getBreed());
                            petBreed.add(breedCatRespose.getData().get(i).getBreed());
                            petBreedHashMap.put(breedCatRespose.getData().get(i).getBreed(),breedCatRespose.getData().get(i).getId());
                        }

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), breedCatRespose.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetAge":
                try {
                    Log.d("GetPetAge",arg0.body().toString());
                    PetAgeValueResponse petAgeValueResponse = (PetAgeValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(petAgeValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petAge=new ArrayList<>();
                        petAge.add("Select Pet Age");
                        Log.d("lalal",""+petAgeValueResponse.getData().size());
                        for(int i=0; i<petAgeValueResponse.getData().size(); i++){
                            Log.d("petttt",""+petAgeValueResponse.getData().get(i).getAge());
                            petAge.add(petAgeValueResponse.getData().get(i).getAge());
                            petAgeHashMap.put(petAgeValueResponse.getData().get(i).getAge(),petAgeValueResponse.getData().get(i).getId());
                        }

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), petAgeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetColor":
                try {
                    Log.d("GetPetColor",arg0.body().toString());
                    PetColorValueResponse petColorValueResponse = (PetColorValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(petColorValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petColor=new ArrayList<>();
                        petColor.add("Pet Color");
                        Log.d("lalal",""+petColorValueResponse.getData().size());
                        for(int i=0; i<petColorValueResponse.getData().size(); i++){
                            Log.d("petttt",""+petColorValueResponse.getData().get(i).getColor());
                            petColor.add(petColorValueResponse.getData().get(i).getColor());
                            petColorHashMap.put(petColorValueResponse.getData().get(i).getColor(),petColorValueResponse.getData().get(i).getId());
                        }
                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), petColorValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetSize":
                try {
                    Log.d("GetPetSize",arg0.body().toString());
                    PetSizeValueResponse petSizeValueResponse = (PetSizeValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(petSizeValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petSize=new ArrayList<>();
                        petSize.add("Pet Size");
                        Log.d("lalal",""+petSizeValueResponse.getData().size());
                        for(int i=0; i<petSizeValueResponse.getData().size(); i++){
                            Log.d("petttt",""+petSizeValueResponse.getData().get(i).getSize());
                            petSize.add(petSizeValueResponse.getData().get(i).getSize());
                            petSizeHashMap.put(petSizeValueResponse.getData().get(i).getSize(),petSizeValueResponse.getData().get(i).getId());
                        }

                    }else if (responseCode==614){
                        Toast.makeText(getActivity(), petSizeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity(), "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        pet_parent_name_edit_dialog.setText(getPetResponse.getData().getPetParentName());
                        pet_contact_number_edit_dialog.setText(getPetResponse.getData().getContactNumber());
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
                            verifyDialog();
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
                            addNewPetMobileNumber.dismiss();
                            strResponseOtp=otpResponse.getData().getOtp();
                            otpDialog();
                        }
                        else
                        {
                            addNewPetMobileNumber.dismiss();
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
                    Log.d("SendOtp",arg0.body().toString());
                    NewPetRegisterResponse newPetRegisterResponse = (NewPetRegisterResponse) arg0.body();
                    int responseCode = Integer.parseInt(newPetRegisterResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        String sexName="";
                        if(newPetRegisterResponse.getData().getSexId().equals("2.0"))
                            sexName="Female";
                        else
                            sexName="Male";
                        Intent petDetailsIntent = new Intent(getActivity().getApplication(), PetDetailsActivity.class);
                        Bundle data = new Bundle();
                        data.putString("pet_id",petId);
                        data.putString("pet_name",newPetRegisterResponse.getData().getPetName()+"("+sexName+")");
                        data.putString("pet_parent",newPetRegisterResponse.getData().getPetParentName());
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
        }

    }


   ////////////////////////////////////ALEART DIALOG////////////////////////////////////////////////////////


    public void addNewEntrysPet(){
        add_new_entrys_dialog = new Dialog(getContext());
        add_new_entrys_dialog.setContentView(R.layout.add_new_entrys);

        peto_reg_number_dialog=add_new_entrys_dialog.findViewById(R.id.peto_reg_number_dialog);
        calenderTextView_dialog=add_new_entrys_dialog.findViewById(R.id.calenderTextView_dialog);
        add_pet_type=add_new_entrys_dialog.findViewById(R.id.add_pet_type);
        add_pet_age_dialog=add_new_entrys_dialog.findViewById(R.id.add_pet_age_dialog);
        add_pet_sex_dialog=add_new_entrys_dialog.findViewById(R.id.add_pet_sex_dialog);
        add_pet_breed_dialog=add_new_entrys_dialog.findViewById(R.id.add_pet_breed_dialog);
        add_pet_color_dialog=add_new_entrys_dialog.findViewById(R.id.add_pet_color_dialog);
        add_pet_size_dialog=add_new_entrys_dialog.findViewById(R.id.add_pet_size_dialog);
        pet_name_ET=add_new_entrys_dialog.findViewById(R.id.pet_name_ET);
        pet_parent_name_ET=add_new_entrys_dialog.findViewById(R.id.pet_parent_name_ET);
        pet_contact_number_ET=add_new_entrys_dialog.findViewById(R.id.pet_contact_number);
        save_changes_dialog=add_new_entrys_dialog.findViewById(R.id.save_changes_dialog);
        cancel_dialog=add_new_entrys_dialog.findViewById(R.id.cancel_dialog);

        peto_reg_number_dialog.setText(petUniqueId);

        save_changes_dialog.setOnClickListener(this);
        cancel_dialog.setOnClickListener(this);
        calenderTextView_dialog.setOnClickListener(this);

        setSpinnerPetSex();
        setPetTypeSpinner();
        setPetBreeSpinner();
        setPetAgeSpinner();
        setPetColorSpinner();
        setPetSizeSpinner();



        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = add_new_entrys_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        add_new_entrys_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        add_new_entrys_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        add_new_entrys_dialog.show();
    }

    public void editPrescriptionDialog(String petId)
    {
        editPetDilog = new Dialog(getContext());
        editPetDilog.setContentView(R.layout.edit_pet_layout);
        petIdGetForUpdate=petId;

        add_pet_type_edit_dialog=editPetDilog.findViewById(R.id.add_pet_type_edit_dialog);
        add_pet_age_edit_dialog=editPetDilog.findViewById(R.id.add_pet_age_edit_dialog);
        add_pet_sex_edit_dialog=editPetDilog.findViewById(R.id.add_pet_sex_edit_dialog);
        add_pet_breed_edit_dialog=editPetDilog.findViewById(R.id.add_pet_breed_edit_dialog);
        add_pet_color_edit_dialog=editPetDilog.findViewById(R.id.add_pet_color_edit_dialog);
        add_pet_size_edit_dialog=editPetDilog.findViewById(R.id.add_pet_size_edit_dialog);
        pet_name_layout_edit_dialog=editPetDilog.findViewById(R.id.pet_name_layout_edit_dialog);
        pet_parent_name_layout_edit_dialog=editPetDilog.findViewById(R.id.pet_parent_name_layout_edit_dialog);
        pet_contact_number_edit_layout_dialog=editPetDilog.findViewById(R.id.pet_contact_number_edit_layout_dialog);
        pet_name_edit_dialog=editPetDilog.findViewById(R.id.pet_name_edit_dialog);
        pet_parent_name_edit_dialog=editPetDilog.findViewById(R.id.pet_parent_name_edit_dialog);
        pet_contact_number_edit_dialog=editPetDilog.findViewById(R.id.pet_contact_number_edit_dialog);
        peto_reg_number_edit_dialog=editPetDilog.findViewById(R.id.peto_reg_number_edit_dialog);
        calenderTextView_edit_dialog=editPetDilog.findViewById(R.id.calenderTextView_edit_dialog);
        save_changes_edit_dialog=editPetDilog.findViewById(R.id.save_changes_edit_dialog);
        cancel_edit_dialog=editPetDilog.findViewById(R.id.cancel_edit_dialog);

        calenderTextView_edit_dialog.setOnClickListener(this);
        save_changes_edit_dialog.setOnClickListener(this);
        cancel_edit_dialog.setOnClickListener(this);
        StringTokenizer tokens = new StringTokenizer(petId, ".");
        String first = tokens.nextToken();
        GetPetListParams getPetListParams = new GetPetListParams();
        getPetListParams.setId(first);
        GetPetListRequest getPetListRequest = new GetPetListRequest();
        getPetListRequest.setData(getPetListParams);
        if(methods.isInternetOn())
        {
            getPetlistData(getPetListRequest);
        }
        else
        {
            methods.DialogInternet();
        }

        setSpinnerEditPetSex();
        setPetTypeEditSpinner();
        setPetBreeEditSpinner();
        setPetAgeEditSpinner();
        setPetColorEditSpinner();
        setPetSizeEditSpinner();



        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = editPetDilog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        editPetDilog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        editPetDilog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        editPetDilog.show();

    }

    private void verifyDialog() {

        addNewPetMobileNumber=new Dialog(getContext());
        addNewPetMobileNumber.setContentView(R.layout.add_new_pet_mobile_verification_dialog);

        mobile_numberTL=addNewPetMobileNumber.findViewById(R.id.mobile_numberTL);
        pet_parent_mobile_number=addNewPetMobileNumber.findViewById(R.id.pet_parent_mobile_number);
        submit_parent_mob_number=addNewPetMobileNumber.findViewById(R.id.submit_parent_mob_number);
        cancelMobileDialog=addNewPetMobileNumber.findViewById(R.id.cancelMobileDialog);

        submit_parent_mob_number.setOnClickListener(this);
        cancelMobileDialog.setOnClickListener(this);



        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = addNewPetMobileNumber.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        addNewPetMobileNumber.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        addNewPetMobileNumber.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        addNewPetMobileNumber.show();
    }

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


    //////////////////////////////////////////Set Spinner/////////////////////////////////////////////

    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type.setAdapter(aa);
        add_pet_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnerItemPetType=item;
                getStrSpnerItemPetNmId=petTypeHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetTypeEditSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type_edit_dialog.setAdapter(aa);
        add_pet_type_edit_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnerItemPetType=item;
                getStrSpnerItemPetNmId=petTypeHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetBreeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petBreed);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_breed_dialog.setAdapter(aa);
        add_pet_breed_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnrBreed=item;
                strSpnrBreedId=petBreedHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetBreeEditSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petBreed);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_breed_edit_dialog.setAdapter(aa);
        add_pet_breed_edit_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnrBreed=item;
                strSpnrBreedId=petBreedHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetAgeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petAge);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_age_dialog.setAdapter(aa);
        add_pet_age_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnrAge=item;
                strSpnrAgeId=petAgeHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetAgeEditSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petAge);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_age_edit_dialog.setAdapter(aa);
        add_pet_age_edit_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnrAge=item;
                strSpnrAgeId=petAgeHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetColorSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petColor);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_color_dialog.setAdapter(aa);
        add_pet_color_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnrColorId=petColorHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetColorEditSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petColor);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_color_edit_dialog.setAdapter(aa);
        add_pet_color_edit_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnrColorId=petColorHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetSizeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petSize);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_size_dialog.setAdapter(aa);
        add_pet_size_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpneSizeId=petSizeHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetSizeEditSpinner() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petSize);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_size_edit_dialog.setAdapter(aa);
        add_pet_size_edit_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpneSizeId=petSizeHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerPetSex() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petSex);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_sex_dialog.setAdapter(aa);
        add_pet_sex_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnrSex=item;
                strSpnrSexId=petSexHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerEditPetSex() {
        ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,petSex);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_sex_edit_dialog.setAdapter(aa);
        add_pet_sex_edit_dialog.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType",""+item);
                strSpnrSex=item;
                strSpnrSexId=petSexHashMap.get(item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////

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


    @Override
    public void onProductDownloadClick(int position) {
        createPdf(petClinicVisitLists.get(position).getVeterinarian(),petClinicVisitLists.get(position).getCreatedByUser().getEmail(),petClinicVisitLists.get(position).getPetName(),petClinicVisitLists.get(position).getPetAge(),petClinicVisitLists.get(position).getPetSex(),petClinicVisitLists.get(position).getDateOfOnset(),petClinicVisitLists.get(position).getPetParentName(),petClinicVisitLists.get(position).getTemperature(),petClinicVisitLists.get(position).getDiagnosisProcedure(),petClinicVisitLists.get(position).getTreatmentRemarks(),petClinicVisitLists.get(position).getFollowUpDate());
    }

    @Override
    public void onProductEditClick(int position) {
        editPrescriptionDialog(petClinicVisitLists.get(position).getId());
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
                                }
                                else
                                {
                                    InPetRegisterRequest inPetRegisterRequest = new InPetRegisterRequest();
                                    InPetregisterParams inPetregisterParams = new InPetregisterParams();
                                    inPetregisterParams.setUniqueId(search_box_add_new.getText().toString());
                                    inPetRegisterRequest.setData(inPetregisterParams);
                                    if (methods.isInternetOn()) {
                                        chkVetInregister(inPetRegisterRequest);
                                    } else {
                                        methods.DialogInternet();
                                    }
                                    Log.d("Add Anotheer Veterian","vet");
                                }
                            }
                            else{
                                search_boxRL.setVisibility(View.VISIBLE);
                                staff_headline_TV.setVisibility(View.GONE);
                                InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm1.hideSoftInputFromWindow(search_box_add_new.getWindowToken(), 0);
                                back_arrow_IV_new_entry.setVisibility(View.VISIBLE);
                            }

                        }
                break;
            case R.id.back_arrow_IV_new_entry:
                clearSearch();
                break;
            case R.id.calenderTextView_dialog:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_dialog.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
                break;
            case R.id.addNewEntry:
                addNewEntrysPet();
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
            case R.id.cancel_dialog:
                add_new_entrys_dialog.dismiss();
            case R.id.save_changes_dialog:
                String strPetName= pet_name_ET.getText().toString().trim();
                String strPetParentName = pet_parent_name_ET.getText().toString().trim();
                String strPetContactNumber = pet_contact_number_ET.getText().toString().trim();
                String strPetBirthDay = calenderTextView_dialog.getText().toString().trim();

                if(strPetName.isEmpty())
                {
                    pet_name_ET.setError("Enter Pet Name");
                    pet_parent_name_ET.setError(null);
                    pet_contact_number_ET.setError(null);
                    calenderTextView_dialog.setError(null);
                }
                else if(strPetParentName.isEmpty())
                {
                    pet_name_ET.setError(null);
                    pet_parent_name_ET.setError("Enter Parent Name");
                    pet_contact_number_ET.setError(null);
                    calenderTextView_dialog.setError(null);
                }
                else if(strPetContactNumber.isEmpty())
                {
                    pet_name_ET.setError(null);
                    pet_parent_name_ET.setError(null);
                    pet_contact_number_ET.setError("Enter Contact Number");
                    calenderTextView_dialog.setError(null);
                }
                else if(strPetBirthDay.isEmpty())
                {
                    pet_name_ET.setError(null);
                    pet_parent_name_ET.setError(null);
                    pet_contact_number_ET.setError(null);
                    calenderTextView_dialog.setError("Pet YOB");
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
                    pet_name_ET.setError(null);
                    pet_parent_name_ET.setError(null);
                    pet_contact_number_ET.setError(null);
                    calenderTextView_dialog.setError(null);
                    Log.d("hahahah", "" + getStrSpnerItemPetNmId + " " + strSpnrSexId + " " + strSpnrAgeId + " " + strSpneSizeId +
                            " " + strSpnrColorId + " " + strSpnrBreedId + " " + strPetName + " " + strPetBirthDay + " "+ currentDateandTime);
                    AddPetRequset addPetRequset = new AddPetRequset();
                    AddPetParams data = new AddPetParams();
                    data.setPetCategoryId(getStrSpnerItemPetNmId);
                    data.setPetSexId(strSpnrSexId);
                    data.setPetAgeId(strSpnrAgeId);
                    data.setPetSizeId(strSpneSizeId);
                    data.setPetColorId(strSpnrColorId);
                    data.setPetBreedId(strSpnrBreedId);
                    data.setPetName(strPetName);
                    data.setPetParentName(strPetParentName);
                    data.setContactNumber(strPetContactNumber);
                    data.setDateOfBirth(strPetBirthDay);

                    data.setPetProfileImageUrl("");
                    data.setFirstServiceImageUrl("");
                    data.setSecondServiceImageUrl("");
                    data.setThirdServiceImageUrl("");
                    data.setFourthServiceImageUrl("");
                    data.setFifthServiceImageUrl("");
                    addPetRequset.setAddPetParams(data);
                    if (methods.isInternetOn()) {
                        addPetData(addPetRequset);
                    } else {
                        methods.DialogInternet();
                    }
                }
                break;
            case R.id.save_changes_edit_dialog:
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
                    data.setId(petIdGetForUpdate);
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
                    pet_parent_mobile_number.setError(null);
                    SendOtpRequest sendOtpRequest = new SendOtpRequest();
                    SendOtpParameter data = new SendOtpParameter();
                    data.setPhoneNumber(StrmobileNumber);
                    data.setEmailId("");
                    sendOtpRequest.setData(data);
                    if (methods.isInternetOn()) {
                        sendotpUsingMobileNumber(sendOtpRequest);
                    } else {
                        methods.DialogInternet();
                    }
                }
             break;

            case R.id.cancelMobileDialog:
                addNewPetMobileNumber.dismiss();
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