package com.vdx.super4kwallpapers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.vdx.super4kwallpapers.Utils.CustomDialog.InnovativesDialog;
import com.vdx.super4kwallpapers.Utils.IResult;
import com.vdx.super4kwallpapers.R;
import com.vdx.super4kwallpapers.Utils.Links;
import com.vdx.super4kwallpapers.Utils.VolleyService;
import com.wang.avi.AVLoadingIndicatorView;

public class SplashActivity extends AppCompatActivity {

    private IResult result, getResult;
    private Intent mainActivityIntent;
    private AVLoadingIndicatorView avi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        avi = findViewById(R.id.indicator);
        if (connectionChecker()) {
            initViews();
        } else {
//            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();
            avi.hide();
            InnovativesDialog.showConnectify(SplashActivity.this, "Connection could not be established", InnovativesDialog.ERROR);
        }

    }

    private void initViews() {

        initRequest();
        getTlist();
        VolleyService service = new VolleyService(result, getApplicationContext());
        VolleyService volleyService = new VolleyService(getResult, getApplicationContext());

        service.getData("GET", Links.URL);
        volleyService.getData("GET", Links.TRENDING_URL);


        mainActivityIntent = new Intent(SplashActivity.this, MainActivity.class);

    }

    private void getTlist() {
        getResult = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                Log.d("Trending_Get", requestType);
                Log.d("RT", response);
                Links.TRENDING_RESPONSE = response;
            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d("Trending_Get", requestType);
                Log.d("RT", String.valueOf(error));

            }
        };

    }

    private void initRequest() {

        result = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                mainActivityIntent.putExtra("response", response);
                if (response != null) {
                    startActivity(mainActivityIntent);
                    finish();
                }

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {
                Log.d("Err", String.valueOf(error));
            }
        };
    }

    private Boolean connectionChecker() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT < 23) {
                final NetworkInfo ni = connectivityManager.getActiveNetworkInfo();

                if (ni != null) {
                    return (ni.isConnected() && (ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE));
                }
            } else {
                final Network n = connectivityManager.getActiveNetwork();

                if (n != null) {
                    final NetworkCapabilities nc = connectivityManager.getNetworkCapabilities(n);

                    assert nc != null;
                    return (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
                }
            }
        }

        return false;

    }
}
