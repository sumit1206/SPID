package com.sumit.spid.splash;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Explode;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.material.snackbar.Snackbar;
import com.sumit.spid.MainActivity;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.remote.RemoteDataCheck;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.login.LoginByMail;
import com.sumit.spid.login.OtpActivity;
import com.sumit.spid.login.SignupWithMail;

import org.json.JSONException;

public class SplashScreen extends AppCompatActivity {
    RelativeLayout relativeLayout;
    RemoteDataCheck remoteDataCheck;
    private ImageView cloud1,star;
    Animation animCloud,animStar;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    private ImageView imageView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    User user;
    RelativeLayout l;

/**App execution starting from here
* spid v0.1
* RedMango Analytics
* Sumit
* Shivam
* Soumya*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Explode());
        }

        imageView = findViewById(R.id.splash_image);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(imageView);
        Glide.with(this).load(R.raw.delivery_splash).into(imageViewTarget);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        relativeLayout = findViewById(R.id.splashLayout);
        remoteDataCheck = new RemoteDataCheck(SplashScreen.this);

        /*Calling function to start animation*/
        cloud1      = findViewById(R.id.cloud1);
        star        = findViewById(R.id.star);
        animCloud   = AnimationUtils.loadAnimation(this,R.anim.animcloud);
        animStar    = AnimationUtils.loadAnimation(this,R.anim.animstar);
        cloud1.startAnimation(animCloud);
        star.startAnimation(animStar);

       StartAnimations();
    }
    private void StartAnimations() {
        user = new User(SplashScreen.this);
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
            anim.reset();
            l = (RelativeLayout) findViewById(R.id.splashLayout);
            l.clearAnimation();
            l.startAnimation(anim);

            anim = AnimationUtils.loadAnimation(this, R.anim.translate);
            anim.reset();
            TextView iv = (TextView) findViewById(R.id.appNameText);
            iv.clearAnimation();
            iv.startAnimation(anim);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toNextIntent();
            }

        },3000);


                    /*Checking if User already logged in
                        * If logged in check 'Active status remotely'
                        * * if remotely active
                        * * redirect to MainActivity.java
                        * * else to LoginActivity.java
                        * else to LoginActivity.java*/




//                        SplashScreen.this.finish();
//                    } catch (InterruptedException e) {
//                        // do nothing
//                    } finally {
//                        SplashScreen.this.finish();
//                    }
//                }
//            };
//        splashTread.start();

    }

    public void toNextIntent(){
        if(user.sessionAvailable()) {
            remoteDataCheck.allLogin(new VolleyCallback() {
                @Override
                public void onSuccess(Object result) {
                    int flag = (int) result;
                    if(flag == 1) {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());
                        }

//                        finish();
                    }else if(flag == 0) {
                        Intent intent = new Intent(SplashScreen.this, LoginByMail.class);
                        intent.putExtra("phoneNumber",user.getPhoneNumber());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this).toBundle());
                        }
//                        finish();
                    }else if(flag == -1){
                        Intent intent = new Intent(SplashScreen.this, SignupWithMail.class);
                        intent.putExtra("phoneNumber",user.getPhoneNumber());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
//                        finish();
                    }else {
                        Snackbar.make(l,"An error occurred!",Snackbar.LENGTH_LONG).show();
                    }

                }

                @Override
                public void noDataFound() {

                }
                @Override
                public void onCatch(JSONException e) {
//                    Snackbar.make(l,"Network error" + e.getMessage(),Snackbar.LENGTH_INDEFINITE).show();
                    Snackbar.make(l,"Please check your internet connection!",Snackbar.LENGTH_LONG).show();
                }
                @Override
                public void onError(VolleyError e) {
//                    Snackbar.make(l,"Server error" + e.getMessage(),Snackbar.LENGTH_INDEFINITE).show();
                    Snackbar.make(l,"Please check your internet connection!",Snackbar.LENGTH_LONG).show();
                }
            }, user.getPhoneNumber(), "", "001");
            /**check if remotely active*/
            //TODO: 001 = remotely active checkSplashScreen.this.finish();

        } else{
            Intent intent = new Intent(SplashScreen.this, OtpActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
//            finish();
        }
    }

}