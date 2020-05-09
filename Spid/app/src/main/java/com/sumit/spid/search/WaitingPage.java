package com.sumit.spid.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sumit.spid.MainActivity;
import com.sumit.spid.R;

public class WaitingPage extends AppCompatActivity {

    ImageView light;
    Button btnstop;
    TextView checking;

    Animation roting, btnanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_page);

        light = (ImageView) findViewById(R.id.light);
        btnstop = (Button) findViewById(R.id.btnstop);

        checking = (TextView) findViewById(R.id.checking);

        roting = AnimationUtils.loadAnimation(this, R.anim.roting);
        btnanim = AnimationUtils.loadAnimation(this, R.anim.btnanim);
        light.startAnimation(roting);

        btnstop.startAnimation(btnanim);

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light.clearAnimation();
//                startActivity(new Intent(WaitingPage.this, MainActivity.class));
                SearchActivity.searchActivity.finish();
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        SearchActivity.searchActivity.finish();
        super.onBackPressed();
    }
}
