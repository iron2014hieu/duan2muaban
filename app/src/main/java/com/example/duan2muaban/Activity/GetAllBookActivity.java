package com.example.duan2muaban.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.InTerFace.ApiInTerFace;
import com.example.duan2muaban.R;
import com.example.duan2muaban.adapter.Sach.SachAdapter;
import com.example.duan2muaban.model.Books;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class GetAllBookActivity extends AppCompatActivity {
    private RecyclerView recyclerview_book_all;
    private ApiInTerFace apiInTerFace;
    SachAdapter sachAdapter;
    private List<Books> listBook = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_book);
        recyclerview_book_all=findViewById(R.id.recyclerview_book_all);

        sachAdapter = new SachAdapter(this, listBook);

        // lấy sách
        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_book_all.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_book_all.setHasFixedSize(true);


        fetchBookall();

    }
    public void fetchBookall() {
        apiInTerFace = ApiClient.getApiClient().create(ApiInTerFace.class);
        Call<List<Books>> call = apiInTerFace.getBookDESC();

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, retrofit2.Response<List<Books>> response) {
                //progressBar.setVisibility(View.GONE);
                listBook= response.body();
                sachAdapter = new SachAdapter(GetAllBookActivity.this,listBook);
                recyclerview_book_all.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
}
