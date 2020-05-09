package com.sumit.spid.explorestores;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sumit.spid.CoordinateManager;
import com.sumit.spid.MainActivity;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.mydelivery.data.NearestShopDeliveryData;
import com.sumit.spid.notification.NotificationData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreStoresInListFragment extends Fragment {


    private RecyclerView exploreNeatestShopRecyclerView;
    private List<NearestShopDeliveryData> nearestShopArryList = new ArrayList<>();
    private ExploreShopListAdapter exploreShopListAdapter;
    NearestShopDeliveryData nearestShopDeliveryData;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog loading;
    private FusedLocationProviderClient mFusedLocationProviderClient;
//    NearestShopDeliveryData nearestShopDeliveryData;


    public ExploreStoresInListFragment(List<NearestShopDeliveryData> nearestShopArryList) {
        this.nearestShopArryList = nearestShopArryList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_nearest_shop_list_explore_stores, container, false);


        /**progress dialog initialization*/
        loading = new ProgressDialog(getContext(), R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        exploreNeatestShopRecyclerView = inflate.findViewById(R.id.explore_store_list_recycler_view);
        exploreShopListAdapter = new ExploreShopListAdapter(getContext(),nearestShopArryList);
        layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        exploreNeatestShopRecyclerView.setHasFixedSize(true);
        exploreNeatestShopRecyclerView.setLayoutManager(layoutManager);
        exploreNeatestShopRecyclerView.setAdapter(exploreShopListAdapter);
        exploreNeatestShopRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        getDeviceLocation();
        return inflate;
    }

//    private void getDeviceLocation() {
//        loading.show();
//        /**
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
//            locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
//                @Override
//                public void onComplete(@NonNull Task<Location> task) {
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        // Set the map's camera position to the current location of the device.
//                        Location mLastKnownLocation = task.getResult();
//                        LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
//                        String myLatitude, myLongitude;
//                        myLatitude = String.valueOf(latLng.latitude);
//                        myLongitude = String.valueOf(latLng.longitude);
//                        nearestShopData(myLatitude,myLongitude);
//                        Toast.makeText(getContext(),"Done: "+myLatitude+"+"+myLongitude,Toast.LENGTH_LONG).show();
//                        Log.println(Log.ASSERT, "If", latLng.latitude+"+"+latLng.longitude);
//                    } else {
//                        loading.dismiss();
//                        Toast.makeText(getContext(),"Oops, your location is unavailable.",Toast.LENGTH_LONG).show();
//                        Log.println(Log.ASSERT, "else","task not successful");
//                    }
//                }
//            });
//        } catch (SecurityException e)  {
//            loading.dismiss();
//            Toast.makeText(getContext(),"Oops, your location is unavailable.",Toast.LENGTH_LONG).show();
//            Log.println(Log.ASSERT, "Catch",Log.getStackTraceString(e));
//        }
//    }
//
//
//    private void nearestShopData(final String myLatitude, final String myLongitude)
//    {
//        nearestShopArryList.clear();
//        Log.println(Log.ASSERT,"nearestShopData: ","caller");
//        new RemoteDataDownload(getContext()).nearestWheelerFetch(new VolleyCallback() {
//            @Override
//            public void onSuccess(Object result) {
//                JSONObject jsonObject = (JSONObject) result;
//                try {
//                    JSONArray jsonArray = jsonObject.getJSONArray("details");
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject post = jsonArray.getJSONObject(i);
//
//                        String id = post.getString("id");
//                        String shopName = post.getString("shop_name");
//                        String ownerName = post.getString("owner_name");
//                        String phoneNumber = post.getString("phone");
//                        String address = post.getString("address");
//                        String imageString = post.getString("shop_image");
//                        String landmark = post.getString("landmark");
//                        String latitude = post.getString("latitude");
//                        String longitude = post.getString("langitude");
//                        String type = "";
//                        String rating = "4";
//                        Double d = CoordinateManager.findDistance(myLatitude,myLongitude,latitude,longitude,0.0,0.0);
//                        String dUnit = "m";
//                        if(d >= 1000){
//                            d = d/1000;
//                            d = (int)(Math.round(d*100))/100.0;
//                            dUnit = "km";
//                        }
//                        String distance = String.valueOf(d)+" "+dUnit;
//                        String openStatus = "Open";
//                        nearestShopDeliveryData = new NearestShopDeliveryData(id,shopName,ownerName,phoneNumber,
//                                address,imageString,landmark,latitude,longitude,type,rating,distance,openStatus);
//                        nearestShopArryList.add(nearestShopDeliveryData);
//                        exploreShopListAdapter.notifyDataSetChanged();
//                        loading.dismiss();
//                    }
//                } catch (Exception e) {
//                    loading.dismiss();
//                    Toast.makeText(getContext(), "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//
//            @Override
//            public void noDataFound() {
//                exploreShopListAdapter.notifyDataSetChanged();
//                loading.dismiss();
////                Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onCatch(JSONException e) {
//                exploreShopListAdapter.notifyDataSetChanged();
//                loading.dismiss();
////                Toast.makeText(getContext(), "catch"+e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onError(VolleyError e) {
//                exploreShopListAdapter.notifyDataSetChanged();
//                loading.dismiss();
////                Toast.makeText(getContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        },myLatitude,myLongitude);
//    }
}
