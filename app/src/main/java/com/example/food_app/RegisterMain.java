package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterMain extends AppCompatActivity {
    EditText edt_email, edt_pass, edt_pass2,edt_hoten, edt_sdt, edt_diachi;
    Button btn_register, btn_login;
    String HOST= LoginMain.HOST;
    String link = HOST+"food_project/registerTK.php";
    String email, pass, pass2, hoten, sdt, diachi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        setTitle("REGISTER");


        edt_email = this.findViewById(R.id.edt_email);
        edt_pass = this.findViewById(R.id.edt_password);
        edt_pass2 = this.findViewById(R.id.edt_password2);
        edt_hoten = this.findViewById(R.id.edt_hoten);
        edt_sdt = this.findViewById(R.id.edt_sdt);
        edt_diachi = this.findViewById(R.id.edt_diachi);
        btn_register = this.findViewById(R.id.btn_register);
        btn_login = this.findViewById(R.id.btn_login);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_email.getText().toString();
                pass = edt_pass.getText().toString();
                pass2 = edt_pass2.getText().toString();
                hoten = edt_hoten.getText().toString();
                sdt = edt_sdt.getText().toString();
                diachi = edt_diachi.getText().toString();

                Validate(email, pass, pass2, hoten, sdt, diachi);
                RegisterTK(email, pass, hoten, sdt, diachi);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterMain.this, LoginMain.class);
                startActivity(intent);
            }
        });

    }
    private void Validate(String email, String pass, String pass2, String hoten, String sdt, String diachi){
        if(email.length() == 0 || pass.length() == 0 || pass2.length() == 0 || hoten.length() == 0 ||
                sdt.length() == 0 || diachi.length() == 0){
            Toast.makeText(this, "Không được để trống khoảng nào ! ", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!pass.equals(pass2)){
            Toast.makeText(this, "PassWord không trùng nhau ! ", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void RegisterTK(final String email, final String password, final String hoten, final String sdt, final String diachi){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(RegisterMain.this, "Đăng ký thành công ", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterMain.this, "Đăng ký thất bại !", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterMain.this, "Lỗi JSON !!!!", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterMain.this, "Lỗi đường link !!!! ", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("hoten", hoten);
                params.put("sdt", sdt+"");
                params.put("diachi", diachi);
                params.put("level", "USER");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(RegisterMain.this);
        requestQueue.add(stringRequest);
    }
}
