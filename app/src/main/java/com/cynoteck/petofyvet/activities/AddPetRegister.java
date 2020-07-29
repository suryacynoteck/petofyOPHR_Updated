package com.cynoteck.petofyvet.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
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
import com.cynoteck.petofyvet.params.petBreedRequest.BreedParams;
import com.cynoteck.petofyvet.params.petBreedRequest.BreedRequest;
import com.cynoteck.petofyvet.response.addPet.addPetResponse.AddPetValueResponse;
import com.cynoteck.petofyvet.response.addPet.breedResponse.BreedCatRespose;
import com.cynoteck.petofyvet.response.addPet.petAgeResponse.PetAgeValueResponse;
import com.cynoteck.petofyvet.response.addPet.petColorResponse.PetColorValueResponse;
import com.cynoteck.petofyvet.response.addPet.petSizeResponse.PetSizeValueResponse;
import com.cynoteck.petofyvet.response.addPet.uniqueIdResponse.UniqueResponse;
import com.cynoteck.petofyvet.response.updateProfileResponse.PetTypeResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Response;

public class AddPetRegister extends AppCompatActivity implements View.OnClickListener, ApiResponse {

    AppCompatSpinner add_pet_age,add_pet_type,add_pet_sex,add_pet_breed,add_pet_color,add_pet_size;
    TextInputLayout pet_name_layout,pet_parent_name_layout,pet_contact_number_layout,pet_description_layout,
                    pet_address_layout;
    TextInputEditText pet_name,pet_parent_name,pet_contact_number,pet_description,pet_address;
    TextView peto_reg_number,calenderView;
    ImageView pet_profile_image,service_cat_img_one,service_cat_img_two,service_cat_img_three,service_cat_img_four,
            service_cat_img_five;
    Button pet_submit;
    String strPetName="",strPetParentName="",strPetContactNumber="",strPetDescription="",strPetAdress="",strPetBirthDay="",
            strSpnerItemPetNm="",getStrSpnerItemPetNmId="",strSpnrBreed="",strSpnrBreedId="",petUniqueId="",
            strSpnrAge="",strSpnrAgeId="",strSpnrColor="",strSpnrColorId="",strSpnrSize="",strSpneSizeId="",
            strSpnrSex="",strSpnrSexId="",currentDateandTime="";


