package com.example.duan2muaban.LiveSearch;


import com.example.duan2muaban.model.Books;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInTerFace {
    @GET("getBooks.php")
    Call<List<Books>> getUsers(@Query("key") String keyword);
}
