package com.vdx.super4kwallpapers.Utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolleyService {
    private IResult mResultCallback = null;
    private Context context;
    private String key, id, count;
    private ArrayList<String> tlist;
    private int Length;


    public VolleyService(IResult mResultCallback, Context context) {
        this.mResultCallback = mResultCallback;
        this.context = context;
    }

    public VolleyService(IResult mResultCallback, Context context, String key, String id, String count) {
        this.mResultCallback = mResultCallback;
        this.context = context;
        this.key = key;
        this.id = id;
        this.count = count;
    }


    public VolleyService(IResult mResultCallback, Context context, ArrayList<String> tlist, int length) {
        this.mResultCallback = mResultCallback;
        this.context = context;
        this.tlist = tlist;
        this.Length = length;
    }

    public void postData(final String requestType, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (mResultCallback != null) {
                    mResultCallback.notifySuccess(requestType, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null) {
                    mResultCallback.notifyError(requestType, error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("action", "post");
                map.put("key", key);
                map.put("id", id);
                map.put("count", count);
                return map;
            }
        };

        requestQueue.add(request);
    }


    public void postTrendingData(final String requestType, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (mResultCallback != null) {
                    mResultCallback.notifySuccess(requestType, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mResultCallback != null) {
                    mResultCallback.notifyError(requestType, error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                String data = new Gson().toJson(tlist);
                map.put("action", "post");
                map.put("links", data);
                map.put("length", String.valueOf(Length));
                return map;
            }
        };

        requestQueue.add(request);
    }

    public void getData(final String requestType, String url) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (mResultCallback != null) {
                        mResultCallback.notifySuccess(requestType, response);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (mResultCallback != null) {
                        mResultCallback.notifyError(requestType, error);
                    }
                }
            });

            requestQueue.add(request);
        } catch (Exception e) {

        }
    }
}


