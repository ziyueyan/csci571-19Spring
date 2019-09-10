package com.example.yanziyue.productsearch;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "wish_table")
public class Item {
    private String image;
    private String title;
    private String zipcode;
    private String shippingcost;
    private String handling;
    private String condition;
    private String price;

    @PrimaryKey
    @NonNull
    private String itemId;
    //private boolean atwish;

    public Item(String image, String title, String zipcode, String shippingcost, String handling, String condition, String price, String itemId){
        this.image = image;
        this.title = title;
        this.zipcode = zipcode;
        this.shippingcost = shippingcost;
        this.handling = handling;
        this.condition = condition;
        this.price = price;
        this.itemId = itemId;
        //this.setWish(false);
    }



    public String getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }
    public String getZipcode() {
        return zipcode;
    }
    public String getShippingcost() {
        return shippingcost;
    }
    public String getHandling() {return handling; }
    public String getCondition() {
        return condition;
    }
    public String getPrice() {
        return price;
    }
    public String getItemId() {
        return itemId;
    }
   // public Boolean isWish() {return atwish;}
   /// public void setWish(boolean wish) {this.atwish = wish;}

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setShippingcost(String shippingcost) {
        this.shippingcost = shippingcost;
    }

    public void setHandling(String handling) {
        this.handling = handling;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
