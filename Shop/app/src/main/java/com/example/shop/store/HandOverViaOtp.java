package com.example.shop.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HandOverViaOtp extends AppCompatActivity {

    EditText parcelId,otp;
    Button submit;
    TextView alertText;
    int checkStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_over_via_otp);
        parcelId = findViewById(R.id.parcel_id);
        otp = findViewById(R.id.parcel_otp);
        submit = findViewById(R.id.otp_submit);
        alertText= findViewById(R.id.alert_text);
        checkStatus = -1;

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                chkPack(parcelId.getText().toString(),otp.getText().toString());
//                if(checkStatus == 1){
////                    Toast.makeText(getApplicationContext(), "otp and packet matched", Toast.LENGTH_LONG).show();
//                    alertText.setText("Handover packet ");
//
//                }else if(checkStatus == 2){
////                    Toast.makeText(getApplicationContext(), "otp did not matched", Toast.LENGTH_LONG).show();
//                    alertText.setText("OTP invalid");
//
//                }else if(checkStatus == 0 ){
////                    Toast.makeText(getApplicationContext(), " packet did not found", Toast.LENGTH_LONG).show();
//                    alertText.setText("Packet not found");
//
//                }
//            }
//        });
    }
//    private  String URL_CHKPACK="http://197.189.202.8:8080/courserv/chkPacketOtp.php";
//    public void chkPack(final String packet_id,final String otp ){
//
//        StringRequest stringRequest;
//        stringRequest = new StringRequest(Request.Method.POST,
//                URL_CHKPACK,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
//                            int flagLgn = jsonObject.getInt("success");
//                            checkStatus = flagLgn;
//                            if(flagLgn==1){
//                                Toast.makeText(getApplicationContext(), "otp and packet matched", Toast.LENGTH_LONG).show();
//
//                            }else if(flagLgn==2){
//                                Toast.makeText(getApplicationContext(), "otp did not matched", Toast.LENGTH_LONG).show();
//
//                            }else if(flagLgn== 0 ){
//                                Toast.makeText(getApplicationContext(), " packet did not found", Toast.LENGTH_LONG).show();
//                            }
//
//                        }catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
//
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("packet_id", packet_id);
//                params.put("otp", otp);
//
//
//                return params;
//            }
//        };
//        RequestQueue req = (RequestQueue) Volley.newRequestQueue(this);
//        req.add(stringRequest);
//    }

}
