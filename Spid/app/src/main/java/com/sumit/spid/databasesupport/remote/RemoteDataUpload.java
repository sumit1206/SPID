package com.sumit.spid.databasesupport.remote;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemoteDataUpload {

    Context context;
    Url url;

    public RemoteDataUpload(Context context) {
        this.context = context;
        url = new Url();
    }

    public void addNotification(final VolleyCallback callback, final String title, final String body, final String type,
                                final String eventId, final String senderPhoneNumber, final String receiverId, final String requestCode){
        String URL_NOTIFICATION = url.getURL_NOTIFICATION();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_NOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_code", requestCode);
                params.put("notification_sender_id", senderPhoneNumber);
                params.put("title", title);
                params.put("body", body);
                params.put("type", type);
                params.put("event_id", eventId);
                params.put("notification_reciever_id", receiverId);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void confirmingDelivery(final VolleyCallback callback, final String dropTime, final String deliveryTime, final String toStnList, final String fromStnList,
                                   final String senderPhone, final String description, final String type, final String weight, final String receiverNumber, final String price,
                                   final String valueDimention, final String valueInsuranceOpted, final String valueInsuranceCharge, final String valueNotesForCarrier, final String valueReceiverName,
                                   final String valueReceiverAddress, final String valueImageOne, final String valueImageTwo, final String valueFromAddress, final String valueToAddress,
                                   final String toLatitude, final String toLongitude){
        String URL_CONFORM = url.getURL_SEARCHRESPONSERECEIVEANDNOTIFICATIONTOCARRIER();
        Log.println(Log.ASSERT,"Hitting: ",URL_CONFORM);
        Log.println(Log.ASSERT,"Sending: ",toLatitude+""+toLongitude);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_CONFORM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
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
                        Log.println(Log.ASSERT,"Error response: ",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("departure_group", dropTime);
                params.put("duration_h", deliveryTime);
                params.put("to_stn_comma_seperated", toStnList);
                params.put("from_stn_comma_seperated", fromStnList);
                params.put("notification_sender_id", senderPhone);
                params.put("about", description);
                params.put("type", type);
                params.put("weight", weight);
                params.put("packet_reciver_id", receiverNumber);
                params.put("cost", price);

                params.put("packet_sender_id", senderPhone);
                params.put("dimentions", valueDimention);
                params.put("insurence_opted", valueInsuranceOpted);
                params.put("insurence_charge", valueInsuranceCharge);
                params.put("notes_for_carrier", valueNotesForCarrier);
                params.put("reciver_name", valueReceiverName);
                params.put("reciver_address", valueReceiverAddress);
                params.put("image_one", valueImageOne);
                params.put("image_two", valueImageTwo);
                params.put("from_address",valueFromAddress);
                params.put("to_address",valueToAddress);
                params.put("to_lat",toLatitude);
                params.put("to_lan",toLongitude);

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void cancelBooking(final VolleyCallback callback, final String eventId, final String packetId, final String requestCode){
        String URL_CANCELBOOKING = url.getURL_USERPROFILEFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_CANCELBOOKING);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_CANCELBOOKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
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
                        Log.println(Log.ASSERT,"Error response: ",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_code", requestCode);
                params.put("packet_id", packetId);
                params.put("event_id", eventId);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void setOtp(final VolleyCallback callback, final String eventId, final String otp, final String receiver, final String requestCode){
        String URL_SETOTP = url.getURL_AFTERCONFORMEDBYCARRIER();
        Log.println(Log.ASSERT,"Hitting: ",URL_SETOTP);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_SETOTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
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
                        Log.println(Log.ASSERT,"Error response: ",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_code", requestCode);
                params.put("otp", otp);
                params.put("rec_id", receiver);
                params.put("event_id", eventId);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void setReceiverPhoneNumber(final VolleyCallback callback, final String eventId, final String packetId, final String receiverNumber, final String requestCode){
        String URL_SETNUMBER = url.getURL_USERPROFILEFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_SETNUMBER);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_SETNUMBER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
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
                        Log.println(Log.ASSERT,"Error response: ",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_code", requestCode);
                params.put("packet_id", packetId);
                params.put("event_id", eventId);
                params.put("user_phone_number", receiverNumber);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void addMoneyOnWallet(final VolleyCallback callback, final String amount, final String number, final String requestCode){
        String URL_ADDMONEY = url.getURL_WHOLEWALLETFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_ADDMONEY);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_ADDMONEY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
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
                        Log.println(Log.ASSERT,"Error response: ",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("req_code", requestCode);
                params.put("amount", amount);
                params.put("user_phone_number", number);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

}
