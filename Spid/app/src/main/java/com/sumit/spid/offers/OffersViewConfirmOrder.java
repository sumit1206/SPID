package com.sumit.spid.offers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.sumit.spid.R;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.history.History;
import com.sumit.spid.history.HistoryAdapter;
import com.sumit.spid.history.HistoryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OffersViewConfirmOrder extends AppCompatActivity {


    private RecyclerView offerRecyclerView;
    private List<OfferDataConfirmOrder> offerArrayList = new ArrayList<>();
    private OfferAdapter offerAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_view_confirm_order);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Offers");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        offerRecyclerView = findViewById(R.id.offersRecyclerView);
        offerRecyclerView.setHasFixedSize(true);
        offerAdapter = new OfferAdapter(OffersViewConfirmOrder.this,offerArrayList);
        layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        offerRecyclerView.setLayoutManager(layoutManager);
        offerRecyclerView.setAdapter(offerAdapter);
        offerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        offerData();

    }

    private void offerData() {
        new RemoteDataDownload(this).promoCodeFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                OfferDataConfirmOrder offerDataConfirmOrder;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);

                        String validTill = post.getString("valid_till");
                        String discountAmount = post.getString("discount_amount");
                        String code = post.getString("code");

                        offerDataConfirmOrder = new OfferDataConfirmOrder(discountAmount,code,validTill);
                        offerArrayList.add(offerDataConfirmOrder);

                        offerAdapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    Toast.makeText(OffersViewConfirmOrder.this, "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {

            }

            @Override
            public void onCatch(JSONException e) {

            }

            @Override
            public void onError(VolleyError e) {

            }
        },"054");

//        OfferDataConfirmOrder offerDataConfirmOrder = new OfferDataConfirmOrder("Flat 50% off","Promo code :"+"SPID1ST","get 50 % off on first booking");
//        offerArrayList.add(offerDataConfirmOrder);
//
//        offerDataConfirmOrder = new OfferDataConfirmOrder("Upto 80% off","Promo code :"+"SPID1ST","get upto 80 % off on first booking");
//        offerArrayList.add(offerDataConfirmOrder);
//
//        offerAdapter.notifyDataSetChanged();
    }
////////////////////////////////////////////////////////////////////////////////////

    public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

        private Context offerContext;
        private List<OfferDataConfirmOrder> offerArrayList;
        public OfferAdapter(Context offerContext, List<OfferDataConfirmOrder> offerArrayList) {
            this.offerContext = offerContext;
            this.offerArrayList = offerArrayList;
        }

        @NonNull
        @Override
        public OfferAdapter.OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_offers_view_confirm_order, parent, false);
            return new OfferAdapter.OfferViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull OfferAdapter.OfferViewHolder holder, final int position) {

            OfferDataConfirmOrder offerDataConfirmOrder = offerArrayList.get(position);
            holder.offer_card.setAnimation(AnimationUtils.loadAnimation(offerContext, R.anim.fade_in));
            holder.offerTitle.setText(offerDataConfirmOrder.getOfferTitle());
            holder.offerPromoCode.setText(offerDataConfirmOrder.getOfferPromoCode());
            holder.offerDetails.setText(offerDataConfirmOrder.offerDetails);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    success(offerArrayList.get(position));
                }
            });
        }

        @Override
        public int getItemCount() {
            return offerArrayList.size();
        }

        public class OfferViewHolder extends RecyclerView.ViewHolder {

            TextView offerTitle,offerPromoCode,offerDetails,termsOffer;
            CardView offer_card;

            public OfferViewHolder(@NonNull View itemView) {
                super(itemView);

                offerTitle = itemView.findViewById(R.id.offer_title);
                offerPromoCode = itemView.findViewById(R.id.promo_code);
                offerDetails = itemView.findViewById(R.id.offer_details_confirm_order);
                termsOffer = itemView.findViewById(R.id.terms_offer);
                offer_card = itemView.findViewById(R.id.offer_card);

            }
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////




    void success(OfferDataConfirmOrder offerDataConfirmOrder){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("codeData",offerDataConfirmOrder);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

}
