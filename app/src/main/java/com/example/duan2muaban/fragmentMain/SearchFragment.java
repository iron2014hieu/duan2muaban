package com.example.duan2muaban.fragmentMain;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duan2muaban.Activity.BookDetailActivity;
import com.example.duan2muaban.LiveSearch.ApiClient;
import com.example.duan2muaban.LiveSearch.ApiInTerFace;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.SearchBookAdapter;
import com.example.duan2muaban.model.Books;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private SearchView searchView;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    public List<Books> listBooks;
    private SearchBookAdapter adapter;
    private ApiInTerFace apiInTerFace;
    ProgressBar progressBar;
    View view;
    private SessionManager sessionManager;
    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        sessionManager = new SessionManager(getContext());
        searchView=view.findViewById(R.id.searchview);
        progressBar = view.findViewById(R.id.progress);
        recyclerView=view.findViewById(R.id.recyclerview_search_book);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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

        fetchUser("");

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Books books =   listBooks.get(position);

                String id = String.valueOf(books.getId());
                String matheloai = String.valueOf(books.getMatheloai());
                String macuahang = String.valueOf(books.getMacuahang());
                String tensach = books.getTensach();
                String hinhanh = books.getHinhanh();
                String chitiet =books.getChitiet();
                String giaban = String.valueOf( books.getGiaban());
                String tongdiem= String.valueOf(books.getTongdiem());
                String linkbook = books.getLinkbook();
                String landanhgia = String.valueOf(books.getLandanhgia());
                sessionManager.createSessionSendInfomationBook(id,matheloai,macuahang,tensach,hinhanh,chitiet,giaban,tongdiem,landanhgia);
                startActivity(new Intent(getContext(), BookDetailActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;

    }
    public void fetchUser(String key){
        apiInTerFace = ApiClient.getApiClient().create(ApiInTerFace.class);
        Call<List<Books>> call = apiInTerFace.getUsers(key);

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                progressBar.setVisibility(View.GONE);
                listBooks= response.body();
                adapter = new SearchBookAdapter(listBooks, getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error on: "+t.toString(), Toast.LENGTH_SHORT).show();
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
