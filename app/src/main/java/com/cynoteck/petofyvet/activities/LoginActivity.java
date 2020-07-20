package com.cynoteck.petofyvet.activities;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.loginparams.Data;
import com.cynoteck.petofyvet.params.loginparams.Loginparams;
import com.cynoteck.petofyvet.response.loginresponse.LoginResponse;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Response;

public class LoginActivity extends FragmentActivity implements View.OnClickListener, ApiResponse {

    boolean doubleBackToExitPressedOnce = false;
    private TextInputLayout email_TIL, password_TIL;
    private TextInputEditText email_TIET, password_TIET;
    private Button login_BT;
    private String emailString="", passwordString="";
    private TextView signUp_TV, forgetPass_TV;
    String emailPattern = "[a-zA-Z._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        email_TIET = findViewById(R.id.email_TIET);
        password_TIET = findViewById(R.id.password_TIET);
        email_TIL= findViewById(R.id.email_TIL);
        password_TIL=findViewById(R.id.password_TIL);
        login_BT=findViewById(R.id.login_BT);
        signUp_TV = findViewById(R.id.signUp_TV);
        forgetPass_TV=findViewById(R.id.forgetPass_TV);
        signUp_TV.setOnClickListener(this);
        forgetPass_TV.setOnClickListener(this);
        login_BT.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.login_BT:
                emailString = email_TIET.getText().toString().trim();
                passwordString=password_TIET.getText().toString().trim();
                if ( emailString.isEmpty()){
                    email_TIL.setError("Email is empty");
                    password_TIL.setError(null);
                }else if (passwordString.isEmpty()){
                    password_TIL.setError("Password is empty");
                    email_TIL.setError(null);
                }else {
                    email_TIL.setError(null);
                    password_TIL.setError(null);
                    Loginparams loginparams = new Loginparams();
                    Data data = new Data();
                    data.setEmail(emailString);
                    data.setPassword(passwordString);
                    loginparams.setLoginData(data);
                    loginUser(loginparams);

                }
                break;

            case R.id.signUp_TV:
                Intent signUP_intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(signUP_intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

        }

    }

    private void loginUser(Loginparams loginparams) {

        ApiService<LoginResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().loginApi(loginparams), "Login");
        Log.e("DATALOG","check1=> "+loginparams);

    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key)
        {
            case "Login":

                try {
                    Log.d("DATALOG",response.body().toString());
                    LoginResponse responseLogin = (LoginResponse) response.body();
                    int responseCode = Integer.parseInt(responseLogin.getResponseLogin().getResponseCode());
                    if (responseCode== 109){
                        Toast.makeText(LoginActivity.this, responseLogin.getResponseLogin().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else if (responseCode==614){
                        Toast.makeText(LoginActivity.this, responseLogin.getResponseLogin().getResponseMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
             finishAffinity();
            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
