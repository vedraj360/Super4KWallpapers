package com.vdx.super4kwallpapers.Utils;

import android.util.Log;
import android.view.View;

import com.vdx.super4kwallpapers.R;

public class ClickListners implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case 0:
                Log.d("ID", String.valueOf(v.getId()));
        }
         }
}
