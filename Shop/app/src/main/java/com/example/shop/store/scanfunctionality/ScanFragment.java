package com.example.shop.store.scanfunctionality;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.store.loginregister.LoginActivity;
import com.example.shop.store.loginregister.WheelerRegistration;
import com.example.shop.store.remote.DbHelper;
//import com.google.android.gms.vision.text.Line;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {

    LinearLayout scan_btn;
    EditText parcelId,parcelOtp;
    Button submit;
    TextView alertText,handoverText;
    int checkStatus;
    LinearLayout handOverLayout;
    ProgressDialog loading;

    public ScanFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scan,container,false);

//        Toolbar toolbar = view.findViewById(R.id.toolbar);
//        toolbar.setTitle("Scan QR");

        /**progress dialog initialization*/
        loading = new ProgressDialog(getContext(), R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        scan_btn = view.findViewById(R.id.scan_qr_btn);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scan_intent = new Intent(getActivity(),ScanQrCamera.class);
                startActivity(scan_intent);
            }
        });

        parcelId = view.findViewById(R.id.parcel_id);
        parcelOtp = view.findViewById(R.id.parcel_otp);
        submit = view.findViewById(R.id.otp_submit);
        alertText= view.findViewById(R.id.alert_text);
        handoverText = view.findViewById(R.id.handover_text);
        handOverLayout = view.findViewById(R.id.handover_layout_scan);
        handOverLayout.setVisibility(View.GONE);
        checkStatus = -1;

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyOtp();
            }
        });

        handoverText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transition transition = new Fade();
                transition.setDuration(1000);
                transition.addTarget(R.id.handover_layout_scan);

                TransitionManager.beginDelayedTransition(handOverLayout,transition);
                if(handOverLayout.getVisibility() == View.VISIBLE)
                    handOverLayout.setVisibility(View.GONE);
                else
                    handOverLayout.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    void verifyOtp(){
        String otp = parcelOtp.getText().toString().trim();
        String pId = parcelId.getText().toString().trim();
        if(otp.length() != 6)
            parcelOtp.setError("Invalid OTP");
        else if(pId.length() != 8)
            parcelId.setError("Invalid order id.");
        else{
            loading.show();
            new DbHelper(getContext()).verifyOtp(new VolleyCallback() {
                @Override
                public void onSuccess(Object result) {
                    loading.dismiss();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("OTP Matched");
                    alertDialog.setMessage("You can now handover this parcel.");
                    alertDialog.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            handOverLayout.setVisibility(View.GONE);
                        }
                    });
                    alertDialog.show();
                }

                @Override
                public void noDataFound() {
                    loading.dismiss();
                    Toast.makeText(getContext(),"Order Id or OTP mismatch",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCatch(JSONException e) {
                    loading.dismiss();
                    Toast.makeText(getContext(),"Order Id or OTP mismatch",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(VolleyError e) {
                    loading.dismiss();
                    Toast.makeText(getContext(),"Order Id or OTP mismatch",Toast.LENGTH_LONG).show();
                }
            },"063",pId,otp);
        }
    }


}
