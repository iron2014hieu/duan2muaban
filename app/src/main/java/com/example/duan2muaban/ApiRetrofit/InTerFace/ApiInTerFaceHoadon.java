package com.example.duan2muaban.ApiRetrofit.InTerFace;

import com.example.duan2muaban.model.CTHD;
import com.example.duan2muaban.model.Hoadon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInTerFaceHoadon {
    @GET("hoadon/get_choxacnhan.php")
    Call<List<Hoadon>> get_choxacnhan(@Query("mauser") String keyword);
    @GET("hoadon/get_cholayhang.php")
    Call<List<Hoadon>> get_cholayhang(@Query("mauser") String keyword);
    @GET("hoadon/get_danggiao.php")
    Call<List<Hoadon>> get_danggiao(@Query("mauser") String keyword);
    @GET("hoadon/get_danhgia.php")
    Call<List<Hoadon>> get_danhgia(@Query("mauser") String keyword);
    //cthd
    @GET("hoadon/get_cthd_bymahd.php")
    Call<List<CTHD>> get_cthd_bymahd(@Query("mahd") String keyword);
    @GET("hoadon/get_library_user.php")
    Call<List<CTHD>> get_library_user(@Query("mauser") String keyword);
}
