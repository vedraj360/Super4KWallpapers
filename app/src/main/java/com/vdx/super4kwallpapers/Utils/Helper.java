package com.vdx.super4kwallpapers.Utils;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class Helper {
    public Helper() {

    }

    public void RateUs(Context c, String name) {
        try {
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + name)));
        } catch (ActivityNotFoundException e) {
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + name)));
        }

    }

    public void whats_share(Context context) {
        try {
            final ComponentName name = new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker");
            Intent oShareIntent = new Intent();
            oShareIntent.setComponent(name);
            oShareIntent.setType("text/plain");
            oShareIntent.putExtra(Intent.EXTRA_TEXT, "Find the Super and best 4k & Amoled Wallpapers: " + "https://play.google.com/store/apps/details?id=com.vdx.super4kwallpapers");
            context.startActivity(oShareIntent);
        } catch (Exception e) {
            Toast.makeText(context, "Whats App Not Found!", Toast.LENGTH_SHORT).show();
            Log.d("Error", String.valueOf(e));
        }

    }

}
