package com.example.shop.store.StoreTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop.R;
import com.example.shop.store.stock.StockAdapter;
import com.example.shop.store.stock.StoreStockData;

import java.util.ArrayList;
import java.util.List;

public class StoreTransaction extends AppCompatActivity {


    private RecyclerView recycler_view_store_transaction;
    private List<StoreTransactionData> storeTransactionsArraList = new ArrayList<>();
    StoreTransactionAdapter storeTransactionAdapter;


    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;

    Toolbar transactionToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_transaction);

        transactionToolbar = findViewById(R.id.toolbar);
        transactionToolbar.setTitle("Transactions");
        setSupportActionBar(transactionToolbar);
        transactionToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        recycler_view_store_transaction = findViewById(R.id.recycler_view_store_transaction);
//        storeTransactionsArraList = new ArrayList<>();
//        storeTransactionAdapter= new StoreTransactionAdapter(StoreTransaction.this,storeTransactionsArraList);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        recycler_view_store_transaction.setLayoutManager(mLayoutManager);
//        recycler_view_store_transaction.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
//        recycler_view_store_transaction.setAdapter(storeTransactionAdapter);
//
//        transactionHistory();

    }

    private void transactionHistory() {

        StoreTransactionData storeTransactionData = new StoreTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        storeTransactionsArraList.add(storeTransactionData);

        storeTransactionData = new StoreTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        storeTransactionsArraList.add(storeTransactionData);

        storeTransactionData = new StoreTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        storeTransactionsArraList.add(storeTransactionData);

        storeTransactionData =   new StoreTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        storeTransactionsArraList.add(storeTransactionData);

        storeTransactionData = new StoreTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        storeTransactionsArraList.add(storeTransactionData);

        storeTransactionData = new StoreTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        storeTransactionsArraList.add(storeTransactionData);

        storeTransactionData = new StoreTransactionData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
        storeTransactionsArraList.add(storeTransactionData);
    }
}
