package com.example.yanziyue.productsearch;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.MyViewHolder> {
    private ArrayList<SimilarItem> similars;
    private Context context;

    public SimilarAdapter(ArrayList<SimilarItem> similars, Context context) {
        this.similars = similars;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView ship;
        public TextView days;
        public TextView price;
        public ConstraintLayout constraintLayout;

        public Spinner sort;
        public Spinner by;

        public MyViewHolder(View v) {
            super(v);

            imageView = (ImageView) v.findViewById(R.id.similar_image);
            title = (TextView) v.findViewById(R.id.similar_title);
            ship = (TextView) v.findViewById(R.id.similar_ship);
            days = (TextView) v.findViewById(R.id.similar_days);
            price = (TextView) v.findViewById(R.id.similar_price);
            constraintLayout = (ConstraintLayout) v.findViewById(R.id.similar_layout);
            sort = (Spinner) v.findViewById(R.id.sort_spinner);
            by = (Spinner) v.findViewById(R.id.by_spinner);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.similar_item, parent, false);
        return new SimilarAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.with(context).load(similars.get(position).getPicture()).resize(150, 150).into(holder.imageView);
        holder.title.setText(similars.get(position).getTitle());
        holder.ship.setText(similars.get(position).getShippingcost());
        String day = similars.get(position).getDaysleft();
        if (day.equals("1") || day.equals("0")) {
            day = day + " Day Left";
        } else {
            day = day + " Days Left";
        }
        holder.days.setText(day);
        holder.price.setText(similars.get(position).getPrice());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(similars.get(position).getUrl()));
                v.getContext().startActivity(myIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return similars.size();
    }

}
