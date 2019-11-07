package com.example.duan2muaban.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
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
import com.example.duan2muaban.CartDetailActivity;
import com.example.duan2muaban.Main2Activity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.model.DatMua;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Callback;
import retrofit2.http.POST;

public class DatmuaActivity extends AppCompatActivity {

    EditText edtmaSp, edtTensp, edtGiasp, edtSL, edtTongtien;
    Button btnTangsl, btnGiamsp, btnDatmua;

    int giaBan = 1;
    private  int giaTri = 1;
    String URL_INSERT ="https://bansachonline.xyz/bansach/giohang/create_carts.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datmua);
        Toolbar toolbar = findViewById(R.id.toolbardm);
        ActionBar actionBar = getSupportActionBar();
        toolbar.setTitle("Đặt mua sản phẩm");

        Intent intent = getIntent();
        final String masach = intent.getStringExtra("masach");
        final String tensach = intent.getStringExtra("tensach");
        String giaban = intent.getStringExtra("gia");

        edtmaSp = findViewById(R.id.edtmaSanPhamKH);
        edtTensp = findViewById(R.id.edtSanPhamKH);
        edtGiasp = findViewById(R.id.edtGiasp);
        edtSL = findViewById(R.id.txtSL);
        edtTongtien = findViewById(R.id.edtTiensp);

        btnGiamsp = findViewById(R.id.btnGiamSL);
        btnTangsl = findViewById(R.id.btnThemSL);
        btnDatmua = findViewById(R.id.btnDatmuaSp);

        giaBan = Integer.valueOf(giaban);
        edtmaSp.setText(masach);
        edtTensp.setText(tensach );
        edtGiasp.setText(giaban);
        edtTongtien.setText(giaban);



        btnTangsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TangSL();
            }
        });
        btnGiamsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiamSL();
            }
        });

        btnDatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemDatmua(masach, tensach);
            }
        });
    }
    private void TangSL(){
        giaTri++;
        edtSL.setText(String.valueOf(giaTri));
        edtTongtien.setText(String.valueOf(giaBan*giaTri));
    }
    private void GiamSL(){
        if(giaTri>1){
            giaTri--;
            edtSL.setText(String.valueOf(giaTri));
            edtTongtien.setText(String.valueOf(giaBan*giaTri));
        }
    }
    private void ThemDatmua(final String masach, final String sp){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("tb")){
                            Toast.makeText(DatmuaActivity.this, "datontai", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("tc")){
                            Toast.makeText(DatmuaActivity.this, "success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DatmuaActivity.this, Main2Activity.class));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DatmuaActivity.this, "Loi roi nhe", Toast.LENGTH_SHORT).show();
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("masach", masach);
                params.put("sanpham", sp);
                params.put("gia", edtGiasp.getText().toString().trim());
                params.put("soluong", edtSL.getText().toString().trim());
                params.put("tongtien", edtTongtien.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}