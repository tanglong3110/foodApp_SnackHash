package com.example.food_app.PagerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.food_app.Model.ScreenItems;
import com.example.food_app.R;

import java.util.ArrayList;

public class IntroViewPagerAdapter extends PagerAdapter {
    public Context context;
    ArrayList<ScreenItems> listScreens;

    public IntroViewPagerAdapter(Context context, ArrayList<ScreenItems> listScreens) {
        this.context = context;
        this.listScreens = listScreens;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.layout_screen,container, false);

        ImageView imgSlide = layoutScreen.findViewById(R.id.imv_intro);
        TextView tv_title = layoutScreen.findViewById(R.id.tv_intro_title);
        TextView tv_description = layoutScreen.findViewById(R.id.tv_intro_description);

        tv_title.setText(listScreens.get(position).getTitle());
        tv_description.setText(listScreens.get(position).getDescription());
        imgSlide.setImageResource(listScreens.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        return listScreens.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
