
package com.vdx.super4kwallpapers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sheet1 {

    @SerializedName("links")
    @Expose
    private String links;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("trendinglinks")
    @Expose
    private String trendinglinks;

    @SerializedName("amoledlinks")
    @Expose
    private String amoledlinks;


    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTrendinglinks() {
        return trendinglinks;
    }

    public void setTrendinglinks(String trendinglinks) {
        this.trendinglinks = trendinglinks;
    }

    public String getAmoledlinks() {
        return amoledlinks;
    }

    public void setAmoledlinks(String amoledlinks) {
        this.amoledlinks = amoledlinks;
    }
}
