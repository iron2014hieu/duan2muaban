package com.example.duan2muaban.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFace;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.Sach.SachAdapter;
import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.nighmode_vanchuyen.SharedPref;
import com.example.duan2muaban.publicString.URL.UrlSql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class GetBookByTheloaiActivity extends AppCompatActivity {
    SharedPref sharedPref;
    UrlSql urlSql = new UrlSql();
    String matheloai, tentheloai, matacgia, tentacgia, manxb, tennxb;
    public String URL_GETDATABYMATHELOAI;

    private List<Books> listBook =new ArrayList<>();
    private RecyclerView recyclerview_book;
    private TextView textViewTB;

    private ApiInTerFace apiInTerFace;
    private SessionManager sessionManager;
    SachAdapter sachAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_get_book_by_theloai);
        Toolbar toolbar = findViewById(R.id.toolbar_theloai);
        ActionBar actionBar = getSupportActionBar();


        recyclerview_book=findViewById(R.id.recyclerview_book_bymatheloai);
        textViewTB=findViewById(R.id.txtThongbaonull);
        sessionManager = new SessionManager(this);

        try {
            HashMap<String,String> user = sessionManager.getUserDetail();
            String quyen = user.get(sessionManager.QUYEN);
            if (quyen==null){

            }else if (quyen.equals("user")){

            }
            else {
            }
        }catch (Exception e){
            Log.e("LOG", e.toString());
        }

        sachAdapter = new SachAdapter(this, listBook);

        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerview_book.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_book.setHasFixedSize(true);

        try {
            //lấy tt thể loai
                Intent intent = getIntent();
                matheloai =  intent.getStringExtra("matheloai");
                tentheloai = intent.getStringExtra("tentheloai");

                manxb =  intent.getStringExtra("manxb");
                tennxb = intent.getStringExtra("tennxb");

                matacgia =  intent.getStringExtra("matacgia");
                tentacgia = intent.getStringExtra("tentacgia");
//            HashMap<String,String> theloai = sessionManager.getMAtheloai();
//            matheloai = theloai.get(sessionManager.MATHELOAI);
//            tentheloai = theloai.get(sessionManager.TEN_THELOAI);
//            HashMap<String,String> nxb = sessionManager.getMaNXB();
//            manxb = nxb.get(sessionManager.MANXB);
//            tennxb = nxb.get(sessionManager.TENNXB);
//
//            HashMap<String,String> tacgia = sessionManager.getMAtacgia();
//            matacgia = tacgia.get(sessionManager.MATACGIA);
//            tentacgia = tacgia.get(sessionManager.TENTACGIA);


            if (matheloai!= null) {
                fetchBookbymatheloai(matheloai);
                toolbar.setTitle("Thể loại "+tentheloai);
            } else if (matacgia!=null){
                fetchBookbyTacgia(matacgia);
                toolbar.setTitle("Tác giả "+tentacgia);
            } else if (manxb !=null){
                fetchBookbyNXB(manxb);
                toolbar.setTitle("Nhà xuất bản "+tennxb);
            }
        }catch (Exception e){
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }



        recyclerview_book.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerview_book, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Books books =   listBook.get(position);
                String masach = String.valueOf(books.getMasach());
                String tensach = String.valueOf(books.getTensach());
                String manxb = String.valueOf(books.getManxb());
                String matheloai = String.valueOf(books.getMatheloai());
                String ngayxb = books.getNgayxb();
                String noidung = books.getNoidung();
                String anhbia =books.getAnhbia();
                String gia = String.valueOf( books.getGia());
                String tennxb= String.valueOf(books.getTennxb());
                String soluong = String.valueOf(books.getSoluong());
                String tacgia = books.getTacgia();
                String matacgia = String.valueOf(books.getMatacgia());
                String tongdiem = String.valueOf(books.getTongdiem());
                String landanhgia = String.valueOf(books.getLandanhgia());

                sessionManager.createSessionSendInfomationBook(masach,tensach,manxb,matheloai,ngayxb,noidung,
                        anhbia,gia,tennxb,soluong,tacgia,matacgia, tongdiem, landanhgia);
                Toast.makeText(GetBookByTheloaiActivity.this, ""+masach, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(GetBookByTheloaiActivity.this, BookDetailActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }
    public void fetchBookbymatheloai(String maTheloai) {
        apiInTerFace = ApiClient.getApiClient().create(ApiInTerFace.class);
        Call<List<Books>> call = apiInTerFace.getBookbyMatheloai(maTheloai);

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, retrofit2.Response<List<Books>> response) {
                //progressBar.setVisibility(View.GONE);
                listBook= response.body();
                sachAdapter = new SachAdapter(GetBookByTheloaiActivity.this,listBook);
                recyclerview_book.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    public void fetchBookbyTacgia(String maTheloai) {
        apiInTerFace = ApiClient.getApiClient().create(ApiInTerFace.class);
        Call<List<Books>> call = apiInTerFace.getBookbyMatacgia(maTheloai);

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, retrofit2.Response<List<Books>> response) {
                //progressBar.setVisibility(View.GONE);
                listBook= response.body();
                sachAdapter = new SachAdapter(GetBookByTheloaiActivity.this,listBook);
                recyclerview_book.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    public void fetchBookbyNXB(String manxb) {
        apiInTerFace = ApiClient.getApiClient().create(ApiInTerFace.class);
        Call<List<Books>> call = apiInTerFace.getBookbyManxb(manxb);

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, retrofit2.Response<List<Books>> response) {
                //progressBar.setVisibility(View.GONE);
                listBook= response.body();
                sachAdapter = new SachAdapter(GetBookByTheloaiActivity.this,listBook);
                recyclerview_book.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
}
