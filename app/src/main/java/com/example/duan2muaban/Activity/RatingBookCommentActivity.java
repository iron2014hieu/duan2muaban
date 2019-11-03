package com.example.duan2muaban.Activity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RatingBookCommentActivity extends AppCompatActivity {
    private RatingBar ratingbarComment;
    private SessionManager sessionManager;
    private String idBook,idbill,nameuser, nhanxet,diemdanhgia;
    private EditText edtNhanxet;
    private String URL_NHANXET ="https://hieuttpk808.000webhostapp.com/books/cart_bill/update_bill.php";
    private String URL_UPDATE_DIEM ="https://hieuttpk808.000webhostapp.com/books/sach/update_xephang.php";

    private Double tongDiem;
    private int lanDanhgia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_book_comment);
        Addcontrols();
        sessionManager = new SessionManager(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarComment);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        diemdanhgia = (intent.getStringExtra("NUMRATE"));

        try {
            HashMap<String,String> user = sessionManager.getDetailBill();
            idbill = user.get(sessionManager.ID_BILL);
            idBook =user.get(sessionManager.MASACH);

            HashMap<String,String> usermm = sessionManager.getUserDetail();
            nameuser = usermm.get(sessionManager.NAME);

            HashMap<String, String> book = sessionManager.getBookDetail();
//            tongDiem = Double.valueOf(book.get(sessionManager.TONGDIEM));
//            lanDanhgia = Integer.valueOf(book.get(sessionManager.LANDANHGIA));

        }catch (Exception e){
            Log.e("RATING", e.toString());
        }

        LayerDrawable stars = (LayerDrawable) ratingbarComment.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
        ratingbarComment.setRating(Float.parseFloat(diemdanhgia));

        tongDiem += Double.valueOf(diemdanhgia);
        lanDanhgia +=1;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RatingBookCommentActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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
                LuuNhanxet(idbill,diemdanhgia, nameuser);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void LuuNhanxet(final String id, final String diem, final String tenuser){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NHANXET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                //goi ham cap nhat sach zo dday
                                UpdateDiemdanhgiaSach();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(RatingBookCommentActivity.this, "error "+e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("id", id);
                params.put("nhanxet", edtNhanxet.getText().toString());
                params.put("diemdanhgia", diem);
                params.put("tenuser", tenuser);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void UpdateDiemdanhgiaSach(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE_DIEM,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                RatingBookCommentActivity.this.finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RatingBookCommentActivity.this, "error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RatingBookCommentActivity.this, "err   "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", idBook);
                params.put("tongdiem", String.valueOf(tongDiem));
                params.put("landanhgia", String.valueOf(lanDanhgia));
                params.put("idhoadon", idbill);
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
