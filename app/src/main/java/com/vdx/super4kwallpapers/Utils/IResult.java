package com.vdx.super4kwallpapers.Utils;

import com.android.volley.VolleyError;

public interface IResult {
    public void notifySuccess(String requestType,String response);
    public void notifyError(String requestType,VolleyError error);
}