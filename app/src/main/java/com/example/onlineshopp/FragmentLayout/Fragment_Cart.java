package com.example.onlineshopp.FragmentLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshopp.ActivityLayout.Activity_payment;
import com.example.onlineshopp.Adapter.cartAdapter;
import com.example.onlineshopp.Database.ConnectSQLite;
import com.example.onlineshopp.MainActivity;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.databinding.FragmentCartBinding;

import java.util.ArrayList;
import java.util.List;
import com.example.onlineshopp.temptlA;

public class Fragment_Cart extends Fragment implements InterFace {
    FragmentCartBinding binding;
    String TAG="Fragment_Cart";
    MainActivity main;
    private List<cartItem> mlistcart=temptlA.listcart;

    //Item Options
    List<cartItem> mlistcartcheckbox=new ArrayList<>();
    cartAdapter listcart;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding=FragmentCartBinding.inflate(getLayoutInflater());

        setMapping();
        eVentCompoment();

        return binding.getRoot();
    }
    @Override
    public void setMapping() {

    }

    @Override
    public void eVentCompoment() {
            setRecycleCart();
            showChucNang();

            binding.totalPrice.setText(temptlA.getTotalBillCart());




            binding.imageViewclear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backFragment1();
                }
            });
            binding.imageselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d(TAG,"Size checkobx:"+mlistcartcheckbox.size()+"\nSizeCart:"+mlistcart.size());
                    listcart.toggleI3Visibility();
                }
            });

        binding.imageViewclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listcart.notifyDataSetChanged();
            }
        });


        binding.gotopayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Di den thanh toan
                Intent int2=new Intent(getActivity(), Activity_payment.class);
                if(!mlistcartcheckbox.isEmpty()){
                   int2.putExtra("size", mlistcartcheckbox.size());
                   int2.putExtra("sizecart",String.valueOf(mlistcartcheckbox.size()));
                    for (int i=0;i<mlistcartcheckbox.size();i++){
                        int2.putExtra("Item"+String.valueOf(i),mlistcartcheckbox.get(i));
                    }
                }else{
                   int2.putExtra("size", mlistcart.size());
                   int2.putExtra("sizecart",String.valueOf(mlistcart.size()));
                    for(int i=0;i<mlistcart.size();i++){
                        int2.putExtra("Item"+String.valueOf(i),mlistcart.get(i));
                    }
                }
                startActivity(int2);
            }
        });

    }
    private void setRecycleCart(){

     listcart  =new cartAdapter(mlistcart,this);
        binding.cartRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,false));
        binding.cartRecycleView.setAdapter(listcart);

        listcart.notifyDataSetChanged();

    }
    @Override
    public void onQuantityChanged() {
        binding.totalPrice.setText(temptlA.getTotalBillCart());
    }

    @Override
    public void getDataCheckBox(List<cartItem> mlistcart) {
        mlistcartcheckbox=mlistcart;
        if(mlistcartcheckbox.isEmpty()){
            Log.v(TAG,"Ban muon thanh toan tat ca?");
        }else{
            Log.v(TAG,"Cac sp muon dc thanh toan: ");

            //Get data item Logcat
            for(int i=0;i<mlistcartcheckbox.size();i++){
                    Log.v(TAG+"Item: "+String.valueOf(i),mlistcartcheckbox.get(i).getItemID()
                            + "\nQuantity: "+mlistcartcheckbox.get(i).getQuantity());
                    Log.v(TAG,"Size: "+mlistcartcheckbox.size());
            }
        }
    }

    private void showChucNang(){
        if (mlistcart.size() > 0) {
            binding.cartRecycleView.setVisibility(View.VISIBLE);
            binding.totalPrice.setVisibility(View.VISIBLE);
            binding.textView5.setVisibility(View.GONE);
        } else {
            binding.cartRecycleView.setVisibility(View.GONE);
            binding.totalPrice.setVisibility(View.GONE);
            binding.textView5.setVisibility(View.VISIBLE);
        }
    }

    private void backFragment1(){
        main = (MainActivity) getActivity();
            main.loadFragment(new Fragment_Home());
    }

    private  void checkprofile(){
        if( temptlA.IDuser == null && temptlA.IDuser.isEmpty()){
            Log.v(TAG,"Ko tồn tại ID này");
        }else{
                ConnectSQLite connect=new ConnectSQLite(getActivity());
                SQLiteDatabase db= connect.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM "+ConnectSQLite.TABLE_6+" WHERE ID_Customer = ?",new String[]{temptlA.IDuser});
                if(cursor.moveToFirst()){
                    if((cursor.getString(4).isEmpty())){
                        Log.d("Fragment_Cart","Vui lòng cập nhật thông tin trước khi mua hàng");
                        Toast.makeText(getActivity(),"Vui lòng cập nhật thông tin trước khi mua hàng",Toast.LENGTH_LONG).show();
                    }
                }
        }
    }
}
