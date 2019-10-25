package com.example.duan2muaban.LiveSearch;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://hieuttpk808.000webhostapp.com/books/sach/";
    public static Retrofit retrofit;
    public static Retrofit getApiClient(){
        if (retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
