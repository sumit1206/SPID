package com.sumit.spid.mydelivery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sumit.spid.BugReport;
import com.sumit.spid.CoordinateManager;
import com.sumit.spid.Hashids;
import com.sumit.spid.MainActivity;
import com.sumit.spid.ParseDateTimeStamp;
import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.RemoteDataUpload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.mydelivery.adapter.NearestShopDeliveryAdapter;
import com.sumit.spid.mydelivery.adapter.TrackAdapter;
import com.sumit.spid.mydelivery.data.MyDeliveryData;
import com.sumit.spid.mydelivery.data.NearestShopDeliveryData;
import com.sumit.spid.mydelivery.data.TrackData;
import com.sumit.spid.offers.OfferDataConfirmOrder;
import com.sumit.spid.paytm.checksum;
import com.sumit.spid.search.ConfirmationPageSearch;
import com.sumit.spid.search.LatLongManager;
import com.sumit.spid.sinch.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyDeliveryDetails extends AppCompatActivity {

    private static final int CHANGE_RECEIVER_NUMBER = 100;

    private RecyclerView deliveryTrackRecyclerView,deliveryNearestShopRecyclerView;
    private List<TrackData> deliveryTrackArryList = new ArrayList<>();
    private TrackAdapter myTrackAdapter;
    private LinearLayout item_details_layout;
    public static MyDeliveryData myDeliveryData;
    User user;
    NearestShopDeliveryData nearestShopDeliveryData;
    ProgressDialog loading;

    TextView headerText;

    TextView packetId, from, to, deliveryCharge, deliveryWithin;
    Button actionButton;
    TextView type, description;
    TextView receiverName, receiverAddress, receiverNumber;
    ImageView parcelImage;
    TextView shippingPrice, insurancePrice, totalPrice;
    TextView cancelBooking;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    //    nearest shop recyclerView Credentials

    private List<NearestShopDeliveryData> nearestShopArryList = new ArrayList<>();
    private NearestShopDeliveryAdapter nearestShopDeliveryAdapter;
    RecyclerView.LayoutManager layoutManager;

    /**Wheeler info*/
    LinearLayout wheelerInfo;
//    TextView dropShopName, dropShopAddress, dropShopDistance, dropShopGetDirection, dropShopCall;
    TextView pickUpShopName, pickUpShopAddress, pickUpShopDistance, pickUpShopGetDirection, pickUpShopCall;

    String dSLat, dSLan, dSPhone, pSLat, pSLan, pSPhone;

    Button changeReceiverNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_delivery_details);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MyDeliveryDetails.this);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("My delivery details");
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

        /**Initializing UI fields*/
        headerText = findViewById(R.id.header_text);
        packetId = findViewById(R.id.packet_id);
        from = findViewById(R.id.from_address);
        to = findViewById(R.id.to_address);
        parcelImage = findViewById(R.id.parcel_image);
        deliveryCharge = findViewById(R.id.delivery_charge);
        actionButton = findViewById(R.id.action_button);
        deliveryWithin = findViewById(R.id.delivery_within);
        receiverName = findViewById(R.id.receiver_name);
        receiverAddress = findViewById(R.id.full_to_address);
        receiverNumber = findViewById(R.id.receiver_number);
        item_details_layout = findViewById(R.id.item_details_layout);
        type = findViewById(R.id.item_type_delivery_details);
        description = findViewById(R.id.item_description_delivery_details);
        shippingPrice = findViewById(R.id.shipping_price);
        insurancePrice = findViewById(R.id.insurance_price);
        totalPrice = findViewById(R.id.total_price);
        cancelBooking = findViewById(R.id.cancel_booking_button);
        changeReceiverNumber = findViewById(R.id.change_receiver_number);

        //Wheeler info fields
        wheelerInfo = findViewById(R.id.my_delivery_wheeler_info);
