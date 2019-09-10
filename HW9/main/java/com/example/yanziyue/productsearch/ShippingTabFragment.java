package com.example.yanziyue.productsearch;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class ShippingTabFragment extends Fragment {
    private static final String tag = "ShippingTab";

    private TextView store;
    private TextView score;
    private TextView popularity;
    private TextView star;
    private TextView cost;
    private TextView global;
    private TextView handle;
    private TextView condition;
    private TextView policy;
    private TextView within;
    private TextView mode;
    private TextView by;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shipping_tab, container, false);

        store = (TextView) view.findViewById(R.id.ship_store);
        score = (TextView) view.findViewById(R.id.ship_score);
        popularity = (TextView) view.findViewById(R.id.ship_popularity);
        star = (TextView) view.findViewById(R.id.ship_star);
        cost = (TextView) view.findViewById(R.id.ship_cost);
        global = (TextView) view.findViewById(R.id.ship_global);
        handle = (TextView) view.findViewById(R.id.ship_handle);
        condition = (TextView) view.findViewById(R.id.ship_condition);
        policy = (TextView) view.findViewById(R.id.ship_policy);
        within = (TextView) view.findViewById(R.id.ship_within);
        mode = (TextView) view.findViewById(R.id.ship_mode);
        by = (TextView) view.findViewById(R.id.ship_by);

        Bundle args = getArguments();

        if(args.getString("store").equals("N/A") && args.getString("score").equals("N/A") && args.getString("popular").equals("N/A") && args.getString("star").equals("N/A")){
            TableLayout t1 = (TableLayout) view.findViewById(R.id.ship_table1);
            t1.setVisibility(View.GONE);
        }
        else{
            if(args.getString("store").equals("N/A")) {
                TableRow store_row = (TableRow) view.findViewById(R.id.name_row);
                store_row.setVisibility(View.GONE);
            }
            else {
                store.setText(Html.fromHtml("<a href=\"" + args.getString("storeurl") + "\">" + args.getString("store")+ "</a>"));
                store.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(args.getString("storeurl")));
                        startActivity(myIntent);

                    }
                });
            }
            if(args.getString("score").equals("N/A")) {
                TableRow score_row = (TableRow) view.findViewById(R.id.score_row);
                score_row.setVisibility(View.GONE);
            }
            else {
                score.setText(args.getString("score"));
            }
            if(args.getString("popular").equals("N/A")) {
                TableRow popular_row = (TableRow) view.findViewById(R.id.popular_row);
                popular_row.setVisibility(View.GONE);
            }
            else {
                popularity.setText(args.getString("popular"));
            }
            if(args.getString("star").equals("N/A")) {
                TableRow star_row = (TableRow) view.findViewById(R.id.star_row);
                star_row.setVisibility(View.GONE);
            }
            else {
                //star.setText(args.getString("star"));
            }

            //Drawable sellerstar = getResources().getDrawable(R.drawable.star_circle);
            //int id = Integer.parseInt("R.color." + args.getString("star"));
            //int color = getResources().getColor(R.color.);
            //sellerstar.setColorFilter(color,PorterDuff.Mode.MULTIPLY  );
        }

        if(args.getString("shippingcost").equals("N/A") && args.getString("global").equals("N/A") && args.getString("mode").equals("N/A") && args.getString("condition").equals("N/A")){
            TableLayout t2 = (TableLayout) view.findViewById(R.id.ship_table2);
            t2.setVisibility(View.GONE);
        }
        else{
            if(args.getString("shippingcost").equals("N/A")) {
                TableRow cost_row = (TableRow) view.findViewById(R.id.cost_row);
                cost_row.setVisibility(View.GONE);
            }
            else {
                cost.setText(args.getString("shippingcost"));
            }
            if(args.getString("global").equals("N/A")) {
                TableRow global_row = (TableRow) view.findViewById(R.id.global_row);
                global_row.setVisibility(View.GONE);
            }
            else {
                global.setText(args.getString("global"));
            }
            if(args.getString("handling").equals("N/A")) {
                TableRow handling_row = (TableRow) view.findViewById(R.id.handle_row);
                handling_row.setVisibility(View.GONE);
            }
            else {
                handle.setText(args.getString("handling"));
            }
            if(args.getString("condition").equals("N/A")) {
                TableRow condition_row = (TableRow) view.findViewById(R.id.cond_row);
                condition_row.setVisibility(View.GONE);
            }
            else {
                condition.setText(args.getString("condition"));
            }
        }


        if(args.getString("policy").equals("N/A") && args.getString("within").equals("N/A") && args.getString("mode").equals("N/A") && args.getString("shipby").equals("N/A")){
            TableLayout t2 = (TableLayout) view.findViewById(R.id.ship_table2);
            t2.setVisibility(View.GONE);
        }
        else{
            if(args.getString("policy").equals("N/A")) {
                TableRow policy_row = (TableRow) view.findViewById(R.id.policy_row);
                policy_row.setVisibility(View.GONE);
            }
            else {
                policy.setText(args.getString("policy"));

            }
            if(args.getString("within").equals("N/A")) {
                TableRow within_row = (TableRow) view.findViewById(R.id.within_row);
                within_row.setVisibility(View.GONE);
            }
            else {
                within.setText(args.getString("within"));

            }
            if(args.getString("mode").equals("N/A")) {
                TableRow mode_row = (TableRow) view.findViewById(R.id.mode_row);
                mode_row.setVisibility(View.GONE);
            }
            else {
                mode.setText(args.getString("mode"));
            }
            if(args.getString("shipby").equals("N/A")) {
                TableRow shipby_row = (TableRow) view.findViewById(R.id. by_row);
                shipby_row.setVisibility(View.GONE);
            }
            else {
                by.setText(args.getString("shipby"));
            }
        }

        return view;
    }

}
