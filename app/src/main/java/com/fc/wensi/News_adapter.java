package com.fc.wensi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class News_adapter extends RecyclerView.Adapter <News_adapter.ViewHolder> {

    public List<Map<String, Object>> list = new ArrayList<>();
    public Context con;

    public News_adapter(List<Map<String,Object>> list, Context con) {
        this.con=con;
        this.list=list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.recyTitle.setText(list.get(position).get("title").toString());
        holder.recyDate.setText(list.get(position).get("date").toString());
        Glide.with(holder.recyImageView.getContext()).load(list.get(position).get("url")).into(holder.recyImageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView recyTitle;
        public TextView recyDate;
        public ImageView recyImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            recyTitle = itemView.findViewById(R.id.news_title);
            recyDate = itemView.findViewById(R.id.news_date);
            recyImageView = itemView.findViewById(R.id.news_image);
        }
    }
}

