package com.example.shop.aggregator.notificationCarrier;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.shop.ParseDateTimeStamp;
import com.example.shop.R;
import com.example.shop.aggregator.RemoteConnection.RemoteConnection;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.aggregator.User;
import com.example.shop.aggregator.dashbord.CarrierDashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationAggrigator extends AppCompatActivity {

    private List<NotificationData> notificationDataListArray = new ArrayList<>();
    private RecyclerView notificationRecyclerView;
    private NotificationAdapter notificationAdapter;
    ProgressDialog loading;


    //  error page credentials
    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_aggrigator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Notification");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /**progress dialog initialization*/
        loading = new ProgressDialog(NotificationAggrigator.this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        notificationRecyclerView = findViewById(R.id.notification_recyclerView);
        notificationRecyclerView.setHasFixedSize(true);
        notificationAdapter = new NotificationAdapter(NotificationAggrigator.this,getIntent(),this,notificationDataListArray);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        notificationRecyclerView.setLayoutManager(layoutManager);
        notificationRecyclerView.setAdapter(notificationAdapter);
        notificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // notificationRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));



//        error page credentials
        errorLinearLayout = findViewById(R.id.error_layout);
        errorImage = findViewById(R.id.error_image);
        errorMessageText = findViewById(R.id.message_error_loading);
        action_text_error = findViewById(R.id.action_text_error);

    }

    public void notificationData()
    {
        loading.show();
        errorLinearLayout.setVisibility(View.GONE);
        notificationDataListArray.clear();
        new RemoteConnection(NotificationAggrigator.this).carrierNotificationsFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                loading.dismiss();
                NotificationData notificationData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        String body = post.getString("body");
                        String date = post.getString("time");
                        date = new ParseDateTimeStamp(date).getDateTimeInFormat("dd MMM,yy hh:mm a");
                        String eventId = post.getString("event_id");
                        String type = post.getString("type");
//                        String seenStatus = post.getString("departure_group");
                        notificationData = new NotificationData(body,date,eventId,type,"1");
                        notificationDataListArray.add(notificationData);
                        notificationAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(NotificationAggrigator.this, "Oops! an error occurred.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
//                Toast.makeText(NotificationAggrigator.this, "no data", Toast.LENGTH_LONG).show();
                errorLinearLayout.setVisibility(View.VISIBLE);
                errorMessageText.setText(R.string.no_notification_text);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(NotificationAggrigator.this).load(R.raw.error_image).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_notification);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent homeIntent = new Intent(NotificationAggrigator.this,CarrierDashboardActivity.class);
                        startActivity(homeIntent);
                    }
                });
                loading.dismiss();
            }

            @Override
            public void onCatch(JSONException e) {
//                Toast.makeText(NotificationAggrigator.this, "catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(NotificationAggrigator.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notificationData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
                loading.dismiss();
            }

            @Override
            public void onError(VolleyError e) {
//                Toast.makeText(NotificationAggrigator.this, "error"+e.getMessage(), Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.internet_connection_error);
                errorLinearLayout.setVisibility(View.VISIBLE);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(NotificationAggrigator.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notificationData();
                    }
                });
                loading.dismiss();
            }
        },"", "020","","",new User(NotificationAggrigator.this).getPhoneNumber());
    }

    @Override
    protected void onResume() {
        notificationData();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
