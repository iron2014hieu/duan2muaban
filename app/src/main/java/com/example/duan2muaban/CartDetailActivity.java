package com.example.duan2muaban;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFaceDatmua;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.CartAdapter;
import com.example.duan2muaban.model.DatMua;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import static com.example.duan2muaban.Notif.App.CHANNEL_1_ID;
public class CartDetailActivity extends AppCompatActivity {

    private ArrayList<CountryItem> countryItems;
    private CountryAdapter countryAdapter;

    EditText edtMaGiamGia, edtSdt, edtDiachi, edtTenkh;
    TextView txtTongtien;
    Button btnCheckMGG,btnThanhtoan;
    RecyclerView recyclerview_create_bill;
    List<DatMua> listDatmua = new ArrayList<>();
    CartAdapter cartAdapter;
    ApiInTerFaceDatmua apiInTerFaceDatmua;
    String tongtien,mauser_session;
    String url_insert_cthd="https://bansachonline.xyz/bansach/hoadon/them_cthd.php";
    String url_insert_hoadon ="https://bansachonline.xyz/bansach/hoadon/them_hoadon.php";
    int sizeList=0;
    SessionManager sessionManager;
    ProgressBar progress_hoadon;

    int Giamgia = 0;
    int Giatri;
    int Phivanchuyen = 0;

    private NotificationManagerCompat notificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        progress_hoadon=findViewById(R.id.progress_hoadon);
        edtMaGiamGia = findViewById(R.id.edtMaGiamGia);
        txtTongtien= findViewById(R.id.txtTongtienthanhtoan);
        edtSdt = findViewById(R.id.edtSdt);
        edtDiachi=findViewById(R.id.edtDiachi);
        edtTenkh=findViewById(R.id.edtTenkh);
        btnCheckMGG = findViewById(R.id.CheckMGG);
        btnThanhtoan= findViewById(R.id.btnThanhtoan);
        recyclerview_create_bill= findViewById(R.id.recyclerview_create_bill);
        sessionManager = new SessionManager(this);
        notificationManager = NotificationManagerCompat.from(this);
        HashMap<String,String> user = sessionManager.getUserDetail();
        mauser_session = user.get(sessionManager.ID);
        Intent intent = getIntent();
        tongtien = intent.getStringExtra("tongtien");

        Toast.makeText(this, ""+tongtien, Toast.LENGTH_SHORT).show();
        Giatri = Integer.valueOf(tongtien);
        if (Giatri > 100000){
            Giatri -= Giatri * 0.05 ;
            txtTongtien.setText(String.valueOf(Giatri));
        }else if(Giatri>250000) {
            Toast.makeText(this, "free ship", Toast.LENGTH_SHORT).show();
        }

        txtTongtien.setText(tongtien+ " VNĐ");

        cartAdapter = new CartAdapter(this,listDatmua);

        initList();
        Spinner spinner = findViewById(R.id.spinner_countries);
        countryAdapter = new CountryAdapter(this, countryItems);
        spinner.setAdapter(countryAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CountryItem clickItem = (CountryItem) adapterView.getItemAtPosition(i);
                String clickItemContry = clickItem.getCountryName();
                String clickItemPrice = clickItem.getPrice();
                Toast.makeText(CartDetailActivity.this, clickItemContry+clickItemPrice, Toast.LENGTH_SHORT).show();
                int tt = Integer.valueOf(tongtien);
                tt+= Integer.valueOf(clickItemPrice);
                tt-= Giamgia;
                Phivanchuyen = Integer.valueOf(clickItemPrice);
                txtTongtien.setText(tt+ " VNĐ");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final String coupon = "abc";

        btnCheckMGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtMaGiamGia.getText().toString().equals(coupon)){
                    final TextView Tv = findViewById(R.id.txtTongtienthanhtoan);
                    Giamgia = 10000;
                    int tt = Integer.valueOf(tongtien);
                    int sp = tt - Giamgia;
                    sp += Phivanchuyen;
                    Toast.makeText(CartDetailActivity.this, ""+ sp, Toast.LENGTH_SHORT).show();
                    Tv.setText(String.valueOf(sp));
                } else {
                    Toast.makeText(getApplicationContext(), "Mã không hợp lệ", Toast.LENGTH_LONG).show();
                }
            }
        });

        StaggeredGridLayoutManager gridLayoutManager3 =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerview_create_bill.setLayoutManager(gridLayoutManager3);
        recyclerview_create_bill.setHasFixedSize(true);
        fetchTacgia(mauser_session);
        progress_hoadon.setVisibility(View.GONE);

