package com.cynoteck.petofyvet.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.adapters.PetParentAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.appointmentParams.CreateAppointParams;
import com.cynoteck.petofyvet.params.appointmentParams.CreateAppointRequest;
import com.cynoteck.petofyvet.params.appointmentParams.UpdateAppointmentParams;
import com.cynoteck.petofyvet.params.appointmentParams.UpdateAppointmentRequest;
import com.cynoteck.petofyvet.params.getPetListRequest.GetPetListParams;
import com.cynoteck.petofyvet.params.getPetListRequest.GetPetListRequest;
import com.cynoteck.petofyvet.response.appointmentResponse.AppointmentDetailsResponse;
import com.cynoteck.petofyvet.response.appointmentResponse.CreateAppointmentResponse;
import com.cynoteck.petofyvet.response.getPetParentResponse.GetPetParentListData;
import com.cynoteck.petofyvet.response.getPetParentResponse.GetPetParentResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.cynoteck.petofyvet.utils.RegisterRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Response;

public class AddUpdateAppointmentActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener, RegisterRecyclerViewClickListener, TextWatcher {
    Button create_appointment_BT;
    ImageView appointment_headline_back;
    TextView appointment_headline ,calenderTextViewAppointDt ,time_TV;
    EditText title_ET, duration_TV, description_ET;
    TextView pet_parent_TV;
    Methods methods;
    ArrayList<GetPetParentListData> petParent = new ArrayList<>();
    PetParentAdapter petParentAdapter;
    Dialog petParentDilog;
    String id="",appointmentID="",userID="", type="", titleString="",descriptionString="",dateString="",timeString ="",durationString="",petParentString="";
    DatePickerDialog picker;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_appointment);
        methods = new Methods(this);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        id = intent.getStringExtra("id");
        init();
        if (type.equals("add")){
            appointment_headline.setText("Add Appointment");
            create_appointment_BT.setText("Create Appointment");
        }else if (type.equals("update")){
            appointment_headline.setText("Edit Appointment");
            create_appointment_BT.setText("Update Appointment");
            GetPetListParams  getPetListParams = new GetPetListParams();
            getPetListParams.setId(id);
            GetPetListRequest getPetListRequest = new GetPetListRequest();
            getPetListRequest.setData(getPetListParams);
            getAppointmentDeatils(getPetListRequest);
        }

        if (methods.isInternetOn()){
            getPetParent();
        }else {

        }



    }


    private void init() {
        appointment_headline_back = findViewById(R.id.appointment_headline_back);
        appointment_headline = findViewById(R.id.appointment_headline);
        calenderTextViewAppointDt = findViewById(R.id.calenderTextViewAppointDt);
        time_TV = findViewById(R.id.time_TV);
        title_ET = findViewById(R.id.title_ET);
        duration_TV = findViewById(R.id.duration_TV);
        description_ET = findViewById(R.id.description_ET);
        pet_parent_TV = findViewById(R.id.pet_parent_ACTV);
        create_appointment_BT=findViewById(R.id.create_appointment_BT);

        appointment_headline_back.setOnClickListener(this);
        create_appointment_BT.setOnClickListener(this);
        calenderTextViewAppointDt.setOnClickListener(this);
        time_TV.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.appointment_headline_back:
                onBackPressed();
                break;


            case R.id.pet_parent_ACTV:
                openParentDiloag();
                break;

            case R.id.create_appointment_BT:
                titleString = title_ET.getText().toString();
                petParentString =pet_parent_TV.getText().toString();
                dateString = calenderTextViewAppointDt.getText().toString();
                timeString = time_TV.getText().toString();
                durationString=duration_TV.getText().toString();
                descriptionString=description_ET.getText().toString();

                if (titleString.isEmpty()){
                    title_ET.setError("Enter Title !");
                    description_ET.setError(null);

                }else if (petParentString.isEmpty()){
                    Toast.makeText(this, "Please Select Pet Parent", Toast.LENGTH_SHORT).show();

                }else if (descriptionString.isEmpty()){
                    title_ET.setError(null);
                    description_ET.setError("Write Description");

                }else if (dateString.isEmpty()){
                    Toast.makeText(this, "Please Select Date", Toast.LENGTH_SHORT).show();

                }else if (timeString.isEmpty()){
                    Toast.makeText(this, "Please Select Time", Toast.LENGTH_SHORT).show();

                }else if (durationString.isEmpty()){
                    Toast.makeText(this, "Please Select Duration", Toast.LENGTH_SHORT).show();

                }else {

                    if (type.equals("add")){
                        CreateAppointParams createAppointParams = new CreateAppointParams();
                        createAppointParams.setUserId(userID);
                        createAppointParams.setDescription(descriptionString);
                        createAppointParams.setDuration(durationString);
                        createAppointParams.setEventStartDate(dateString);
                        createAppointParams.setEventStartTime(timeString);
                        createAppointParams.setTitle(titleString);
                        CreateAppointRequest createAppointRequest = new CreateAppointRequest();
                        createAppointRequest.setData(createAppointParams);
                        if (methods.isInternetOn()){
                            createUpdateAppointment(createAppointRequest);

                        }else {
                            methods.DialogInternet();
                        }
                    }else {
                        UpdateAppointmentParams updateAppointmentParams = new UpdateAppointmentParams();
                        updateAppointmentParams.setUserId(userID);
                        updateAppointmentParams.setDescription(descriptionString);
                        updateAppointmentParams.setDuration(durationString);
                        updateAppointmentParams.setEventStartDate(dateString);
                        updateAppointmentParams.setEventStartTime(timeString);
                        updateAppointmentParams.setTitle(titleString);
                        updateAppointmentParams.setId(id);
                        UpdateAppointmentRequest  updateAppointmentRequest = new UpdateAppointmentRequest();
                        updateAppointmentRequest.setData(updateAppointmentParams);
                        if (methods.isInternetOn()){
                            Log.d("updateRequest",updateAppointmentRequest.toString());
                            updateAppointment(updateAppointmentRequest);

                        }else {
                            methods.DialogInternet();
                        }

                    }
                }

                break;


            case R.id.calenderTextViewAppointDt:

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                                String displayValue = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                                calenderTextViewAppointDt.setText(Config.changeDateFormat(displayValue));
                            }
                        }, year, month, day);
                picker.show();
                break;


            case R.id.time_TV:
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                String displayValue = hourOfDay+":"+ minute;
                                time_TV.setText(Config.changeTimePicker(displayValue));
                                timeString = Config.changeTimePicker(displayValue);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();

                break;



        }

    }
    private void getAppointmentDeatils(GetPetListRequest id) {
        ApiService<AppointmentDetailsResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getAppointmentsDetails(Config.token,id), "GetAppointmentDetails");

    }

    private void getPetParent() {
        ApiService<GetPetParentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getPetParentList(Config.token), "GetPetParent");
    }

    private void updateAppointment(UpdateAppointmentRequest updateAppointmentRequest) {
        ApiService<CreateAppointmentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().updateAppointment(Config.token,updateAppointmentRequest), "UpdateAppointment");

    }

    private void createUpdateAppointment(CreateAppointRequest createAppointRequest) {
        Log.d("create",createAppointRequest.toString());
        ApiService<CreateAppointmentResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().createAppointment(Config.token,createAppointRequest), "CreateAppointment");
    }

    private void openParentDiloag() {

        petParentDilog = new Dialog(this);
        petParentDilog.setContentView(R.layout.pet_parent_dilog);
        RecyclerView pet_parent_list_recycler=petParentDilog.findViewById(R.id.pet_parent_list_recycler);
        EditText ed_parent=petParentDilog.findViewById(R.id.ed_parent);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pet_parent_list_recycler.setLayoutManager(layoutManager);
        petParentAdapter  = new PetParentAdapter(this,petParent,this);
        pet_parent_list_recycler.setAdapter(petParentAdapter);
        petParentAdapter.notifyDataSetChanged();

        ed_parent.addTextChangedListener(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = petParentDilog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        petParentDilog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        petParentDilog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        petParentDilog.show();


    }

    @Override
    public void onResponse(Response arg0, String key) {

        switch (key){

            case "GetPetParent":
                try {
                    Log.d("GetPetParent",arg0.body().toString());
                    GetPetParentResponse getPetParentResponse = (GetPetParentResponse) arg0.body();
                    Log.d("GetPetParent",getPetParentResponse.toString());
                    int responseCode = Integer.parseInt(getPetParentResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        petParent = getPetParentResponse.getData();
                        pet_parent_TV.setOnClickListener(this);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

            case "UpdateAppointment":
                try {
                    Log.d("updateAppoint",arg0.body().toString());
                    CreateAppointmentResponse createAppointmentResponse  = (CreateAppointmentResponse) arg0.body();
                    Log.d("updateAppoint",createAppointmentResponse.toString());
                    int responseCode = Integer.parseInt(createAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode==109) {

                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                        Config.backCall="hit";
                        onBackPressed();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;


            case "CreateAppointment":
                try {
                    Log.d("createAppoint",arg0.body().toString());
                    CreateAppointmentResponse createAppointmentResponse  = (CreateAppointmentResponse) arg0.body();
                    Log.d("createAppoint",createAppointmentResponse.toString());
                    int responseCode = Integer.parseInt(createAppointmentResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                        Config.backCall="hit";
                        onBackPressed();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;



            case "GetAppointmentDetails":
                try {
                    Log.d("AppointDeatils",arg0.body().toString());
                    AppointmentDetailsResponse appointmentDetailsResponse  = (AppointmentDetailsResponse) arg0.body();
                    Log.d("AppointDeatils",appointmentDetailsResponse.toString());
                    int responseCode = Integer.parseInt(appointmentDetailsResponse.getResponse().getResponseCode());
                    if (responseCode==109) {
                        userID = appointmentDetailsResponse.getData().getUserId();
                        pet_parent_TV.setText(appointmentDetailsResponse.getData().getPetParent().getFullName());
                        title_ET.setText(appointmentDetailsResponse.getData().getTitle());
                        dateString = appointmentDetailsResponse.getData().getEventStartDate().substring(0,10);
                        Log.d("time",dateString);
                        calenderTextViewAppointDt.setText(Config.changeDateFormat(appointmentDetailsResponse.getData().getEventStartDate().substring(0,10)));
                        description_ET.setText(appointmentDetailsResponse.getData().getDescription());
                        duration_TV.setText(appointmentDetailsResponse.getData().getDuration());

                        timeString = appointmentDetailsResponse.getData().getEventStartDate().substring(appointmentDetailsResponse.getData().getEventStartDate().length()-8,appointmentDetailsResponse.getData().getEventStartDate().length());
                        Log.d("datee",timeString);
                        Log.d("datee",Config.changeTimeFormat(timeString));
                        time_TV.setText(Config.changeTimeFormat(timeString));


                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        petParentAdapter.getFilter().filter(s.toString());

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onProductClick(int position) {
        petParentDilog.dismiss();
        userID=petParent.get(position).getUserId();
        pet_parent_TV.setText(petParent.get(position).getFirstName()+" "+petParent.get(position).getLastName());


    }
}
