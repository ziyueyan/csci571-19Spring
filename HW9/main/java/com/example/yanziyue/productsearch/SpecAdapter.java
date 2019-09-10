package com.example.yanziyue.productsearch;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SpecAdapter extends RecyclerView.Adapter<SpecAdapter.MyViewHolder> {

    private ArrayList<String> specs;
    private Context context;

    public SpecAdapter(ArrayList<String> specs, Context context){
        this.specs = specs;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ConstraintLayout constraintLayout;

        public MyViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.spec_text);
            constraintLayout = (ConstraintLayout) v.findViewById(R.id.spec_layout);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spec_item, parent, false);
        return new SpecAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(specs.get(position));
    }

    @Override
    public int getItemCount() {
        return specs.size();
    }
}
