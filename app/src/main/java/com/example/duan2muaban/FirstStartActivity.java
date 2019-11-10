package com.example.duan2muaban;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

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
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_start);

        tabViewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        //add fragmenr here
        tabViewPagerAdapter.AddFragment(new FirstStart1Fragment(), "1");
        tabViewPagerAdapter.AddFragment(new FirstStart2Fragment(), "2");
        tabViewPagerAdapter.AddFragment(new FirstStart3Fragment(), "3");
        viewPager.setAdapter(tabViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
