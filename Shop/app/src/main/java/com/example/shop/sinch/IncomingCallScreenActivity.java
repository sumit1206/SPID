package com.example.shop.sinch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.shop.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallEndCause;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

public class IncomingCallScreenActivity extends BaseActivity {

    static final String TAG = IncomingCallScreenActivity.class.getSimpleName();
    private String mCallId;
    private AudioPlayer mAudioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incoming);

        Log.println(Log.ASSERT,"IncomingCallScreenActi","onCreate");

        FloatingActionButton answer = findViewById(R.id.answerButton);
        answer.setOnClickListener(mClickListener);
        FloatingActionButton decline = findViewById(R.id.declineButton);
        decline.setOnClickListener(mClickListener);

        mAudioPlayer = new AudioPlayer(this);
        mAudioPlayer.playRingtone();
        mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.println(Log.ASSERT,"IncomingCallScreenActi","onResume");
        if (getIntent() != null)
        {
            if (getIntent().getStringExtra(SinchService.CALL_ID) != null)
            {
                mCallId = getIntent().getStringExtra(SinchService.CALL_ID);
            }
        }
    }


    @Override
    protected void onServiceConnected() {
        Log.println(Log.ASSERT,"IncomingCallScreenActi","onServiceConnected");
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.addCallListener(new SinchCallListener());
            TextView remoteUser = (TextView) findViewById(R.id.remoteUser);
//            remoteUser.setText(call.getRemoteUserId());
            remoteUser.setText("SPID user");
        } else {
            Log.e(TAG, "Started with invalid callId, aborting");
            finish();
        }
    }

    private void answerClicked() {
        Log.println(Log.ASSERT,"IncomingCallScreenActi","answerClicked");
        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            Log.d(TAG, "Answering call");
            call.answer();
            Intent intent = new Intent(this, CallScreenActivity.class);
            intent.putExtra(SinchService.CALL_ID, mCallId);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    private void declineClicked() {
        Log.println(Log.ASSERT,"IncomingCallScreenActi","declineClicked");
        mAudioPlayer.stopRingtone();
        Call call = getSinchServiceInterface().getCall(mCallId);
        if (call != null) {
            call.hangup();
        }
        finish();
    }

    private class SinchCallListener implements CallListener {

        @Override
        public void onCallEnded(Call call) {
            Log.println(Log.ASSERT,"IncomingCallScreenActi","onCallEnded");
            CallEndCause cause = call.getDetails().getEndCause();
            Log.d(TAG, "Call ended, cause: " + cause.toString());
            mAudioPlayer.stopRingtone();
            Intent mainActivity = new Intent(IncomingCallScreenActivity.this, PlaceCallActivity.class);
            mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(mainActivity);
            finish();
//            onBackPressed();
        }

        @Override
        public void onCallEstablished(Call call) {
            Log.println(Log.ASSERT,"IncomingCallScreenActi","onCallEstablished");
            Log.d(TAG, "Call established");
            finish();
//            onBackPressed();
        }

        @Override
        public void onCallProgressing(Call call) {
            Log.println(Log.ASSERT,"IncomingCallScreenActi","onCallProgressing");
            Log.d(TAG, "Call progressing");
            finish();
//            onBackPressed();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> pushPairs) {
            // no need to implement for managed push
        }

    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.answerButton:
                    answerClicked();
                    break;
                case R.id.declineButton:
                    declineClicked();
                    break;
            }
        }
    };
}
