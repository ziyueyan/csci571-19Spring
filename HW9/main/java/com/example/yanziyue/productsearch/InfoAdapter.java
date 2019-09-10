package com.example.yanziyue.productsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.time.Instant;
import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.MyViewHolder>{
    private String[] specs;

    private ArrayList<String> infophotos;
    private Context context;


    public InfoAdapter( ArrayList<String> infophotos, Context context) {
        this.infophotos = infophotos;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ConstraintLayout constraintLayout;

        public MyViewHolder(View v) {
            super(v);

            imageView = (ImageView) v.findViewById(R.id.info_image);
            constraintLayout = (ConstraintLayout) v.findViewById(R.id.info_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.info_item, parent, false);
        return new InfoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        System.out.println(infophotos.get(position));
        //Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(infophotos.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return infophotos.size();
    }

}
