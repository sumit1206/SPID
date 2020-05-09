package com.example.shop.store.scanfunctionality;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.shop.R;
import com.example.shop.store.StoreMainActivity;
import com.example.shop.store.StoreUser;
import com.example.shop.store.remote.DbHelper;
import com.example.shop.store.remote.VolleyCallbackStore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanQrCamera extends AppCompatActivity {

    //qr code scanner object
    private IntentIntegrator qrScan;
    public static ProgressDialog loading;
    public static Activity scanQrCamera;

    public String eventId, number, wheelerPhone, latitude, longitude;
    boolean firstTime = true;

    StoreUser storeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr_camera);

        storeUser = new StoreUser(ScanQrCamera.this);
        scanQrCamera = ScanQrCamera.this;

        /**progress dialog initialization*/
        loading = new ProgressDialog(this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false);
        qrScan.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
//                    textViewName.setText(obj.getString("name"));
//                    textViewAddress.setText(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    String qrData = result.getContents();
                    String qrDataArray[] = qrData.split(",");
                    try {
                        eventId = qrDataArray[0];
                        number = qrDataArray[1];
                        wheelerPhone = storeUser.getPhone();
                        latitude = storeUser.getLatitude();
                        longitude = storeUser.getLongitude();
                        verifyQr();
                    }catch (Exception error){
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScanQrCamera.this);
                        alertDialog.setTitle("QR Invalid");
                        alertDialog.setMessage("Please scan a valid QR");
                        alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                    }
                }
            }
        } else {
            startActivity(new Intent(ScanQrCamera.this, StoreMainActivity.class));
            finish();
        }
    }

    private void verifyQr() {
        loading.show();
        Log.println(Log.ASSERT,"values",eventId+"="+number+"="+wheelerPhone+"="+latitude+"="+longitude);
        new DbHelper(ScanQrCamera.this).verifyQr(new VolleyCallbackStore() {
            @Override
            public void onSuccess(Object result) {
                loading.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(result));
                    String message = jsonObject.getString("message");
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScanQrCamera.this);
                    alertDialog.setTitle("QR valid");
                    alertDialog.setMessage(message);
                    alertDialog.setCancelable(false);
                    alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
//                            finish();
                            onBackPressed();
                        }
                    });
                    alertDialog.show();
                } catch (JSONException e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                String message = "no data found";
//                Intent intent = new Intent(ScanQrCamera.this, ScanData.class);
//                intent.putExtra("message",message);
//                startActivity(intent);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScanQrCamera.this);
                alertDialog.setTitle("QR Invalid");
                alertDialog.setMessage(message);
                alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
                        onBackPressed();
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                String message = "Our server is not responding in this moment. Some maintenance may be going on!";
//                String message = "catch "+e.getMessage();
//                Intent intent = new Intent(ScanQrCamera.this, ScanData.class);
//                intent.putExtra("message",message);
//                startActivity(intent);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScanQrCamera.this);
                alertDialog.setTitle("Server down");
                alertDialog.setMessage(message);
                alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
                        onBackPressed();
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                String message = "Please check your internet connection and try again";
//                String message = "error "+e.getMessage();
//                Intent intent = new Intent(ScanQrCamera.this, ScanData.class);
//                intent.putExtra("message",message);
//                startActivity(intent);
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScanQrCamera.this);
                alertDialog.setTitle("Internet error");
                alertDialog.setMessage(message);
                alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        finish();
                        onBackPressed();
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
            }
        },eventId,number,wheelerPhone,latitude,longitude);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        finish();
//        if(firstTime)
//            firstTime = false;
//        else
//            super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
