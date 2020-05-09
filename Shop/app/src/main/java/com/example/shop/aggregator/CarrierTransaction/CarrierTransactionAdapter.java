package com.example.shop.aggregator.CarrierTransaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.shop.R;
import java.util.List;

public class CarrierTransactionAdapter extends RecyclerView.Adapter<CarrierTransactionAdapter.CarrierTransactionViewHolder> {

    private Context tContext;
    private List<CarrierTransactionData> carrierTransactionDataArrayList;

    public CarrierTransactionAdapter(Context tContext, List<CarrierTransactionData> carrierTransactionDataArrayList) {
        this.tContext = tContext;
        this.carrierTransactionDataArrayList = carrierTransactionDataArrayList;
    }

    @NonNull
    @Override
    public CarrierTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_store_transaction_card, parent, false);
        return new CarrierTransactionAdapter.CarrierTransactionViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull CarrierTransactionViewHolder holder, int position) {

        CarrierTransactionData carrierTransactionData = carrierTransactionDataArrayList.get(position);
        holder.parcelIdStoreTransaction.setText(carrierTransactionData.getParcel_id());
        holder.descriptionStoreTransaction.setText(carrierTransactionData.getDescription());
        holder.parcelTypeStoreTransaction.setText(carrierTransactionData.getParcelType());
        holder.parcelImageStoreTransaction.setImageResource(carrierTransactionData.getParcelImage());
        holder.timeStampStoreTransaction.setText(carrierTransactionData.getTimeStampStock());


    }

    @Override
    public int getItemCount() {
        return carrierTransactionDataArrayList.size();
    }

    public class CarrierTransactionViewHolder extends RecyclerView.ViewHolder {

        TextView descriptionStoreTransaction,parcelIdStoreTransaction,timeStampStoreTransaction,parcelTypeStoreTransaction;
        ImageView parcelImageStoreTransaction;

        public CarrierTransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            parcelIdStoreTransaction = itemView.findViewById(R.id.parcel_id_store_transaction);
            descriptionStoreTransaction = itemView.findViewById(R.id.item_description_store_transaction);
            timeStampStoreTransaction = itemView.findViewById(R.id.time_stamp_store_transaction);
            parcelTypeStoreTransaction = itemView.findViewById(R.id.parcel_type_store_transaction);
            parcelImageStoreTransaction = itemView.findViewById(R.id.parcel_image_store_transaction);

        }
    }
}
