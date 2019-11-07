package com.example.duan2muaban.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.duan2muaban.Activity.hoadon.HoadonActivity;
import com.example.duan2muaban.LoginRegister.LoginActivity;
import com.example.duan2muaban.Main2Activity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.nighmode.SharedPref;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookDetailActivity extends AppCompatActivity {
    SharedPref sharedPref;
    Button btn_themgh,btn_muangay;
    private ImageView img_book;
    private String idBook, tensach,chitiet,
            hinhanh, giaban, soluong, landanhgia, tongdiem, linkImage;
    private Float diemdanhgia;
    SessionManager sessionManager;
    private TextView edtTensach, edtGiaban,edtChitiet;
    private TextView txtDiemdanhgia, textNotify, titleToolbar;

    private RatingBar ratingBar;
    private Button btnThemvaogio;
    private Double giabansach = 0.0;
    private ImageButton btn_Share,btn_Message;
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

        Toolbar toolbar = findViewById(R.id.toolbar_sach_chitiet);
        ActionBar actionBar = getSupportActionBar();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                onBackPressed();
            }
        });

        toolbar.animate().translationY(-toolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
        toolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();

        sessionManager = new SessionManager(BookDetailActivity.this);

        HashMap<String,String> book = sessionManager.getBookDetail();
        idBook = book.get(sessionManager.MASACH);
        chitiet=book.get(sessionManager.NOIDUNG);
        giaban = (book.get(sessionManager.GIA));
        soluong = (book.get(sessionManager.SOLUONG));
        tensach = book.get(sessionManager.TENSACH);

        toolbar.setTitle(tensach);

//        tongdiem= (book.get(sessionManager.TONGDIEM));
//        landanhgia=book.get(sessionManager.LANDANHGIA);
        linkImage = book.get(sessionManager.ANHBIA);

        Picasso.with(this)
                .load(linkImage).into(img_book);
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

        btn_themgh.setOnClickListener(new View.OnClickListener() {
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
                                startActivity(new Intent(BookDetailActivity.this, LoginActivity.class));
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
        btn_muangay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(BookDetailActivity.this);
                alertDialog.setMessage("Bạn có muốn mua sách "+tensach+" với giá "+giaban+ " không?");
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
                                startActivity(new Intent(BookDetailActivity.this, LoginActivity.class));
                            }else {
                                Intent intent=new Intent(getApplicationContext(), DatmuaActivity.class);
                                intent.putExtra("masach", idBook);
                                intent.putExtra("tensach", tensach);
                                intent.putExtra("gia", giaban);
                                startActivity(intent);
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
        //share text
        // sharing intent
        //share all
        btn_Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chỉ cần truyền ảnh từ URL thành bitmap bên dưới là ok
                Drawable myDrawable  = img_book.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) myDrawable).getBitmap();

                //sharing image
                try {
                    File file= new File(BookDetailActivity.this.getExternalCacheDir(), tensach+".jpg");
                    FileOutputStream fOut = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fOut);
                    fOut.flush();
                    fOut.close();
                    file.setReadable(true, false);

                    //sharing intent
                    Intent intent = new Intent(Intent.ACTION_SEND);

//                    intent.putExtra(Intent.EXTRA_SUBJECT, "Wirite subject here");
//                    intent.putExtra(Intent.EXTRA_TEXT, tensach);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    intent.setType("image/png");

                    startActivity(Intent.createChooser(intent, "Share image vie"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                        }else {
                            btnThemvaogio.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void addcontrols(){
        edtTensach = findViewById(R.id.edtTensach);
        edtGiaban=findViewById(R.id.edtGiaban);
        edtChitiet=findViewById(R.id.extractEditTextChitiet);
        txtDiemdanhgia=findViewById(R.id.txtDiemdanhgia);
        btnThemvaogio=findViewById(R.id.btnThemvaogio);
        ratingBar = findViewById(R.id.ratingbar);
        btn_themgh=findViewById(R.id.btn_themGH);
        btn_muangay=findViewById(R.id.btn_muangay);
        img_book=findViewById(R.id.imgBook);
        textNotify= findViewById(R.id.textNotify);
//        titleToolbar= findViewById(R.id.titleToolbar);

        btn_Share= findViewById(R.id.btn_Share);
    }
}
