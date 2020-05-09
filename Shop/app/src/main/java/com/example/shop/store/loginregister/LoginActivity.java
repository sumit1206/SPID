package com.example.shop.store.loginregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shop.R;
import com.example.shop.store.StoreMainActivity;
import com.example.shop.store.remote.DbHelper;
import com.example.shop.store.StoreUser;
import com.example.shop.store.remote.VolleyCallbackStore;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    String phoneNo, password, latitude, longitude;
    EditText phoneNoEt, passwordEt;
    TextView signUpText;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    StoreUser storeUser;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_login);

        loginButton = findViewById(R.id.login_btn);
        phoneNoEt = findViewById(R.id.login_phone);
        passwordEt = findViewById(R.id.login_password);
        signUpText = findViewById(R.id.sign_up_text);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(LoginActivity.this);
        storeUser = new StoreUser(LoginActivity.this);

        /**progress dialog initialization*/
        loading = new ProgressDialog(LoginActivity.this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                phoneNo = phoneNoEt.getText().toString().trim();
                password = passwordEt.getText().toString().trim();
                if(phoneNo.length() != 10){
                    passwordEt.setError("Valid number required");
                    passwordEt.requestFocus();
                }else if(password.length() == 0){
                    passwordEt.setError("Valid password required");
                    passwordEt.requestFocus();
                }else {
                    login();
                }

            }
        });
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent  = new Intent (LoginActivity.this,WheelerRegistration.class);
                startActivity(registerIntent);
            }
        });
    }

    void login(){
        getDeviceLocation();
        new DbHelper(LoginActivity.this).
                loginRegister(new VolleyCallbackStore() {
                    @Override
                    public void onSuccess(Object result) {
                        loading.dismiss();
                        storeUser.setPhone(phoneNo);
                        storeUser.setLatitude(latitude);
                        storeUser.setLongitude(longitude);
                        startActivity(new Intent(LoginActivity.this, StoreMainActivity.class));
                    }

                    @Override
                    public void noDataFound() {
                        loading.dismiss();
                        passwordEt.setError("Phone number or password invalid");
                        Toast.makeText(LoginActivity.this,"You are not registered",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCatch(JSONException e) {
                        Toast.makeText(LoginActivity.this,"You are not registered",Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }

                    @Override
                    public void onError(VolleyError e) {
                        Toast.makeText(LoginActivity.this,"Please check your internet connection",Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                },"035","","","","",phoneNo,"","","","","","",
                        "","","","",password);
    }
    private void getDeviceLocation() {
        loading.show();
        /**
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        try {
                            Location mLastKnownLocation = task.getResult();
                            LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            latitude = String.valueOf(latLng.latitude);
                            longitude = String.valueOf(latLng.longitude);
//                            Toast.makeText(LoginActivity.this, "Done: " + latitude + "+" + longitude, Toast.LENGTH_LONG).show();
                            Log.println(Log.ASSERT, "If", latLng.latitude + "+" + latLng.longitude);
                        }catch (Exception e){
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                            alertDialog.setTitle("Sorry!");
                            alertDialog.setMessage("A problem occurred while fetching your location, please open Google map and calibrate");
                            alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    LoginActivity.super.onBackPressed();
                                }
                            });
                            alertDialog.show();
                        }
                    } else {
                        Log.println(Log.ASSERT, "else","task not successful");
                    }
                }
            });
        } catch (SecurityException e)  {
            Log.println(Log.ASSERT, "Catch",Log.getStackTraceString(e));
        }
    }

}