//        btnCheckMGG.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String s = edtMaGiamGia.getText().toString();
//                if (s.isEmpty()){
//                    Toast.makeText(CartDetailActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                Intent i = new Intent("android.intent.action.CUSTOM_INTENT_BAI3");
//                Bundle bundle = new Bundle();
//                bundle.putString("MA",edtMaGiamGia.getText().toString());
//                i.putExtras(bundle);
//                sendBroadcast(i);
//                edtMaGiamGia.setText("");
//            }
//        });

        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(CartDetailActivity.this);
                alertDialog.setMessage("Bạn có muốn thanh toán đơn hàng này không");
                alertDialog.setIcon(R.drawable.ic_check_black_24dp);
                alertDialog.setTitle("Đặt hàng");
                alertDialog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progress_hoadon.setVisibility(View.VISIBLE);
                        btnThanhtoan.setVisibility(View.GONE);
                        ThemHoadon(mauser_session,tongtien,"0333756922", url_insert_hoadon);
                    }
                });
                alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progress_hoadon.setVisibility(View.GONE);
                        btnThanhtoan.setVisibility(View.VISIBLE);
                    }

                });
                alertDialog.show();

            }
        });
    }
    private void initList(){
        countryItems = new ArrayList<>();
        countryItems.add(new CountryItem("Giao hàng tiết kiệm","20000", R.drawable.vanchuyen));
        countryItems.add(new CountryItem("Giao hàng nhanh", "30000", R.drawable.vanchuyen));
        countryItems.add(new CountryItem("Giao hàng siêu tốc", "45000", R.drawable.vanchuyen));
    }
    public void fetchTacgia(String mauser){
        apiInTerFaceDatmua = ApiClient.getApiClient().create(ApiInTerFaceDatmua.class);
        Call<List<DatMua>> call = apiInTerFaceDatmua.getDatMuaThanhtoan(mauser);

        call.enqueue(new Callback<List<DatMua>>() {
            @Override
            public void onResponse(Call<List<DatMua>> call, retrofit2.Response<List<DatMua>> response) {
                progress_hoadon.setVisibility(View.GONE);
                listDatmua= response.body();
                sizeList = listDatmua.size();
                cartAdapter = new CartAdapter(CartDetailActivity.this,listDatmua);
                recyclerview_create_bill.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DatMua>> call, Throwable t) {
                progress_hoadon.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    private void ThemHoadon( final String mauser, final String tongtien,final String sdt,String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int mahoadon =Integer.valueOf(jsonObject.getString("mahoadon"));
                            mahoadon++;
                                for (int n = 0; n < sizeList; n++) {
                                    String masach = String.valueOf(listDatmua.get(n).getMasach());
                                    String tensach = listDatmua.get(n).getSanpham();
                                    String giaban = String.valueOf(listDatmua.get(n).getGia());
                                    String soluong = String.valueOf(listDatmua.get(n).getSoluong());
                                    String hinhanh = listDatmua.get(n).getHinhanh();
                                    ThemCTHD(String.valueOf(mahoadon), masach, tensach, giaban, soluong, hinhanh, url_insert_cthd);
                                }
                                Toast.makeText(CartDetailActivity.this, "Thêm tc hóa đơn: " + mahoadon, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError regis ", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("mauser", mauser);
                params.put("tongtien", tongtien);
                params.put("tenkh", edtTenkh.getText().toString().trim());
                params.put("diachi", edtDiachi.getText().toString().trim());
                params.put("sdt", sdt);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void ThemCTHD( final String mahd, final String masach, final String tensach, final String giaban,
                           final String soluong, final String hinhanh,
                           String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("tc")){
                            progress_hoadon.setVisibility(View.GONE);
                            btnThanhtoan.setVisibility(View.VISIBLE);
                            sendOnChannel("Hóa đơn: "+mahd,"Xem đơn hàng của bạn");
//                            startActivity(new Intent(getBaseContext(), CartDetailActivity.class));
                            Intent intent = new Intent(getBaseContext(), MuahangActivity.class);
                            intent.putExtra("check", 0);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Loi roi nhe", Toast.LENGTH_SHORT).show();
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("mahd", mahd);
                params.put("masach", masach);
                params.put("tensach", tensach);
                params.put("giaban", giaban);
                params.put("soluong", soluong);
                params.put("hinhanh", hinhanh);
                params.put("mauser", mauser_session);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void sendOnChannel(String title, String message) {
        Intent activityIntent = new Intent(this, MuahangActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, activityIntent, 0);

        Notification summaryNotification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_arrow)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine(title + " " + message)
                        .setBigContentTitle("1 đơn hàng mới")
                        .setSummaryText("user@example.com"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setGroup("example_group")
                .setAutoCancel(true)
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
                .setContentIntent(contentIntent)
                .setGroupSummary(true)
                .build();
        summaryNotification.defaults |= Notification.DEFAULT_SOUND;
        SystemClock.sleep(1000);
        notificationManager.notify(2, summaryNotification);
    }
}
