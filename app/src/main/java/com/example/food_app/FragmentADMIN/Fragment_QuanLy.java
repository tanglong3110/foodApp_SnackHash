package com.example.food_app.FragmentADMIN;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.example.food_app.Model.HoaDon;
import com.example.food_app.MyAdapter.AdapterHoaDon_B_TRANGTHAI_USING;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_QuanLy extends Fragment {
    static RecyclerView recycler_listhd;
    Button btn_listhd_thanhtoan;

    String HOST = LoginMain.HOST;
    String link = HOST+"food_project/GetAllHoaDon_B_TRANGTHAI_USING.php/";
    public static ArrayList<HoaDon> list;
    AdapterHoaDon_B_TRANGTHAI_USING adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quanly_fragment,container,false);
        recycler_listhd = view.findViewById(R.id.recycler_listhd);
        btn_listhd_thanhtoan = view.findViewById(R.id.btn_listhd_thanhtoan);

        GetAllHoaDon_B_TRANGTHAI_USING(recycler_listhd);
        btn_listhd_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Main_ListHoaDon_DONE.class);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }



    public void GetAllHoaDon_B_TRANGTHAI_USING(final RecyclerView recycler_listhd ){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        list = new ArrayList<HoaDon>();
                        AdapterHoaDon_B_TRANGTHAI_USING adapter;
                        try {
                            JSONArray jsonArray = response.getJSONArray("hoadon");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject hoadon = jsonArray.getJSONObject(i);
                                int mahd = hoadon.getInt("mahd");
                                String email = hoadon.getString("email");
                                String ngay = hoadon.getString("ngay");
                                String trangthai = hoadon.getString("trangthai");


                                HoaDon hd = new HoaDon(mahd, email, ngay, trangthai);
                                list.add(hd);

                            }

                            adapter = new AdapterHoaDon_B_TRANGTHAI_USING(getContext(), list);
                            GridLayoutManager manager = new GridLayoutManager(getContext(),2);
                            recycler_listhd.setLayoutManager(manager);
                            recycler_listhd.setAdapter(adapter);
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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }




}
