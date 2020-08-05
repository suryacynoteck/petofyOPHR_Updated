package com.cynoteck.petofyvet.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

import com.cynoteck.petofyvet.R;
public class Methods {
    private Context c;
    private Dialog progressBarDialog;

    public Methods(Context c) {
        this.c = c;
    }

    public final boolean isInternetOn() {
        boolean flag = false;
        ConnectivityManager connec = (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
        if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED) {
            flag = true;


        } else if (connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED) {
            flag = false;
        }
        return flag;
    }

    public void DialogInternet() {

        AlertDialog.Builder ad = new AlertDialog.Builder(c);
        ad.setMessage(R.string.CheckInternet);
        ad.setCancelable(true);
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ad.show();
    }

    public void showCustomProgressBarDialog(Context context) {
        try {
            progressBarDialog = new Dialog(context);
            progressBarDialog.setContentView(R.layout.custom_spin_progress_dialog);
            progressBarDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressBarDialog.setCancelable(false);
            progressBarDialog.setCanceledOnTouchOutside(false);
            progressBarDialog.show();
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            Window window = progressBarDialog.getWindow();
            lp.copyFrom(window.getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void customProgressDismiss() {
        progressBarDialog.dismiss();
    }

    public String removeLastElement(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

}
