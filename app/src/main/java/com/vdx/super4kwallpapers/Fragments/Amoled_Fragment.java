package com.vdx.super4kwallpapers.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.google.gson.Gson;
import com.vdx.super4kwallpapers.Adapters.Amoled_Adapter;
import com.vdx.super4kwallpapers.Models.ModelLinks;
import com.vdx.super4kwallpapers.Models.Sheet1;
import com.vdx.super4kwallpapers.R;
import com.vdx.super4kwallpapers.Utils.IResult;
import com.vdx.super4kwallpapers.Utils.Links;
import com.vdx.super4kwallpapers.Utils.VolleyService;

import java.util.ArrayList;

import java.util.Objects;

public class Amoled_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private IResult result = null;


    public Amoled_Fragment() {
    }

    public static Amoled_Fragment newInstance() {
        return new Amoled_Fragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amoled_, container, false);
        mRecyclerView = view.findViewById(R.id.amoled_fragment);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
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
            String response = Objects.requireNonNull(Objects.requireNonNull(getActivity()).getIntent().getExtras()).getString("response");

            Gson gson = new Gson();
            ModelLinks modelLinks = gson.fromJson(response, ModelLinks.class);
            ArrayList<Sheet1> linkList = modelLinks.getSheet1();

            if (linkList != null) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                mRecyclerView.setHasFixedSize(true);

                mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
                mRecyclerView.setAdapter(new Amoled_Adapter(linkList, getActivity(), response));


            }

        } catch (Exception e) {
            Log.d("InitAdapter_Exception", String.valueOf(e));
        }

    }

    private void initReq() {
        result = new IResult() {
            @Override
            public void notifySuccess(String requestType, String response) {
                Links.Res = response;
                Log.d("Ressss", response);

            }

            @Override
            public void notifyError(String requestType, VolleyError error) {

            }
        };
    }

    @Override
    public void onResume() {
        Log.d("OnResum", "Resumne");
        Log.d("WALLSC", String.valueOf(Links.BACKPRESS));
        if (Links.BACKPRESS == 1) {

            VolleyService service = new VolleyService(result, getActivity());
            service.getData("GET", Links.URL);
            initReq();
            Links.BACKPRESS = 0;
        }
        super.onResume();
    }
}