    Methods methods;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_register);
        init();
        currentDateAndTime();
        methods=new Methods(this);

        petSex=new ArrayList<>();
        petSex.add("Pet Sex");
        petSex.add("Male");
        petSex.add("Female");

        petSexHashMap.put("Pet Sex","0");
        petSexHashMap.put("Male","1");
        petSexHashMap.put("Female","2");

        if(methods.isInternetOn())
        {
            petType();
            genaretePetUniqueKey();
            getPetBreed();
            getPetAge();
            getPetColor();
            getPetSize();
            setSpinnerPetSex();
        }
        else
        {
            methods.DialogInternet();
        }

    }

    private void currentDateAndTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy h:mm:ss a", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        Log.d("currentDateandTime",""+currentDateandTime);
    }


    private void petType() {
        methods.showCustomProgressBarDialog(this);
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



    private void init() {
        //Spinner
        add_pet_type=findViewById(R.id.add_pet_type);
        add_pet_sex=findViewById(R.id.add_pet_sex);
        add_pet_breed=findViewById(R.id.add_pet_breed);
        add_pet_color=findViewById(R.id.add_pet_color);
        add_pet_size=findViewById(R.id.add_pet_size);
        add_pet_age=findViewById(R.id.add_pet_age);

        //TextInputLayout
        pet_name_layout=findViewById(R.id.pet_name_layout);
        pet_parent_name_layout=findViewById(R.id.pet_parent_name_layout);
        pet_contact_number_layout=findViewById(R.id.pet_contact_number_layout);
        pet_description_layout=findViewById(R.id.pet_description_layout);
        pet_address_layout=findViewById(R.id.pet_address_layout);

        //TextInputEditText
        pet_name=findViewById(R.id.pet_name);
        pet_parent_name=findViewById(R.id.pet_parent_name);
        pet_contact_number=findViewById(R.id.pet_contact_number);
        pet_description=findViewById(R.id.pet_description);
        pet_address=findViewById(R.id.pet_address);

        //TextView
        peto_reg_number=findViewById(R.id.peto_reg_number);
        calenderView=findViewById(R.id.calenderTextView);
        calenderView.setOnClickListener(this);

        //ImageView
        pet_profile_image=findViewById(R.id.pet_profile_image);
        service_cat_img_one=findViewById(R.id.service_cat_img_one);
        service_cat_img_two=findViewById(R.id.service_cat_img_two);
        service_cat_img_three=findViewById(R.id.service_cat_img_three);
        service_cat_img_four=findViewById(R.id.service_cat_img_four);
        service_cat_img_five=findViewById(R.id.service_cat_img_five);

        //Button
        pet_submit=findViewById(R.id.pet_submit);
        pet_submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pet_submit:
                strPetName= pet_name.getText().toString().trim();
                strPetParentName = pet_parent_name.getText().toString().trim();
                strPetContactNumber = pet_contact_number.getText().toString().trim();
                strPetDescription = pet_description.getText().toString().trim();
                strPetAdress = pet_address.getText().toString().trim();
                strPetBirthDay = calenderView.getText().toString().trim();

                if(strPetName.isEmpty())
                {
                    pet_name.setError("Enter Pet Name");
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                    calenderView.setError(null);
                }
                else if(strPetParentName.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError("Enter Parent Name");
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                    calenderView.setError(null);
                }
                else if(strPetContactNumber.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError("Enter Contact Number");
                    pet_description.setError(null);
                    pet_address.setError(null);
                    calenderView.setError(null);
                }
                else if(strPetDescription.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError("Enter Description");
                    pet_address.setError(null);
                    calenderView.setError(null);
                }
                else if(strPetAdress.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError("Enter Pet Address");
                    calenderView.setError(null);
                }
                else if(strPetBirthDay.isEmpty())
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                    calenderView.setError("Pet YOB");
                }
                //pet size and color.
                else if(strSpnerItemPetNm.isEmpty()||(strSpnerItemPetNm.equals("Select Pet Type")))
                {
                    Toast.makeText(this, "Select Type!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrBreed.isEmpty()||(strSpnrBreed.equals("Pet Breed")))
                {
                    Toast.makeText(this, "Select Breed!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrAge.isEmpty()||(strSpnrAge.equals("Select Pet Age")))
                {
                    Toast.makeText(this, "Select Pet Age!!", Toast.LENGTH_SHORT).show();
                }
                else if(strSpnrSex.isEmpty()||(strSpnrSex.equals("Pet Sex")))
                {
                    Toast.makeText(this, "Select Pet Sex!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    pet_name.setError(null);
                    pet_parent_name.setError(null);
                    pet_contact_number.setError(null);
                    pet_description.setError(null);
                    pet_address.setError(null);
                    calenderView.setError(null);
                    AddPetRequset addPetRequset = new AddPetRequset();
                    AddPetParams data = new AddPetParams();
                    data.setUserId(Config.user_id);
                    data.setPetCategoryId(getStrSpnerItemPetNmId);
                    data.setPetSexId(strSpnrSexId);
                    data.setPetAgeId(strSpnrAgeId);
                    data.setPetSizeId(strSpneSizeId);
                    data.setPetColorId(strSpnrColorId);
                    data.setPetName(strPetName);
                    data.setPetParentName(strPetParentName);
                    data.setContactNumber(strPetContactNumber);
                    data.setDateOfBirth(strPetBirthDay);
                    data.setDescription(strPetDescription);
                    data.setCreateDate(currentDateandTime);
                    data.setPetProfileImageUrl("\\Images\\PetDetailImages\\a706cd07-9d06-44e5-8358-a82a63bd6e81.png");
                    data.setFirstServiceImageUrl("");
                    data.setSecondServiceImageUrl("");
                    data.setThirdServiceImageUrl("");
                    data.setFourthServiceImageUrl("");
                    data.setFifthServiceImageUrl("");
                    addPetRequset.setAddPetParams(data);
                    if(methods.isInternetOn())
                    {
                        addPetData(addPetRequset);
                    }
                    else
                    {
                        methods.DialogInternet();
                    }
                }
            break;

            case R.id.calenderTextView:
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddPetRegister.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calenderView.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            break;

        }

    }

    private void addPetData(AddPetRequset addPetRequset) {

        methods.showCustomProgressBarDialog(this);
        ApiService<AddPetValueResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().addNewPet(Config.token,addPetRequset), "AddPet");
        Log.e("DATALOG","check1=> "+addPetRequset);

    }

    @Override
    public void onResponse(Response arg0, String key) {
        methods.customProgressDismiss();
        switch (key)
        {
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

            case "GeneratePetUniqueId":
                try {
                    Log.d("GeneratePetUniqueId",arg0.body().toString());
                    UniqueResponse uniqueResponse = (UniqueResponse) arg0.body();
                    int responseCode = Integer.parseInt(uniqueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        petUniqueId=uniqueResponse.getData().getPetUniqueId();
                        peto_reg_number.setText(petUniqueId);
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
                    Log.d("AddPet",arg0.body().toString());
                    AddPetValueResponse addPetValueResponse = (AddPetValueResponse) arg0.body();
                    int responseCode = Integer.parseInt(addPetValueResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(this, "Sucessss", Toast.LENGTH_SHORT).show();
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

    private void setPetTypeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_type.setAdapter(aa);
        add_pet_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnerItemPetNm=item;
                Log.d("spnerType",""+strSpnerItemPetNm);
                getStrSpnerItemPetNmId=petTypeHashMap.get(strSpnerItemPetNm);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetBreeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petBreed);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_breed.setAdapter(aa);
        add_pet_breed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrBreed=item;
                Log.d("spnerType",""+strSpnrBreed);
                strSpnrBreedId=petBreedHashMap.get(strSpnrBreed);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetAgeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petAge);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_age.setAdapter(aa);
        add_pet_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrAge=item;
                Log.d("spnerType",""+strSpnrAge);
                strSpnrAgeId=petAgeHashMap.get(strSpnrAge);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetColorSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petColor);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_color.setAdapter(aa);
        add_pet_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrColor=item;
                Log.d("spnerType",""+strSpnrColor);
                strSpnrColorId=petColorHashMap.get(strSpnrColor);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setPetSizeSpinner() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petSize);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_size.setAdapter(aa);
        add_pet_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrSize=item;
                Log.d("spnerType",""+strSpnrSize);
                strSpneSizeId=petSizeHashMap.get(strSpnrSize);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setSpinnerPetSex() {
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,petSex);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        add_pet_sex.setAdapter(aa);
        add_pet_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                // Showing selected spinner item
                strSpnrSex=item;
                Log.d("spnerType",""+strSpnrSex);
                strSpnrSexId=petSexHashMap.get(strSpnrSex);
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}