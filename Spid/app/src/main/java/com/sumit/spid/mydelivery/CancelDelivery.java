package com.sumit.spid.mydelivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.sumit.spid.MainActivity;
import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import com.sumit.spid.databasesupport.remote.RemoteDataUpload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.mydelivery.data.MyDeliveryData;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CancelDelivery extends AppCompatActivity {

    TextView orderId, type, description, price;
    ImageView image;
    Spinner reason;
    EditText comments;
    Button cancel;

    MyDeliveryData myDeliveryData;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_delivery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Order cancellation");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.clear);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /**progress dialog initialization*/
        loading = new ProgressDialog(this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        orderId = findViewById(R.id.cancel_order_id);
        type = findViewById(R.id.cancel_tyoe);
        description = findViewById(R.id.cancel_description);
        price = findViewById(R.id.cancel_price);
        image = findViewById(R.id.cancel_image);
        reason = findViewById(R.id.cancel_reason);
        comments = findViewById(R.id.cancel_comments);
        cancel = findViewById(R.id.cancel_confirm);

        myDeliveryData = (MyDeliveryData) getIntent().getSerializableExtra("cancelData");

        final List<String> reasonList = new ArrayList<String>();
        reasonList.add("Select reason");
        reasonList.add("I didn't like your service");
        reasonList.add("More fast delivery needed");
        reasonList.add("No nearby hub available");
        reasonList.add("Personal reasons");
        reasonList.add("My reason is not listed");

        ArrayAdapter<String> adapterReasons = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, reasonList);
        adapterReasons.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        reason.setAdapter(adapterReasons);
        orderId.setText(myDeliveryData.getPacketId());
        type.setText(myDeliveryData.getType());
        description.setText(myDeliveryData.getAbout());
        price.setText(getString(R.string.rupees_symbol)+myDeliveryData.getCost());
        new ParseImage(image).setImageString(myDeliveryData.getImage());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateCancel();
            }
        });
    }

    void validateCancel(){
        if(reason.getSelectedItem().toString().equals("Select reason")){
            Toast.makeText(CancelDelivery.this,"Reason is required",Toast.LENGTH_LONG).show();
        }else{
            cancelBooking();
        }
    }

    /**Cancel booking*/
    private void cancelBooking() {
        loading.show();
        new RemoteDataUpload(CancelDelivery.this).cancelBooking(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                Toast.makeText(CancelDelivery.this,"Cancelled successfully",Toast.LENGTH_LONG).show();
                loading.dismiss();
                startActivity(new Intent(CancelDelivery.this, ConfirmCancel.class));
                finish();
            }

            @Override
            public void noDataFound() {
                Toast.makeText(CancelDelivery.this,"Cancellation failed",Toast.LENGTH_LONG).show();
                loading.dismiss();
            }

            @Override
            public void onCatch(JSONException e) {
                Toast.makeText(CancelDelivery.this,"Cancellation failed",Toast.LENGTH_LONG).show();
                loading.dismiss();
            }

            @Override
            public void onError(VolleyError e) {
                Toast.makeText(CancelDelivery.this,"Cancellation failed",Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        },myDeliveryData.getEventId(),myDeliveryData.getPacketId(),"050");
    }


}
