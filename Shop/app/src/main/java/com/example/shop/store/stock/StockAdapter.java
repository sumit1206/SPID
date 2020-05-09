package com.example.shop.store.stock;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.ParseImage;
import com.example.shop.R;
import com.example.shop.store.StoreTransaction.StoreTransaction;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.MyViewHolder> {

    private Context sContext;
    private List<StoreStockData>storeStockDataArray;

    public StockAdapter(Context sContext,List<StoreStockData>storeStockDataArray)
    {
        this.sContext = sContext;
        this.storeStockDataArray = storeStockDataArray;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_stock_store_data, parent, false);
        return new StockAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final StoreStockData dataStock = storeStockDataArray.get(position);
        holder.parcelIdStock.setText(dataStock.getPacketId());
        holder.parcelTypeStock.setText(dataStock.getType());
        holder.descriptionStock.setText(dataStock.getDescription());
//        holder.parcelImageStock.setImageResource(dataStock.getParcelImage());
        holder.timeStampStock.setText("");
        new ParseImage(holder.parcelImageStock).setImageString(dataStock.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final StoreStockData dataStock = storeStockDataArray.get(position);
                Intent intent = new Intent(sContext,StockItemDetails.class);
                intent.putExtra("dataStock",dataStock);
                sContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeStockDataArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView descriptionStock,parcelIdStock,timeStampStock,parcelTypeStock;
        ImageView parcelImageStock;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            parcelIdStock = itemView.findViewById(R.id.parcel_id_stock);
            descriptionStock = itemView.findViewById(R.id.item_description_stock);
            timeStampStock = itemView.findViewById(R.id.time_stamp_stock);
            parcelTypeStock = itemView.findViewById(R.id.parcel_type_stock);
            parcelImageStock = itemView.findViewById(R.id.parcel_image_stock);

        }
    }

}
