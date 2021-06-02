package com.work.myapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPolyline;

import java.util.ArrayList;

public class CustomApdater extends RecyclerView.Adapter<CustomApdater.CustomViewHolder> {

    private ArrayList<locationdata> arraylist;
    private Context context;

    public CustomApdater(ArrayList<locationdata> arraylist,Context context) {
        this.arraylist = arraylist;
        this.context = context;
    }



    interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }




    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recview,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load(arraylist.get(position).getImage())
                .into(holder.image);
        holder.street.setText(arraylist.get(position).getStreet());
        holder.time.setText(arraylist.get(position).getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onItemClick(holder.itemView,position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arraylist != null ? arraylist.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView street,time;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.image);
            this.street = itemView.findViewById(R.id.street);
            this.time = itemView.findViewById(R.id.time);
        }
    }
}
