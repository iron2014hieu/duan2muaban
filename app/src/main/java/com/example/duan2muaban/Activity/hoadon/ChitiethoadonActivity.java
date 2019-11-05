package com.example.duan2muaban.Activity.hoadon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.duan2muaban.R;

public class ChitiethoadonActivity extends AppCompatActivity {
    Button btn_themcthd,btn_huy;
    EditText edtmahd,edttenkh,edtngaydat,edtngaygiao,edtdiachi,edtgiavanchuyen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiethoadon);
        Anhxa();
    }
    private void Anhxa(){
        edtmahd=findViewById(R.id.edtmahoadon);
        edttenkh=findViewById(R.id.edttenkhachhang);
        edtngaydat=findViewById(R.id.edtngaydat);
        edtngaygiao=findViewById(R.id.edtngaygiao);
        edtdiachi=findViewById(R.id.edtthongtingiaohang);
        edtgiavanchuyen=findViewById(R.id.edtgiavanchuyen);
        btn_themcthd=findViewById(R.id.btn_themcthd);
//        btn_huy=findViewById(R.id.btn_huy);
    }
}
