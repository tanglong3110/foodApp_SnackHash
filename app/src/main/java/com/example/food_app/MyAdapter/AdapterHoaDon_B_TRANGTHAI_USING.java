package com.example.food_app.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.FragmentADMIN.Fragment_QuanLy;
import com.example.food_app.FragmentADMIN.Main_ListHDCT_B_MAHD;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.HDCT;
import com.example.food_app.Model.HoaDon;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterHoaDon_B_TRANGTHAI_USING extends RecyclerView.Adapter<AdapterHoaDon_B_TRANGTHAI_USING.MyViewHolder> {
    Context context;
    ArrayList<HoaDon> list;
    String HOST = LoginMain.HOST;


    public AdapterHoaDon_B_TRANGTHAI_USING(Context context, ArrayList<HoaDon> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.item_hoadon_b_trangthai_using, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HoaDon hd = list.get(position);
        holder.tv_mahd.setText(""+hd.getMaHD());
        holder.tv_email.setText(hd.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_mahd, tv_email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_mahd = itemView.findViewById(R.id.tv_mahd);
            tv_email = itemView.findViewById(R.id.tv_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HoaDon hd = list.get(getAdapterPosition());
                    int MAHD = hd.getMaHD();
                    Intent i = new Intent(context, Main_ListHDCT_B_MAHD.class);
                    i.putExtra("MAHD",MAHD);
                    context.startActivity(i);

                    removeItem(hd);
                }
            });
        }
    }


    public void removeItem(HoaDon hd){
        int position = list.indexOf(hd);
        list.remove(position);
        notifyItemRemoved(position);

    }


}
