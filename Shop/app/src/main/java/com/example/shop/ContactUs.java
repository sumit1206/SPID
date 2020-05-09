package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {

    TextView postal_address;
    LinearLayout addressLayout;
    ScrollView scrollView;

    EditText nameEt, messageEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        scrollView = findViewById(R.id.scroll_contact_scroll);
        nameEt = findViewById(R.id.contact_us_name);
        messageEt = findViewById(R.id.contact_us_message);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Contact us");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addressLayout = findViewById(R.id.address_layout);
        postal_address = findViewById(R.id.postal_address);
        postal_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addressLayout.setVisibility(View.VISIBLE);
                focusOnView();
            }
        });


    }

    private void focusOnView(){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, addressLayout.getBottom());
            }
        });
    }

    public void send(View view) {
        String name = nameEt.getText().toString().trim();
        String message = messageEt.getText().toString().trim();
        if(name.equals("")){
            nameEt.setError("Name required");
            nameEt.requestFocus();
        }else if(message.equals("")){
            messageEt.setError("Message required");
            messageEt.requestFocus();
        }else{
            new BugReport(ContactUs.this,name+": "+message).sendEmail();
        }

    }
}
