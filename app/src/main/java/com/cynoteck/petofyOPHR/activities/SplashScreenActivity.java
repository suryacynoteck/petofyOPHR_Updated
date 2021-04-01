package com.cynoteck.petofyOPHR.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.Methods;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class SplashScreenActivity extends AppCompatActivity {

    Animation animation;
    ImageView splash_logo;
    Methods methods;
    int internetChkCode=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splash_logo= (ImageView) findViewById(R.id.splashlogo);
        methods = new Methods(this);


      /*  ActionBar actionBar=getSupportActionBar();
        actionBar.hide();*/

        animation = AnimationUtils.loadAnimation(SplashScreenActivity.this, R.anim.bounce);
        splash_logo.setAnimation(animation);
        NetwordDetect();
        try {
            if (methods.isInternetOn()) {
                internetChkCode=1;
                updateMethod();

            } else {
                internetChkCode=0;
                methods.DialogInternet();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    private void NetwordDetect() {
        boolean WIFI = false;
        boolean MOBILE = false;
        ConnectivityManager CM = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = CM.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    WIFI = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    MOBILE = true;
        }

        if (WIFI) {
            Config.IpAddress = GetDeviceipWiFiData();
//            Constants.EndUserIp= Config.IpAddress;
            Log.e("WIFI", Config.IpAddress + "");
        }

        if (MOBILE) {
            Config.IpAddress = getLocalIpAddress();
//          Constants.EndUserIp= Config.IpAddress;
            Log.e("MOBILE", Config.IpAddress + "");
        }
    }

    public String GetDeviceipWiFiData() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        @SuppressWarnings("deprecation")
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        return ip;
    }

    public String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String ip = Formatter.formatIpAddress(inetAddress.hashCode());
                        Log.e("sssss", ip + "");
                        return ip;
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }

    void updateMethod() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                internetChkCode=0;
                Intent intent;
                SharedPreferences sharedPreferences = getSharedPreferences("userdetails", 0);
                String loggedIn = sharedPreferences.getString("loggedIn", "");
                if (loggedIn.equals("loggedIn")){
                     intent = new Intent(SplashScreenActivity.this,DashBoardActivity.class);
                }else {
                    intent = new Intent(SplashScreenActivity.this, InstructionActivity.class);
                }
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        },2500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(internetChkCode==0)
        {
            try {
                if (methods.isInternetOn()) {

                    updateMethod();

                } else {
                    methods.DialogInternet();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}
