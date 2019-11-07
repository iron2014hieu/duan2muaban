package com.example.duan2muaban.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.duan2muaban.R;
import com.example.duan2muaban.model.Cart;
import com.example.duan2muaban.model.DatMua;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<DatMua> mData;
    Dialog myDialog;


    public CartAdapter(Context context, List<DatMua> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_giohang, viewGroup, false);

        final MyViewHolder viewHolder= new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_name.setText(mData.get(i).getSanpham());
        myViewHolder.tv_soluongmua.setText(mData.get(i).getGia()+" VNĐ");
        myViewHolder.img.setVisibility(View.GONE);
        
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_soluongmua;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=(TextView)itemView.findViewById(R.id.tv_name_book_cart);
            tv_soluongmua=(TextView)itemView.findViewById(R.id.tv_soluongmua);
            img=(ImageView) itemView.findViewById(R.id.img_delete_cart);
        }
    }

}
