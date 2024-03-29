package com.example.duan2muaban.adapter.KhuyenMai;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan2muaban.MainActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.model.KhuyenMai;
import com.example.duan2muaban.model.TheLoai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.MyViewHolder>{

    Context context;
    List<KhuyenMai> mData;

    public KhuyenMaiAdapter(Context context, List<KhuyenMai> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public KhuyenMaiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_khuyenmai, viewGroup, false);
        final KhuyenMaiAdapter.MyViewHolder viewHolder= new KhuyenMaiAdapter.MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull KhuyenMaiAdapter.MyViewHolder myViewHolder,final int i) {
        myViewHolder.tvTenKM.setText(mData.get(i).getTenKM());
        myViewHolder.tvMaKM.setText(mData.get(i).getMaKM());
        myViewHolder.tvTinhChat.setText(mData.get(i).getTinhChat());
        myViewHolder.tvNgayBatDau.setText(mData.get(i).getNgayBatDau());
        myViewHolder.tvNgayKetThuc.setText(mData.get(i).getHangSuDung());

        myViewHolder.img_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipData clip = ClipData.newPlainText("makm",  mData.get(i).getMaKM());
                MainActivity.clipboardManager.setPrimaryClip(clip);
                Toast.makeText(context, "Đã sao chép mã "+mData.get(i).getMaKM(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTenKM, tvMaKM, tvTinhChat, tvNgayBatDau, tvNgayKetThuc;

        ImageView img_copy;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTenKM =(TextView)itemView.findViewById(R.id.tvTenKM);
            tvMaKM =(TextView)itemView.findViewById(R.id.tvMaKM);
            tvTinhChat =(TextView)itemView.findViewById(R.id.tvTinhChat);
            tvNgayBatDau =(TextView)itemView.findViewById(R.id.tvNgayBatDau);
            tvNgayKetThuc =(TextView)itemView.findViewById(R.id.tvNgayKetThuc);
            img_copy = itemView.findViewById(R.id.img_copy);
        }
    }

}

