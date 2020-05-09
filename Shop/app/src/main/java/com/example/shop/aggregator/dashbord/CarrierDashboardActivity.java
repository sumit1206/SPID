package com.example.shop.aggregator.dashbord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.example.shop.BugReport;
import com.example.shop.ContactUs;
import com.example.shop.PushNotification;
import com.example.shop.R;
import com.example.shop.SelectingActivity;
import com.example.shop.aggregator.BgRunner;
import com.example.shop.aggregator.CarrierTransaction.CarrierTransaction;
import com.example.shop.aggregator.RemoteConnection.RemoteConnection;
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.aggregator.User;
import com.example.shop.aggregator.dashbord.history.HistoryCarrier;
import com.example.shop.aggregator.notificationCarrier.NotificationAggrigator;
import com.example.shop.aggregator.profile.ProfileCarrier;
import com.example.shop.aggregator.CarrierWallet.CarrierWallet;
import com.example.shop.sinch.PlaceCallActivity;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CarrierDashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView carrierMainRecyclerView;

    BgRunner bgRunner;

    TextView aggregator_view_profile_layout;
    static Switch travellingButton;
    String myDeliveryRequestCode;

    //  error page credentials
    static LinearLayout errorLinearLayout;
    static ImageView errorImage;
    static TextView errorMessageText,action_text_error;

    ImageView info_travelling_mode;
    Animation blink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main_aggrigator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        travellingButton = findViewById(R.id.travelling_button);

        new PushNotification(CarrierDashboardActivity.this,new User(CarrierDashboardActivity.this).getPhoneNumber());


        try {
            readSms();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        myDeliveryRequestCode = "022";

        setSupportActionBar(toolbar);
        toolbar.setTitle("Spid Pegion");
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);


        blink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        //info
        info_travelling_mode =  findViewById(R.id.info_travelling_mode);
        info_travelling_mode.setVisibility(View.VISIBLE);
        info_travelling_mode.startAnimation(blink);

        info_travelling_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoIntent = new Intent(CarrierDashboardActivity.this, InfoActtvity.class);
                startActivity(infoIntent);
            }
        });

        /*error page credentials*/
        errorLinearLayout = findViewById(R.id.error_layout);
        errorImage = findViewById(R.id.error_image);
        errorMessageText = findViewById(R.id.message_error_loading);
        action_text_error = findViewById(R.id.action_text_error);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        carrierMainRecyclerView = findViewById(R.id.carrier_dashboard_recyclerView);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
