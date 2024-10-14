package com.example.onlineshopp.FragmentLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.onlineshopp.Adapter.cartAdapter;
import com.example.onlineshopp.MainActivity;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.databinding.FragmentCartBinding;

import java.util.List;
import com.example.onlineshopp.temptlA;
public class Fragment_Cart extends Fragment implements InterFace {
    FragmentCartBinding binding;
    String TAG=Fragment_Cart.class.getName();
    MainActivity main;
    private List<cartItem> mlistcart=temptlA.listcart;
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

            binding.checkoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v(TAG,"Ban muon chuyen den thanh toan");
                }
            });


            binding.imageViewclear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    backFragment1();
                }
            });
    }
    private void setRecycleCart(){
        cartAdapter listcart=new cartAdapter(mlistcart,this);
        binding.cartRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.cartRecycleView.setAdapter(listcart);

        binding.imageViewclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listcart.toggleI3Visibility();
                listcart.notifyDataSetChanged();
            }
        });

        listcart.notifyDataSetChanged();

    }
    @Override
    public void onQuantityChanged() {
        binding.totalPrice.setText(temptlA.getTotalBillCart());
    }

    @Override
    public void onDatapass() {

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
}
