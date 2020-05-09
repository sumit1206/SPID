package com.sumit.spid.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sumit.spid.R;
import com.sumit.spid.mydelivery.data.TrackData;
import java.util.ArrayList;

public class TrackParcelAdapterDashbord extends RecyclerView.Adapter<TrackParcelAdapterDashbord.TrackViewHolder>
{
    ArrayList<TrackData> trackDataArrayList;
    private Context trackParcelContext;

    public TrackParcelAdapterDashbord(ArrayList<TrackData> trackDataArrayList, Context trackParcelContext) {
        this.trackDataArrayList = trackDataArrayList;
        this.trackParcelContext = trackParcelContext;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_track_order_horizontal_dashbord, parent, false);
        return new TrackParcelAdapterDashbord.TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {

        TrackData trackData = trackDataArrayList.get(position);
        holder.dateTrack.setText(trackData.getTimeStamp());
        holder.positionTrack.setText(trackData.getPacketPosition());
    }

    @Override
    public int getItemCount() {
        return trackDataArrayList.size();
    }

    public class TrackViewHolder extends RecyclerView.ViewHolder {

        TextView dateTrack,positionTrack;
        public TrackViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTrack = itemView.findViewById(R.id.date_track);
            positionTrack = itemView.findViewById(R.id.position_track);

        }
    }
}
