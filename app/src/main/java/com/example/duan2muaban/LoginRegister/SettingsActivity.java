package com.example.duan2muaban.LoginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.duan2muaban.MainActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.nighmode.SharedPref;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    private Switch mySwitch;
    SharedPref sharedPref;

    SessionManager sessionManager;
    Toolbar toolbar_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mySwitch = (Switch)findViewById(R.id.mySwitch);
        toolbar_setting=(Toolbar)findViewById(R.id.toolbar_setting);
        toolbar_setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });


        if (sharedPref.loadNightModeState()==true){
            mySwitch.setChecked(true);
        }
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    sharedPref.setNightModelState(true);
                    restartApp();
                }else {
                    sharedPref.setNightModelState(false);
                    restartApp();
                }
            }
        });

        sessionManager = new SessionManager(this);
        HashMap<String,String> theloai = sessionManager.getMAtheloai();
        String matheloai = theloai.get(sessionManager.MATHELOAI);
        TextView textView = findViewById(R.id.txtCardview);
        Toast.makeText(this, ""+matheloai, Toast.LENGTH_SHORT).show();
    }
    public void  restartApp(){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        finish();
    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    @Override
    public void onBackPressed() {

    }
}
