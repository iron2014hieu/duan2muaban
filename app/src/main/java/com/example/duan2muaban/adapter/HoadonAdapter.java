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
import com.example.duan2muaban.model.Hoadon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HoadonAdapter extends RecyclerView.Adapter<HoadonAdapter.MyViewHolder> {

    Context context;
    List<Hoadon> mData;
    Dialog myDialog;
    private ProductItemActionListener actionListener;

    public HoadonAdapter(Context context, List<Hoadon> mData) {
        this.context = context;
        this.mData = mData;
    }
    public void setActionListener(ProductItemActionListener actionListener) {
        this.actionListener = actionListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, viewGroup, false);

        final MyViewHolder viewHolder= new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_hoadon_stt.setText("Hóa đơn "+mData.get(i).getMahoadon());
        myViewHolder.tv_tongtien.setText(mData.get(i).getTongtien()+"₫");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout item_contact;
        private TextView tv_hoadon_stt;
        private TextView tv_tongtien;
        private TextView txtXemchitiet;
        //tùy từng tab
        private TextView txtUoctinhnhan;
        private TextView txtUoctinhgiao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_hoadon_stt=(TextView)itemView.findViewById(R.id.tv_hoadon_stt);
            tv_tongtien=(TextView)itemView.findViewById(R.id.tv_tongtien);
            txtXemchitiet=(TextView) itemView.findViewById(R.id.txtXemchitiet);

            txtUoctinhnhan=(TextView) itemView.findViewById(R.id.txtUoctinhnhan);
            txtUoctinhgiao=(TextView) itemView.findViewById(R.id.txtUoctinhgiao);
        }
    }
    public interface ProductItemActionListener{
        void onItemTap(ImageView imageView);
    }
}
