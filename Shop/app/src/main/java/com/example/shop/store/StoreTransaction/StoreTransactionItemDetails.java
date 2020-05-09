package com.example.shop.store.StoreTransaction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;

import com.example.shop.R;

public class StoreTransactionItemDetails extends AppCompatActivity {

    Toolbar itemDetailsToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_transaction_item_details);

        itemDetailsToolbar = findViewById(R.id.toolbar);
        itemDetailsToolbar.setTitle("Item Details");
        setSupportActionBar(itemDetailsToolbar);
        itemDetailsToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
