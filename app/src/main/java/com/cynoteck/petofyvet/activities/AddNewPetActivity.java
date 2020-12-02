package com.cynoteck.petofyvet.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.addParamRequest.AddPetParams;
import com.cynoteck.petofyvet.params.addParamRequest.AddPetRequset;
import com.cynoteck.petofyvet.params.getpetAgeRequest.GetPetAgeParameter;
import com.cynoteck.petofyvet.params.getpetAgeRequest.GetPetAgeRequestData;
import com.cynoteck.petofyvet.params.petBreedRequest.BreedParams;
import com.cynoteck.petofyvet.params.petBreedRequest.BreedRequest;
import com.cynoteck.petofyvet.params.searchPetParentRequest.SearchPetParentParameter;
import com.cynoteck.petofyvet.params.searchPetParentRequest.SearchPetParentRequestData;
import com.cynoteck.petofyvet.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofyvet.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofyvet.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofyvet.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofyvet.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofyvet.response.addPet.uniqueIdResponse.UniqueResponse;
import com.cynoteck.petofyvet.response.dateOfBirthResponse.DateOfBirthResponse;
import com.cynoteck.petofyvet.response.getPetAgeResponse.GetPetAgeresponseData;
import com.cynoteck.petofyvet.response.getPetParrentnameReponse.GetPetParentResponseData;
import com.cynoteck.petofyvet.response.petAgeUnitResponse.PetAgeUnitResponseData;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.xml.datatype.Duration;

import retrofit2.Response;

