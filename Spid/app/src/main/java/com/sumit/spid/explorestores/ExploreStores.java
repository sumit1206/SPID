package com.sumit.spid.explorestores;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.sumit.spid.CoordinateManager;
import com.sumit.spid.R;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.mydelivery.data.NearestShopDeliveryData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ExploreStores extends AppCompatActivity {


    private TabLayout storeTabLayout;
    private ViewPager storeViewPager;
    View mIndicator;
    private int indicatorWidth;

    EditText searchPinCodeOrState;
    TextView nearestSearchButton;

    ////////////////////////////////
    private List<NearestShopDeliveryData> nearestShopArryList = new ArrayList<>();
    NearestShopDeliveryData nearestShopDeliveryData;
    ProgressDialog loading;
    private FusedLocationProviderClient mFusedLocationProviderClient;
////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_stores);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mIndicator = findViewById(R.id.indicator);
        storeViewPager = findViewById(R.id.store_viewPager);
        storeTabLayout =  findViewById(R.id.store_tabs);
        searchPinCodeOrState = findViewById(R.id.search_pincode_or_state);
        nearestSearchButton = findViewById(R.id.nearest_search_button);

        /**progress dialog initialization*/
        loading = new ProgressDialog(ExploreStores.this, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ExploreStores.this);


        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setTitle("Nearest store");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }


//        setupViewPager();
        getDeviceLocation();


        nearestSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = searchPinCodeOrState.getText().toString().trim();
                if(s != null || s != ""){
                    getLocationFromAddress(s);
                }else{
                    searchPinCodeOrState.setError("Please enter a valid place");}
            }
        });

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void setupViewPager()
    {
        ExploreStores.ViewPagerAdapter adapter = new ExploreStores.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ExploreStoresInListFragment(nearestShopArryList), "List");
        adapter.addFrag(new ExploreShopInMapsFragment(), "Map");
        storeViewPager.setAdapter(adapter);

        storeTabLayout.setupWithViewPager(storeViewPager);

        storeTabLayout.post(new Runnable() {
            @Override
            public void run() {

                indicatorWidth = storeTabLayout.getWidth() / storeTabLayout.getTabCount();
                //Assign new width
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                mIndicator.setLayoutParams(indicatorParams);
            }
        });

        storeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (positionOffset+position) * indicatorWidth ;
                params.leftMargin = (int) translationOffset;
                mIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    void getLocationFromAddress(String place){
        Geocoder geocoder = new Geocoder(getBaseContext());
        loading.show();
        try {
            // Getting a maximum of 1 Address that matches the input
            // text
            Address address = geocoder.getFromLocationName(place, 1).get(0);
            if (address != null && !address.equals("")) {
                String myLat, myLon;
                myLat = String.valueOf(address.getLatitude());
                myLon = String.valueOf(address.getLongitude());
                nearestShopData(myLat,myLon);
            }else {
                searchPinCodeOrState.setError("Place not found");
                loading.dismiss();
            }
        } catch (Exception e){
            Log.println(Log.ERROR,"Catch exception:",e.getMessage());
            searchPinCodeOrState.setError("Place not found");
            loading.dismiss();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private void getDeviceLocation() {
        loading.show();
        /**
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful() && task.getResult() != null) {
                        // Set the map's camera position to the current location of the device.
                        Location mLastKnownLocation = task.getResult();
                        LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
                        String myLatitude, myLongitude;
                        myLatitude = String.valueOf(latLng.latitude);
                        myLongitude = String.valueOf(latLng.longitude);
                        nearestShopData(myLatitude,myLongitude);
                        Toast.makeText(ExploreStores.this,"Done: "+myLatitude+"+"+myLongitude,Toast.LENGTH_LONG).show();
                        Log.println(Log.ASSERT, "If", latLng.latitude+"+"+latLng.longitude);
                    } else {
                        loading.dismiss();
                        Toast.makeText(ExploreStores.this,"Oops, your location is unavailable.",Toast.LENGTH_LONG).show();
                        Log.println(Log.ASSERT, "else","task not successful");
                    }
                }
            });
        } catch (SecurityException e)  {
            loading.dismiss();
            Toast.makeText(ExploreStores.this,"Oops, your location is unavailable.",Toast.LENGTH_LONG).show();
            Log.println(Log.ASSERT, "Catch",Log.getStackTraceString(e));
        }
    }


    private void nearestShopData(final String myLatitude, final String myLongitude)
    {
        nearestShopArryList.clear();
        Log.println(Log.ASSERT,"nearestShopData: ","caller");
        new RemoteDataDownload(ExploreStores.this).nearestWheelerFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);

                        String id = post.getString("id");
                        String shopName = post.getString("shop_name");
                        String ownerName = post.getString("owner_name");
                        String phoneNumber = post.getString("phone");
                        String address = post.getString("address");
                        String imageString = post.getString("shop_image");
                        String landmark = post.getString("landmark");
                        String latitude = post.getString("latitude");
                        String longitude = post.getString("langitude");
                        String type = "";
                        String rating = "4";
                        Double d = CoordinateManager.findDistance(myLatitude,myLongitude,latitude,longitude,0.0,0.0);
                        String dUnit = "m";
                        if(d >= 1000){
                            d = d/1000;
                            d = (int)(Math.round(d*100))/100.0;
                            dUnit = "km";
                        }else{
                            d = (int)(Math.round(d*100))/100.0;
                        }
                        String distance = String.valueOf(d)+" "+dUnit;
                        String openStatus = post.getString("status");
                        if(openStatus.equals("1"))
                            openStatus = "open";
                        else
                            openStatus = "close";
                        nearestShopDeliveryData = new NearestShopDeliveryData(id,shopName,ownerName,phoneNumber,
                                address,imageString,landmark,latitude,longitude,type,rating,distance,openStatus);
                        nearestShopArryList.add(nearestShopDeliveryData);
//                        exploreShopListAdapter.notifyDataSetChanged();
                        loading.dismiss();
                    }
                    setupViewPager();
                } catch (Exception e) {
                    loading.dismiss();
                    Toast.makeText(ExploreStores.this, "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
//                exploreShopListAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCatch(JSONException e) {
//                exploreShopListAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "catch"+e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(VolleyError e) {
//                exploreShopListAdapter.notifyDataSetChanged();
                loading.dismiss();
//                Toast.makeText(getContext(), "error"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        },myLatitude,myLongitude);
    }

}
