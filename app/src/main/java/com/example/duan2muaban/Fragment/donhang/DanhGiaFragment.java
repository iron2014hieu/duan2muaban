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
import com.example.duan2muaban.adapter.hoadoncthd.HoadonRatingAdapter;
import com.example.duan2muaban.model.Hoadon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class DanhGiaFragment extends Fragment {
    private RecyclerView recyclerview_danhgia;
    private HoadonRatingAdapter hoadonAdapter;
    private List<Hoadon> listHoadon = new ArrayList<>();
    ApiInTerFaceHoadon apiInTerFaceHoadon;
    private SessionManager sessionManager;
    String mauser;
    TextView txtBill_empty_danhgia;
    public DanhGiaFragment() {
        // Required empty public constructor
    }
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_danh_gia, container, false);
        addControls();
        sessionManager = new SessionManager(getContext());
        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_danhgia.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_danhgia.setHasFixedSize(true);

        HashMap<String,String> user = sessionManager.getUserDetail();
        mauser = user.get(sessionManager.ID);

        fetchHoadon(mauser);

        return v;
    }
    public void fetchHoadon(String miduser){
        apiInTerFaceHoadon = ApiClient.getApiClient().create(ApiInTerFaceHoadon.class);
        Call<List<Hoadon>> call = apiInTerFaceHoadon.get_danhgia(miduser);

        call.enqueue(new Callback<List<Hoadon>>() {
            @Override
            public void onResponse(Call<List<Hoadon>> call, retrofit2.Response<List<Hoadon>> response) {
                if (response.body().size() == 0){
                    txtBill_empty_danhgia.setVisibility(View.VISIBLE);
                    recyclerview_danhgia.setVisibility(View.GONE);
                }else {
                    txtBill_empty_danhgia.setVisibility(View.GONE);
                    recyclerview_danhgia.setVisibility(View.VISIBLE);
                    //progressBar.setVisibility(View.GONE);
                    listHoadon= response.body();
                    hoadonAdapter = new HoadonRatingAdapter(getContext(),listHoadon);
                    recyclerview_danhgia.setAdapter(hoadonAdapter);
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
        recyclerview_danhgia = v.findViewById(R.id.recyclerview_danhgia);
        txtBill_empty_danhgia = v.findViewById(R.id.txtBill_empty_danhgia);
    }
}
