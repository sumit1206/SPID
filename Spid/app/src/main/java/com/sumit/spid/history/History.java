package com.sumit.spid.history;

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
import com.sumit.spid.MainActivity;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.mydelivery.MyDeliveryDetails;
import com.sumit.spid.mydelivery.adapter.NearestShopDeliveryAdapter;
import com.sumit.spid.mydelivery.adapter.TrackAdapter;
import com.sumit.spid.mydelivery.data.MyDeliveryData;
import com.sumit.spid.mydelivery.data.TrackData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity {


    private RecyclerView historyRecyclerView;
    private List<HistoryData> historyArrayList = new ArrayList<>();
    private HistoryAdapter historyAdapter;
    private LinearLayout item_details_layout;
    HistoryData historyData;
    RecyclerView.LayoutManager layoutManager;

    /** error page credentials*/
    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("History");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /**progress dialog initialization*/
        loading = new ProgressDialog(this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);


//        error page credentials
        errorLinearLayout = findViewById(R.id.error_layout);
        errorImage = findViewById(R.id.error_image);
        errorMessageText = findViewById(R.id.message_error_loading);
        action_text_error = findViewById(R.id.action_text_error);

        historyRecyclerView = findViewById(R.id.history_recycler_view);
        historyRecyclerView.setHasFixedSize(true);
        historyAdapter = new HistoryAdapter(History.this,historyArrayList);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        historyRecyclerView.setLayoutManager(layoutManager);
        historyRecyclerView.setAdapter(historyAdapter);
        historyRecyclerView.setItemAnimator(new DefaultItemAnimator());
        historyData();
    }

    public void historyData() {

        loading.show();
        new RemoteDataDownload(History.this).historyFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                loading.dismiss();
                HistoryData historyData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        String packetId = post.getString("packet_id");
                        String eventId = post.getString("event_id");
                        String type = post.getString("type");
                        String weight = post.getString("weight");
                        String image = post.getString("image_one");
                        String about = post.getString("about");
                        String senderId = post.getString("sender_id");
                        String carrierId = post.getString("carrier_id");
                        String receiverId = post.getString("reciver_id");
                        String receiverName = post.getString("reciver_name");
                        String insurance = post.getString("insurence");
                        String paidStatus = post.getString("paid");
                        String cost = post.getString("cost");
                        String fromAddress = post.getString("from_address");
                        String from[] = fromAddress.split(",");
                        fromAddress = from[0];
                        String toAddress = post.getString("to_address");
                        String to[] = toAddress.split(",");
                        toAddress = to[0];
                        String deliveryTime = post.getString("duration_hr");
                        String progress = post.getString("progress");
                        String status = post.getString("status");
                        historyData = new HistoryData(fromAddress,toAddress,deliveryTime,packetId,eventId,image,receiverId,receiverName,insurance,cost,
                                type,weight,about,status);
                        historyArrayList.add(historyData);
                        historyAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    loading.dismiss();
                    Toast.makeText(History.this, "Server error", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {

                loading.dismiss();
                errorMessageText.setText(R.string.no_history_text);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(History.this).load(R.raw.error_image).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_notification);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent action_intent = new Intent(History.this, MainActivity.class);
                        startActivity(action_intent);
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCatch(JSONException e) {

                loading.dismiss();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(History.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.places_try_again);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        historyData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(VolleyError e) {

                loading.dismiss();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(History.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.places_try_again);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        historyData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);

            }
        },new User(History.this).getPhoneNumber(),"");

    }
}
