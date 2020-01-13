package com.vdx.super4kwallpapers.Utils.CustomDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.thecode.aestheticdialogs.AestheticDialog;
import com.vdx.super4kwallpapers.R;

import java.util.Objects;

public class InnovativesDialog {

    public final static String SUCCESS = "TOASTER_SUCCESS";
    public final static String ERROR = "TOASTER_ERROR";
    public final static String WARNING = "TOASTER_WARNING";
    public final static String INFO = "TOASTER_INFO";

    public static void showConnectify(Activity activity, String message, String dialogType) {

        AlertDialog.Builder dialogBuilder;
        final AlertDialog alertDialog;
        dialogBuilder = new AlertDialog.Builder(activity);
        View layoutView;
        if (dialogType.equals(InnovativesDialog.SUCCESS)) {
            layoutView = activity.getLayoutInflater().inflate(R.layout.dialog_conectify_success, null);
        } else {
            layoutView = activity.getLayoutInflater().inflate(R.layout.dialog_conectify_error, null);
        }
        ImageView imgClose = layoutView.findViewById(R.id.image_close);
        TextView textMessage = layoutView.findViewById(R.id.text_message);
        textMessage.setText(message);
        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        alertDialog.show();
        alertDialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        imgClose.setOnClickListener(v -> alertDialog.dismiss());
    }
}
