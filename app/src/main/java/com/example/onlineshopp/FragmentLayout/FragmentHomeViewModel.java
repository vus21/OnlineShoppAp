package com.example.onlineshopp.FragmentLayout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlineshopp.Database.ConnectFirebase;
import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.Object.ItemCat;
import com.example.onlineshopp.Object.ItemFood;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
    public  List<ItemFood> mlisst=new ArrayList<>();
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


    public  void loadlistFood(Context context) {
        List<ItemFood> mlistt = new ArrayList<>();
        ConnectSQLite connect = new ConnectSQLite(context);
        SQLiteDatabase db = connect.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ConnectSQLite.TABLE_1, null);
        Log.i("TAG", "\n\n\n\n"+String.valueOf(cursor.getCount()));
        if(cursor.getCount()>0){
            if (cursor.moveToFirst()) {
                do {
                    ArrayList<String> listimg = new ArrayList<>();
                    listimg.add(cursor.getString(6));
                    ItemFood item = new ItemFood(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getInt(2),
                            cursor.getInt(4),
                            cursor.getInt(3),
                            cursor.getInt(5),
                            listimg,
                            cursor.getString(7)
                    );
                    mlistt.add(item);
                    Log.v("TAG","awdawd\\n\n\n\n\n\n\n\n");
                } while (cursor.moveToNext());
            }
            cursor.close();
            mlistfood.setValue(mlistt);
            Log.v("SQLite","Tai thanh cong Data.................");
        }else{
            Log.v("SQLite","Tai that bai Data.................");
            loadFirebase();
        }

    }
    private void loadFirebase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference userRef = firebaseDatabase.getReference("Items");
        List<ItemFood> mlistt = new ArrayList<>();

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey();
                    String name = dataSnapshot.child("title").getValue(String.class);
                    int idCate = dataSnapshot.child("idCate").getValue(Integer.class);
                    String inventory = String.valueOf(dataSnapshot.child("Inventory").getValue(Long.class));
                    String price = String.valueOf(dataSnapshot.child("price").getValue(Long.class));
                    ArrayList<String> listUrl = new ArrayList<>();

                    for (DataSnapshot imageSnapshot : dataSnapshot.child("picUrl").getChildren()) {
                        String imageUrl = imageSnapshot.getValue(String.class);
                        if (imageUrl != null) {
                            listUrl.add(imageUrl);
                        }else{
                            Log.e("Fragment_Home Error","Errors");
                        }
                    }

                    String description = dataSnapshot.child("description").getValue(String.class);
                    ItemFood itemFood = new ItemFood(Integer.parseInt(id),
                            name,
                            idCate,
                            Integer.parseInt(price),
                            Integer.parseInt(inventory),
                            25,
                            listUrl,
                            description);
                    mlistt.add(itemFood);
                }
                mlistfood.setValue(mlistt);
                Log.i("Firebase Realtime Database", "Data loaded successfully, number of items: " + mlistt.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Failed to read value.", error.toException());
            }
        });
    }

}