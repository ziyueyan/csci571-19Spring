package com.example.yanziyue.productsearch;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class SimilarTabFragment extends Fragment {
    private static final String tag = "SimilarTab";

    private ArrayList<SimilarItem> similars;
    private ArrayList<SimilarItem> default_similar;

    private Spinner sort;
    private Spinner by;
    boolean isDe = false;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_similar_tab, container, false);

        sort = (Spinner) v.findViewById(R.id.sort_spinner);
        by = (Spinner) v.findViewById(R.id.by_spinner);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.similars);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Bundle args = getArguments();
        similars = (ArrayList<SimilarItem>) args.getSerializable("similarList");
        default_similar = new ArrayList<SimilarItem>(similars);
        TextView noResult = (TextView) v.findViewById(R.id.no_result);
        RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.similar_layout);
        if(similars.size() == 0) {
            noResult.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
        else{
            noResult.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
        }

        mAdapter = new SimilarAdapter(similars, getContext());
        mRecyclerView.setAdapter(mAdapter);

        by.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int j, long l) {
                int i = sort.getSelectedItemPosition();
                sortwhat(i, j);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int j = by.getSelectedItemPosition();
                sortwhat(i, j);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    public void sortname(){
        System.out.println("sort name...");
        Collections.sort(similars, new Comparator<SimilarItem>() {
            @Override
            public int compare(SimilarItem a, SimilarItem b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
    }

    public void sortprice(){
        System.out.println("sort price...");
        Collections.sort(similars, new Comparator<SimilarItem>() {
            @Override
            public int compare(SimilarItem a, SimilarItem b) {
                return Double.compare(Double.parseDouble(a.getPrice().substring(1)),Double.parseDouble(b.getPrice().substring(1)));
            }
        });
    }

    public void sortday(){
        System.out.println("sort day...");
        Collections.sort(similars, new Comparator<SimilarItem>() {
            @Override
            public int compare(SimilarItem a, SimilarItem b) {
                return Integer.compare(Integer.parseInt(a.getDaysleft()), Integer.parseInt(b.getDaysleft()));
            }
        });
    }

    public void sortwhat(int i, int j) {
        System.out.println(i + "..." + j);
        if(i == 0) {
            mAdapter = new SimilarAdapter(default_similar, getContext());
            by.setEnabled(false);
            by.setSelection(0);
        }
        else if(i == 1) {
            by.setEnabled(true);
            sortname();
        }
        else if(i == 2) {
            by.setEnabled(true);
            sortprice();
        }
        else {
            by.setEnabled(true);
            sortday();
        }

        if(j == 0) {
            if(i != 0) {
                mAdapter = new SimilarAdapter(similars, getContext());
            }
        }
        else if (j == 1) {
            System.out.println("sort descending...");
            if(i != 0){
                Collections.reverse(similars);
                mAdapter = new SimilarAdapter(similars, getContext());
            }
        }
        mRecyclerView.setAdapter(mAdapter);
    }


}
