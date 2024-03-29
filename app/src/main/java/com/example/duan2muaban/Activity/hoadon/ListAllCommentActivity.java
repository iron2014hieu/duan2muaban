package com.example.duan2muaban.Activity.hoadon;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.duan2muaban.Activity.BookDetailActivity;
import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFaceHoadon;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.NhanxetAdapter;
import com.example.duan2muaban.adapter.hoadoncthd.CTHDAdapter;
import com.example.duan2muaban.model.CTHD;
import com.example.duan2muaban.nighmode_vanchuyen.SharedPref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ListAllCommentActivity extends AppCompatActivity {
    List<CTHD> listComment = new ArrayList<>();
    NhanxetAdapter nhanxetAdapter;
    RecyclerView recyclerView_allcm;
    SharedPref sharedPref;
    ApiInTerFaceHoadon apiInTerFaceHoadon;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_list_all_comment);
        sessionManager = new SessionManager(this);
        Toolbar toolbar = findViewById(R.id.toolbar_allcm);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        recyclerView_allcm = findViewById(R.id.recyclerview_all_comment);
        StaggeredGridLayoutManager gridLayoutManagerVeticl1 =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_allcm.setLayoutManager(gridLayoutManagerVeticl1);
        recyclerView_allcm.setHasFixedSize(true);

        HashMap<String,String> books = sessionManager.getBookDetail();
        String masach = books.get(sessionManager.MASACH);
        fetchNhanxet(masach);
    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true;
    }

    public void fetchNhanxet(String key){
        apiInTerFaceHoadon = ApiClient.getApiClient().create(ApiInTerFaceHoadon.class);
        Call<List<CTHD>> call = apiInTerFaceHoadon.get_all_cthd(key);

        call.enqueue(new Callback<List<CTHD>>() {
            @Override
            public void onResponse(Call<List<CTHD>> call, retrofit2.Response<List<CTHD>> response) {
                //progressBar.setVisibility(View.GONE);
                listComment= response.body();
                nhanxetAdapter = new NhanxetAdapter(ListAllCommentActivity.this,listComment);
                recyclerView_allcm.setAdapter(nhanxetAdapter);
                nhanxetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<CTHD>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
}
