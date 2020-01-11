package com.example.food_app.MyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.food_app.FragmentUSER.GioHangMain;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.HDCT;
import com.example.food_app.Model.SanPham;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterHDCT_WAITTING_USER extends RecyclerView.Adapter<AdapterHDCT_WAITTING_USER.MyViewHolder> {
    Context context;
    ArrayList<HDCT> list ;

    String HOST = LoginMain.HOST;
    String linkGetIMV = HOST+"food_project/GetSanPham_MOTASP_TENSP_B_MASP.php/";
    String linkUpdate_TRANGTHAI_B_MAHDCT = HOST+"food_project/UpdateHDCT_TRANGTHAI_DELETE_B_MAHDCT.php/";
    ArrayList<SanPham> listSP;

    public AdapterHDCT_WAITTING_USER(Context context, ArrayList<HDCT> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hdct_waitting_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final HDCT hdct = list.get(position);
         int masp = hdct.getMasp();
        GetSanPham_MOTASP_B_MASP(holder, masp);
        holder.tv_gia.setText(hdct.getGiatien()+"");
        holder.tv_soluong.setText(hdct.getSoluong()+"");

        holder.imv_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mahdct = hdct.getMahdct();
                UpdateHDCT_TRANGTHAI_B_MAHDCT(mahdct);

                removeItem(hdct);
                TextView tv_tonggia = ((GioHangMain)context).tv_tonggia;
                float tongtien = ((GioHangMain)context).tongtien;
                ((GioHangMain)context).GetTongTien(list, tongtien, tv_tonggia);



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imv_sanpham, imv_xoa;
        TextView tv_tensp, tv_gia, tv_soluong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_sanpham = itemView.findViewById(R.id.imv_sanpham);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            tv_gia = itemView.findViewById(R.id.tv_giatien);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
            imv_xoa = itemView.findViewById(R.id.imv_xoa);


        }
    }

    public void GetSanPham_MOTASP_B_MASP(final MyViewHolder holder, final int MASP){
        StringRequest stringRequest = new StringRequest(Request.Method.POST ,linkGetIMV,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listSP = new ArrayList<SanPham>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("sanpham");
                            for (int i = 0 ; i < jsonArray.length(); i++){
                                JSONObject hdct = jsonArray.getJSONObject(i);
                                String tensp  = hdct.getString("tensp");
                                String imv = hdct.getString("motasp");

                                holder.tv_tensp.setText(tensp);
                                Glide.with(context).load(HOST+"food_project/"+imv).into(holder.imv_sanpham);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                            Log.d("loi" , e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi dữ liệu", Toast.LENGTH_SHORT).show();
                        Log.d("loi" , error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("masp", MASP+"");
                return params;
             }
        };
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }


    public  void UpdateHDCT_TRANGTHAI_B_MAHDCT(final int mahdct){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkUpdate_TRANGTHAI_B_MAHDCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mahdct", mahdct+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void removeItem(HDCT hdct){
        int position = list.indexOf(hdct);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
