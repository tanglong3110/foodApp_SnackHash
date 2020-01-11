package com.example.food_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.food_app.Model.ScreenItems;
import com.example.food_app.PagerAdapter.IntroViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager screenPager;
    IntroViewPagerAdapter introViewPagerAdapter;
    ArrayList<ScreenItems> list;
    TabLayout tab_indicator;
    Button btn_next;
    int position = 0;
    Button btnGetstarted;
    Animation btnAnim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hàm này chỉ chảy 1 lần khi cài đặt app
        if(restorePrefData()){
            Intent mainActivity = new Intent(getApplicationContext(),LoginMain.class);
            startActivity(mainActivity);
            finish();
        }
        setContentView(R.layout.activity_main);

//        getSupportActionBar().hide();



        btn_next = findViewById(R.id.btn_next);
        screenPager = findViewById(R.id.view_pager);
        tab_indicator = findViewById(R.id.tab_indicator);
        btnGetstarted = findViewById(R.id.btn_Getstarted);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);



        list = new ArrayList<ScreenItems>();
        list.add(new ScreenItems("Fresh Food","", R.drawable.img1));
        list.add(new ScreenItems("Fast Delivery","", R.drawable.img2));
        list.add(new ScreenItems("Easy Payment","", R.drawable.img3));


        introViewPagerAdapter = new IntroViewPagerAdapter(MainActivity.this,list);
        screenPager.setAdapter(introViewPagerAdapter);
        tab_indicator.setupWithViewPager(screenPager);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position = screenPager.getCurrentItem();
                if(position < list.size()){
                    position++;
                    screenPager.setCurrentItem(position);
                }

                if(position == list.size()-1){
                    //TODO : show the GETSTARTED Button and hide the indicator and the next button
                    loaLastScreen();
                }
            }


        });
        tab_indicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == list.size()-1){
                    loaLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btnGetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginMain.class);
                startActivity(intent);

                savePrefsData();
//                finish();
            }
        });
    }
    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
        return isIntroActivityOpnendBefore;
    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpnend",true);
        editor.commit();
    }

    private void loaLastScreen() {
        btn_next.setVisibility(View.INVISIBLE);
        btnGetstarted.setVisibility(View.VISIBLE);
        tab_indicator.setVisibility(View.INVISIBLE);

        btnGetstarted.setAnimation(btnAnim);
    }
}
