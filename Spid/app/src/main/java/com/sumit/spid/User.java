package com.sumit.spid;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.widget.Toast.LENGTH_LONG;

public class User {
    Context context;
    private String phoneNumber;
    SharedPreferences sharedPreferences;

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find now's date time

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getPhoneNumber() {
        phoneNumber = sharedPreferences.getString("userPhoneNumber","");
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        sharedPreferences.edit().putString("userPhoneNumber", phoneNumber).commit();
    }

    public boolean sessionAvailable(){
        if(sharedPreferences.getString("userPhoneNumber","").equalsIgnoreCase("")){
            return false;
        }else {
            return true;
        }
    }

    public User(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userInfo",context.MODE_PRIVATE);
    }


    /*Checking for network connection*/

    public void checkInternetConnection(final View viewMain) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("ResourceAsColor") Snackbar snackbar = Snackbar.make(viewMain, "Please check your internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        checkInternetConnection(viewMain);
                    }
                }).setActionTextColor(R.color.white);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) {
//                return true;
                viewMain.setVisibility(View.VISIBLE);
                if(!snackbar.isShown())
                    snackbar.dismiss();
            }
            else {
//                return false;
                viewMain.setVisibility(View.INVISIBLE);
                if(!snackbar.isShown())
                    snackbar.show();
            }
        } else {
//            return false;
            viewMain.setVisibility(View.INVISIBLE);
            if(!snackbar.isShown())
                snackbar.show();
        }
    }

}
