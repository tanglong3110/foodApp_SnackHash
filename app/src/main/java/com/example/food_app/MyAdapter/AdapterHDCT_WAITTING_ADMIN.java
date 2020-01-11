package com.example.food_app.MyAdapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.food_app.FragmentADMIN.Main_ListHDCT_B_MAHD;
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

public class AdapterHDCT_WAITTING_ADMIN extends RecyclerView.Adapter<AdapterHDCT_WAITTING_ADMIN.MyViewHolder>{
    Context context;
    ArrayList<HDCT> list;
    ArrayList<SanPham> listSP;
    int MAHD = Main_ListHDCT_B_MAHD.MAHD;
    String HOST = LoginMain.HOST;
    String linkGetIMV = HOST+"food_project/GetSanPham_MOTASP_TENSP_B_MASP.php/";
    String linkCONFIRM_HDCT = HOST+"food_project/UpdateHDCT_TRANGTHAI_CONFIRM_B_MAHDCT.php/";
    String linkGetSanPham_SOLUONG_B_MASP = HOST+ "food_project/GetSanpham_SOLUONG_B_MASP.php/";
    String link_UpdateSanPham_SOLUONG_B_MASP = HOST+ "food_project/UpdateSanPham_SOLUONG_B_MASP.php/";
    String link_UpdateHDCT_SOLUONG_CONFIRM_B_MAHDCT= HOST+"food_project/UpdateHDCT_SOLUONG_B_MAHDCT.php/";
    String link_UpdateHDCT_TRANGTHAI_DELETE_B_MAHDCT = HOST+"food_project/UpdateHDCT_TRANGTHAI_DELETE_B_MAHDCT.php/";
    String linkGetSanPham_TENSP_B_MASP= HOST+"food_project/GetSanPham_TENSP_B_MASP.php/";



