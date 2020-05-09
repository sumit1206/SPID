package com.example.shop.aggregator.LoginRegistration;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.example.shop.CallingUi;
import com.example.shop.R;
import com.example.shop.aggregator.dashbord.CarrierDashboardActivity;
import com.example.shop.aggregator.RemoteConnection.RemoteConnection;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.aggregator.User;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;

import org.json.JSONException;

public class CarrierLogin extends AppCompatActivity {

    EditText loginPhone, loginPassword;
    String phone, password;
    User user;
    RelativeLayout view;
    SinchClient sinchClient;
    ProgressDialog loading;

    /**Sinch credentials*/
    private static final String APP_KEY = "3f40f24a-318a-4714-9fbb-a3be2de27f65";
    private static final String APP_SECRET = "Qvpv6aku6kSrevwkdiNSMw==";
    private static final String ENVIRONMENT = "clientapi.sinch.com";
    /***/

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrier_login);

        loginPhone = findViewById(R.id.loginPhone);
        loginPassword = findViewById(R.id.loginPassword);
        view = findViewById(R.id.view);

        /**progress dialog initialization*/
        loading = new ProgressDialog(CarrierLogin.this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        user = new User(CarrierLogin.this);

    }

    public void login(View view) {
        boolean fieldsNotEmpty = validate(new EditText[] { loginPhone, loginPassword });
        if(fieldsNotEmpty){
            loading.show();
            phone = loginPhone.getText().toString().trim();
            password = loginPassword.getText().toString().trim();

            new RemoteConnection(CarrierLogin.this).registerAggrigator(new VolleyCallback() {
                @Override
                public void onSuccess(Object result) {
                    Intent intent = new Intent (CarrierLogin.this,CarrierDashboardActivity.class);
                    user.setPhoneNumber(phone);
                    /**Initializing sinch*/
                    sinchClient = Sinch.getSinchClientBuilder()
                            .context(CarrierLogin.this)
                            .userId(phone)
                            .applicationKey(APP_KEY)
                            .applicationSecret(APP_SECRET)
                            .environmentHost(ENVIRONMENT)
                            .build();

                    sinchClient.setSupportCalling(true);
                    sinchClient.startListeningOnActiveConnection();
                    sinchClient.start();
                    /***/
                    loading.dismiss();
                    startActivity(intent);
                }

                @Override
                public void noDataFound() {
                    loading.dismiss();
                    loginPassword.setError("Phone number or password invalid!");
                    loginPassword.requestFocus();
                }

                @Override
                public void onCatch(JSONException e) {
                    loading.dismiss();
                    Toast.makeText(CarrierLogin.this, "Oops!An error occurred!", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onError(VolleyError e) {
                    loading.dismiss();
                    Toast.makeText(CarrierLogin.this, "Please check your internet connection!", Toast.LENGTH_LONG).show();
                }
            },phone,"",password,"014");
            /**014 = aggrigator login*/
        }

    }

    public void toRegistrationPage(View view) {
        Intent registerIntent = new Intent (CarrierLogin.this,CarrierRegistration.class);
        startActivity(registerIntent);
    }

    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().trim().length() <= 0){
                currentField.setError("Cannot be empty!");
                currentField.requestFocus();
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        user.checkInternetConnection(view);
        super.onResume();
    }
}

