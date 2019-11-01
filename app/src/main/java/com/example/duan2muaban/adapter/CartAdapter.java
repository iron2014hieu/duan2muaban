package com.example.duan2muaban.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Activity.BookDetailPayActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.model.Cart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    String matheloai,macuahang,tensach,hinhanh,chitiet,giaban,tongdiem,landanhgia;
    String URL_READ_DEATIL="http://hieuttpk808.000webhostapp.com/books/sach/read_detail_book.php";
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
        getDetailBookPay(String.valueOf(mData.get(i).getMasach()));
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
    public void getDetailBookPay(final String id){
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ_DEATIL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray jsonArray = jsonobject.getJSONArray("books_table");
                            JSONObject data = jsonArray.getJSONObject(0);

                            int id = data.getInt("id");
                             matheloai = String.valueOf(data.getInt("matheloai"));
                             macuahang = String.valueOf(data.getInt("macuahang"));
                            tensach = data.getString("tensach");
                            hinhanh = data.getString("hinhanh");
                            chitiet = data.getString("chitiet");
                             giaban = String.valueOf(data.getDouble("giaban"));
                            tongdiem = data.getString("tongdiem");
                            landanhgia = data.getString("landanhgia");
                            String linkBook = data.getString("linkbook");



                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(context,"loi e "+e, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
//                        Toast.makeText(context, "Error reading"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
