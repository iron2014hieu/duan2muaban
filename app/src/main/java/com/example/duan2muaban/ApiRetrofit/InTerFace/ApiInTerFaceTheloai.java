package com.example.duan2muaban.ApiRetrofit.InTerFace;

import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.model.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInTerFaceTheloai {
    @GET("theloai/getTheloai.php")
    Call<List<TheLoai>> getTheloai();
}
