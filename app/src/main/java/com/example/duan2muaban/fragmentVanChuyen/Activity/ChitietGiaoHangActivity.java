package com.example.duan2muaban.fragmentVanChuyen.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Activity.ShipperActivity;
import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFaceHoadon;
import com.example.duan2muaban.R;
import com.example.duan2muaban.adapter.hoadoncthd.CTHDAdapter;
import com.example.duan2muaban.model.CTHD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ChitietGiaoHangActivity extends AppCompatActivity {

    TextView tvMaHD, tvTenKH, tvDiaChi, tvSDT, tvTongTien, tvTinhTrang;
    RecyclerView recyclerView;
    List<CTHD> cthdList = new ArrayList<>();
    CTHDAdapter cthdAdapter;
    ApiInTerFaceHoadon apiInTerFaceHoadon;
    public static String mahd, tenkh, diachi, sdt, tongtien, tinhtrang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_giao_hang);

        recyclerView = findViewById(R.id.recyclerview_xacnhadonhang);

        tvMaHD = findViewById(R.id.tvmaHD);
        tvTenKH = findViewById(R.id.tvtenKH);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvSDT = findViewById(R.id.tvSDT);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvTinhTrang = findViewById(R.id.tvTinhTrang);
        Intent intent = getIntent();
        mahd = intent.getStringExtra("mahoadon");
        tenkh = intent.getStringExtra("tenkh");
        diachi = intent.getStringExtra("diachi");
        sdt = intent.getStringExtra("sdt");
        tongtien = intent.getStringExtra("tongtien");
        tinhtrang = intent.getStringExtra("tinhtrang");

        tvTenKH.setText(tenkh);
        tvDiaChi.setText(diachi);
        tvSDT.setText(sdt);
        tvTongTien.setText(tongtien);
        tvTinhTrang.setText(tinhtrang);

        cthdAdapter = new CTHDAdapter(this, cthdList);
        fetchcthdbymahd(mahd);
        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManagerVeticl);
        recyclerView.setHasFixedSize(true);

    }
    public void fetchcthdbymahd(String mahd){
        apiInTerFaceHoadon = ApiClient.getApiClient().create(ApiInTerFaceHoadon.class);
        Call<List<CTHD>> call = apiInTerFaceHoadon.get_cthd_bymahd(mahd);

        call.enqueue(new Callback<List<CTHD>>() {
            @Override
            public void onResponse(Call<List<CTHD>> call, retrofit2.Response<List<CTHD>> response) {
                //progressBar.setVisibility(View.GONE);
                cthdList= response.body();
                cthdAdapter = new CTHDAdapter(ChitietGiaoHangActivity.this,cthdList);
                recyclerView.setAdapter(cthdAdapter);
                cthdAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CTHD>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
}
