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

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.duan2muaban.ApiRetrofit.ApiClient;
import com.example.duan2muaban.ApiRetrofit.LiveSearch.ApiInTerFaceTensach;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.SliderAdapterExample;
import com.example.duan2muaban.adapter.SachAdapter;
import com.example.duan2muaban.adapter.TheLoaiAdapter;
import com.example.duan2muaban.model.Books;
import com.example.duan2muaban.model.TheLoai;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
//    ViewFlipper v_vflipper;

    SliderView sliderView;

    private SearchView searchView;
    TheLoaiAdapter theLoaiAdapter;
    SachAdapter sachAdapter;
    ProgressBar progressBar;
    private List<TheLoai> listTheloai = new ArrayList<>();
    private List<Books> listBookhome = new ArrayList<>();
    private RecyclerView recyclerview_book_home;
    private ApiInTerFaceTensach apiInTerFaceTensach;

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

        sliderView = view.findViewById(R.id.imageSlider);

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

//        int images[] = {R.drawable.sach1, R.drawable.sach2, R.drawable.sach3};
//        v_vflipper= view.findViewById(R.id.v_flipper);
//
//        for (int i= 0; i < images.length;i++) {
//            flipperImages(images[i]);
//        }
//
//        v_vflipper.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "ádadadada", Toast.LENGTH_SHORT).show();
//            }
//        });

        sessionManager = new SessionManager(getContext());
        sachAdapter = new SachAdapter(getContext(), listBookhome);


        StaggeredGridLayoutManager gridLayoutManagerVeticl =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerview_book_home.setLayoutManager(gridLayoutManagerVeticl);
        recyclerview_book_home.setHasFixedSize(true);


        fetchUser("");
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
