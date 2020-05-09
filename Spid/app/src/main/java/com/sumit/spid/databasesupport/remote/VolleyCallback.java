package com.sumit.spid.databasesupport.remote;

import com.android.volley.VolleyError;

import org.json.JSONException;

public abstract class VolleyCallback {
    public abstract void onSuccess(Object result);
    public abstract void noDataFound();
    public abstract void onCatch(JSONException e);
    public abstract void onError(VolleyError e);
}