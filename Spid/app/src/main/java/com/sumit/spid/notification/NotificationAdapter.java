package com.sumit.spid.notification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sumit.spid.MainActivity;
import com.sumit.spid.ParseDateTimeStamp;
import com.sumit.spid.R;
import com.sumit.spid.mydelivery.MyDeliveries;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private Context notificationContext;
//    private Activity notificationActivity;
    private List<NotificationData> notificationDataArray;
    private int lastPosition = -1;


public NotificationAdapter(Context notificationContext,List<NotificationData> notificationDataArray) {
//    this.notificationActivity = notificationActivity;
    this.notificationContext = notificationContext;
    this.notificationDataArray = notificationDataArray;
}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    NotificationData notificationData = notificationDataArray.get(position);
    holder.notification_body.setText(notificationData.getBody());
    String dt = new ParseDateTimeStamp(notificationData.getDate()).getDateTimeInFormat("dd MMM,yy hh:mm a");
    holder.date.setText(dt);
//    holder.notification_type.(notificationData.getThumbnail(2));
    Glide.with(notificationContext).load(notificationData.getThumbnail(1)).into(holder.notification_type);
    setAnimation(holder.itemView,position);
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadFragment(new MyDeliveries());
        }
    });
    }

    @Override
    public int getItemCount() {
        return notificationDataArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView notification_type;
        public TextView notification_body,date;

        public MyViewHolder(View view) {
            super(view);
            notification_body = view.findViewById(R.id.notification_body);
            date = view.findViewById(R.id.notification_time_stamp);
            notification_type = view.findViewById(R.id.notification_thumbnail);

        }
    }
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(notificationContext, R.anim.fall_doun_animation);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    //Loading home fragment
    public void loadFragment(Fragment fragment) {
        final FragmentTransaction transaction = Notification.notificationActivity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }

}
