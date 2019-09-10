package com.example.yanziyue.productsearch;

import android.os.Parcelable;
import android.widget.Spinner;

import java.io.Serializable;

public class SimilarItem implements Serializable {
    private String image;
    private String url;
    private String title;
    private String shippingcost;
    private String daysleft;
    private String price;

    public SimilarItem(String image, String url, String title, String shippingcost, String price, String daysleft) {
        this.image = image;
        this.url = url;
        this.title = title;
        this.shippingcost = shippingcost;
        this.daysleft = daysleft;
        this.price = price;
    }

    public String getPicture() { return image; }
    public String getUrl() {return url;}
    public String getTitle() { return title; }
    public String getShippingcost() { return shippingcost; }
    public String getDaysleft() {
        StringBuilder sb = new StringBuilder();
        int i = 1;
        while (daysleft.charAt(i) != 'D') {
            sb.append(daysleft.charAt(i));
            i++;
        }
        String day = sb.toString();
        return day;
    }
    public String getPrice() { return price; }
}
