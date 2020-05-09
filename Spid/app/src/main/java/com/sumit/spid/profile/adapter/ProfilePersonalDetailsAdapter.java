package com.sumit.spid.profile.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.local.CookiesAdapter;
import com.sumit.spid.profile.ProfileEditorActivity;
import com.sumit.spid.profile.profiledata.ProfileDetailsData;

import java.util.List;

public class ProfilePersonalDetailsAdapter extends RecyclerView.Adapter<ProfilePersonalDetailsAdapter.ProfileViewHolder> {

    private Context profileDetailsContext;
    private List<ProfileDetailsData> profileDetailsList;
    User user;
    CookiesAdapter cookiesAdapter;
    String fieldValue;

    public ProfilePersonalDetailsAdapter(Context profileDetailsContext, List<ProfileDetailsData> profileDetailsList)
    {
        this.profileDetailsContext = profileDetailsContext;
        this.profileDetailsList = profileDetailsList;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_profile_details_card, parent, false);
        user = new User(profileDetailsContext);
        cookiesAdapter = new CookiesAdapter(profileDetailsContext);
        return new ProfileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileViewHolder holder, final int position) {
        fieldValue = "";
        ProfileDetailsData profileDetailsData = profileDetailsList.get(position);

        holder.itemThumbnail.setAnimation(AnimationUtils.loadAnimation(profileDetailsContext,R.anim.fade_transition_animation));
        holder.container.setAnimation(AnimationUtils.loadAnimation(profileDetailsContext,R.anim.fade_scale_animation));

        holder.profileFieldName.setText(profileDetailsData.getProfileFieldName());

        cookiesAdapter.openReadable();
        fieldValue = cookiesAdapter.getProfileValue(user.getPhoneNumber(),profileDetailsData.getTableAttribute());
        cookiesAdapter.close();
        //        Toast.makeText(profileDetailsContext,profileDetailsData.getTableAttribute()+"=="+fieldValue,Toast.LENGTH_LONG).show();

        if(fieldValue == null || fieldValue.equalsIgnoreCase("")) {
            holder.profileFieldValue.setText(profileDetailsData.getProfileFieldValue());
            holder.profileDetailsActionIcon.setImageResource(R.drawable.add_circle_outline);
            holder.profileDetailsActionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProfileDetailsData data = profileDetailsList.get(position);
                    Intent addIntent = new Intent(profileDetailsContext, ProfileEditorActivity.class);
                    addIntent.putExtra("profilevalue",data);
                    profileDetailsContext.startActivity(addIntent);
                }
            });
       }else{
            holder.profileFieldValue.setText(fieldValue);
            holder.profileDetailsActionIcon.setImageResource(R.drawable.edit);
            holder.profileDetailsActionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.profileFieldName.setError("");
                }
            });
        }
        holder.itemThumbnail.setImageResource(profileDetailsData.getItemThumbnail());
    }

    @Override
    public int getItemCount() {
        return profileDetailsList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder
    {
        TextView  profileFieldValue,profileFieldName;
        ImageView itemThumbnail,profileDetailsActionIcon;
        RelativeLayout container;

        public ProfileViewHolder(@NonNull View itemView)
        {
            super(itemView);
            profileFieldValue = itemView.findViewById(R.id.profile_activity_field_value);
            profileFieldName= itemView.findViewById(R.id.profile_personal_field_name);
            itemThumbnail = itemView.findViewById(R.id.profile_bank_thumbnail);
            profileDetailsActionIcon = itemView.findViewById(R.id.profile_activity_thumbnail);
            container =  itemView.findViewById(R.id.container);
        }
    }
}
