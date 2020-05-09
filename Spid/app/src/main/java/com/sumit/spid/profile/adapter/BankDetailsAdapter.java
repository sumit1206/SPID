package com.sumit.spid.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.sumit.spid.R;
import com.sumit.spid.profile.profiledata.BankDetailsData;
import com.sumit.spid.profile.profiledata.ProfileDetailsData;

import java.util.List;

public class BankDetailsAdapter extends RecyclerView.Adapter<BankDetailsAdapter.BankDetailsViewHolder>  {

    private Context profileBakDatailsContext;
    private List<BankDetailsData> profileBankDetailsList;


    public BankDetailsAdapter(Context profileBakDetailsContext, List<BankDetailsData> profileBankDetailsList) {
        this.profileBankDetailsList = profileBankDetailsList;
        this.profileBakDatailsContext = profileBakDetailsContext;
    }

    @NonNull
    @Override
    public BankDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_bank_details_card, parent, false);
        return new BankDetailsAdapter.BankDetailsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BankDetailsViewHolder holder, int position) {

        BankDetailsData bankDetailsData = profileBankDetailsList.get(position);
//        holder.itemBankThumbnail.setAnimation(AnimationUtils.loadAnimation(profileBakDatailsContext,R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(profileBakDatailsContext,R.anim.fade_scale_animation));
        holder.profileBankValue.setText(bankDetailsData.getProfileBankFieldValue());
        holder.profileBankFieldName.setText(bankDetailsData.getProfileBankFieldName());
//      holder.itemBankThumbnail.setImageResource(bankDetailsData.getItemBankThumbnail());
        holder.profileBankActionIcon.setImageResource(bankDetailsData.getProfileBankDetailsActionIcon());
    }

    @Override
    public int getItemCount() {
        return profileBankDetailsList.size();
    }

    public class BankDetailsViewHolder extends RecyclerView.ViewHolder {

        TextView profileBankValue,profileBankFieldName;
        ImageView itemBankThumbnail,profileBankActionIcon;
        RelativeLayout container;

        public BankDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            profileBankValue = itemView.findViewById(R.id.profile_bank_field_value);
            profileBankFieldName = itemView.findViewById(R.id.profile_activity_field_name);
//            itemBankThumbnail = itemView.findViewById(R.id.profile_activity_bank_thumbnail);
            profileBankActionIcon = itemView.findViewById(R.id.profileBankActionIcon);
            container =  itemView.findViewById(R.id.container);
        }
    }
}
