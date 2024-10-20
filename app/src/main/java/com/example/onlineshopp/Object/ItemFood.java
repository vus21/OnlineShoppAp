package com.example.onlineshopp.Object;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemFood implements Serializable {
    private  int ID;
    private  int costOld,costNew,review;
    private int idCate;

    public int getIdCate() {
        return idCate;
    }

    public void setIdCate(int idCate) {
        this.idCate = idCate;
    }

    public int getInventory() {
        return Inventory;
    }

    public void setInventory(int inventory) {
        Inventory = inventory;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    private int Inventory;
    private int Price;
    private int Discount;
    float rating;
    private  String picURL,Title,desc;

    private ArrayList<String> listURL;

    public ArrayList<String> getListURL() {
        return listURL;
    }

    public void setListURL(ArrayList<String> listURL) {
        this.listURL = listURL;
    }
    public ItemFood(int ID,String title,int idCate,int Price,int Iventory,int Discount,ArrayList<String> listURL,String desc){
        this.ID = ID;
        Title = title;
        this.desc = desc;
        this.listURL = listURL;
         this.Price=Price;
         this.idCate=idCate;
         this.Inventory=Iventory;
         this.Discount=Discount;

    }
    public ItemFood() {}





    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
