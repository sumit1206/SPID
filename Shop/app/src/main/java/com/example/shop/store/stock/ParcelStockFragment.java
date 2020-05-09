package com.example.shop.store.stock;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.shop.R;
import com.example.shop.store.StoreUser;
import com.example.shop.store.remote.DbHelper;
import com.example.shop.store.remote.VolleyCallbackStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Parcel stock fragment
 * that shows stock of shop
 */
public class ParcelStockFragment extends Fragment {

    ProgressDialog loading;

    private RecyclerView storeStockRecyclerView;
    private List<StoreStockData>storeStockDataArray = new ArrayList<>();
    StockAdapter stockAdapter;

    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;


    public ParcelStockFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_parcel_stock,container,false);
        storeStockRecyclerView = view.findViewById(R.id.recycler_view_stock);
        storeStockDataArray=new ArrayList<>();
        stockAdapter= new StockAdapter(getContext(),storeStockDataArray);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        storeStockRecyclerView.setLayoutManager(mLayoutManager);
        storeStockRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        storeStockRecyclerView.setAdapter(stockAdapter);

        /**progress dialog initialization*/
        loading = new ProgressDialog(getContext(), R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        //error page credentials
        errorLinearLayout = view.findViewById(R.id.error_layout);
        errorImage = view.findViewById(R.id.error_image);
        errorMessageText = view.findViewById(R.id.message_error_loading);
        action_text_error = view.findViewById(R.id.action_text_error);
        
        stockData();
        return view;
    }

    private void stockData ()
    {
        errorLinearLayout.setVisibility(View.INVISIBLE);
        loading.show();
        new DbHelper(getContext()).fetchStock(new VolleyCallbackStore() {
            @Override
            public void onSuccess(Object result) {
                StoreStockData stockData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        String packetId = post.getString("packet_id");
                        String eventId = post.getString("event_id");
                        String type = post.getString("type");
                        String weight = post.getString("weight");
                        String dimension = post.getString("dimentions");
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

                        stockData = new StoreStockData(fromAddress,toAddress,deliveryTime,packetId,eventId,image,receiverId,receiverName,
                                insurance,cost,type,weight,about,dimension);
                        storeStockDataArray.add(stockData);
                        stockAdapter.notifyDataSetChanged();

                    }
                }catch (Exception e){}
                loading.dismiss();
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                errorMessageText.setText(R.string.no_item_in_stock);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.error_image).into(imageViewTarget);
                errorLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();

                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        stockData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        stockData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }
        },new StoreUser(getContext()).getPhone(),"044");

//        StoreStockData stockData = new StoreStockData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
//        storeStockDataArray.add(stockData);
//
//        stockData = new StoreStockData("120789",R.drawable.parcel_box,"parcel description","Documents","12,sep,19");
//        storeStockDataArray.add(stockData);
//
//         stockData = new StoreStockData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
//        storeStockDataArray.add(stockData);
//
//        stockData = new StoreStockData("120789",R.drawable.parcel_box,"parcel description","Documents","12,sep,19");
//        storeStockDataArray.add(stockData);
//
//         stockData = new StoreStockData("120789",R.drawable.parcel_box,"parcel descrition","Documents","12,sep,19");
//        storeStockDataArray.add(stockData);
//
//        stockData = new StoreStockData("120789",R.drawable.parcel_box,"parcel description","Documents","12,sep,19");
//        storeStockDataArray.add(stockData);

    }

}