public class AddNewPetActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    ScrollView scrollView;
    AppCompatSpinner age_wise,parent_address,  add_pet_type, add_pet_age_dialog ,add_pet_sex_dialog,add_pet_breed_dialog, add_pet_color_dialog,add_pet_size_dialog;
    EditText pet_name_ET,age_neumeric;
    Button save_BT;
    TextView peto_reg_number_dialog,calenderTextView_dialog,ageViewTv;
    CheckBox convert_yr_to_age;
    AutoCompleteTextView pet_parent_name_ET,pet_contact_number_ET;
    LinearLayout day_and_age_layout;
    String petUniqueId="",getStrSpnerItemPetNmId="",strSpnrBreedId="",strSpnrAgeId="",strSpnrColorId="",strAgeCount="",
            strSpneSizeId="",strSpnrSexId="",strSpnerItemPetType="",strSpnrBreed="",strSpnrAge="",strSpnrSex="",
            currentDateandTime="",strSexType="",strResponseOtp="",petId="",petParentContactNumber="",type="";
    ImageView back_arrow_IV;
    DatePickerDialog picker;
    ArrayList<String> petType;
    ArrayList<String> petBreed;
    ArrayList<String> petAge;
    ArrayList<String> petColor;
    ArrayList<String> petSize;
    ArrayList<String> petSex;
    ArrayList<String> petAgeType;
    ArrayList<String> parentAdress;

    HashMap<String,String> petTypeHashMap=new HashMap<>();
    HashMap<String,String> petBreedHashMap=new HashMap<>();
    HashMap<String,String> petAgeHashMap=new HashMap<>();
    HashMap<String,String> petColorHashMap=new HashMap<>();
    HashMap<String,String> petSizeHashMap=new HashMap<>();
    HashMap<String,String> petSexHashMap=new HashMap<>();
    HashMap<String,String> petExistingSearch=new HashMap<>();
    HashMap<String,String> petAgeUnitHash=new HashMap<>();
    Methods methods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pet2);
        methods = new Methods(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("appointment");
        intlization();
        petSex=new ArrayList<>();
        petSex.add("Pet Sex");
        petSex.add("Male");
        petSex.add("Female");
        petSexHashMap.put("Pet Sex","0");
        petSexHashMap.put("Male","1");
        petSexHashMap.put("Female","2");

        parentAdress=new ArrayList<>();
        parentAdress.add("Mr.");
        parentAdress.add("Mrs.");
        parentAdress.add("Miss.");

        currentDateAndTime();
        if (methods.isInternetOn()){
            petType();
            genaretePetUniqueKey();
            getPetAgeUnit();
            getPetParentname();
        }else {

            methods.DialogInternet();
        }
        setSpinnerPetSex();
        setPetParentAdress();

    }




    private void intlization() {
        scrollView=findViewById(R.id.scrollView);
        peto_reg_number_dialog=findViewById(R.id.peto_reg_number_dialog);
        calenderTextView_dialog=findViewById(R.id.calenderTextView_dialog);
        add_pet_type=findViewById(R.id.add_pet_type);
        add_pet_age_dialog=findViewById(R.id.add_pet_age_dialog);
        add_pet_sex_dialog=findViewById(R.id.add_pet_sex_dialog);
        add_pet_breed_dialog=findViewById(R.id.add_pet_breed_dialog);
        add_pet_color_dialog=findViewById(R.id.add_pet_color_dialog);
        add_pet_size_dialog=findViewById(R.id.add_pet_size_dialog);
        pet_name_ET=findViewById(R.id.pet_name_ET);
        pet_parent_name_ET=findViewById(R.id.pet_parent_name_ET);
        pet_contact_number_ET=findViewById(R.id.pet_contact_number);
        save_BT=findViewById(R.id.save_changes_BT);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        convert_yr_to_age=findViewById(R.id.convert_yr_to_age);
        age_wise=findViewById(R.id.age_wise);
        age_neumeric=findViewById(R.id.age_neumeric);
        parent_address=findViewById(R.id.parent_address);
        day_and_age_layout=findViewById(R.id.day_and_age_layout);
        ageViewTv=findViewById(R.id.ageViewTv);
        ageViewTv.setText("Age:- 0 Days");

        save_BT.setOnClickListener(this);
        back_arrow_IV.setOnClickListener(this);
        calenderTextView_dialog.setOnClickListener(this);
        convert_yr_to_age.setOnClickListener(this);

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (age_neumeric.isFocused()) {
                        Rect outRect = new Rect();
                        age_neumeric.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            if(age_neumeric.getText().toString().isEmpty())
                            {

                            }
                            else
                            {
                                if(strAgeCount.equals("Day"))
                                {
                                    getPetDateofBirthDependsOnDays(age_neumeric.getText().toString());
                                }
                                else if(strAgeCount.equals("Week"))
                                {
                                    int weekToDays= Integer.parseInt(age_neumeric.getText().toString());
                                    int days=weekToDays*7;
                                    getPetDateofBirthDependsOnDays(String.valueOf(days));
                                }
                                else if(strAgeCount.equals("Month"))
                                {
                                    int monthToDays= Integer.parseInt(age_neumeric.getText().toString());
                                    int days=monthToDays*30;
                                    getPetDateofBirthDependsOnDays(String.valueOf(days));
                                }
                                else
                                {
                                    int yearsToDays= Integer.parseInt(age_neumeric.getText().toString());
                                    int days=yearsToDays*365;
                                    getPetDateofBirthDependsOnDays(String.valueOf(days));
                                }

                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.calenderTextView_dialog:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderTextView_dialog.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                String DoB=dayOfMonth + " " + (monthOfYear + 1) + " " + year;
                                String DoBforage=dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                Log.d("jajajaajja",""+methods.getDays(DoB,methods.getDate()));
                                String age= String.valueOf(methods.getDays(DoB,methods.getDate()));
                                age=age.substring(0,age.length()-2);
                                getPetAgeString(DoBforage);
                                age_neumeric.setText(age);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();
                break;
            case R.id.convert_yr_to_age:
                if(((CompoundButton) v).isChecked()){
                    day_and_age_layout.setVisibility(View.VISIBLE);
                    calenderTextView_dialog.setVisibility(View.GONE);
                }
                else
                {
                    if(age_neumeric.getText().toString().isEmpty())
                    {

                    }
                    else
                    {
                        if(strAgeCount.equals("Day"))
                        {
                            getPetDateofBirthDependsOnDays(age_neumeric.getText().toString());
                        }
                        else if(strAgeCount.equals("Week"))
                        {
                           int weekToDays= Integer.parseInt(age_neumeric.getText().toString());
                           int days=weekToDays*7;
                           getPetDateofBirthDependsOnDays(String.valueOf(days));
                        }
                        else if(strAgeCount.equals("Month"))
                        {
                            int monthToDays= Integer.parseInt(age_neumeric.getText().toString());
                            int days=monthToDays*30;
                            getPetDateofBirthDependsOnDays(String.valueOf(days));
                        }
                        else
                        {
                            int yearsToDays= Integer.parseInt(age_neumeric.getText().toString());
                            int days=yearsToDays*365;
                            getPetDateofBirthDependsOnDays(String.valueOf(days));
                        }

                    }
                    day_and_age_layout.setVisibility(View.GONE);
                    calenderTextView_dialog.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.save_changes_BT:
                String strPetName= pet_name_ET.getText().toString().trim();
                String strPetParentName = pet_parent_name_ET.getText().toString().trim();
                String strPetContactNumber = pet_contact_number_ET.getText().toString().trim();
                String strPetBirthDay = calenderTextView_dialog.getText().toString().trim();

                if(strPetName.isEmpty())
                {
                    Toast.makeText(this, "Enter Pet Name", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError("Enter Pet Name");
                    pet_parent_name_ET.setError(null);
                    pet_contact_number_ET.setError(null);
                    calenderTextView_dialog.setError(null);
                }
                else if(strPetParentName.isEmpty())
                {
                    Toast.makeText(this, "Enter Parent Name", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_parent_name_ET.setError("Enter Parent Name");
                    pet_contact_number_ET.setError(null);
                    calenderTextView_dialog.setError(null);
                }
                else if(strPetContactNumber.isEmpty())
                {
                    Toast.makeText(this, "Enter Contact Number", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_parent_name_ET.setError(null);
                    pet_contact_number_ET.setError("Enter Contact Number");
                    calenderTextView_dialog.setError(null);
                }
                else if(strPetBirthDay.isEmpty())
                {
                    Toast.makeText(this, "Set Pet DOB", Toast.LENGTH_SHORT).show();
                    pet_name_ET.setError(null);
                    pet_parent_name_ET.setError(null);
                    pet_contact_number_ET.setError(null);
                    calenderTextView_dialog.setError("Pet DOB");
                }
                //pet size and color.
                else if(strSpnerItemPetType.isEmpty()||(strSpnerItemPetType.equals("Select Pet Type")))
                {
                    Toast.makeText(this, "Select Type!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrBreed.isEmpty()||(strSpnrBreed.equals("Pet Breed")))
                {
                    Toast.makeText(this, "Select Breed!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrSex.isEmpty()||(strSpnrSex.equals("Pet Sex")))
                {
                    Toast.makeText(this, "Select Pet Sex!!", Toast.LENGTH_SHORT).show();
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
                    data.setPetUniqueId(petUniqueId);
                    data.setPetCategoryId(getStrSpnerItemPetNmId);
                    data.setPetSexId(strSpnrSexId);
                    data.setPetAgeId("0.0");
                    data.setPetSizeId("0.0");
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
                        peto_reg_number_dialog.setText("PETO-XYZ");
                        pet_contact_number_ET.getText().clear();
                        pet_parent_name_ET.getText().clear();

                        addPetData(addPetRequset);
                    } else {
                        methods.DialogInternet();
                    }
                }
                break;
        }
    }

    private void getPetAgeUnit() {
        ApiService<PetAgeUnitResponseData> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetAgeUnit(Config.token), "GetPetAgeUnit");
    }

    private void getPetDateofBirthDependsOnDays(String days)
    {
        ApiService<DateOfBirthResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().GetPetDateOfBirth(Config.token,days), "getDateOfYear");
    }

    private void getPetAgeString(String DOB)
    {
        GetPetAgeParameter getPetAgeParameter=new GetPetAgeParameter();
        getPetAgeParameter.setDateOfBirth(DOB);
        GetPetAgeRequestData getPetAgeRequestData=new GetPetAgeRequestData();
        getPetAgeRequestData.setData(getPetAgeParameter);
        ApiService<GetPetAgeresponseData> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetAgeString(Config.token,getPetAgeRequestData), "GetPetAgeString");
        Log.e("DAILOG","getPetAgeString==>"+getPetAgeRequestData);
    }

    private void getPetParentname()
    {
        pet_parent_name_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("dataChange","afterTextChanged"+new String(editable.toString()));
                String value=editable.toString();
                SearchPetParentParameter searchPetParentParameter=new SearchPetParentParameter();
                searchPetParentParameter.setPrefix(value);
                SearchPetParentRequestData searchPetParentRequestData=new SearchPetParentRequestData();
                searchPetParentRequestData.setData(searchPetParentParameter);
                ApiService<GetPetParentResponseData> service = new ApiService<>();
                service.get( AddNewPetActivity.this, ApiClient.getApiInterface().searchPetParent(Config.token,searchPetParentRequestData), "SearchPetParent");
                Log.e("DAILOG","getPetaParentName==>"+searchPetParentRequestData);
            }
        });


        pet_parent_name_ET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String value=pet_parent_name_ET.getText().toString();
            String[] city_array = value.split("\\(");

                pet_parent_name_ET.setText(city_array[0]);
                pet_contact_number_ET.setText(city_array[1].substring(0,city_array[1].length()-1).trim());
          }
         });

        pet_contact_number_ET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value=editable.toString();
                SearchPetParentParameter searchPetParentParameter=new SearchPetParentParameter();
                searchPetParentParameter.setPrefix(value);
                SearchPetParentRequestData searchPetParentRequestData=new SearchPetParentRequestData();
                searchPetParentRequestData.setData(searchPetParentParameter);
                ApiService<GetPetParentResponseData> service = new ApiService<>();
                service.get( AddNewPetActivity.this, ApiClient.getApiInterface().searchPetParent(Config.token,searchPetParentRequestData), "SearchPetParent");
                Log.e("DAILOG","getPetaParentName==>"+searchPetParentRequestData);

            }
        });

        pet_contact_number_ET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String value=pet_contact_number_ET.getText().toString();
                String[] city_array = value.split("\\(");
                pet_parent_name_ET.setText(city_array[0]);
                pet_contact_number_ET.setText(city_array[1].substring(0,city_array[1].length()-1).trim());
            }
        });

    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "getDateOfYear":
                try {
                    DateOfBirthResponse dateOfBirthResponse = (DateOfBirthResponse) arg0.body();
                    Log.d("getDateOfYear",dateOfBirthResponse.toString());
                    int responseCode = Integer.parseInt(dateOfBirthResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        calenderTextView_dialog.setText(dateOfBirthResponse.getData());
                        getPetAgeString(dateOfBirthResponse.getData());
                       // showDatePickerDialog(dateOfBirthResponse.getData());
                    }else if (responseCode==614){
                        Toast.makeText(this, dateOfBirthResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "SearchPetParent":
                try {
                    Log.d("SearchPetParent",arg0.body().toString());
                    GetPetParentResponseData getPetParentResponseData = (GetPetParentResponseData) arg0.body();
                    int responseCode = Integer.parseInt(getPetParentResponseData.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Log.d("SearchPetParent",""+getPetParentResponseData.getData().size());
                        ArrayList remarksSearchList=new ArrayList<>();
                        for(int i=0;i<getPetParentResponseData.getData().size();i++)
                        {
                            remarksSearchList.add(getPetParentResponseData.getData().get(i).getPetParentName()
                                                   +"\n( "+getPetParentResponseData.getData().get(i).getContactNumber()+" )");
                        }

                        //for parent name

                        ArrayAdapter<String> randomArray = new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1, remarksSearchList);
                        pet_parent_name_ET.setAdapter(randomArray);
                        randomArray.notifyDataSetChanged();

                        //for contact number
                        ArrayAdapter<String> randomArrayContactNumber = new ArrayAdapter<String>(this,
                                android.R.layout.simple_list_item_1, remarksSearchList);
                        pet_contact_number_ET.setAdapter(randomArrayContactNumber);
                        randomArrayContactNumber.notifyDataSetChanged();


                    }else if (responseCode==614){
                        Toast.makeText(this, getPetParentResponseData.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        petTypeHashMap.put("Select Pet Type","0");
                        Log.d("lalal",""+petTypeResponse.getData().size());
                        for(int i=0; i<petTypeResponse.getData().size(); i++){
                            Log.d("petttt",""+petTypeResponse.getData().get(i).getPetType1());
                            petType.add(petTypeResponse.getData().get(i).getPetType1());
                            petTypeHashMap.put(petTypeResponse.getData().get(i).getPetType1(),petTypeResponse.getData().get(i).getId());
                        }
                        setPetTypeSpinner();


                    }else if (responseCode==614){
                        Toast.makeText(this, petTypeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetAgeString":
                try {
                    Log.d("GetPetAgeString",arg0.body().toString());
                    GetPetAgeresponseData getPetAgeresponseData = (GetPetAgeresponseData) arg0.body();
                    int responseCode = Integer.parseInt(getPetAgeresponseData.getResponse().getResponseCode());
                    if (responseCode== 109){
                        ageViewTv.setText(getPetAgeresponseData.getData().getPetAge());
                    }else if (responseCode==614){
                        Toast.makeText(this, getPetAgeresponseData.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "GetPetAgeUnit":
                try {
                    Log.d("GetPetTypes",arg0.body().toString());
                    PetAgeUnitResponseData petAgeUnitResponseData = (PetAgeUnitResponseData) arg0.body();
                    int responseCode = Integer.parseInt(petAgeUnitResponseData.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petAgeType=new ArrayList<>();
                        Log.d("lalal",""+petAgeUnitResponseData.getData().size());
                        for(int i=0; i<petAgeUnitResponseData.getData().size(); i++){
                            Log.d("petttt",""+petAgeUnitResponseData.getData().get(i).getAge());
                            petAgeType.add(petAgeUnitResponseData.getData().get(i).getAgeUnit());
                            petAgeUnitHash.put(petAgeUnitResponseData.getData().get(i).getAgeUnit(),petAgeUnitResponseData.getData().get(i).getAge());
                        }
                        setPetAgeType();

                    }else if (responseCode==614){
                        Toast.makeText(this, petAgeUnitResponseData.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GeneratePetUniqueId":
                try {
                    Log.d("GeneratePetUniqueId",arg0.body().toString());
                    UniqueResponse uniqueResponse = (UniqueResponse) arg0.body();
                    int responseCode = Integer.parseInt(uniqueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petUniqueId=uniqueResponse.getData().getPetUniqueId();
                        peto_reg_number_dialog.setText(petUniqueId);

                    }else if (responseCode==614){
                        Toast.makeText(this, uniqueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        setPetBreeSpinner();

                    }else if (responseCode==614){
                        Toast.makeText(this, breedCatRespose.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        setPetAgeSpinner();

                    }else if (responseCode==614){
                        Toast.makeText(this, petAgeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "GetPetColor":
                try {
                    methods.customProgressDismiss();
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
                        setPetColorSpinner();
                    }else if (responseCode==614){
                        Toast.makeText(this, petColorValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
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
                        setPetSizeSpinner();

                    }else if (responseCode==614){
                        Toast.makeText(this, petSizeValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
            case "AddPet":
                try {
                    methods.customProgressDismiss();
                    Log.d("AddPet",arg0.body().toString());

                    AddPetValueResponse addPetValueResponse = (AddPetValueResponse) arg0.body();

                    int responseCode = Integer.parseInt(addPetValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if (type.equals("appointment")){
                            Intent intent = new Intent();
                            intent.putExtra("userId", addPetValueResponse.getData().getUserId());
                            intent.putExtra("uniqueId", addPetValueResponse.getData().getPetUniqueId());
                            intent.putExtra("parent", addPetValueResponse.getData().getPetParentName());
                            intent.putExtra("sex", addPetValueResponse.getData().getPetSex());
                            intent.putExtra("age", addPetValueResponse.getData().getPetAge());
                            intent.putExtra("petName", addPetValueResponse.getData().getPetName());
                            intent.putExtra("petId", addPetValueResponse.getData().getId());
                            setResult(RESULT_OK, intent);
                            finish();
                            Toast.makeText(this, "Pet Added Successfully", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intentPetDetails = new Intent(this, PetDetailsActivity.class);
                            Bundle data = new Bundle();
                            data.putString("pet_id", addPetValueResponse.getData().getId().toString());
                            data.putString("pet_name", addPetValueResponse.getData().getPetName());
                            data.putString("pet_parent", addPetValueResponse.getData().getPetParentName());
                            if (addPetValueResponse.getData().getPetSexId().equals("2.0"))
                                strSexType = "Female";
                            else
                                strSexType = "Male";
                            data.putString("pet_sex", strSexType);
                            data.putString("pet_unique_id", addPetValueResponse.getData().getPetUniqueId());
                            data.putString("pet_DOB",addPetValueResponse.getData().getDateOfBirth());
                            data.putString("pet_encrypted_id",addPetValueResponse.getData().getEncryptedId());
                            intentPetDetails.putExtras(data);
                            startActivity(intentPetDetails);
                            Toast.makeText(this, "Pet Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }else if (responseCode==614){
                        Toast.makeText(this, addPetValueResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
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

    /*private void showDatePickerDialog(String date) {
        // here date is 5-12-2013
        String[] split = date.split("/");
        int day = Integer.valueOf(split[0]);
        int month = Integer.valueOf(split[1]);
        int year = Integer.valueOf(split[2]);
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

            }
        };

        picker = new DatePickerDialog(this,
                dateSetListener, year, month, day);
        picker.show();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.backCall.equals("hitUnique")){
            Config.backCall ="";
            genaretePetUniqueKey();
        }

    }

    private void currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy h:mm:ss a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Log.d("currentDateandTime",""+currentDateandTime);
    }
    private void setPetSizeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petSize);
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
    private void setPetColorSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petColor);
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
    private void setPetAgeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petAge);
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
    private void setPetBreeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petBreed);
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
    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petType);
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
                if(!getStrSpnerItemPetNmId.equals("0"))
                {
                    getPetBreed();
                    getPetAge();
                    getPetColor();
                    getPetSize();

                }

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerPetSex() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petSex);
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

    private void setPetAgeType() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petAgeType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        age_wise.setAdapter(aa);
        age_wise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strAgeCount=item;
                Log.d("spnerType","PetAge"+item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetParentAdress() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,parentAdress);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        parent_address.setAdapter(aa);
        parent_address.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                Log.d("spnerType","ParentAddress"+item);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void petType() {
        ApiService<PetTypeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().petTypeApi(Config.token), "GetPetTypes");
    }
    private void getPetBreed() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if(!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<BreedCatRespose> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetBreedApi(Config.token,breedParams), "GetPetBreed");
    }
    private void genaretePetUniqueKey() {
        ApiService<UniqueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGeneratePetUniqueId(Config.token), "GeneratePetUniqueId");
    }
    private void getPetAge() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if(!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetAgeValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetAgeApi(Config.token,breedParams), "GetPetAge");
    }
    private void getPetColor() {
        methods.showCustomProgressBarDialog(this);
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if(!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetColorValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetColorApi(Config.token,breedParams), "GetPetColor");
    }
    private void getPetSize() {
        BreedRequest breedRequest = new BreedRequest();
        breedRequest.setGetAll("false");
        if(!getStrSpnerItemPetNmId.equals("0"))
            breedRequest.setPetCategoryId(getStrSpnerItemPetNmId);
        else
            breedRequest.setPetCategoryId("1");
        BreedParams breedParams = new BreedParams();
        breedParams.setData(breedRequest);

        ApiService<PetSizeValueResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getGetPetSizeApi(Config.token,breedParams), "GetPetSize");
    }
    private void addPetData(AddPetRequset addPetRequset) {
        methods.showCustomProgressBarDialog(this);
        ApiService<AddPetValueResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addNewPet(Config.token,addPetRequset), "AddPet");
        Log.e("DATALOG","check1=> "+addPetRequset);

    }
  


}
