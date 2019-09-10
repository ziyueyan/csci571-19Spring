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

import java.util.ArrayList;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.MyViewHolder> {
    private ArrayList<String> photos;
    private Context context;

    public PhotoAdapter(ArrayList<String> photos, Context context){
        this.photos = photos;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ConstraintLayout constraintLayout;

        public MyViewHolder(View v) {
            super(v);

            imageView = (ImageView) v.findViewById(R.id.photo_image);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        return new PhotoAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.with(context).load(photos.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

}
