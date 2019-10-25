package com.example.duan2muaban;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.duan2muaban.LoginRegister.ProfileActivity;
import com.example.duan2muaban.LoginRegister.SettingsActivity;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.adapter.FragmentAdapter;
import com.example.duan2muaban.fragmentMain.HomeFragment;
import com.example.duan2muaban.fragmentMain.LibraryFragment;
import com.example.duan2muaban.fragmentMain.SearchFragment;
import com.example.duan2muaban.fragmentMain.StoreFragment;
import com.example.duan2muaban.nighmode.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    SharedPref sharedPref;
    SessionManager sessionManager;
    BottomNavigationView navigation;
    ViewPager viewPager;

    private static final String TAG = "MainActivity";
    private MenuItem mSearchMenuItem;
    private SearchView mSearchView;
    private String mSearchString;
    private static final String SEARCH_KEY = "search";
    SearchFragment searchFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        //Toobar đã như ActionBar


        sessionManager = new SessionManager(this);
        navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = findViewById(R.id.viewpager); //Init Viewpager
        viewPager.setPageTransformer(true, new CubeTransformer());

        setupFm(getSupportFragmentManager(), viewPager); //Setup Fragment
        viewPager.setCurrentItem(0); //Set Currrent Item When Activity Start
        viewPager.setOnPageChangeListener(new PageChange()); //Listeners For Viewpager When Page Changed

        // if you saved something on outState you can recover them here
//        if (savedInstanceState != null) {
//            mSearchString = savedInstanceState.getString(SEARCH_KEY);
//        }
//        searchFragment = new SearchFragment().newInstance();
        try {
            HashMap<String,String> user = sessionManager.getUserDetail();
            String name = user.get(sessionManager.NAME);
            if (name==null){
                setTitle("Doc sach - bạn chưa đăng nhập");
            }else {
                setTitle("Doc sach - "+name);
            }

        }catch (Exception e){
            Log.e("LOG", e.toString());
        }
    }
    // This is called before the activity is destroyed
//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mSearchString = mSearchView.getQuery().toString();
//        outState.putString(SEARCH_KEY, mSearchString);
//    }
    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){
        FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);
        //Add All Fragment To List
        Adapter.add(new HomeFragment(), "Trang chủ");
        Adapter.add(new LibraryFragment(), "Thư viện");
        Adapter.add(new StoreFragment(), "Cửa hàng");
        Adapter.add(new SearchFragment(), "Tìm kiếm");
        viewPager.setAdapter(Adapter);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_library:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.nav_store:
                    viewPager.setCurrentItem(2);
                    return true;
                case R.id.nav_search:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };
    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    navigation.setSelectedItemId(R.id.nav_home);
                    break;
                case 1:
                    navigation.setSelectedItemId(R.id.nav_library);
                    break;
                case 2:
                    navigation.setSelectedItemId(R.id.nav_store);
                    break;
                case 3:
                    navigation.setSelectedItemId(R.id.nav_search);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }


    //
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
//
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName())
//        );
//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.profile:
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

    }
}
