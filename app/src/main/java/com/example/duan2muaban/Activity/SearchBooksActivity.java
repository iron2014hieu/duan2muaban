package com.example.duan2muaban.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.LiveSearch.ApiInTerFace;
import com.example.duan2muaban.ApiRetrofit.LiveSearch.ApiInTerFaceTensach;
import com.example.duan2muaban.R;
import com.example.duan2muaban.RecycerViewTouch.RecyclerTouchListener;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.SachAdapter;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.model.Books;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchBooksActivity extends AppCompatActivity {
    SearchView searchView;
    ApiInTerFace apiInTerFace;
    private SachAdapter sachAdapter;
    private List<Books> listBookSearch = new ArrayList<>();

    ImageButton buttonRecord;
    RecyclerView recyclerview_book_search;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_books);
        addControl();

        sessionManager = new SessionManager(this);

        buttonRecord.setVisibility(View.GONE);



        sachAdapter = new SachAdapter(SearchBooksActivity.this, listBookSearch);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_book_search.setLayoutManager(gridLayoutManager);
        recyclerview_book_search.setAdapter(sachAdapter);
        recyclerview_book_search.setHasFixedSize(true);

        fetchBookRandom("");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    fetchBookRandom(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    fetchBookRandom(newText);
                    return false;
                }
            });


        recyclerview_book_search.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerview_book_search, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Books books =   listBookSearch.get(position);
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
                Toast.makeText(SearchBooksActivity.this, ""+masach, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(SearchBooksActivity.this, BookDetailActivity.class));
            }

            @Override
            public void onLongClick(View view, int position) {
                // code here
            }
        }));


        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                return false;
            }
        });

        //speech to text
        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
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
                        break;
                }
                return false;
            }
        });
    }

    public void fetchBookRandom(String key){
        apiInTerFace = ApiClient.getApiClient().create(ApiInTerFace.class);
        Call<List<Books>> call = apiInTerFace.getBookRandom(key);

        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, retrofit2.Response<List<Books>> response) {
//                progressBar.setVisibility(View.GONE);
                listBookSearch= response.body();
                sachAdapter = new SachAdapter(SearchBooksActivity.this,listBookSearch);
                recyclerview_book_search.setAdapter(sachAdapter);
                sachAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Log.e("Error Search:","Error on: "+t.toString());
            }
        });
    }
    private void addControl() {
        searchView = findViewById(R.id.search_view_all);
        buttonRecord =findViewById(R.id.buttonSpeech);
        recyclerview_book_search= findViewById(R.id.recyclerview_book_search);
    }
}
