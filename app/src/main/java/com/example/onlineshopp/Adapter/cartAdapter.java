package com.example.onlineshopp.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshopp.ActivityLayout.ActivityDeltai_Item;
import com.example.onlineshopp.interface1.InterFace;
import com.example.onlineshopp.Object.cartItem;
import com.example.onlineshopp.databinding.ItemCartBinding;

import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.myItemHolder> {
        private List<cartItem> mlist;
            private   boolean isVisibility=false;
    private InterFace ls;
    public cartAdapter(List<cartItem> mlist, InterFace ls1) {
        this.mlist = mlist;
        isVisibility=false;
        ls=ls1;
    }

    @NonNull
    @Override
    public myItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCartBinding binding = ItemCartBinding.inflate(inflater, parent, false);


        return new myItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull myItemHolder holder, @SuppressLint("RecyclerView") int position) {
                cartItem item =mlist.get(position);

                holder.tv.setText(item.getItem().getTitle());
                holder.tv1.setText(String.valueOf(item.getItem().getCostNew()));
                holder.tv2.setText(String.valueOf(item.getQuantity()));
        Glide.with(holder.itemView.getContext()).load(item.getItem().getListURL().get(0)).into(holder.img);


        holder.binding.imageViewminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getQuantity()>0){
                    item.setQuantity(item.getQuantity()-1);
                    holder.binding.editTextText2.setText(String.valueOf(item.getQuantity()));
                notifyDataSetChanged();
                }else{
                    item.setQuantity(1);
                    holder.binding.editTextText2.setText(String.valueOf(item.getQuantity()));
                }
                ls.onQuantityChanged();
            }
        });
        if(!isVisibility){
            holder.binding.imageView4.setVisibility(View.GONE);
            holder.binding.cartItemImage.setMaxWidth(120);
            holder.binding.cartItemName.setMaxWidth(430);
        }else{
            holder.binding.imageView4.setVisibility(View.VISIBLE);
            holder.binding.cartItemImage.setMaxWidth(80);
            holder.binding.cartItemName.setMaxWidth(300);
        }
        holder.binding.editTextText2.setText(String.valueOf(item.getQuantity()));
        holder.binding.imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mlist.get(position).getItemID().equals(item.getItemID());
                mlist.remove(item);
            }
        });
        holder.binding.imageView3plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getQuantity()>0){
                    item.setQuantity(item.getQuantity()+1);
                    holder.binding.editTextText2.setText(String.valueOf(item.getQuantity()));
                notifyDataSetChanged();
                }else{
                    item.setQuantity(1);
                holder.binding.editTextText2.setText(String.valueOf(item.getQuantity()));
                }
                ls.onQuantityChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(holder.itemView.getContext(), ActivityDeltai_Item.class);
                i.putExtra("items",item.getItem());
                holder.itemView.getContext().startActivity(i);
            }
        });

    }
        public void toggleI3Visibility(){
            isVisibility = !isVisibility;
            notifyDataSetChanged();
        }
    @Override
    public int getItemCount() {
        return mlist.size()>0?mlist.size():0;
    }

    public class myItemHolder extends RecyclerView.ViewHolder {
        TextView tv,tv1,tv2;
        ImageView img;
        ItemCartBinding binding;
        public myItemHolder(ItemCartBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
            tv=binding.cartItemName;
            tv1=binding.cartItemPrice;
            tv2=binding.cartItemQuantity;
            img=binding.cartItemImage;

        }
    }

}
