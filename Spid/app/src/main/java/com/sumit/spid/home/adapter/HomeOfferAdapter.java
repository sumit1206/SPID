package com.sumit.spid.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sumit.spid.R;
import com.sumit.spid.home.HomeData.HomeOfferData;
import java.util.ArrayList;

public class HomeOfferAdapter extends RecyclerView.Adapter<HomeOfferAdapter.HomeViewHolder> {

    ArrayList<HomeOfferData> homeOfferDataArrayList;
    private Context homeOfferContext;


    public HomeOfferAdapter(ArrayList<HomeOfferData> homeOfferDataArrayList, Context homeOfferContext) {
        this.homeOfferDataArrayList = homeOfferDataArrayList;
        this.homeOfferContext = homeOfferContext;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_home_offers_layout, parent, false);
        return new HomeOfferAdapter.HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.offerThumbnail.setImageResource(homeOfferDataArrayList.get(position).getOffers_Image());
    }

    @Override
    public int getItemCount()
    {
        return homeOfferDataArrayList.size();
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView offerThumbnail;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            offerThumbnail = itemView.findViewById(R.id.image_offers);
        }
    }
}
