package com.example.food_app.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.food_app.FragmentADMIN.Main_ListHDCT_TRANGTHAI_CONFIRM_B_MAHD;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.HoaDon;
import com.example.food_app.Model.SanPham;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_HoaDon_TRANGTHAI_DONE_ADMIN extends RecyclerView.Adapter<Adapter_HoaDon_TRANGTHAI_DONE_ADMIN.MyViewHolder>{
    Context context;
    ArrayList<HoaDon> list;
    String HOST = LoginMain.HOST;



    public Adapter_HoaDon_TRANGTHAI_DONE_ADMIN(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon_trangthai_done_admin,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HoaDon hoaDon = list.get(position);
        holder.tv_mahd.setText(hoaDon.getMaHD()+"");
        holder.tv_email.setText(hoaDon.getEmail());
        holder.tv_ngay.setText(hoaDon.getNgay());
        holder.tv_trangthai.setText(hoaDon.getTrangthai());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_mahd, tv_email, tv_ngay, tv_trangthai;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mahd = itemView.findViewById(R.id.tv_mahd);
            tv_email = itemView.findViewById(R.id.tv_email);
            tv_ngay = itemView.findViewById(R.id.tv_ngay);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HoaDon hd = list.get(getAdapterPosition());
                    int MAHD = hd.getMaHD();

                    Intent intent = new Intent(context, Main_ListHDCT_TRANGTHAI_CONFIRM_B_MAHD.class);
                    intent.putExtra("MAHD",MAHD);
                    context.startActivity(intent);
                }
            });
        }
    }


}
