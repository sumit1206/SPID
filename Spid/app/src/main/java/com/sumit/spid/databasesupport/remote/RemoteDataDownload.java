package com.sumit.spid.databasesupport.remote;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
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

public class RemoteDataDownload{

    Context context;
    Url url;

    public RemoteDataDownload(Context context) {
        this.context = context;
        url = new Url();
    }

    public void carrierSearch(final VolleyCallback callback, final Double fromLat, final Double fromLon, final Double toLat, final Double toLon) {
        String URL_CARRIERSEARCH = url.getURL_CARRIERSEARCH();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_CARRIERSEARCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(String.valueOf(response));
                            int flagLgn = jsonObject.getInt("success");
                            if (flagLgn == 1) {
                                callback.onSuccess(jsonObject);

                            } else {
                                callback.noDataFound();
                            }

                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("from_lat", String.valueOf(fromLat));
                params.put("from_lan", String.valueOf(fromLon));
                params.put("to_lat", String.valueOf(toLat));
                params.put("to_lan", String.valueOf(toLon));
//                params.put("from_lat", "73.873086000000000");
//                params.put("from_lan", "18.529378000000000");
//                params.put("to_lat", "72.836903000000000");
//                params.put("to_lan", "18.944481000000000");
//                Log.println(Log.ERROR,"from lat",String.valueOf(fromLat));
//                Log.println(Log.ERROR,"from lon",String.valueOf(fromLon));
//                Log.println(Log.ERROR,"to lat",String.valueOf(toLat));
//                Log.println(Log.ERROR,"to lon",String.valueOf(toLon));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void notificationsFetch(final VolleyCallback callback, final String carrierId, final String requestCode,
                                          final String eventId, final String progress, final String receiverId){
        String URL_NOTIFICATION = url.getURL_AFTERCONFORMEDBYCARRIER();
        Log.println(Log.ASSERT,"Hitting: ",URL_NOTIFICATION);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_NOTIFICATION,
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
                params.put("carrier_id", carrierId);
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

    public void nearestWheelerFetch(final VolleyCallback callback, final String latitude, final String longitude){
        String URL_NEARESTWHEELER = url.getURL_SEARCHNEARESTSHOP();
        Log.println(Log.ASSERT,"Hitting: ",URL_NEARESTWHEELER);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_NEARESTWHEELER,
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
                params.put("lat", latitude);
                params.put("lan", longitude);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void priceFetch(final VolleyCallback callback, final String type, final String requestCode){
        String URL_PRICEFETCH = url.getURL_ALLPRICINGFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_PRICEFETCH);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_PRICEFETCH,
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
                params.put("type", type);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void historyFetch(final VolleyCallback callback, final String id, final String requestCode){
        String URL_HISTORYFETCH = url.getURL_HISTORYFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_HISTORYFETCH);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_HISTORYFETCH,
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
                params.put("id", id);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void walletFetch(final VolleyCallback callback, final String phoneNumber, final String requestCode){
        String URL_WALLET = url.getURL_WHOLEWALLETFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_WALLET);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_WALLET,
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
                        Log.println(Log.ASSERT,"On Response: ",Log.getStackTraceString(error));
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

    public void promoCodeFetch(final VolleyCallback callback,final String requestCode){
        String URL_PROMOCODEFETCH = url.getURL_ALLPRICINGFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_PROMOCODEFETCH);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_PROMOCODEFETCH,
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
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void promoCodePriceFetch(final VolleyCallback callback, final String requestCode, final String to, final String from, final String code){
        String URL_PROMOCODEPRICEFETCH = url.getURL_ALLPRICINGFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_PROMOCODEPRICEFETCH);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_PROMOCODEPRICEFETCH,
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
                params.put("to", to);
                params.put("from", from);
                params.put("code", code);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void getPickupAndDropWheeler(final VolleyCallback callback, final String requestCode, final String eventId, final String packetId){
        String URL_WHEELERDETAILSFETCH = "";
        Log.println(Log.ASSERT,"Hitting: ",URL_WHEELERDETAILSFETCH);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_WHEELERDETAILSFETCH,
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
                params.put("event_id", eventId);
                params.put("packet_id", packetId);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }


    public void trackParcel(final VolleyCallback callback,final String requestCode,final String packetId){
        String URL_TRACKPARCEL = url.getURL_FETCHDELIVERYDETAILS();
        Log.println(Log.ASSERT,"Hitting: ",URL_TRACKPARCEL);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_TRACKPARCEL,
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
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void parcelWheelerDetails(final VolleyCallback callback,final String requestCode,final String packetId, final String phoneNumber){
        String URL_PARCELWHEELERDETAILS = url.getURL_EXTRASADDED();
        Log.println(Log.ASSERT,"Hitting: ",URL_PARCELWHEELERDETAILS);
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

    public void profileFetch(final VolleyCallback callback,final String requestCode,final String number){
        String URL_PROFILEFETCH = url.getURL_USERPROFILEFUNCTIONALITY();
        Log.println(Log.ASSERT,"Hitting: ",URL_PROFILEFETCH);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_PROFILEFETCH,
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
                params.put("User_id", number);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }


}
