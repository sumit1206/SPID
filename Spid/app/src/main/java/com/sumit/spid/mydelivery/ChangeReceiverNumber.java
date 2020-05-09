package com.sumit.spid.mydelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.sumit.spid.R;
import com.sumit.spid.databasesupport.remote.RemoteDataUpload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.offers.OfferDataConfirmOrder;
import com.sumit.spid.sinch.CallingClass;

import org.json.JSONException;

public class ChangeReceiverNumber extends AppCompatActivity {

    EditText number;
    Button save;
    String eventId, packetId, n;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_receiver_number);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Change number");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.clear);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        number = findViewById(R.id.new_phone_number);
        save = findViewById(R.id.number_save_button);

        eventId = getIntent().getStringExtra("eventId");
        packetId = getIntent().getStringExtra("packetId");

        /**progress dialog initialization*/
        loading = new ProgressDialog(this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save() {
        n = number.getText().toString().trim();
        if(n.length() != 10){
            number.setError("Phone number invalid");
        }else{
            loading.show();
            new RemoteDataUpload(ChangeReceiverNumber.this).setReceiverPhoneNumber(new VolleyCallback() {
                @Override
                public void onSuccess(Object result) {
                    loading.dismiss();
                    Toast.makeText(ChangeReceiverNumber.this,"Number updated successfully",Toast.LENGTH_LONG).show();
                    success(n);
                }

                @Override
                public void noDataFound() {
                    loading.dismiss();
                    Toast.makeText(ChangeReceiverNumber.this,"Failed",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCatch(JSONException e) {
                    loading.dismiss();
                    Toast.makeText(ChangeReceiverNumber.this,"Failed",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(VolleyError e) {
                    loading.dismiss();
                    Toast.makeText(ChangeReceiverNumber.this,"Failed",Toast.LENGTH_LONG).show();
                }
            },eventId,packetId,n,"062");
        }
    }

    void success(String n){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("number",n);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}
