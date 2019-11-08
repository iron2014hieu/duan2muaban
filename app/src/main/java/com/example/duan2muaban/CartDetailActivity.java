package com.example.duan2muaban;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText edtMaGiamGia, edtTongtien;
    Button btnCheckMGG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);

        edtMaGiamGia = findViewById(R.id.edtMaGiamGia);
        edtTongtien = findViewById(R.id.edtTongtien);
        btnCheckMGG = findViewById(R.id.CheckMGG);

        btnCheckMGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edtMaGiamGia.getText().toString();
                if (s.isEmpty()){
                    Toast.makeText(CartDetailActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i = new Intent("android.intent.action.CUSTOM_INTENT_BAI3");
                Bundle bundle = new Bundle();
                bundle.putString("MA",edtMaGiamGia.getText().toString());
                i.putExtras(bundle);
                sendBroadcast(i);
                edtMaGiamGia.setText("");
            }
        });


    }
}
