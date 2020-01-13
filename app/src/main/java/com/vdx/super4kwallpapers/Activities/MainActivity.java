package com.vdx.super4kwallpapers.Activities;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.vdx.super4kwallpapers.Fragments.About_Fragment;
import com.vdx.super4kwallpapers.Fragments.Amoled_Fragment;
import com.vdx.super4kwallpapers.Fragments.RecyclerViewFragment;
import com.vdx.super4kwallpapers.Fragments.TrendingFragment;
import com.vdx.super4kwallpapers.Fragments._4KFragment;
import com.vdx.super4kwallpapers.R;

import java.util.Objects;

public class MainActivity extends DrawerActivity {

    private MaterialViewPager mViewPager;
    private int check = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewPager = findViewById(R.id.materialViewPager);


        final Toolbar toolbar = mViewPager.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);

            mViewPager.getViewPager().setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
                @NonNull
                @Override
                public Fragment getItem(int position) {
                    switch (position) {
                        case 0:
                            return Amoled_Fragment.newInstance();
                        case 1:
                            return _4KFragment.newInstance();
                        case 2:
                            return TrendingFragment.newInstance();
                        case 3:
                            return About_Fragment.newInstance();
                        default:
                            return RecyclerViewFragment.newInstance();
                    }
                }

                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    switch (position % 4) {
                        case 0:
                            return "Amoled";
                        case 1:
                            return "4K";
                        case 2:
                            return "Trending";
                        case 3:
                            return "About";
                    }
                    return "";
                }
            });

            mViewPager.setMaterialViewPagerListener(new MaterialViewPager.Listener() {
                @Override
                public HeaderDesign getHeaderDesign(int page) {
                    switch (page) {
                        case 0:
                            return HeaderDesign.fromColorResAndUrl(
                                    R.color.darkg,
                                    "https://i.imgur.com/dA2GVtI.jpg");
                        case 1:
                            return HeaderDesign.fromColorResAndUrl(
                                    R.color.darkg,
                                    "https://images.pexels.com/photos/36717/amazing-animal-beautiful-beautifull.jpg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940");
                        case 2:
                            check = 0;
                            actionBar.setDisplayShowTitleEnabled(true);
                            mViewPager.getPagerTitleStrip().setVisibility(View.VISIBLE);
                            return HeaderDesign.fromColorResAndUrl(
                                    R.color.darkg,
                                    "https://i.imgur.com/r1IuDfS.jpg");
                        case 3:
                            check = 1;
                            mViewPager.getPagerTitleStrip().setVisibility(View.INVISIBLE);
                            actionBar.setDisplayShowTitleEnabled(false);
                            return HeaderDesign.fromColorResAndUrl(
                                    R.color.darkg,
                                    "https://i.imgur.com/r62lrzE.jpg");
                    }

                    //execute others actions if needed (ex : modify your header logo)

                    return null;
                }
            });


            mViewPager.getViewPager().setOffscreenPageLimit(Objects.requireNonNull(mViewPager.getViewPager().getAdapter()).getCount());
            mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());

            final View logo = findViewById(R.id.logo_white);
            if (logo != null) {
                logo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.notifyHeaderChanged();
                        Toast.makeText(getApplicationContext(), "Yes, the title is clickable", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }


    }

    @Override
    protected void onResume() {
        if (check == 1) {
            {
                mViewPager.getPagerTitleStrip().setVisibility(View.INVISIBLE);
                actionBar.setDisplayShowTitleEnabled(false);

            }
        }
        super.onResume();

    }
}
