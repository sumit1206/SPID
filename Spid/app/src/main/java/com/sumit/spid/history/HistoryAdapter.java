package com.sumit.spid.history;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private Context historyContext;
    private List<HistoryData> historyDataArrayList;

    public HistoryAdapter(Context historyContext, List<HistoryData> historyDataArrayList) {
        this.historyContext = historyContext;
        this.historyDataArrayList = historyDataArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_history_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        final HistoryData historyData = historyDataArrayList.get(position);
        holder.historyCard.setAnimation(AnimationUtils.loadAnimation(historyContext, R.anim.fade_in));
        holder.from_location_history.setText(historyData.getFrom_address());
        holder.to_address_history.setText(historyData.getTo_address());
        holder.item_type_history.setText(historyData.getType());
//        holder.delivery_date_history.setText(historyData.getStatus());
//        holder.parcel_image_history.setImageResource(historyData.getParcelImageThumbnail());
        new ParseImage(holder.parcel_image_history).setImageString(historyData.getImage());
        if(historyData.getStatus().equals("0")){
            holder.delivery_date_history.setText("Delivered");
            holder.delivery_date_history.setTextColor(Color.parseColor("#128C1F"));
        }else if(historyData.getStatus().equals("-1")){
            holder.delivery_date_history.setText("Cancelled");
            holder.delivery_date_history.setTextColor(Color.RED);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HistoryData historyData = historyDataArrayList.get(position);
                Intent orederDetailsIntent = new Intent (historyContext, HistoryDetails.class);
                orederDetailsIntent.putExtra("historyData",historyData);
                historyContext.startActivity(orederDetailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyDataArrayList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView from_location_history,to_address_history,item_type_history,delivery_date_history;
        ImageView parcel_image_history;
        CardView historyCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            from_location_history = itemView.findViewById(R.id.from_location_history);
            to_address_history = itemView.findViewById(R.id.to_address_history);
            item_type_history = itemView.findViewById(R.id.item_type_history);
            delivery_date_history = itemView.findViewById(R.id.delivery_date_history);
            parcel_image_history  = itemView.findViewById(R.id.parcel_image_history);
            historyCard = itemView.findViewById(R.id.history_card);
        }
    }
}
