package com.vdx.super4kwallpapers.Adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;

import com.squareup.picasso.Picasso;
import com.vdx.super4kwallpapers.Models.Sheet1;
import com.vdx.super4kwallpapers.R;


import java.util.List;

public class ImagePageAdapter extends PagerAdapter {

    private Context context;
    private List<Sheet1> sheet1List;
    private String wallType;


    public ImagePageAdapter(Context context, List<Sheet1> sheet1List, String wallType) {
        this.context = context;
        this.sheet1List = sheet1List;
        this.wallType = wallType;
    }

    @Override
    public int getCount() {
        return sheet1List.size();
    }

    //innerhtml.bind
    // GAURAV.NITD2015@GMAIL.COM
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, null);
        PhotoView imageView = view.findViewById(R.id.wallpaper_image);
        final Sheet1 list = sheet1List.get(position);
        String link;
        Log.d("WALLTYPE", wallType);
        if (wallType.equals("4k")) {
            link = list.getLinks();
        } else if (wallType.equals("trending")) {
            link = list.getTrendinglinks();
        } else {
            link = list.getAmoledlinks();
        }

        Picasso.get().load(link).fit().placeholder(R.color.black).into(imageView);


        container.addView(view);
        return view;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

}
