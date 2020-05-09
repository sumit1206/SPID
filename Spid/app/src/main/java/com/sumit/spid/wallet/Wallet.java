package com.sumit.spid.wallet;

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
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.contactus.ContactUs;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.history.History;
import com.sumit.spid.history.HistoryAdapter;
import com.sumit.spid.history.HistoryData;
import com.sumit.spid.offers.OfferDataConfirmOrder;
import com.sumit.spid.offers.OffersViewConfirmOrder;
import com.sumit.spid.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Wallet extends AppCompatActivity {


    private RecyclerView transactionRecyclerView;
    private List<WalletTransactionData> transactionArrayList = new ArrayList<>();
    private WalletTransactionAdapter walletTransactionAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog loading;
    TextView totalBalance, addMoney;

    String balance;

    /**error page credentials*/
    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        totalBalance = findViewById(R.id.total_balance);
        addMoney = findViewById(R.id.add_money_button);


        /**error page credentials*/
        errorLinearLayout = findViewById(R.id.error_layout);
        errorImage = findViewById(R.id.error_image);
        errorMessageText = findViewById(R.id.message_error_loading);
        action_text_error = findViewById(R.id.action_text_error);

        /**progress dialog initialization*/
        loading = new ProgressDialog(this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        transactionRecyclerView = findViewById(R.id.wallet_transaction_recycler_view);
        transactionRecyclerView.setHasFixedSize(true);
        walletTransactionAdapter = new WalletTransactionAdapter(this,transactionArrayList);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        transactionRecyclerView.setLayoutManager(layoutManager);
        transactionRecyclerView.setAdapter(walletTransactionAdapter);
        transactionRecyclerView.setItemAnimator(new DefaultItemAnimator());

        transactionData();

    }

    public void transactionData() {
        loading.show();
        new RemoteDataDownload(Wallet.this).walletFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                WalletTransactionData walletTransactionData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    balance = jsonObject.getString("balance");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);

                        String transactionAmount = post.getString("transaction_amount");
                        String operation = post.getString("operation");
                        if(operation.equals("1")){
                            operation = "Added";
                        }else{
                            operation = "Deducted";
                        }
                        String dateTime = post.getString("date_time_stamp");
                        String transactionId = "";

                        walletTransactionData = new WalletTransactionData("", R.drawable.paytm_logo, operation, dateTime, transactionAmount);
                        transactionArrayList.add(walletTransactionData);
                        loading.dismiss();
                        walletTransactionAdapter.notifyDataSetChanged();
                    }
                    totalBalance.setText(getString(R.string.rupees_symbol)+balance);
                } catch (Exception e) {
                    loading.dismiss();
                    Toast.makeText(Wallet.this, "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                totalBalance.setText(getString(R.string.rupees_symbol)+"00");
//                Toast.makeText(Wallet.this, "No transaction", Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.no_transaction);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(Wallet.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                errorLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
//                Toast.makeText(Wallet.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(Wallet.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        transactionData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
//                Toast.makeText(Wallet.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(Wallet.this).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        transactionData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);

            }
        },new User(Wallet.this).getPhoneNumber(),"056");
    }

    public void tellFriends(View view) {
       Intent contactIntent = new Intent (Wallet.this, ContactUs.class);
       startActivity(contactIntent);
    }

    public void add_money(View view) {
        Intent intent = new Intent(Wallet.this, AddMoney.class);
        intent.putExtra("balance", balance);
        startActivity(intent);
//        Toast.makeText(this,"This Feature is coming soon",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