//        View headerview = navigationView.getHeaderView(0);
//        aggregator_view_profile_layout = headerview.findViewById(R.id.profile_activity_name_layout);
//        aggregator_view_profile_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent profileIntent = new Intent (CarrierDashboardActivity.this,ProfileCarrier.class);
//                startActivity(profileIntent);
//            }
//        });

        bgRunner = new BgRunner(CarrierDashboardActivity.this,carrierMainRecyclerView);

        if(bgRunner.isRunning()){
            travellingButton.setChecked(true);
        }

        travellingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (travellingButton.isChecked())
                {
                    myDeliveryRequestCode = "039";//Aggrigator
                    bgRunner.startRepeatingTask();
                    prepareCarrierData(CarrierDashboardActivity.this,carrierMainRecyclerView,myDeliveryRequestCode);
                }else {
                    myDeliveryRequestCode = "022";//Carrier
                    bgRunner.stopRepeatingTask();
                    prepareCarrierData(CarrierDashboardActivity.this,carrierMainRecyclerView,myDeliveryRequestCode);
                }
            }
        });

    }

    public static void prepareCarrierData (final Context context, final RecyclerView carrierMainRecyclerView, final String myDeliveryRequestCode) {

        final ProgressDialog loading;
        final List<CarrierMainData> carrierMainArryList = new ArrayList<>();
        final CarrierMainAdapter carrierMainAdapter;


        /**progress dialog initialization*/
        loading = new ProgressDialog(context, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        carrierMainAdapter = new CarrierMainAdapter(context,carrierMainArryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        carrierMainRecyclerView.setLayoutManager(layoutManager);
        carrierMainRecyclerView.setAdapter(carrierMainAdapter);

        loading.show();
        carrierMainArryList.clear();
        new RemoteConnection(context).myDeliveriesFetch(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                errorLinearLayout.setVisibility(View.GONE);
                CarrierMainData carrierMainData;
                JSONObject jsonObject = (JSONObject) result;
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("details");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject post = jsonArray.getJSONObject(i);
                        String packetId = post.getString("packet_id");
                        String eventId = post.getString("event_id");
                        String type = post.getString("type");
                        String weight = post.getString("weight");
                        String dimension = post.getString("dimentions");
                        String notes = post.getString("notes_for_carrier");
                        String image = post.getString("image_one");
                        String about = post.getString("about");
                        String senderId = post.getString("sender_id");
                        String carrierId = post.getString("carrier_id");
                        String receiverId = post.getString("reciver_id");
                        String receiverName = post.getString("reciver_name");
                        String insurance = post.getString("insurence");
                        String paidStatus = post.getString("paid");
                        String cost = post.getString("cost");
                        float c = Integer.valueOf(cost);
                        if(myDeliveryRequestCode.equals("022")){//carrier
                            c = (float) (c * 0.25);
                        }
                        if(myDeliveryRequestCode.equals("039")){//aggrigator
                            c = (float) (c * 0.05);
                        }
                        cost = "You earn Rs."+String.valueOf(c);
                        String fromAddress = post.getString("from_address");
                        String from[] = fromAddress.split(",");
                        fromAddress = from[0];
                        String toAddress = post.getString("to_address");
                        String to[] = toAddress.split(",");
                        toAddress = to[0];
                        String deliveryTime = post.getString("duration_hr");
                        String progress = post.getString("progress");
                        if(myDeliveryRequestCode.equals("022") && progress.equals("2")){
                            progress = "With you";
                        }else{
                            progress = "";
                        }
                        carrierMainData = new CarrierMainData(packetId, image, fromAddress, toAddress, deliveryTime, progress, eventId, senderId,
                                carrierId, receiverId, receiverName, insurance, paidStatus, cost,type,weight,about,dimension, notes);
                        carrierMainArryList.add(carrierMainData);
                        carrierMainAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
                    carrierMainAdapter.notifyDataSetChanged();
                }
                loading.dismiss();
            }

            @Override
            public void noDataFound()
            {
                loading.dismiss();
                errorLinearLayout.setVisibility(View.VISIBLE);
                errorMessageText.setText(R.string.aleart_travelling_mode);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(context).load(R.raw.walking).into(imageViewTarget);
                action_text_error.setText(R.string.on_traveling_mode);

                carrierMainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCatch(JSONException e) {
                loading.dismiss();
                carrierMainAdapter.notifyDataSetChanged();
//                Toast.makeText(context,"On catch",Toast.LENGTH_LONG).show();
                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(context).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prepareCarrierData (context,carrierMainRecyclerView,myDeliveryRequestCode);
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);

            }


            @Override
            public void onError(VolleyError e) {
                loading.dismiss();
                carrierMainAdapter.notifyDataSetChanged();

                errorMessageText.setText(R.string.internet_connection_error);
                DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(errorImage);
                Glide.with(context).load(R.raw.slow_internet_error).into(imageViewTarget);
                action_text_error.setText(R.string.action_text_no_internet);
                action_text_error.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prepareCarrierData (context,carrierMainRecyclerView,myDeliveryRequestCode);
                    }
                });
                errorLinearLayout.setVisibility(View.VISIBLE);


            }
        },new User(context).getPhoneNumber(),myDeliveryRequestCode);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.nav_profile:
                Intent profileIntent = new Intent(CarrierDashboardActivity.this, ProfileCarrier.class);
                startActivity(profileIntent);
                break;

            case R.id.nav_wallet:

                Intent walletIntent = new Intent (this, CarrierWallet.class);
                startActivity(walletIntent);
                break;


                /** will be added */

