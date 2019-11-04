package com.example.duan2muaban.fragmentMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan2muaban.LoginRegister.ProfileActivity;
import com.example.duan2muaban.LoginRegister.SettingsActivity;
import com.example.duan2muaban.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CanhanFragment extends Fragment {
    private TextView txtSetting,txtTaikhoan;
    public CanhanFragment() {
        // Required empty public constructor
    }
    View v;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_canhan, container, false);
        txtSetting=v.findViewById(R.id.txtSetting);
        txtTaikhoan=v.findViewById(R.id.txtTaikhoan);

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
        return v;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

}
