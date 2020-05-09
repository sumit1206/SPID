package com.sumit.spid.notification;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.google.android.gms.common.util.Base64Utils;
import com.sumit.spid.MainActivity;
import com.sumit.spid.ParseDateTimeStamp;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.mydelivery.MyDeliveries;
import com.sumit.spid.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Notification extends Fragment {

    ProgressDialog loading;
    public static FragmentActivity notificationActivity;

    private List<NotificationData> notificationDataListArray = new ArrayList<>();
    private RecyclerView notificationRecyclerView;
    private NotificationAdapter notificationAdapter;

//  error page credentials
    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;

    SwipeRefreshLayout mSwipeRefreshLayout;
    public Notification() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification,container,false);

        notificationActivity = getActivity();

//        error page credentials
        errorLinearLayout = view.findViewById(R.id.error_layout);
        errorImage = view.findViewById(R.id.error_image);
        errorMessageText = view.findViewById(R.id.message_error_loading);
        action_text_error = view.findViewById(R.id.action_text_error);


        notificationRecyclerView = view.findViewById(R.id.notification_recycler_view);
        notificationRecyclerView.setHasFixedSize(true);
        notificationAdapter = new NotificationAdapter(getContext(),notificationDataListArray);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        notificationRecyclerView.setLayoutManager(layoutManager);
        notificationRecyclerView.setAdapter(notificationAdapter);
        notificationRecyclerView.setItemAnimator(new DefaultItemAnimator());
       // notificationRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        /**progress dialog initialization*/
        loading = new ProgressDialog(getContext(), R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);



        mSwipeRefreshLayout = view.findViewById(R.id.notification_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.orange, R.color.green_color, R.color.text_color);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run()
                    {
                        //LOAD FETCH URL
                        notificationData();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        notificationData();
        return view;
    }
    public void notificationData()
    {
        notificationDataListArray.clear();
        loading.show();
        errorLinearLayout.setVisibility(View.GONE);
        new RemoteDataDownload(getContext()).notificationsFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                NotificationData notificationData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        String body = post.getString("body");
//                        String date = post.getString("departure_group");
                        String eventId = post.getString("event_id");
                        String time = post.getString("time");
//                        time = new ParseDateTimeStamp(time).getDateTimeInFormat("dd MMM,yy hh:mm a");
//                        String seenStatus = post.getString("departure_group");
                        notificationData = new NotificationData(body, time, eventId);
                        notificationDataListArray.add(notificationData);
                        notificationAdapter.notifyDataSetChanged();
                        loading.dismiss();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
                notificationAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.no_notification_text);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.error_image).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_notification);
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
                notificationAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "catch"+e.getMessage(), Toast.LENGTH_LONG).show();


                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        notificationData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(VolleyError e) {
                notificationAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();



                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       notificationData();
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);
            }
        },"","020","","",new User(getContext()).getPhoneNumber());

    }



}
