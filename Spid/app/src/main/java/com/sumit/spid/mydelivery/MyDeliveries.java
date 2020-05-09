package com.sumit.spid.mydelivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.sumit.spid.MainActivity;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.mydelivery.adapter.MyDeliveryAdapter;
import com.sumit.spid.mydelivery.data.MyDeliveryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyDeliveries extends Fragment {

    private List<MyDeliveryData> myDeliveryArryList = new ArrayList<>();
    private RecyclerView myDeliveriesRecyclerView;
    private MyDeliveryAdapter myDeliveryAdapter;
    ProgressDialog loading;


    /**error page credentials*/
    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;

    SwipeRefreshLayout mSwipeRefreshLayout;

    public MyDeliveries() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_deliveries,container,false);
        myDeliveriesRecyclerView = view.findViewById(R.id.myDeliveries_recycler_view);
        myDeliveryAdapter = new MyDeliveryAdapter(getContext(),myDeliveryArryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        myDeliveriesRecyclerView.setLayoutManager(layoutManager);
        myDeliveriesRecyclerView.setAdapter(myDeliveryAdapter);
//        myDeliveriesRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        myDeliveriesRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(2), true));

        /**progress dialog initialization*/
        loading = new ProgressDialog(getContext(), R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mSwipeRefreshLayout = view.findViewById(R.id.my_deliveries_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green_color, R.color.text_color);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        //LOAD FETCH URL
                        prepareDeliveryData();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });



        /**error page credentials*/
        errorLinearLayout = view.findViewById(R.id.error_layout);
        errorImage = view.findViewById(R.id.error_image);
        errorMessageText = view.findViewById(R.id.message_error_loading);
        action_text_error = view.findViewById(R.id.action_text_error);
        prepareDeliveryData();
        return view;
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge)
            {
                outRect.left = spacing - column * spacing / spanCount;  //spacing - column * ((1f / spanCount) * spacing);
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount)
                { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            }
            else
            {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp)
    {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void prepareDeliveryData (){
        myDeliveryArryList.clear();
        loading.show();
        errorLinearLayout.setVisibility(View.GONE);
        new RemoteDataDownload(getContext()).myDeliveriesFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                MyDeliveryData myDeliveryData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        String packetId = post.getString("packet_id");
                        String eventId = post.getString("event_id");
                        String image = post.getString("image_one");
                        String senderId = post.getString("sender_id");
                        String carrierId = post.getString("carrier_id");
                        String receiverId = post.getString("reciver_id");
                        String receiverName = post.getString("reciver_name");
                        String insurance = post.getString("insurence");
                        String paidStatus = post.getString("paid");
                        String cost = post.getString("cost");
                        String fromAddress = post.getString("from_address");
//                        String from[] = fromAddress.split(",");
//                        fromAddress = from[0];
                        String toAddress = post.getString("to_address");
//                        String to[] = toAddress.split(",");
//                        toAddress = to[0];
                        String deliveryTime = post.getString("duration_hr");
                        String progress = post.getString("progress");
                        String type = post.getString("type");
                        String weight = post.getString("weight");
                        String about = post.getString("about");
                        String dimension = post.getString("dimentions");
//                        String dimension = "to be fetched";
                        myDeliveryData = new MyDeliveryData(fromAddress,toAddress,deliveryTime,progress,packetId,eventId,image,senderId,
                            carrierId,receiverId,receiverName,insurance,paidStatus,cost,type,weight,about,dimension);
                        myDeliveryArryList.add(myDeliveryData);
                        myDeliveryAdapter.notifyDataSetChanged();
                        loading.dismiss();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                    loading.dismiss();
                    errorMessageText.setText(R.string.internet_connection_error);
                    DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                    Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                    action_text_error.setText(R.string.action_text_no_internet);
                }

            }

            @Override
            public void noDataFound() {
                myDeliveryAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "no data", Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.no_deliveries_text);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.delivery).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_deliveries);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent action_intent = new Intent(getContext(), MainActivity.class);
                        startActivity(action_intent);
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCatch(JSONException e) {
                myDeliveryAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "catch"+e.getMessage(), Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prepareDeliveryData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(VolleyError e) {
                myDeliveryAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prepareDeliveryData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }
        },new User(getContext()).getPhoneNumber(),"021");
    }
}
