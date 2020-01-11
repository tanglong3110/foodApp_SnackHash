package com.example.food_app.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app.Model.ImageHome;
import com.example.food_app.R;

import java.util.ArrayList;

public class AdapterIMV extends RecyclerView.Adapter<AdapterIMV.ViewHolder> {
    ArrayList<ImageHome> list;
    Context context;
    public AdapterIMV(Context context, ArrayList<ImageHome> list){
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_imv,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageHome iv = list.get(position);
        holder.imv.setImageResource(iv.getImage());
        holder.tv_nameimv.setText(iv.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imv;
        TextView tv_nameimv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imageView);
            tv_nameimv = itemView.findViewById(R.id.tv_nameimv);
        }
    }
}
