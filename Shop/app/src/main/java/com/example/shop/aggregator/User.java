package com.example.shop.aggregator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import com.example.shop.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        @SuppressLint("ResourceAsColor") Snackbar snackbar = Snackbar.make(viewMain, "Please check your intrernet connection!", Snackbar.LENGTH_INDEFINITE)
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

//    //fetching elected details from body
//    void fetch(String sample) {
//        String element, pnr = "", trainNo = "", date = "", time = "", type = "", fare = "", name = "", seatNo = "", status = "", typeCode, to = "", from = "";
//        int separaterIndex = sample.indexOf(',');
//        while (separaterIndex != -1) {
//            element = sample.substring(0, separaterIndex);
//            if (element.contains("PNR"))
//                pnr = element.substring(element.indexOf(':') + 1);
//            else if (element.contains("TRN") || element.contains("TRAIN"))
//                trainNo = element.substring(element.indexOf(':') + 1);
//            else if (element.contains("DOJ"))
//                date = element.substring(element.indexOf(':') + 1);
//            else if (element.contains("DP") || element.contains("Dep"))
//                time = element.substring(element.indexOf(':') + 1);
//            else if (element.contains("Fare"))
//                fare = element.substring(element.indexOf(':') + 1);
//            else if (element.length() == 2) {
//                typeCode = element.substring(element.indexOf(':') + 1);
//                if (typeCode.equals("1A"))
//                    type = "AC First Class";
//                else if (typeCode.equals("2A"))
//                    type = "AC 2-tire";
//                else if (typeCode.equals("3A"))
//                    type = "AC 3-tire";
//                else if (typeCode.equals("FC"))
//                    type = "First Class";
//                else if (typeCode.equals("CC"))
//                    type = "AC Chair Car";
//                else if (typeCode.equals("SL"))
//                    type = "Sleeper Class";
//                else if (typeCode.equals("2S"))
//                    type = "Second Sitting";
//                else
//                    type = "Unknown";
//            } else if (occurance(element, '-') == 1) {
//                from = element.substring(0, element.indexOf('-'));
//                to = element.substring(element.indexOf('-') + 1);
//            } else {
//                if (element.contains("+") || !(element.contains("1") || element.contains("2") || element.contains("3") || element.contains("4") || element.contains("5") || element.contains("6") || element.contains("7") || element.contains("8") || element.contains("9") || element.contains("0")))
//                    name = element;
//                else
//                    seatNo = seatNo + element + ",";
//            }
//
//            sample = sample.substring(separaterIndex + 1);
//            separaterIndex = sample.indexOf(',');
//        }
//        if (sample.contains("Fare"))
//            fare = sample.substring(sample.indexOf(':') + 1);
//        User user = new User(StoreMainActivity.this);
//        phone = user.getName();
//
//        /*Writing to database
//         * writing all the values fetch from sms to database*/
//
//        DbHelper.dbUpload(this,pnr,phone,trainNo,name,type,seatNo,fare,"2019-07-19 10:48:00.000000","2019-07-20 10:48:00.000000",to,from);
//    }



}
