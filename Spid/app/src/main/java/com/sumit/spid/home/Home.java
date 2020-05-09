package com.sumit.spid.home;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sumit.spid.R;
import com.sumit.spid.User;
import com.sumit.spid.databasesupport.local.CookiesAdapter;
import com.sumit.spid.home.HomeData.HomeOfferData;
import com.sumit.spid.home.HomeData.PagerData;
import com.sumit.spid.home.HomeData.PeopleSaysData;
import com.sumit.spid.home.HomeData.RecommentedData;
import com.sumit.spid.home.HomeData.ServiceCityData;
import com.sumit.spid.home.adapter.HomeAdapter;
import com.sumit.spid.home.adapter.ViewPagerDataAdapter;
import com.sumit.spid.mydelivery.data.TrackData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private List<PagerData> pagerData;
    private ViewPager sliderPager;
    private TabLayout indicator;

    User user;
    CookiesAdapter cookiesAdapter;
    Dialog popupDialog;
    Switch earningSwitch;
    RecyclerView homeRecyclerView;
    private ArrayList <Object> objects = new ArrayList<>();
    public static ArrayList<TrackData> trackData = new ArrayList<>();



//dashboard credentials



    public Home()
    {
        trackData.add(new TrackData("Nothing found on this order Id","Today"));

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        cookiesAdapter = new CookiesAdapter(getActivity());
        user = new User(getActivity());
        //setup dashboard recycler view
        homeRecyclerView = view.findViewById(R.id.home_recycler_view);
        HomeAdapter homeAdapter = new HomeAdapter(getContext(), getObject());
        homeRecyclerView.setAdapter(homeAdapter);
        homeRecyclerView.setHasFixedSize(true);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    //array list for dashboard recycler view
    private ArrayList<Object> getObject() {
        objects.add(getServiceCityData().get(0));
        objects.add(getRecommentedData().get(0));
        objects.add(getViewPagerData().get(0));
//        if(!getTrackata().isEmpty())
        objects.add(getTrackata().get(0));
        objects.add(getPeopleSaysData().get(0));
        objects.add(getHomeOffersrData().get(0));
        return objects;
    }




    //view pager slide animation

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

    public static ArrayList<ServiceCityData> getServiceCityData() {
        ArrayList<ServiceCityData> serviceCityData = new ArrayList<>();
        serviceCityData.add(new ServiceCityData(R.drawable.gps, "Nearby"));
        serviceCityData.add(new ServiceCityData(R.drawable.kolkata, "Kolkata"));
        serviceCityData.add(new ServiceCityData(R.drawable.delhi, "Delhi"));
        serviceCityData.add(new ServiceCityData(R.drawable.mumbai_city, "Mumbai"));
        serviceCityData.add(new ServiceCityData(R.drawable.chenai, "Chennai"));
        serviceCityData.add(new ServiceCityData(R.drawable.banglore_city, "Bengaluru"));
        serviceCityData.add(new ServiceCityData(R.drawable.hydrabad, "Hydrabad"));
        serviceCityData.add(new ServiceCityData(R.drawable.pune_city,"Pune"));
        serviceCityData.add(new ServiceCityData(R.drawable.patna, "Bihar"));
        serviceCityData.add(new ServiceCityData(R.drawable.karnataka, "Karnataka"));
        return serviceCityData;
    }

    public static ArrayList<PeopleSaysData> getPeopleSaysData() {
        ArrayList<PeopleSaysData> peopleSaysData = new ArrayList<>();
        peopleSaysData.add(new PeopleSaysData("What is my parcel security ?","Good"));
        peopleSaysData.add(new PeopleSaysData("How much first service this is?","Too first service this is"));
        peopleSaysData.add(new PeopleSaysData("What is my parcel security ?","Good"));
        peopleSaysData.add(new PeopleSaysData("How much first service this is?","Too first service this is"));
        peopleSaysData.add(new PeopleSaysData("What is my parcel security ?","Good"));
        peopleSaysData.add(new PeopleSaysData("How much first service this is?","Too first service this is"));
        peopleSaysData.add(new PeopleSaysData("What is my parcel security ?","Good"));
        peopleSaysData.add(new PeopleSaysData("How much first service this is?","Too first service this is"));
        peopleSaysData.add(new PeopleSaysData("What is my parcel security ?","Good"));
        peopleSaysData.add(new PeopleSaysData("How much first service this is?","Too first service this is"));
        peopleSaysData.add(new PeopleSaysData("What is my parcel security ?","Good"));
        peopleSaysData.add(new PeopleSaysData("How much first service this is?","Too first service this is"));
        return peopleSaysData;
    }


    public static ArrayList<PagerData> getViewPagerData() {
        ArrayList<PagerData> viewFlipperData = new ArrayList<>();
        viewFlipperData.add(new PagerData(R.drawable.offer_second, ""));
        viewFlipperData.add(new PagerData(R.drawable.offer_first, ""));
        viewFlipperData.add(new PagerData(R.drawable.view_door_to_door, ""));
        viewFlipperData.add(new PagerData(R.drawable.view_inviteposter, ""));
        viewFlipperData.add(new PagerData(R.drawable.view_parntership, ""));
        return viewFlipperData;

    }


    public static ArrayList<HomeOfferData> getHomeOffersrData() {
        ArrayList<HomeOfferData> homeOfferData = new ArrayList<>();
        homeOfferData.add(new HomeOfferData(R.drawable.spid_first_offers));
        homeOfferData.add(new HomeOfferData(R.drawable.spid_offers_two));
        return homeOfferData;

    }
    public static ArrayList<TrackData> getTrackata() {
        return trackData;
    }

    public static void setTrackData(String position, String time){
        trackData.add(new TrackData(position,time));
//        trackData.add(new TrackData("in  howrah station hub","24 jan,19"));
//        trackData.add(new TrackData("in delhi station hub","24 jan,19"));
//        trackData.add(new TrackData("in indriapuram hub","24 jan,19"));
//        trackData.add(new TrackData("delivered","24 jan,19"));
    }



    public static ArrayList<RecommentedData> getRecommentedData() {
        ArrayList<RecommentedData> recommentedData = new ArrayList<>();
        recommentedData.add(new RecommentedData(R.drawable.new_booking,"Send parcel"));
        recommentedData.add(new RecommentedData(R.drawable.earn,"Earn"));
        recommentedData.add(new RecommentedData(R.drawable.store,"Nearest Store"));
        return recommentedData;
    }


//    show popup when turning on earning mode

//    public void ShowPopup(View v) {
//        TextView txtclose;
//        Button agreeBtn;
//        popupDialog = new Dialog(getContext());
//        popupDialog.setContentView(R.layout.carrier_mode_custom_popup);
//        txtclose = popupDialog.findViewById(R.id.close_btn);
//        txtclose.setText("X");
//        agreeBtn = popupDialog.findViewById(R.id.agree_btn);
//        txtclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                earningSwitch.setChecked(false);
//                popupDialog.dismiss();
//            }
//        });
//        agreeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cookiesAdapter.openWritable();
//                cookiesAdapter.updateProfileValue(getString(R.string.attributeCookiesCarrierStatus),"1",user.getPhoneNumber());
//                cookiesAdapter.close();
//                popupDialog.dismiss();
//            }
//        });
//        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        popupDialog.show();
//    }

    @Override
    public void onResume() {
//        Toast.makeText(getActivity(),"Home resumed",Toast.LENGTH_LONG).show();
//        cookiesAdapter.openReadable();
//        if (cookiesAdapter.getProfileValue(user.getPhoneNumber(), getString(R.string.attributeCookiesCarrierStatus)).equals("1")) {
//        }else {
//        }
//        cookiesAdapter.close();
        super.onResume();
    }
}


