package com.sumit.spid.mydelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.sumit.spid.MainActivity;
import com.sumit.spid.R;
import com.sumit.spid.history.History;
import com.sumit.spid.search.SearchActivity;

public class ConfirmCancel extends AppCompatActivity {
    TextView check_status_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_cancel);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Cancelled");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.clear);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        check_status_cancel = findViewById(R.id.check_status_cancel);
        check_status_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent confirmIntent = new Intent(ConfirmCancel.this, History.class);
                startActivity(confirmIntent);
            }
        });
    }

    public void continue_send(View view) {
        Intent searchIntent = new Intent (ConfirmCancel.this, SearchActivity.class);
        startActivity(searchIntent);
    }

    public void myDelivery(View view) {
        Intent deliveryIntent = new Intent(ConfirmCancel.this, MainActivity.class);
        startActivity(deliveryIntent);
    }
}
