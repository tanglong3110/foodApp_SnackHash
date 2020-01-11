package com.example.food_app.MyAdapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
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

public class AdapterSanPhamUSER extends RecyclerView.Adapter<AdapterSanPhamUSER.MyViewHolder> {
    ArrayList<SanPham> list;
    Context context;
    String HOST = LoginMain.HOST;
    String email = LoginMain.email;
    String linkGetMaHD = HOST+"food_project/GetHoaDon_B_TRANGTHAI_EMAIL.php/";
    String linkAddHDCT = HOST+"food_project/AddHDCT.php/";
    int MAHD =0   ;
    int soluong =0;
    int MASP = 0;
    float giatien = 0 ;
    

    public AdapterSanPhamUSER(Context context, ArrayList<SanPham> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_list_sanpham_user, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SanPham sp = list.get(position);
        holder.tv_giatien.setText(sp.getGiatien()+"");
        holder.tv_tensp.setText(sp.getTensp());
        Glide.with(context).load(HOST+"food_project/"+sp.getMotasp()).into(holder.imv_sanpham);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imv_sanpham;
        TextView tv_tensp;
        TextView tv_giatien;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imv_sanpham = itemView.findViewById(R.id.imv_sanpham);
            tv_tensp = itemView.findViewById(R.id.tv_tensp);
            tv_giatien = itemView.findViewById(R.id.tv_giatien);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GetHOADON_MaHD(email);
                    final SanPham sp = list.get(getAdapterPosition());
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_add_hdct);
                    TextView tv_tensp = dialog.findViewById(R.id.tv_tensp);
                    final EditText edt_soluong = dialog.findViewById(R.id.edt_soluong);
                    Button btn_okie = dialog.findViewById(R.id.btn_okie);
                    Button btn_xoa = dialog.findViewById(R.id.btn_xoa);

                    tv_tensp.setText(""+sp.getTensp());

                    MASP = sp.getMasp();




                    btn_okie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            soluong = Integer.parseInt(edt_soluong.getText().toString());
                            giatien = soluong * sp.getGiatien();
                            MASP = sp.getMasp();
                            AddHDCT(MAHD, MASP, soluong, giatien);
                            dialog.cancel();
                        }
                    });

                    btn_xoa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                        }
                    });


                    dialog.show();

                }
            });
        }
    }



    public void GetHOADON_MaHD(final String email){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkGetMaHD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hoadon");
                            for(int i =0 ; i< jsonArray.length(); i++){
                                JSONObject hoadon = jsonArray.getJSONObject(i);
                                int mahd = hoadon.getInt("mahd");
                                String emai = hoadon.getString("email");
                                String ngay = hoadon.getString("ngay");
                                String trangthai = hoadon.getString("trangthai");
                                MAHD = mahd;

                            }
                            if (success.equals("EXIST")){
                                Toast.makeText(context, "Tìm thấy hóa đơn của bạn " + MAHD, Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(context, "Bạn đã có hóa đơn mới "+ MAHD, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Lồi dữ liệu !!!", Toast.LENGTH_LONG).show();
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
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void AddHDCT(final int MAHD, final int MASP, final int soluong, final float giatien){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkAddHDCT,
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
                params.put("mahd", MAHD+"");
                params.put("masp", MASP+"");
                params.put("soluong", soluong+"");
                params.put("giatien", giatien+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }


}
