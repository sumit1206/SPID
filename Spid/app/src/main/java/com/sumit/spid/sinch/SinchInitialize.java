package com.sumit.spid.sinch;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.sinch.android.rtc.PushTokenRegistrationCallback;
import com.sinch.android.rtc.SinchError;
import com.sumit.spid.User;

public class SinchInitialize extends BaseActivity implements SinchService.StartFailedListener, PushTokenRegistrationCallback {

    private ProgressDialog mSpinner;
    private boolean mPushTokenIsRegistered;
    Context context;
    String callReceiver;

    public SinchInitialize(Context context, String callReceiver){
        Log.println(Log.ASSERT,"SinchInitialize",callReceiver);
        this.context = context;
        this.callReceiver = callReceiver;
        mPushTokenIsRegistered = false;
    }


    @Override
    protected void onServiceConnected() {
        Log.println(Log.ASSERT,"onServiceConnected",callReceiver);
        if (getSinchServiceInterface().isStarted()) {
            openPlaceCallActivity();
        } else {
            loginClicked();
            getSinchServiceInterface().setStartListener(this);
        }
    }

    @Override
    protected void onPause() {
        Log.println(Log.ASSERT,"onPause",callReceiver);
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
        super.onPause();
    }

    @Override
    public void onStartFailed(SinchError error) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
        if (mSpinner != null) {
            mSpinner.dismiss();
        }
    }

    @Override
    public void onStarted() {
        Log.println(Log.ASSERT,"onStarted",callReceiver);
        nextActivityIfReady();
    }

    private void loginClicked() {
        Log.println(Log.ASSERT,"login clicked",callReceiver);
        String username = new User(context).getPhoneNumber();
        getSinchServiceInterface().setUsername(username);

        if (username.isEmpty()) {
            Toast.makeText(context, "Please enter a name", Toast.LENGTH_LONG).show();
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
        Log.println(Log.ASSERT,"openPlaceCallActivity",callReceiver);
        new CallingClass(context,callReceiver);
    }

    private void showSpinner() {
        Log.println(Log.ASSERT,"show spinner",callReceiver);
        mSpinner = new ProgressDialog(context);
        mSpinner.setTitle("Calling");
        mSpinner.setMessage("Please wait...");
        mSpinner.show();
    }

    @Override
    public void tokenRegistered() {
        Log.println(Log.ASSERT,"token registered",callReceiver);
        mPushTokenIsRegistered = true;
        nextActivityIfReady();
    }

    @Override
    public void tokenRegistrationFailed(SinchError sinchError) {
        Log.println(Log.ASSERT,"tokenRegistrationFailed",callReceiver);
        mPushTokenIsRegistered = false;
        Toast.makeText(context, "Push token registration failed - incoming calls can't be received!", Toast.LENGTH_LONG).show();
    }

    private void nextActivityIfReady() {
        Log.println(Log.ASSERT,"nextActivityIfReady",callReceiver);
        if (mPushTokenIsRegistered && getSinchServiceInterface().isStarted()) {
            openPlaceCallActivity();
        }
    }
}