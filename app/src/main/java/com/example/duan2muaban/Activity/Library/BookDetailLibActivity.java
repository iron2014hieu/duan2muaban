package com.example.duan2muaban.Activity.Library;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFace;
import com.example.duan2muaban.CartDetailActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.Sach.SachAdapter;
import com.example.duan2muaban.model.Books;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class BookDetailLibActivity extends AppCompatActivity {
    String masach, linkbook,hinhanh, tensach, tongdiem, landanhgia;

    ImageView imgBook_lib;
    TextView txtTensach_lib,numrating_book_detail_lib,txtDocsach,txtXemnhanxet;
    RatingBar ratingbar_book_detail_lib;
    String URL ="https://bansachonline.xyz/bansach/sach/getBookDetail.php/?masach=";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_lib);
        addcontrols();
        sessionManager = new SessionManager(this);
        Intent intent = getIntent();
        masach = intent.getStringExtra("masach");
        getDetailBook(URL+masach);

    }
    public void getDetailBook(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetailLibActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            try {
                                JSONObject object = response.getJSONObject(0);
                                tensach = object.getString("tensach");
                                linkbook = object.getString("linkbook");
                                hinhanh = object.getString("anhbia");
                                tongdiem = object.getString("tongdiem");
                                landanhgia = object.getString("landanhgia");

                                sessionManager.createGuiLinkBook(tensach, linkbook);
                                txtTensach_lib.setText(tensach);
                                Picasso.with(BookDetailLibActivity.this).load(hinhanh).into(imgBook_lib);
                                numrating_book_detail_lib.setText(landanhgia);
                                ratingbar_book_detail_lib.setRating(Float.valueOf(tongdiem)/Float.valueOf(landanhgia));
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(BookDetailLibActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                            }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookDetailLibActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void DocSach(View view){
        Intent intent = new Intent(getBaseContext(), ViewBookActivity.class);
        startActivity(intent);
    }
    private void addcontrols() {
        imgBook_lib = findViewById(R.id.imgBook_lib_acti);
        txtTensach_lib = findViewById(R.id.txtTensach_lib);
        numrating_book_detail_lib= findViewById(R.id.numrating_book_detail_lib);
        txtDocsach= findViewById(R.id.txtDocsach);
        txtXemnhanxet= findViewById(R.id.txtXemnhanxet);
        ratingbar_book_detail_lib = findViewById(R.id.ratingbar_book_detail_lib);
    }
}
