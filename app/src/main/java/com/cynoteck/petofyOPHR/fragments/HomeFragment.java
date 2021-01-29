package com.cynoteck.petofyOPHR.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.activities.AddNewPetActivity;
import com.cynoteck.petofyOPHR.activities.LoginActivity;
import com.cynoteck.petofyOPHR.activities.PetDetailsActivity;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.checkpetInVetRegister.InPetRegisterRequest;
import com.cynoteck.petofyOPHR.params.checkpetInVetRegister.InPetregisterParams;
import com.cynoteck.petofyOPHR.params.newPetEntryParams.NewPetParams;
import com.cynoteck.petofyOPHR.params.newPetEntryParams.NewPetRequest;
import com.cynoteck.petofyOPHR.params.otpRequest.SendOtpParameter;
import com.cynoteck.petofyOPHR.params.otpRequest.SendOtpRequest;
import com.cynoteck.petofyOPHR.params.petReportsRequest.PetDataParams;
import com.cynoteck.petofyOPHR.params.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyOPHR.response.InPetVeterian.InPetVeterianResponse;
import com.cynoteck.petofyOPHR.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyOPHR.response.loginRegisterResponse.UserPermissionMasterList;
import com.cynoteck.petofyOPHR.response.newPetResponse.NewPetRegisterResponse;
import com.cynoteck.petofyOPHR.response.otpResponse.OtpResponse;
import com.cynoteck.petofyOPHR.response.staffPermissionListResponse.CheckStaffPermissionResponse;
import com.cynoteck.petofyOPHR.response.updateProfileResponse.CityResponse;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.Methods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import retrofit2.Response;

public class HomeFragment extends Fragment implements View.OnClickListener, ApiResponse, TextWatcher {

    RecyclerView pet_list_RV;
    Context context;
    View view;
    ImageView reports_IV,back_arrow_IV_new_entry,new_pet_search;
    RelativeLayout mainHome,search_boxRL;
    LinearLayout addNewEntry;
    Methods methods;
    CardView reports_CV, all_staff_CV, allPets_CV,appoint_CV;
    AutoCompleteTextView search_box_add_new;
    ArrayList<String> petUniueId=null;
    HashMap<String,String> petExistingSearch;
    TextView staff_headline_TV,cancelOtpDialog;
    String petId="",petParentContactNumber="",strResponseOtp="";
    Dialog otpDialog;
    TextInputLayout otp_TL;
    TextInputEditText pet_parent_otp;
    Button submit_parent_otp;
    SharedPreferences sharedPreferences;
    String userTYpe="";
    String permissionId="";
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferences = getActivity().getSharedPreferences("userdetails", 0);

