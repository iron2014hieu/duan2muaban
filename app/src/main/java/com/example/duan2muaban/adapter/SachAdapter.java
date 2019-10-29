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
import com.squareup.picasso.Picasso;

import java.util.List;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.MyViewHolder> {

    Context context;
    List<Books> mData;
    Dialog myDialog;
    private ProductItemActionListener actionListener;

    public SachAdapter(Context context, List<Books> mData) {
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
        view = LayoutInflater.from(context).inflate(R.layout.item_sach, viewGroup, false);

        final MyViewHolder viewHolder= new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_name.setText(mData.get(i).getTensach());
        myViewHolder.tv_phone.setText(mData.get(i).getChitiet());
        myViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actionListener!=null)
                    actionListener.onItemTap(myViewHolder.img);
            }
        });
        try {
            String urlImage = mData.get(i).getHinhanh();
            if (urlImage==null){
                myViewHolder.img.setImageResource(R.drawable.profile2);
            }else {
                Picasso.with(context).load(urlImage).into(myViewHolder.img);
            }
        }catch (Exception e){
            Log.e("IMG", e.toString());
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout item_contact;
        private TextView tv_name;
        private TextView tv_phone;
        ImageView img, favorite, un_favorite;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_contact=(LinearLayout)itemView.findViewById(R.id.contact_item);
            tv_name=(TextView)itemView.findViewById(R.id.books_name);
            tv_phone=(TextView)itemView.findViewById(R.id.books_chitiet);
            img=(ImageView) itemView.findViewById(R.id.img_book_iv);
            favorite=(ImageView) itemView.findViewById(R.id.favorite);
            un_favorite=(ImageView) itemView.findViewById(R.id.un_favorite);
        }
    }
    public interface ProductItemActionListener{
        void onItemTap(ImageView imageView);
    }
}
