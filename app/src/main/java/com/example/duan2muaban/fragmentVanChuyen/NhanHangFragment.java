package com.example.duan2muaban.fragmentVanChuyen;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan2muaban.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NhanHangFragment extends Fragment {


    public NhanHangFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nhan_hang, container, false);
    }

}
