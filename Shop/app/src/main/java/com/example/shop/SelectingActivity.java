package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import com.example.shop.aggregator.LoginRegistration.CarrierLogin;
import com.example.shop.aggregator.User;
import com.example.shop.aggregator.dashbord.CarrierDashboardActivity;
import com.example.shop.store.StoreMainActivity;
import com.example.shop.store.StoreUser;
import com.example.shop.store.loginregister.LoginActivity;

public class SelectingActivity extends AppCompatActivity {

    int MY_PERMISSIONS_REQUEST_READ_LOCATION = 001;
    int MY_PERMISSIONS_REQUEST_READ_SMS = 002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting);

        if(new StoreUser(SelectingActivity.this).sessionAvailable())
            startActivity(new Intent(SelectingActivity.this, StoreMainActivity.class));
        else if(new User(SelectingActivity.this).sessionAvailable())
            startActivity(new Intent (SelectingActivity.this, CarrierDashboardActivity.class));


    }

    public void aggregatorClick(View view) {
        startActivity(new Intent (SelectingActivity.this, CarrierLogin.class));
    }

    public void storeClick(View view) {
        startActivity(new Intent(SelectingActivity.this, LoginActivity.class));
    }

    @Override
    protected void onResume() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(SelectingActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions(SelectingActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
        }
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == MY_PERMISSIONS_REQUEST_READ_SMS || requestCode == MY_PERMISSIONS_REQUEST_READ_LOCATION){

            for (int grantResult: grantResults) {
                if(grantResult == PackageManager.PERMISSION_DENIED)
                    super.onBackPressed();
            }
        }else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
