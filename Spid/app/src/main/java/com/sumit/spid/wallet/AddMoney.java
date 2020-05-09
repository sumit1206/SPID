package com.sumit.spid.wallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sumit.spid.Hashids;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.mydelivery.MyDeliveryDetails;
import com.sumit.spid.paytm.checksum;

public class AddMoney extends AppCompatActivity {

    EditText amountEt;
    Button addBtn;
    TextView balanceTv;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Add Money");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        String balance = getIntent().getStringExtra("balance");

        user = new User(AddMoney.this);
        amountEt = findViewById(R.id.add_money_amount);
        addBtn = findViewById(R.id.add_money_button);
        balanceTv = findViewById(R.id.add_money_balance);

        if(balance == null){
            balanceTv.setText("Available balance "+getString(R.string.rupees_symbol)+"00");
        }else {
            balanceTv.setText("Available balance " + getString(R.string.rupees_symbol) + balance);
        }


        final String PRE_ORDER_ID = "SPIDPYTM";

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amountValid()){
                    String amount = amountEt.getText().toString().trim();
                    Intent intent = new Intent(AddMoney.this, checksum.class);
                    Hashids hashids = new Hashids(user.getCurrentTimeStamp(), 6);
                    String timeId = hashids.encode(1, 2, 3);
                    intent.putExtra("orderid", PRE_ORDER_ID + timeId);
                    intent.putExtra("custid", user.getPhoneNumber());
                    intent.putExtra("amount", amount);
//                    intent.putExtra("eventId", myDeliveryData.getEventId());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private boolean amountValid() {
        String amount = amountEt.getText().toString().trim();
        if(amount.equals("")){
            amountEt.setError("Amount cannot be empty");
            amountEt.requestFocus();
            return false;
        }
        else if(Double.valueOf(amount) <= 1.00){
            amountEt.setError("Minimum amount "+getString(R.string.rupees_symbol)+"1");
            amountEt.requestFocus();
            return false;
        }
        return true;
    }
}
