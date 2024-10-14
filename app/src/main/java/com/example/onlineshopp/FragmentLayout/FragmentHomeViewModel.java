package com.example.onlineshopp.FragmentLayout;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshopp.Object.ItemCat;
import com.example.onlineshopp.Object.ItemFood;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentHomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    static FirebaseDatabase firebaseDatabase;
    private MutableLiveData<List<ItemCat>> mlistcat=new MutableLiveData<>();
    public LiveData< List<ItemCat> > getlistCat(){
        return  mlistcat;
    }
    private static MutableLiveData<List<ItemFood>> mlistfood=new MutableLiveData<>();
    public LiveData< List<ItemFood> > getlistfood(){
        return  mlistfood;
    }
    public static List<ItemFood> mlisst=new ArrayList<>();
    public void loadlistCat(){
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("Category");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ItemCat> mlistt=new ArrayList<>();
                for (DataSnapshot child:snapshot.getChildren()){
                    int ID= child.child("id").getValue(int.class);
                    String imgURL = child.child("picUrl").getValue(String.class);
                    String title = child.child("title").getValue(String.class);
                    if(mlistt!=null){
                        mlistt.add(new ItemCat(ID,imgURL,title));
                    }
                }

                mlistcat.setValue(mlistt);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public static void loadlistFood(){
        firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference("Items");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ItemFood> mlistt=new ArrayList<>();
                for (DataSnapshot child:snapshot.getChildren()){
                   int ID= Integer.parseInt(child.getKey());
                    String desc = child.child("description").getValue(String.class);
                    String title = child.child("title").getValue(String.class);
                    int price,priceold,review;
                    float rating;
                    price=child.child("price").getValue(int.class);
                    priceold=child.child("oldPrice").getValue(int.class);
                    review=child.child("review").getValue(int.class);
                    rating=child.child("rating").getValue(float.class);

                    // get picUrls
                    ArrayList<String> picUrls = new ArrayList<>();
                    for (DataSnapshot snapshot1 : child.child("picUrl").getChildren()) {
                        String url = snapshot1.getValue(String.class);
                        picUrls.add(url);
                    }
                    if(mlistt!=null){
                        mlistt.add(new ItemFood(ID,priceold,price,review,rating,title,desc,picUrls));
                    }
                }

                mlistfood.setValue(mlistt);
                mlisst=mlistt;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}