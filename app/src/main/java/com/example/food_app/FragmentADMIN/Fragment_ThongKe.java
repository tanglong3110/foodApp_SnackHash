package com.example.food_app.FragmentADMIN;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.food_app.Model.LoaiSP;
import com.example.food_app.MyAdapter.AdapterSpinnerLoaiSP;
import com.example.food_app.MyAdapter.Adapter_HoaDon_TRANGTHAI_DONE_ADMIN;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_ThongKe extends Fragment {
    EditText edt_search;
    ImageView imv_search;
    RecyclerView recyclerView;
    String HOST = LoginMain.HOST;
    String link = HOST+"food_project/GetAllHoaDon_B_MONTHOFNGAY.php";

    Adapter_HoaDon_TRANGTHAI_DONE_ADMIN adapter;
    ArrayList<HoaDon> list;
    int thang = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongke_fragment,container,false);
        edt_search = view.findViewById(R.id.edt_search);
        imv_search = view.findViewById(R.id.imv_search);
        recyclerView = view.findViewById(R.id.recyclerView);

        imv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thang = Integer.parseInt(edt_search.getText().toString());
                if(thang>0 || thang<13) {
                    Toast.makeText(getContext(), "Tháng "+ thang, Toast.LENGTH_SHORT).show();
                    GetAllHoaDon_B_MONTHOFNGAY(thang, recyclerView);
                }


            }
        });
        return view;
    }
    public void GetAllHoaDon_B_MONTHOFNGAY(final int thang, final RecyclerView recyclerView) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        list = new ArrayList<HoaDon>();
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("hoadon");
                        for(int i=0 ; i< jsonArray.length(); i++){
                            JSONObject hoadon = jsonArray.getJSONObject(i);
                            int mahd = hoadon.getInt("mahd");
                            String email = hoadon.getString("email");
                            String ngay = hoadon.getString("ngay");
                            String trangthai = hoadon.getString("trangthai");

                            HoaDon hd = new HoaDon(mahd, email, ngay, trangthai);
                            list.add(hd);
                        }

                        adapter = new Adapter_HoaDon_TRANGTHAI_DONE_ADMIN(getContext(), list);
                        GridLayoutManager manager = new GridLayoutManager(getContext(),1);
                        recyclerView.setLayoutManager(manager);
                        recyclerView.setAdapter(adapter);
                        System.out.println(list.size());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Lỗi dữ liệu ", Toast.LENGTH_SHORT).show();
                        Log.d("loi", e.toString());
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "Lỗi dường link !!!", Toast.LENGTH_SHORT).show();
                    Log.d("loi", error.toString());
                }
            }) {
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            Map<String, String> param = new HashMap<String, String>();
            param.put("thang", thang+"");
            return param;
        }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }



}
