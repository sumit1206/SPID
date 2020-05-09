package com.sumit.spid.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumit.spid.R;
import com.sumit.spid.home.HomeData.ServiceCityData;
import com.sumit.spid.search.SearchActivity;

import java.util.ArrayList;

public class ServiceCityAdapter extends RecyclerView.Adapter<ServiceCityAdapter.ServiceViewHolder> {

    ArrayList<ServiceCityData> serviceCityData;
    Context serviceCityContext;

    public ServiceCityAdapter(ArrayList<ServiceCityData> serviceCityData, Context serviceCityContext) {
        this.serviceCityData = serviceCityData;
        this.serviceCityContext = serviceCityContext;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_single_nearby_layout, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, final int position) {
        holder.city_thumbnail.setImageResource(serviceCityData.get(position).getThumbnail());
        holder.city_name.setText(serviceCityData.get(position).getCity_name());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent searchIntent = new Intent(serviceCityContext, SearchActivity.class);
                searchIntent.putExtra("place",serviceCityData.get(position).getCity_name());
                serviceCityContext.startActivity(searchIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceCityData.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView city_name;
        ImageView city_thumbnail;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            city_thumbnail = (ImageView) itemView.findViewById(R.id.city_thumbnail_dashboard);
            city_name = (TextView) itemView.findViewById(R.id.city_name);
        }
    }
}
