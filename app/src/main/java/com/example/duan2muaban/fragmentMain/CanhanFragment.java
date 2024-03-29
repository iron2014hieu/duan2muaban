package com.example.duan2muaban.fragmentMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan2muaban.Fragment.donhang.ChoLayHangFragment;
import com.example.duan2muaban.Fragment.donhang.ChoXacNhanFragment;
import com.example.duan2muaban.LoginRegister.LoginActivity;
import com.example.duan2muaban.LoginRegister.ProfileActivity;
import com.example.duan2muaban.LoginRegister.RegisterActivity;
import com.example.duan2muaban.LoginRegister.SettingsActivity;
import com.example.duan2muaban.MuahangActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class CanhanFragment extends Fragment implements View.OnClickListener {
    private TextView txtSetting,txtTaikhoan;

    private TextView txtChoxacnhan, txtCholayhang,txtDanggiao,txtDanhgia;
    private CardView cardview_canhan;

    public CanhanFragment() {
        // Required empty public constructor
    }
    SessionManager sessionManager;
    String email;
    Button btnDangnhap,btnDangky;
    View v;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_canhan, container, false);
        addcontrols();
        sessionManager = new SessionManager(getContext());
        txtTaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });
        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        });

        HashMap<String, String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.NAME);
        if (email !=null){
            cardview_canhan.setVisibility(View.GONE);
            btnDangnhap.setVisibility(View.GONE);
            btnDangky.setVisibility(View.GONE);
        }else {
            cardview_canhan.setVisibility(View.VISIBLE);
            btnDangnhap.setVisibility(View.VISIBLE);
            btnDangky.setVisibility(View.VISIBLE);
        }
        txtChoxacnhan.setOnClickListener(this);
        txtCholayhang.setOnClickListener(this);
        txtDanggiao.setOnClickListener(this);
        txtDanhgia.setOnClickListener(this);

        btnDangky.setOnClickListener(this);
        btnDangnhap.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtChoxacnhan:
                chuyenMuahang("0");
                break;
            case R.id.txtCholayhang:
                chuyenMuahang("1");
                break;
            case R.id.txtDanggiao:
                chuyenMuahang("2");
                break;
            case R.id.txtDanhgia:
                chuyenMuahang("3");
                break;
            case R.id.btnDangky:
                goToregister();
                break;
            case R.id.btnDangnhap:
                goTologin();
                break;
        }
    }
    private void chuyenMuahang(String check) {
        Intent intent = new Intent(getContext(), MuahangActivity.class);
        intent.putExtra("check", check);
        startActivity(intent);
    }
    //    private void goToCholayhang(){
//        Fragment fragment = new ChoLayHangFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_container_muahang,fragment);
//        ft.commit();
//    }
    public void goTologin(){
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
    public void goToregister(){
        startActivity(new Intent(getContext(), RegisterActivity.class));
    }
    private void addcontrols() {
        txtSetting=v.findViewById(R.id.txtSetting);
        txtTaikhoan=v.findViewById(R.id.txtTaikhoan);
        txtChoxacnhan = v.findViewById(R.id.txtChoxacnhan);
        txtCholayhang= v.findViewById(R.id.txtCholayhang);
        txtDanggiao = v.findViewById(R.id.txtDanggiao);
        txtDanhgia = v.findViewById(R.id.txtDanhgia);
        cardview_canhan = v.findViewById(R.id.cardview_canhan);
        btnDangnhap= v.findViewById(R.id.btnDangnhap);
        btnDangky= v.findViewById(R.id.btnDangky);
    }
}
