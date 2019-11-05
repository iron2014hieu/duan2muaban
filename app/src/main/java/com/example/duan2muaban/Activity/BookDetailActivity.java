package com.example.duan2muaban.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Main2Activity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.nighmode.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class BookDetailActivity extends AppCompatActivity {
    SharedPref sharedPref;
    private ImageView img_book;
    private String idBook, tensach,chitiet,
            hinhanh, giaban, soluong, landanhgia, tongdiem, linkImage;
    private Float diemdanhgia;
    SessionManager sessionManager;
    private TextView edtTensach, edtGiaban,edtChitiet;
    private TextView txtDiemdanhgia,btn_view_book_when_bill, textNotify, titleToolbar;

    private RatingBar ratingBar;
    private Button btnThemvaogio;
    private Double giabansach = 0.0;

    private String URL_INSERT ="http://hieuttpk808.000webhostapp.com/books/cart_bill/insert.php";
    private String URL_CHECK ="https://hieuttpk808.000webhostapp.com/books/cart_bill/checklibrary.php";
    String idUser, name, quyen;
    private int item_count =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        addcontrols();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("dobh");
        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();

        sessionManager = new SessionManager(BookDetailActivity.this);

        HashMap<String,String> book = sessionManager.getBookDetail();
        idBook = book.get(sessionManager.MASACH);
        chitiet=book.get(sessionManager.NOIDUNG);
        giaban = (book.get(sessionManager.GIA));
        soluong = (book.get(sessionManager.SOLUONG));
        tensach = book.get(sessionManager.TENSACH);

//        tongdiem= (book.get(sessionManager.TONGDIEM));
//        landanhgia=book.get(sessionManager.LANDANHGIA);
        linkImage = book.get(sessionManager.ANHBIA);

        Picasso.with(this)
                .load(linkImage).into(img_book);
        textNotify.setText(String.valueOf(item_count));
        try {
            HashMap<String,String> user = sessionManager.getUserDetail();
            quyen = user.get(sessionManager.QUYEN);
            name = user.get(sessionManager.NAME);
            idUser = user.get(sessionManager.ID);

            if (quyen.equals("user")){
                CheckLibrary(idBook, idUser);
                btnThemvaogio.setVisibility(View.VISIBLE);
            }else {
                btnThemvaogio.setVisibility(View.GONE);
                btn_view_book_when_bill.setVisibility(View.VISIBLE);
            }
        }catch (Exception e){
            Log.e("LOG", e.toString());
        }

//        diemdanhgia =(Float.parseFloat(tongdiem)/Float.parseFloat(landanhgia));
//        ratingBar.setRating(diemdanhgia);

//        txtDiemdanhgia.setText("Đánh giá "+(diemdanhgia)+" (với "+landanhgia+" đánh giá)");

        edtTensach.setText(tensach);
        edtGiaban.setText(giaban+" VNĐ");
        edtChitiet.setText(chitiet);

        giabansach = Double.valueOf(giaban);




        btn_view_book_when_bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnThemvaogio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(BookDetailActivity.this);
                alertDialog.setMessage("Bạn có muốn thêm sách "+tensach+" vào giỏ hàng không?");
                alertDialog.setIcon(R.drawable.ic_check_black_24dp);
                alertDialog.setTitle("Thêm vào giỏ hàng");
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            HashMap<String,String> user = sessionManager.getUserDetail();
                            idUser = user.get(sessionManager.ID);
                            if (idUser==null){
                                Toast.makeText(BookDetailActivity.this, "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
                            }else {
                                ThemCart(idBook, idUser, tensach, giaban);
                                sessionManager.createCart(idBook, idUser, tensach, giaban, "0");
                            }
                        }catch (Exception e){

                        }

                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }

                });
                alertDialog.show();


            }
        });
    }
    private void ThemCart(final String idBook, final String idUser, final String tensach, final String giaban){
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetailActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("datontai")){
                            Toast.makeText(BookDetailActivity.this, "Bạn đã mua sách này", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("success")){
                            Toast.makeText(BookDetailActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            item_count +=1;
                            textNotify.setText(String.valueOf(item_count));
//                            startActivity(new Intent(BookDetailActivity.this, Main2Activity.class));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("masach", idBook);
                params.put("mauser", idUser);
                params.put("tensach", tensach);
                params.put("giaban",giaban);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void CheckLibrary(final String idBook, final String idUser){
        RequestQueue requestQueue = Volley.newRequestQueue(BookDetailActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("datontai")){
                            Toast.makeText(BookDetailActivity.this, "Bạn đã mua sách này", Toast.LENGTH_SHORT).show();
                            btnThemvaogio.setVisibility(View.GONE);
                            btn_view_book_when_bill.setVisibility(View.VISIBLE);
                        }else {
                            btnThemvaogio.setVisibility(View.VISIBLE);
                            btn_view_book_when_bill.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("masach", idBook);
                params.put("mauser", idUser);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
    private void addcontrols(){
        edtTensach = findViewById(R.id.edtTensach);
        edtGiaban=findViewById(R.id.edtGiaban);
        edtChitiet=findViewById(R.id.extractEditTextChitiet);
        txtDiemdanhgia=findViewById(R.id.txtDiemdanhgia);
        btnThemvaogio=findViewById(R.id.btnThemvaogio);
        btn_view_book_when_bill=findViewById(R.id.btn_view_book_when_bill);
        ratingBar = findViewById(R.id.ratingbar);

        img_book=findViewById(R.id.imgBook);
        textNotify= findViewById(R.id.textNotify);
//        titleToolbar= findViewById(R.id.titleToolbar);
    }
}
