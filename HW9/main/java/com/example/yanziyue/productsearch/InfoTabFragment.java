package com.example.yanziyue.productsearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class InfoTabFragment extends Fragment {
    private static final String tag = "InfoTab";

    private TextView title;
    private TextView price;
    private TextView ship;
    private TextView subtitle;
    private TextView price2;
    private TextView brand;
    private ArrayList<String> specs;

    private TableRow sub_row;
    private TableRow brand_row;
    private TextView spec_title;
    SpecAdapter sa;

    private ArrayList<String> infophotos;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView sRecyclerView;
    private RecyclerView.Adapter sAdapter;
    private RecyclerView.LayoutManager sLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_tab, container, false);
        Bundle args = getArguments();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.info_photos);

        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        infophotos = args.getStringArrayList("picture");

        mAdapter = new InfoAdapter(infophotos, getContext());
        mRecyclerView.setAdapter(mAdapter);

        title = (TextView) view.findViewById(R.id.info_title);
        price = (TextView) view.findViewById(R.id.info_price);
        ship = (TextView) view.findViewById(R.id.info_ship);
        subtitle = (TextView) view.findViewById(R.id.info_subtitle);
        price2 = (TextView) view.findViewById(R.id.info_Price);
        brand = (TextView) view.findViewById(R.id.info_Brand);

        title.setText(args.getString("title"));
        price.setText(args.getString("price"));
        ship.setText("With " + args.getString("shippingcost"));
        if (args.getString("subtitle").equals("N/A")) {
            sub_row = (TableRow) view.findViewById(R.id.sub_row);
            sub_row.setVisibility(View.GONE);
        } else {
            subtitle.setText(args.getString("subtitle"));
        }

        price2.setText(args.getString("price"));
        if (args.getString("subtitle").equals("N/A")) {
            brand_row = (TableRow) view.findViewById(R.id.brand_row);
            brand_row.setVisibility(View.GONE);
        } else {
            brand.setText(args.getString("brand"));
        }


        specs = args.getStringArrayList("spec");
        if (specs.size() == 0) {
            spec_title = (TextView) view.findViewById(R.id.info_Specs);
            spec_title.setVisibility(View.GONE);
        } else {
            sRecyclerView = (RecyclerView) view.findViewById(R.id.specs);

            sLayoutManager = new LinearLayoutManager(getActivity());
            sRecyclerView.setLayoutManager(sLayoutManager);

            sAdapter = new SpecAdapter(specs, getContext());
            sRecyclerView.setAdapter(sAdapter);
        }

        return view;
    }
}
