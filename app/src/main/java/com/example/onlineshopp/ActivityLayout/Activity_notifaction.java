package com.example.onlineshopp.ActivityLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshopp.Database.ConnectFirebase;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.databinding.ActivityPaymentBinding;
import com.example.onlineshopp.databinding.LayoutnotificationBinding;
import com.example.onlineshopp.temptlA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_notifaction extends AppCompatActivity {
    LayoutnotificationBinding binding;
    private final String TAG="Activity_payment";
    private ArrayList<String> listmethods=new ArrayList<>();
    private ArrayList<Integer> listfreeship=new ArrayList<>();
    private ArrayList<cartItem> receivedList = new ArrayList<>();
    private  String name,address,phone;
    private  int selectionPosition =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutnotificationBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if (intent != null) {
            for (int i = 0; i < intent.getIntExtra("size", 0); i++) {
                receivedList.add((cartItem) intent.getSerializableExtra("Items" + String.valueOf(i)));
            }
            Log.d(TAG, "Kich thuoc cua list nay:"+String.valueOf(intent.getIntExtra("size", 0)));
            name=intent.getStringExtra("name");
            phone=intent.getStringExtra("phone");
            address=intent.getStringExtra("address");
            selectionPosition=intent.getIntExtra("payment",0);
        }else{
            Log.w(TAG,"nuull");
        }
        createOder(temptlA.user.getID(),name,address,phone,selectionPosition);
        updateProducts();
        removeItemincart();
    }


    private void createOder(String iduser,String name,String address,String phone,int methodpayment){
        ConnectFirebase.setDb();
        String idoder;
        Map<String,Object> newData=new HashMap<>();
        newData.put("ID_Customer",iduser);
        newData.put("Address",address);
        newData.put("Nameuser",name);
        newData.put("Payment",methodpayment);
        newData.put("Phone",phone);
        newData.put("Timer", temptlA.Datetimecurrent);
        newData.put("Amount: ", temptlA.getAmountItem(receivedList));
        newData.put("Total", temptlA.getTotalBillcartitem(receivedList));
       DocumentReference document= ConnectFirebase.db.collection("order").document();
             idoder=document.getId();
            document.set(newData).addOnSuccessListener(aVoid->{
                Log.i(TAG,"Tạo thành công order: "+document.getId()+"\nTimer: "+temptlA.Datetimecurrent);
            });
        Log.v(TAG,"ID: "+idoder);
       CollectionReference reference= ConnectFirebase.db.collection("orderdetails").document().collection(idoder);
            for(cartItem item: receivedList){
                Map<String,Object> newData1=new HashMap<>();
                newData1.put("ID",item.getItemID());
                newData1.put("Cost",item.getItem().getPrice());
                newData1.put("Quantity",item.getQuantity());
                newData1.put("Discount",item.getItem().getDiscount());
                float discount=((float)item.getItem().getDiscount())/100;
                float Pricediscount= (float)item.getItem().getPrice()*discount;
                float total=Pricediscount*(float)item.getQuantity() ;
                newData1.put("Total",total);
                reference.document(item.getItemID()).set(newData1).addOnSuccessListener(aVoid->{
                    Log.i(TAG,"Day len thanh cong");
                }).addOnFailureListener(e -> {
                    Log.w(TAG,e.getMessage());
                });
            }

    }
    private void removeItemincart(){
        ConnectFirebase.setDb();
        for(cartItem item:receivedList) {
            ConnectFirebase.db.collection("cart_customer")
                    .document(temptlA.IDCART).collection(temptlA.IDuser).document(item.getItemID())
                    .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        Log.w(TAG,"Error deleting document",e);
                        }
                    });
        }
        //Remove in cart

    }
    private void updateProducts(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference Ref=database.getReference("Items");

        Ref.get().addOnSuccessListener(dataSnapshot -> {
            for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                Log.v(TAG,"Inventory :"+String.valueOf(snapshot.child("Inventory").getValue(Long.class)));
                for(cartItem item:receivedList){
                    if(snapshot.getKey().equals(item.getItemID())){
                        DatabaseReference inventoryRef= snapshot.child("Inventory").getRef();
                        inventoryRef.setValue(snapshot.child("Inventory").getValue(Long.class)-(long)item.getQuantity());
                        Log.v(TAG,"ID:"+snapshot.getKey()+" \nInventory sau cap nhat :"+
                            String.valueOf(snapshot.child("Inventory").
                                    getValue(Long.class)));
                    }
                }

            }
        });

    }
}
