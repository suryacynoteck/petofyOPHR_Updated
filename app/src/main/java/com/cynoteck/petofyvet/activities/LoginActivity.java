package com.cynoteck.petofyvet.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.loginRequest.LoginRequest;
import com.cynoteck.petofyvet.params.loginRequest.Loginparams;
import com.cynoteck.petofyvet.params.otpRequest.SendOtpParameter;
import com.cynoteck.petofyvet.params.otpRequest.SendOtpRequest;
import com.cynoteck.petofyvet.response.loginRegisterResponse.LoginRegisterResponse;
import com.cynoteck.petofyvet.response.otpResponse.OtpResponse;
import com.cynoteck.petofyvet.utils.Config;
import com.cynoteck.petofyvet.utils.Methods;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

public class LoginActivity extends FragmentActivity implements View.OnClickListener, ApiResponse {
    LoginRegisterResponse responseLogin;
    TextInputLayout mobile_numberTL,otp_TL;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor login_editor;
    Dialog otpDialog;
    TextInputEditText pet_parent_otp;
    TextView cancelOtpDialog,otpHeading;
    boolean doubleBackToExitPressedOnce = false;
    private TextInputLayout email_TIL, password_TIL;
    private EditText email_TIET, password_TIET;
    private Button login_BT,submit_parent_otp;
    private String emailString="", passwordString="";
    private TextView signUp_TV, forgetPass_TV;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Methods methods;
    ImageView logoVet;
    TelephonyManager telephonyManager;
    String imeiNumber="", token="";
    String deviceIp, strResponseOtp="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        methods = new Methods(this);
        //requestMultiplePermissions();

        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        String ipAddress = Formatter.formatIpAddress(ip);

//        String ipAddress = Formatter.formatIpAddress(ip);
//        WifiManager wm = (WifiManager)getApplicationContext().getSystemService(getApplication().WIFI_SERVICE);
//        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        getDeviceId();
        String mobileIp = getMobileIPAddress();
        deviceIp = ipAddress;

        //Toast.makeText(this, "IPADDRESS"+deviceIp, Toast.LENGTH_SHORT).show();

        Log.d("IP address",ipAddress);

        init();
        SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
        String loggedIn = sharedPreferences.getString("loggedIn", "");
        if (loggedIn.equals("loggedIn")){
            Intent intent = new Intent(this,DashBoardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_right);
        }
    }

    private void getDeviceId() {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
        imeiNumber = telephonyManager.getDeviceId();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    imeiNumber = telephonyManager.getDeviceId();
//                    Toast.makeText(LoginActivity.this,imeiNumber,Toast.LENGTH_LONG).show();
                } else {

                    Toast.makeText(LoginActivity.this,"Without permission we check",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public static String getMobileIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        return  addr.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
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
                }else if (imeiNumber.isEmpty()){
                         getDeviceId();
                        Toast.makeText(this, "sasPermission is not Granted !", Toast.LENGTH_SHORT).show();
                }/*else if (deviceIp.isEmpty()){
                    //requestMultiplePermissions();
                    Toast.makeText(this, "Permission is not Granted !", Toast.LENGTH_SHORT).show();

                }*/else {
                    email_TIET.setError(null);
                    password_TIET.setError(null);
                    Loginparams loginparams = new Loginparams();
                    LoginRequest data = new LoginRequest();
                    data.setEmail(emailString);
                    data.setDeviceId(imeiNumber);
                    data.setDeviceIp(deviceIp);
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
                    loginSucess();
                }
                break;

            case R.id.cancelOtpDialog:
                otpDialog.dismiss();
                break;
        }

    }

    private void loginUser(Loginparams loginparams) {
       methods.showCustomProgressBarDialog(this);
        ApiService<LoginRegisterResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().loginApi(loginparams), "Login");
        Log.e("DATALOG","check1=> "+loginparams);

    }
    private void requestMultiplePermissions() {
        Dexter.withActivity(this)
                .withPermissions(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            Toast.makeText(LoginActivity.this, "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(LoginActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    @Override
    public void onResponse(Response response, String key) {
        methods.customProgressDismiss();
        switch (key)
        {
            case "Login":

                try {
                    Log.d("DATALOG",response.body().toString());
                    responseLogin = (LoginRegisterResponse) response.body();
                    int responseCode = Integer.parseInt(responseLogin.getResponseLogin().getResponseCode());
                    if (responseCode== 109){
                        token = responseLogin.getResponseLogin().getToken();

                        if (responseLogin.getData().getEnableTwoStepVerification().equals("true")){
                            String actualNumber=responseLogin.getData().getPhoneNumber().replaceAll("-", "");
                            SendOtpRequest sendOtpRequest = new SendOtpRequest();
                            SendOtpParameter data = new SendOtpParameter();
                            data.setPhoneNumber(actualNumber);
                            data.setEmailId(responseLogin.getData().getEmail().trim());
                            sendOtpRequest.setData(data);
                            if (methods.isInternetOn()) {
                                sendotpUsingMobileNumber(sendOtpRequest);
                            } else {
                                methods.DialogInternet();
                            }


                        }else {
                            loginSucess();
                        }

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


            case "SendOtp":
                try {
                    Log.d("SendOtp",response.body().toString());
                    OtpResponse otpResponse = (OtpResponse) response.body();
                    int responseCode = Integer.parseInt(otpResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        if(otpResponse.getData().getSuccess().equals("true"))
                        {
                            strResponseOtp=otpResponse.getData().getOtp();
                            otpDialog();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
                        }
                    }else if (responseCode==614){
                        Toast.makeText(LoginActivity.this, otpResponse.getResponse().getResponseMessage(), Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "Please Try Again !", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void loginSucess() {
        email_TIET.getText().clear();
        password_TIET.getText().clear();
        sharedPreferences = LoginActivity.this.getSharedPreferences("userdetails", 0);
        login_editor = sharedPreferences.edit();
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
    }

    private void otpDialog() {
        otpDialog=new Dialog(this);
        otpDialog.setContentView(R.layout.otp_layout);

        otp_TL=otpDialog.findViewById(R.id.otp_TL);
        pet_parent_otp=otpDialog.findViewById(R.id.pet_parent_otp);
        submit_parent_otp=otpDialog.findViewById(R.id.submit_parent_otp);
        cancelOtpDialog=otpDialog.findViewById(R.id.cancelOtpDialog);
        otpHeading = otpDialog.findViewById(R.id.otpHeading);
        submit_parent_otp.setOnClickListener(this);
        cancelOtpDialog.setOnClickListener(this);
        otpHeading.setText("Enter O.T.P");


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

    private void sendotpUsingMobileNumber(SendOtpRequest sendOtpRequest) {
        ApiService<OtpResponse> service = new ApiService<>();
        service.get( this, ApiClient.getApiInterface().senOtp(token,sendOtpRequest), "SendOtp");
        Log.e("DATALOG","check1=> "+sendOtpRequest);

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

    @Override
    protected void onResume() {
        super.onResume();
        getDeviceId();
    }
}
