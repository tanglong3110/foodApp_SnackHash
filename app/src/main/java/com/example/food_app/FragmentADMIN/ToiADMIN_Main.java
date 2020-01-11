package com.example.food_app.FragmentADMIN;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.food_app.LoginMain;
import com.example.food_app.R;

public class ToiADMIN_Main extends AppCompatActivity {
    TextView tv_email, tv_tenAMDIN, tv_sdt, tv_diachi;
    String email = LoginMain.email;
    String ten = LoginMain.name;
    String sdt = LoginMain.sdtUSER;
    String diachi = LoginMain.diachiUSER;
    Button btnDX;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toi_admin__main);
        setTitle("ADMIN");

        tv_email = ToiADMIN_Main.this.findViewById(R.id.tv_email);
        tv_tenAMDIN = ToiADMIN_Main.this.findViewById(R.id.tv_tenADMIN);
        tv_sdt = ToiADMIN_Main.this.findViewById(R.id.tv_sdt);
        tv_diachi = ToiADMIN_Main.this.findViewById(R.id.tv_diachi);
        btnDX = ToiADMIN_Main.this.findViewById(R.id.btnDX);

        tv_email.setText(email);
        tv_tenAMDIN.setText(ten);
        tv_sdt.setText(sdt);
        tv_diachi.setText(diachi);

        btnDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ToiADMIN_Main.this, LoginMain.class);
                startActivity(i);
            }
        });
    }
}
