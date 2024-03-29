package com.example.duan2muaban.Activity.hoadon;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.nighmode_vanchuyen.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RatingBookCommentActivity extends AppCompatActivity {
    private RatingBar ratingbarComment;
    private SessionManager sessionManager;
    private String nameuser,masach,idcthd, diemnhanxet;
    private EditText edtNhanxet;
    private String URL_THEMNHATXET ="https://bansachonline.xyz/bansach/hoadon/them_nhanxet.php";
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_rating_book_comment);

        Addcontrols();
        sessionManager = new SessionManager(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarComment);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        Intent intent = getIntent();
        masach = intent.getStringExtra("masach");
        idcthd = intent.getStringExtra("idcthd");


        try {
            HashMap<String,String> usermm = sessionManager.getUserDetail();
            nameuser = usermm.get(sessionManager.NAME);
        }catch (Exception e){
            Log.e("RATING", e.toString());
        }

    }
    //settheme
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn thoát?")
                .setCancelable(false)
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RatingBookCommentActivity.this.finish();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_comment, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_send:
                if (!edtNhanxet.getText().toString().trim().equals("") && ratingbarComment.getRating()>0){
                    diemnhanxet =String.valueOf(ratingbarComment.getRating());
                    LuuNhanxet(masach,diemnhanxet, idcthd);
                    break;
                }else {
                    Toast.makeText(this, "Bạn phải nhập nhận xét và cho điểm đánh giá", Toast.LENGTH_SHORT).show();
                }

        }
        return super.onOptionsItemSelected(item);
    }
    private void LuuNhanxet(final String masach, final String diemdanhgia, final String id_sach_cthd){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_THEMNHATXET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.equals("tc")){
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(RatingBookCommentActivity.this, "err   "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("masach", masach);
                params.put("diemdanhgia", diemdanhgia);
                params.put("noidungdanhgia", edtNhanxet.getText().toString());
                params.put("id", id_sach_cthd);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void Addcontrols(){
        ratingbarComment=findViewById(R.id.ratingbarComment);
        edtNhanxet = findViewById(R.id.edtNhanxet);
    }
}
