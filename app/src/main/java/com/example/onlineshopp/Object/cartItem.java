package com.example.onlineshopp.Object;

import java.io.Serializable;

public class cartItem   implements Serializable {
    private String ItemID;
    private ItemFood item;
    private int Quantity;

    public cartItem() {
    }

    public cartItem(String itemID, ItemFood item, int quantity) {
        ItemID = itemID;
        this.item = item;
        Quantity = quantity;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public ItemFood getItem() {
        return item;
    }

    public void setItem(ItemFood item) {
        this.item = item;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
}
