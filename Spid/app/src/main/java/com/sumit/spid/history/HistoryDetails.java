package com.sumit.spid.history;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.sumit.spid.ParseImage;
import com.sumit.spid.R;

public class HistoryDetails extends AppCompatActivity {

    HistoryData historyData;
    TextView packetId, from, to, type, description, price, receiverName, receiverAddress, receiverNumber;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_details);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Delivery details");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        historyData = (HistoryData) getIntent().getSerializableExtra("historyData");

        packetId = findViewById(R.id.history_packet_id);
        from = findViewById(R.id.history_from_address);
        to = findViewById(R.id.history_to_address);
        type = findViewById(R.id.history_type);
        description = findViewById(R.id.history_description);
        price = findViewById(R.id.history_delivery_charge);
        receiverName = findViewById(R.id.history_receiver_name);
        receiverAddress = findViewById(R.id.history_receiver_address);
        receiverNumber = findViewById(R.id.history_receiver_number);
        image = findViewById(R.id.history_parcel_image);

        packetId.setText(historyData.getPacketId());
        from.setText(historyData.getFrom_address());
        to.setText(historyData.getTo_address());
        type.setText(historyData.getType());
        description.setText(historyData.getDescription());
        price.setText(getString(R.string.rupees_symbol)+historyData.getPrice());
        receiverName.setText(historyData.getReceiverName());
        receiverAddress.setText(historyData.getTo_address());
        receiverNumber.setText(historyData.getReceiverId());
        new ParseImage(image).setImageString(historyData.getImage());

    }
}
