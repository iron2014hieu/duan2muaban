package com.example.duan2muaban.ApiRetrofit.ApiTacgia;


import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.model.Tacgia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInTerFaceTacgia {
    @GET("tacgia/getTacgia.php")
    Call<List<Tacgia>> getTacgia();

}
