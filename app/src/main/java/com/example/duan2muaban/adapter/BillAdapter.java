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
import com.example.duan2muaban.model.Hoadon;

import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {

    Context context;
    List<Hoadon> mData;
    Dialog myDialog;

    public BillAdapter(Context context, List<Hoadon> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_thuvien, viewGroup, false);

        final MyViewHolder viewHolder= new MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        myViewHolder.tv_name.setText(mData.get(i).getTenSach());
        myViewHolder.txtKetquadanhgia.setText("");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,txtKetquadanhgia;
        private ImageView img_book_profile;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=(TextView)itemView.findViewById(R.id.tv_name_book_cart);
            txtKetquadanhgia=(TextView)itemView.findViewById(R.id.txtKetquadanhgia);
            img=(ImageView) itemView.findViewById(R.id.img_book_profile);
        }
    }
}
