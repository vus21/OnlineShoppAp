package com.example.onlineshopp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.databinding.ItemfoodBinding;
import com.example.onlineshopp.databinding.ItemseleclbuysBinding;

import java.util.List;

public class itemselectAdapter extends RecyclerView.Adapter<itemselectAdapter.MyviewHolder>{
    private List<cartItem> mlist;


    public itemselectAdapter(List<cartItem> mlist){
        this.mlist=mlist;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemseleclbuysBinding binding= ItemseleclbuysBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new MyviewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
                cartItem newitem= mlist.get(position);

        Glide.with(holder.itemView.getContext()).
                load(newitem.getItem().getPicURL()).
                into(holder.binding.imageView6);

        holder.binding.textTitle.setText(newitem.getItem().getTitle());
        holder.binding.textAmount.setText(String.valueOf(newitem.getQuantity()*newitem.getItem().getPrice()));
        holder.binding.textQuantitly.setText(String.valueOf(newitem.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mlist.size()!=0?mlist.size():0;
    }

    public class MyviewHolder extends RecyclerView.ViewHolder{
        ItemseleclbuysBinding binding;

        public MyviewHolder(ItemseleclbuysBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
