package com.example.shop.store.profile.StoreProfileAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shop.R;
import com.example.shop.store.profile.storeProfileData.StoreProfileDetailsData;

import java.util.List;

public class StoreProfilePersonalDetailsAdapter extends RecyclerView.Adapter<StoreProfilePersonalDetailsAdapter.MyViewHolder> {

    private Context storeProfileDetailsContext;
    private List<StoreProfileDetailsData> storeProfileDetailsList;

    public StoreProfilePersonalDetailsAdapter(Context storeProfileDetailsContext, List<StoreProfileDetailsData> storeProfileDetailsList) {
        this.storeProfileDetailsContext = storeProfileDetailsContext;
        this.storeProfileDetailsList = storeProfileDetailsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_profile_details_card, parent, false);
//        user = new User(profileDetailsContext);
//        cookiesAdapter = new CookiesAdapter(profileDetailsContext);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        StoreProfileDetailsData storeProfileDetailsData = storeProfileDetailsList.get(position);
        holder.profileFieldValue.setText(storeProfileDetailsData.getProfileFieldValue());
        holder.profileDetailsActionIcon.setImageResource(R.drawable.add_circle_outline);
        holder.profileFieldName.setText(storeProfileDetailsData.getProfileFieldName());
        holder.itemThumbnail.setImageResource(storeProfileDetailsData.getItemThumbnail());
    }

    @Override
    public int getItemCount() {
        return storeProfileDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView profileFieldValue,profileFieldName;
        ImageView itemThumbnail,profileDetailsActionIcon;
        RelativeLayout container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profileFieldValue = itemView.findViewById(R.id.profile_activity_field_value);
            profileFieldName= itemView.findViewById(R.id.profile_personal_field_name);
            itemThumbnail = itemView.findViewById(R.id.profile_bank_thumbnail);
            profileDetailsActionIcon = itemView.findViewById(R.id.profile_activity_thumbnail);
            container =  itemView.findViewById(R.id.container);
        }
    }

//    private Context profileDetailsContext;
//    private List<ProfileDetailsData> profileDetailsList;
//    User user;
//    CookiesAdapter cookiesAdapter;
//    String fieldValue;
//
//    public StoreProfilePersonalDetailsAdapter(Context profileDetailsContext, List<ProfileDetailsData> profileDetailsList)
//    {
//        this.profileDetailsContext = profileDetailsContext;
//        this.profileDetailsList = profileDetailsList;
//    }
//
//    @NonNull
//    @Override
//    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_profile_details_card, parent, false);
//        user = new User(profileDetailsContext);
//        cookiesAdapter = new CookiesAdapter(profileDetailsContext);
//        return new ProfileViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final ProfileViewHolder holder, final int position) {
//        fieldValue = "";
//        ProfileDetailsData profileDetailsData = profileDetailsList.get(position);
//
//        holder.itemThumbnail.setAnimation(AnimationUtils.loadAnimation(profileDetailsContext,R.anim.fade_transition_animation));
//        holder.container.setAnimation(AnimationUtils.loadAnimation(profileDetailsContext,R.anim.fade_scale_animation));
//
//        holder.profileFieldName.setText(profileDetailsData.getProfileFieldName());
//
//        cookiesAdapter.openReadable();
//        fieldValue = cookiesAdapter.getProfileValue(user.getPhoneNumber(),profileDetailsData.getTableAttribute());
//        cookiesAdapter.close();
//        //        Toast.makeText(profileDetailsContext,profileDetailsData.getTableAttribute()+"=="+fieldValue,Toast.LENGTH_LONG).show();
//
//        if(fieldValue == null || fieldValue.equalsIgnoreCase("")) {
//            holder.profileFieldValue.setText(profileDetailsData.getProfileFieldValue());
//            holder.profileDetailsActionIcon.setImageResource(R.drawable.add_circle_outline);
//            holder.profileDetailsActionIcon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ProfileDetailsData data = profileDetailsList.get(position);
//                    Intent addIntent = new Intent(profileDetailsContext, ProfileEditorActivity.class);
//                    addIntent.putExtra("profilevalue",data);
//                    profileDetailsContext.startActivity(addIntent);
//                }
//            });
//       }else{
//            holder.profileFieldValue.setText(fieldValue);
//            holder.profileDetailsActionIcon.setImageResource(R.drawable.edit);
//            holder.profileDetailsActionIcon.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    holder.profileFieldName.setError("");
//                }
//            });
//        }
//        holder.itemThumbnail.setImageResource(profileDetailsData.getItemThumbnail());
//    }
//
//    @Override
//    public int getItemCount() {
//        return profileDetailsList.size();
//    }
//
//    public class ProfileViewHolder extends RecyclerView.ViewHolder
//    {
//        TextView  profileFieldValue,profileFieldName;
//        ImageView itemThumbnail,profileDetailsActionIcon;
//        RelativeLayout container;
//
//        public ProfileViewHolder(@NonNull View itemView)
//        {
//            super(itemView);
//            profileFieldValue = itemView.findViewById(R.id.profile_activity_field_value);
//            profileFieldName= itemView.findViewById(R.id.profile_personal_field_name);
//            itemThumbnail = itemView.findViewById(R.id.profile_bank_thumbnail);
//            profileDetailsActionIcon = itemView.findViewById(R.id.profile_activity_thumbnail);
//            container =  itemView.findViewById(R.id.container);
//        }
//    }


}
