package com.example.duan2muaban;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox checkbox_cartlist;
    int sizeList;
    public  static TextView txtTongtien;
    private Button btnnext;
    int tongTien =0;
    String quyen, name, idUser;
    SessionManager sessionManager;
    int getTongtien;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart_list, container, false);

        cartAdapter  = new CartAdapter(getContext(), listDatmua);
        recyclerView_dat_mua = view.findViewById(R.id.listDatmua);
        txtTongtien=view.findViewById(R.id.txtTongtien);
        btnnext = (Button) view.findViewById(R.id.next);
        checkbox_cartlist=(CheckBox) view.findViewById(R.id.checkbox_cartlist);

        sessionManager= new SessionManager(getContext());

        HashMap<String,String> user = sessionManager.getUserDetail();
        quyen = user.get(sessionManager.QUYEN);
        name = user.get(sessionManager.NAME);
        idUser = user.get(sessionManager.ID);

        tongTien += CartAdapter.tongTienSach;
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_dat_mua.setLayoutManager(gridLayoutManager);
        recyclerView_dat_mua.setHasFixedSize(true);
        fetchDatmua(idUser);
//        txtTongtien.setText(String.valueOf(CartAdapter.tongTienSach));
//        for(int i = 0; i < sizeList ; i++){
//            if(CartAdapter.listGiohang.get(i).getSelected() == false) {
//                tongtien+=listDatmua.get(i).getTongtien();
//                txtTongtien.setText("Tổng tiền: "+tongtien+ " VNĐ");
//            }
//
//        }
        checkbox_cartlist.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    listDatmua = getModel(true);
                    cartAdapter = new CartAdapter(getContext(),listDatmua);
                    recyclerView_dat_mua.setAdapter(cartAdapter);

                    for (int i = 0; i< sizeList;i++){
                        if (listDatmua.get(i).getSelected() == true){
                            getTongtien = listDatmua.get(i).getGia()*listDatmua.get(i).getSoluong();
                            tongTien += getTongtien;
                            txtTongtien.setText(String.valueOf(tongTien));
                        }
                    }
                }else {
                    listDatmua = getModel(false);
                    cartAdapter = new CartAdapter(getContext(),listDatmua);
                    recyclerView_dat_mua.setAdapter(cartAdapter);

                    for (int i = 0; i< sizeList;i++){
                        if (listDatmua.get(i).getSelected() == false){
                            getTongtien = listDatmua.get(i).getGia()*listDatmua.get(i).getSoluong();
                            tongTien -= getTongtien;
                            txtTongtien.setText(String.valueOf(0));
                        }
                    }
                }
            }
        });

        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),CartDetailActivity.class);
                Toast.makeText(getContext(), "Tiền: "+tongTien, Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        return view;
    }


    public void fetchDatmua(String key){
        apiInTerFaceDatmua = ApiClient.getApiClient().create(ApiInTerFaceDatmua.class);
        Call<List<DatMua>> call = apiInTerFaceDatmua.getDatMua(key);

        call.enqueue(new Callback<List<DatMua>>() {
            @Override
            public void onResponse(Call<List<DatMua>> call, retrofit2.Response<List<DatMua>> response) {
                listDatmua= response.body();
                sizeList = listDatmua.size();
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
    private List<DatMua> getModel(boolean isSelect){
        for(int i = 0; i < sizeList ; i++){

            DatMua datMua = new DatMua();
            listDatmua.get(i).setSelected(isSelect);
//            listDatmua.add(datMua);
        }
        return listDatmua;
    }
}
