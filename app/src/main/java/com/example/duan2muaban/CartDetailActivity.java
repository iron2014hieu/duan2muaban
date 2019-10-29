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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.nighmode.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CartDetailActivity extends AppCompatActivity {
    SharedPref sharedPref;
    private TextView txtTensach, txtGiaban, txtSoluongmua, txtThanhtien, txtDathanhtoan;
    private TextView btnThanhtoan, btnXoagiohang;
    String idSach,tensach, giaban, soluongmua, idCart;
    int dathanhtoan =0;
    private SessionManager sessionManager;
    private String URL_THANHTOAN ="http://hieuttpk808.000webhostapp.com/books/cart_bill/cartpay.php";
    private String URL_DELETE ="https://hieuttpk808.000webhostapp.com/books/cart_bill/delete_cart_byid.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_cart_detail);
        AddControls();
        sessionManager = new SessionManager(CartDetailActivity.this);
        HashMap<String,String> cart = sessionManager.getCart();

        idSach = cart.get(sessionManager.ID_BOOK);
        tensach = cart.get(sessionManager.TENSACH);
        giaban = cart.get(sessionManager.GIABAN);
        dathanhtoan = Integer.valueOf(cart.get(sessionManager.DATHANHTOAN));

        txtTensach.setText(tensach);
        txtGiaban.setText("Giá bán: "+giaban+" VNĐ");
        Double thanhtien = Double.valueOf(giaban);
        txtThanhtien.setText("Thành tiền: "+thanhtien.toString() +" VNĐ");
        Intent intent = getIntent();
        idCart = intent.getStringExtra("IDCART");


        if (dathanhtoan == 0){
            txtDathanhtoan.setText("Chưa thanh toán");
        }else {
            txtDathanhtoan.setText("Đã thanh toán");
        }

        btnXoagiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CartDetailActivity.this);
                alert.setMessage("Bạn có muốn xóa sách "+tensach+" khỏi giỏ hàng không?");
                alert.setTitle("Xóa khỏi giỏ hàng");
                alert.setIcon(R.drawable.ic_warning_black_24dp);
                alert.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        xoaGiohang(idCart);

                    }
                });
                alert.setNegativeButton("không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });

        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartDetailActivity.this);
                alertDialog.setMessage("Bạn có muốn thanh toán "+soluongmua+" cuốn sách "+tensach+" với giá "+giaban+" VNĐ không?");
                alertDialog.setIcon(R.drawable.ic_check_black_24dp);
                alertDialog.setTitle("Thanh toán");
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        thanhToanGiohang(idSach);
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
    private void xoaGiohang(final String idGiohang) {
        final ProgressDialog progressDialog = new ProgressDialog(CartDetailActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        if (response.equals("success")){
                            startActivity(new Intent(getBaseContext(), Main2Activity.class));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id", idGiohang);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CartDetailActivity.this);
        requestQueue.add(stringRequest);
    }
    private void thanhToanGiohang(final String masach) {
        final ProgressDialog progressDialog = new ProgressDialog(CartDetailActivity.this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_THANHTOAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
//                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                progressDialog.dismiss();
//                                Toast.makeText(getContext(), "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CartDetailActivity.this, MainActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
//                            Toast.makeText(getContext(), "Loi e! "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CartDetailActivity.this, "Error ! "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("masach", masach);
                params.put("dathanhtoan", "1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(CartDetailActivity.this);
        requestQueue.add(stringRequest);
    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }

    private void AddControls(){
        txtTensach=findViewById(R.id.txtTensach);
        txtGiaban=findViewById(R.id.txtGiaban);
        txtThanhtien=findViewById(R.id.txtThanhtien);
        txtDathanhtoan=findViewById(R.id.txtDathanhtoan);
        btnThanhtoan=findViewById(R.id.btnThanhtoan);
        btnXoagiohang = findViewById(R.id.txtXoagiohang);
    }
}
