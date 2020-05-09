package com.example.shop.aggregator.CarrierWallet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.shop.ContactUs;
import com.example.shop.ParseDateTimeStamp;
import com.example.shop.R;
import com.example.shop.aggregator.RemoteConnection.RemoteConnection;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.aggregator.User;
import com.example.shop.aggregator.notificationCarrier.NotificationAggrigator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CarrierWallet extends AppCompatActivity {

    private RecyclerView transactionRecyclerView;
    private List<CarrierWalletTransactionData> transactionArrayList = new ArrayList<>();
    private CarrierWalletAdapter walletTransactionAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog loading;

    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;
    TextView totalBalance;

    Toolbar walletToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrier_wallet);

        walletToolbar = findViewById(R.id.toolbar);
        walletToolbar.setTitle("");
        setSupportActionBar(walletToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        walletToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //error page credentials
        errorLinearLayout = findViewById(R.id.error_layout);
        errorImage = findViewById(R.id.error_image);
        errorMessageText = findViewById(R.id.message_error_loading);
        action_text_error = findViewById(R.id.action_text_error);
        totalBalance = findViewById(R.id.total_balance);

        /**progress dialog initialization*/
        loading = new ProgressDialog(this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        transactionRecyclerView = findViewById(R.id.wallet_transaction_recycler_view);
        transactionRecyclerView.setHasFixedSize(true);
        walletTransactionAdapter = new CarrierWalletAdapter(this,transactionArrayList);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        transactionRecyclerView.setLayoutManager(layoutManager);
        transactionRecyclerView.setAdapter(walletTransactionAdapter);
        transactionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        transactionData();

    }

    public void transactionData() {
        loading.show();
        new RemoteConnection(CarrierWallet.this).walletFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                CarrierWalletTransactionData carrierWalletTransactionData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    String balance = jsonObject.getString("balance");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);

                        String transactionAmount = post.getString("transaction_amount");
                        transactionAmount = getString(R.string.rupees_symbol)+transactionAmount;
                        String operation = post.getString("operation");
                        int thumbnail;
                        if(operation.equals("1")){
                            operation = "Added";
                            thumbnail =  R.drawable.play_for;

                        }else{
                            operation = "Deducted";
                            thumbnail = R.drawable.call_made_black;
                        }
                        String dateTime = post.getString("date_time_stamp");
                        dateTime = new ParseDateTimeStamp(dateTime).getDateTimeInFormat("dd MMM,yy hh:mm a");
                        String transactionId = "";

                        carrierWalletTransactionData = new CarrierWalletTransactionData("", thumbnail, operation, dateTime, transactionAmount);
                        transactionArrayList.add(carrierWalletTransactionData);
                        walletTransactionAdapter.notifyDataSetChanged();
                    }
                    loading.dismiss();
                    totalBalance.setText(getString(R.string.rupees_symbol)+balance);
                } catch (Exception e) {
                    Toast.makeText(CarrierWallet.this, "Oops! an error occurred.", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
//                Toast.makeText(CarrierWallet.this, "No transaction", Toast.LENGTH_LONG).show();
                loading.dismiss();
                totalBalance.setText(getString(R.string.rupees_symbol)+"00");
                errorMessageText.setText(R.string.no_transaction);
                errorLinearLayout.setVisibility(View.VISIBLE);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(CarrierWallet.this).load(R.raw.slow_internet_error).into(imageViewTarget);
//
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(CarrierWallet.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                errorLinearLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(CarrierWallet.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                errorLinearLayout.setVisibility(View.VISIBLE);

            }
        },new User(CarrierWallet.this).getPhoneNumber(),"056");


    }

    public void needOnClick(View view) {
        Intent contactIntent = new Intent(this, ContactUs.class);
        startActivity(contactIntent);
    }
}
