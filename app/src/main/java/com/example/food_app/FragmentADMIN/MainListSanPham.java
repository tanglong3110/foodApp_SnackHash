package com.example.food_app.FragmentADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.SanPham;
import com.example.food_app.MyAdapter.AdapterSanPhamADMIN;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainListSanPham extends AppCompatActivity {
    RecyclerView recyclerV;
    ArrayList<SanPham> listsp;
    AdapterSanPhamADMIN adapter;
    String HOST = LoginMain.HOST;
    String link = HOST+"food_project/GetAllSanpham.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_san_pham);
        recyclerV = findViewById(R.id.recyclerV);
        listsp = new ArrayList<SanPham>();
        getAllSanPham(recyclerV);




    }
    public void getAllSanPham(final RecyclerView recyclerV){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("sanpham");
                            listsp = new ArrayList<SanPham>();
                            for(int i=0 ; i< jsonArray.length(); i++){
                                JSONObject sanpham = jsonArray.getJSONObject(i);
                                int masp = sanpham.getInt("masp");
                                String motasp = sanpham.getString("motasp");
                                String tensp = sanpham.getString("tensp");
                                String loaisp = sanpham.getString("loaisp");
                                int soluong = sanpham.getInt("soluong");
                                float giatien = (float) sanpham.getDouble("giatien");

                                SanPham sp = new SanPham(masp, motasp, tensp, loaisp, soluong, giatien );
                                listsp.add(sp);

                            }

                            adapter = new AdapterSanPhamADMIN(MainListSanPham.this, listsp);
                            GridLayoutManager manager = new GridLayoutManager(MainListSanPham.this,1);
                            recyclerV.setLayoutManager(manager);
                            recyclerV.setAdapter(adapter);
                            Log.d("dulieu" , listsp.size()+"");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });



        RequestQueue requestQueue = Volley.newRequestQueue(MainListSanPham.this);
        requestQueue.add(jsonObjectRequest);
    }
}
