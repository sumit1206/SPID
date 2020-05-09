package com.example.shop.store.profile;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.shop.R;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.aggregator.profile.adapter.ProfilePersonalDetailsAdapter;
import com.example.shop.aggregator.profile.profiledata.ProfileDetailsData;
import com.example.shop.store.StoreUser;
import com.example.shop.store.profile.StoreProfileAdapter.StoreProfilePersonalDetailsAdapter;
import com.example.shop.store.profile.storeProfileData.StoreProfileDetailsData;
import com.example.shop.store.remote.DbHelper;
import com.example.shop.store.stock.StoreStockData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileStorePersonalFragment extends Fragment {

    private List<StoreProfileDetailsData> storeProfileDetailsList;
    private RecyclerView storeProfileRecyclerView;
    private StoreProfilePersonalDetailsAdapter profileDetailsAdapter;
    ProgressDialog loading;

    public ProfileStorePersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_personal_detail_store, container, false);


        storeProfileRecyclerView = inflate.findViewById(R.id.profile_details_store_recycler_view);
        storeProfileDetailsList = new ArrayList<>();
        profileDetailsAdapter = new StoreProfilePersonalDetailsAdapter(getContext(),storeProfileDetailsList);

        /**progress dialog initialization*/
        loading = new ProgressDialog(getContext(), R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        profileDetailsAdapter = new StoreProfilePersonalDetailsAdapter(getContext(),storeProfileDetailsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        storeProfileRecyclerView.setLayoutManager(layoutManager);
        storeProfileRecyclerView.setAdapter(profileDetailsAdapter);
        storeProfileRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        prepareProfileData();
        return inflate;
    }

    private void prepareProfileData() {
        loading.show();
        new DbHelper(getContext()).pfofileFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                StoreProfileDetailsData storeProfileDetailsData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    String shopName = jsonObject.getString("shop_name");
                    String ownerName = jsonObject.getString("owner_name");
                    String mail = jsonObject.getString("mail");
                    String phone = jsonObject.getString("phone");
                    String address = jsonObject.getString("address");
                    String landmark = jsonObject.getString("landmark");
                    storeProfileDetailsData = new StoreProfileDetailsData(shopName,"Shop Name",R.drawable.store_name,"Phone_number");
                    storeProfileDetailsList.add(storeProfileDetailsData);
                    storeProfileDetailsData = new StoreProfileDetailsData(ownerName,"Owner Name",R.drawable.account,"Phone_number");
                    storeProfileDetailsList.add(storeProfileDetailsData);
                    storeProfileDetailsData = new StoreProfileDetailsData(mail,"Mail",R.drawable.email,"Phone_number");
                    storeProfileDetailsList.add(storeProfileDetailsData);
                    storeProfileDetailsData = new StoreProfileDetailsData(phone,"Phone Number",R.drawable.smartphone,"Phone_number");
                    storeProfileDetailsList.add(storeProfileDetailsData);
                    storeProfileDetailsData = new StoreProfileDetailsData(address,"Address",R.drawable.location_city,"Phone_number");
                    storeProfileDetailsList.add(storeProfileDetailsData);
                    storeProfileDetailsData = new StoreProfileDetailsData(landmark,"Landmark",R.drawable.pin_drop_location,"Phone_number");
                    storeProfileDetailsList.add(storeProfileDetailsData);
                    loading.dismiss();
                    profileDetailsAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                }
            }

            @Override
            public void noDataFound() {
                loading.dismiss();
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                Toast.makeText(getContext(),"Please check your internet connection",Toast.LENGTH_LONG).show();
            }
        },"060",new StoreUser(getContext()).getPhone());



//        StoreProfileDetailsData storeProfileDetailsData;
//        storeProfileDetailsData = new StoreProfileDetailsData("Add number","",R.drawable.smartphone,"Phone_number");
//        storeProfileDetailsList.add(storeProfileDetailsData);
//        profileDetailsAdapter.notifyDataSetChanged();

    }
}
