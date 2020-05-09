package com.sumit.spid.mydelivery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.sumit.spid.R;
import com.sumit.spid.mydelivery.MyDeliveryDetails;
import com.sumit.spid.mydelivery.data.TrackData;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.MyViewHolder>
{

    private Context deliveryTrackContext;
    private List<TrackData> deliveryTrackArryList;


    public TrackAdapter(MyDeliveryDetails deliveryTrackContext, List<TrackData> deliveryTrackArryList)
    {
        this.deliveryTrackArryList = deliveryTrackArryList;
        this.deliveryTrackContext = deliveryTrackContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView dateTime,parcel_position;
        TimelineView timelineView;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            dateTime = itemView.findViewById(R.id.date_time);
            parcel_position = itemView.findViewById(R.id.parcel_position);
//            timelineView = itemView.findViewById(R.id.timeline);
        }
    }

    @NonNull
    @Override
    public TrackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_tracking_details, parent, false);
        return new TrackAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackAdapter.MyViewHolder holder, int position)
    {
        TrackData trackData = deliveryTrackArryList.get(position);
        holder.dateTime.setText(trackData.getTimeStamp());
        holder.parcel_position.setText(trackData.getPacketPosition());
    }

    @Override
    public int getItemCount() {
        return deliveryTrackArryList.size();
    }
}
