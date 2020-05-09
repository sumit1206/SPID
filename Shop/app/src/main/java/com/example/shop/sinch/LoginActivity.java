package com.example.shop.sinch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shop.R;
import com.sinch.android.rtc.PushTokenRegistrationCallback;
import com.sinch.android.rtc.SinchError;

public class LoginActivity extends BaseActivity implements SinchService.StartFailedListener, PushTokenRegistrationCallback {

    private Button mLoginButton;
    private EditText mLoginName;
    private ProgressDialog mSpinner;
    private boolean mPushTokenIsRegistered;
    String extraNumber, myExtraNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login);

        extraNumber = getIntent().getStringExtra("extraNumber");
        myExtraNumber = getIntent().getStringExtra("myExtraNumber");

//        mLoginName = (EditText) findViewById(R.id.loginName);

//        mLoginButton = (Button) findViewById(R.id.loginButton);
//        mLoginButton.setEnabled(false);
//        mLoginButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginClicked();
//            }
//        });

        mPushTokenIsRegistered = false;
    }

    @Override
    protected void onServiceConnected() {
        if (getSinchServiceInterface().isStarted()) {
            openPlaceCallActivity();
        } else {
//            mLoginButton.setEnabled(true);
            loginClicked();
            getSinchServiceInterface().setStartListener(this);
        }
    }

    @Override
    protected void onPause() {
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show();
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
    }

    @Override
    public void onStarted() {
        nextActivityIfReady();
    }

    private void loginClicked() {
//        String username = mLoginName.getText().toString();
        String username = myExtraNumber;
        getSinchServiceInterface().setUsername(username);

        if (username.isEmpty()) {
            Toast.makeText(this, "Please logout and login again", Toast.LENGTH_LONG).show();
            return;
        }

        if (!username.equals(getSinchServiceInterface().getUsername())) {
            getSinchServiceInterface().stopClient();
        }

        if (!mPushTokenIsRegistered) {
            getSinchServiceInterface().registerPushToken(this);
        }

        if (!getSinchServiceInterface().isStarted()) {
            getSinchServiceInterface().startClient();
            showSpinner();
        } else {
            nextActivityIfReady();
        }
    }

    private void openPlaceCallActivity() {
        if(extraNumber.equals("")){
            finish();
        }else {
            Intent mainActivity = new Intent(this, PlaceCallActivity.class);
            mainActivity.putExtra("extraNumber",extraNumber);
            startActivity(mainActivity);
            finish();
        }
    }

    private void showSpinner() {
        mSpinner = new ProgressDialog(this);
        mSpinner.setTitle("Setting up your account");
        mSpinner.setMessage("Please wait...");
        mSpinner.show();
    }

    @Override
    public void tokenRegistered() {
        mPushTokenIsRegistered = true;
        nextActivityIfReady();
    }

    @Override
    public void tokenRegistrationFailed(SinchError sinchError) {
        mPushTokenIsRegistered = false;
        Toast.makeText(this, "Push token registration failed - incoming calls can't be received!", Toast.LENGTH_LONG).show();
    }

    private void nextActivityIfReady() {
        if (mPushTokenIsRegistered && getSinchServiceInterface().isStarted()) {
            openPlaceCallActivity();
        }
    }
}
