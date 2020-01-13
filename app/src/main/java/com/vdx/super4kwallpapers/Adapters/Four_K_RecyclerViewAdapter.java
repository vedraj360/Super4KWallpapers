package com.vdx.super4kwallpapers.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vdx.super4kwallpapers.Activities.WallpaperActivity;
import com.vdx.super4kwallpapers.Models.Sheet1;
import com.vdx.super4kwallpapers.R;

import java.util.ArrayList;


public class Four_K_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Sheet1> sheet1List;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_CELL = 1;
    private Context context;
    private String response;


    public Four_K_RecyclerViewAdapter(ArrayList<Sheet1> sheet1List, Context context, String response) {
        this.sheet1List = sheet1List;
        this.context = context;
        this.response = response;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return sheet1List.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        switch (viewType) {
            case TYPE_CELL: {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_item_card_small, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        ImageView imageView;

        switch (getItemViewType(position)) {
            case TYPE_CELL:
                imageView = holder.itemView.findViewById(R.id.wallImage);
                final Sheet1 sheet1 = sheet1List.get(position);
                final String value = sheet1.getLinks();
//                Log.d("Value", value);

                try {
                    Picasso.get().load(value).placeholder(R.drawable.back).fit().into(imageView);

                } catch (Exception e) {
                    Log.e("Error", String.valueOf(e));
                }
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent wallpaperActivityIntent = new Intent(context, WallpaperActivity.class);
                        wallpaperActivityIntent.putExtra("wall", "4k");
                        wallpaperActivityIntent.putExtra("response", response);
                        wallpaperActivityIntent.putExtra("pos", position);
                        context.startActivity(wallpaperActivityIntent);
                    }
                });
                break;
        }
    }
}