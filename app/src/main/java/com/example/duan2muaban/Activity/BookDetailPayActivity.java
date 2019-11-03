package com.example.duan2muaban.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.NhanxetAdapter;
import com.example.duan2muaban.model.Hoadon;
import com.example.duan2muaban.nighmode.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookDetailPayActivity extends AppCompatActivity {
    SharedPref sharedPref;
    private SessionManager sessionManager;
    String idBook, idBill, nameuser;
    private String URL_READ_DEATIL ="http://hieuttpk808.000webhostapp.com/books/sach/read_detail_book.php";
    private String URL_CHECK ="https://hieuttpk808.000webhostapp.com/books/cart_bill/check_danhgia.php";
    private TextView txtTensach_detail_view,txtChitiet_detail_view,txtTitledg, txtDown, txtUp;
    private Button btn_view_book;
    private String tongdiem, landanhgia, chitiet,hinhanh, tensach;
    private RatingBar ratingbar_book_view,ratingbarBook;
    private CardView cardview;

    RecyclerView recyclerview_danhgia;
    List<Hoadon> listNhanxet = new ArrayList<>();
    NhanxetAdapter nhanxetAdapter;

    String URL_GET_ALL ="https://hieuttpk808.000webhostapp.com/books/cart_bill/get_all_nhanxet.php/?masach=";
    String URL_GET_ALL_LIMIT ="https://hieuttpk808.000webhostapp.com/books/cart_bill/get_all_nhanxet_limit.php/?masach=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_book_detail_pay);
        AddControl();
        sessionManager = new SessionManager(this);

        btn_view_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookDetailPayActivity.this, ViewBookActivity.class));
            }
        });

        HashMap<String,String> user = sessionManager.getDetailBill();
        idBook = user.get(sessionManager.MASACH);
        nameuser = user.get(sessionManager.TEN_USER);
        idBill = user.get(sessionManager.ID_BILL);
        getDetailBookPay(idBook);


        CheckNhanxet(URL_CHECK,idBill);

        LayerDrawable stars = (LayerDrawable) ratingbarBook.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        ratingbarBook.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Intent intent = new Intent(getBaseContext(), RatingBookCommentActivity.class);
                intent.putExtra("NUMRATE", String.valueOf(v));
                startActivity(intent);
            }
        });

        nhanxetAdapter = new NhanxetAdapter(this, listNhanxet);
        recyclerview_danhgia.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_danhgia.setAdapter(nhanxetAdapter);
        Get_all_nhanxet(URL_GET_ALL_LIMIT);

        txtDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Get_all_nhanxet(URL_GET_ALL+idBook);
                txtDown.setVisibility(View.GONE);
                txtUp.setVisibility(View.VISIBLE);
            }
        });
        txtUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Get_all_nhanxet(URL_GET_ALL_LIMIT+idBook);
                txtDown.setVisibility(View.VISIBLE);
                txtUp.setVisibility(View.GONE);
            }
        });

    }
    private void getDetailBookPay(final String id){
        final ProgressDialog progressDialog = new ProgressDialog(BookDetailPayActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ_DEATIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray jsonArray = jsonobject.getJSONArray("books_table");
                            JSONObject data = jsonArray.getJSONObject(0);

                            int id = data.getInt("id");
                            String matheloai = String.valueOf(data.getInt("matheloai"));
                            String macuahang = String.valueOf(data.getInt("macuahang"));
                            tensach = data.getString("tensach");
                            hinhanh = data.getString("hinhanh");
                            chitiet = data.getString("chitiet");
                            String giaban = String.valueOf(data.getDouble("giaban"));
                            tongdiem = data.getString("tongdiem");
                            landanhgia = data.getString("landanhgia");
                            String linkBook = data.getString("linkbook");

                            sessionManager.createGuiLinkBook(tensach, linkBook);
                            txtTensach_detail_view.setText(tensach);
                            txtChitiet_detail_view.setText(chitiet);

//                            sessionManager.createSessionSendInfomationBook(String.valueOf(id),matheloai,macuahang,tensach,hinhanh,chitiet,giaban,tongdiem,landanhgia);

                            ratingbar_book_view.setRating(Float.valueOf(tongdiem)/Float.valueOf(landanhgia));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookDetailPayActivity.this,"loi e "+e, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(BookDetailPayActivity.this, "Error reading"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetailPayActivity.this);
        requestQueue.add(stringRequest);
    }
    private void CheckNhanxet(String url,final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetailPayActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("datontai")){
                            cardview.setVisibility(View.VISIBLE);
                            txtTitledg.setVisibility(View.GONE);
                            ratingbarBook.setVisibility(View.GONE);
                        }else {
                            cardview.setVisibility(View.GONE);
                            txtTitledg.setVisibility(View.VISIBLE);
                            ratingbarBook.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookDetailPayActivity.this, "Loi roi nhe", Toast.LENGTH_SHORT).show();
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Get_all_nhanxet(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetailPayActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listNhanxet.clear();
//                        if (response.length() > 0){
//                            recyclerView.setVisibility(View.VISIBLE);
//                            textViewTB.setVisibility(View.GONE);
//                        }else{
//                            recyclerView.setVisibility(View.GONE);
//                            textViewTB.setVisibility(View.VISIBLE);
//                        }

                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listNhanxet.add(new Hoadon(
                                        object.getInt("ID"),
                                        object.getInt("MaSach"),
                                        object.getInt("MaUser"),
                                        object.getString("TenSach"),
                                        object.getString("TenUser"),
                                        object.getString("GiaBan"),
                                        object.getString("NhanXet"),
                                        object.getInt("DaThanhToan"),
                                        object.getDouble("DiemDanhGia"),
                                        object.getInt("HienThi")
                                ));
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(BookDetailPayActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        nhanxetAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookDetailPayActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
    private void AddControl(){
        txtTensach_detail_view = findViewById(R.id.txtTensach_detail_view);
        txtChitiet_detail_view=findViewById(R.id.txtChitiet_detail_view);
        btn_view_book=findViewById(R.id.btn_view_book);
        ratingbar_book_view=findViewById(R.id.ratingbar_book_view);
        ratingbarBook=findViewById(R.id.ratingbarBook);
        recyclerview_danhgia=findViewById(R.id.recyclerview_danhgia);
        txtTitledg=findViewById(R.id.txtTitledg);
        cardview = findViewById(R.id.cardview);
        txtDown=findViewById(R.id.txtDown);
        txtUp=findViewById(R.id.txtUp);
    }
}
