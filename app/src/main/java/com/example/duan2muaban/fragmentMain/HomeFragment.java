package com.example.duan2muaban.fragmentMain;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import com.example.duan2muaban.Activity.BookDetailActivity;
import com.example.duan2muaban.Activity.GetBookByTheloaiActivity;
import com.example.duan2muaban.Activity.SearchBooksActivity;
import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.ApiNXB.ApiInTerFaceNXB;
import com.example.duan2muaban.ApiRetrofit.ApiTacgia.ApiInTerFaceTacgia;
import com.example.duan2muaban.ApiRetrofit.LiveSearch.ApiInTerFaceTensach;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.SliderAdapterExample;
import com.example.duan2muaban.adapter.NhaxuatbanAdapter;
import com.example.duan2muaban.adapter.SachAdapter;
import com.example.duan2muaban.adapter.TacgiaAdapter;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.model.Nhaxuatban;
import com.example.duan2muaban.model.Tacgia;
import com.example.duan2muaban.model.TheLoai;
import com.example.duan2muaban.publicString.URL.UrlSql;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    UrlSql urlSql = new UrlSql();

    SliderView sliderView;


    private SearchView searchView;
    TheLoaiAdapter theLoaiAdapter;
    SachAdapter sachAdapter;
    TacgiaAdapter tacgiaAdapter;

    ProgressBar progressBar;
    NhaxuatbanAdapter nhaxuatbanAdapter;
    private List<TheLoai> listTheloai = new ArrayList<>();
    private List<Books> listBookhome = new ArrayList<>();
    private List<Nhaxuatban> listNhaxuatbanHome = new ArrayList<>();
    private List<Tacgia> listTacgia = new ArrayList<>();

    private RecyclerView recyclerview_book_home;
    private RecyclerView recyclerview_nxb_home;
    private RecyclerView recyclerViewTheloai;
    private RecyclerView recyclerViewTacgia;

    private ApiInTerFaceTensach apiInTerFaceTensach;
    private ApiInTerFaceNXB apiInTerFaceNXB;
    private ApiInTerFaceTacgia apiInTerFaceTacgia;

    ImageButton buttonRecord;
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
        addControls();


        sessionManager = new SessionManager(getContext());
        sachAdapter = new SachAdapter(getContext(), listBookhome);
        nhaxuatbanAdapter = new NhaxuatbanAdapter(getContext(), listNhaxuatbanHome);
        tacgiaAdapter = new TacgiaAdapter(getContext(), listTacgia);

        final SliderAdapterExample adapter = new SliderAdapterExample(getContext());
        adapter.setCount(5);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });

        recyclerview_book_home=view.findViewById(R.id.recyclerview_book_home);
        searchView=view.findViewById(R.id.searchview);
        progressBar = view.findViewById(R.id.progress);
        buttonRecord =view.findViewById(R.id.buttonSpeech);

        buttonRecord.setVisibility(View.GONE);

        theLoaiAdapter = new TheLoaiAdapter(getContext(), listTheloai);
        // lấy sách
        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerview_book_home.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_book_home.setHasFixedSize(true);
        // the loaij sachs
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerViewTheloai.setLayoutManager(gridLayoutManager);
        recyclerViewTheloai.setHasFixedSize(true);
        // recyclerview nhà xuất bản
        StaggeredGridLayoutManager gridLayoutManager3 =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerview_nxb_home.setLayoutManager(gridLayoutManager3);
        recyclerview_nxb_home.setHasFixedSize(true);
        // tac gia sachs
        StaggeredGridLayoutManager gridLayoutManager4 =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        recyclerViewTacgia.setLayoutManager(gridLayoutManager4);
        recyclerViewTacgia.setHasFixedSize(true);

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
        recyclerview_book_home.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerview_book_home, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Books books =   listBookhome.get(position);
                String masach = String.valueOf(books.getMasach());
                String tensach = String.valueOf(books.getTensach());
                String manxb = String.valueOf(books.getManxb());
                String matheloai = String.valueOf(books.getMatheloai());
                String ngayxb = books.getNgayxb();
                String noidung = books.getNoidung();
                String anhbia =books.getAnhbia();
                String gia = String.valueOf( books.getGia());
                String tennxb= String.valueOf(books.getTennxb());
                String soluong = String.valueOf(books.getSoluong());
                String tacgia = books.getTacgia();

                sessionManager.createSessionSendInfomationBook(masach,tensach,manxb,matheloai,ngayxb,noidung,anhbia,gia,tennxb,soluong,tacgia);
                Toast.makeText(getContext(), ""+masach, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getContext(), BookDetailActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));





        GetAllData(urlSql.URL_GETDATA_THELOAI);
        fetchUser("");
        fetchNXB();
        fetchTacgia();
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

        //speech to text
        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        searchView.clearFocus();
        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus(); // close the keyboard on load
                buttonRecord.setVisibility(View.VISIBLE);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                buttonRecord.setVisibility(View.GONE);
                return false;
            }
        });

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    searchView.setQuery(String.valueOf(matches.get(0)), false);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });

        buttonRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        searchView.setQueryHint("You will see input here");
                        break;

                    case MotionEvent.ACTION_DOWN:
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        searchView.setQueryHint("");
//                        editText.setHint("Listening...");
                        break;
                }
                return false;
            }
        });

        return view;
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



