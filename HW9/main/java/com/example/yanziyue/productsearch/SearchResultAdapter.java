package com.example.yanziyue.productsearch;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.EventListener;
import java.util.List;

class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.MyViewHolder> {
    private List<Item> Items;
    private Context context;

    private SharedPreference sharedPreference;

    private WishViewModel wishViewModel;

    /*EventListener listener;

    public interface EventListener{
        void onEvent();
    }*/
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        ImageView icon;
        TextView zip;
        TextView ship;
        TextView condition;
        TextView cost;
        ConstraintLayout layout;
        public MyViewHolder(View v) {
            super(v);
            image = (ImageView) v.findViewById(R.id.item_image);
            title = (TextView) v.findViewById(R.id.item_title);
            zip = (TextView) v.findViewById(R.id.item_zip);
            ship = (TextView) v.findViewById(R.id.item_ship);
            condition = (TextView)v.findViewById(R.id.item_condition);
            cost = (TextView) v.findViewById(R.id.item_cost);
            icon = (ImageView) v.findViewById(R.id.add_wish_btn);
            layout = (ConstraintLayout) v.findViewById(R.id.item_layout);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)

    public SearchResultAdapter(Context context, List<Item> Items){
        this.context = context;
        this.Items = Items;
        //this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SearchResultAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_results_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        wishViewModel =ViewModelProviders.of((FragmentActivity) parent.getContext()).get(WishViewModel.class);
        sharedPreference = new SharedPreference();
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context).load(Items.get(position).getImage()).resize(180,180).into(holder.image);
        String cuttitle = "";
        if(Items.get(position).getTitle().length() > 50) {
            cuttitle = Items.get(position).getTitle().substring(0,50) +"...";
            holder.title.setText(cuttitle);
        }
        else {
            cuttitle = Items.get(position).getTitle();
            holder.title.setText(Items.get(position).getTitle());
        }
        //holder.title.setText("items");
        holder.zip.setText("Zip:" + Items.get(position).getZipcode());
        holder.ship.setText(Items.get(position).getShippingcost());
        holder.condition.setText(Items.get(position).getCondition());
        holder.cost.setText(Items.get(position).getPrice());
        if(isWish(Items.get(position))) {
            holder.icon.setImageResource(R.drawable.cart_remove);
        }
        else {
            holder.icon.setImageResource(R.drawable.cart_plus);
        }
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "";
                String cuttitle = "";
                if(Items.get(position).getTitle().length() > 50) {
                    cuttitle = Items.get(position).getTitle().substring(0,50) +"...";
                    holder.title.setText(cuttitle);
                }
                else {
                    cuttitle = Items.get(position).getTitle();
                    holder.title.setText(Items.get(position).getTitle());
                }
                if(!isWish(Items.get(position))) {
                    holder.icon.setImageResource(R.drawable.cart_remove);
                    wishViewModel.insert(Items.get(position));
                    sharedPreference.addWish(context, Items.get(position));
                    message = cuttitle + " was added to wish list";
                }
                else {
                    holder.icon.setImageResource(R.drawable.cart_plus);
                    wishViewModel.delete(Items.get(position));
                    sharedPreference.removeWish(context, Items.get(position));
                    message = cuttitle + " was removed from wish list";
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                //listener.onEvent();

            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent search_detail = new Intent(view.getContext(), DetailResults.class);
                search_detail.putExtra("title", Items.get(position).getTitle());
                search_detail.putExtra("itemId", Items.get(position).getItemId());
                search_detail.putExtra("shippingcost", Items.get(position).getShippingcost());
                search_detail.putExtra("handling", Items.get(position).getHandling());
                view.getContext().startActivity(search_detail);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return Items.size();
    }

    public boolean isWish(Item item) {
        boolean iswish = false;
        List<Item> wishes = sharedPreference.getWishes(context);
        if(wishes != null) {
            for(Item mItem : wishes) {
                if(mItem.getItemId().equals(item.getItemId())) {
                    iswish = true;
                    break;
                }
            }
        }
        return iswish;
    }



}
