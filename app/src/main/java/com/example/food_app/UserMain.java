package com.example.food_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.food_app.FragmentUSER.Fragment_Home;
import com.example.food_app.FragmentUSER.Fragment_ThongBao;
import com.example.food_app.FragmentUSER.Fragment_ToiUSER;
import com.example.food_app.FragmentUSER.GioHangMain;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserMain extends AppCompatActivity {
    BottomNavigationView bot_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        setTitle("USER");
        bot_nav = findViewById(R.id.bot_nav_user);
        bot_nav.setOnNavigationItemSelectedListener(navItemSelected);


        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                    new Fragment_Home()).commit();
            bot_nav.setSelectedItemId(R.id.nav_home);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navItemSelected =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case (R.id.nav_home):
                        {
                            selectedFragment = new Fragment_Home();
                            break;
                        }
                        case (R.id.nav_thongbao):
                        {
                            selectedFragment = new Fragment_ThongBao();
                            break;
                        }
                        case (R.id.nav_toi_user):
                        {
                            selectedFragment = new Fragment_ToiUSER();
                            break;
                        }
                        default:
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.optionmenu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.item_shop):
            {
                Intent intent = new Intent(UserMain.this, GioHangMain.class);
                startActivity(intent);
                break;
            }
            default:
        }
        return true;
    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
}


