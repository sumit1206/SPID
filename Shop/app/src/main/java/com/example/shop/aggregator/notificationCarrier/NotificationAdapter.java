package com.example.shop.aggregator.notificationCarrier;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.shop.R;
import com.example.shop.aggregator.ItemDetailsNotificationAggregator;
import com.example.shop.aggregator.RemoteConnection.RemoteConnection;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.aggregator.User;

import org.json.JSONException;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{
    private  Intent notificationIntent;
    private Context notificationContext;
    private Activity notificationActivity;
    private List<NotificationData> notificationDataArray;
    ProgressDialog loading;
    private int lastPosition = -1;


public NotificationAdapter(Activity notificationActivity, Intent notificationIntent,Context notificationContext,List<NotificationData> notificationDataArray) {
    this.notificationActivity = notificationActivity;
    this.notificationIntent = notificationIntent;
    this.notificationContext = notificationContext;
    this.notificationDataArray = notificationDataArray;

    /**progress dialog initialization*/
    loading = new ProgressDialog(notificationContext, R.style.ProgressDialog);
    loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification_layout, parent, false);
        return new NotificationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
    final NotificationData notificationData = notificationDataArray.get(position);
    holder.container.setAnimation(AnimationUtils.loadAnimation(notificationContext, R.anim.fade_in));
    holder.notification_body.setText(notificationData.getBody());
    holder.date.setText(notificationData.getDate());


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent item_details_intent = new Intent (notificationContext, ItemDetailsNotificationAggregator.class);
//                notificationContext.startActivity(item_details_intent);
//            }
//        });
    if(notificationData.getType().equals("1")){
        holder.buttonLayout.setVisibility(View.VISIBLE);
        holder.accept.setOnClickListener(new View.OnClickListener() {
            /**Carrier accepted*/
            @Override
            public void onClick(View view) {
                loading.show();
                NotificationData selectedNotificationData = notificationDataArray.get(position);
                new RemoteConnection(notificationContext).carrierNotificationsFetch(new VolleyCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        loading.dismiss();
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(notificationContext);
                        alertDialog.setTitle("Request accepted");
                        alertDialog.setMessage("You are assigned for this purpose.");
                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                notificationContext.startActivity(notificationIntent);
                                notificationActivity.finish();

                            }
                        });
                        alertDialog.show();
                    }

                    @Override
                    public void noDataFound() {
                        loading.dismiss();
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(notificationContext);
                        alertDialog.setTitle("Session expired");
                        alertDialog.setMessage("This session is already expired.");
                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alertDialog.show();

                    }

                    @Override
                    public void onCatch(JSONException e) {
                        loading.dismiss();
                    }

                    @Override
                    public void onError(VolleyError e) {
                        loading.dismiss();
                    }
                },new User(notificationContext).getPhoneNumber(),"017",selectedNotificationData.getEventId(),"","");
            }
        });
        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading.show();
                NotificationData selectedNotificationData = notificationDataArray.get(position);
                new RemoteConnection(notificationContext).carrierNotificationsFetch(new VolleyCallback() {
                    @Override
                    public void onSuccess(Object result) {
                        loading.dismiss();
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(notificationContext);
                        alertDialog.setTitle("Request rejected");
                        alertDialog.setMessage("You have rejected this request");
                        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        alertDialog.show();
                    }

                    @Override
                    public void noDataFound() {
                        loading.dismiss();
                    }

                    @Override
                    public void onCatch(JSONException e) {
                        loading.dismiss();
                    }

                    @Override
                    public void onError(VolleyError e) {
                        loading.dismiss();
                    }
                },new User(notificationContext).getPhoneNumber(),"030",selectedNotificationData.getEventId(),"","");
            }
        });
    }else {
        holder.buttonLayout.setVisibility(View.GONE);
    }
    }

    @Override
    public int getItemCount() {
        return notificationDataArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView notification_type;
        public TextView notification_body,date;
        public LinearLayout buttonLayout;
        Button accept, reject;
        public RelativeLayout container;

        public MyViewHolder(View view) {
            super(view);
            notification_body = view.findViewById(R.id.notification_body);
            date = view.findViewById(R.id.notification_time_stamp);
            notification_type = view.findViewById(R.id.notification_thumbnail);
            buttonLayout = view.findViewById(R.id.button_layout);
            container =  itemView.findViewById(R.id.container);
            accept = view.findViewById(R.id.button_accept);
            reject = view.findViewById(R.id.button_reject);

        }
    }
}
