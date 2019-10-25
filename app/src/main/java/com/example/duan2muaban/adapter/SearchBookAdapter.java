package com.example.duan2muaban.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan2muaban.R;
import com.example.duan2muaban.model.Books;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchBookAdapter extends RecyclerView.Adapter<SearchBookAdapter.MyViewHolder>{
    private List<Books> users;
    private Context context;

    public SearchBookAdapter(List<Books> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.name.setText(users.get(position).getTensach());
        holder.email.setText(users.get(position).getChitiet());


        try {
            String urlImage = users.get(position).getHinhanh();
            if (urlImage==null){
                holder.img.setImageResource(R.drawable.book);
            }else {
                Picasso.with(context).load(urlImage).into(holder.img);
            }

        }catch (Exception e){
            Log.e("IMG", e.toString());
        }

        holder.un_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Thêm yêu thich "+users.get(position).getTensach(), Toast.LENGTH_SHORT).show();
                holder.un_favorite.setVisibility(View.GONE);
                holder.favorite.setVisibility(View.VISIBLE);
            }
        });
        holder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Xóa yêu thich "+users.get(position).getTensach(), Toast.LENGTH_SHORT).show();
                holder.un_favorite.setVisibility(View.VISIBLE);
                holder.favorite.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, email;
        ImageView img, favorite, un_favorite;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.books_name);
            email = itemView.findViewById(R.id.books_chitiet);
            favorite=(ImageView) itemView.findViewById(R.id.favorite);
            un_favorite=(ImageView) itemView.findViewById(R.id.un_favorite);
            img = itemView.findViewById(R.id.img_book_iv);
        }
    }
}
