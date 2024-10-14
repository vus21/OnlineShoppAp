package com.example.onlineshopp.ActivityLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.onlineshopp.Adapter.foodAdapter;
import com.example.onlineshopp.FragmentLayout.FragmentHomeViewModel;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.MainActivity;
import com.example.onlineshopp.Object.ItemFood;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.databinding.ItembuysBinding;
import com.example.onlineshopp.databinding.ItemfoodDeltaiBinding;
import com.example.onlineshopp.temptlA;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        binding.detailitemsell.setText(String.valueOf(item.getReview()));
        binding.txtratingitem.setText(String.valueOf(item.getRating()));
        binding.txtCostitem.setText(String.valueOf(item.getCostNew()));
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



        binding.itemorther.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        FragmentHomeViewModel.loadlistFood();
        foodAdapter adapter =new foodAdapter(FragmentHomeViewModel.mlisst);
        binding.itemorther.setAdapter(adapter);


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
    public void onDatapass() {

    }

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
        binding1.textView3.setText(String.valueOf(item.getCostNew()));
        binding1.textView2.setText(String.valueOf(item.getCostOld()));
        binding1.buttonminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i[0] >1 && i[0]<item.getCostOld()){
                    i[0] = i[0] -1;
                    binding1.editTextText.setText(String.valueOf(i[0]));
                }
            }
        });

        binding1.buttonplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i[0]<=item.getCostOld()){
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


    private void addToCart(){
        LocalDateTime currentTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        cartItem itemFake =new cartItem(String.valueOf(item.getID()),item,1);
        if(temptlA.addProduct(itemFake)){
            Log.v(TAG,"Cap nhat lai gio hang thanh cong");
        }else{
            temptlA.listcart.add(itemFake);
            Log.v(TAG,"Them sp moi vao gio hang voi id:"+String.valueOf(itemFake.getItem().getID()));
        }

        for(int i=0;i<temptlA.listcart.size();i++){
            Log.v(TAG, "addCart:"+temptlA.listcart.get(i).getQuantity());
            Log.v(TAG, "addCart: "+String.valueOf(temptlA.listcart.get(i).getItem().getID()) );
        }

        Log.v(TAG,itemFake.getItemID()+"\n"+itemFake.getItem().getTitle()+"\n"+String.valueOf(itemFake.getItem().getCostNew()));
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
