package com.example.duan2muaban.ApiRetrofit.ApiThongtinKH;


import com.example.duan2muaban.model.Books;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInTerFaceThongtinKH {
    @GET("sach/getBooksBytensach.php")
    Call<List<Books>> getUsers(@Query("key") String keyword);
}
