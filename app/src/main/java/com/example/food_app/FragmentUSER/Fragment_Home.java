package com.example.food_app.FragmentUSER;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.ImageHome;
import com.example.food_app.Model.SanPham;
import com.example.food_app.MyAdapter.AdapterIMV;
import com.example.food_app.MyAdapter.AdapterSanPhamUSER;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Home extends Fragment {
    String HOST = LoginMain.HOST;
    String link = HOST+"food_project/GetAllSanPham_B_LOAISP.php/";
    TextView tv_name;
//    RecyclerView rc_view;
    ArrayList<ImageHome> list;
    AdapterIMV adapter;
    String name = "";
    RecyclerView recycler_food, recycler_water, recycler_beer, recycler_milktea;
    ArrayList<SanPham> list_food, list_water, list_beer, list_milktea;

    LinearLayoutManager manager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container , false);
        name = LoginMain.name;
        tv_name = view.findViewById(R.id.tv_name);
//        rc_view = view.findViewById(R.id.recyclerView);
        recycler_food = view.findViewById(R.id.recycler_food);
        recycler_beer = view.findViewById(R.id.recycler_beer);
        recycler_milktea = view.findViewById(R.id.recycler_milktea);
        recycler_water = view.findViewById(R.id.recycler_water);
        list = new ArrayList<ImageHome>();



        tv_name.setText("WELLCOME "+name);
//        list.add(new ImageHome(R.drawable.imv_1, "HAMBURGER"));
//        list.add(new ImageHome(R.drawable.imv_2, "BRAZILIAN SOUL"));
//        list.add(new ImageHome(R.drawable.imv_3, "MUTHU'S CURRY"));
//        list.add(new ImageHome(R.drawable.imv_4, "VEGETARAIN"));
//        list.add(new ImageHome(R.drawable.imv_5, "FRIED CHICKEN"));
//        list.add(new ImageHome(R.drawable.imv_6, "HAMBURGER"));
//        adapter = new AdapterIMV(getContext(), list);
//
//
//        manager = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
//        rc_view.setLayoutManager(manager);
//        rc_view.setAdapter(adapter);

        GetAllSanPham_FOOD(recycler_food);
        GetAllSanPham_BEER(recycler_beer);
        GetAllSanPham_WATER(recycler_water);
        GetAllSanPham_MILKTEA(recycler_milktea);

        return view;
    }

    public void GetAllSanPham_FOOD(final RecyclerView recycler_food){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("sanpham");
                            list_food = new ArrayList<SanPham>();
                            for(int i = 0 ; i < jsonArray.length(); i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                int MaSP = json.getInt("masp");
                                String motasp = json.getString("motasp");
                                String tensp = json.getString("tensp");
                                String loaisp = json.getString("loaisp");
                                int soluong = json.getInt("soluong");
                                float giatien = (float) json.getDouble("giatien");

                                SanPham sp = new SanPham(MaSP, motasp, tensp, loaisp, soluong, giatien);
                                list_food.add(sp);
                            }
                            AdapterSanPhamUSER adapter_food = new AdapterSanPhamUSER(getContext(), list_food);
                            LinearLayoutManager manager_food = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recycler_food.setLayoutManager(manager_food);
                            recycler_food.setAdapter(adapter_food);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Lỗi dữ liệu !!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi đường link !!!!", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("loaisp", "FOOD");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    public void GetAllSanPham_BEER(final RecyclerView recycler_beer){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("sanpham");
                            list_beer = new ArrayList<SanPham>();
                            for(int i = 0 ; i < jsonArray.length(); i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                int MaSP = json.getInt("masp");
                                String motasp = json.getString("motasp");
                                String tensp = json.getString("tensp");
                                String loaisp = json.getString("loaisp");
                                int soluong = json.getInt("soluong");
                                float giatien = (float) json.getDouble("giatien");

                                SanPham sp = new SanPham(MaSP, motasp, tensp, loaisp, soluong, giatien);
                                list_beer.add(sp);
                            }
                            AdapterSanPhamUSER adapter_beer = new AdapterSanPhamUSER(getContext(), list_beer);
                            LinearLayoutManager manager_beer = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recycler_beer.setLayoutManager(manager_beer);
                            recycler_beer.setAdapter(adapter_beer);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Lỗi dữ liệu !!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi đường link !!!!", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("loaisp", "BEER");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }


    public void GetAllSanPham_WATER(final RecyclerView recycler_water){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("sanpham");
                            list_water = new ArrayList<SanPham>();
                            for(int i = 0 ; i < jsonArray.length(); i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                int MaSP = json.getInt("masp");
                                String motasp = json.getString("motasp");
                                String tensp = json.getString("tensp");
                                String loaisp = json.getString("loaisp");
                                int soluong = json.getInt("soluong");
                                float giatien = (float) json.getDouble("giatien");

                                SanPham sp = new SanPham(MaSP, motasp, tensp, loaisp, soluong, giatien);
                                list_water.add(sp);
                            }
                            AdapterSanPhamUSER adapter_water = new AdapterSanPhamUSER(getContext(), list_water);
                            LinearLayoutManager manager_water= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recycler_water.setLayoutManager(manager_water);
                            recycler_water.setAdapter(adapter_water);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Lỗi dữ liệu !!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi đường link !!!!", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("loaisp", "WATER");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }


    public void GetAllSanPham_MILKTEA(final RecyclerView recycler_milktea){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("sanpham");
                            list_milktea = new ArrayList<SanPham>();
                            for(int i = 0 ; i < jsonArray.length(); i++){
                                JSONObject json = jsonArray.getJSONObject(i);
                                int MaSP = json.getInt("masp");
                                String motasp = json.getString("motasp");
                                String tensp = json.getString("tensp");
                                String loaisp = json.getString("loaisp");
                                int soluong = json.getInt("soluong");
                                float giatien = (float) json.getDouble("giatien");

                                SanPham sp = new SanPham(MaSP, motasp, tensp, loaisp, soluong, giatien);
                                list_milktea.add(sp);
                            }
                            AdapterSanPhamUSER adapter_milktea = new AdapterSanPhamUSER(getContext(), list_milktea);
                            LinearLayoutManager manager_milktea = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                            recycler_milktea.setLayoutManager(manager_milktea);
                            recycler_milktea.setAdapter(adapter_milktea);
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Lỗi dữ liệu !!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi đường link !!!!", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("loaisp", "MILK_TEA");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
