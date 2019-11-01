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
import com.example.duan2muaban.Activity.BookDetailActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.SachAdapter;
import com.example.duan2muaban.adapter.SachTop123Adapter;
import com.example.duan2muaban.model.Books;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreFragment extends Fragment {
    public String URL_GETDATA ="http://hieuttpk808.000webhostapp.com/books/sach/getdata.php";
    public String URL_GETDATA_TOP123 ="http://hieuttpk808.000webhostapp.com/books/sach/getdatatop3.php";
    private List<Books> listBook ;
    private List<Books> listBookTop123 ;
    private RecyclerView recyclerView,recyclerview_book_top123;
    SessionManager sessionManager;
    View v;
    private TextView textViewTB;

    SachAdapter sachAdapter;
    SachTop123Adapter sachTop123Adapter;
    public StoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_store, container, false);
        recyclerView=v.findViewById(R.id.book_recyclerview);
        recyclerview_book_top123 =v.findViewById(R.id.recyclerview_book_top123);
        textViewTB=v.findViewById(R.id.txtThongbaonull);


        sachAdapter = new SachAdapter(getContext(), listBook);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sachAdapter);
        sessionManager = new SessionManager(getContext());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
        public void onClick(View view, int position) {
            Books books =   listBook.get(position);
            String id = String.valueOf(books.getId());
            String matheloai = String.valueOf(books.getMatheloai());
            String macuahang = String.valueOf(books.getMacuahang());
            String tensach = books.getTensach();
            String hinhanh = books.getHinhanh();
            String chitiet =books.getChitiet();
            String giaban = String.valueOf( books.getGiaban());
            String tongdiem= String.valueOf(books.getTongdiem());
            String landanhgia = String.valueOf(books.getLandanhgia());

//                Toast.makeText(getContext(), "Ten sach "+tensach+" ma sach: "+id, Toast.LENGTH_SHORT).show();
            sessionManager.createSessionSendInfomationBook(id,matheloai,macuahang,tensach,hinhanh,chitiet,giaban,tongdiem,landanhgia);
            startActivity(new Intent(getContext(), BookDetailActivity.class));
//                goToBookDetail();
        }

        @Override
        public void onLongClick(View view, int position) {

        }
    }));
        //top 123 book
        sachTop123Adapter = new SachTop123Adapter(getContext(), listBookTop123);
        recyclerview_book_top123.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerview_book_top123.setAdapter(sachTop123Adapter);

        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listBook = new ArrayList<>();
        listBookTop123 = new ArrayList<>();

        GetData(URL_GETDATA);
        GetDataTop123(URL_GETDATA_TOP123);


    }
    public void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listBook.clear();
                        if (response.length() > 0){
                            recyclerView.setVisibility(View.VISIBLE);
                            textViewTB.setVisibility(View.GONE);
                        }else{
                            recyclerView.setVisibility(View.GONE);
                            textViewTB.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listBook.add(new Books(
                                        object.getInt("ID"),
                                        object.getInt("MaTheLoai"),
                                        object.getInt("MaCuaHang"),
                                        object.getString("TenSach"),
                                        object.getString("HinhAnh"),
                                        object.getString("ChiTiet"),
                                        object.getDouble("GiaBan"),
                                        object.getDouble("TongDiem"),
                                        object.getInt("LanDanhGia"),
                                        object.getString("LinkBook")
                                ));
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        sachAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    public void GetDataTop123(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listBookTop123.clear();
                        if (response.length() > 0){
                            recyclerView.setVisibility(View.VISIBLE);
                            textViewTB.setVisibility(View.GONE);
                        }else{
                            recyclerView.setVisibility(View.GONE);
                            textViewTB.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listBookTop123.add(new Books(
                                        object.getInt("ID"),
                                        object.getInt("MaTheLoai"),
                                        object.getInt("MaCuaHang"),
                                        object.getString("TenSach"),
                                        object.getString("HinhAnh"),
                                        object.getString("ChiTiet"),
                                        object.getDouble("GiaBan"),
                                        object.getDouble("TongDiem"),
                                        object.getInt("LanDanhGia"),
                                        object.getString("LinkBook")
                                ));
                            }catch (JSONException e){
                                e.printStackTrace();
                                Log.e("Error e: ", e.toString());
                                //Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        sachTop123Adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
//    private void goToBookDetail(){
//        Fragment fragment = new BookDetailFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container,fragment);
//        ft.commit();
//    }
}
