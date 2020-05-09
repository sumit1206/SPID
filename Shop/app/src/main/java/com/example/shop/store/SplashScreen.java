package com.example.shop.store;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.store.loginregister.LoginActivity;

public class SplashScreen extends AppCompatActivity
{

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }

    Thread splashTread;
    private ImageView imageView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phoneKey";
    public static final String Password = "passKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = findViewById(R.id.splashView);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

//        StartAnimations();
    }

//    private void StartAnimations() {
//        final User user = new User(SplashScreen.this);
//        if (!isConnected(SplashScreen.this))
//            buildDialog(SplashScreen.this).show();
//        else {
//            Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
//            anim.reset();
//            RelativeLayout l = (RelativeLayout) findViewById(R.id.splashLayout);
//            l.clearAnimation();
//            l.startAnimation(anim);
//
//            anim = AnimationUtils.loadAnimation(this, R.anim.translate);
//            anim.reset();
//            TextView iv = (TextView) findViewById(R.id.appNameText);
//            iv.clearAnimation();
//            iv.startAnimation(anim);
//
//            splashTread = new Thread()
//            {
//                @Override
//                public void run() {
//                    try {
//                        int waited = 0;
//                        // Splash screen pause time
//                        while (waited < 2000) {
//                            sleep(100);
//                            waited += 100;
//                        }
////                        Toast.makeText(SplashScreen.this,user.getStnName(),Toast.LENGTH_LONG).show();
//                        if(new User(SplashScreen.this).getStnName()=="") {
//                            Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
//                            finish();
//                        }else{
//                            Intent intent = new Intent(SplashScreen.this, StoreMainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                            startActivity(intent);
//                            finish();
//                        }
//                        SplashScreen.this.finish();
//                    } catch (InterruptedException e) {
//                        // do nothing
//                    } finally {
//                        SplashScreen.this.finish();
//                    }
//                }
//            };
//            splashTread.start();
//
//        }
//    }


//    public boolean isConnected(Context context) {
//
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netinfo = cm.getActiveNetworkInfo();
//
//        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
//            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
//
//            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
//            else return false;
//        } else
//            return false;
//    }

//    public AlertDialog.Builder buildDialog(Context c) {
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
//        builder.setTitle("No Internet Connection");
//        builder.setMessage("You need to have internet  wifi to access this. Press ok to open Setting");
//
//        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which)
//            {
//                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
//            }
//        });
//        return builder;
//    }
}
