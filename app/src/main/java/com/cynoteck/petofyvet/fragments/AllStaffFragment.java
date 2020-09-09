package com.cynoteck.petofyvet.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.StaffPermissionActivity;
import com.cynoteck.petofyvet.adapters.AllStaffAdapter;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.allStaffRequest.AddStaffParams;
import com.cynoteck.petofyvet.params.allStaffRequest.AddStaffRequest;
import com.cynoteck.petofyvet.params.allStaffRequest.ChangeStaffStatusParams;
import com.cynoteck.petofyvet.params.allStaffRequest.ChangeStaffStatusRequest;
import com.cynoteck.petofyvet.params.allStaffRequest.UpdateStaffParams;
import com.cynoteck.petofyvet.params.allStaffRequest.UpdateStaffRequest;
import com.cynoteck.petofyvet.response.getStaffResponse.GetAllStaffData;
import com.cynoteck.petofyvet.response.getStaffResponse.GetAllStaffResponse;
import com.cynoteck.petofyvet.response.getStaffResponse.GetStaffDetailsResponse;
import com.cynoteck.petofyvet.response.getStaffResponse.GetStaffStatusResponse;
import com.cynoteck.petofyvet.response.getStaffResponse.GetUpdateStaffResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.cynoteck.petofyvet.utils.StaffListClickListener;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import retrofit2.Response;

