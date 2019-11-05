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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.SachAdapter;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.nighmode.SharedPref;
import com.example.duan2muaban.publicString.URL.UrlSql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class GetBookByTheloaiActivity extends AppCompatActivity {
    SharedPref sharedPref;
    UrlSql urlSql = new UrlSql();
    String matheloai;
    public String URL_GETDATABYMATHELOAI;

    private List<Books> listBook =new ArrayList<>();
    private RecyclerView recyclerView;
    private CircleImageView circleImageView;
    private TextView textViewTB;

    private SessionManager sessionManager;
    String tentheloai;

    SachAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_get_book_by_theloai);
        Toolbar toolbar = findViewById(R.id.toolbar_theloai);
        ActionBar actionBar = getSupportActionBar();


        recyclerView=findViewById(R.id.recyclerview_book_bymatheloai);
        textViewTB=findViewById(R.id.txtThongbaonull);
        sessionManager = new SessionManager(this);
        try {
            HashMap<String,String> user = sessionManager.getUserDetail();
            String quyen = user.get(sessionManager.QUYEN);
//            Toast.makeText(GetBookByTheloaiActivity.this, ""+quyen, Toast.LENGTH_SHORT).show();
            if (quyen==null){

            }else if (quyen.equals("user")){

            }
            else {
                circleImageView.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            Log.e("LOG", e.toString());
        }

        recyclerViewAdapter = new SachAdapter(this, listBook);

        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManagerVeticl);
        recyclerView.setAdapter(recyclerViewAdapter);

        HashMap<String,String> theloai = sessionManager.getMAtheloai();
        matheloai = theloai.get(sessionManager.MATHELOAI);
        tentheloai = theloai.get(sessionManager.TEN_THELOAI);
        String tenTheloai = theloai.get(sessionManager.TEN_THELOAI);

        toolbar.setTitle("Thể loại "+tenTheloai);
        URL_GETDATABYMATHELOAI = urlSql.URL_GETDATA_BY_MATHELOAI+matheloai;
        GetData(URL_GETDATABYMATHELOAI);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
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

                sessionManager.createSessionSendInfomationBook(masach,tensach,manxb,matheloai,ngayxb,noidung,anhbia,gia,tennxb,soluong,tacgia);
                Toast.makeText(GetBookByTheloaiActivity.this, ""+masach, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(GetBookByTheloaiActivity.this, BookDetailActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));





    }
    public void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(GetBookByTheloaiActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listBook.clear();
//                        if (response.length() > 0){
//                            recyclerView.setVisibility(View.VISIBLE);
//                            textViewTB.setVisibility(View.GONE);
//                        }else{
//                            recyclerView.setVisibility(View.GONE);
//                            textViewTB.setVisibility(View.VISIBLE);
//                            try {
//                                HashMap<String,String> user = sessionManager.getUserDetail();
//                                String quyen = user.get(sessionManager.QUYEN);
//                                if (quyen.equals("store")||quyen.equals("admin")){
//                                    textViewTB.setText("Chưa có sách trong thể loại "+tentheloai+ ", bạn hãy thêm để chúng xuất hiện ở đây!");
//                                }else {
//                                    textViewTB.setText("Chưa có sách trong thể loại "+tentheloai+ ", vui lòng quay lại sau!");
//                                }
//
//                            }catch (Exception e){
//                                Log.e("LOG", e.toString());
//                            }
//                        }
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listBook.add(new Books(
                                        object.getInt("masach"),
                                        object.getString("tensach"),
                                        object.getInt("manxb"),
                                        object.getInt("matheloai"),
                                        object.getString("ngayxb"),
                                        object.getString("noidung"),
                                        object.getString("anhbia"),
                                        object.getInt("gia"),
                                        object.getString("tennxb"),
                                        object.getInt("soluong"),
                                        object.getString("tacgia")
                                ));
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(GetBookByTheloaiActivity.this, "loi e: "+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        recyclerViewAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GetBookByTheloaiActivity.this,"error: "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
}
