package com.example.food_app.FragmentADMIN;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.food_app.LoginMain;
import com.example.food_app.MainActivity;
import com.example.food_app.Model.LoaiSP;
import com.example.food_app.MyAdapter.AdapterSpinnerLoaiSP;
import com.example.food_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_SanPham extends Fragment {
    String HOST = LoginMain.HOST;
    String linkGetAllLoaiSP = HOST+"food_project/GetAllLoaiSP.php/";
    String linkAddSP = HOST+"food_project/addSanPham.php/";

    ImageView imv;
    Button btn_chonhinh, btn_okie, btn_xemsp;
    EditText edt_ten, edt_soluong, edt_giatien;

    AdapterSpinnerLoaiSP adapterSpinnerLoaiSP;
    ArrayList<LoaiSP> listLoaiSP ;
    Spinner sp_loaisp;

    String img, tensp, loaisp;
    String soluong , giatien;

    Bitmap bitmap;

    private static final int PERMISSION_CODE =1000;
    private static final int IMG_PICK_CODE =1000;
    private static final int RESULT_OK =-1;

    public Fragment_SanPham() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sanpham_fragment,container,false);
        edt_ten = view.findViewById(R.id.edt_tensp);
        edt_soluong = view.findViewById(R.id.edt_soluong);
        edt_giatien = view.findViewById(R.id.edt_giatien);
        btn_chonhinh = view.findViewById(R.id.btn_chonhinh);
        btn_okie = view.findViewById(R.id.btn_okie);
        btn_xemsp = view.findViewById(R.id.btn_xemsp);
        sp_loaisp = view.findViewById(R.id.sp_loaisp);
        imv = view.findViewById(R.id.imv);

        getAllLoaiSP(sp_loaisp);

        sp_loaisp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int position = sp_loaisp.getSelectedItemPosition();
                LoaiSP loaiSP = listLoaiSP.get(position);
                loaisp = loaiSP.getLoaiSP();

                Toast.makeText(getContext(), ""+loaisp, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        btn_xemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "XEMSP", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MainListSanPham.class);
                startActivity(intent);
            }
        });

        btn_chonhinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                {
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
                    {
                        String[] permissons = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissons,PERMISSION_CODE);
                    }
                    else
                    {
                        pickImg();
                    }
                }
                else {
                    pickImg();
                }
            }

        });

        btn_okie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tensp = edt_ten.getText().toString();
                soluong = edt_soluong.getText().toString();
                giatien = edt_giatien.getText().toString();
                ValidateForm(tensp, loaisp, soluong, giatien);
                addSanPham(tensp, loaisp, soluong, giatien);

                edt_ten.setText(null);
                edt_giatien.setText(null);
                edt_soluong.setText(null);
                imv.setImageResource(R.drawable.img1);

            }
        });
        return view;
    }

    private void ValidateForm( String tensp, String loai, String soluong, String giatien){
        if(tensp.length() == 0 || loai.length() == 0 ||
                soluong.length() == 0 || giatien.length() == 0){
            Toast.makeText(getContext(), "Không được bỏ trống khoảng nào !!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void getAllLoaiSP(final Spinner sp_loaisp){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, linkGetAllLoaiSP,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      listLoaiSP = new ArrayList<LoaiSP>();
                        try {
                            JSONArray jsonArray = response.getJSONArray("loaisp");
                            for(int i= 0; i < jsonArray.length(); i++){
                                JSONObject loaisp = jsonArray.getJSONObject(i);
                                final String tenloai = loaisp.getString("loai");

                                LoaiSP loai = new LoaiSP(tenloai);
                                listLoaiSP.add(loai);
                            }
                            adapterSpinnerLoaiSP = new AdapterSpinnerLoaiSP(getContext(), listLoaiSP);
                            sp_loaisp.setAdapter(adapterSpinnerLoaiSP);



                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "Không lấy được ", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e. toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi đường link !! ", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void pickImg()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMG_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_CODE:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {
                    Toast.makeText(getContext(), "PERMISSION DENIED", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode==IMG_PICK_CODE)
        {
            Uri filepath = data.getData();

            try
            {
                InputStream inputStream = getContext().getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                imv.setImageBitmap(bitmap);
                Toast.makeText(getContext(),filepath.toString(), Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    private String imagetoString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream  = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgageBytes = byteArrayOutputStream.toByteArray();

        String encodedimg = Base64.encodeToString(imgageBytes,Base64.DEFAULT);
        return encodedimg;
    }

    private void addSanPham(final String tensp, final String loaisp, final String soluong, final String gia)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linkAddSP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(getContext(), "Thêm thành công !!!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            Log.d("loi", e.toString());

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi đường link", Toast.LENGTH_SHORT).show();
                        Log.d("loi", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String img =imagetoString(bitmap);
                Map<String, String> params = new HashMap<String, String>();
                params.put("img",img);
                params.put("tensp", tensp);
                params.put("loaisp", loaisp);
                params.put("soluong", soluong);
                params.put("giatien", gia);
                return params;
            }
        };
        RequestQueue requestQueue =Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}