        init();
        return view;
    }



    private void init() {
        methods = new Methods(context);
        search_box_add_new = view.findViewById(R.id.search_box_add_new);
        search_boxRL = view.findViewById(R.id.search_boxRL);
        staff_headline_TV = view.findViewById(R.id.staff_headline_TV);
        new_pet_search = view.findViewById(R.id.new_pet_search);
        back_arrow_IV_new_entry = view.findViewById(R.id.back_arrow_IV_new_entry);
        addNewEntry = view.findViewById(R.id.addNewEntry);
        reports_CV=view.findViewById(R.id.reports_CV);
        all_staff_CV = view.findViewById(R.id.staff_CV);
        mainHome=view.findViewById(R.id.mainHome);
        pet_list_RV=view.findViewById(R.id.pet_id_TV);
        allPets_CV=view.findViewById(R.id.allPets_CV);
        appoint_CV=view.findViewById(R.id.appoint_CV);

        addNewEntry.setOnClickListener(this);
        search_box_add_new.addTextChangedListener(this);
        allPets_CV.setOnClickListener(this);
        reports_CV.setOnClickListener(this);
        all_staff_CV.setOnClickListener(this);
        appoint_CV.setOnClickListener(this);
        new_pet_search.setOnClickListener(this);
        back_arrow_IV_new_entry.setOnClickListener(this);

        search_box_add_new.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("dataChange","afterTextChanged"+new String(editable.toString()));
                final String value=editable.toString();
                if (methods.isInternetOn()){
                            getPetList(value);
                }else {
                    methods.DialogInternet();
                }
            }
        });

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
                String pet_cat_id = st.nextToken();
                Log.d("ppppp",""+PetUniqueId+" "+PetName+" "+PetParentName+" "+PetSex+" "+PetAge+" "+Id+" "+pet_DOB+" "+pet_encrypted_id+" "+pet_cat_id);
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
                data.putString("pet_cat_id",pet_cat_id);
                petDetailsIntent.putExtras(data);
                startActivity(petDetailsIntent);
                clearSearch();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.new_pet_search:
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
                            String pet_cat_id = st.nextToken();
                            Log.d("ppppp",""+PetUniqueId+" "+PetName+" "+PetParentName+" "+PetSex+" "+petAge+" "+Id+" "+pet_DOB+" "+pet_encrypted_id+" "+pet_cat_id);
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
                            data.putString("pet_cat_id",pet_cat_id);
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

            case R.id.addNewEntry:
                userTYpe = sharedPreferences.getString("user_type", "");
                if (userTYpe.equals("Vet Staff")){
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("userPermission", null);
                    Type type = new TypeToken<ArrayList<UserPermissionMasterList>>() {}.getType();
                    ArrayList<UserPermissionMasterList> arrayList = gson.fromJson(json, type);
                    Log.e("ArrayList",arrayList.toString());
                    Log.d("UserType",userTYpe);
                    permissionId = "1";
                    methods.showCustomProgressBarDialog(getContext());
                    String url  = "user/CheckStaffPermission/"+permissionId;
                    Log.e("URL",url);
                    ApiService<CheckStaffPermissionResponse> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().getCheckStaffPermission(Config.token,url), "CheckPermission");
                }else if (userTYpe.equals("Veterinarian")){
                    Intent addNewPetIntent = new Intent(getContext(), AddNewPetActivity.class);
                    addNewPetIntent.putExtra("appointment","");
                    startActivity(addNewPetIntent);
                }

                break;

            case R.id.cancelOtpDialog:
                otpDialog.dismiss();
                break;

            case R.id.back_arrow_IV_new_entry:
                clearSearch();
                break;

            case R.id.reports_CV:
                ReportSelectionFragment reportSelectionFragment = new ReportSelectionFragment();
                replaceFragment(reportSelectionFragment);
                break;

            case R.id.staff_CV:
                userTYpe = sharedPreferences.getString("user_type", "");
                if (userTYpe.equals("Vet Staff")){
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("userPermission", null);
                    Type type = new TypeToken<ArrayList<UserPermissionMasterList>>() {}.getType();
                    ArrayList<UserPermissionMasterList> arrayList = gson.fromJson(json, type);
//                    for (int i=0;i<arrayList.size();i++){
//                        if (arrayList.get(i).getPermissionCode().equals("15")){
//                            Toast.makeText(context, "15", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
                    Log.e("ArrayList",arrayList.toString());
                    Log.d("UserType",userTYpe);
                    permissionId = "15";
                    methods.showCustomProgressBarDialog(getContext());
                    String url  = "user/CheckStaffPermission/"+permissionId;
                    Log.e("URL",url);
                    ApiService<CheckStaffPermissionResponse> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().getCheckStaffPermission(Config.token,url), "CheckPermission");
                }else if (userTYpe.equals("Veterinarian")){
                    AllStaffFragment allStaffFragment = new AllStaffFragment();
                    replaceFragment(allStaffFragment);

                }

                break;

            case R.id.allPets_CV:
                PetRegisterFragment petRegisterFragment = new PetRegisterFragment();
                replaceFragment(petRegisterFragment);

                break;

            case R.id.appoint_CV:
                userTYpe = sharedPreferences.getString("user_type", "");
                if (userTYpe.equals("Vet Staff")){
                    Gson gson = new Gson();
                    String json = sharedPreferences.getString("userPermission", null);
                    Type type = new TypeToken<ArrayList<UserPermissionMasterList>>() {}.getType();
                    ArrayList<UserPermissionMasterList> arrayList = gson.fromJson(json, type);
                    Log.e("ArrayList",arrayList.toString());
                    Log.d("UserType",userTYpe);
                    permissionId = "16";
                    methods.showCustomProgressBarDialog(getContext());
                    String url  = "user/CheckStaffPermission/"+permissionId;
                    Log.e("URL",url);
                    ApiService<CheckStaffPermissionResponse> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().getCheckStaffPermission(Config.token,url), "CheckPermission");
                }else if (userTYpe.equals("Veterinarian")){
                    AppointementFragment appointementFragment = new AppointementFragment();
                    replaceFragment(appointementFragment);

                }

                break;


        }

    }

    private void replaceFragment(Fragment fragment) {
        Config.count = 0;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getPetList(String prefix) {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(0);
        getPetDataParams.setPageSize(10);
        getPetDataParams.setSearch_Data(prefix);
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetList(Config.token,getPetDataRequest), "GetPetList");
        Log.e("DATALOG","check1=> "+methods.getRequestJson(getPetDataRequest));


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

        switch (key) {
            case "CheckPermission":
                try {
                    methods.customProgressDismiss();
                    CheckStaffPermissionResponse checkStaffPermissionResponse = (CheckStaffPermissionResponse) arg0.body();
                    Log.d("GetPetList", checkStaffPermissionResponse.toString());
                    int responseCode = Integer.parseInt(checkStaffPermissionResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        if (checkStaffPermissionResponse.getData().equals("true")){
                            if (permissionId.equals("16")) {
                                AppointementFragment appointementFragment = new AppointementFragment();
                                replaceFragment(appointementFragment);
                            }else if (permissionId.equals("1")){
                                Intent addNewPetIntent = new Intent(getContext(), AddNewPetActivity.class);
                                addNewPetIntent.putExtra("appointment","");
                                startActivity(addNewPetIntent);
                            }else if (permissionId.equals("15")){
                                AllStaffFragment allStaffFragment = new AllStaffFragment();
                                replaceFragment(allStaffFragment);
                            }
                        }else {
                            Toast.makeText(context, "Permission not Granted!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(context, "Please Try Again!!", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;

            case "GetPetList":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) arg0.body();
                    Log.d("GetPetList", getPetListResponse.toString());
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());

                    if (responseCode == 109) {
                        petUniueId = new ArrayList<>();
                        petExistingSearch = new HashMap<>();
                        for (int i = 0; i < getPetListResponse.getData().getPetList().size(); i++) {
                            petUniueId.add(getPetListResponse.getData().getPetList().get(i).getPetUniqueId() + ":- "
                                    + getPetListResponse.getData().getPetList().get(i).getPetName() + "(" + getPetListResponse.getData().getPetList().get(i).getPetSex() + "," + getPetListResponse.getData().getPetList().get(i).getPetParentName() + ")");

                            petExistingSearch.put(getPetListResponse.getData().getPetList().get(i).getPetUniqueId() + ":- "
                                            + getPetListResponse.getData().getPetList().get(i).getPetName() + "(" + getPetListResponse.getData().getPetList().get(i).getPetSex() + "," + getPetListResponse.getData().getPetList().get(i).getPetParentName() + ")",
                                    getPetListResponse.getData().getPetList().get(i).getPetUniqueId() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getPetName() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getPetParentName() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getPetSex() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getPetAge() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getId() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getDateOfBirth() + ","
                                            + getPetListResponse.getData().getPetList().get(i).getEncryptedId() +","
                                            +getPetListResponse.getData().getPetList().get(i).getPetCategoryId());
                        }

                        Log.d("jajajajjaja", "" + petUniueId.size() + " \n" + petUniueId.toString());
                        Log.d("lllllllllll", "" + petExistingSearch.size() + " \n" + petExistingSearch);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (getActivity(), android.R.layout.simple_list_item_1, petUniueId);
                        search_box_add_new.setThreshold(1);//will start working from first character
                        search_box_add_new.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
                        search_box_add_new.setTextColor(Color.BLACK);
                    }
                    else
                    {
                        methods.customProgressDismiss();
                        if(getPetListResponse.getResponse().getResponseMessage().equals("Invalid token."))
                        {
                            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                            alertDialog.setTitle("Warning!!");
                            alertDialog.setMessage("Your Session Expired. Please, Logout and Login Again.");
                            alertDialog.setIcon(getActivity().getDrawable(R.drawable.ic_baseline_warning));
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "LOGOUT",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            SharedPreferences preferences =getActivity().getSharedPreferences("userdetails",0);
                                            SharedPreferences.Editor editor = preferences.edit();
                                            editor.clear();
                                            editor.apply();
                                            startActivity(new Intent(getActivity(), LoginActivity.class));
                                            getActivity().finish();
                                        }
                                    });
                            alertDialog.setCancelable(false);
                            alertDialog.setCanceledOnTouchOutside(false);
                            alertDialog.show();
                        }
                    }


                } catch (Exception e) {
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
                        //getPetList();
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
        }

    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

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

    private void clearSearch() {
        search_box_add_new.getText().clear();
        search_boxRL.setVisibility(View.GONE);
        back_arrow_IV_new_entry.setVisibility(View.GONE);
        staff_headline_TV.setVisibility(View.VISIBLE);
        InputMethodManager imm1 = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm1.hideSoftInputFromWindow(search_box_add_new.getWindowToken(), 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if(getPetListRefresh==false)
        getPetList();*/
    }
}