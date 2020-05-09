package com.example.shop.aggregator.profile;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.shop.R;
import com.example.shop.aggregator.RemoteConnection.RemoteConnection;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.aggregator.User;
import com.example.shop.aggregator.notificationCarrier.NotificationAggrigator;
import com.example.shop.aggregator.profile.adapter.ProfilePersonalDetailsAdapter;
import com.example.shop.aggregator.profile.profiledata.ProfileDetailsData;
import com.example.shop.store.profile.StoreProfileAdapter.StoreProfilePersonalDetailsAdapter;
import com.example.shop.store.profile.storeProfileData.StoreProfileDetailsData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileActivityPersonalFragment extends Fragment {

    private List<ProfileDetailsData> profileDetailsList;
    private RecyclerView profileRecyclerView;
    private ProfilePersonalDetailsAdapter profileDetailsAdapter;
    ProgressDialog loading;


    // error page credentials
    LinearLayout errorLinearLayout;
    ImageView errorImage;
    TextView errorMessageText,action_text_error;


    public ProfileActivityPersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_details_carrier,container,false);
        profileRecyclerView = view.findViewById(R.id.profile_details_recycler_view);
        profileDetailsList = new ArrayList<>();
        profileDetailsAdapter = new ProfilePersonalDetailsAdapter(getContext(),profileDetailsList);

        /**progress dialog initialization*/
        loading = new ProgressDialog(getContext(), R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        //        error page credentials
        errorLinearLayout = view.findViewById(R.id.error_layout);
        errorImage = view.findViewById(R.id.error_image);
        errorMessageText = view.findViewById(R.id.message_error_loading);
        action_text_error = view.findViewById(R.id.action_text_error);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        profileRecyclerView.setLayoutManager(layoutManager);
        profileRecyclerView.setAdapter(profileDetailsAdapter);
        profileRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        prepareProfileData();
        return view;
    }

    private void prepareProfileData()
    {
        loading.show();
        new RemoteConnection(getContext()).profileFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                ProfileDetailsData profileDetailsData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    String mail = jsonObject.getString("email_id");
                    String phone = jsonObject.getString("mobile");
                    profileDetailsData = new ProfileDetailsData(phone,"Mobile Number",R.drawable.smartphone,"Phone_number");
                    profileDetailsList.add(profileDetailsData);
                    profileDetailsData = new ProfileDetailsData(mail,"Email id",R.drawable.email,"Phone_number");
                    profileDetailsList.add(profileDetailsData);
                    loading.dismiss();
                    profileDetailsAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
//                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
                errorMessageText.setText(R.string.internet_connection_error);
                errorLinearLayout.setVisibility(View.VISIBLE);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prepareProfileData();
                    }
                });

            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
//                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.internet_connection_error);
                errorLinearLayout.setVisibility(View.VISIBLE);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prepareProfileData();
                    }
                });

            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
//              Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();

                errorMessageText.setText(R.string.internet_connection_error);
                errorLinearLayout.setVisibility(View.VISIBLE);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(getContext()).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prepareProfileData();
                    }
                });
            }
        },new User(getContext()).getPhoneNumber(),"065");
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
