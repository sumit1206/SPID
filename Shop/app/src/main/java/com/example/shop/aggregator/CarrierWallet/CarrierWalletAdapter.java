package com.example.shop.aggregator.CarrierWallet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.store.StoreTransaction.StoreTransactionItemDetails;

import java.util.List;

public class CarrierWalletAdapter extends RecyclerView.Adapter<CarrierWalletAdapter.MyViewHolder> {

    private Context walletContext;
    private List<CarrierWalletTransactionData> transactionDataArrayList;

    public CarrierWalletAdapter(Context walletContext, List<CarrierWalletTransactionData> transactionDataArrayList) {
        this.walletContext = walletContext;
        this.transactionDataArrayList = transactionDataArrayList;
    }

    @NonNull
    @Override
    public CarrierWalletAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_single_transaction, parent, false);
        return new CarrierWalletAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarrierWalletAdapter.MyViewHolder holder, int position) {

        CarrierWalletTransactionData carrierWalletTransactionData = transactionDataArrayList.get(position);
        holder.transactionCard.setAnimation(AnimationUtils.loadAnimation(walletContext, R.anim.fade_in));
//        holder.walletTransactionId.setText(carrierWalletTransactionData.getWalletTransaction_id());
        holder.walletTransactionMethod.setImageResource(carrierWalletTransactionData.getWalletTransactionMethod());
        holder.walletTransactionAmount.setText(carrierWalletTransactionData.getWalletMoneyAmount());
        holder.walletTransactionStatus.setText(carrierWalletTransactionData.getWalletMoneyAddStatus());
        holder.walletTimeStamp.setText(carrierWalletTransactionData.getWalletMoneyAddTimeStamp());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itemDetailsIntent = new Intent(walletContext, StoreTransactionItemDetails.class);
                walletContext.startActivity(itemDetailsIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transactionDataArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView walletTransactionId,walletTimeStamp,walletTransactionStatus,walletTransactionAmount;
        ImageView walletTransactionMethod;
        CardView transactionCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            walletTransactionId = itemView.findViewById(R.id.wallet_transaction_id);
            walletTimeStamp = itemView.findViewById(R.id.time_stamp_wallet);
            walletTransactionStatus = itemView.findViewById(R.id.wallet_add_status);
            walletTransactionAmount  = itemView.findViewById(R.id.wallet_add_money_amount);
            walletTransactionMethod = itemView.findViewById(R.id.wallet_payment_method);
            transactionCard = itemView.findViewById(R.id.transaction_card);
        }
    }
}