//    private void flipperImages(int image) {
//        ImageView imageView = new ImageView(getContext());
//        imageView.setBackgroundResource(image);
//
//        v_vflipper.addView(imageView);
//        v_vflipper.setFlipInterval(3000);
//        v_vflipper.setAutoStart(true);
//
//        v_vflipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
//        v_vflipper.setOutAnimation(getContext(),android.R.anim.slide_out_right  );
//    }

    public void fetchUser(String key){
        apiInTerFaceTensach = ApiClient.getApiClient().create(ApiInTerFaceTensach.class);
        Call<List<Books>> call = apiInTerFaceTensach.getUsers(key);

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
    public void fetchNXB(){
        apiInTerFaceNXB = ApiClient.getApiClient().create(ApiInTerFaceNXB.class);
        Call<List<Nhaxuatban>> call = apiInTerFaceNXB.getNXB();

        call.enqueue(new Callback<List<Nhaxuatban>>() {
            @Override
            public void onResponse(Call<List<Nhaxuatban>> call, retrofit2.Response<List<Nhaxuatban>> response) {
                progressBar.setVisibility(View.GONE);
                listNhaxuatbanHome= response.body();
                nhaxuatbanAdapter = new NhaxuatbanAdapter(getContext(),listNhaxuatbanHome);
                recyclerview_nxb_home.setAdapter(nhaxuatbanAdapter);
                nhaxuatbanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Nhaxuatban>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    public void fetchTacgia(){
        apiInTerFaceTacgia = ApiClient.getApiClient().create(ApiInTerFaceTacgia.class);
        Call<List<Tacgia>> call = apiInTerFaceTacgia.getTacgia();

        call.enqueue(new Callback<List<Tacgia>>() {
            @Override
            public void onResponse(Call<List<Tacgia>> call, retrofit2.Response<List<Tacgia>> response) {
                progressBar.setVisibility(View.GONE);
                listTacgia= response.body();
                tacgiaAdapter = new TacgiaAdapter(getContext(),listTacgia);
                recyclerViewTacgia.setAdapter(tacgiaAdapter);
                tacgiaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Tacgia>> call, Throwable t) {
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

    private void addControls(){
        recyclerViewTheloai = view.findViewById(R.id.recyclerview_theloai);
        sliderView = view.findViewById(R.id.imageSlider);
        recyclerview_nxb_home = view.findViewById(R.id.recyclerview_nxb_home);
        recyclerViewTacgia = view.findViewById(R.id.recyclerview_tacgia_home);
    }
}
