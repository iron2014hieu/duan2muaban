package com.example.duan2muaban;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.duan2muaban.Fragment.FirstStartApp.FirstStart1Fragment;
import com.example.duan2muaban.Fragment.FirstStartApp.FirstStart2Fragment;
import com.example.duan2muaban.Fragment.FirstStartApp.FirstStart3Fragment;
import com.example.duan2muaban.Fragment.donhang.ChoLayHangFragment;
import com.example.duan2muaban.Fragment.donhang.ChoXacNhanFragment;
import com.example.duan2muaban.Fragment.donhang.DangGiaoFragment;
import com.example.duan2muaban.Fragment.donhang.DanhGiaFragment;
import com.example.duan2muaban.adapter.ViewPagerFM.TabViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class FirstStartActivity extends AppCompatActivity {
    TabViewPagerAdapter tabViewPagerAdapter;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
