package com.example.duan2muaban.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.CartListFragment;
import com.example.duan2muaban.Main2Activity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.model.DatMua;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    public static List<DatMua> listGiohang;
    public static int tongTienSach;
    private int tongTienTungsach;

    int giaBan = 0;
    private  int soLuong = 1;

    String iduser, masach;

    public CartAdapter(Context context, List<DatMua> listGiohang) {
        this.context = context;
        this.listGiohang = listGiohang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_giohang, viewGroup, false);

        final MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {

//        holder.checkBox.setText("haha "+i);
        holder.checkBox.setChecked(listGiohang.get(i).getSelected());// nếu tự tạo sẻ là isSelected
        holder.tv_name.setText(listGiohang.get(i).getSanpham());
        holder.tv_giaban.setText(listGiohang.get(i).getGia()+" VNĐ");
        Picasso.with(context).load(listGiohang.get(i).getHinhanh()).into(holder.img_cart);

        soLuong = listGiohang.get(i).getSoluong();
        giaBan = listGiohang.get(i).getGia();

        holder.checkBox.setTag(i);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer pos = (Integer) holder.checkBox.getTag();
                if (isChecked) {
                    listGiohang.get(pos).setSelected(true);
                        if(listGiohang.get(i).getSelected() == true) {
                            tongTienTungsach = listGiohang.get(i).getGia() * listGiohang.get(i).getSoluong();
                            tongTienSach +=tongTienTungsach;
                            CartListFragment.txtTongtien.setText(String.valueOf(tongTienSach)+" VNĐ");
                    }
                } else {
                    listGiohang.get(pos).setSelected(false);
                        if (listGiohang.get(i).getSelected()== false){
                            tongTienTungsach = listGiohang.get(i).getGia() * listGiohang.get(i).getSoluong();
                            tongTienSach -=tongTienTungsach;
                            CartListFragment.txtTongtien.setText(String.valueOf(tongTienSach)+" VNĐ");
                    }

                }
            }
        });

        iduser = listGiohang.get(i).getMauser();
        masach = String.valueOf(listGiohang.get(i).getMasach());

        // get details
//        holder.linear_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatMua datMua = listGiohang.get(i);
//                Intent intent = new Intent(context, EditGioHangActivity.class);
//                String masp = String.valueOf(datMua.getMasach());
//                String sanpham = datMua.getSanpham();
//                String gia = String.valueOf(datMua.getGia());
//                String soluong = String.valueOf(datMua.getSoluong());
//
//                intent.putExtra("masp", masp);
//                intent.putExtra("sanpham", sanpham);
//                intent.putExtra("gia", gia);
//                intent.putExtra("soluong", soluong);
//
//
//                context.startActivity(intent);
//            }
//        });

        holder.tv_soluongmua.setText(String.valueOf(listGiohang.get(i).getSoluong()));

        holder.btntang_sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soLuong++;
                String tt = String.valueOf(giaBan*soLuong);
                holder.tv_soluongmua.setText(String.valueOf(soLuong));
                updateSoluongTongtien(String.valueOf(soLuong),iduser, masach, "https://bansachonline.xyz/bansach/giohang/update_carts.php");
                if(listGiohang.get(i).getSelected() == true) {
                    //tongTienTungsach = listGiohang.get(i).getGia() * listGiohang.get(i).getSoluong();
                    //tongTienSach +=tongTienTungsach;
                    CartListFragment.txtTongtien.setText(String.valueOf(tongTienSach)+" VNĐ");
                }

            }
        });
        holder.btnGiam_sl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(soLuong>1){
                    soLuong--;
                    String tt = String.valueOf(giaBan*soLuong);
                    holder.tv_soluongmua.setText(String.valueOf(soLuong));
                    updateSoluongTongtien(String.valueOf(soLuong),iduser, masach, "https://bansachonline.xyz/bansach/giohang/update_carts.php");
                    if(listGiohang.get(i).getSelected() == true) {
                        //tongTienTungsach = listGiohang.get(i).getGia() * listGiohang.get(i).getSoluong();
                        CartListFragment.txtTongtien.setText(String.valueOf(tongTienSach)+" VNĐ");
                    }

                }
            }
        });
        holder.btn_iv_Xoa_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteGiohang(iduser, masach,"https://bansachonline.xyz/bansach/giohang/delete_carts.php");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGiohang.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_soluongmua,tv_giaban;
        private CheckBox checkBox;
        private LinearLayout  linear_cart;
        private ImageView img_cart;

        private ImageButton btnGiam_sl,btntang_sl;
        private Button btn_iv_Xoa_giohang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name_book_cart);
            tv_giaban=(TextView)itemView.findViewById(R.id.tv_giaban);
            checkBox=(CheckBox)itemView.findViewById(R.id.cb_giohang);
            linear_cart=(LinearLayout)itemView.findViewById(R.id.linear_cart);
            img_cart=(ImageView)itemView.findViewById(R.id.img_cart);

            btn_iv_Xoa_giohang= itemView.findViewById(R.id.btn_iv_Xoa_giohang);
            btnGiam_sl = itemView.findViewById(R.id.btnGiam_sl);
            btntang_sl = itemView.findViewById(R.id.btnTang_sl);
            tv_soluongmua= itemView.findViewById(R.id.tv_soluongmua);
        }
    }
    private void updateSoluongTongtien(final String soluong, final String mauser, final String masach, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("tb")){
                            Toast.makeText(context, "datontai", Toast.LENGTH_SHORT).show();
                        }else if (response.trim().equals("tc")){
                            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Loi roi nhe", Toast.LENGTH_SHORT).show();
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("soluong", soluong);
                params.put("mauser", mauser);
                params.put("masach", masach);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void deleteGiohang( final String mauser, final String masach, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("tb")){
                        }else if (response.trim().equals("tc")){
                            context.startActivity(new Intent(context, Main2Activity.class));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Loi roi nhe", Toast.LENGTH_SHORT).show();
                Log.d("MYSQL", "Lỗi! \n" +error.toString());
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("mauser", mauser);
                params.put("masach", masach);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
