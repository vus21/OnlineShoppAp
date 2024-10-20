package com.example.onlineshopp.Database;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ConnectFirebase {
    public static FirebaseFirestore db;

    public  static FirebaseAuth mAuth;
    private static String TAG="ConnectFirebase";

    public  static void setDb(){
        db=FirebaseFirestore.getInstance();
    }

    public  static void setmAuth(){
        mAuth=FirebaseAuth.getInstance();
    }
    public static void getDataCart(String id_cart,String id_customer,Map<String,Object> newData,int selection){
        setDb();
        FirebaseFirestore firestore=db;
        CollectionReference khCollectionRef = db.collection("cart_customer")
                .document(id_cart)
                .collection(id_customer);

        switch (selection){
            //insert and update
            case 1:
                    addtoFirebase(khCollectionRef,newData);
                break;
                //deleted
            case 2:
                break;
            case 3:
                break;
            default:
                Log.v(TAG,"Không hợp lệ");
                break;
        }
//        khCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    if(!task.getResult().isEmpty()){
//                        for(int i=0;i<task.getResult().getDocuments().size();i++){
//                            DocumentSnapshot document = task.getResult().getDocuments().get(i);
//                            String idProduct = document.getId();
//                            Long quantity = document.getLong("Quantity");
//                            if(idProduct!=null && idProduct.equals("15")){
//                                //Update lại quantity
//                                Map<String, Object> newData = new HashMap<>();
//                                newData.put("Quantity", quantity);
//                                Log.d(TAG, "onComplete: "+document.get("Quantity"));
//                                khCollectionRef.document(idProduct).update(newData).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Log.d(TAG, "Document updated successfully!");
//                                        } else {
//                                            Log.d(TAG, "Error updating document: ", task.getException());
//                                        }
//                                    }
//                                });
//                            }else{
//
//                                //    dữ liệu dưới dạng map (field,values)
//                                Map<String, Object> newData = new HashMap<>();
//                                newData.put("ID_produc", "16");
//                                newData.put("Product_name", "Blue Cosmetic Bag");
//                                newData.put("Quantity", 1);
//                                khCollectionRef.document("16").set(newData).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if(task.isSuccessful()){
//                                            Log.d(TAG, "Thêm vào giỏ hàng thành công");
//                                        }else{
//                                            Log.e(TAG, "Thêm vào giỏ hàng không  thành công");
//                                        }
//                                    }
//                                });
//                            }
//                            //Đoạn này có thể bỏ
//                            Log.v("Item","ID: "+task.getResult().getDocuments().get(i).getId()+"\n"
//                                    +task.getResult().getDocuments().get(i).getString("ID_product")
//                                    +"\n"+task.getResult().getDocuments().get(i).getString("Product_name")
//                                    +"\n"+String.valueOf(  task.getResult().getDocuments().get(i).getLong("Quantity")));
//                        }
//                    }
//                    else{
//                        Log.v(TAG,"Ban ghi rong~");
//                    }
//                } else {
//                    Log.d("TAG", "Error getting documents: ", task.getException());
//                }
//            }
//        });

    }
        private void temp(){
            FirebaseFirestore db=FirebaseFirestore.getInstance();
            CollectionReference khCollectionRef = db.collection("cart_customer")
                    .document("3oTT6w0UvwttIcBPfupC")
                    .collection("KH0000");
            khCollectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        if(!task.getResult().isEmpty()){
                            for(int i=0;i<task.getResult().getDocuments().size();i++){
                                DocumentSnapshot document = task.getResult().getDocuments().get(i);
                                String idProduct = document.getId();
                                Long quantity = document.getLong("Quantity");
                                if(idProduct!=null && idProduct.equals("15")){
                                    //Update lại quantity
                                    Map<String, Object> newData = new HashMap<>();
                                    newData.put("Quantity", quantity);
                                    Log.d(TAG, "onComplete: "+document.get("Quantity"));
                                    khCollectionRef.document(idProduct).update(newData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Document updated successfully!");
                                            } else {
                                                Log.d(TAG, "Error updating document: ", task.getException());
                                            }
                                        }
                                    });
                                }else{

                                    //    dữ liệu dưới dạng map (field,values)
                                    Map<String, Object> newData = new HashMap<>();
                                    newData.put("ID_produc", "16");
                                    newData.put("Product_name", "Blue Cosmetic Bag");
                                    newData.put("Quantity", 1);
                                    khCollectionRef.document("16").set(newData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Log.d(TAG, "Thêm vào giỏ hàng thành công");
                                            }else{
                                                Log.e(TAG, "Thêm vào giỏ hàng không  thành công");
                                            }
                                        }
                                    });
                                }
                                //Đoạn này có thể bỏ
                                Log.v("Item","ID: "+task.getResult().getDocuments().get(i).getId()+"\n"
                                        +task.getResult().getDocuments().get(i).getString("ID_product")
                                        +"\n"+task.getResult().getDocuments().get(i).getString("Product_name")
                                        +"\n"+String.valueOf(  task.getResult().getDocuments().get(i).getLong("Quantity")));
                            }
                        }
                        else{
                            Log.v(TAG,"Ban ghi rong~");
                        }
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    private static  void addtoFirebase(CollectionReference collectionReference,Map<String,Object> newData){
            collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(!task.getResult().isEmpty()){
                        for(int i=0;i<task.getResult().getDocuments().size();i++){
                            DocumentSnapshot document = task.getResult().getDocuments().get(i);
                            String idProduct = document.getId();
                            Long quantity = document.getLong("Quantity");
                            if(idProduct!=null && idProduct.equals(newData.get("ID_product"))){
                                //Update lại quantity
                                Map<String, Object> newData1 = new HashMap<>();
                                newData1.put("Quantity", newData1.get("Quantity"));
                                Log.d(TAG, "onComplete: "+document.get("Quantity"));
                                collectionReference.document(idProduct).update(newData1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Document updated successfully!");
                                        } else {
                                            Log.d(TAG, "Error updating document: ", task.getException());
                                        }
                                    }
                                });
                            }else{

                                collectionReference.document((String)newData.get("ID_product")).set(newData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Log.d(TAG, "Thêm vào giỏ hàng thành công");
                                        }else{
                                            Log.e(TAG, "Thêm vào giỏ hàng không  thành công");
                                        }
                                    }
                                });
                            }
                            //Đoạn này có thể bỏ
                            Log.v("Item","ID: "+task.getResult().getDocuments().get(i).getId()+"\n"
                                    +task.getResult().getDocuments().get(i).getString("ID_product")
                                    +"\n"+task.getResult().getDocuments().get(i).getString("Product_name")
                                    +"\n"+String.valueOf(  task.getResult().getDocuments().get(i).getLong("Quantity")));
                        }
                    }
                    else{
                        Log.v(TAG,"Ban ghi rong~");

                        collectionReference.document((String) newData.get("ID_product")).set(newData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.v(TAG,"Tạo thành công một giỏi hàng mới "+(String)newData.get("ID"));
                                }
                            }
                        });
                    }
                }
            });
    }


    public static void SaveuserFirebase(Map<String,Object> newData,FirebaseFirestore db){
        String id=(String)newData.get("ID_Customer");
        String nameuser =(String)newData.get("Name_cus");

        db.collection("customers").document(id).set(newData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.v(TAG,"Thêm thành công " +id + "\nNameuser: "+nameuser);
                }else {
                    Log.v(TAG,"Thêm Thất bại  " +id + "\nNameuser: "+nameuser);
                }
            }
        });
    }

    public static void SaveaccountFirebase(Map<String,Object> newData,FirebaseFirestore db){
        String id=(String)newData.get("ID_Customer");
        String nameuser =(String)newData.get("Nameuser");
        int Roleid =(int) newData.get("Roleid");
        DocumentReference userRef =db.collection("customers").document(id);
        DocumentReference accRef =db.collection("accounts").document(id);
        db.collection("customers").document(id).set(newData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.v(TAG,"Thêm thành công Account " +id + "\nNameuser: "+nameuser);
                }else {
                    Log.v(TAG,"Thêm Thất bại  " +id + "\nNameuser: "+nameuser+"\nRoleid: "+Roleid);
                }
            }
        });

    }
}
