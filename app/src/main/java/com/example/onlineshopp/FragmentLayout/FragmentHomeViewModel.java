package com.example.onlineshopp.FragmentLayout;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshopp.Database.ConnectFirebase;
import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.Object.ItemCat;
import com.example.onlineshopp.Object.ItemFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class FragmentHomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private MutableLiveData<List<ItemCat>> mlistcat=new MutableLiveData<>();
    public LiveData< List<ItemCat> > getlistCat(){
        return  mlistcat;
    }
    public   void setListLiveData(List<ItemCat> data){

        mlistcat.setValue(data);
    }

    private static MutableLiveData<List<ItemFood>> mlistfood=new MutableLiveData<>();
    public LiveData< List<ItemFood> > getlistfood(){
        return  mlistfood;
    }
    public  List<ItemFood> mlisst=new ArrayList<>();

    public void setListLiveDataFood(List<ItemFood> data){
        mlistfood.setValue(data);
    }
}