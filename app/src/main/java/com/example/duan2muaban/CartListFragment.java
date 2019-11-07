package com.example.duan2muaban;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Activity.EditGioHangActivity;
import com.example.duan2muaban.Activity.GetBookByTheloaiActivity;
import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.ApiNXB.ApiInTerFaceDatmua;
import com.example.duan2muaban.ApiRetrofit.ApiTacgia.ApiInTerFaceTacgia;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.CartAdapter;
import com.example.duan2muaban.adapter.TacgiaAdapter;
import com.example.duan2muaban.model.Cart;
import com.example.duan2muaban.model.DatMua;
import com.example.duan2muaban.model.Nhaxuatban;
import com.example.duan2muaban.model.Tacgia;
import com.example.duan2muaban.model.TheLoai;
import com.example.duan2muaban.publicString.URL.UrlSql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartListFragment extends Fragment {

    View view;
    public CartListFragment() {
        // Required empty public constructor
    }
    Toolbar toolbar;
    ApiInTerFaceDatmua apiInTerFaceDatmua;
    CartAdapter cartAdapter;
    RecyclerView recyclerView_dat_mua;
    private List<DatMua> listDatmua = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart_list, container, false);

        cartAdapter  = new CartAdapter(getContext(), listDatmua);
        recyclerView_dat_mua = view.findViewById(R.id.listDatmua);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_dat_mua.setLayoutManager(gridLayoutManager);
        recyclerView_dat_mua.setHasFixedSize(true);
        fetchDatmua();

        recyclerView_dat_mua.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView_dat_mua, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DatMua datMua = listDatmua.get(position);
                Intent intent = new Intent(getContext(), EditGioHangActivity.class);
                String masp = String.valueOf(datMua.getMasach());
                String sanpham = datMua.getSanpham();
                String gia = String.valueOf(datMua.getGia());
                String soluong = String.valueOf(datMua.getSoluong());
                String tongtien = String.valueOf(datMua.getTongtien());
                intent.putExtra("masp", masp);
                intent.putExtra("sanpham", sanpham);
                intent.putExtra("gia", gia);
                intent.putExtra("soluong", soluong);
                intent.putExtra("tongtien", tongtien);

                Toast.makeText(getContext(), ""+masp+sanpham+gia+soluong+tongtien, Toast.LENGTH_SHORT).show();

                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;
    } public void fetchDatmua(){
        apiInTerFaceDatmua = ApiClient.getApiClient().create(ApiInTerFaceDatmua.class);
        Call<List<DatMua>> call = apiInTerFaceDatmua.getDatMua();

        call.enqueue(new Callback<List<DatMua>>() {
            @Override
            public void onResponse(Call<List<DatMua>> call, retrofit2.Response<List<DatMua>> response) {
                listDatmua= response.body();
                cartAdapter = new CartAdapter(getContext(),listDatmua);
                recyclerView_dat_mua.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DatMua>> call, Throwable t) {
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
}
