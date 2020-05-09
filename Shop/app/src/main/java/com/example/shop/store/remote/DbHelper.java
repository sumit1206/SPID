package com.example.shop.store.remote;

import android.content.Context;
import android.content.Intent;
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
import com.example.shop.aggregator.RemoteConnection.VolleyCallback;
import com.example.shop.store.scanfunctionality.ItemDetaisEdit;
import com.example.shop.store.scanfunctionality.ScanQrCamera;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Stores most of the data uploading functions*/


public class DbHelper {

    public DbHelper(Context context) {
        this.context = context;
    }

    Context context;
    String ROOT_URL = "http://197.189.202.8/spid_dev/";
    String URL_LOGINREGISTER = ROOT_URL+"whealer_funtionality_class.php";
    String URL_QRFUNCTIONALITY = ROOT_URL+"total_aggregator_funtionality.php";
    String URL_STOCKFUNCTIONALITY = ROOT_URL+"whealer_funtionality_class.php";
    String URL_PRICEFETCH = ROOT_URL+"all_pricing_funtionality.php";
    String URL_VERIFYOTP = ROOT_URL+"after_confirmed_by_carrier.php";
    String URL_UPDATEPARCELDETAILS = ROOT_URL+"whealer_funtionality_class.php";
    String URL_WALLET = ROOT_URL+"whole_wallet_funtionality.php";


    public void loginRegister(final VolleyCallbackStore callback, final String requestCode, final String id, final String shopName, final String ownerName,
                              final String mail, final String phone, final String address, final String tag, final String latitude, final String longitude,
                              final String aadharNo, final String panNo, final String aadharImage, final String panImage, final String shopImage,
                              final String landmark, final String password){
        Log.println(Log.ASSERT,"Hitting: ",URL_LOGINREGISTER);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_LOGINREGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1) {
                                callback.onSuccess(response);
                            }
                            else {
                                callback.noDataFound();
                            }
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
                params.put("shop_name", shopName);
                params.put("owner_name", ownerName);
                params.put("mail", mail);
                params.put("phone", phone);
                params.put("address", address);
                params.put("tag", tag);
                params.put("latitude", latitude);
                params.put("langitude", longitude);
                params.put("adhar_no", aadharNo);
                params.put("pan_no", panNo);
                params.put("adhar_image", aadharImage);
                params.put("pan_image", panImage);
                params.put("shop_image", shopImage);
                params.put("landmark", landmark);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void setAcceptingStatus(final VolleyCallbackStore callback, final String requestCode, final String id, final String status){
        Log.println(Log.ASSERT,"Hitting: ",URL_LOGINREGISTER);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_LOGINREGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.println(Log.ASSERT,"Response: ",response);
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1) {
                                callback.onSuccess(response);
                            }
                            else {
                                callback.noDataFound();
                            }
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
//                params.put("id", "");
//                params.put("shop_name", "");
//                params.put("owner_name", "");
//                params.put("mail", "");
                params.put("phone", id);
//                params.put("address", "");
//                params.put("tag", "");
//                params.put("latitude", "");
//                params.put("langitude", "");
//                params.put("adhar_no", "");
//                params.put("pan_no", "");
//                params.put("adhar_image", "");
//                params.put("pan_image", "");
//                params.put("shop_image", "");
//                params.put("landmark", "");
//                params.put("password", "");
                params.put("status", status);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }



    public void verifyQr(final VolleyCallbackStore callback, final String eventId, final String number, final String wheelerPhone, final String latitude, final String longitude){
        Log.println(Log.ASSERT,"Hitting: ",URL_QRFUNCTIONALITY);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_QRFUNCTIONALITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response:",response);
//                        Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1 || flagSuccess == 0) {
                                callback.onSuccess(response);
                            }
                            else if(flagSuccess == 2){
                                Intent editIntent = new Intent(context, ItemDetaisEdit.class);
                                editIntent.putExtra("response",response);
                                ScanQrCamera.loading.dismiss();
                                ScanQrCamera.scanQrCamera.finish();
                                context.startActivity(editIntent);
                            }
                            else {
                                callback.noDataFound();
                            }
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT,"Error Response",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("event_id", eventId);
                params.put("mobile_num", number);
                params.put("whealerno", wheelerPhone);
                params.put("lat", latitude);
                params.put("lan", longitude);
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

    public void fetchStock(final VolleyCallbackStore callback, final String wheelerPhone, final String requestCode){
        Log.println(Log.ASSERT,"Response",URL_STOCKFUNCTIONALITY);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_STOCKFUNCTIONALITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1) {
                                callback.onSuccess(jsonObject);
                            }
                            else {
                                callback.noDataFound();
                            }
                        } catch (JSONException e) {
                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT,"Error Response",Log.getStackTraceString(error));
                        callback.onError(error);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("phone", wheelerPhone);
                params.put("req_code", requestCode);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void priceFetch(final VolleyCallback callback, final String type, final String requestCode){
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

    public void updateParcelDetails(final VolleyCallback callback, final String requestCode, final String type, final String dimension, final String weight,
                                    final String price, final String packetId, final String excessPrice){
        Log.println(Log.ASSERT,"Hitting: ",URL_UPDATEPARCELDETAILS);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_UPDATEPARCELDETAILS,
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
                params.put("weight", weight);
                params.put("dimentions", dimension);
                params.put("price", price);
                params.put("packet_id", packetId);
                params.put("changed_price", excessPrice);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void verifyOtp(final VolleyCallback callback, final String requestCode, final String packetId, final String otp){
        Log.println(Log.ASSERT,"Hitting: ",URL_VERIFYOTP);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_VERIFYOTP,
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
                params.put("otp", otp);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void pfofileFetch(final VolleyCallback callback, final String requestCode, final String phoneNumber){
        Log.println(Log.ASSERT,"Hitting: ",URL_LOGINREGISTER);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_LOGINREGISTER,
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
                params.put("phone", phoneNumber);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }

    public void walletFetch(final VolleyCallback callback, final String phoneNumber, final String requestCode){
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

    public void addDeductFromWallet(final String amount, final String number, final String requestCode){
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
                            if(flagSuccess == 1) {
//                                callback.onSuccess(flagSuccess);
                            }
                            else {
//                                callback.noDataFound();
                            }
                        } catch (JSONException e) {
//                            callback.onCatch(e);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT,"Error response: ",Log.getStackTraceString(error));
//                        callback.onError(error);
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
