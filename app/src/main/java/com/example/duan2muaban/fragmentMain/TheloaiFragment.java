package com.example.duan2muaban.fragmentMain;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Activity.GetBookByTheloaiActivity;
import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFaceHoadon;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.KhuyenMai.KhuyenMaiAdapter;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.adapter.hoadoncthd.HoadonAdapter;
import com.example.duan2muaban.model.Hoadon;
import com.example.duan2muaban.model.KhuyenMai;
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
public class TheloaiFragment extends Fragment {
    private RecyclerView recyclerview_khuyenmai;
    private KhuyenMaiAdapter khuyenMaiAdapter;
    private List<KhuyenMai> listHoadon = new ArrayList<>();
    ApiInTerFaceHoadon apiInTerFaceHoadon;
    private SessionManager sessionManager;
    View view;

    public TheloaiFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_theloai, container, false);

        addControls();
        sessionManager = new SessionManager(getContext());
        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_khuyenmai.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_khuyenmai.setHasFixedSize(true);
        fetchHoadon();
        return  view;
    }
    public void fetchHoadon(){
        apiInTerFaceHoadon = ApiClient.getApiClient().create(ApiInTerFaceHoadon.class);
        Call<List<KhuyenMai>> call = apiInTerFaceHoadon.get_all_khuyenmai();

        call.enqueue(new Callback<List<KhuyenMai>>() {
            @Override
            public void onResponse(Call<List<KhuyenMai>> call, retrofit2.Response<List<KhuyenMai>> response) {
                //progressBar.setVisibility(View.GONE);
                if (response.body().size() == 0){
                    recyclerview_khuyenmai.setVisibility(View.GONE);
                }else {
                    recyclerview_khuyenmai.setVisibility(View.VISIBLE);
                    listHoadon= response.body();
                    khuyenMaiAdapter = new KhuyenMaiAdapter(getContext(),listHoadon);
                    recyclerview_khuyenmai.setAdapter(khuyenMaiAdapter);
                    khuyenMaiAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<KhuyenMai>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    private void addControls(){
        recyclerview_khuyenmai = view.findViewById(R.id.recyclerview_khuyenmai);
    }
}
