package com.example.food_app.FragmentUSER;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.LoginMain;
import com.example.food_app.Model.HDCT;
import com.example.food_app.MyAdapter.AdapterHDCT_WAITTING_USER;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GioHangMain extends AppCompatActivity {
    public String EMAIL = LoginMain.email;
    String HOST = LoginMain.HOST;
    String link = HOST+"food_project/GetAllHDCT_B_EMAIL_TRANGTHAI_WAITTING.php/";
    ArrayList<HDCT> listHDCT;
    AdapterHDCT_WAITTING_USER adapter;
    public String tenUSER = LoginMain.name;
    public float tongtien = 0;

    public RecyclerView recyclerV;
    public TextView tv_tenUSER, tv_tonggia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang_main);
        recyclerV = GioHangMain.this.findViewById(R.id.recyclerV);
        tv_tenUSER = GioHangMain.this.findViewById(R.id.tv_tenUSER);
        tv_tonggia = GioHangMain.this.findViewById(R.id.tv_tongtien);

        tv_tenUSER.setText(tenUSER);
        GetALLHDCT_B_EMAIL_TRANGTHAI_WAITTING(EMAIL, recyclerV);

    }

    public void GetALLHDCT_B_EMAIL_TRANGTHAI_WAITTING(final String EMAIL, final RecyclerView recyclerV){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listHDCT = new ArrayList<HDCT>();
                        try {
                            tongtien = 0;
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("hdct");
                            for (int i = 0 ;i < jsonArray.length(); i++){
                                JSONObject hdct = jsonArray.getJSONObject(i);
                                int mahdct = hdct.getInt("mahdct");
                                int mahd = hdct.getInt("mahd");
                                int masp = hdct.getInt("masp");
                                int soluong = hdct.getInt("soluong");
                                float giatien = (float) hdct.getDouble("giatien");
                                String trangthai = hdct.getString("trangthai");


                                HDCT hdCT = new HDCT(mahdct, mahd, masp, soluong, giatien, trangthai);
                                listHDCT.add(hdCT);
                            }

                           GetTongTien(listHDCT, tongtien, tv_tonggia);


                            adapter = new AdapterHDCT_WAITTING_USER(GioHangMain.this, listHDCT);
                            GridLayoutManager manager = new GridLayoutManager(GioHangMain.this,1);
                            recyclerV.setLayoutManager(manager);
                            recyclerV.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(GioHangMain.this, "Lỗi dữ liệu !!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GioHangMain.this, "Lỗi đường link  !!!", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", EMAIL);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(GioHangMain.this);
        queue.add(stringRequest);
    }

    public void GetTongTien(ArrayList<HDCT> listHDCT, float tongtien, TextView tv_tonggia){
        for(int i = 0 ;i < listHDCT.size();i++ ){
            float giatien = listHDCT.get(i).getGiatien();
            tongtien += giatien;

        }
        tv_tonggia.setText(tongtien+ " VND");
    }
}
