package com.sumit.spid.mydelivery.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumit.spid.ParseImage;
import com.sumit.spid.R;
import com.sumit.spid.mydelivery.data.NearestShopDeliveryData;
import com.sumit.spid.sinch.LoginActivity;
import com.sumit.spid.sinch.SinchInitialize;

import java.util.List;

public class NearestShopDeliveryAdapter extends RecyclerView.Adapter<NearestShopDeliveryAdapter.MyViewHolder>{

    private Context nearestShopDeliveryContext;
    private List<NearestShopDeliveryData> nearestShopDeliveryArryList;

    public NearestShopDeliveryAdapter(Context nearestShopDeliveryContext, List<NearestShopDeliveryData> nearestShopArryList)
    {
        this.nearestShopDeliveryContext = nearestShopDeliveryContext;
        this.nearestShopDeliveryArryList = nearestShopArryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_nearest_shop_card, parent, false);
        return new NearestShopDeliveryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        NearestShopDeliveryData nearestShopDeliveryData = nearestShopDeliveryArryList.get(position);
        holder.layout_nearby_shop.setAnimation(AnimationUtils.loadAnimation(nearestShopDeliveryContext, R.anim.fade_in));
        holder.shop_name_nearest_shop_delivery.setText(nearestShopDeliveryData.getShopName());
        holder.rating_shop_details.setText(nearestShopDeliveryData.getRating());
        holder.distance_nearest_shop_delivery.setText(nearestShopDeliveryData.getDistance());
        holder.shop_type_delivery.setText(nearestShopDeliveryData.getType());
        holder.shop_address_nearest_shop_delivery.setText(nearestShopDeliveryData.getAddress());
        holder.shop_activity_status.setText(nearestShopDeliveryData.getOpenStatus());
        if(nearestShopDeliveryData.getOpenStatus().toLowerCase().contains("open")){
            holder.shop_activity_status.setTextColor(Color.parseColor("#128C1F"));
        }else{
            holder.shop_activity_status.setTextColor(Color.RED);
        }
//        holder.image_shop_delivery.setImageResource(nearestShopDeliveryData.getShopimageDelivery());
        new ParseImage(holder.image_shop_delivery).setImageString(nearestShopDeliveryData.getImageString());
        holder.getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    NearestShopDeliveryData nearestShopDeliveryData = nearestShopDeliveryArryList.get(position);
                    String uri = "http://maps.google.com/maps?q=loc:" + nearestShopDeliveryData.getLatitude() + "," +
                            nearestShopDeliveryData.getLongitude() + " (" + nearestShopDeliveryData.getShopName() + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    nearestShopDeliveryContext.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(nearestShopDeliveryContext,"Unable to plot location",Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NearestShopDeliveryData nearestShopDeliveryData = nearestShopDeliveryArryList.get(position);
//                new SinchInitialize(nearestShopDeliveryContext,"soumya");
                Intent callIntent = new Intent(nearestShopDeliveryContext, LoginActivity.class);
                callIntent.putExtra("extraNumber",nearestShopDeliveryData.getPhoneNumber());
                nearestShopDeliveryContext.startActivity(callIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return nearestShopDeliveryArryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView shop_name_nearest_shop_delivery,rating_shop_details,distance_nearest_shop_delivery,shop_type_delivery,
                shop_address_nearest_shop_delivery,shop_activity_status;
        TextView getDirection, call;
        ImageView image_shop_delivery;
        LinearLayout layout_nearby_shop;


        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            shop_name_nearest_shop_delivery = itemView.findViewById(R.id.shop_name_nearest_shop_delivery);
            rating_shop_details = itemView.findViewById(R.id.rating_shop_details);
            distance_nearest_shop_delivery = itemView.findViewById(R.id.distance_nearest_shop_delivery);
            shop_type_delivery = itemView.findViewById(R.id.shop_type_delivery);
            shop_address_nearest_shop_delivery = itemView.findViewById(R.id.shop_address_nearest_shop_delivery);
            shop_activity_status = itemView.findViewById(R.id.shop_activity_status);
            image_shop_delivery = itemView.findViewById(R.id.image_shop_delivery);
            layout_nearby_shop = itemView.findViewById(R.id.layout_nearby_shop);
            getDirection = itemView.findViewById(R.id.get_direction_shop);
            call = itemView.findViewById(R.id.call_shop);
        }
    }
}
