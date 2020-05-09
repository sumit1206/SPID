package com.example.shop.store.stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shop.ParseImage;
import com.example.shop.R;

public class StockItemDetails extends AppCompatActivity {

    Toolbar itemDetailsToolbar;
    TextView packetId, type, description, weight, dimension, type2;
    ImageView image;
    StoreStockData storeStockData;

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

        storeStockData = (StoreStockData) getIntent().getSerializableExtra("dataStock");

        packetId = findViewById(R.id.store_order_id);
        type = findViewById(R.id.store_parcel_type);
        description =findViewById(R.id.store_parcel_description);
        weight =findViewById(R.id.store_weight);
        dimension =findViewById(R.id.store_dimension);
        type2 =findViewById(R.id.store_type);
        image = findViewById(R.id.store_parcel_image);

        packetId.setText(storeStockData.getPacketId());
        type.setText(storeStockData.getType());
        description.setText(storeStockData.getDescription());
        weight.setText(storeStockData.getWeight());
        dimension.setText(storeStockData.getDimension());
        type2.setText(storeStockData.getType());
        new ParseImage(image).setImageString(storeStockData.getImage());

    }
}
