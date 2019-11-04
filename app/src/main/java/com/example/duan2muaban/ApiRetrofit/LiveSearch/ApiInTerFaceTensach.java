package com.example.duan2muaban.ApiRetrofit.LiveSearch;


import com.example.duan2muaban.model.Books;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInTerFaceTensach {
    @GET("sach/getBooksTensach.php")
    Call<List<Books>> getUsers(@Query("key") String keyword);

    @GET("sach/getBooksDESC.php")
    Call<List<Books>> getNotes();
}
