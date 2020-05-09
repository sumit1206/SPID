package com.sumit.spid.home.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sumit.spid.R;
import com.sumit.spid.explorestores.ExploreStores;
import com.sumit.spid.home.HomeData.RecommentedData;
import com.sumit.spid.search.SearchActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RecommentedAdapter extends RecyclerView.Adapter<RecommentedAdapter.RecommentedViewHolder> {

    ArrayList<RecommentedData> recommentedData;
    private Context recommentedContext;


    public RecommentedAdapter(ArrayList<RecommentedData> recommentedData,Context recommentedContext) {
        this.recommentedData = recommentedData;
        this.recommentedContext = recommentedContext;
    }

    @NonNull
    @Override
    public RecommentedAdapter.RecommentedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recomended_for_you_single_item, parent, false);
        return new RecommentedAdapter.RecommentedViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RecommentedViewHolder holder, final int position) {

        holder.recomendedThumbnail.setImageResource(recommentedData.get(position).getRecommentThumbnail());
        holder.titleCardTop.setText(recommentedData.get(position).getRecommendedTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0){
                    Intent bookingIntent = new Intent(recommentedContext, SearchActivity.class);
                    recommentedContext.startActivity(bookingIntent);
                } else if (position == 1){

                    Toast.makeText(recommentedContext,"This Features is in Under Development",Toast.LENGTH_LONG).show();

                } else if (position == 2) {

                    Intent exploreStoreIntent = new Intent(recommentedContext, ExploreStores.class);
                    recommentedContext.startActivity(exploreStoreIntent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return recommentedData.size();
    }

    public class RecommentedViewHolder extends RecyclerView.ViewHolder
    {
        ImageView recomendedThumbnail;
        TextView titleCardTop;
        public RecommentedViewHolder(@NonNull View itemView) {
            super(itemView);
            recomendedThumbnail =itemView.findViewById(R.id.recommended_layout_background);
            titleCardTop = itemView.findViewById(R.id.title_card_top);
        }
    }
}
