package com.example.shop.store.scanfunctionality;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.store.remote.DbHelper;

public class ScanData extends AppCompatActivity {
    TextView extraData;
    Button b;
    String ev_id;
    int spro;
    String phone;
//    String stn;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_data);

        extraData=findViewById(R.id.show_extra);
        b=findViewById(R.id.btn);

        String message = getIntent().getStringExtra("message");
        extraData.setText(message);
        b.setVisibility(View.VISIBLE);
    }

    public void confrmClk(View view) {
        super.onBackPressed();
//        String progg = Integer.toString(spro);
    }

}
