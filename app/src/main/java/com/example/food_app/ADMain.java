package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.food_app.FragmentADMIN.Fragment_QuanLy;
import com.example.food_app.FragmentADMIN.Fragment_SanPham;
import com.example.food_app.FragmentADMIN.Fragment_ThongKe;
import com.example.food_app.FragmentADMIN.ToiADMIN_Main;
import com.example.food_app.Model.HDCT;
import com.example.food_app.Model.HoaDon;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ADMain extends AppCompatActivity {
    BottomNavigationView bot_nav;
    public static String name = "Snack_Hack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admain);
        setTitle(name);
        bot_nav = findViewById(R.id.bot_nav);
        bot_nav.setOnNavigationItemSelectedListener(navSelect);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Fragment_QuanLy()).commit();
            bot_nav.setSelectedItemId(R.id.nav_quanly);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navSelect =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.nav_quanly: {
                            selectedFragment = new Fragment_QuanLy();
                            break;
                        }
                        case R.id.nav_sanpham: {
                            selectedFragment = new Fragment_SanPham();
                            break;
                        }
                        case R.id.nav_thongke: {
                            selectedFragment = new Fragment_ThongKe();
                            break;
                        }

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.optionmenu_ad, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.item_toi):
            {
                Toast.makeText(this, "Tôi", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ADMain.this, ToiADMIN_Main.class);
                startActivity(intent);
                break;
            }
            default:

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

}

