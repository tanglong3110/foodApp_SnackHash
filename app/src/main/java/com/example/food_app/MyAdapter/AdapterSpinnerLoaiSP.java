package com.example.food_app.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.food_app.Model.LoaiSP;
import com.example.food_app.R;

import java.util.ArrayList;

public class AdapterSpinnerLoaiSP extends BaseAdapter {
    Context context;
    ArrayList<LoaiSP> list;
    public AdapterSpinnerLoaiSP(Context context, ArrayList<LoaiSP> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MyHolder holder;

        if(view == null){
            holder = new MyHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_sp_loaisp, viewGroup, false);
            holder.tv_loai = view.findViewById(R.id.tv_loaisp);

            view.setTag(holder);
        }
        else {
            holder = (MyHolder) view.getTag();
        }
            LoaiSP loaiSP = list.get(i);
            String loai = loaiSP.getLoaiSP();

            holder.tv_loai.setText(loai);

        return view;
    }

    class MyHolder{
        TextView tv_loai;
    }
}
