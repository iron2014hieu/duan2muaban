package com.example.duan2muaban.Fragment.donhang;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFaceHoadon;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.hoadoncthd.HoadonAdapter;
import com.example.duan2muaban.model.Hoadon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class DangGiaoFragment extends Fragment {
    private RecyclerView recyclerview_danggiao;
    private HoadonAdapter hoadonAdapter;
    private List<Hoadon> listHoadon = new ArrayList<>();
    private ApiInTerFaceHoadon apiInTerFaceHoadon;
    private SessionManager sessionManager;
    private String mauser;
    private TextView txtBill_empty_danggiao;
    public DangGiaoFragment() {
        // Required empty public constructor
    }
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_dang_giao, container, false);
        addControls();
        sessionManager = new SessionManager(getContext());
        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_danggiao.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_danggiao.setHasFixedSize(true);

        HashMap<String,String> user = sessionManager.getUserDetail();
        mauser = user.get(sessionManager.ID);

        fetchHoadon(mauser);
        return v;
    }
    public void fetchHoadon(String miduser){
        apiInTerFaceHoadon = ApiClient.getApiClient().create(ApiInTerFaceHoadon.class);
        Call<List<Hoadon>> call = apiInTerFaceHoadon.get_danggiao(miduser);

        call.enqueue(new Callback<List<Hoadon>>() {
            @Override
            public void onResponse(Call<List<Hoadon>> call, retrofit2.Response<List<Hoadon>> response) {
                if (response.body().size() == 0){
                    txtBill_empty_danggiao.setVisibility(View.VISIBLE);
                    recyclerview_danggiao.setVisibility(View.GONE);
                }else {
                    txtBill_empty_danggiao.setVisibility(View.GONE);
                    recyclerview_danggiao.setVisibility(View.VISIBLE);
                    //progressBar.setVisibility(View.GONE);
                    listHoadon= response.body();
                    hoadonAdapter = new HoadonAdapter(getContext(),listHoadon);
                    recyclerview_danggiao.setAdapter(hoadonAdapter);
                    hoadonAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<Hoadon>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    private void addControls(){
        recyclerview_danggiao = v.findViewById(R.id.recyclerview_danggiao);
        txtBill_empty_danggiao=v.findViewById(R.id.txtBill_empty_danggiao);
    }
}
