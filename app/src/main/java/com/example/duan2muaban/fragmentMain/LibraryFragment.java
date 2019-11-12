package com.example.duan2muaban.fragmentMain;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.model.Books;


/**
 * A simple {@link Fragment} subclass.
 */
public class LibraryFragment extends Fragment {

    View view;
    private SessionManager sessionManager;
    private RecyclerView recyclerview_book_library;
    private ProgressBar progressBar;
    public LibraryFragment() {
        // Required empty public constructor
    }

    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
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
        view = inflater.inflate(R.layout.fragment_library, container, false);
        recyclerview_book_library = view.findViewById(R.id.recyclerview_book_library);
        progressBar = view.findViewById(R.id.progress_lib);
        sessionManager = new SessionManager(getContext());



        recyclerview_book_library.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerview_book_library, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Books books =   listBooks.get(position);
//
//                String masach = String.valueOf(books.getMasach());
//                String tensach = String.valueOf(books.getTensach());
//                String manxb = String.valueOf(books.getManxb());
//                String matheloai = String.valueOf(books.getMatheloai());
//                String ngayxb = books.getNgayxb();
//                String noidung = books.getNoidung();
//                String anhbia =books.getAnhbia();
//                String gia = String.valueOf( books.getGia());
//                String tennxb= String.valueOf(books.getTennxb());
//                String soluong = String.valueOf(books.getSoluong());
//                String tacgia = books.getTacgia();
//                String tongdiem = String.valueOf(books.getTongdiem());
//                String landanhgia = String.valueOf(books.getLandanhgia());
//
//                sessionManager.createSessionSendInfomationBook(masach,tensach,manxb,matheloai,ngayxb,noidung,
//                        anhbia,gia,tennxb,soluong,tacgia, tongdiem, landanhgia);
//                startActivity(new Intent(getContext(), BookDetailActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return view;

    }
//    public void fetchUser(String key){
//        apiInTerFace = ApiClient.getApiClient().create(ApiInTerFace.class);
//        Call<List<Books>> call = apiInTerFace.getBooks(key);
//
//        call.enqueue(new Callback<List<Books>>() {
//            @Override
//            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
//                progressBar.setVisibility(View.GONE);
//                listBooks= response.body();
//                adapter = new SearchBookAdapter(listBooks, getContext());
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<List<Books>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                Log.e("Error Search:","Error on: "+t.toString());
//            }
//        });
//    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
