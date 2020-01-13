package com.vdx.super4kwallpapers.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.google.gson.Gson;
import com.vdx.super4kwallpapers.Adapters.Trending_Adapter;
import com.vdx.super4kwallpapers.Models.ModelLinks;
import com.vdx.super4kwallpapers.Models.Sheet1;
import com.vdx.super4kwallpapers.R;
import com.vdx.super4kwallpapers.Utils.Links;
import com.vdx.super4kwallpapers.Utils.RefreshDrawable;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Objects;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class TrendingFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private Trending_Adapter trending_adapter;
    private PullRefreshLayout pullRefreshLayout;
    private AVLoadingIndicatorView indicatorView;

    public TrendingFragment() {
        // Required empty public constructor
    }

    public static TrendingFragment newInstance() {

        return new TrendingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        indicatorView = view.findViewById(R.id.indicator_trend);
        mRecyclerView = view.findViewById(R.id.trending_recycler);
        pullRefreshLayout = view.findViewById(R.id.refresh_layout);
        pullRefreshLayout.setColorSchemeColors(Color.WHITE);
        pullRefreshLayout.setRefreshDrawable(new RefreshDrawable(getActivity(), pullRefreshLayout));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();

        pullRefreshLayout.setOnRefreshListener(() -> {
            JsonRequests();
            mRecyclerView.setVisibility(View.INVISIBLE);
            indicatorView.setVisibility(View.VISIBLE);
            Log.d("REFRESH", "ref");

        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void initAdapter() {


        try {
            Gson gson = new Gson();
            ModelLinks modelLinks = gson.fromJson(Links.TRENDING_RESPONSE, ModelLinks.class);
            ArrayList<Sheet1> linkList = modelLinks.getSheet1();

            if (linkList != null) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                mRecyclerView.setHasFixedSize(true);
                mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                trending_adapter = new Trending_Adapter(linkList, getActivity(), Links.TRENDING_RESPONSE);
                mRecyclerView.setAdapter(trending_adapter);
            }

        } catch (Exception e) {
            Log.d("InitAdapter_Exception", String.valueOf(e));
        }

    }

    private void JsonRequests() {
        StringRequest stringRequests = new StringRequest(Links.TRENDING_URL, response -> {


            try {
                Gson gson = new Gson();
                ModelLinks modelLinks = gson.fromJson(response, ModelLinks.class);
                ArrayList<Sheet1> linkList = modelLinks.getSheet1();

                if (linkList != null) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    mRecyclerView.setHasFixedSize(true);

                    trending_adapter = new Trending_Adapter(linkList, getActivity(), Links.TRENDING_RESPONSE);
                    AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(trending_adapter);
                    ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter(alphaInAnimationAdapter);
                    scaleInAnimationAdapter.setDuration(1000);
                    mRecyclerView.setAdapter(scaleInAnimationAdapter);
                    mRecyclerView.invalidate();
                    trending_adapter.notifyDataSetChanged();
                    pullRefreshLayout.setRefreshing(false);
                    indicatorView.hide();


                }

            } catch (Exception e) {
                Log.d("InitAdapter_Exception", String.valueOf(e));
            }

        }, error -> pullRefreshLayout.setRefreshing(false));


        RequestQueue requestQueues = Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        requestQueues.add(stringRequests);

    }

}