    String TENSP ;
    public AdapterHDCT_WAITTING_ADMIN(Context context, ArrayList<HDCT> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_sanpham_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            final HDCT hdct = list.get(position);
            final int masp = hdct.getMasp();
            final int mahdct = hdct.getMahdct();
            final int mahd = hdct.getMahd();
            GetSanPham_MOTASP_TENSP_B_MASP(holder, masp);
            holder.tv_gia.setText(hdct.getGiatien() + "");
            holder.tv_soluong.setText(hdct.getSoluong() + "");
            holder.imv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        GetSanPham_SOLUONG_B_MASP(masp, mahdct, hdct);

                }
            });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imv_sanpham, imv_confirm;
        TextView tv_tensp, tv_gia, tv_soluong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_sanpham = itemView.findViewById(R.id.imv_sanpham);
            imv_confirm = itemView.findViewById(R.id.imv_confirm);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            tv_gia = itemView.findViewById(R.id.tv_giatien);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);
            tv_tensp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, ""+tv_tensp.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public void GetSanPham_MOTASP_TENSP_B_MASP(final AdapterHDCT_WAITTING_ADMIN.MyViewHolder holder, final int MASP){
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

                                TENSP = tensp;
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




    public void UpdateHDCT_TRANGTHAI_COMFIRM_B_MAHDCT(final int MAHDCT){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkCONFIRM_HDCT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(context, "CONFIRM thành công ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "CONFIRM thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Lỗi dữ liệu !!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi đường link !!!", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("mahdct", MAHDCT+"");
                return param;
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



    public void GetSanPham_SOLUONG_B_MASP(final int MASP, final int mahdct, final HDCT hdct){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkGetSanPham_SOLUONG_B_MASP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int SOLUONG_SanPham = 0;
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("sanpham");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject sanpham = jsonArray.getJSONObject(i);
                                int soluong = sanpham.getInt("soluong");

                                SOLUONG_SanPham = soluong;
                            }




                            int SOLUONG_HDCT = hdct.getSoluong();


                            if(SOLUONG_SanPham > SOLUONG_HDCT){
                                UpdateHDCT_TRANGTHAI_COMFIRM_B_MAHDCT(mahdct);
                                Toast.makeText(context, ""+ SOLUONG_SanPham+ SOLUONG_HDCT, Toast.LENGTH_LONG).show();
                                int sl = SOLUONG_SanPham - SOLUONG_HDCT;
                                int masp = hdct.getMasp();
                                UpdateSanPham_SOLUONG_B_MASP(masp, sl);
                                removeItem(hdct);
                            }

                            else if(SOLUONG_SanPham == 0 ){
                                final Dialog dialog = new Dialog(context);
                                dialog.setContentView(R.layout.dialog_sanpham_het_admin);
                                TextView tv_tensp = dialog.findViewById(R.id.tv_tensp);
                                Button btn_delete = dialog.findViewById(R.id.btn_delete);
                                dialog.setCancelable(true);

                                GetSanPham_TENSP_B_MASP(MASP, tv_tensp);
                                btn_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        UpdateHDCT_TRANGTHAI_DELETE_B_MAHDCT(mahdct);
                                        removeItem(hdct);

                                        dialog.cancel();
                                    }
                                });
                                dialog.show();

                            }

                            else if(SOLUONG_HDCT == SOLUONG_SanPham){
                                UpdateHDCT_TRANGTHAI_COMFIRM_B_MAHDCT(mahdct);
                                UpdateSanPham_SOLUONG_B_MASP(MASP,0);
                                removeItem(hdct);
                            }



                            else if(SOLUONG_SanPham < SOLUONG_HDCT){
                                Toast.makeText(context, "Số lượng trong hdct quá lớn", Toast.LENGTH_SHORT).show();
                                final Dialog dialog = new Dialog(context);
                                dialog.setCancelable(false);
                                dialog.setContentView(R.layout.dialog_sanpham_soluong_admin);
                                TextView tv_tensp = dialog.findViewById(R.id.tv_tensp);
                                TextView tv_soluong = dialog.findViewById(R.id.tv_soluong);
                                Button btn_ok = dialog.findViewById(R.id.btn_ok);
                                Button btn_delete = dialog.findViewById(R.id.btn_delete);

                                GetSanPham_TENSP_B_MASP(MASP, tv_tensp);
                                tv_soluong.setText(SOLUONG_SanPham+"");

                                final int finalSOLUONG_SanPham = SOLUONG_SanPham;
                                btn_ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        UpdateHDCT_SOLUONG_CONFIRM_B_MAHDCT(mahdct, finalSOLUONG_SanPham);
                                        UpdateSanPham_SOLUONG_B_MASP(MASP, 0);
                                        removeItem(hdct);
                                        dialog.cancel();
                                    }
                                });

                                btn_delete.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        UpdateHDCT_TRANGTHAI_DELETE_B_MAHDCT(mahdct);
                                        removeItem(hdct);
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Lỗi dữ liệu !!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi đường link!!!", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<String, String>();
                param.put("masp", MASP+"");
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void GetSanPham_TENSP_B_MASP(final int MASP, final TextView tv_tensp){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkGetSanPham_TENSP_B_MASP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("sanpham");

                            for(int i = 0 ; i <jsonArray.length(); i++){
                                JSONObject sanpham = jsonArray.getJSONObject(i);
                                String tensp = sanpham.getString("tensp");

                                tv_tensp.setText(tensp);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Lỗi dữ liệu !!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi đường link !!!", Toast.LENGTH_SHORT).show();
                        Log.d("loi",error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("masp", MASP+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    public void UpdateSanPham_SOLUONG_B_MASP(final int MASP, final int soluong){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link_UpdateSanPham_SOLUONG_B_MASP,
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
                params.put("masp",MASP+"");
                params.put("soluong", soluong+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void UpdateHDCT_SOLUONG_CONFIRM_B_MAHDCT(final int mahdct, final int soluong){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link_UpdateHDCT_SOLUONG_CONFIRM_B_MAHDCT,
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
                params.put("soluong", soluong+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void UpdateHDCT_TRANGTHAI_DELETE_B_MAHDCT(final int mahdct){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link_UpdateHDCT_TRANGTHAI_DELETE_B_MAHDCT,
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
                Map<String, String> param = new HashMap<String, String>();
                param.put("mahdct", mahdct+"");
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }










}
