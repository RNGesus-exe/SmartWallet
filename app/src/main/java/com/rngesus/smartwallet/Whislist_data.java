package com.rngesus.smartwallet;

import android.widget.ImageView;

public class Whislist_data {
    int img;
    String title;
    String Avalable;
    String price;
    String totalproduct;

    public Whislist_data(int img, String title, String avalable, String price, String totalproduct) {
        this.img = img;
        this.title = title;
        Avalable = avalable;
        this.price = price;
        this.totalproduct = totalproduct;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAvalable() {
        return Avalable;
    }

    public void setAvalable(String avalable) {
        Avalable = avalable;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalproduct() {
        return totalproduct;
    }

    public void setTotalproduct(String totalproduct) {
        this.totalproduct = totalproduct;
    }
}
