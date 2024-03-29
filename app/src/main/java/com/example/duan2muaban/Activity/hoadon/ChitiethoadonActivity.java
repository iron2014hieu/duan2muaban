package com.example.duan2muaban.Activity.hoadon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFaceHoadon;
import com.example.duan2muaban.R;
import com.example.duan2muaban.adapter.hoadoncthd.CTHDAdapter;
import com.example.duan2muaban.model.CTHD;
import com.example.duan2muaban.nighmode_vanchuyen.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ChitiethoadonActivity extends AppCompatActivity {
    RecyclerView recyclerView_cthd;
    List<CTHD> cthdList = new ArrayList<>();
    CTHDAdapter cthdAdapter;
    ApiInTerFaceHoadon apiInTerFaceHoadon;
    public static String mahd, tinhtrang;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_chitiethoadon);
        Anhxa();
        Intent intent = getIntent();
        mahd = intent.getStringExtra("mahd");

        cthdAdapter = new CTHDAdapter(this, cthdList);

        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_cthd.setLayoutManager(gridLayoutManagerVeticl);
        recyclerView_cthd.setHasFixedSize(true);

        fetchcthdbymahd(mahd);
    }
    //settheme
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
    public void fetchcthdbymahd(String mahd){
        apiInTerFaceHoadon = ApiClient.getApiClient().create(ApiInTerFaceHoadon.class);
        Call<List<CTHD>> call = apiInTerFaceHoadon.get_cthd_bymahd(mahd);

        call.enqueue(new Callback<List<CTHD>>() {
            @Override
            public void onResponse(Call<List<CTHD>> call, retrofit2.Response<List<CTHD>> response) {
                //progressBar.setVisibility(View.GONE);
                cthdList= response.body();
                cthdAdapter = new CTHDAdapter(ChitiethoadonActivity.this,cthdList);
                recyclerView_cthd.setAdapter(cthdAdapter);
                cthdAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CTHD>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    private void Anhxa(){
       recyclerView_cthd = findViewById(R.id.recyclerview_cthd);
    }
}
