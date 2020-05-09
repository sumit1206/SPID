package com.sumit.spid.sinch;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.sinch.android.rtc.calling.Call;

public class CallingClass extends BaseActivity {

    Context context;
    String callReceiver;

    CallingClass(Context context, String callReceiver){
        Log.println(Log.ASSERT,"calling class","---------"+callReceiver);
        this.context = context;
        this.callReceiver = callReceiver;
    }

    @Override
    protected void onServiceConnected() {
        Log.println(Log.ASSERT,"onServiceConnected",callReceiver);
        callButtonClicked();
    }

    private void logoutButtonClicked() {
        if (getSinchServiceInterface() != null) {
            getSinchServiceInterface().unregisterPushToken();
            getSinchServiceInterface().stopClient();
        }
        finish();
    }

    private void callButtonClicked() {
        Log.println(Log.ASSERT,"callButtonClicked",callReceiver);
        String userName = callReceiver;
        if (userName.isEmpty() || userName.equals("")) {
//            Toast.makeText(context, "Please enter a user to call", Toast.LENGTH_LONG).show();
            return;
        }

        Call call = getSinchServiceInterface().callUser(userName);
        String callId = call.getCallId();

        Intent callScreen = new Intent(context, CallScreenActivity.class);
        callScreen.putExtra(SinchService.CALL_ID, callId);
        startActivity(callScreen);
    }
}