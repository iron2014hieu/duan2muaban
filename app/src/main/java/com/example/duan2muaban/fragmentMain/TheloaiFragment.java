package com.example.duan2muaban.fragmentMain;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.model.TheLoai;
import com.example.duan2muaban.publicString.URL.UrlSql;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TheloaiFragment extends Fragment {
    private TextView txtLoikhuyen, txtDencuahang;
    private RecyclerView recyclerViewTheloai;
    private List<TheLoai> listTheloai = new ArrayList<>();
    private TheLoaiAdapter theLoaiAdapter;
    private SessionManager sessionManager;
    private String mauser;
    private UrlSql urlSql = new UrlSql();
    View view;


    public TheloaiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_theloai, container, false);
        addControls();
        sessionManager = new SessionManager(getContext());
        theLoaiAdapter = new TheLoaiAdapter(getContext(), listTheloai);

        //danh sách thể loại
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewTheloai.setLayoutManager(gridLayoutManager);
        recyclerViewTheloai.setAdapter(theLoaiAdapter);
        recyclerViewTheloai.setHasFixedSize(true);

        recyclerViewTheloai.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerViewTheloai, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TheLoai theloai =   listTheloai.get(position);
                String id = String.valueOf(theloai.getMaLoai());
                String ten = theloai.getTenLoai();

                sessionManager.createSessionGuimatheloai(id,ten);
                startActivity(new Intent(getContext(), GetBookByTheloaiActivity.class));

            }

            @Override
            public void onLongClick(View view, int position) {
                TheLoai theloai =   listTheloai.get(position);

            }
        }));


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
        HashMap<String,String> user = sessionManager.getUserDetail();
        mauser = user.get(sessionManager.ID);

        GetAllData(urlSql.URL_GETDATA_THELOAI);

       // Toast.makeText(getContext(), ""+listBill.size(), Toast.LENGTH_SHORT).show();
    }

    public void GetAllData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listTheloai.clear();
                        if (response.length() > 0){
                            recyclerViewTheloai.setVisibility(View.VISIBLE);
                        }else{
                            recyclerViewTheloai.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listTheloai.add(new TheLoai(
                                        object.getInt("MaLoai"),
                                        object.getString("TenLoai"),
                                        object.getString("Image")
                                ));

                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }

                        theLoaiAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void addControls(){
        recyclerViewTheloai = view.findViewById(R.id.contact_recyclerview_theloai);
    }
}
