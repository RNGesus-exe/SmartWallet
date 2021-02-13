package com.rngesus.smartwallet;

public class WishList {
    int img;
    String title;
    String available;
    String price;
    String totalProduct;

    public WishList(int img, String title, String available, String price, String totalProduct) {
        this.img = img;
        this.title = title;
        this.available = available;
        this.price = price;
        this.totalProduct = totalProduct;
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

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalProduct() {
        return totalProduct;
    }

    public void setTotalProduct(String totalProduct) {
        this.totalProduct = totalProduct;
    }
}
