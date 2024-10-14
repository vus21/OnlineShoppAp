package com.example.onlineshopp.Object;

import java.io.Serializable;
import java.util.ArrayList;

public class ItemFood implements Serializable {
    private  int ID,costOld,costNew,review;
    float rating;
    private  String picURL,Title,desc;

    private ArrayList<String> listURL;

    public ArrayList<String> getListURL() {
        return listURL;
    }

    public void setListURL(ArrayList<String> listURL) {
        this.listURL = listURL;
    }

    public ItemFood() {}

    public ItemFood(int ID, int costOld, int costNew, int review,float rating, String title, String desc, ArrayList<String> listURL) {
        this.ID = ID;
        this.costOld = costOld;
        this.costNew = costNew;
        this.review = review;
        Title = title;
        this.desc = desc;
        this.rating=rating;
        this.listURL = listURL;
    }

    public int getCostOld() {
        return costOld;
    }

    public void setCostOld(int costOld) {
        this.costOld = costOld;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float costOld) {
        this.rating = costOld;
    }

    public int getCostNew() {
        return costNew;
    }

    public void setCostNew(int costNew) {
        this.costNew = costNew;
    }

    public int getReview() {
        return review;
    }

    public void setReview(int review) {
        this.review = review;
    }

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
