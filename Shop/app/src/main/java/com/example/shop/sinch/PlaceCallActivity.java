package com.example.shop.sinch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.R;
import com.example.shop.SelectingActivity;
import com.sinch.android.rtc.calling.Call;

public class PlaceCallActivity extends BaseActivity {

    private Button mCallButton;
    private EditText mCallName;
    Boolean inCallingMode;
    String extraNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);

        Log.println(Log.ASSERT,"SINCH","onCreate");

        inCallingMode = false;
        if(getIntent().hasExtra("extraNumber")) {
            inCallingMode = true;
            extraNumber = getIntent().getStringExtra("extraNumber");
        }
        if(getIntent().hasExtra("logout")){
            Log.println(Log.ASSERT, "SINCH:", "Logout request received");
            logoutButtonClicked();
        }
//        mCallName = (EditText) findViewById(R.id.callName);
//        mCallButton = (Button) findViewById(R.id.callButton);
//        mCallButton.setEnabled(false);
//        mCallButton.setOnClickListener(buttonClickListener);

//        Button logoutButton = (Button) findViewById(R.id.logoutButton);
//        logoutButton.setOnClickListener(buttonClickListener);
    }

    @Override
    protected void onServiceConnected() {
//        TextView userName = (TextView) findViewById(R.id.loggedInName);
//        userName.setText(getSinchServiceInterface().getUsername());
//        mCallButton.setEnabled(true);
        if(inCallingMode)
            callButtonClicked();
    }

    private void logoutButtonClicked() {
        Log.println(Log.ASSERT, "SINCH:", "Trying to logout");
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().unregisterPushToken();
            getSinchServiceInterface().stopClient();
        }
        if(getSinchServiceInterface() != null) {
            Log.println(Log.ASSERT, "SINCH:", "Log out failed");
        }else {
            Log.println(Log.ASSERT, "SINCH:", "Logged out successfully");
        }
        Intent logout = new Intent (PlaceCallActivity.this, SelectingActivity.class);
        startActivity(logout);
        finish();
    }

    private void callButtonClicked() {
//        String userName = mCallName.getText().toString();
        String userName = extraNumber;
        if (userName == null || userName.isEmpty()) {
            finish();
//            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = getSinchServiceInterface().callUser(userName);
        String callId = call.getCallId();

        Intent callScreen = new Intent(this, CallScreenActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        startActivity(callScreen);
        finish();
    }

//    private OnClickListener buttonClickListener = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.callButton:
//                    callButtonClicked();
//                    break;
//
//                case R.id.logoutButton:
//                    logoutButtonClicked();
//                    break;
//
//            }
//        }
//    };
}
