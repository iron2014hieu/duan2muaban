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

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemSachActivity extends AppCompatActivity {
    private EditText edtTensach, edtGiaban, edtChitiet;
    private Button btnThemsach;
    private CircleImageView img_themanhsach;
    private SessionManager sessionManager;
    private String idCuahang, maTheloai;
    private String URL_INSERT ="http://hieuttpk808.000webhostapp.com/books/sach/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);
        Addcontrols();
        sessionManager = new SessionManager( this);

        HashMap<String,String> cuahang = sessionManager.getUserDetail();
        idCuahang = cuahang.get(sessionManager.ID);

        HashMap<String,String> theloai = sessionManager.getMAtheloai();
        maTheloai = theloai.get(sessionManager.MATHELOAI);

        btnThemsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThemSach(idCuahang, maTheloai);
            }
        });
//        Toast.makeText(this, "idCuahang: "+idCuahang +" maTheloai: "+maTheloai, Toast.LENGTH_SHORT).show();
    }
    private void ThemSach(final String idcuahang, final String matheloai){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("datontai")){
                            Toast.makeText(ThemSachActivity.this, "datontai", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("success")){
                            Toast.makeText(ThemSachActivity.this, "success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ThemSachActivity.this, MainActivity.class));
                        }else {
                            Toast.makeText(ThemSachActivity.this, "Them that bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ThemSachActivity.this, "Loi roi nhe", Toast.LENGTH_SHORT).show();
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("matheloai", matheloai);
                params.put("macuahang", idcuahang);
                params.put("tensach", edtTensach.getText().toString().trim());
                params.put("giaban", edtGiaban.getText().toString().trim());
                params.put("chitiet", edtChitiet.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void Addcontrols(){
        edtTensach = findViewById(R.id.edtTensach);
        edtGiaban = findViewById(R.id.edtGiaban);
        edtChitiet = findViewById(R.id.edtChitiet);
        btnThemsach = findViewById(R.id.btnThemsach);
        img_themanhsach = findViewById(R.id.img_themanhsach);
    }
}
