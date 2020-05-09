package com.example.shop.aggregator.CarrierTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shop.R;
import com.example.shop.store.StoreTransaction.StoreTransaction;
import com.example.shop.store.StoreTransaction.StoreTransactionAdapter;
import com.example.shop.store.StoreTransaction.StoreTransactionData;

import java.util.ArrayList;
import java.util.List;

public class CarrierTransaction extends AppCompatActivity {

    private RecyclerView recycler_view_carrier_transaction;
    private List<CarrierTransactionData> carrierTransactionDataArrayList = new ArrayList<>();
    CarrierTransactionAdapter carrierTransactionAdapter;

    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;

    Toolbar transactionToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier_transaction);

        transactionToolbar = findViewById(R.id.toolbar);
        transactionToolbar.setTitle("Transactions");
        setSupportActionBar(transactionToolbar);
        transactionToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        transactionToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        recycler_view_carrier_transaction = findViewById(R.id.recycler_view_carrier_transaction);
//        carrierTransactionDataArrayList = new ArrayList<>();
//        carrierTransactionAdapter= new CarrierTransactionAdapter(CarrierTransaction.this,carrierTransactionDataArrayList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        recycler_view_carrier_transaction.setLayoutManager(mLayoutManager);
//        recycler_view_carrier_transaction.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recycler_view_carrier_transaction.setAdapter(carrierTransactionAdapter);
//        transactionHistory();
    }
    private void transactionHistory() {

        CarrierTransactionData carrierTransactionData = new CarrierTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        carrierTransactionDataArrayList.add(carrierTransactionData);

        carrierTransactionData = new CarrierTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        carrierTransactionDataArrayList.add(carrierTransactionData);

        carrierTransactionData = new CarrierTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        carrierTransactionDataArrayList.add(carrierTransactionData);

        carrierTransactionData = new CarrierTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        carrierTransactionDataArrayList.add(carrierTransactionData);

        carrierTransactionData = new CarrierTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        carrierTransactionDataArrayList.add(carrierTransactionData);

        carrierTransactionData = new CarrierTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        carrierTransactionDataArrayList.add(carrierTransactionData);

    }
}
