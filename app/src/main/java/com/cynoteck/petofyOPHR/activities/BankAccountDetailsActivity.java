package com.cynoteck.petofyOPHR.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.addBankAccountParams.AddBankAccountParams;
import com.cynoteck.petofyOPHR.params.addBankAccountParams.AddBankAccountRequest;
import com.cynoteck.petofyOPHR.params.addBankAccountParams.ValidateIfscParams;
import com.cynoteck.petofyOPHR.params.addBankAccountParams.ValidateIfscRequest;
import com.cynoteck.petofyOPHR.response.bankAccountResponse.AddBankAccountResponse;
import com.cynoteck.petofyOPHR.response.bankAccountResponse.ValidateIfscCodeResponse;
import com.cynoteck.petofyOPHR.utils.Config;

import retrofit2.Response;

public class BankAccountDetailsActivity extends AppCompatActivity implements ApiResponse,View.OnClickListener {
    EditText name_for_bank_ET, email_for_bank_ET, contact_for_bank_ET, ifsc_for_bank_ET, account_for_bank_ET,confirm_account_for_bank_ET;
    TextView ifsc_for_bank_details_TV;
    Button add_account_BT;
    ImageView back_arrow_IV;
    ProgressBar progressBar;
    String name_for_bank_str, email_for_bank_str, contact_for_bank_str,account_no_str, confirm_account_no_str, ifsc_str;
    String finalIfsc="";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account_details);

        initization();
        ifsc_for_bank_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (ifsc_for_bank_ET.getText().toString().trim().length()==11){
                    progressBar.setVisibility(View.VISIBLE);
                    validateIfscCode(ifsc_for_bank_ET.getText().toString().trim());
                }else {
                    progressBar.setVisibility(View.GONE);
                    ifsc_for_bank_details_TV.setVisibility(View.INVISIBLE);
                }
            }
        });
        account_for_bank_ET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
               account_for_bank_ET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        });
    }

    private void validateIfscCode(String ifscCode) {
        ValidateIfscParams validateIfscParams = new ValidateIfscParams();
        validateIfscParams.setIfscCode(ifscCode);
        ValidateIfscRequest validateIfscRequest = new ValidateIfscRequest();
        validateIfscRequest.setData(validateIfscParams);

        ApiService<ValidateIfscCodeResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().validateIfscCode(Config.token, validateIfscRequest), "ValidateIfsc");
        Log.d("ifscCode", "parameter" + validateIfscRequest);


    }

    private void initization() {
        name_for_bank_ET = findViewById(R.id.name_for_bank_ET);
        email_for_bank_ET = findViewById(R.id.email_for_bank_ET);
        contact_for_bank_ET = findViewById(R.id.contact_for_bank_ET);
        ifsc_for_bank_ET = findViewById(R.id.ifsc_for_bank_ET);
        account_for_bank_ET = findViewById(R.id.account_for_bank_ET);
        confirm_account_for_bank_ET = findViewById(R.id.confirm_account_for_bank_ET);
        ifsc_for_bank_details_TV = findViewById(R.id.ifsc_for_bank_details_TV);
        add_account_BT = findViewById(R.id.add_account_BT);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);
        progressBar=findViewById(R.id.progressBar);

        back_arrow_IV.setOnClickListener(this);
        add_account_BT.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onResponse(Response arg0, String key) {
        switch (key){
            case "ValidateIfsc":
                try {
                    ValidateIfscCodeResponse validateIfscCodeResponse  = (ValidateIfscCodeResponse) arg0.body();
                    Log.d("ValidateIfsc", validateIfscCodeResponse.toString());
                    int responseCode = Integer.parseInt(validateIfscCodeResponse.getResponse().getResponseCode());
                    progressBar.setVisibility(View.GONE);
                    if (responseCode == 109) {
                        if (validateIfscCodeResponse.getData().getIsValid().equals("true")){
                            finalIfsc = validateIfscCodeResponse.getData().getIfsc();
                            ifsc_for_bank_details_TV.setVisibility(View.VISIBLE);
                            ifsc_for_bank_details_TV.setTextColor(R.color.colorAccent);
                            ifsc_for_bank_details_TV.setText(validateIfscCodeResponse.getData().getBank()+", "+validateIfscCodeResponse.getData().getDistrict()+", "+validateIfscCodeResponse.getData().getState());
                        }
                    } else if (responseCode == 110) {
                        ifsc_for_bank_details_TV.setVisibility(View.INVISIBLE);
                        ifsc_for_bank_details_TV.setError(validateIfscCodeResponse.getResponse().getResponseMessage());
                    } else if (responseCode == 614) {
                        Toast.makeText(this, validateIfscCodeResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case "AddAccount":
                try {
                    AddBankAccountResponse addBankAccountResponse   = (AddBankAccountResponse) arg0.body();
                    Log.d("addAccount", addBankAccountResponse.toString());
                    int responseCode = Integer.parseInt(addBankAccountResponse.getResponse().getResponseCode());
                    if (responseCode == 109) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                        Toast.makeText(this, "Account Added Succesfully ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.add_account_BT:
                name_for_bank_str = name_for_bank_ET.getText().toString().trim();
                contact_for_bank_str = contact_for_bank_ET.getText().toString().trim();
                email_for_bank_str = email_for_bank_ET.getText().toString().trim();
                account_no_str = account_for_bank_ET.getText().toString().trim();
                confirm_account_no_str = confirm_account_for_bank_ET.getText().toString().trim();
                ifsc_str = ifsc_for_bank_ET.getText().toString().toUpperCase().trim();

                if (name_for_bank_str.isEmpty()){
                    name_for_bank_ET.setError("Name is Empty !");
                    contact_for_bank_ET.setError(null);
                    email_for_bank_ET.setError(null);
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError(null);
                    ifsc_for_bank_ET.setError(null);

                }else if (contact_for_bank_str.isEmpty()){
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError("Number Is Empty !");
                    email_for_bank_ET.setError(null);
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError(null);
                    ifsc_for_bank_ET.setError(null);

                }else if (email_for_bank_str.isEmpty()){
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError(null);
                    email_for_bank_ET.setError("Email is Empty !");
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError(null);
                    ifsc_for_bank_ET.setError(null);

                }else if (account_no_str.isEmpty()){
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError(null);
                    email_for_bank_ET.setError(null);
                    account_for_bank_ET.setError("Account No. is Empty !");
                    confirm_account_for_bank_ET.setError(null);
                    ifsc_for_bank_ET.setError(null);

                }else if (ifsc_str.isEmpty()){
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError(null);
                    email_for_bank_ET.setError(null);
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError(null);
                    ifsc_for_bank_ET.setError("IFSC code is Empty !");

                }else if (confirm_account_no_str.isEmpty()){
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError(null);
                    email_for_bank_ET.setError(null);
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError("Account No. is Empty !");
                    ifsc_for_bank_ET.setError(null);

                }else if (!email_for_bank_str.matches(emailPattern)) {
                    email_for_bank_ET.setError("Invalid Email");
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError(null);
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError(null);
                    ifsc_for_bank_ET.setError(null);
                }else if (!account_for_bank_ET.getText().toString().equals(confirm_account_for_bank_ET.getText().toString())){
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError(null);
                    email_for_bank_ET.setError(null);
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError("Account No. do not match!");
                    ifsc_for_bank_ET.setError(null);
                }else if (contact_for_bank_str.length()<10){
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError("Invalid Number !");
                    email_for_bank_ET.setError(null);
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError(null);
                    ifsc_for_bank_ET.setError(null);
                }else if (!finalIfsc.equals(ifsc_for_bank_ET.getText().toString())) {
                    name_for_bank_ET.setError(null);
                    contact_for_bank_ET.setError(null);
                    email_for_bank_ET.setError(null);
                    account_for_bank_ET.setError(null);
                    confirm_account_for_bank_ET.setError(null);
                    ifsc_for_bank_ET.setError("Invalid Ifsc Code !");
                }else {
                    AddBankAccountParams addBankAccountParams = new AddBankAccountParams();
                    addBankAccountParams.setName(name_for_bank_str);
                    addBankAccountParams.setEmail(email_for_bank_str);
                    addBankAccountParams.setIfsc(finalIfsc);
                    addBankAccountParams.setAccountNumber(confirm_account_no_str);
                    addBankAccountParams.setContact(contact_for_bank_str);
                    AddBankAccountRequest addBankAccountRequest = new AddBankAccountRequest();
                    addBankAccountRequest.setData(addBankAccountParams);

                    ApiService<AddBankAccountResponse> service = new ApiService<>();
                    service.get(this, ApiClient.getApiInterface().addBankAccount(Config.token, addBankAccountRequest), "AddAccount");
                    Log.d("AddAccount", "parameter" + addBankAccountRequest);

                }

                break;
        }


    }
}