//            case R.id.nav_history:
//
//                Intent transactionIntent = new Intent (this, CarrierTransaction.class);
//                startActivity(transactionIntent);
//                break;

            case R.id.nav_share:
                new BugReport(CarrierDashboardActivity.this,"").shareApp();
                break;

            case R.id.nav_help:

                Intent contactIntent = new Intent(this, ContactUs.class);
                startActivity(contactIntent);
                break;

            case R.id.nav_report:
                new BugReport(CarrierDashboardActivity.this,"").sendEmail();
                break;

            case R.id.legal:

                Toast.makeText(this,"Will be added Soon",Toast.LENGTH_LONG).show();
                break;

            case R.id.privacy:
                Toast.makeText(this,"Will be added Soon",Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_logout:
                new User(CarrierDashboardActivity.this).removeUser();
                bgRunner.stopRepeatingTask();
                Intent logoutSinch = new Intent (CarrierDashboardActivity.this, PlaceCallActivity.class);
                logoutSinch.putExtra("logout","1");
                startActivity(logoutSinch);
//                startActivity(new Intent(CarrierDashboardActivity.this, SelectingActivity.class));
                finish();
                break;

            default:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.notification) {

            Intent search_intent = new Intent(CarrierDashboardActivity.this, NotificationAggrigator.class);
            startActivity(search_intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        prepareCarrierData(CarrierDashboardActivity.this,carrierMainRecyclerView,myDeliveryRequestCode);
        super.onResume();
    }

    public static void travellingSwitchOff(){
        travellingButton.setChecked(false);
    }

    /** Reading SMS from IRCTC*/

    private void readSms() throws ParseException {
        Log.println(Log.ASSERT,"readSms","in read Sms block");
        String number = "IRCTC";
        int day=15,hour=24;//sms only for last 'day' days and 'hour' hours is read
        Date d = new Date();
        CharSequence s  = DateFormat.format("yyyy-MM-dd", d.getTime()-(60*1000*60*hour*day));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        Date dateStart = formatter.parse(s + "T00:00:00");

        String filter = "date>=" + dateStart.getTime();
        final Uri SMS_INBOX = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(SMS_INBOX, null, filter, null, null);

        while(cursor.moveToNext()) {

            String Number = cursor.getString(cursor.getColumnIndexOrThrow("address")).toString();
            String Body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();

            /*Reading only train booking sms*/

            if (Number.contains(number) && Body.contains("PNR") && Body.contains("DOJ") && Body.contains("Fare") && (Body.contains("DP") || Body.contains("Dep"))&& (Body.contains("TRN") || Body.contains("TRAIN"))) {
                try {
                    Log.println(Log.ASSERT,"Messege body","body"+Body);
                    fetch(Body);//fetching selected details from body
                }catch (Exception e){}
            }

        }
        cursor.close();
    }

    /**Fetching data from sms*/

    void fetch(String sample){
        String kdt[],kv[],key,value;
        String pnr = "", trainNo = "", date = "", time = "", to = "", from = "", dateTime= "";
//        String sample = "PNR:2201302023,TRN:12372,DOJ:03-10-19,SL,DLI-HWH,DP:15:05,\n" +
//                "DIPTESH CHAKRABO+3,WL 18,WL 19,WL 20,WL 21,\n" +
//                "Fare:3140,C Fee:17.7+PG";
        sample = sample.replace("\n", "");
        String allData[] = sample.split(",");
        for (String data:allData) {
            if(occurance(data,':') == 1) {
                kv = data.split(":");
                key = kv[0];
                value = kv[1];
                if(key.toUpperCase().contains("PNR"))
                    pnr = value;
                else if(key.toUpperCase().contains("TRN") || key.toUpperCase().contains("TRAIN"))
                    trainNo = value;
                else if(key.toUpperCase().contains("DOJ")) {
                    date = value;
                    String dmy[] = date.split("-");
                    String d = dmy[0];
                    String m = dmy[1];
                    String y = dmy[2];
                    date = "20"+y+"-"+m+"-"+d;
                }
            }else if(occurance(data,':') == 2){
                kdt = data.split(":");
                key = kdt[0];
                if(key.toUpperCase().contains("DP") || key.contains("Dep")){
                    time = kdt[1]+":"+kdt[2];
                }
            }else if(occurance(data,'-') == 1){
                kv = data.split("-");
                from = kv[0];
                to = kv[1];
            }
        }
//        dateTime = date+" "+time+":00";

        uploadJoourneyDetails(pnr,trainNo,date,time,from,to);

//        System.out.println(pnr);
//        System.out.println(trainNo);
//        System.out.println(date);
//        System.out.println(time);
//        System.out.println(to);
//        System.out.println(from);
    }

    int occurance(String s, char c) {
        int l = s.length(), count = 0;
        for (int i = 0; i < l; i++)
            if (s.charAt(i) == c)
                count++;
        return count;
    }

    void uploadJoourneyDetails(String pnr, String trainNumber, String date, String time, String from, String to){
        new RemoteConnection(CarrierDashboardActivity.this).updateJourneyDetails(new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {


            }

            @Override
            public void noDataFound() {

            }

            @Override
            public void onCatch(JSONException e) {

            }

            @Override
            public void onError(VolleyError e) {

            }
        },new User(CarrierDashboardActivity.this).getPhoneNumber(),pnr,trainNumber,date,time,from,to);
    }


}
