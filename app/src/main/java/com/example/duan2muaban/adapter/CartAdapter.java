package com.example.duan2muaban.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan2muaban.R;
import com.example.duan2muaban.model.Cart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<Cart> mData;
    Dialog myDialog;


    public CartAdapter(Context context, List<Cart> mData) {
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
        myViewHolder.tv_name.setText(mData.get(i).getTenSach());
        myViewHolder.tv_soluongmua.setText(mData.get(i).getGiaBan()+" VNĐ");
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
