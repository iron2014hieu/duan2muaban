package com.example.duan2muaban.ApiRetrofit.InTerFace;


import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.model.Nhaxuatban;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInTerFaceNXB {
    @GET("nxb/getNXB.php")
    Call<List<Nhaxuatban>> getNXB();
}
