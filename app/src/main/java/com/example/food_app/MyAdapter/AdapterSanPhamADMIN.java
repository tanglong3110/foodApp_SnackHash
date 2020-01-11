package com.example.food_app.MyAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.example.food_app.FragmentADMIN.Fragment_SanPham;
import com.example.food_app.FragmentADMIN.MainListSanPham;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.SanPham;
import com.example.food_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterSanPhamADMIN extends RecyclerView.Adapter<AdapterSanPhamADMIN.MyViewHolder> {
    public String HOST = LoginMain.HOST;
    public Context context;
    public ArrayList<SanPham> listsp;
    String link = HOST+"food_project/EditSanpham_B_MaSP.php/";
    String linkDeteleSanPham = HOST+"food_project/DeleteSanPham_B_MASP.php/";
    public AdapterSanPhamADMIN(Context context, ArrayList<SanPham> listsp){
        this.context = context;
        this.listsp = listsp;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inf.inflate(R.layout.item_list_sanpham_admin, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPham sp = listsp.get(position);
        holder.tv_tensp.setText(sp.getTensp());
        holder.tv_soluong.setText(sp.getSoluong()+"");
        holder.tv_giatien.setText(sp.getGiatien()+"");
        Glide.with(context).load(HOST+"food_project/"+sp.getMotasp()).into(holder.imv_sanpham);


    }



    @Override
    public int getItemCount() {
        return listsp.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView imv_sanpham;
        public TextView tv_tensp;
        public TextView tv_soluong;
        public TextView tv_giatien;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_sanpham = itemView.findViewById(R.id.imv_sanpham);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            tv_giatien = itemView.findViewById(R.id.tv_giatien);
            tv_soluong = itemView.findViewById(R.id.tv_soluong);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SanPham sp = listsp.get(getAdapterPosition());
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_sanpham_admin);
                    TextView tv_tensp = dialog.findViewById(R.id.tv_tensp);
                    final EditText edt_soluong = dialog.findViewById(R.id.edt_soluong);
                    final EditText edt_gia = dialog.findViewById(R.id.edt_giatien);
                    Button btn_okie = dialog.findViewById(R.id.btn_okie);
                    Button btn_xoa = dialog.findViewById(R.id.btn_xoa);
                    Button btn_sua = dialog.findViewById(R.id.btn_sua);





                    tv_tensp.setText(sp.getTensp());
                    edt_gia.setText(sp.getGiatien()+"");
                    edt_soluong.setText(sp.getSoluong()+"");

                    btn_okie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });

                    btn_sua.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int masp = sp.getMasp();
                            int soluong = Integer.parseInt(edt_soluong.getText().toString());
                            float giatien = Float.parseFloat(edt_gia.getText().toString());


                            EditSanPham_B_MaSP(masp, soluong, giatien);

                            RecyclerView recyclerView = ((MainListSanPham)context).findViewById(R.id.recyclerV);
                            ((MainListSanPham)context).getAllSanPham(recyclerView);
                            dialog.cancel();
                        }

                    });

                    btn_xoa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SanPham sp = listsp.get(getAdapterPosition());
                            int masp = sp.getMasp();
                            DeleteSanPham_B_MASP(masp);
                            dialog.cancel();
                            RecyclerView recyclerView = ((MainListSanPham)context).findViewById(R.id.recyclerV);
                            ((MainListSanPham)context).getAllSanPham(recyclerView);
                        }
                    });

                    dialog.show();
                }
            });
        }
    }
    //Hàm sửa Sản Phẩm
    private void EditSanPham_B_MaSP(final int masp, final int soluong, final float giatien){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(context, "Sửa thành công ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("masp",masp+"");
                params.put("soluong", soluong+"");
                params.put("giatien", giatien+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
        }

        public void DeleteSanPham_B_MASP(final int masp){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkDeteleSanPham,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(context, "Xóa thành công ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "Xóa thất bại ", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Xóa thất bại ", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi đường link !!! ", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("masp", masp+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

        }
}
