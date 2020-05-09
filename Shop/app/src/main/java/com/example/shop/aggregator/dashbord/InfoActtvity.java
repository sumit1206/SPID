package com.example.shop.aggregator.dashbord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.shop.R;
import com.example.shop.aggregator.notificationCarrier.NotificationAggrigator;

public class InfoActtvity extends AppCompatActivity {

    ImageView aggregator_info_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_acttvity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Info");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.clear);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aggregator_info_image = findViewById(R.id.aggregator_info_image);
        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(aggregator_info_image);
        Glide.with(InfoActtvity.this).load(R.raw.fastest_delivery).into(imageViewTarget);


    }
}
