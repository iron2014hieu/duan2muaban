package com.example.duan2muaban;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.CartAdapter;
import com.example.duan2muaban.model.Cart;
import com.example.duan2muaban.publicString.URL.UrlSql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {
    private UrlSql urlSql;
    private SessionManager sessionManager;
    String tensach,idBook,giaBan, iduser;
    private TextView txtTongtienall;
    private RecyclerView recyclerview_cart;
    private CartAdapter cartAdapter;
    private List<Cart> listCart = new ArrayList<>();
    View view;
    public CartListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cart_list, container, false);
        recyclerview_cart = view.findViewById(R.id.recyclerview_cart);
        txtTongtienall= view.findViewById(R.id.txtTongtienall);


//        Toast.makeText(getContext(), "idUser "+iduser, Toast.LENGTH_SHORT).show();




        cartAdapter = new CartAdapter(getContext(), listCart);
        recyclerview_cart.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_cart.setAdapter(cartAdapter);

        recyclerview_cart.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerview_cart, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Cart cart =   listCart.get(position);
                String id = String.valueOf(cart.getId());
                String idSach =String.valueOf(cart.getMasach());
                String idUser = String.valueOf(cart.getMaUser());
                String tenSach = cart.getTenSach();
                String giaBan = String.valueOf(cart.getGiaBan());
                String dathanhtoan = String.valueOf(cart.getDathanhtoan());

                sessionManager.createCart(idSach, idUser, tenSach, giaBan, dathanhtoan);
                Intent intent = new Intent(getContext(), CartDetailActivity.class);
                intent.putExtra("IDCART", id);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
        HashMap<String,String> user = sessionManager.getUserDetail();
        iduser = user.get(sessionManager.ID);


        String URl_GETDATA = urlSql.URl_GETDATA_CART+iduser;
        GetData(URl_GETDATA);
        GetAlltongtien(urlSql.URl_GETDATA_CART_ALL_MONEY+iduser);

    }

    public void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listCart.clear();
//                        if (response.length() > 0){
//                            recyclerview_cart.setVisibility(View.VISIBLE);
//                            textViewTB.setVisibility(View.GONE);
//                        }else{
//                            recyclerView.setVisibility(View.GONE);
//                            textViewTB.setVisibility(View.VISIBLE);
//                        }
                        Toast.makeText(getContext(), ""+response.length(), Toast.LENGTH_SHORT).show();
                        double tongtien=0.0;
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listCart.add(new Cart(
                                        object.getInt("ID"),
                                        object.getInt("MaSach"),
                                        object.getInt("MaUser"),
                                        object.getString("TenSach"),
                                        object.getString("GiaBan"),
                                        object.getInt("DaThanhToan")
                                ));
                                String giaBan = listCart.get(i).getGiaBan();
                                tongtien+= Double.valueOf(giaBan);
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        cartAdapter.notifyDataSetChanged();
                        txtTongtienall.setText(""+tongtien +" VNĐ");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void GetAlltongtien(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Double tt=0.0;
                        for (int i = 0; i < response.length(); i++){

                            try {
                                JSONObject object = response.getJSONObject(i);
                                Double giaBan = object.getDouble("GiaBan");
                                int dathanhtoan = 0;
                                 tt += giaBan;

                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
