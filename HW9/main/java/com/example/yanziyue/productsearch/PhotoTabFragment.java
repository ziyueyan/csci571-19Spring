package com.example.yanziyue.productsearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class PhotoTabFragment extends Fragment {
    private static final String tag = "PhotoTab";

    private ArrayList<String> photos;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_tab, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.photos);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Bundle args = getArguments();
        photos = args.getStringArrayList("photos");
        TextView noResult = (TextView) view.findViewById(R.id.no_result);
        if(photos.size() == 0) {
            noResult.setVisibility(View.VISIBLE);
        }
        else{
            noResult.setVisibility(View.GONE);
        }


        mAdapter = new PhotoAdapter(photos, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

}
