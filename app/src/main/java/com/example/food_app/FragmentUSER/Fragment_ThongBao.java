package com.example.food_app.FragmentUSER;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.food_app.R;

public class Fragment_ThongBao extends Fragment {
    ListView listView;
    String []mang = new String[]{
            "Bóng đá",
            "Thời trang",
            "Ẩm thực",
            "Làm đẹp",
            "phim",
            "Giáo dục - du học",
            "Bạn trẻ - cuộc sống",
            "Ca nhạc - MTV",
            "Thể thao"
    };
    String []diachi = new String[]{
            "https://www.24h.com.vn/upload/rss/bongda.rss",
            "https://www.24h.com.vn/upload/rss/thoitrang.rss",
            "https://www.24h.com.vn/upload/rss/amthuc.rss",
            "https://www.24h.com.vn/upload/rss/lamdep.rss",
            "https://www.24h.com.vn/upload/rss/phim.rss",
            "https://www.24h.com.vn/upload/rss/giaoducduhoc.rss",
            "https://www.24h.com.vn/upload/rss/bantrecuocsong.rss",
            "https://www.24h.com.vn/upload/rss/canhacmtv.rss",
            "https://www.24h.com.vn/upload/rss/thethao.rss"
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongbao_fragment,container, false);
        listView = view.findViewById(R.id.lv);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mang);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(), MainDocBao.class);
                i.putExtra("link",diachi[position]);
                startActivity(i);
            }
        });
        return view;
    }




//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser){
//            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
//        }
//    }
}
