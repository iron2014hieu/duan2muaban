package com.example.duan2muaban.Activity.Library;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.duan2muaban.MainActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Service.App;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.Sach.SachAdapter;
import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.nighmode_vanchuyen.SharedPref;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class BookDetailLibActivity extends AppCompatActivity {
    String masach, linkbook,hinhanh, tensach, tongdiem, landanhgia;

    ImageView imgBook_lib;
    TextView txtTensach_lib,numrating_book_detail_lib,txtDocsach,txtXemnhanxet,txtNgheaudio;
    RatingBar ratingbar_book_detail_lib;
    String URL ="https://bansachonline.xyz/bansach/sach/getBookDetail.php/?masach=";
    SessionManager sessionManager;
    SharedPref sharedPref;
    NotificationManagerCompat notificationManagerCompat;
    MediaSessionCompat mediaSessionCompat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_book_detail_lib);
        addcontrols();
        Toolbar toolbar = findViewById(R.id.toolbar_lib);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        sessionManager = new SessionManager(this);
        Intent intent = getIntent();
        masach = intent.getStringExtra("masach");
        tensach = intent.getStringExtra("tensach");
        Log.d("tensach_lib", tensach);
        toolbar.setTitle(tensach);
        getDetailBook(URL+masach);
        notificationManagerCompat = NotificationManagerCompat.from(this);
        mediaSessionCompat = new MediaSessionCompat(this, "tag");
        Intent intent1 = new Intent(this, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        String check = "4";
        intent.putExtra("check", check);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0,
                intent,
                0
        );
        mediaSessionCompat.setSessionActivity(pendingIntent);

        txtNgheaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

    }
    private void sendNotification(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sach3);
        Notification channel = new NotificationCompat.Builder(getApplicationContext(), App.CHANNEL_ID_1)
                .setSmallIcon(R.drawable.ic_music)
                .setContentTitle("2 con vit")
                .setContentText("By Hoang dang luaan")
                .setLargeIcon(bitmap)
                .addAction(R.drawable.ic_fast_rewind, "xx10", null)
                .addAction(R.drawable.ic_skip_previous, "prev", null)
                .addAction(R.drawable.ic_pause_circle_outline, "next", null)
                .addAction(R.drawable.ic_skip_next, "xx10", null)
                .addAction(R.drawable.ic_fast_forward, "pause", null)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1,2,3)
                .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setSubText("Sub text")
                .build();
        notificationManagerCompat.notify(1, channel);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_library, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_book:
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
        }
        return super.onOptionsItemSelected(item);
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
        txtNgheaudio= findViewById(R.id.txtNgheaudio);
    }
}
