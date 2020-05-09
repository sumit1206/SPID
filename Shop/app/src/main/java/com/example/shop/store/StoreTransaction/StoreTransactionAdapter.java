package com.example.shop.store.StoreTransaction;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shop.R;
import java.util.List;


public class StoreTransactionAdapter extends RecyclerView.Adapter<StoreTransactionAdapter.TransactionViewHolder> {

    private Context tContext;
    private List<StoreTransactionData> storeTransactionsArrayList;


    public StoreTransactionAdapter(Context tContext, List<StoreTransactionData> storeTransactionsArrayList) {
        this.tContext = tContext;
        this.storeTransactionsArrayList = storeTransactionsArrayList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_store_transaction_card, parent, false);
        return new StoreTransactionAdapter.TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {

        StoreTransactionData storeTransactionData = storeTransactionsArrayList.get(position);
        holder.parcelIdStoreTransaction.setText(storeTransactionData.getParcel_id());
        holder.descriptionStoreTransaction.setText(storeTransactionData.getDescription());
        holder.parcelTypeStoreTransaction.setText(storeTransactionData.getParcelType());
        holder.parcelImageStoreTransaction.setImageResource(storeTransactionData.getParcelImage());
        holder.timeStampStoreTransaction.setText(storeTransactionData.getTimeStampStock());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemDetailsIntent = new Intent(tContext,StoreTransactionItemDetails.class);
                tContext.startActivity(itemDetailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeTransactionsArrayList.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        TextView descriptionStoreTransaction,parcelIdStoreTransaction,timeStampStoreTransaction,parcelTypeStoreTransaction;
        ImageView parcelImageStoreTransaction;
        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            parcelIdStoreTransaction = itemView.findViewById(R.id.parcel_id_store_transaction);
            descriptionStoreTransaction = itemView.findViewById(R.id.item_description_store_transaction);
            timeStampStoreTransaction = itemView.findViewById(R.id.time_stamp_store_transaction);
            parcelTypeStoreTransaction = itemView.findViewById(R.id.parcel_type_store_transaction);
            parcelImageStoreTransaction = itemView.findViewById(R.id.parcel_image_store_transaction);
        }
    }
}
