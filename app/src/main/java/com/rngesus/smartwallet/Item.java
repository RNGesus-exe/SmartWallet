package com.rngesus.smartwallet;

public class Item {
    private String ItemName;
    private String ItemStatus;
    private String OriginalPrice;
    private String DiscountPrice;

    public Item(String itemName, String itemStatus, String originalPrice, String discountPrice) {
        ItemName = itemName;
        ItemStatus = itemStatus;
        OriginalPrice = originalPrice;
        DiscountPrice = discountPrice;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemStatus() {
        return ItemStatus;
    }

    public void setItemStatus(String itemStatus) {
        ItemStatus = itemStatus;
    }

    public String getOriginalPrice() {
        return OriginalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        OriginalPrice = originalPrice;
    }

    public String getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        DiscountPrice = discountPrice;
    }
}
