package com.example.shop.aggregator.LoginRegistration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shop.R;
import com.example.shop.aggregator.RemoteConnection.RemoteConnection;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;

import org.json.JSONException;

public class CarrierRegistration extends AppCompatActivity {

    EditText registerPhone, registerMail, registerPassword, registerConformPassword;
    String phone, mail, password, conformPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier_registration);
        registerPhone = findViewById(R.id.registerPhone);
        registerMail = findViewById(R.id.registerMail);
        registerPassword = findViewById(R.id.registerPassword);
        registerConformPassword = findViewById(R.id.registerConfirmPassword);

    }

    public void register(View view) {

        boolean fieldsNotEmpty = validate(new EditText[] { registerPhone, registerMail, registerPassword, registerConformPassword });
        if(fieldsNotEmpty){
            phone = registerPhone.getText().toString().trim();
            mail = registerMail.getText().toString().trim();
            password = registerPassword.getText().toString().trim();
            conformPassword = registerConformPassword.getText().toString().trim();

            if(password.equals(conformPassword)) {

                new RemoteConnection(CarrierRegistration.this).registerAggrigator(new VolleyCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        Toast.makeText(CarrierRegistration.this, "successfully registered", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(CarrierRegistration.this,CarrierLogin.class));
                    }

                    @Override
                    public void noDataFound() {
                        Toast.makeText(CarrierRegistration.this, "no data found", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCatch(JSONException e) {
                        Toast.makeText(CarrierRegistration.this, "catch", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(VolleyError e) {
                        Toast.makeText(CarrierRegistration.this, "error", Toast.LENGTH_LONG).show();
                    }
                }, phone, mail, password, "015");
                /**015 = Request code for registration*/
            }else{
                registerConformPassword.setError("Must be same as confirm password!");
            }

        }

    }

    private boolean validate(EditText[] fields){
        for(int i = 0; i < fields.length; i++){
            EditText currentField = fields[i];
            if(currentField.getText().toString().trim().length() <= 0){
                currentField.setError("Cannot be empty!");
                return false;
            }
        }
        return true;
    }
}
