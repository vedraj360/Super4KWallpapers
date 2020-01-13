package com.vdx.super4kwallpapers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vdx.super4kwallpapers.R;
import com.vdx.super4kwallpapers.Utils.Helper;

import java.util.Calendar;
import java.util.Objects;

public class About_Fragment extends Fragment {

    private static final String privacy_policy = "https://hidemyx.blogspot.com/2019/05/privacy-policy.html";
    private static final String Termsofservice = "https://hidemyx.blogspot.com/2019/05/terms-of-service.html";
    private String versionName;

    private TextView version;
    private Button contact_us, rate_us, privacy, t_c;
    private View view;

    public About_Fragment() {
        // Required empty public constructor
    }

    public static About_Fragment newInstance() {
        return new About_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_about_, null, false);

        defineIds();

        String boldText = "Super 4K Wallpapers";
        SpannableString str = new SpannableString(boldText);
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        try {
            versionName = Objects.requireNonNull(getActivity()).getPackageManager()
                    .getPackageInfo(getActivity().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String vt = "Version " + versionName;
        version.setText(vt);
        rate_click();
        mailIntent();
        setPrivacy_policy();
        setTermsofservice();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void rate_click() {

        rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper helper = new Helper();
                helper.RateUs(Objects.requireNonNull(getActivity()), "com.vdx.super4kwallpapers");
            }
        });
    }

    private void setPrivacy_policy() {
        String copyrights = String.format(getString(R.string.privacypolicy), Calendar.getInstance().get(Calendar.YEAR));
        privacy.setText(copyrights);

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(privacy_policy)));

            }
        });
    }

    private void setTermsofservice() {
        final String copyrights = String.format(getString(R.string.tc), Calendar.getInstance().get(Calendar.YEAR));
        t_c.setText(copyrights);
        t_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Termsofservice)));
            }
        });
    }

    private void mailIntent() {

        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri data = Uri.parse("mailto:"
                        + "innovativesapp@gmail.com");
                intent.setData(data);
                startActivity(intent);
            }
        });


    }

    private void defineIds() {
        version = view.findViewById(R.id.version_name);
        contact_us = view.findViewById(R.id.contact_us);
        rate_us = view.findViewById(R.id.rate_us);
        privacy = view.findViewById(R.id.privacy_policy);
        t_c = view.findViewById(R.id.terms_conditions);
    }

}
