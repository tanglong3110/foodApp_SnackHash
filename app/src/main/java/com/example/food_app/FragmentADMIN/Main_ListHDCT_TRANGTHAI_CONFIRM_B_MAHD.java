package com.example.food_app.FragmentADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.HDCT;
import com.example.food_app.MyAdapter.AdapterHDCT_TRANGTHAI_CONFIRM;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main_ListHDCT_TRANGTHAI_CONFIRM_B_MAHD extends AppCompatActivity {
    TextView tv_tongtien;
    RecyclerView recyclerView;
    ArrayList<HDCT> list ;
    String HOST = LoginMain.HOST;
    String link = HOST+"food_project/GetAllHDCT_TRANGTHAI_CONFIRM_B_MAHD.php";

    AdapterHDCT_TRANGTHAI_CONFIRM adapter;

    int MAHD ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hdct_trangthai_confirm_b_mahd);
        MAHD = getIntent().getExtras().getInt("MAHD");
        setTitle("Hóa đơn "+ MAHD);
        tv_tongtien = findViewById(R.id.tv_tongtien);
        recyclerView = findViewById(R.id.recyclerView);

        GetAllHDCT_TRANGTHAI_CONFIRM_B_MAHD(recyclerView);
    }

    public void GetAllHDCT_TRANGTHAI_CONFIRM_B_MAHD(final RecyclerView recyclerView){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            list = new ArrayList<HDCT>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("hdct");
                            for (int i = 0 ; i < jsonArray.length(); i++){
                                JSONObject hdct = jsonArray.getJSONObject(i);
                                int mahdct = hdct.getInt("mahdct");
                                int mahd = hdct.getInt("mahd");
                                int masp = hdct.getInt("masp");
                                int soluong = hdct.getInt("soluong");
                                float giatien = (float) hdct.getDouble("giatien");
                                String trangthai = hdct.getString("trangthai");

                                HDCT hdct1 = new HDCT(mahdct, mahd, masp, soluong, giatien, trangthai);
                                list.add(hdct1);
                            }
                            float tongtien = 0 ;
                            for (int i = 0 ; i < list.size(); i++){
                                HDCT hdct = list.get(i);
                                float giatien = hdct.getGiatien();
                                tongtien += giatien;
                                tv_tongtien.setText(tongtien+" VND");
                            }

                            adapter = new AdapterHDCT_TRANGTHAI_CONFIRM(Main_ListHDCT_TRANGTHAI_CONFIRM_B_MAHD.this, list);
                            LinearLayoutManager manager = new LinearLayoutManager(Main_ListHDCT_TRANGTHAI_CONFIRM_B_MAHD.this,
                                    RecyclerView.VERTICAL,
                                    false);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                param.put("mahd", MAHD+"");
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Main_ListHDCT_TRANGTHAI_CONFIRM_B_MAHD.this);
        requestQueue.add(stringRequest);

    }
}
