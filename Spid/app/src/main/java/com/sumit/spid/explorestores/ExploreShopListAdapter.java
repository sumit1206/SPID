package com.sumit.spid.explorestores;

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
import java.util.List;

public class ExploreShopListAdapter extends RecyclerView.Adapter<ExploreShopListAdapter.MyViewHolder> {

    private Context exploreShopListContext;
    private List<NearestShopDeliveryData> nearestShopDeliveryArryList;


    public ExploreShopListAdapter(Context exploreShopListContext, List<NearestShopDeliveryData> nearestShopDeliveryArryList) {
        this.exploreShopListContext = exploreShopListContext;
        this.nearestShopDeliveryArryList = nearestShopDeliveryArryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_shop_list_layout_dashboard, parent, false);
        return new ExploreShopListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final NearestShopDeliveryData nearestShopDeliveryData = nearestShopDeliveryArryList.get(position);
        holder.layout_nearby_shop.setAnimation(AnimationUtils.loadAnimation(exploreShopListContext, R.anim.fade_in));
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
        new ParseImage(holder.image_shop_delivery).setImageString(nearestShopDeliveryData.getImageString());
        holder.pickup_location_on_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    String uri = "http://maps.google.com/maps?q=loc:" + nearestShopDeliveryData.getLatitude() + "," +
                        nearestShopDeliveryData.getLongitude() + " (" + nearestShopDeliveryData.getShopName() + ")";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    exploreShopListContext.startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(exploreShopListContext,"Unable to plot location",Toast.LENGTH_LONG).show();
                }
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
        TextView pickup_location_on_map;
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
            pickup_location_on_map = itemView.findViewById(R.id.pickup_location_on_map);
        }
    }
}
