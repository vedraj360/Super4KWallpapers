
package com.vdx.super4kwallpapers.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ModelLinks {

    @SerializedName("Sheet1")
    @Expose
    private ArrayList<Sheet1> sheet1 = null;

    public ArrayList<Sheet1> getSheet1() {
        return sheet1;
    }

    public void setSheet1(ArrayList<Sheet1> sheet1) {
        this.sheet1 = sheet1;
    }

}
