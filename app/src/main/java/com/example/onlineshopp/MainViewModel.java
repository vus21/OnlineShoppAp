package com.example.onlineshopp;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.example.onlineshopp.FragmentLayout.Fragment_Home;
import com.example.onlineshopp.Object.ItemCat;
import com.example.onlineshopp.databinding.ActivityCartBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AppCompatActivity {
    ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding=ActivityCartBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        FirebaseDatabase db=FirebaseDatabase.getInstance();

                List<ItemCat> mlistt=new ArrayList<>();
            DatabaseReference myRef = db.getReference("Items");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        String name = snapshot1.child("title").getValue(String.class);  // Lấy giá trị của "name"
                        Log.d("TAG",name+"\nKey: "+snapshot1.getKey());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            for(ItemCat c:mlistt){
                Log.d("TAG",c.getPicURL());
            }
        String imgURL="https://firebasestorage.googleapis.com/v0/b/project204-1.appspot.com/o/cupCake.png?alt=media&token=cfa60b5e-a909-4557-a3cd-ee4d617722fa";
        Glide.with(this).load(imgURL).into(binding.test123);




    }

}