//        dropShopName = findViewById(R.id.drop_shop_name);
//        dropShopAddress = findViewById(R.id.drop_shop_address);
//        dropShopDistance = findViewById(R.id.drop_shop_distance);
//        dropShopCall = findViewById(R.id.drop_shop_call_wheeler);
//        dropShopGetDirection = findViewById(R.id.drop_shop_get_direction);

        pickUpShopName = findViewById(R.id.pickup_shop_name);
        pickUpShopAddress = findViewById(R.id.pickup_shop_address);
        pickUpShopDistance = findViewById(R.id.pickup_shop_distance);
        pickUpShopCall = findViewById(R.id.pickup_shop_call_wheeler);
        pickUpShopGetDirection = findViewById(R.id.pickup_shop_get_direction);
        /***/

        myDeliveryData = (MyDeliveryData) getIntent().getSerializableExtra("selectedDeliveryData");
        user = new User(MyDeliveryDetails.this);

        packetId.setText(myDeliveryData.getPacketId());
        from.setText(myDeliveryData.getFrom_address());
        to.setText(myDeliveryData.getTo_address());
        deliveryCharge.setText(getString(R.string.rupees_symbol)+myDeliveryData.getCost());
        deliveryWithin.setText(myDeliveryData.getDelivery_time() + "hrs");
        receiverName.setText(myDeliveryData.getReceiverName());
        receiverNumber.setText(myDeliveryData.getReceiverId());
        receiverAddress.setText(myDeliveryData.getTo_address());
        try {
            new ParseImage(parcelImage).setImageString(myDeliveryData.getImage());
//            Toast.makeText(MyDeliveryDetails.this,"Details try",Toast.LENGTH_LONG).show();
        }catch (Exception e){
//            Toast.makeText(MyDeliveryDetails.this,"Details catch",Toast.LENGTH_LONG).show();
        }
        type.setText(myDeliveryData.getType());
        description.setText(myDeliveryData.getAbout());
        shippingPrice.setText(myDeliveryData.getCost());
        insurancePrice.setText("00");
        totalPrice.setText(myDeliveryData.getCost());

        item_details_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemDetailsIntent = new Intent(MyDeliveryDetails.this, ItemDetailsActivity.class);
                itemDetailsIntent.putExtra("data", myDeliveryData);
                startActivity(itemDetailsIntent);
            }
        });

//        dropShopCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent callIntent = new Intent(MyDeliveryDetails.this, LoginActivity.class);
//                callIntent.putExtra("extraNumber",dSPhone);
//                startActivity(callIntent);
//            }
//        });
//
//        dropShopGetDirection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    String uri = "http://maps.google.com/maps?q=loc:" + dSLat + "," +
//                            dSLan + " (Drop here)";
//                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                    startActivity(intent);
//                }catch (Exception e){
//                    Toast.makeText(MyDeliveryDetails.this,"Unable to plot location",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        pickUpShopCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(MyDeliveryDetails.this, LoginActivity.class);
                callIntent.putExtra("extraNumber",pSPhone);
                startActivity(callIntent);
            }
        });

        pickUpShopGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String uri = "http://maps.google.com/maps?q=loc:" + pSLat + "," +
                            pSLan;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    new BugReport(MyDeliveryDetails.this).shareAnything(uri);
