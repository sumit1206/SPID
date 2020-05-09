package com.sumit.spid.mydelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import com.sumit.spid.mydelivery.data.MyDeliveryData;
import com.sumit.spid.mydelivery.MyDeliveryDetails;

import java.util.List;

public class MyDeliveryAdapter extends RecyclerView.Adapter<MyDeliveryAdapter.MyViewHolder>
{
    private Context myDeliveryContext;
    private List<MyDeliveryData>myDeliveryArryList;

    public MyDeliveryAdapter(Context myDeliveryContext,List<MyDeliveryData>myDeliveryArryList)
    {
        this.myDeliveryContext = myDeliveryContext;
        this.myDeliveryArryList = myDeliveryArryList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView fromAddress,toAddress,deliveryTime,deliveryStatus;
        ImageView parcelImage;
        RelativeLayout container;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            fromAddress = itemView.findViewById(R.id.from_address);
            toAddress = itemView.findViewById(R.id.to_address);
            deliveryTime = itemView.findViewById(R.id.delivery_expected);
            parcelImage = itemView.findViewById(R.id.parcel_image);
            deliveryStatus = itemView.findViewById(R.id.delivery_with_you);
            container =  itemView.findViewById(R.id.container);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_my_delivery, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        MyDeliveryData myDeliveryData = myDeliveryArryList.get(position);
        holder.parcelImage.setAnimation(AnimationUtils.loadAnimation(myDeliveryContext, R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(myDeliveryContext, R.anim.fade_scale_animation));
        holder.fromAddress.setText(myDeliveryData.getFrom_address());
        holder.toAddress.setText(myDeliveryData.getTo_address());
        holder.deliveryTime.setText(myDeliveryData.getDelivery_time() + "hrs");
        try{
            new ParseImage(holder.parcelImage).setImageString(myDeliveryData.getImage());
//            Toast.makeText(myDeliveryContext,"Adapter try",Toast.LENGTH_LONG).show();
        }catch (Exception e){
//            Toast.makeText(myDeliveryContext,"Adapter catch",Toast.LENGTH_LONG).show();
        }
        String withYou = "";
        if(myDeliveryData.getProgress().equals("0")){
            if(myDeliveryData.getPaidStatus().equals("0")){
                holder.deliveryStatus.setText("pay now");
                holder.deliveryStatus.setTextColor(Color.parseColor("#FF1212"));
            }else {
                holder.deliveryStatus.setText("with you");
                holder.deliveryStatus.setTextColor(Color.parseColor("#FFC107"));
            }
        }else {
            holder.deliveryStatus.setText("In progress");
            holder.deliveryStatus.setTextColor(Color.parseColor("#128C1F"));

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDeliveryData selectedDeliveryData = myDeliveryArryList.get(position);
                Intent deliveryIntent = new Intent(myDeliveryContext, MyDeliveryDetails.class);
                deliveryIntent.putExtra("selectedDeliveryData",selectedDeliveryData);
                myDeliveryContext.startActivity(deliveryIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDeliveryArryList.size();
    }
}
