package com.sumit.spid.mydelivery;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.VolleyError;
import com.sumit.spid.BugReport;
import com.sumit.spid.R;
import com.sumit.spid.databasesupport.remote.RemoteDataUpload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;

import org.json.JSONException;

import java.util.Random;

public class OfflineOtp extends AppCompatActivity {

    TextView otp;
    Button regenOtp;
    String eventId, receiver, packetId;
    Toolbar toolbar;
    ImageView shareOtp;
    String otpString;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline_otp);

        toolbar = findViewById(R.id.otp_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        eventId = getIntent().getExtras().getString("event_id");
        receiver = getIntent().getExtras().getString("receiver");
        packetId = getIntent().getExtras().getString("packet_id");

        /**progress dialog initialization*/
        loading = new ProgressDialog(this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        otp = findViewById(R.id.otp);
        regenOtp = findViewById(R.id.regen_otp);
        shareOtp = findViewById(R.id.otp_share_button);

        otp.setText(getOtp());
        regenOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp.setText(getOtp());
            }
        });

        shareOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BugReport(OfflineOtp.this).shareAnything("SPID parcel delivery app.\n"+otpString+" is your OTP for Order id "+packetId+".Provide this OTP before collecting parcel.");
            }
        });
    }

    public String getOtp() {
        // It will generate 6 digit random Number.
        // from 0 to 999999

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        otpString = String.format("%06d", number);

        // TODO: write otp to database here
        setOtp(otpString);

        return otpString;
    }

    void setOtp(String otp){
        loading.show();
        new RemoteDataUpload(OfflineOtp.this).setOtp(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                loading.dismiss();
                Toast.makeText(OfflineOtp.this,"Otp updated successfully",Toast.LENGTH_LONG).show();
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                Toast.makeText(OfflineOtp.this,"Otp updating failed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                Toast.makeText(OfflineOtp.this,"Otp updating failed",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                Toast.makeText(OfflineOtp.this,"Otp updating failed",Toast.LENGTH_LONG).show();
            }
        },eventId,otp,receiver,"061");
    }
}
