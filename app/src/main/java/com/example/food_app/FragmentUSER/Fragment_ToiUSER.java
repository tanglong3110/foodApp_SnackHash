package com.example.food_app.FragmentUSER;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food_app.LoginMain;
import com.example.food_app.R;

public class Fragment_ToiUSER extends Fragment {
    TextView tv_email, tv_tenUSER, tv_sdt, tv_diachi;
    String email = LoginMain.email;
    String ten = LoginMain.name;
    String sdt = LoginMain.sdtUSER;
    String diachi = LoginMain.diachiUSER;
    Button btnDX ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.toi_user_fragment, container, false);
        tv_email = view.findViewById(R.id.tv_email);
        tv_tenUSER = view.findViewById(R.id.tv_tenUSER);
        tv_sdt = view.findViewById(R.id.tv_sdt);
        tv_diachi = view.findViewById(R.id.tv_diachi);
        btnDX = view.findViewById(R.id.btnDX);

        tv_email.setText(email);
        tv_tenUSER.setText(ten);
        tv_diachi.setText(diachi);
        tv_sdt.setText(sdt);

        btnDX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),LoginMain.class);
                startActivity(i);
            }
        });
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
