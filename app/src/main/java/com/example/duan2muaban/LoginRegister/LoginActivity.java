package com.example.duan2muaban.LoginRegister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Activity.ShipperActivity;
import com.example.duan2muaban.MainActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.nighmode_vanchuyen.SharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERNAME = "userNameKey";
    public static final String PASS = "passKey";
    public static final String REMEMBER = "remember";
    public SharedPreferences sharedpreferences;
    public CheckBox cbRemember;
    SharedPref sharedPref;


    EditText edtEmail, edtPassword;
    CircleImageView cicler_logo;
    Button btnLogin;
    ProgressBar progressBar;
    private String URL_LOGIN = "https://bansachonline.xyz/bansach/loginregister/login.php";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPref = new SharedPref(this);
        theme();
        setContentView(R.layout.activity_login);
        addControls();
        sessionManager = new SessionManager(this);

        //khởi tạo shared preferences
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        loadData();//lấy dữ liệu đã lưu nếu có

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mEmail = edtEmail.getText().toString().trim();
                    String mPassword = edtPassword.getText().toString().trim();
//                    edtEmail.setVisibility(View.VISIBLE);
//                    edtPassword.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                    cicler_logo.setVisibility(View.VISIBLE);
//                    btnLogin.setVisibility(View.VISIBLE);
//                    cbRemember.setVisibility(View.VISIBLE);
                    if (!mEmail.isEmpty() || !mPassword.isEmpty()){
                        if (cbRemember.isChecked()){
                            Login(mEmail,mPassword);
                            saveData(mEmail, mPassword);
                        }else {
                            Login(mEmail,mPassword);
                            clearData();
                        }
                    }else {
                        edtEmail.setError("Nhap email");
                        edtPassword.setError("nhap password");
                    }
                }
            });
        }
        public void loginsms(View view){
        startActivity(new Intent(getBaseContext(), VerifyPhoneActivity.class));
        }
    private void Login(final String email, final String password){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")){
                                for (int i =0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String phone = object.getString("phone").trim();
                                    String id = object.getString("id").trim();
                                    String quyen = object.getString("quyen").trim();

                                    sessionManager.createSession(id, email,phone, name, quyen);


                                    if(quyen.equals("shipper")){
                                        startActivity(new Intent(LoginActivity.this, ShipperActivity.class));
                                    }else {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Login error: ", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Login error: ", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void regis(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
    public void clearData() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }
    private void saveData(String username, String Pass) {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASS, Pass);
        editor.putBoolean(REMEMBER,cbRemember.isChecked());
        editor.commit();
    }
    public void loadData() {
        if(sharedpreferences.getBoolean(REMEMBER,false)) {
            edtEmail.setText(sharedpreferences.getString(USERNAME, ""));
            edtPassword.setText(sharedpreferences.getString(PASS, ""));
            cbRemember.setChecked(true);
        }
        else
            cbRemember.setChecked(false);

    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
    private void addControls(){
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.loading_login);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        cicler_logo=(CircleImageView)findViewById(R.id.cicler_logo);
    }
}



