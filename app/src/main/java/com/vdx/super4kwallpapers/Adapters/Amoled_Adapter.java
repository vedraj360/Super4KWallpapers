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
import com.vdx.super4kwallpapers.Utils.Links;

import java.util.ArrayList;

public class Amoled_Adapter extends RecyclerView.Adapter<Amoled_Adapter.ViewHolder> {
    private ArrayList<Sheet1> sheet1ArrayList;
    private Context context;
    private String response;

    public Amoled_Adapter(ArrayList<Sheet1> sheet1ArrayList, Context context, String response) {
        this.sheet1ArrayList = sheet1ArrayList;
        this.context = context;
        this.response = response;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_card_small, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Sheet1 sheet1 = sheet1ArrayList.get(position);
        final String value = sheet1.getAmoledlinks();


        try {
            Picasso.get().load(value).placeholder(R.drawable.back).fit().into(holder.imageView);


        } catch (Exception e) {
            Log.e("Error", String.valueOf(e));
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wallpaperActivityIntent = new Intent(context, WallpaperActivity.class);
                wallpaperActivityIntent.putExtra("wall", "Amoled");
                wallpaperActivityIntent.putExtra("response", response);
                wallpaperActivityIntent.putExtra("pos", position);
                context.startActivity(wallpaperActivityIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return sheet1ArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.wallImage);

        }
    }

}
