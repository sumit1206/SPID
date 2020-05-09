package com.sumit.spid.home.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.VolleyError;
import com.google.android.material.tabs.TabLayout;
import com.sumit.spid.ParseDateTimeStamp;
import com.sumit.spid.R;
import com.sumit.spid.databasesupport.remote.RemoteDataDownload;
import com.sumit.spid.databasesupport.remote.VolleyCallback;
import com.sumit.spid.home.Home;
import com.sumit.spid.home.HomeData.HomeOfferData;
import com.sumit.spid.home.HomeData.PeopleSaysData;
import com.sumit.spid.home.HomeData.RecommentedData;
import com.sumit.spid.home.HomeData.ServiceCityData;
import com.sumit.spid.home.HomeData.PagerData;
import com.sumit.spid.mydelivery.MyDeliveryDetails;
import com.sumit.spid.mydelivery.data.TrackData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.sumit.spid.home.Home.getHomeOffersrData;
import static com.sumit.spid.home.Home.getPeopleSaysData;
import static com.sumit.spid.home.Home.getRecommentedData;
import static com.sumit.spid.home.Home.getServiceCityData;
import static com.sumit.spid.home.Home.getTrackata;
import static com.sumit.spid.home.Home.getViewPagerData;
import static com.sumit.spid.home.Home.setTrackData;


public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Object> items;
    private final int SERVICE_IN_CITY = 1;
    private final int BOOKING_OPTIONS = 2;
    private final int VIEWFLIPPER_OFFERS = 3;
    private final int PEOPLE_SAYS = 4;
    private final int OFFERS_HOME = 5;
    private final int TRACK_PARCEL_DASHBORD = 6;

    EditText track_parcel_order_id;
    TextView track_parcel_search_button;


    private ViewPager sliderPager;
    private TabLayout indicator;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.
    final static long NUM_PAGES = 6;



    public HomeAdapter(Context context, ArrayList<Object> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder holder = null;
        View view;

        switch (viewType) {
            case SERVICE_IN_CITY:
                view = inflater.inflate(R.layout.near_by_recycler_view, parent, false);
                holder = new ServiceInCityViewHolder(view);
                break;

            case BOOKING_OPTIONS:
                view = inflater.inflate(R.layout.send_parcel_with_in, parent, false);
                holder = new SendParcelWithInViewHolder(view);
                break;

            case VIEWFLIPPER_OFFERS:
                view = inflater.inflate(R.layout.view_pager_home_layout, parent, false);
                holder = new OffersViewPagerViewHolder(view);
                break;

            case TRACK_PARCEL_DASHBORD:
                view = inflater.inflate(R.layout.track_parcel_recycler_view_dashbord, parent, false);
                holder = new TrackParcelViewHolder(view);
                break;


            case PEOPLE_SAYS:
                view = inflater.inflate(R.layout.people_says_recycler_view, parent, false);
                holder = new PeopleSaysViewHolder(view);
                break;

            case OFFERS_HOME:
                view = inflater.inflate(R.layout.exclusive_offers_booking_deals, parent, false);
                holder = new ExcitingOffersViewHolder(view);
                break;

////        add case to add more layout

            default:
                Toast.makeText(context, "default case" + viewType, Toast.LENGTH_LONG).show();
                view = inflater.inflate(R.layout.near_by_recycler_view, parent, false);
                holder = new SendParcelWithInViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

//        homeAdapterHolder = (TrackParcelViewHolder) holder;
        if (holder.getItemViewType() == SERVICE_IN_CITY)
            serviceInCityView((ServiceInCityViewHolder) holder);

        else if (holder.getItemViewType() == BOOKING_OPTIONS)
            sendParcelWithInView((SendParcelWithInViewHolder) holder);

        else if (holder.getItemViewType() == VIEWFLIPPER_OFFERS)
            viewFlipperView((OffersViewPagerViewHolder) holder);

//        else if (holder.getItemViewType() == TRACK_PARCEL_DASHBORD)
//            TrackParcelView((TrackParcelViewHolder) holder);

        else if (holder.getItemViewType() == PEOPLE_SAYS)
            peopleSaysView((PeopleSaysViewHolder) holder);

        else if (holder.getItemViewType() == OFFERS_HOME)
            excitingOffersView((ExcitingOffersViewHolder) holder);

//        add if statement to set view type
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    //set ItemView Type In Dashboard
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof ServiceCityData)
            return SERVICE_IN_CITY;
        else if (items.get(position) instanceof RecommentedData)
            return BOOKING_OPTIONS;
        else if (items.get(position) instanceof PagerData)
            return VIEWFLIPPER_OFFERS;
        else if (items.get(position) instanceof TrackData)
            return TRACK_PARCEL_DASHBORD;
        else if (items.get(position) instanceof PeopleSaysData)
            return PEOPLE_SAYS;
        else if (items.get(position) instanceof HomeOfferData)
            return OFFERS_HOME;
        return -1;
    }


    //    nearby
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class ServiceInCityViewHolder extends RecyclerView.ViewHolder {
        RecyclerView serviceRecyclerView;

        ServiceInCityViewHolder(View itemView) {
            super(itemView);
            serviceRecyclerView = itemView.findViewById(R.id.service_city_recyclerView_dashboard);
        }
    }

    private void serviceInCityView(ServiceInCityViewHolder holder) {

        ServiceCityAdapter adapter = new ServiceCityAdapter(getServiceCityData(), context);
        holder.serviceRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.serviceRecyclerView.setAdapter(adapter);
    }

    ///////////people says data//////////////////////////////////////////////////////////////////////
    public class PeopleSaysViewHolder extends RecyclerView.ViewHolder {
        RecyclerView peopleSaysRecyclerView;

        PeopleSaysViewHolder(View itemView) {
            super(itemView);
            peopleSaysRecyclerView = itemView.findViewById(R.id.people_recyclerView_dashboard);
        }
    }

    private void peopleSaysView(PeopleSaysViewHolder holder) {

        PeopleSaysAdapter adapter = new PeopleSaysAdapter(getPeopleSaysData(), context);
        holder.peopleSaysRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.peopleSaysRecyclerView.setAdapter(adapter);
    }

    ///////////track with packet id//////////////////////////////////////////////////////////////////////
    public class TrackParcelViewHolder extends RecyclerView.ViewHolder {
        RecyclerView trackParcelRecyclerView;
        TrackParcelViewHolder(final View itemView) {
            super(itemView);
            trackParcelRecyclerView = itemView.findViewById(R.id.track_order_recyclerView_dashboard);
            track_parcel_order_id = itemView.findViewById(R.id.track_parcel_order_id);
            track_parcel_search_button = itemView.findViewById(R.id.track_parcel_search_button);

            track_parcel_search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(track_parcel_order_id.getText().toString().trim().length() != 8){
//                        Toast.makeText(context,"Order id invalid",Toast.LENGTH_LONG).show();
                        track_parcel_order_id.setError("Order id invalid");
                        track_parcel_order_id.requestFocus();
                    }else {
                        TrackParcelView(new TrackParcelViewHolder(itemView),track_parcel_order_id.getText().toString().trim());
                    }
                }
            });
        }
    }

    private void TrackParcelView(final TrackParcelViewHolder holder, String orderId) {
        final ProgressDialog loading;
        /**progress dialog initialization*/
        loading = new ProgressDialog(context, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        Home.trackData.clear();
        new RemoteDataDownload(context).trackParcel(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                loading.dismiss();
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);

                        String time = post.getString("start_datetime_stamp");
                        time = new ParseDateTimeStamp(time).getDateTimeInFormat("dd MMM,yy hh:mm a");
                        String position = post.getString("short_desc");
                        setTrackData(position,time);
                        }
                    TrackParcelAdapterDashbord adapter = new TrackParcelAdapterDashbord (getTrackata(),context);
                    holder.trackParcelRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    holder.trackParcelRecyclerView.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(context, "success catch"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void noDataFound() {
                loading.dismiss();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
            }

            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
            }
        },"058",orderId);
