package com.example.duan2muaban.fragmentMain;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Activity.GetBookByTheloaiActivity;
import com.example.duan2muaban.Activity.ThemTheloaiSachActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.model.TheLoai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public String URL_GETDATA ="http://hieuttpk808.000webhostapp.com/books/theloai/getdata.php";
    public String URL_GETBYMACUAHNG = "http://hieuttpk808.000webhostapp.com/books/theloai/getdatabymacuahang.php/?macuahang=";
    String name,id,quyen;
    TheLoaiAdapter theLoaiAdapter;
    private List<TheLoai> listTheloai = new ArrayList<>();
    private RecyclerView recyclerViewTheloai;
    private Button btnDencuahangHome;
    View v;
    private TextView textViewTB;

    CircleImageView circleImageViewThemtheloai;
    SessionManager sessionManager;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewTheloai=v.findViewById(R.id.contact_recyclerview_theloai);
        textViewTB=v.findViewById(R.id.txtThongbaonull);
        circleImageViewThemtheloai=v.findViewById(R.id.img_themtheloai);

        sessionManager = new SessionManager(getContext());

        theLoaiAdapter = new TheLoaiAdapter(getActivity(), listTheloai);

//        StaggeredGridLayoutManager gridLayoutManager =
//                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        recyclerViewTheloai.setLayoutManager(gridLayoutManager);
//        recyclerViewTheloai.setAdapter(theLoaiAdapter);
//        recyclerViewTheloai.setHasFixedSize(true);

        recyclerViewTheloai.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerViewTheloai.setAdapter(theLoaiAdapter);


        recyclerViewTheloai.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerViewTheloai, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TheLoai theloai =   listTheloai.get(position);
                String id = String.valueOf(theloai.getID());
                String ten = theloai.getTenTheLoai();

                sessionManager.createSessionGuimatheloai(id,ten);
                startActivity(new Intent(getContext(), GetBookByTheloaiActivity.class));

            }

            @Override
            public void onLongClick(View view, int position) {
                TheLoai theloai =   listTheloai.get(position);

            }
        }));
        circleImageViewThemtheloai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ThemTheloaiSachActivity.class));
            }
        });
       try {
           HashMap<String,String> user = sessionManager.getUserDetail();
           quyen = user.get(sessionManager.QUYEN);
           name = user.get(sessionManager.NAME);
           id = user.get(sessionManager.ID);

           if (id==null){
               GetAllData(URL_GETDATA);
           }else if (quyen.equals("user") || quyen.equals("admin")){
               GetAllData(URL_GETDATA);
           }else if (quyen.equals("store")){
               GetDataBymacuahang(URL_GETBYMACUAHNG+id);
           }

           if (quyen==null){
               circleImageViewThemtheloai.setVisibility(View.GONE);
           }else if (quyen.equals("user")){
               circleImageViewThemtheloai.setVisibility(View.GONE);
           }
           else {
               circleImageViewThemtheloai.setVisibility(View.VISIBLE);
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
    public void GetAllData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listTheloai.clear();
                        if (response.length() > 0){
                            recyclerViewTheloai.setVisibility(View.VISIBLE);
                            textViewTB.setVisibility(View.GONE);
                        }else{
                            recyclerViewTheloai.setVisibility(View.GONE);
                            textViewTB.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listTheloai.add(new TheLoai(
                                        object.getInt("ID"),
                                        object.getInt("MaCuaHang"),
                                        object.getString("TenTheLoai"),
                                        object.getString("MoTa"),
                                        object.getString("PhoTo")
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
    public void GetDataBymacuahang(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listTheloai.clear();
                        if (response.length() > 0){
                            recyclerViewTheloai.setVisibility(View.VISIBLE);
                            textViewTB.setVisibility(View.GONE);
                        }else{
                            recyclerViewTheloai.setVisibility(View.GONE);
                            textViewTB.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listTheloai.add(new TheLoai(
                                        object.getInt("ID"),
                                        object.getInt("MaCuaHang"),
                                        object.getString("TenTheLoai"),
                                        object.getString("MoTa"),
                                        object.getString("PhoTo")
                                ));

                            }catch (JSONException e){
                                e.printStackTrace();
//                                Toast.makeText(getContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        theLoaiAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
