package com.example.duan2muaban.adapter.hoadoncthd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.model.CTHD;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyViewHolder> {

    Context context;
    List<CTHD> mData;
    private ProductItemActionListener actionListener;
    SessionManager sessionManager;
    public LibraryAdapter(Context context, List<CTHD> mData) {
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
        view = LayoutInflater.from(context).inflate(R.layout.item_thuvien, viewGroup, false);
        sessionManager = new SessionManager(context);
        final MyViewHolder viewHolder= new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int i) {
        holder.tv_name_book_lib.setText(mData.get(i).getTensach());

        try {
            Picasso.with(context).load(mData.get(i).getHinhanh()).into(holder.img_book_lib);
        }catch (Exception e){}

    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name_book_lib;

        private ImageView img_book_lib;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name_book_lib=(TextView)itemView.findViewById(R.id.tv_name_book_lib);
            img_book_lib=(ImageView) itemView.findViewById(R.id.img_book_lib);

        }
    }
    public interface ProductItemActionListener{
        void onItemTap(ImageView imageView);
    }
}
