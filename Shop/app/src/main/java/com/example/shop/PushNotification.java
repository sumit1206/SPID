package com.example.shop;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class PushNotification {
    Context context;
    String userId;
    public PushNotification(final Context context, final String userId) {
        this.context = context;
        this.userId = userId;
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//To do//
                            return;
                        }

// Get the Instance ID token//
                        String token = task.getResult().getToken();
                        String msg = context.getString(R.string.fcm_token, token);
                        Log.d(TAG, msg);
                        Log.println(Log.ASSERT,"FCM Token:",token);
                        setFcmToken(token,userId);
                    }
                });
    }

    public void setFcmToken(final String TOKEN, final String USER){
        String URL_FCMTOKEN = "http://197.189.202.8/spid_dev/fcmInsert.php";
        Log.println(Log.ASSERT,"Hitting: ",URL_FCMTOKEN);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL_FCMTOKEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.println(Log.ASSERT,"Response: ",response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int flagSuccess = jsonObject.getInt("success");
                            if(flagSuccess == 1){

                            }else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.println(Log.ASSERT,"Error response: ",Log.getStackTraceString(error));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("token", TOKEN);
                params.put("user_id", USER);
                return params;
            }
        };
        RequestQueue req = (RequestQueue) Volley.newRequestQueue(context);
        req.add(stringRequest);
    }


}
