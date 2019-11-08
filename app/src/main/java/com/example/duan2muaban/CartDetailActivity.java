package com.example.duan2muaban;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.CartAdapter;
import com.example.duan2muaban.model.Cart;
import com.example.duan2muaban.nighmode.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CartDetailActivity extends AppCompatActivity {
    private TextView tv;
    CartAdapter cartAdapter;
    RecyclerView recyclerview_create_hoadon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        tv = (TextView) findViewById(R.id.tv);
        recyclerview_create_hoadon= findViewById(R.id.recyclerview_create_hoadon);
        for (int i = 0; i < CartAdapter.listGiohang.size(); i++){
            if(CartAdapter.listGiohang.get(i).getSelected()) {
                tv.setText(tv.getText() +" Mã sách: "+ CartAdapter.listGiohang.get(i).getMasach());
//                cartAdapter = new CartAdapter(this, CartAdapter.listGiohang);
            }
        }

        // recyclerview nhà xuất bản
        StaggeredGridLayoutManager gridLayoutManager3 =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_create_hoadon.setLayoutManager(gridLayoutManager3);
        recyclerview_create_hoadon.setHasFixedSize(true);
        recyclerview_create_hoadon.setAdapter(cartAdapter);

    }
}
