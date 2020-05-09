package com.example.shop.store.StoreWallet;

import android.content.Context;
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

import java.util.List;

public class StoreWalletAdapter extends RecyclerView.Adapter<StoreWalletAdapter.MyViewHolder> {

    private Context walletContext;
    private List<StoreWalletTransactionData> storeTransactionDataArrayList;

    public StoreWalletAdapter(Context walletContext, List<StoreWalletTransactionData> storeTransactionDataArrayList) {
        this.walletContext = walletContext;
        this.storeTransactionDataArrayList = storeTransactionDataArrayList;
    }

    @NonNull
    @Override
    public StoreWalletAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_single_transaction, parent, false);
        return new StoreWalletAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreWalletAdapter.MyViewHolder holder, int position) {

        StoreWalletTransactionData storeWalletTransactionData = storeTransactionDataArrayList.get(position);
        holder.transactionCard.setAnimation(AnimationUtils.loadAnimation(walletContext, R.anim.fade_in));
//        holder.walletTransactionId.setText(storeWalletTransactionData.getWalletTransaction_id());
        holder.walletTransactionMethod.setImageResource(storeWalletTransactionData.getWalletTransactionMethod());
        holder.walletTransactionAmount.setText(storeWalletTransactionData.getWalletMoneyAmount());
        holder.walletTransactionStatus.setText(storeWalletTransactionData.getWalletMoneyAddStatus());
        holder.walletTimeStamp.setText(storeWalletTransactionData.getWalletMoneyAddTimeStamp());
    }

    @Override
    public int getItemCount() {
        return storeTransactionDataArrayList.size();
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
