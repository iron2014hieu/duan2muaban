package com.example.duan2muaban;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.adapter.UsersAdapter;
import com.example.duan2muaban.model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QLTKActivity extends AppCompatActivity {
    private RecyclerView recyclerViewQLTK;
    private UsersAdapter usersAdapter;
    private List<Users> listUsers = new ArrayList<>();
    private String URL_GETDATA ="http://hieuttpk808.000webhostapp.com/books/login_register/getdata.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qltk);

        recyclerViewQLTK = findViewById(R.id.recyclerview_QLTK);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarQLTK);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        usersAdapter = new UsersAdapter(this, listUsers);
        recyclerViewQLTK.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewQLTK.setAdapter(usersAdapter);

        GetData(URL_GETDATA);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(QLTKActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listUsers.clear();
                        if (response.length() > 0){
                            recyclerViewQLTK.setVisibility(View.VISIBLE);
                        }else{
                            recyclerViewQLTK.setVisibility(View.GONE);
                        }
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listUsers.add(new Users(
                                        object.getInt("ID"),
                                        object.getString("Name"),
                                        object.getString("Email"),
                                        object.getString("Address"),
                                        object.getString("PhoneNumber"),
                                        object.getString("Photo"),
                                        object.getString("Quyen")
                                ));
                            }catch (JSONException e){
                                e.printStackTrace();
                                Toast.makeText(QLTKActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        usersAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(QLTKActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }
}
