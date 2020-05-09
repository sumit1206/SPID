package com.example.shop.aggregator.dashbord;

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


import com.example.shop.ParseImage;
import com.example.shop.R;

import java.util.List;

public class CarrierMainAdapter extends RecyclerView.Adapter<CarrierMainAdapter.MyViewHolder>
{
    private Context carrierMainContext;
    private List<CarrierMainData>carrierMainArryList;

    public CarrierMainAdapter(Context carrierMainContext, List<CarrierMainData> carrierMainArryList) {
        this.carrierMainContext = carrierMainContext;
        this.carrierMainArryList = carrierMainArryList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView order_id_aggregator,from_address_aggregator,to_address_aggregator,parcel_status;
        ImageView parcelImage;
        CardView container;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            parcelImage = itemView.findViewById(R.id.parcel_image_aggregator);
            order_id_aggregator = itemView.findViewById(R.id.order_id_aggregator);
            from_address_aggregator = itemView.findViewById(R.id.from_address_aggregator);
            to_address_aggregator = itemView.findViewById(R.id.to_address_aggregator);
            parcel_status = itemView.findViewById(R.id.parcel_status);
            container = itemView.findViewById(R.id.container_card);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_carrier_single_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        CarrierMainData carrierMainData = carrierMainArryList.get(position);
        holder.parcelImage.setAnimation(AnimationUtils.loadAnimation(carrierMainContext, R.anim.fade_in));
        holder.container.setAnimation(AnimationUtils.loadAnimation(carrierMainContext, R.anim.fade_scale_animation));
//        holder.parcelImage.setImageResource(carrierMainData.getParcelImage());
        new ParseImage(holder.parcelImage).setImageString(carrierMainData.getParcelImage());
        holder.order_id_aggregator.setText(carrierMainData.getParcelId());
        holder.from_address_aggregator.setText(carrierMainData.getFrom_address());
        holder.to_address_aggregator.setText(carrierMainData.getTo_address());
        holder.parcel_status.setText(carrierMainData.getProgress());
        if(carrierMainData.getProgress().equals("")){
            holder.parcel_status.setVisibility(View.GONE);
        }
        holder.parcel_status.setTextColor(Color.parseColor("#047A09"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CarrierMainData selectedCarrierMainData = carrierMainArryList.get(position);
                Intent itemDetailsIntent =  new Intent (carrierMainContext, ItemDetailsAggrigator.class);
                itemDetailsIntent.putExtra("selectedItemDetails",selectedCarrierMainData);
                carrierMainContext.startActivity(itemDetailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carrierMainArryList.size();
    }
}
