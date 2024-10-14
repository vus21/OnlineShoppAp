package com.example.onlineshopp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.onlineshopp.R;

import java.util.List;

public class Pager2Adapter extends RecyclerView.Adapter<Pager2Adapter.ViewHolder> {

    private List<String> mlist;

    public Pager2Adapter(List<String> mlist) {
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mview= LayoutInflater.from(parent.getContext()).inflate(R.layout.itempager2_banner,parent,false);

        return new ViewHolder(mview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String urlimg=mlist.get(position);

            if (!urlimg.isEmpty()){
                Glide.with(holder.itemView.getContext()).load(urlimg).into(holder.img);
            }else{
                holder.img.setImageResource(R.drawable.view_border_top);
            }
    }

    @Override
    public int getItemCount() {
        return mlist.size()>0?mlist.size():0;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
            ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.pager2);
        }
    }
}
