package com.example.duan2muaban.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan2muaban.R;
import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.model.Nhaxuatban;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NhaxuatbanAdapter extends RecyclerView.Adapter<NhaxuatbanAdapter.MyViewHolder> {

    Context context;
    List<Nhaxuatban> mData;

    public NhaxuatbanAdapter(Context context, List<Nhaxuatban> mData) {
        this.context = context;
        this.mData = mData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_nhaxuatban, viewGroup, false);

        final MyViewHolder viewHolder= new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_name.setText(mData.get(i).getTennxb());
        String urlImage = mData.get(i).getHinhanh();
        Picasso.with(context).load(urlImage).into(myViewHolder.img);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_mota;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name=(TextView)itemView.findViewById(R.id.tvname_nxb);
//            tv_mota=(TextView)itemView.findViewById(R.id.books_chitiet);
            img=(ImageView) itemView.findViewById(R.id.img_nxb);
        }
    }
}
