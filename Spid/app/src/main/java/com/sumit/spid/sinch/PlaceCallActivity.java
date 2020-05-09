package com.sumit.spid.sinch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sinch.android.rtc.calling.Call;
import com.sumit.spid.R;

public class PlaceCallActivity extends BaseActivity {

    private Button mCallButton;
    Boolean inCallMode;
    private EditText mCallName;
    String extraNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);

//        mCallName = (EditText) findViewById(R.id.callName);
//        mCallButton = (Button) findViewById(R.id.callButton);
//        mCallButton.setEnabled(false);
//        mCallButton.setOnClickListener(buttonClickListener);

//        Button logoutButton = (Button) findViewById(R.id.logoutButton);
//        logoutButton.setOnClickListener(buttonClickListener);
        inCallMode = false;
        if(getIntent().hasExtra("extraNumber")) {
            inCallMode = true;
            extraNumber = getIntent().getStringExtra("extraNumber");
        }
        if(getIntent().hasExtra("logout"))
            logoutButtonClicked();

    }

    @Override
    protected void onServiceConnected() {
//        TextView userName = (TextView) findViewById(R.id.loggedInName);
//        userName.setText(getSinchServiceInterface().getUsername());
        if(inCallMode)
            callButtonClicked();
//        mCallButton.setEnabled(true);
    }

    private void logoutButtonClicked() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().unregisterPushToken();
            getSinchServiceInterface().stopClient();
        }
        finish();
    }

    private void callButtonClicked() {
//        String userName = mCallName.getText().toString();
        String userName = extraNumber;
        if (userName.isEmpty()) {
            Toast.makeText(this, "Please enter a user to call", Toast.LENGTH_LONG).show();
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
