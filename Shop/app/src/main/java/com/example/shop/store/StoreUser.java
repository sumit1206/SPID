package com.example.shop.store;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StoreUser {
    Context context;
    private String phone;
    private String stnName;
    private String latitude;
    private String longitude;
    SharedPreferences sharedPreferences;

    public String getLatitude() {
        latitude = sharedPreferences.getString("latitude","");
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
        sharedPreferences.edit().putString("latitude",latitude).commit();
    }

    public String getLongitude() {
        longitude = sharedPreferences.getString("longitude","");
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
        sharedPreferences.edit().putString("longitude",longitude).commit();
    }

    public String getPhone() {
        phone = sharedPreferences.getString("phone","");
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        sharedPreferences.edit().putString("phone",phone).commit();
    }

    public String getStnName() {
        stnName = sharedPreferences.getString("station","");
        return stnName;
    }

    public void setStnName(String stnName) {
        this.stnName = stnName;
        sharedPreferences.edit().putString("station",stnName).commit();
    }

    public static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date())+".000000"; // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public boolean sessionAvailable(){
        if(sharedPreferences.getString("phone","").equalsIgnoreCase("")){
            return false;
        }else {
            return true;
        }
    }

    public void setOpen(){
        sharedPreferences.edit().putString("door","open").commit();
    }

    public void setClose(){
        sharedPreferences.edit().putString("door","close").commit();
    }

    public boolean isOpen(){
        String door = sharedPreferences.getString("door","");
        if(door.equals("open"))
            return true;
        else
            return false;
    }

    public StoreUser(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("storeUserInfo",context.MODE_PRIVATE);
    }
}
