package com.example.onlineshopp.Object;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemFood implements Serializable {
    private  String ID;
    private int idCate;
    private int Price,sell;
    private int Discount, status;
    float rating;
    private  String picURL,Title,desc;

    private ArrayList<String> listURL;

    public ArrayList<String> getListURL() {
        return listURL;
    }

    public void setListURL(ArrayList<String> listURL) {
        this.listURL = listURL;
    }
    public ItemFood(String id,String name,String desc,int Price,int idCate
            ,String url, int Status, int sell){
        this.ID=id;
        this.Title=name;
        this.desc=desc;
        this.Price=Price;
        this.idCate=idCate;
        this.picURL=url;
        this.status=Status;
        this.sell=sell;
    }
    public ItemFood() {}


    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
    public int getIdCate() {
        return idCate;
    }

    public void setIdCate(int idCate) {
        this.idCate = idCate;
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
}
