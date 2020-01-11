package com.example.food_app.FragmentADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.HoaDon;
import com.example.food_app.MyAdapter.Adapter_HoaDon_TRANGTHAI_DONE_ADMIN;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_ListHoaDon_DONE extends AppCompatActivity {
    String HOST = LoginMain.HOST;
    String link_GetAllHoaDon_TRANGTHAI_DONE = HOST + "food_project/GetAllHoaDon_TRANGTHAI_DONE.php";
    Adapter_HoaDon_TRANGTHAI_DONE_ADMIN adapter;
    ArrayList<HoaDon> listHD;
    RecyclerView recyclerV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__list_hoa_don__done);
        setTitle("Hóa đơn đã thanh toán");
        recyclerV = findViewById(R.id.recyclerV);
        GetAllHoaDon_TRANGTHAI_DONE(recyclerV);
    }

    public void GetAllHoaDon_TRANGTHAI_DONE(final RecyclerView recyclerV){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link_GetAllHoaDon_TRANGTHAI_DONE,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            listHD = new ArrayList<HoaDon>();
                            JSONArray jsonArray = response.getJSONArray("hoadon");
                            for (int i =0; i< jsonArray.length(); i++) {
                                JSONObject hoadon = jsonArray.getJSONObject(i);
                                int mahd = hoadon.getInt("mahd");
                                String email = hoadon.getString("email");
                                String ngay = hoadon.getString("ngay");
                                String trangthai = hoadon.getString("trangthai");

                                HoaDon hd = new HoaDon(mahd, email, ngay, trangthai);
                                listHD.add(hd);
                            }
                            adapter = new Adapter_HoaDon_TRANGTHAI_DONE_ADMIN(Main_ListHoaDon_DONE.this, listHD);
                            LinearLayoutManager manager = new LinearLayoutManager
                                    (Main_ListHoaDon_DONE.this, RecyclerView.VERTICAL,true);
                            recyclerV.setLayoutManager(manager);
                            recyclerV.setAdapter(adapter);
                            System.out.println(listHD.size());
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
            RequestQueue requestQueue = Volley.newRequestQueue(Main_ListHoaDon_DONE.this);
            requestQueue.add(jsonObjectRequest);

    }
}
