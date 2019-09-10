package com.example.yanziyue.productsearch;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WishListFragment extends Fragment {
    private static final String tag = "WishList";

    //private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager layoutManager;

    List<Item> wishlist;
    TextView noWish;
    //private SharedPreference sharedPreference;
    private WishViewModel wishViewModel;

    private TextView total;
    private TextView money;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wish_list, container, false);
        total = (TextView) v.findViewById(R.id.total);
        money = (TextView) v.findViewById(R.id.money);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.wish_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        noWish = (TextView) v.findViewById(R.id.nowish);

        final WishListAdapter mAdapter = new WishListAdapter(getContext());
        recyclerView.setAdapter(mAdapter);

        //sharedPreference = new SharedPreference();
        //wishlist = sharedPreference.getWishes(getActivity());
        wishViewModel = ViewModelProviders.of(this).get(WishViewModel.class);
        wishViewModel.getAllWishes().observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> wishlist) {
                //update Recycler View
                mAdapter.setItems(wishlist);
                if(wishlist != null) {
                    noWish.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    double d = 0.0;
                    for (Item item : wishlist){
                        d += Double.parseDouble(item.getPrice().substring(1));
                    }
                    total.setText("Wishlist total("+ wishlist.size()+" items)");
                    money.setText("$" + String.format("%.2f", d));
                }
                else {
                    noWish.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    wishlist = new ArrayList<Item>();
                    total.setText("Wishlist total(0 item)");
                    money.setText("$0.00");
                }
            }
        });




        return v;
    }


    public void reset(){

    }

    /*@Override
    public void onEvent() {
        wishlist = sharedPreference.getWishes(getActivity());
        if(wishlist != null) {
            noWish.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            double d = 0.0;
            for (Item item : wishlist){
                d += Double.parseDouble(item.getPrice().substring(1));
            }
            total.setText("Wishlist total("+ wishlist.size()+" items)");
            money.setText("$" + String.format("%.2f", d));

        }
        else {
            wishlist = new ArrayList<Item>();
            noWish.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            total.setText("Wishlist total(0 items)");
            money.setText("$0.00");
        }

        mAdapter = new WishListAdapter(getActivity().getApplicationContext(), wishlist);
        recyclerView.setAdapter(mAdapter);
    }*/
}
