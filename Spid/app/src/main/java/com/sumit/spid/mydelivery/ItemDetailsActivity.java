package com.sumit.spid.mydelivery;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import com.sumit.spid.mydelivery.data.MyDeliveryData;

public class ItemDetailsActivity extends AppCompatActivity {

    ImageView image;
    TextView type, description, weight, dimension, type2;
    MyDeliveryData myDeliveryData;

    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_layout);


        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Change number");
        mToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        myDeliveryData = (MyDeliveryData) getIntent().getSerializableExtra("data");
        image = findViewById(R.id.parcel_image);
        type = findViewById(R.id.parcel_type);
        description = findViewById(R.id.parcel_description);
        weight = findViewById(R.id.parcel_weight);
        dimension = findViewById(R.id.parcel_dimension);
        type2 = findViewById(R.id.parcel_type_2);

        new ParseImage(image).setImageString(myDeliveryData.getImage());
        type.setText(myDeliveryData.getType());
        description.setText(myDeliveryData.getAbout());
        weight.setText(myDeliveryData.getWeight()+" gm");
        dimension.setText(myDeliveryData.getDimension());
        type2.setText(myDeliveryData.getType());
    }
}
