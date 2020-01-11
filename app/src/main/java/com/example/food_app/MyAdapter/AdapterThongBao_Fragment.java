package com.example.food_app.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.food_app.Model.DocBao;
import com.example.food_app.R;

import java.util.List;

public class AdapterThongBao_Fragment extends ArrayAdapter<DocBao> {
    public AdapterThongBao_Fragment(Context context, int resource, List<DocBao> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.item_list_thongbao_fragment, null);
        }
        DocBao p = getItem(position);
        if (p != null) {
            // Anh xa + Gan gia tri
            TextView txt = (TextView) view.findViewById(R.id.txtTitle);
            ImageView img = view.findViewById(R.id.img);

            txt.setText(p.title);
            Glide.with(getContext()).load(p.image).into(img);

        }
        return view;
    }
}
