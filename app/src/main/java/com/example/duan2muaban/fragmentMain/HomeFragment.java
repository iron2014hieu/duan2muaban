package com.example.duan2muaban.fragmentMain;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Activity.GetBookByTheloaiActivity;
import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.LiveSearch.ApiInTerFace;
import com.example.duan2muaban.ApiRetrofit.LiveSearch.ApiInTerFaceDesc;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.SachAdapter;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.model.TheLoai;
import com.example.duan2muaban.publicString.URL.UrlSql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private SearchView searchView;
    TheLoaiAdapter theLoaiAdapter;
    SachAdapter sachAdapter;
    ProgressBar progressBar;
    private List<TheLoai> listTheloai = new ArrayList<>();
    private List<Books> listBookhome = new ArrayList<>();
    private RecyclerView recyclerview_book_home;
    private ApiInTerFaceDesc apiInTerFaceDesc;
    View view;

    SessionManager sessionManager;
    public HomeFragment() {
        // Required empty public constructor
    }
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerview_book_home=view.findViewById(R.id.recyclerview_book_home);
        searchView=view.findViewById(R.id.searchview);
        progressBar = view.findViewById(R.id.progress);

        sessionManager = new SessionManager(getContext());
        sachAdapter = new SachAdapter(getContext(), listBookhome);


        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_book_home.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_book_home.setHasFixedSize(true);



        try {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    fetchUser(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    fetchUser(newText);
                    return false;
                }
            });
        }catch (Exception e){
            Log.e("SEARCH", e.toString());
        }
        return view;
    }

    public void fetchUser(String key){
        apiInTerFaceDesc = ApiClient.getApiClient().create(ApiInTerFaceDesc.class);
        Call<List<Books>> call = apiInTerFaceDesc.getUsers(key);

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, retrofit2.Response<List<Books>> response) {
                progressBar.setVisibility(View.GONE);
                listBookhome= response.body();
                sachAdapter = new SachAdapter(getContext(),listBookhome);
                recyclerview_book_home.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//    public void refreshString(String string) {
//        mTextView.setText(string);
//    }
}
