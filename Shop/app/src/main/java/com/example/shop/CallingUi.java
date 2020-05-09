package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.shop.aggregator.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

public class CallingUi extends AppCompatActivity {

    ImageView bellImage;
//    private static final String APP_KEY = "3f40f24a-318a-4714-9fbb-a3be2de27f65";
//    private static final String APP_SECRET = "Qvpv6aku6kSrevwkdiNSMw==";
//    private static final String ENVIRONMENT = "clientapi.sinch.com";
    User user;
    private com.sinch.android.rtc.calling.Call call;
    private SinchClient sinchClient;
    FloatingActionButton callEndButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_ui);
        callEndButton = findViewById(R.id.callEndButton);

        String callReceiver = getIntent().getStringExtra("callReceiver");

        bellImage = findViewById(R.id.image_bell);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(bellImage);
        Glide.with(this).load(R.raw.ringing_bell).into(imageViewTarget);

//        /**Initializing sinch*/
//        user = new User(CallingUi.this);
//        sinchClient = Sinch.getSinchClientBuilder()
//                .context(CallingUi.this)
//                .userId(user.getPhoneNumber())
//                .applicationKey(APP_KEY)
//                .applicationSecret(APP_SECRET)
//                .environmentHost(ENVIRONMENT)
//                .build();
//
//        sinchClient.setSupportCalling(true);
//        sinchClient.startListeningOnActiveConnection();
//        sinchClient.start();
//        /***/

        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener());

        if (call == null) {
            try {
                call = sinchClient.getCallClient().callUser(callReceiver);
                call.addCallListener(new SinchCallListener());
            }catch (Exception e){
                throw e;
            }
        }

        callEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call.hangup();
            }
        });
    }

    private class SinchCallListener implements CallListener {
        @Override
        public void onCallProgressing(com.sinch.android.rtc.calling.Call call) {
        }

        @Override
        public void onCallEstablished(com.sinch.android.rtc.calling.Call call) {
            setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
        }

        @Override
        public void onCallEnded(com.sinch.android.rtc.calling.Call endedCall) {
            call = null;
            SinchError a = endedCall.getDetails().getError();
            setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
        }

        @Override
        public void onShouldSendPushNotification(com.sinch.android.rtc.calling.Call call, List<PushPair> list) {

        }
    }

    private class SinchCallClientListener implements CallClientListener {
        @Override
        public void onIncomingCall(CallClient callClient, com.sinch.android.rtc.calling.Call incomingCall) {
            call = incomingCall;
            Toast.makeText(CallingUi.this, "incoming call", Toast.LENGTH_SHORT).show();
            call.answer();
            call.addCallListener(new SinchCallListener());
        }
    }

}
