package com.example.duan2muaban.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.example.duan2muaban.Activity.BookDetailPayActivity;
import com.example.duan2muaban.Activity.EditGioHangActivity;
import com.example.duan2muaban.CartListFragment;
import com.example.duan2muaban.R;
import com.example.duan2muaban.model.Cart;
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

        holder.checkBox.setText("haha "+i);
        holder.checkBox.setChecked(listGiohang.get(i).getSelected());// nếu tự tạo sẻ là isSelected
        holder.tv_name.setText(listGiohang.get(i).getSanpham());
        holder.tv_soluongmua.setText(listGiohang.get(i).getGia()+" VNĐ");
        Picasso.with(context).load(listGiohang.get(i).getHinhanh()).into(holder.img_cart);

        holder.checkBox.setTag(i);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer pos = (Integer) holder.checkBox.getTag();
                if (isChecked) {
                    listGiohang.get(pos).setSelected(true);
                        if(listGiohang.get(i).getSelected() == true) {
                            tongTienSach +=listGiohang.get(i).getTongtien();
                            CartListFragment.txtTongtien.setText(String.valueOf(tongTienSach));
                        //Toast.makeText(context, listGiohang.get(pos).getSanpham() + " vừa ĐƯỢC chọn! tt "+tongTienSach, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    listGiohang.get(pos).setSelected(false);
                        if (listGiohang.get(i).getSelected()== false){
                            tongTienSach -=listGiohang.get(i).getTongtien();
                            CartListFragment.txtTongtien.setText(String.valueOf(tongTienSach));
                        //Toast.makeText(context, listGiohang.get(pos).getSanpham() + " vừa BỎ chọn! tt"+ tongTienSach, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });




        // get details
        holder.linear_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatMua datMua = listGiohang.get(i);
                Intent intent = new Intent(context, EditGioHangActivity.class);
                String masp = String.valueOf(datMua.getMasach());
                String sanpham = datMua.getSanpham();
                String gia = String.valueOf(datMua.getGia());
                String soluong = String.valueOf(datMua.getSoluong());
                String tongtien = String.valueOf(datMua.getTongtien());

                intent.putExtra("masp", masp);
                intent.putExtra("sanpham", sanpham);
                intent.putExtra("gia", gia);
                intent.putExtra("soluong", soluong);
                intent.putExtra("tongtien", tongtien);

                Toast.makeText(context, ""+masp+sanpham+gia+soluong+tongtien, Toast.LENGTH_SHORT).show();

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGiohang.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_soluongmua;
        private CheckBox checkBox;
        private LinearLayout  linear_cart;
        private ImageView img_cart;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name_book_cart);
            tv_soluongmua=(TextView)itemView.findViewById(R.id.tv_soluongmua);
            checkBox=(CheckBox)itemView.findViewById(R.id.cb_giohang);
            linear_cart=(LinearLayout)itemView.findViewById(R.id.linear_cart);
            img_cart=(ImageView)itemView.findViewById(R.id.img_cart);
        }
    }

}
