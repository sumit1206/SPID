package com.sumit.spid.databasesupport.remote;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RemoteDataCheck {

    Context context;
    Url url;

    public RemoteDataCheck(Context context) {
        this.context = context;
        url = new Url();
    }

    public void allLogin(final VolleyCallback callback,final String phoneNumber, final String mailId, final String requestCode){
        String URL_LOGIN = url.getURL_LOGIN();
                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        URL_LOGIN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int flagLgn = jsonObject.getInt("success");
                                    callback.onSuccess(flagLgn);

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
                        params.put("user_phone_number", phoneNumber);
                        params.put("User_mail_id", mailId);

                        return params;
                    }
                };
                RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
                req.add(stringRequest);
    }
}