public class AllStaffFragment extends Fragment implements ApiResponse, StaffListClickListener,View.OnClickListener {
    RecyclerView all_staff_RV;
    View view;
    Methods methods;
    AllStaffAdapter allStaffAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    ArrayList<GetAllStaffData> getAllStaffData;
    ImageView add_staff_IV;
    Dialog add_staff_dialog, edit_staff_dialog;
    String email_id, first_name, last_name, password, confirm_password, phone_number,encrypt_id;
    EditText nameET,lastET,emailET,mobileET,passwordET,confirmPassET;
    TextView staff_permission;
    Button cancel_button, submit_button, update_button,update_cancel_button;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public AllStaffFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_staff, container, false);
        init();

        return  view;
    }

    private void init() {
        methods = new Methods(getContext());
        staff_permission = view.findViewById(R.id.staff_permission);
        all_staff_RV = view.findViewById(R.id.all_staff_List_RV);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        add_staff_IV=view.findViewById(R.id.add_staff_IV);
        add_staff_IV.setOnClickListener(this);
        staff_permission.setOnClickListener(this);

        if (methods.isInternetOn()){
            getAllStaff();
        }else {
            methods.DialogInternet();
        }
    }

    @Override
    public void onViewDetailsClick(int position) {
        Log.d("staff_details",getAllStaffData.toString());
        encrypt_id = getAllStaffData.get(position).getEncryptedId();
        String staff_first_name = getAllStaffData.get(position).getFirstName();
        String staff_last_name = getAllStaffData.get(position).getLastName();
        String staff_email_id = getAllStaffData.get(position).getEmail();
        String staff_phone = getAllStaffData.get(position).getPhoneNumber();
        showEditStaffDialog(staff_first_name,staff_last_name,staff_email_id,staff_phone);

    }


    @Override
    public void onStausClick(int position, Button button) {
        String status = getAllStaffData.get(position).getIsActive();
        String post_status ="";
        String encrtpty_id = getAllStaffData.get(position).getEncryptedId();
        if (status.equals("true")){
            getAllStaffData.get(position).setIsActive("false");
            button.setBackgroundResource(R.drawable.deactivate_status);
            button.setText("Deactivated");
            post_status = "false";
        }else {
            getAllStaffData.get(position).setIsActive("true");
            button.setBackgroundResource(R.drawable.activated_status);
            button.setText("Activated");
            post_status = "true";
        }

        ChangeStaffStatusParams changeStaffStatusParams = new ChangeStaffStatusParams();
        changeStaffStatusParams.setEncryptedId(encrtpty_id);
        changeStaffStatusParams.setStatus(post_status);
        ChangeStaffStatusRequest changeStaffStatusRequest = new ChangeStaffStatusRequest();
        changeStaffStatusRequest.setData(changeStaffStatusParams);
        ApiService<GetStaffStatusResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getStaffStatus(Config.token,changeStaffStatusRequest), "GetStaffStatus");
        Log.e("DATALOG","=> "+changeStaffStatusRequest);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.add_staff_IV:

                showAddStaffDilaog();

                break;

            case R.id.cancel_button:

                add_staff_dialog.dismiss();
                break;

            case R.id.update_cancel_button:
                edit_staff_dialog.dismiss();
                break;

            case R.id.submit_button:
                first_name = nameET.getText().toString().trim();
                last_name = lastET.getText().toString().trim();
                email_id = emailET.getText().toString().trim();
                phone_number = mobileET.getText().toString().trim();
                password = passwordET.getText().toString().trim();
                confirm_password = confirmPassET.getText().toString().trim();

                Log.d("details",""+first_name+last_name+email_id+phone_number+password+confirm_password);
                if (first_name.isEmpty()){
                    nameET.setError("Name is empty");
                    lastET.setError(null);
                    emailET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (last_name.isEmpty()){
                    lastET.setError("Name is empty");
                    nameET.setError(null);
                    emailET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (email_id.isEmpty()){
                    emailET.setError("Email is empty");
                    lastET.setError(null);
                    nameET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (!email_id.matches(emailPattern)) {
                    emailET.setError("Invalid Email");
                    lastET.setError(null);
                    nameET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (phone_number.isEmpty()){
                    mobileET.setError("Phone number is empty");
                    lastET.setError(null);
                    emailET.setError(null);
                    nameET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (phone_number.length()<10){
                    mobileET.setError("Invalid number");
                    lastET.setError(null);
                    emailET.setError(null);
                    nameET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (password.isEmpty()){
                    passwordET.setError("Password is empty");
                    lastET.setError(null);
                    emailET.setError(null);
                    mobileET.setError(null);
                    nameET.setError(null);
                    confirmPassET.setError(null);
                }else if (confirm_password.isEmpty()){
                    confirmPassET.setError("Password is empty");
                    lastET.setError(null);
                    emailET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    nameET.setError(null);
                }else if (!passwordET.getText().toString().equals(confirmPassET.getText().toString())){
                    confirmPassET.setError("Password is not matched ");
                    lastET.setError(null);
                    emailET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    nameET.setError(null);
                }else if (password.length()<6){
                    passwordET.setError("Password is to short");
                    lastET.setError(null);
                    emailET.setError(null);
                    mobileET.setError(null);
                    confirmPassET.setError(null);
                    nameET.setError(null);
                }else {
                    if (methods.isInternetOn()) {
                        addStaff();
                        add_staff_dialog.dismiss();
                        add_staff_dialog.dismiss();
                        all_staff_RV.setVisibility(View.GONE);
                        mShimmerViewContainer.startShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.VISIBLE);

                    } else {
                        methods.DialogInternet();
                    }
                }


                break;


            case R.id.update_button:
                first_name = nameET.getText().toString().trim();
                last_name = lastET.getText().toString().trim();
                email_id = emailET.getText().toString().trim();
                phone_number = mobileET.getText().toString().trim();
                Log.d("details",""+first_name+last_name+email_id+phone_number+password+confirm_password);
                if (first_name.isEmpty()){
                    nameET.setError("Name is empty");
                    lastET.setError(null);
                    emailET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (last_name.isEmpty()){
                    lastET.setError("Name is empty");
                    nameET.setError(null);
                    emailET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (email_id.isEmpty()){
                    emailET.setError("Email is empty");
                    lastET.setError(null);
                    nameET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (!email_id.matches(emailPattern)) {
                    emailET.setError("Invalid Email");
                    lastET.setError(null);
                    nameET.setError(null);
                    mobileET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (phone_number.isEmpty()){
                    mobileET.setError("Phone number is empty");
                    lastET.setError(null);
                    emailET.setError(null);
                    nameET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else if (phone_number.length()<10){
                    mobileET.setError("Invalid number");
                    lastET.setError(null);
                    emailET.setError(null);
                    nameET.setError(null);
                    passwordET.setError(null);
                    confirmPassET.setError(null);
                }else {
                    if (methods.isInternetOn()) {
                        updateStaff();
                        edit_staff_dialog.dismiss();
                        all_staff_RV.setVisibility(View.GONE);
                        mShimmerViewContainer.startShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.VISIBLE);

                    } else {
                        methods.DialogInternet();
                    }
                }
                break;

            case R.id.staff_permission:
                startActivity(new Intent(getActivity(), StaffPermissionActivity.class));
                break;
        }

    }

    private void getAllStaff() {
        ApiService<GetAllStaffResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().getAllStaff(Config.token), "GetAllStaff");

    }

    private void updateStaff() {
        UpdateStaffParams updateStaffParams = new UpdateStaffParams();
        updateStaffParams.setEncryptedId(encrypt_id);
        updateStaffParams.setFirstName(first_name);
        updateStaffParams.setLastName(last_name);
        updateStaffParams.setEmail(email_id);
        updateStaffParams.setPhoneNumber(phone_number);
        UpdateStaffRequest updateStaffRequest = new UpdateStaffRequest();
        updateStaffRequest.setData(updateStaffParams);
        ApiService<GetUpdateStaffResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().updateStaff(Config.token, updateStaffRequest), "UpdateStaff");
        Log.d("updateStaff",updateStaffRequest.toString());

    }

    private void addStaff() {
        AddStaffParams addStaffParams = new AddStaffParams();
        addStaffParams.setFirstName(first_name);
        addStaffParams.setLastName(last_name);
        addStaffParams.setEmail(email_id);
        addStaffParams.setPhoneNumber(phone_number);
        addStaffParams.setPassword(password);
        addStaffParams.setConfirmPassword(confirm_password);
        AddStaffRequest addStaffRequest = new AddStaffRequest();
        addStaffRequest.setData(addStaffParams);
        ApiService<GetStaffDetailsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().addNewStaff(Config.token, addStaffRequest), "AddStaff");

    }

    private void showAddStaffDilaog() {
        add_staff_dialog = new Dialog(getContext());
        add_staff_dialog.setContentView(R.layout.add_staff_dilog);

        nameET=add_staff_dialog.findViewById(R.id.first_nameET);
        lastET=add_staff_dialog.findViewById(R.id.last_nameET);
        emailET=add_staff_dialog.findViewById(R.id.emailET);
        mobileET=add_staff_dialog.findViewById(R.id.mobileET);
        passwordET=add_staff_dialog.findViewById(R.id.passwordET);
        confirmPassET=add_staff_dialog.findViewById(R.id.confirm_passET);
        cancel_button = add_staff_dialog.findViewById(R.id.cancel_button);
        submit_button=add_staff_dialog.findViewById(R.id.submit_button);
        cancel_button.setOnClickListener(this);
        submit_button.setOnClickListener(this);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = add_staff_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        add_staff_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        add_staff_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        add_staff_dialog.show();


    }

    private void showEditStaffDialog(String staff_first_name, String staff_last_name, String staff_email_id, String staff_phone) {

        edit_staff_dialog = new Dialog(getContext());
        edit_staff_dialog.setContentView(R.layout.edit_staff_dialog);

        nameET=edit_staff_dialog.findViewById(R.id.first_nameET);
        lastET=edit_staff_dialog.findViewById(R.id.last_nameET);
        emailET=edit_staff_dialog.findViewById(R.id.emailET);
        mobileET=edit_staff_dialog.findViewById(R.id.mobileET);

        nameET.setText(staff_first_name);
        lastET.setText(staff_last_name);
        emailET.setText(staff_email_id);
        mobileET.setText(staff_phone);

//        passwordET=edit_staff_dialog.findViewById(R.id.passwordET);
//        confirmPassET=edit_staff_dialog.findViewById(R.id.confirm_passET);
        update_cancel_button = edit_staff_dialog.findViewById(R.id.update_cancel_button);
        update_button=edit_staff_dialog.findViewById(R.id.update_button);
        update_cancel_button.setOnClickListener(this);
        update_button.setOnClickListener(this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = edit_staff_dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        edit_staff_dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        edit_staff_dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        edit_staff_dialog.show();

    }

    @Override
    public void onResponse(Response response, String key) {
        Log.e("DATALOG","=> "+response.body());

        switch (key){
            case "GetAllStaff":
                try {
                    GetAllStaffResponse getAllStaffResponse = (GetAllStaffResponse) response.body();
                    Log.d("DATALOG", getAllStaffResponse.toString());
                    int responseCode = Integer.parseInt(getAllStaffResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        all_staff_RV.setLayoutManager(linearLayoutManager);
                        allStaffAdapter  = new AllStaffAdapter(getContext(),getAllStaffResponse.getData(),this);
                        all_staff_RV.setAdapter(allStaffAdapter);
                        getAllStaffData = getAllStaffResponse.getData();
                        allStaffAdapter.notifyDataSetChanged();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        mShimmerViewContainer.stopShimmerAnimation();
                    }

                }
                catch(Exception e) {


                    e.printStackTrace();
                }

                break;

            case "GetStaffStatus":
                try {
                    GetStaffStatusResponse getStaffStatusResponse = (GetStaffStatusResponse) response.body();
                    Log.d("DATALOG-1", getStaffStatusResponse.toString());
                    int responseCode = Integer.parseInt(getStaffStatusResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        Toast.makeText(getContext(), "Status Changed Succesfully", Toast.LENGTH_SHORT).show();
                    }

                }
                catch(Exception e) {


                    e.printStackTrace();
                }

                break;



            case "AddStaff":
                try {
                    GetStaffDetailsResponse getStaffDetailsResponse = (GetStaffDetailsResponse) response.body();
                    Log.d("DATALOG-1", getStaffDetailsResponse.toString());
                    int responseCode = Integer.parseInt(getStaffDetailsResponse.getResponse().getResponseCode());

                    if (responseCode== 109){
                        getAllStaff();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        all_staff_RV.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Staff Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }

                break;
            case "UpdateStaff":
                try {
                    GetUpdateStaffResponse getUpdateStaffResponse = (GetUpdateStaffResponse) response.body();
                    Log.d("DATALOG-2", getUpdateStaffResponse.toString());
                    int responseCode = Integer.parseInt(getUpdateStaffResponse.getResponse().getResponseCode());


                    if (responseCode== 109){
                        getAllStaff();
                        mShimmerViewContainer.setVisibility(View.GONE);
                        all_staff_RV.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Staff Updated Successfully", Toast.LENGTH_SHORT).show();
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
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}
