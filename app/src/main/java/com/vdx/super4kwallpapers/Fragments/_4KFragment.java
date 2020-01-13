package com.vdx.super4kwallpapers.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.google.gson.Gson;
import com.vdx.super4kwallpapers.Adapters.Four_K_RecyclerViewAdapter;
import com.vdx.super4kwallpapers.Utils.IResult;
import com.vdx.super4kwallpapers.Models.ModelLinks;
import com.vdx.super4kwallpapers.Models.Sheet1;
import com.vdx.super4kwallpapers.R;
import com.vdx.super4kwallpapers.Utils.VolleyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class _4KFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private static String key = "1GHh1LuFZ4PaiMwl7bnuee6iQs4lhjVC8I7du3JTn8UA";
    private static String URL = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec?id=" + key + "&sheet=Sheet1";
    private VolleyService service;

    public _4KFragment() {
    }


    public static _4KFragment newInstance() {
        return new _4KFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__4_k, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView4K);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();


    }

    private void initAdapter() {
        try {
            String response = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("response");
            Gson gson = new Gson();
            ModelLinks modelLinks = gson.fromJson(response, ModelLinks.class);
            ArrayList<Sheet1> linkList = modelLinks.getSheet1();

            if (linkList != null) {

                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                mRecyclerView.setHasFixedSize(true);

                //Use this now
                mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                mRecyclerView.setAdapter(new Four_K_RecyclerViewAdapter(linkList, getActivity(), response));

            }

        } catch (Exception e) {
            Log.d("InitAdapter_Exception", String.valueOf(e));
        }
    }


}




