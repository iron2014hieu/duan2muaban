package com.example.duan2muaban;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.duan2muaban.nighmode.SharedPref;


public class Main2Activity extends AppCompatActivity {
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbarCartlist);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container2,
                new CartListFragment()).commit();
    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
