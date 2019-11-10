package com.example.duan2muaban;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.duan2muaban.Fragment.donhang.ChoLayHangFragment;
import com.example.duan2muaban.Fragment.donhang.ChoXacNhanFragment;
import com.example.duan2muaban.Fragment.donhang.DangGiaoFragment;
import com.example.duan2muaban.Fragment.donhang.DanhGiaFragment;
import com.example.duan2muaban.adapter.ViewPagerFM.TabViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MuahangActivity extends AppCompatActivity {
    TabViewPagerAdapter tabViewPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    TabLayout.Tab tab;
    String check;
    int select =2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muahang);
        viewPager = findViewById(R.id.fragment_container_muahang);
        tabLayout = findViewById(R.id.tablayout_id);
        Toolbar toolbar = findViewById(R.id.toolbar_muahang);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        Intent intent = getIntent();
        check = intent.getStringExtra("check");

        tabViewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        //add fragmenr here
        tabViewPagerAdapter.AddFragment(new ChoXacNhanFragment(), "Chờ xác nhận");
        tabViewPagerAdapter.AddFragment(new ChoLayHangFragment(), "Chờ lấy hàng");
        tabViewPagerAdapter.AddFragment(new DangGiaoFragment(), "Đang giao");
        tabViewPagerAdapter.AddFragment(new DanhGiaFragment(), "Đánh giá");
        viewPager.setAdapter(tabViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tab = tabLayout.getTabAt(Integer.valueOf(check));
        tab.select();




    }

}
