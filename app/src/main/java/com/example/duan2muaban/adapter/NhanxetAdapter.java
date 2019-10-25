package com.example.duan2muaban.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan2muaban.R;
import com.example.duan2muaban.model.Hoadon;

import java.util.List;

public class NhanxetAdapter extends RecyclerView.Adapter<NhanxetAdapter.MyViewHolder> {

    Context context;
    List<Hoadon> mData;

    public NhanxetAdapter(Context context, List<Hoadon> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_nhanxet, viewGroup, false);

        final MyViewHolder viewHolder= new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String diem = String.valueOf(mData.get(i).getDiemdanhgia());
        myViewHolder.txtNameuser_item.setText(mData.get(i).getTenUser());
        myViewHolder.txtLoinhanxet_item.setText(mData.get(i).getNhanxet());
        myViewHolder.ratingbar_cuaban_item.setRating(Float.valueOf(diem));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout item_comment;
        private TextView txtNameuser_item;
        private TextView txtLoinhanxet_item;
        RatingBar ratingbar_cuaban_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_comment=(LinearLayout)itemView.findViewById(R.id.linearlayoutnx);
            txtNameuser_item=(TextView)itemView.findViewById(R.id.txtNameuser_item);
            txtLoinhanxet_item=(TextView)itemView.findViewById(R.id.txtLoinhanxet_item);
            ratingbar_cuaban_item=(RatingBar) itemView.findViewById(R.id.ratingbar_cuaban_item);
        }
    }
}
