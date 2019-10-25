package com.example.duan2muaban.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.MainActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;

import java.util.HashMap;
import java.util.Map;

public class ThemTheloaiSachActivity extends AppCompatActivity {
    private EditText edtTentl, edtMotatl;
    private Button btnThemtl;
    private String URL_INSERT ="https://hieuttpk808.000webhostapp.com/books/theloai/insert.php";
    private SessionManager sessionManager;
    private String macuahang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_theloai_sach);
        edtMotatl = findViewById(R.id.edtMotatheloai);
        edtTentl = findViewById(R.id.edtTentheloai);
        btnThemtl = findViewById(R.id.btnThemtheloai);

        sessionManager = new SessionManager(this);
        HashMap<String, String> store = sessionManager.getUserDetail();
        macuahang = store.get(sessionManager.ID);
        Toast.makeText(this, ""+macuahang, Toast.LENGTH_SHORT).show();
        btnThemtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemTheloai(URL_INSERT, macuahang);
            }
        });
    }
    private void ThemTheloai(String url,final String macuahang){
        RequestQueue requestQueue = Volley.newRequestQueue(ThemTheloaiSachActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("datontai")){
                            Toast.makeText(ThemTheloaiSachActivity.this, "datontai", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("success")){
                            Toast.makeText(ThemTheloaiSachActivity.this, "success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ThemTheloaiSachActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(ThemTheloaiSachActivity.this, "Them that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThemTheloaiSachActivity.this, "Loi roi nhe", Toast.LENGTH_SHORT).show();
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("macuahang", macuahang);
                params.put("tentheloai", edtTentl.getText().toString().trim());
                params.put("mota", edtMotatl.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
