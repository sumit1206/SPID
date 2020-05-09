package com.example.shop.aggregator;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.R;
import com.example.shop.aggregator.RemoteConnection.Url;
import com.example.shop.aggregator.dashbord.CarrierDashboardActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BgRunner {

    private static int INTERVAL;
    MediaPlayer alertTone;
    boolean isRunning;
    Handler mHandler;
    Runnable mHandlerTask;
    Context context;
    RecyclerView carrierMainRecyclerView;
    ProgressDialog loading;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    Location mLastKnownLocation;
    User user;
    int i = 0;
    String eventId;
    String previousEventId="";
    String URL_ACCEPTREJECT = "http://197.189.202.8/spid_dev/agregator_accept_reject.php";

    public BgRunner(final Context context, RecyclerView carrierMainRecyclerView) {
        this.context = context;
        this.carrierMainRecyclerView = carrierMainRecyclerView;
        INTERVAL = 1000 * 30 * 1; //30 seconds
        user = new User(context);
        mHandler = new Handler();
        isRunning = false;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        alertTone= MediaPlayer.create(context,R.raw.never);

        /**progress dialog initialization*/
        loading = new ProgressDialog(context, R.style.ProgressDialog);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        mHandlerTask = new Runnable() {
            @Override
            public void run() {
                i++;
                Log.println(Log.ASSERT, "try", "iteration " + i);
                if(!user.getPhoneNumber().equals("")) {
                    getDeviceLocation();
                    checkNotification();
                }else{
                    stopRepeatingTask();
                }
                mHandler.postDelayed(mHandlerTask, INTERVAL);
            }
        };
    }
    public void startRepeatingTask()
    {

        mHandlerTask.run();
        isRunning = true;
    }

    public void stopRepeatingTask()
    {
        mHandler.removeCallbacks(mHandlerTask);
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }


    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            try {
                                mLastKnownLocation = task.getResult();
                                LatLng latLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                                Log.println(Log.ASSERT, "If", latLng.latitude + "+" + latLng.longitude);
                                updateLocation(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude), user.getPhoneNumber());
                            }catch (Exception e){
                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                                alertDialog.setTitle("Sorry!");
                                alertDialog.setMessage("A problem occurred while fetching your location, please open Google map and calibrate");
                                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                                CarrierDashboardActivity.travellingSwitchOff();
                                alertDialog.show();
                            }
                        } else {
                            Log.println(Log.ASSERT, "else","task not successful");
                        }
                    }
                });
        } catch (SecurityException e)  {
            Log.println(Log.ASSERT, "Catch",Log.getStackTraceString(e));
        }
    }

    //TODO: Updating last location in every 5 minutes

    public void updateLocation(final String latitude, final String longitude, final String number){
        String URL_LATLONTRACK = new Url().getURL_AGGRIGATORFUNCTIONALITY();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_LATLONTRACK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1) {
//                                Toast.makeText(context, "success " + response, Toast.LENGTH_LONG).show();
                                }
                            else {
//                                Toast.makeText(context,"failed "+response,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
//                            Toast.makeText(context,"catch "+e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context,"error "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("req_code", "033");
                params.put("last_lat", latitude);
                params.put("last_lan", longitude);
                params.put("id", number);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    /** Updating block ends here */

    //TODO: check for notifications

    public void checkNotification(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_ACCEPTREJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.println(Log.ASSERT, "checkNotification try", response);
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1) {
//                                Toast.makeText(context, "success " + response, Toast.LENGTH_LONG).show();
                                JSONArray jsonArray = jsonObject.getJSONArray("details");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject post = jsonArray.getJSONObject(i);

                                    String body = post.getString("body");
                                    String aggregator_id = post.getString("aggregator_id");
                                    String type = post.getString("type");
                                    eventId = post.getString("event_id");
                                    String from_address = post.getString("from_address");
                                    String to_address = post.getString("to_address");
                                    String timestamp = post.getString("timestamp");

                                    Log.println(Log.ASSERT, "\nEvent ids", eventId+"+"+previousEventId);
/////////////////////////////////////////
//                                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
//                                    alertDialog.setCancelable(false);
//                                    alertDialog.setTitle("New request");
//                                    alertDialog.setMessage("Deliver parcel from:\n" +from_address+ "\nTo:\n" +to_address);
//                                    alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            accept();
//                                        }
//                                    });
//                                    alertDialog.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            reject();
//                                        }
//                                    });
/////////////////////////////////////////
                                    final Dialog alertDialog = new Dialog(context);
                                    alertDialog.setCancelable(false);
                                    alertDialog.setContentView(R.layout.custom_popup_aggregator);
                                    TextView from = alertDialog.findViewById(R.id.dialog_from_address);
                                    from.setText(from_address);
                                    TextView to = alertDialog.findViewById(R.id.dialog_to_address);
                                    to.setText(to_address);
                                    TextView a = alertDialog.findViewById(R.id.dialog_accept);
                                    a.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            accept();
                                            alertDialog.dismiss();
                                        }
                                    });
                                    TextView r = alertDialog.findViewById(R.id.dialog_reject);
                                    r.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            reject();
                                            alertDialog.dismiss();
                                        }
                                    });
                                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                    //////////////////////////
                                    if(!eventId.equals(previousEventId)) {
                                        alertDialog.show();
                                        alertTone.start();
                                        previousEventId = eventId;
                                    }
                                }
                            }
                            else {
//                                Toast.makeText(context,"checkNotification failed "+response, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
//                            Toast.makeText(context,"checkNotification catch "+e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context,"checkNotification error "+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("req_code", "036");
                params.put("id", user.getPhoneNumber());
                params.put("event_id", "");
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    /**Checking block ends here*/

    //TODO: Accepts request block

    public void accept(){
        loading.show();
//        String URL_LATLONTRACK = "http://197.189.202.8/spid_dev/agregator_accept_reject.php";
        Log.println(Log.ASSERT,"HITTING:",URL_ACCEPTREJECT);
        Log.println(Log.ASSERT,"SENDING:",eventId+"="+user.getPhoneNumber());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_ACCEPTREJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT, "accept try", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1) {
                                Toast.makeText(context, "Request accepted", Toast.LENGTH_LONG).show();
                                CarrierDashboardActivity.prepareCarrierData(context,carrierMainRecyclerView,"039");
                                }
                            else {
                                Toast.makeText(context,"Session expired", Toast.LENGTH_LONG).show();
                            }
                            loading.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(context,"Oops! a server error occurred",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            loading.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("req_code", "037");
                params.put("id", user.getPhoneNumber());
                params.put("event_id", eventId);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    /**Accepting block ends here*/

    //TODO: Reject request block

    public void reject(){
        loading.show();
//        String URL_LATLONTRACK = "http://197.189.202.8/spid_dev/agregator_accept_reject.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_ACCEPTREJECT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT, "reject try", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1) {
                                Toast.makeText(context, "Request rejected", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(context,"Request rejected", Toast.LENGTH_LONG).show();
                            }
                            loading.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(context,"Oops! a server error occurred",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            loading.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("req_code", "038");
                params.put("id", user.getPhoneNumber());
                params.put("event_id", eventId);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }
    /**Accepting block ends here*/
}
