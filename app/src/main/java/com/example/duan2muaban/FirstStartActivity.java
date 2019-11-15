package com.example.duan2muaban;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.duan2muaban.Fragment.FirstStartApp.FirstStart1Fragment;
import com.example.duan2muaban.Fragment.FirstStartApp.FirstStart2Fragment;
import com.example.duan2muaban.Fragment.FirstStartApp.FirstStart3Fragment;
import com.example.duan2muaban.adapter.ViewPagerFM.TabViewPagerAdapter;
import com.example.duan2muaban.nighmode_vanchuyen.SharedPref;

public class FirstStartActivity extends AppCompatActivity {
    TabViewPagerAdapter tabViewPagerAdapter;
    ViewPager viewPager;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_first_start);
        viewPager = findViewById(R.id.fragment_container_first_appstart);

        tabViewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        //add fragmenr here
        tabViewPagerAdapter.AddFragment(new FirstStart1Fragment(), "");
        tabViewPagerAdapter.AddFragment(new FirstStart2Fragment(), "");
        tabViewPagerAdapter.AddFragment(new FirstStart3Fragment(), "");
        viewPager.setAdapter(tabViewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);

    }
    //settheme
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
}
