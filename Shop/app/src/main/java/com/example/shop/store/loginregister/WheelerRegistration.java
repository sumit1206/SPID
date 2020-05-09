package com.example.shop.store.loginregister;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shop.ParseImage;
import com.example.shop.R;
import com.example.shop.store.remote.DbHelper;
import com.example.shop.store.remote.VolleyCallbackStore;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;

import java.util.Locale;

public class WheelerRegistration extends AppCompatActivity {

    /**Request codes*/
    private static final int CAMERA_REQUEST_ONE = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 110;


    private FusedLocationProviderClient mFusedLocationProviderClient;
    Location mLastKnownLocation;

    ProgressDialog loading;

    String shopName;
    String ownerName;
    String mail;
    String phone;
    String id;
    String tag;
    String aadharNo;
    String panNo;
    String landMark;
    String password;
    String address;
    LatLng latLng;
    String latitude;
    String longitude;
    String shopImageString;

    EditText shopNameEt;
    EditText ownerNameEt;
    EditText mailEt;
    EditText phoneEt;
//    EditText idEt;
    EditText tagEt;
    EditText aadharNoEt;
    EditText panNoEt;
    EditText landMarkEt;
    EditText passwordEt;

    ImageView shopImage;

    TextView termsRegistration, locationField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheeler_registration);
        termsRegistration = findViewById(R.id.seeTerms);
        termsRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent termsIntent = new Intent(WheelerRegistration.this,TermsRegistration.class);
                startActivity(termsIntent);
            }
        });

        /**progress dialog initialization*/
        loading = new ProgressDialog(WheelerRegistration.this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(WheelerRegistration.this);

        locationField = findViewById(R.id.reg_location_value);
        shopNameEt = findViewById(R.id.reg_shop_name);
        ownerNameEt = findViewById(R.id.reg_owner_name);
        mailEt = findViewById(R.id.reg_email_address);
        phoneEt = findViewById(R.id.reg_phone_no);
//        idEt = findViewById(R.id.reg_id);
        tagEt = findViewById(R.id.reg_tag);
        aadharNoEt = findViewById(R.id.reg_aadhar_no);
        panNoEt = findViewById(R.id.reg_pan_no);
        landMarkEt = findViewById(R.id.reg_landmark);
        passwordEt = findViewById(R.id.reg_password);
        shopImage = findViewById(R.id.shop_image);

        shopImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST_ONE);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo;
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_ONE:
                    photo = (Bitmap) data.getExtras().get("data");
                    shopImage.setImageBitmap(photo);
                    break;
                }
        }else
            Toast.makeText(WheelerRegistration.this,"Photo capturing failed",Toast.LENGTH_LONG).show();

    }


    void pickUpAllData(){
        shopName = shopNameEt.getText().toString().trim();
        ownerName = ownerNameEt.getText().toString().trim();
        mail = mailEt.getText().toString().trim();
        phone = phoneEt.getText().toString().trim();
//        id = idEt.getText().toString().trim();
        id = phone;
        tag = tagEt.getText().toString().trim();
        aadharNo = aadharNoEt.getText().toString().trim();
        panNo = panNoEt.getText().toString().trim();
        landMark = landMarkEt.getText().toString().trim();
        password = passwordEt.getText().toString().trim();
        shopImageString = new ParseImage(shopImage).getImageString();
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
                            mLastKnownLocation = task.getResult();
                            Log.println(Log.ASSERT, "task result", task.getResult().toString());
                            latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            latitude = String.valueOf(latLng.latitude);
                            longitude = String.valueOf(latLng.longitude);
                            Toast.makeText(WheelerRegistration.this, "Done: " + latitude + "+" + longitude, Toast.LENGTH_LONG).show();
                            Log.println(Log.ASSERT, "If", latLng.latitude + "+" + latLng.longitude);
                            getAddresFromLocation();
                        }catch (Exception e){
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(WheelerRegistration.this);
                            alertDialog.setTitle("Sorry!");
                            alertDialog.setMessage("A problem occurred while fetching your location, please open Google map and calibrate");
                            alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    WheelerRegistration.super.onBackPressed();
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

    void getAddresFromLocation(){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            Address myAddress = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0);
            address = myAddress.getAddressLine(0);
            locationField.setText(address);
            loading.dismiss();
        }catch (Exception e){

        }

    }

    public void register(View view) {
        loading.show();
        pickUpAllData();
        new DbHelper(WheelerRegistration.this).loginRegister(new VolleyCallbackStore() {
            @Override
            public void onSuccess(Object result) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(WheelerRegistration.this);
                alertDialog.setTitle("Congratulations!");
                alertDialog.setMessage("You are successfully registered as our business partner.");
                alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(WheelerRegistration.this, LoginActivity.class));
                    }
                });
                alertDialog.show();
                loading.dismiss();
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                Toast.makeText(WheelerRegistration.this,"no data found",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                Toast.makeText(WheelerRegistration.this,"catch"+e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                Log.println(Log.ASSERT, "Error",Log.getStackTraceString(e));
                Toast.makeText(WheelerRegistration.this,"error"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        },"034",id,shopName,ownerName,mail,phone,address,tag,latitude,longitude,aadharNo,
                panNo,"","",shopImageString,landMark,password);
        Log.println(Log.ASSERT, "Values",id+"="+shopName+"="+ownerName+"="+mail+"="+phone+"="+address+"="+tag+"="+latitude+"="+
                        longitude+"="+aadharNo+"="+panNo+"="+shopImageString+"="+""+"="+""+"="+landMark+"="+password);
        Toast.makeText(WheelerRegistration.this,latitude+"+"+longitude,Toast.LENGTH_LONG).show();
    }

    public void pickLocation(View view) {
        getDeviceLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_ONE);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

}
