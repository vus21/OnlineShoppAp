package com.example.onlineshopp.Object;

import com.example.onlineshopp.Database.ConnectFirebase;

public class ItemCat {
    private  int ID;
    private  String picURL,Title;
    public ItemCat() {}
    public ItemCat(int ID, String picURL, String title) {
        this.ID = ID;
        this.picURL = picURL;
        Title = title;
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

    public static void LoadCartUser(String ID){
        ConnectFirebase.setDb();
    }
}
