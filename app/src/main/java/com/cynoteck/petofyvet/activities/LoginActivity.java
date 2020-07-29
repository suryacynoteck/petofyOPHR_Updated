package com.cynoteck.petofyvet.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.loginRequest.LoginRequest;
import com.cynoteck.petofyvet.params.loginRequest.Loginparams;
import com.cynoteck.petofyvet.response.loginRegisterResponse.LoginRegisterResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Response;

public class LoginActivity extends FragmentActivity implements View.OnClickListener, ApiResponse {

    boolean doubleBackToExitPressedOnce = false;
    private TextInputLayout email_TIL, password_TIL;
    private EditText email_TIET, password_TIET;
    private Button login_BT;
    private String emailString="", passwordString="";
    private TextView signUp_TV, forgetPass_TV;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Methods methods;
    ImageView logoVet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        methods = new Methods(this);

        init();
        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        String loggedIn = sharedPreferences.getString("loggedIn", "");
        if (loggedIn.equals("loggedIn")){
            Intent intent = new Intent(this,DashBoardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        }
    }

    private void init() {

        logoVet=findViewById(R.id.logoVet);
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
                    email_TIET.setError("Email is empty");
                    password_TIET.setError(null);
                }else if (!emailString.matches(emailPattern))
                {
                    email_TIET.setError("Invalid Email");
                    password_TIET.setError(null);
                }else if (passwordString.isEmpty()){
                    email_TIET.setError("Password is empty");
                    password_TIET.setError(null);
                }else {
                    email_TIET.setError(null);
                    password_TIET.setError(null);
                    Loginparams loginparams = new Loginparams();
                    LoginRequest data = new LoginRequest();
                    data.setEmail(emailString);
                    data.setPassword(passwordString);
                    loginparams.setLoginData(data);
                    if(methods.isInternetOn())
                    {
                        loginUser(loginparams);
                    }
                    else
                    {
                        methods.DialogInternet();
                    }

                }
                break;

            case R.id.signUp_TV:
                Intent signUP_intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(signUP_intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

                break;

            case R.id.forgetPass_TV:

                Intent forgetPass_intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(forgetPass_intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);

                break;

        }

    }

    private void loginUser(Loginparams loginparams) {
       methods.showCustomProgressBarDialog(this);
        ApiService<LoginRegisterResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().loginApi(loginparams), "Login");
        Log.e("DATALOG","check1=> "+loginparams);

    }

    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        switch (key)
        {
            case "Login":

                try {
                    Log.d("DATALOG",response.body().toString());
                    LoginRegisterResponse responseLogin = (LoginRegisterResponse) response.body();
                    int responseCode = Integer.parseInt(responseLogin.getResponseLogin().getResponseCode());
                    if (responseCode== 109){
                        email_TIET.getText().clear();
                        password_TIET.getText().clear();
                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences("userdetails", 0);
                        SharedPreferences.Editor login_editor = sharedPreferences.edit();
                        login_editor.putString("email",responseLogin.getData().getEmail());
                        login_editor.putString("userId",responseLogin.getData().getUserId());
                        login_editor.putString("firstName",responseLogin.getData().getFirstName());
                        login_editor.putString("lastName",responseLogin.getData().getLastName());
                        login_editor.putString("phoneNumber",responseLogin.getData().getPhoneNumber());
                        login_editor.putString("address",responseLogin.getData().getAddress());
                        login_editor.putString("token",responseLogin.getResponseLogin().getToken());
                        Config.token=responseLogin.getResponseLogin().getToken();
                        login_editor.putString("loggedIn","loggedIn");
                        login_editor.commit();

                        Intent intent = new Intent(this,DashBoardActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
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
        methods.customProgressDismiss();
        Log.e("error",t.getMessage());
        Log.e("errrrr",t.getLocalizedMessage());

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
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
