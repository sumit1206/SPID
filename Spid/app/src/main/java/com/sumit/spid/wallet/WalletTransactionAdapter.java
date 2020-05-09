package com.sumit.spid.wallet;

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
import com.sumit.spid.R;
import java.util.List;

public class WalletTransactionAdapter extends RecyclerView.Adapter<WalletTransactionAdapter.MyViewHolder> {

    private Context walletContext;
    private List<WalletTransactionData> transactionDataArrayList;

    public WalletTransactionAdapter(Context walletContext, List<WalletTransactionData> transactionDataArrayList) {
        this.walletContext = walletContext;
        this.transactionDataArrayList = transactionDataArrayList;
    }

    @NonNull
    @Override
    public WalletTransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_single_transaction, parent, false);
        return new WalletTransactionAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletTransactionAdapter.MyViewHolder holder, int position) {

        WalletTransactionData walletTransactionData = transactionDataArrayList.get(position);
        holder.transactionCard.setAnimation(AnimationUtils.loadAnimation(walletContext, R.anim.fade_in));
        holder.walletTransactionId.setText(walletTransactionData.getWalletTransaction_id());
        holder.walletTransactionMethod.setImageResource(walletTransactionData.getWalletTransactionMethod());
        holder.walletTransactionAmount.setText(walletTransactionData.getWalletMoneyAmount());
        holder.walletTransactionStatus.setText(walletTransactionData.getWalletMoneyAddStatus());
        holder.walletTimeStamp.setText(walletTransactionData.getWalletMoneyAddTimeStamp());
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
            walletTransactionId = itemView.findViewById(R.id.wallet_transaction_id);
            walletTimeStamp = itemView.findViewById(R.id.time_stamp_wallet);
            walletTransactionStatus = itemView.findViewById(R.id.wallet_add_status);
            walletTransactionAmount  = itemView.findViewById(R.id.wallet_add_money_amount);
            walletTransactionMethod = itemView.findViewById(R.id.wallet_payment_method);
            transactionCard = itemView.findViewById(R.id.transaction_card);
        }
    }
}
