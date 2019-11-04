package com.example.duan2muaban.fragmentMain;


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
    UrlSql urlSql = new UrlSql();
    public String URL_GETBYMACUAHNG = "http://hieuttpk808.000webhostapp.com/books/theloai/getdatabymacuahang.php/?macuahang=";
    String name,id,quyen;
    TheLoaiAdapter theLoaiAdapter;
    SachAdapter sachAdapter;
    ProgressBar progressBar;
    private List<TheLoai> listTheloai = new ArrayList<>();
    private List<Books> listBookhome = new ArrayList<>();
    private RecyclerView recyclerview_book_home;
    private Button btnDencuahangHome;
    private ApiInTerFace apiInTerFace;
    View v;
    private TextView textViewTB;

    SessionManager sessionManager;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerview_book_home = v.findViewById(R.id.recyclerview_book_home);
        textViewTB=v.findViewById(R.id.txtThongbaonull);
        progressBar= v.findViewById(R.id.progress);

        sessionManager = new SessionManager(getContext());
        theLoaiAdapter = new TheLoaiAdapter(getActivity(), listTheloai);
        sachAdapter = new SachAdapter(getActivity(), listBookhome);



        //ds sách
        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_book_home.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_book_home.setAdapter(sachAdapter);
        recyclerview_book_home.setHasFixedSize(true);



       try {
           HashMap<String,String> user = sessionManager.getUserDetail();
           quyen = user.get(sessionManager.QUYEN);
           name = user.get(sessionManager.NAME);
           id = user.get(sessionManager.ID);
           if (id==null){
//               GetAllData(urlSql.URL_GETDATA_THELOAI);
               fetchUser("");
           }else if (quyen.equals("user") || quyen.equals("admin")){
//               GetAllData(urlSql.URL_GETDATA_THELOAI);
       }
       }catch (Exception e){
           Log.e("LOG", e.toString());
       }

        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void fetchUser(String key){
        apiInTerFace = ApiClient.getApiClient().create(ApiInTerFace.class);
        Call<List<Books>> call = apiInTerFace.getUsers(key);

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
}
