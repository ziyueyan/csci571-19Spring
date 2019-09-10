package com.example.yanziyue.productsearch;


import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {
    public SharedPreference() {
        super();
    }

    public void saveWishes(Context context, List<Item> wishes) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences("product_search", Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonWishes = gson.toJson(wishes);
        editor.putString("wish_items", jsonWishes);

        editor.commit();

    }

    public void addWish(Context context, Item Item) {
        List<Item> wishes = getWishes(context);
        if(wishes == null) {
            wishes = new ArrayList<Item>();
        }
        wishes.add(Item);
        saveWishes(context, wishes);
    }

    public void removeWish(Context context, Item item) {
        ArrayList<Item> wishes = getWishes(context);
        if(wishes != null) {
            for(int i = 0; i < wishes.size(); ++i) {
                if(wishes.get(i).getItemId().equals(item.getItemId())) {
                    wishes.remove(i);
                }
            }
            saveWishes(context, wishes);
        }
    }

    public ArrayList<Item> getWishes(Context context) {
        SharedPreferences settings;
        List<Item> wishes;

        settings = context.getSharedPreferences("product_search", Context.MODE_PRIVATE);

        if(settings.contains("wish_items")) {
            String jsonWishes = settings.getString("wish_items", null);
            Gson gson = new Gson();
            Item[] Items = gson.fromJson(jsonWishes, Item[].class);

            wishes = Arrays.asList(Items);
            wishes = new ArrayList<Item>(wishes);
        } else {
            return null;
        }
        return (ArrayList<Item>) wishes;
    }

}
