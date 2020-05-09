package com.sumit.spid.contactus;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.sumit.spid.BugReport;
import com.sumit.spid.R;

public class ContactUs extends AppCompatActivity {

    TextView postal_address;
    EditText nameEt, messageEt;
    LinearLayout addressLayout;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

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
        if(name.length() <= 3){
            nameEt.setError("Valid name required");
        }else if(message.length() < 10){
            messageEt.setError("Valid message required");
        }else {
            new BugReport(ContactUs.this).sendMessage(name+": "+message);
        }
    }
}
