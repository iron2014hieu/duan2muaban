package com.example.duan2muaban.fragmentMain;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.duan2muaban.Activity.BookDetailPayActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.BillAdapter;
import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.model.Hoadon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {
    private TextView txtLoikhuyen, txtDencuahang;
    private RecyclerView recyclerview_bills;
    private List<Hoadon> listBill = new ArrayList<>();
    private List<Books> listBooks = new ArrayList<>();
    private BillAdapter billAdapter;
    private SessionManager sessionManager;
    private String mauser;
    View view;



    public LibraryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_library, container, false);
        addControls();
        sessionManager = new SessionManager(getContext());
        billAdapter = new BillAdapter(getContext(), listBill);
        recyclerview_bills.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview_bills.setAdapter(billAdapter);

        recyclerview_bills.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerview_bills, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Hoadon hoadon =   listBill.get(position);
                String idBill = String.valueOf(hoadon.getId());
                String idSach =String.valueOf(hoadon.getMasach());
                String tenUser = hoadon.getTenUser();

                sessionManager.createBill(idBill,idSach, tenUser);
                startActivity(new Intent(getContext(), BookDetailPayActivity.class));
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
        mauser = user.get(sessionManager.ID);

        String URL_GET_HOADOWN = "https://hieuttpk808.000webhostapp.com/books/cart_bill/getdatabill.php/?mauser="+mauser;
        GetData(URL_GET_HOADOWN);

       // Toast.makeText(getContext(), ""+listBill.size(), Toast.LENGTH_SHORT).show();
    }

    public void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listBill.clear();
                        if (response.length() > 0){
                            recyclerview_bills.setVisibility(View.VISIBLE);
                            txtDencuahang.setVisibility(View.GONE);
                            txtLoikhuyen.setVisibility(View.GONE);
                        }else{
                            recyclerview_bills.setVisibility(View.GONE);
                            txtDencuahang.setVisibility(View.VISIBLE);
                            txtLoikhuyen.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listBill.add(new Hoadon(
                                        object.getInt("ID"),
                                        object.getInt("MaSach"),
                                        object.getInt("MaUser"),
                                        object.getString("TenSach"),
                                        object.getString("TenUser"),
                                        object.getString("GiaBan"),
                                        object.getString("NhanXet"),
                                        object.getInt("DaThanhToan"),
                                        object.getDouble("DiemDanhGia"),
                                        object.getInt("HienThi")
                                ));
                            }catch (JSONException e){
                                e.printStackTrace();
                                Log.e("Loi e: ", e.toString());
                            }
                        }
                        billAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void addControls(){
        txtLoikhuyen=view.findViewById(R.id.txtLoigioithieu);
        txtDencuahang=view.findViewById(R.id.txtDencuahang);

        recyclerview_bills = view.findViewById(R.id.recyclerview_bills);
    }
}
