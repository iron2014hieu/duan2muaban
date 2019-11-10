package com.example.duan2muaban.adapter.hoadoncthd;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan2muaban.Activity.hoadon.ChitiethoadonActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.model.CTHD;
import com.example.duan2muaban.model.Hoadon;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CTHDAdapter extends RecyclerView.Adapter<CTHDAdapter.MyViewHolder> {

    Context context;
    List<CTHD> mData;
    Dialog myDialog;
    private ProductItemActionListener actionListener;

    public CTHDAdapter(Context context, List<CTHD> mData) {
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
        view = LayoutInflater.from(context).inflate(R.layout.item_cthd, viewGroup, false);

        final MyViewHolder viewHolder= new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        holder.txt_books_name_cthd.setText("Sách "+mData.get(i).getTensach());
        holder.txt_giaban_cthd.setText(mData.get(i).getGiaban()+"₫");
        holder.txt_soluong_cthd.setText("x"+mData.get(i).getSoluong());
        try {
            Picasso.with(context).load(mData.get(i).getHinhanh()).into(holder.img_cthd);
        }catch (Exception e){

        }
//        holder.txtXemchitiet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, ChitiethoadonActivity.class);
//                String mahd = String.valueOf(mData.get(i).getMahoadon());
//                intent.putExtra("mahd", mahd);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_soluong_cthd;
        private TextView txt_books_name_cthd;
        private TextView txt_giaban_cthd;

        private ImageView img_cthd;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_soluong_cthd=(TextView)itemView.findViewById(R.id.txt_soluong_cthd);
            txt_books_name_cthd=(TextView)itemView.findViewById(R.id.txt_books_name_cthd);
            txt_giaban_cthd=(TextView) itemView.findViewById(R.id.txt_giaban_cthd);

            img_cthd=(ImageView) itemView.findViewById(R.id.img_cthd);
        }
    }
    public interface ProductItemActionListener{
        void onItemTap(ImageView imageView);
    }
}
