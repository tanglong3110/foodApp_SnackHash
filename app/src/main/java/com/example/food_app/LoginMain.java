package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginMain extends AppCompatActivity {
    TextView tv_signin;
    public static String HOST = "http://192.168.1.8:8181/";
    EditText edt_email, edt_pass;
    Button btn_login;
    public static String email, pass;
    CheckBox checkBox;
    public static String name, sdtUSER, diachiUSER;
    String link =HOST + "food_project/loginTK.php/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);
        setTitle("LOGIN");
        tv_signin = findViewById(R.id.tv_signin);
        edt_email = LoginMain.this.findViewById(R.id.edt_email);
        edt_pass = LoginMain.this.findViewById(R.id.edt_password);
        btn_login = LoginMain.this.findViewById(R.id.btn_login);
        checkBox = LoginMain.this.findViewById(R.id.checkBox);

        String text = "Do you have an account? <font color=#18a3fe>SIGN UP</font>";
        tv_signin.setText(Html.fromHtml(text));

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginMain.this, RegisterMain.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = edt_email.getText().toString();
                pass = edt_pass.getText().toString();

                Validate(email, pass);
                SaveSharedPreferences();
                email = Login(email, pass);


            }
        });

        GetSharedPreferences();
    }

    private void Validate(String email, String pass){
        if(email.length() == 0 || pass.length() == 0 ){
            Toast.makeText(this, "Không được để trống khoảng nào !", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void SaveSharedPreferences()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("mydata",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String user = edt_email.getText().toString();
        String pass = edt_pass.getText().toString();
        boolean chk = checkBox.isChecked();
        if(!chk)
        {
            editor.clear();
        }
        else
        {
            editor.putString("email",user);
            editor.putString("password",pass);
            editor.putBoolean("check",chk);
        }

        editor.commit();
    }
    private void GetSharedPreferences()
    {
        SharedPreferences pref = getSharedPreferences("mydata",MODE_PRIVATE);
        boolean chk = pref.getBoolean("check",false);
        if(chk)
        {
            String user = pref.getString("email","");
            String pass = pref.getString("password","");
            edt_email.setText(user);
            edt_pass.setText(pass);
        }
        checkBox.setChecked(chk);
    }

    private String Login(final String email, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            String success = json.getString("success");
                            JSONArray jsonArray = json.getJSONArray("taikhoan");
                            if(success.equals("1")){
                                for(int i = 0 ; i< jsonArray.length() ; i++){
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String email = jsonObject.getString("email");
                                    String password = jsonObject.getString("password");
                                    String hoten = jsonObject.getString("hoten");
                                    String sdt = jsonObject.getString("sdt");
                                    String diachi = jsonObject.getString("diachi");
                                    String level = jsonObject.getString("level");
                                    name = hoten;
                                    sdtUSER =sdt;
                                    diachiUSER = diachi;
                                    if(level.equals("USER")){
                                        Toast.makeText(LoginMain.this, "USER " + email, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginMain.this, UserMain.class);
                                        startActivity(intent);
                                    }
                                    else if(level.equals("ADMIN")){
                                        Toast.makeText(LoginMain.this, "ADMIN " + email, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginMain.this, ADMain.class);
                                        startActivity(intent);
                                    }
                                }

                            }
                            else {
                                Toast.makeText(LoginMain.this, "Sai tài khoản !", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginMain.this, "Sai tài khoản !", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginMain.this, "Lỗi đường link !", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }



        };
        RequestQueue requestQueue = Volley.newRequestQueue(LoginMain.this);
        requestQueue.add(stringRequest);
        return  email;
    }
}
