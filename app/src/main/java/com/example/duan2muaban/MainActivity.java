package com.example.duan2muaban;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Activity.SearchBooksActivity;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.ViewPagerFM.FragmentAdapter;
import com.example.duan2muaban.fragmentMain.HomeFragment;
import com.example.duan2muaban.fragmentMain.TheloaiFragment;
import com.example.duan2muaban.fragmentMain.NotificationFragment;
import com.example.duan2muaban.fragmentMain.SearchFragment;
import com.example.duan2muaban.fragmentMain.CanhanFragment;
import com.example.duan2muaban.nighmode.SharedPref;
import com.example.duan2muaban.publicString.URL.UrlSql;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SharedPref sharedPref;
    SessionManager sessionManager;
    BottomNavigationView navigation;
    ViewPager viewPager;
    private TextView textNotify;
    private ImageButton cartButtonIV;
    private Button btnSearchView;
    private UrlSql urlSql;
    LinearLayout linearLayoutMain;
    SharedPreferences prefs;
    boolean firstStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        textNotify=findViewById(R.id.textNotify);
        cartButtonIV= findViewById(R.id.cartButtonIV);
        linearLayoutMain= findViewById(R.id.linearLayoutMain);
        btnSearchView = findViewById(R.id.btnSearch);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart){
            ShowFirstAppStart();
        }
//        Toobar đã như ActionBar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sessionManager = new SessionManager(this);
        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.viewpager); //Idnit Viewpager
        viewPager.setPageTransformer(true, new CubeTransformer());

        setupFm(getSupportFragmentManager(), viewPager); //Setup Fragment
        viewPager.setCurrentItem(0); //Set Currrent Item When Activity Start
        viewPager.setOnPageChangeListener(new PageChange()); //Listeners For Viewpager When Page Changed
        try {
            HashMap<String,String> user = sessionManager.getUserDetail();
            String name = user.get(sessionManager.NAME);
            String id = user.get(sessionManager.ID);
            if (InternetConnection.checkConnection(getApplicationContext())) {
                if (name == null) {
                    cartButtonIV.setVisibility(View.GONE);
                    textNotify.setVisibility(View.GONE);
                } else {
                    GetDataCouterCart(urlSql.URl_GETDATA_CART + id);
                    cartButtonIV.setVisibility(View.VISIBLE);
                    textNotify.setVisibility(View.VISIBLE);
                }
            }else {
                Snackbar.make(linearLayoutMain,
                        R.string.string_internet_connection_not_available,
                        Snackbar.LENGTH_LONG).show();
            }

        }catch (Exception e){
            Log.e("LOG", e.toString());
        }
        //Setup seerch view
        btnSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchBooksActivity.class));
            }
        });
        cartButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
    }

    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){
        FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);
        //Add All Fragment To List
        Adapter.add(new HomeFragment(), "Trang chủ");
        Adapter.add(new TheloaiFragment(), "The loại");
        Adapter.add(new SearchFragment(), "Tìm kiếm");
        Adapter.add(new NotificationFragment(), "Thông báo");
        Adapter.add(new CanhanFragment(), "Cá nhân");
        viewPager.setAdapter(Adapter);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_library:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.nav_search:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.nav_notif:
                    viewPager.setCurrentItem(3);
                    return true;
                case R.id.nav_profile:
                    viewPager.setCurrentItem(4);
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.cartButtonIV:
//                startActivity(new Intent(getBaseContext(), Main2Activity.class));
//                break;
        }
    }

    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    navigation.setSelectedItemId(R.id.nav_home);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.nav_library);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.nav_search);
                    break;
                case 3:
                    navigation.setSelectedItemId(R.id.nav_notif);
                    break;
                case 4:
                    navigation.setSelectedItemId(R.id.nav_profile);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }


    //settheme
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.cart_mn:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void GetDataCouterCart(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        item_count = response.length();
//                        textNotify.setText(String.valueOf(item_count));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
//        textNotify.setText(String.valueOf(item_count));
        super.onResume();
    }
    private void ShowFirstAppStart(){
        new AlertDialog.Builder(this)
                .setTitle("One Time Dialog")
                .setMessage("This should only be show own")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
        //set boolearn check first start app to false
        SharedPreferences.Editor editor =prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}
