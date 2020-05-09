package com.example.shop.aggregator.RemoteConnection;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop.aggregator.notificationCarrier.NotificationAggrigator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemoteConnection {

    Context context;
    Url url;

    public RemoteConnection(Context context) {
        this.context = context;
        url = new Url();
    }

    public void registerAggrigator(final VolleyCallback callback, final String phoneNumber, final String email, final String password, final String requestCode){
        String URL_REGISTRATION = url.getURL_AGGREGATORLOGINFUNCTIONALITY();
        Log.println(Log.ASSERT,"HITTING:",URL_REGISTRATION);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_REGISTRATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"RESPONSE:",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1)
                                callback.onSuccess(flagSuccess);
                            else
                                callback.noDataFound();
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT,"ERROR RESPONSE",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_code", requestCode);
                params.put("phone", phoneNumber);
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void carrierNotificationsFetch(final VolleyCallback callback, final String phoneNumber, final String requestCode,
                                          final String eventId, final String progress, final String receiverId){
        String URL_NOTIFICATION = url.getURL_AFTERCONFIRMEDBYCARRIER();
        Log.println(Log.ASSERT,"HITTINH:",URL_NOTIFICATION);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_NOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"RESPONSE:",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
//                            Toast.makeText(context, String.valueOf(flagSuccess)+response, Toast.LENGTH_LONG).show();
                            if(flagSuccess == 1)
                                callback.onSuccess(jsonObject);
                            else
                                callback.noDataFound();
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT,"ERROR RESPONSE:",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("carrier_id", phoneNumber);
                params.put("req_code", requestCode);
                params.put("event_id", eventId);
                params.put("progress", progress);
                params.put("rec_id", receiverId);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void myDeliveriesFetch(final VolleyCallback callback, final String phoneNumber, final String requestCode){
        String URL_MYDELIVERIES = url.getURL_FETCHDELIVERYDETAILS();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_MYDELIVERIES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
//                            Toast.makeText(context, String.valueOf(flagSuccess)+response, Toast.LENGTH_LONG).show();
                            if(flagSuccess == 1)
                                callback.onSuccess(jsonObject);
                            else
                                callback.noDataFound();
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", phoneNumber);
                params.put("req_code", requestCode);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void updateJourneyDetails(final VolleyCallback callback, final String phone, final String pnr, final String trainNumber, final String date,
                                     final String time, final String from, final String to){
        String URL_JOURNEYUPLOAD = url.getURL_JOURNEYUPLOAD();
        Log.println(Log.ASSERT,"Hitting: ",URL_JOURNEYUPLOAD);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_JOURNEYUPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
//                            Toast.makeText(context, String.valueOf(flagSuccess)+response, Toast.LENGTH_LONG).show();
                            if(flagSuccess == 1)
                                callback.onSuccess(jsonObject);
                            else
                                callback.noDataFound();
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT,"Error Response: ",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("pnr", pnr);
                params.put("travellers_id", phone);
                params.put("train_no", trainNumber);
                params.put("dep_date", date);
                params.put("dep_time", time);
                params.put("from_stn_code", from);
                params.put("to_stn_code", to);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void walletFetch(final VolleyCallback callback, final String phoneNumber, final String requestCode){
        String URL_WALLET = url.getURL_WHOLEWALLETFUNCTIONALITY();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_WALLET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
//                            Toast.makeText(context, String.valueOf(flagSuccess)+response, Toast.LENGTH_LONG).show();
                            if(flagSuccess == 1)
                                callback.onSuccess(jsonObject);
                            else
                                callback.noDataFound();
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_phone_number", phoneNumber);
                params.put("req_code", requestCode);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void profileFetch(final VolleyCallback callback, final String phoneNumber, final String requestCode){
        String URL_WALLET = url.getURL_AGGREGATORLOGINFUNCTIONALITY();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_WALLET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
//                            Toast.makeText(context, String.valueOf(flagSuccess)+response, Toast.LENGTH_LONG).show();
                            if(flagSuccess == 1)
                                callback.onSuccess(jsonObject);
                            else
                                callback.noDataFound();
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phoneNumber);
                params.put("req_code", requestCode);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }


    public void parcelWheelerDetails(final VolleyCallback callback,final String requestCode,final String packetId, final String phoneNumber){
        String URL_PARCELWHEELERDETAILS = url.getURL_EXTRASADDED();
        Log.println(Log.ASSERT,"Hitting: ",URL_PARCELWHEELERDETAILS);
        Log.println(Log.ASSERT,"Sending: ",requestCode+"+"+packetId+"+"+phoneNumber);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_PARCELWHEELERDETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
//                            Toast.makeText(context, String.valueOf(flagSuccess)+response, Toast.LENGTH_LONG).show();
                            if(flagSuccess == 1)
                                callback.onSuccess(jsonObject);
                            else
                                callback.noDataFound();
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT,"Error Response: ",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_code", requestCode);
                params.put("packet_id", packetId);
                params.put("phone_number", phoneNumber);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }


}
