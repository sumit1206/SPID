package com.example.shop.aggregator.dashbord;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shop.CallingUi;
import com.example.shop.ParseImage;
import com.example.shop.R;
import com.example.shop.aggregator.RemoteConnection.RemoteConnection;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.aggregator.User;
import com.example.shop.sinch.LoginActivity;
import com.example.shop.store.StoreMainActivity;
import com.example.shop.store.StoreUser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ItemDetailsAggrigator extends AppCompatActivity {


    private TextView callFirstWheelerBtn,callSecondWheelerFloating;

    TextView orderId, description, price, notes, pickUpTime, weight, dimention, type, type2, parcelStatus;
    TextView toShopName, toShopAddress, fromShopName, fromShopAddress;
    ImageView parcelImage;
    LinearLayout shopDetailsLayout;
    Button qrButton;

    String dSLat, dSLan, dSPhone, pSLat, pSLan, pSPhone;

    String pickupWheelerId, dropWheelerId;
    TextView pickupOnMap, dropOnMap;

    CarrierMainData carrierMainData;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_aggrigator);

        Toolbar toolbar = findViewById(R.id.toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Item details");
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

        /**progress dialog initialization*/
        loading = new ProgressDialog(this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        carrierMainData = (CarrierMainData) getIntent().getSerializableExtra("selectedItemDetails");
        setValues();

        pickupOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String uri = "http://maps.google.com/maps?q=loc:" + pSLat + "," +
                            pSLan + " (" + "Pick from here" + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(ItemDetailsAggrigator.this,"Unable to plot location",Toast.LENGTH_LONG).show();
                }
            }
        });

        dropOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String uri = "http://maps.google.com/maps?q=loc:" + dSLat + "," +
                            dSLan + " (" + "Drop here" + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(ItemDetailsAggrigator.this,"Unable to plot location",Toast.LENGTH_LONG).show();
                }
            }
        });

        callFirstWheelerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(ItemDetailsAggrigator.this, LoginActivity.class);
                callIntent.putExtra("extraNumber",pSPhone);
                callIntent.putExtra("myExtraNumber",new User(ItemDetailsAggrigator.this).getPhoneNumber());
                startActivity(callIntent);
            }
        });

        callSecondWheelerFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(ItemDetailsAggrigator.this, LoginActivity.class);
                callIntent.putExtra("extraNumber",dSPhone);
                callIntent.putExtra("myExtraNumber",new User(ItemDetailsAggrigator.this).getPhoneNumber());
                startActivity(callIntent);
            }
        });

        parcelWheelerFetch();
    }

    void setValues(){
        orderId = findViewById(R.id.order_id_aggregator);
        parcelStatus = findViewById(R.id.parcel_status);
        description = findViewById(R.id.parcel_description);
        price = findViewById(R.id.total_cost);
        notes = findViewById(R.id.notes_for_carrier);
//        pickUpTime = findViewById(R.id.pickup_time);
        weight = findViewById(R.id.weight);
        dimention = findViewById(R.id.dimention);
        type = findViewById(R.id.type);
        type2 = findViewById(R.id.parcel_type);
        parcelImage = findViewById(R.id.parcel_image);
        qrButton = findViewById(R.id.view_qr_button);

        shopDetailsLayout = findViewById(R.id.shop_details_layout);
        toShopName = findViewById(R.id.to_shop_name);
        toShopAddress = findViewById(R.id.to_shop_address);
        fromShopName = findViewById(R.id.from_shop_name);
        fromShopAddress = findViewById(R.id.from_shop_address);
        callFirstWheelerBtn = findViewById(R.id.call_pickup_wheeler_btn);
        callSecondWheelerFloating = findViewById(R.id.call_drop_wheeler_btn);
        pickupOnMap = findViewById(R.id.pickup_location_on_map);
        dropOnMap = findViewById(R.id.drop_loacation_on_map);

        parcelStatus.setText(carrierMainData.getProgress());
        orderId.setText(carrierMainData.getParcelId());
        type2.setText(carrierMainData.getType());
        description.setText(carrierMainData.getDescription());
        price.setText(carrierMainData.getPrice());
        notes.setText(carrierMainData.getNotes());
//        pickUpTime.setText("");
        weight.setText(carrierMainData.getWeight());
        dimention.setText(carrierMainData.getDimension());
        type.setText(carrierMainData.getType());
        new ParseImage(parcelImage).setImageString(carrierMainData.getParcelImage());

        if(carrierMainData.getPaidStatus().equals("0")){
            qrButton.setText("In progress");
        }else{
            qrButton.setText("View QR");
            qrButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent qrIntent = new Intent(ItemDetailsAggrigator.this, QrActivity.class);
                    qrIntent.putExtra("value",carrierMainData.getEventId()+","+new User(ItemDetailsAggrigator.this).getPhoneNumber());
                    qrIntent.putExtra("packetId",carrierMainData.getParcelId());
                    startActivity(qrIntent);
                }
            });
        }

        if(carrierMainData.getProgress().equals("2")){

        }

    }

    void parcelWheelerFetch(){
        loading.show();
        new RemoteConnection(ItemDetailsAggrigator.this).parcelWheelerDetails(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                JSONObject jsonObject = (JSONObject) result;
                try {
                    shopDetailsLayout.setVisibility(View.VISIBLE);
//                    JSONArray jsonArray = jsonObject.getJSONArray("details");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject post = jsonArray.getJSONObject(i);

                        String dSName = jsonObject.getString("drop_shop_name");
                        String dSAddress = jsonObject.getString("drop_address");
                        dSLat = jsonObject.getString("drop_latitude");
                        dSLan = jsonObject.getString("drop_langitude");
                        dSPhone = jsonObject.getString("drop_phone");

                        String pSName = jsonObject.getString("pick_shop_name");
                        String pSAddress = jsonObject.getString("pick_address");
                        pSLat = jsonObject.getString("pick_latitude");
                        pSLan = jsonObject.getString("pick_langitude");
                        pSPhone = jsonObject.getString("pick_phone");

                        fromShopName.setText(pSName);
                        fromShopAddress.setText(pSAddress);

                        toShopName.setText(dSName);
                        toShopAddress.setText(dSAddress);

//                    }
                } catch (Exception e) {
                    Log.println(Log.ASSERT,"Catch error:",Log.getStackTraceString(e));
//                    Toast.makeText(ItemDetailsAggrigator.this, "Oops! an error occurred while fetching delivery address.", Toast.LENGTH_LONG).show();
                }
                loading.dismiss();
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
//                Toast.makeText(ItemDetailsAggrigator.this, "Oops! an error occurred while fetching delivery address.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                Toast.makeText(ItemDetailsAggrigator.this, "Oops! an error occurred while fetching delivery address.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                Toast.makeText(ItemDetailsAggrigator.this, "Oops! an error occurred while fetching delivery address.", Toast.LENGTH_LONG).show();
            }
        },"070",carrierMainData.getEventId(),new User(ItemDetailsAggrigator.this).getPhoneNumber());
    }


}
