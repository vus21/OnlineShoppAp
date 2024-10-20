package com.example.onlineshopp.ActivityLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.onlineshopp.Adapter.foodAdapter;
import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.FragmentLayout.FragmentHomeViewModel;
import com.example.onlineshopp.FragmentLayout.Fragment_Home;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.MainActivity;
import com.example.onlineshopp.Object.ItemFood;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.databinding.ItembuysBinding;
import com.example.onlineshopp.databinding.ItemfoodDeltaiBinding;
import com.example.onlineshopp.temptlA;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityDeltai_Item extends AppCompatActivity  implements InterFace {
    ItemfoodDeltaiBinding binding;
    ItemFood item;
    String TAG =ActivityDeltai_Item.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding=ItemfoodDeltaiBinding.inflate(getLayoutInflater());
        Intent i=getIntent();
        item=(ItemFood)i.getSerializableExtra("items");
        setContentView(binding.getRoot());
            eVentCompoment();
    }


    @Override
    public void setMapping() {

    }

    @Override
    public void eVentCompoment() {

        binding.imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.textView4.setText(slProduct());

        binding.imagegotocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(ActivityDeltai_Item.this, MainActivity.class);
                startActivityForResult(i,temptlA.REQUEST_GOHOME);
            }
        });


        Glide.with(this).load(item.getListURL().get(0)).into(binding.imageViewitems);
        binding.txtTitleitem.setText(item.getTitle());
        binding.detailitemsell.setText("SL ban ra: "+String.valueOf(item.getInventory()*6));
        binding.txtratingitem.setText(String.valueOf(item.getInventory()));
        binding.txtCostitem.setText(String.valueOf(item.getPrice()));
        binding.textdescriptionitem.setText(item.getDesc());


        binding.btnbuyitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!temptlA.isIsLogin()){
                    Toast.makeText(ActivityDeltai_Item.this,"Vui lòng đăng nhập để thực hiện các bước tiếp theo",Toast.LENGTH_LONG);
                    Log.v(TAG,"BTNBuyItem \n"+"Người dùng cố truy cập vao mua hàng");
                }else{
                    OpenDialogSelectItem();
                    Log.v(TAG,"User đang thực hiện mua hàng");
                }
            }
        });



        binding.itemorther.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,
                false));
//        Fragment_Home fragmentHome=new Fragment_Home();
//        fragmentHome.loadlistFood(getApplicationContext());
//        foodAdapter adapter =new foodAdapter(fragmentHome.getMlistfood());
//        binding.itemorther.setAdapter(adapter);


        binding.imageaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!temptlA.isIsLogin()){
                    Toast.makeText(ActivityDeltai_Item.this,"Vui lòng đăng nhập để thực hiện các bước tiếp theo",Toast.LENGTH_LONG);
                    Log.v(TAG,"ImageaddtoCart \n"+"Người dùng cố truy cập vao mua hàng");
                }else {
                    addToCart();
                    binding.textView4.setText(slProduct());
                }
            }
        });

    }

    @Override
    public void onQuantityChanged() {

    }

    @Override
    public void getDataCheckBox(List<cartItem> mlistcart) {

    }
//    public boolean checkProfile(String id){
//        ConnectSQLite connect=new ConnectSQLite(getApplicationContext());
//        SQLiteDatabase db=connect.getWritableDatabase();
//        Cursor cursor=db.rawQuery("SELECT * FROM "+ConnectSQLite.TABLE_6+" WHERE ID_Customer =?",new String[]{id});
//    }
    //dem so lg san pham
    public static  String slProduct(){
        int total=0;
        if(temptlA.listcart.size()>0){
            for( int i=0;i<temptlA.listcart.size();i++){
               cartItem item= temptlA.listcart.get(i);
                total=total+item.getQuantity();
            }
        }
            return String.valueOf(total);
    }

    private void OpenDialogSelectItem(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        // Sử dụng ViewBinding để lấy `ItembuysBinding`
        ItembuysBinding binding1 = ItembuysBinding.inflate(inflater);
        View mview = binding1.getRoot();
        final int[] i = {Integer.parseInt(binding1.editTextText.getText().toString())};
        binding1.textView.setText(item.getTitle());
        binding1.textView3.setText(String.valueOf(item.getPrice()));
        binding1.textView2.setText(String.valueOf(item.getInventory()));
        binding1.buttonminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i[0] >1 && i[0]<item.getInventory()){
                    i[0] = i[0] -1;
                    binding1.editTextText.setText(String.valueOf(i[0]));
                }
            }
        });

        binding1.buttonplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i[0]<=item.getInventory()){
                    i[0]=i[0]+1;
                    binding1.editTextText.setText(String.valueOf(i[0]));
                }
            }
        });
        builder.setView(mview);
