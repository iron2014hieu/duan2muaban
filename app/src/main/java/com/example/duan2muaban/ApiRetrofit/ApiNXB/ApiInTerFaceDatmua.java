package com.example.duan2muaban.ApiRetrofit.ApiNXB;


import com.example.duan2muaban.model.Cart;
import com.example.duan2muaban.model.DatMua;
import com.example.duan2muaban.model.Nhaxuatban;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInTerFaceDatmua {
    @GET("giohang/query_carts.php")
    Call<List<DatMua>> getDatMua();
}
