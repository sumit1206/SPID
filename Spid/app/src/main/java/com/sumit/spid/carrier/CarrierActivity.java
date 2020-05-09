package com.sumit.spid.carrier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sumit.spid.R;
import com.sumit.spid.search.SearchActivity;

public class CarrierActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 101;
    boolean firstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firstTime = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier);
    }

    @Override
    protected void onResume() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED))
        {
            if(firstTime){
            firstTime = false;
            Toast.makeText(CarrierActivity.this,"Permission not granted",Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(CarrierActivity.this,
                    new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
            }else {
                super.onBackPressed();
            }
        }else{
            Toast.makeText(CarrierActivity.this,"Permission granted",Toast.LENGTH_LONG).show();
        }
        super.onResume();
    }
}