// Tạo AlertDialog từ builder
        AlertDialog dialog = builder.create();

        binding1.imageButtonexits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        binding.btnbuyitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(ActivityDeltai_Item.this,)
            }
        });

        // Hiển thị dialog
        dialog.show();
    }

    private void canbuy() {
        if (temptlA.IDuser == null && temptlA.IDuser.isEmpty()) {
            Log.v(TAG, "Ko tồn tại ID này");
        } else {
            ConnectSQLite connect = new ConnectSQLite(getApplicationContext());
            SQLiteDatabase db = connect.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM " + ConnectSQLite.TABLE_6 + " WHERE ID_Customer = ?", new String[]{temptlA.IDuser});
            if (cursor.moveToFirst()) {
                if ((cursor.getString(4).isEmpty())) {
                    Log.d("Fragment_Cart", "Vui lòng cập nhật thông tin trước khi mua hàng");
                    Toast.makeText(getApplicationContext(), "Vui lòng cập nhật thông tin trước khi mua hàng", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void addToCart(){
        LocalDateTime currentTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);


        if(!temptlA.IDuser.isEmpty()) {
            cartItem itemFake = new cartItem(String.valueOf(item.getID()), item, 1);
            if (temptlA.addProduct(itemFake)) {
                Log.v(TAG, "Cap nhat lai gio hang thanh cong");
            } else {
                temptlA.listcart.add(itemFake);
                Log.v(TAG, "Them sp moi vao gio hang voi id:" + String.valueOf(itemFake.getItem().getID()));
            }

            for (int i = 0; i < temptlA.listcart.size(); i++) {
                Log.v(TAG, "addCart:" + temptlA.listcart.get(i).getQuantity());
            }
            pushdataFirebaseStore(temptlA.IDuser,itemFake);
        }

    }

    private  void pushdataFirebaseStore(String id,cartItem itemFake){
        ConnectSQLite connectSQLite= new ConnectSQLite(getApplicationContext());
        SQLiteDatabase db=connectSQLite.getWritableDatabase();
        String query="SELECT * FROM "+ConnectSQLite.TABLE_3+" WHERE ID_Customer = ? ";
        Cursor cursor =db.rawQuery(query,new String[]{temptlA.IDuser});
        if(cursor.getCount()>0){

        }
        checkadd(itemFake,id);


    }
    private void checkadd(cartItem itemFake, String id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Tạo truy vấn để kiểm tra xem tài liệu có tồn tại không
        Query db123 = db.collection("cartdeltai").whereEqualTo("ID_CUs", temptlA.IDuser);
        db123.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult() != null && !task.getResult().isEmpty()) {
                        // Nếu tài liệu tồn tại, lấy ID
                        String existingId = task.getResult().getDocuments().get(0).getId();
                        temptlA.IDCART=existingId;
                        Log.d(TAG, "day la id Cart\n" + existingId);
                        Additem(existingId,itemFake);
                        return;
                    } else {
                        // Nếu không có tài liệu nào, thêm tài liệu mới
                        DocumentReference doc12 = db.collection("cartdeltai").document();

                        Map<String, Object> maps = new HashMap<>();
                        maps.put("ID", doc12.getId());
                        maps.put("ID_CUs", temptlA.IDuser);

                        // Lưu tài liệu mới vào Firestore
                        doc12.set(maps).addOnSuccessListener(aVoid -> {
                            Log.v(TAG, "Them oi 1 document \n ID: " + doc12.getId());
                        }).addOnFailureListener(e -> {
                            Log.w(TAG, "Error: " + e.getMessage());
                        });
                    }
                } else {
                    Log.w("Firestore", "Error getting documents.", task.getException());
                }
            }
        });
    }

    private void Additem(String id,cartItem item){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> newdata=new HashMap<>();
        newdata.put("ID_product",item.getItemID());
        newdata.put("Quantity",item.getQuantity());
        newdata.put("Product_name",item.getItem().getTitle());
        // Tạo truy vấn để kiểm tra xem tài liệu có tồn tại không
        CollectionReference db123 = db.collection("cart_customer").document(id).collection(temptlA.IDuser);
        DocumentReference document123 = db123.document(item.getItemID());

        document123.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        //  cập nhật số lượng
                        int currentQuantity = document.getLong("Quantity").intValue();
                        document123.update("Quantity", currentQuantity + 1)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Số lượng đã được cập nhật thành công cho ID: " + item.getItemID());
                                })
                                .addOnFailureListener(e -> {
                                    Log.w(TAG, "Lỗi khi cập nhật số lượng: " + e.getMessage());
                                });
                    } else {
                        // Nếu tài liệu không tồn tại, tạo mới
                        Map<String, Object> newdata = new HashMap<>();
                        newdata.put("ID_product", item.getItemID());
                        newdata.put("Quantity", item.getQuantity());
                        newdata.put("Product_name", item.getItem().getTitle());

                        document123.set(newdata).addOnSuccessListener(aVoid -> {
                                    Log.d(TAG, "Tài liệu đã được thêm thành công với ID: " + item.getItemID());
                                })
                                .addOnFailureListener(e -> {
                                    Log.w(TAG, "Lỗi khi thêm tài liệu: " + e.getMessage());
                                });
                    }
                } else {
                    Log.w(TAG, "Lỗi khi kiểm tra tài liệu: " + task.getException());
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==temptlA.REQUEST_GOHOME){
            Log.v(TAG,"Da nhan REQUEST_GOHOME");
        }else {
            Log.v(TAG,"ko nhan REQUEST_GOHOME");

        }
    }
}