//        TrackParcelAdapterDashbord adapter = new TrackParcelAdapterDashbord (getTrackata(),context);
//        holder.trackParcelRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//        holder.trackParcelRecyclerView.setAdapter(adapter);
    }


    ////////////////Send parcelwith in///////////////////
    public class SendParcelWithInViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recommentedRecyclerView;

        SendParcelWithInViewHolder(View itemView) {
            super(itemView);
            recommentedRecyclerView = itemView.findViewById(R.id.recommended_layout_recyclerView);
        }
    }

    private void sendParcelWithInView(SendParcelWithInViewHolder holder) {
        RecommentedAdapter adapter = new RecommentedAdapter(getRecommentedData(), context);
        holder.recommentedRecyclerView.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        holder.recommentedRecyclerView.setAdapter(adapter);

    }


    //    view pager offers
    public class OffersViewPagerViewHolder extends RecyclerView.ViewHolder {
        OffersViewPagerViewHolder(View itemView) {
            super(itemView);
            sliderPager = itemView.findViewById(R.id.slider_pager);
            indicator = itemView.findViewById(R.id.indicator);
        }
    }

    private void viewFlipperView(OffersViewPagerViewHolder holder) {
        ViewPagerDataAdapter adapter = new ViewPagerDataAdapter(context, getViewPagerData());
        sliderPager.setAdapter(adapter);
        sliderPager.setPageTransformer(true, new DepthPageTransformer());
        indicator.setupWithViewPager(sliderPager, true);

        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES-1) {
                    currentPage = 0;
                }
                sliderPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    /** view pager animation*/
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



    //   exciting offers recyclerview
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class ExcitingOffersViewHolder extends RecyclerView.ViewHolder {
        RecyclerView offersRecyclerView;

        ExcitingOffersViewHolder(View itemView) {
            super(itemView);
            offersRecyclerView = itemView.findViewById(R.id.exciting_offers_recycler_view);
        }
    }

    private void excitingOffersView(HomeAdapter.ExcitingOffersViewHolder holder) {

        HomeOfferAdapter adapter = new HomeOfferAdapter(getHomeOffersrData(), context);
        holder.offersRecyclerView.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
        holder.offersRecyclerView.setAdapter(adapter);
    }

}



