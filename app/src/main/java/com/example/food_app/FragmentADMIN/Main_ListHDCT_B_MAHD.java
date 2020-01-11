package com.example.food_app.FragmentADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.HDCT;
import com.example.food_app.Model.HoaDon;
import com.example.food_app.MyAdapter.AdapterHDCT_WAITTING_ADMIN;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main_ListHDCT_B_MAHD extends AppCompatActivity {
    public static int MAHD;
    RecyclerView recyclerView;
    String HOST = LoginMain.HOST;
    ArrayList<HDCT> listhdct;
    String link= HOST+ "food_project/GetAllHDCT_B_MAHD_TRANGTHAI_WAITTING.php/";
    String link_updateHoaDon_TRANGTHAI_DONE_B_MAHD = HOST+ "food_project/UpdateHoaDon_TRANGTHAI_B_MAHD.php/";
    AdapterHDCT_WAITTING_ADMIN adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hdct_b_mahd);
        MAHD = getIntent().getExtras().getInt("MAHD");
        recyclerView = Main_ListHDCT_B_MAHD.this.findViewById(R.id.recycler_listhdct_b_mahd);
        UpdateHoaDon_TRANGTHAI_DONE_B_MAHD(MAHD);

    }

    @Override
    protected void onResume() {
        super.onResume();
        GetAllHDCT_B_MAHD_TRANGTHAI_WAITTING(MAHD, recyclerView);
    }

    public void GetAllHDCT_B_MAHD_TRANGTHAI_WAITTING(final int mahd, final RecyclerView recyclerView){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            listhdct = new ArrayList<HDCT>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("hdct");
                            for(int i = 0 ; i < jsonArray.length(); i++){
                                JSONObject hdct = jsonArray.getJSONObject(i);
                                int mahdct = hdct.getInt("mahdct");
                                int mahd = hdct.getInt("mahd");
                                int masp = hdct.getInt("masp");
                                int soluong = hdct.getInt("soluong");
                                float giatien = (float) hdct.getDouble("giatien");
                                String trangthai = hdct.getString("trangthai");

                                HDCT hdCT = new HDCT(mahdct, mahd, masp, soluong, giatien,trangthai);
                                listhdct.add(hdCT);
                            }

                            if(listhdct.size()>0) {
                                GridLayoutManager manager = new GridLayoutManager(Main_ListHDCT_B_MAHD.this, 1);
                                adapter = new AdapterHDCT_WAITTING_ADMIN(Main_ListHDCT_B_MAHD.this, listhdct);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                    Toast.makeText(Main_ListHDCT_B_MAHD.this, "LIST 0", Toast.LENGTH_SHORT).show();
                                    System.out.println("LIST 0");
                                    finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Main_ListHDCT_B_MAHD.this, "lỗi dữ liệu"+ e.toString(), Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_ListHDCT_B_MAHD.this, "lỗi đường link", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mahd", mahd+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Main_ListHDCT_B_MAHD.this);
        requestQueue.add(stringRequest);

    }

    public void UpdateHoaDon_TRANGTHAI_DONE_B_MAHD(final int MAHD){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link_updateHoaDon_TRANGTHAI_DONE_B_MAHD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mahd", MAHD+"");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Main_ListHDCT_B_MAHD.this);
        requestQueue.add(stringRequest);
    }









}