//                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(MyDeliveryDetails.this,"Unable to plot location",Toast.LENGTH_LONG).show();
                }
            }
        });

        /////////////////////////////////////////////////////

        changeReceiverNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changeReceiverNumberIntent = new Intent(MyDeliveryDetails.this, ChangeReceiverNumber.class);
                changeReceiverNumberIntent.putExtra("eventId",myDeliveryData.getEventId());
                changeReceiverNumberIntent.putExtra("packetId",myDeliveryData.getPacketId());
                startActivityForResult(changeReceiverNumberIntent, CHANGE_RECEIVER_NUMBER);
            }
        });

       //TODO: Delivery track recycler view credentials

        deliveryTrackRecyclerView = findViewById(R.id.status_track_ist);
        deliveryTrackRecyclerView.setHasFixedSize(true);
        myTrackAdapter = new TrackAdapter(MyDeliveryDetails.this,deliveryTrackArryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        deliveryTrackRecyclerView.setLayoutManager(layoutManager);
        deliveryTrackRecyclerView.setAdapter(myTrackAdapter);
        deliveryTrackRecyclerView.setItemAnimator(new DefaultItemAnimator());


        /**Nearest wheeler credential complete*/

        //TODO: Nearest shop recycler view credentials

        deliveryNearestShopRecyclerView = findViewById(R.id.nearest_drop_point_recyclerView);
        deliveryNearestShopRecyclerView.setHasFixedSize(true);
        nearestShopDeliveryAdapter = new NearestShopDeliveryAdapter(MyDeliveryDetails.this,nearestShopArryList);
        layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        deliveryNearestShopRecyclerView.setLayoutManager(layoutManager);
        deliveryNearestShopRecyclerView.setAdapter(nearestShopDeliveryAdapter);
        deliveryNearestShopRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if(myDeliveryData.getProgress().equals("0")) {
            getDeviceLocation();
        }else{
            trackData();
        }

        /**Nearest wheeler credential complete*/

    }


    private void getDeviceLocation() {
        /**
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        // Set the map's camera position to the current location of the device.
                        Location mLastKnownLocation = task.getResult();
                        LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
                        String myLatitude, myLongitude;
                        myLatitude = String.valueOf(latLng.latitude);
                        myLongitude = String.valueOf(latLng.longitude);
                        nearestShopData(myLatitude,myLongitude);
//                        Toast.makeText(MyDeliveryDetails.this,"Done: "+myLatitude+"+"+myLongitude,Toast.LENGTH_LONG).show();
                        Log.println(Log.ASSERT, "If", latLng.latitude+"+"+latLng.longitude);
                    } else {
                        Toast.makeText(MyDeliveryDetails.this,"Oops, your location is unavailable.Cant find your nearest hub.",Toast.LENGTH_LONG).show();
                        Log.println(Log.ASSERT, "else","task not successful");
                    }
                }
            });
        } catch (SecurityException e)  {
            Toast.makeText(MyDeliveryDetails.this,"Oops, your location is unavailable.",Toast.LENGTH_LONG).show();
            Log.println(Log.ASSERT, "Catch",Log.getStackTraceString(e));
        }
    }

    /**Finding nearest hub*/

    private void nearestShopData(final String myLatitude, final String myLongitude)
    {
        nearestShopArryList.clear();
        Log.println(Log.ASSERT,"nearestShopData: ","caller");
        new RemoteDataDownload(MyDeliveryDetails.this).nearestWheelerFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                JSONObject jsonObject = (JSONObject) result;
                try {
                    headerText.setVisibility(View.VISIBLE);
                    headerText.setText("Nearest drop points");
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);

                        String id = post.getString("id");
                        String shopName = post.getString("shop_name");
                        String ownerName = post.getString("owner_name");
                        String phoneNumber = post.getString("phone");
                        String address = post.getString("address");
                        String imageString = post.getString("shop_image");
                        String landmark = post.getString("landmark");
                        String latitude = post.getString("latitude");
                        String longitude = post.getString("langitude");
                        String type = "";
                        String rating = "4";
                        Double d = CoordinateManager.findDistance(myLatitude,myLongitude,latitude,longitude,0.0,0.0);
                        String dUnit = "m";
                        if(d >= 1000){
                            d = d/1000;
                            d = (int)(Math.round(d*100))/100.0;
                            dUnit = "km";
                        }else{
                            d = (int)(Math.round(d*100))/100.0;
                        }
                        String distance = String.valueOf(d)+" "+dUnit;
                        String openStatus = post.getString("status");
                        if(openStatus.equals("1"))
                            openStatus = "open";
                        else
                            openStatus = "close";
                        nearestShopDeliveryData = new NearestShopDeliveryData(id,shopName,ownerName,phoneNumber,
                                address,imageString,landmark,latitude,longitude,type,rating,distance,openStatus);
                        nearestShopArryList.add(nearestShopDeliveryData);
                        nearestShopDeliveryAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(MyDeliveryDetails.this, "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
                nearestShopDeliveryAdapter.notifyDataSetChanged();
                  Toast.makeText(MyDeliveryDetails.this, "No data found", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                nearestShopDeliveryAdapter.notifyDataSetChanged();
                  Toast.makeText(MyDeliveryDetails.this, "catch"+e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                nearestShopDeliveryAdapter.notifyDataSetChanged();
                  Toast.makeText(MyDeliveryDetails.this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        },myLatitude,myLongitude);
    }

    /**Tracking parcel*/

    public void trackData()
    {
        new RemoteDataDownload(MyDeliveryDetails.this).trackParcel(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                TrackData trackData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    headerText.setVisibility(View.VISIBLE);
                    headerText.setText("Your parcel is here");
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);

                        String time = post.getString("start_datetime_stamp");
                        time = new ParseDateTimeStamp(time).getDateTimeInFormat("dd MMM,yy hh:mm a");
                        String position = post.getString("short_desc");

                        trackData = new TrackData(position, time);
                        deliveryTrackArryList.add(trackData);
                        myTrackAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(MyDeliveryDetails.this, "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
                Toast.makeText(MyDeliveryDetails.this, "No data found", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                Toast.makeText(MyDeliveryDetails.this, "No catch", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                Toast.makeText(MyDeliveryDetails.this, "No error", Toast.LENGTH_LONG).show();
            }
        },"058",myDeliveryData.getPacketId());
    }

    /**Fetching pickup and drop store info*/

    void parcelWheelerFetch(){
        new RemoteDataDownload(MyDeliveryDetails.this).parcelWheelerDetails(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                JSONObject post = (JSONObject) result;
                try {
                    wheelerInfo.setVisibility(View.VISIBLE);
//                    JSONArray jsonArray = jsonObject.getJSONArray("details");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject post = jsonArray.getJSONObject(i);

//                        String dSName = post.getString("drop_shop_name");
//                        String dSAddress = post.getString("drop_address");
//                        dSLat = post.getString("drop_latitude");
//                        dSLan = post.getString("drop_langitude");
//                        dSPhone = post.getString("drop_phone");

                        String pSName = post.getString("shop_name");
                        String pSAddress = post.getString("address");
                        pSLat = post.getString("latitude");
                        pSLan = post.getString("langitude");
                        pSPhone = post.getString("phone");
                        String rec_lat  = post.getString("rec_lat");
                        String rec_lon  = post.getString("rec_lan");

                        Double distance = CoordinateManager.findDistance(pSLat,pSLan,rec_lat,rec_lon,0.0,0.0);
                        String unit = "m";
                        if(distance >= 1000){
                            distance = distance/1000;
                            unit = "km";
                        }
                        distance = (int)(distance*100)/100.0;

//                        dropShopName.setText(dSName);
//                        dropShopAddress.setText(dSAddress);

                        pickUpShopName.setText(pSName);
                        pickUpShopAddress.setText(pSAddress);
                        pickUpShopDistance.setText(String.valueOf(distance)+unit);

//                    }
                } catch (Exception e) {
                    Toast.makeText(MyDeliveryDetails.this, "Cannot fetch hub details", Toast.LENGTH_LONG).show();
                    Log.println(Log.ASSERT,"CATCH",Log.getStackTraceString(e));
                }

            }

            @Override
            public void noDataFound() {
                Toast.makeText(MyDeliveryDetails.this, "Cannot fetch hub details", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                Toast.makeText(MyDeliveryDetails.this, "Cannot fetch hub details", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                Toast.makeText(MyDeliveryDetails.this, "Cannot fetch hub details", Toast.LENGTH_LONG).show();
            }
        },"072",myDeliveryData.getEventId(),new User(MyDeliveryDetails.this).getPhoneNumber());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHANGE_RECEIVER_NUMBER:
                    String n = data.getStringExtra("number");
                    receiverNumber.setText(n);
                    break;

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myDeliveryData.getProgress().equals("0")) {

            if (myDeliveryData.getPaidStatus().equals("0")) {

                cancelBooking.setVisibility(View.VISIBLE);
                cancelBooking.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cancelIntent = new Intent(MyDeliveryDetails.this, CancelDelivery.class);
                        cancelIntent.putExtra("cancelData", myDeliveryData);
                        startActivity(cancelIntent);
                    }
                });

                actionButton.setText("PAY NOW");
                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MyDeliveryDetails.this, checksum.class);
                        Hashids hashids = new Hashids(user.getCurrentTimeStamp(), 6);
                        String timeId = hashids.encode(1, 2, 3);
                        intent.putExtra("orderid", myDeliveryData.getPacketId() + timeId);
                        intent.putExtra("custid", user.getPhoneNumber());
                        intent.putExtra("amount", myDeliveryData.getCost());
                        intent.putExtra("eventId", myDeliveryData.getEventId());
                        startActivity(intent);
                    }
                });
            } else {
                actionButton.setText("VIEW QR");
                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent QrIntent = new Intent(MyDeliveryDetails.this, QrActivity.class);
                        QrIntent.putExtra("value", myDeliveryData.getEventId() + "," + user.getPhoneNumber());
                        QrIntent.putExtra("packetId",myDeliveryData.getPacketId());
                        startActivity(QrIntent);
                    }
                });
            }
        }else{
//            getPickUpAndDropWheelerInfo();
            parcelWheelerFetch();
            actionButton.setText("GENERATE OTP");
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent otpIntent = new Intent(MyDeliveryDetails.this, OfflineOtp.class);
                    otpIntent.putExtra("event_id", myDeliveryData.getEventId());
                    otpIntent.putExtra("receiver",myDeliveryData.getReceiverId());
                    otpIntent.putExtra("packet_id",myDeliveryData.getPacketId());
                    startActivity(otpIntent);
                }
            });
        }

    }
}
