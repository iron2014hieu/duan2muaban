package com.example.duan2muaban.LoginRegister;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan2muaban.Main2Activity;
import com.example.duan2muaban.QLTKActivity;
import com.example.duan2muaban.R;
import com.example.duan2muaban.Session.SessionManager;
import com.example.duan2muaban.nighmode.SharedPref;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    SharedPref sharedPref;
    EditText txtEmail, txtName, txtPassword;
    Button btnLogout;
    SessionManager sessionManager;
    private String TAG = "TAG_PROFILE";

    private static String URL_READ ="https://bansachonline.xyz/bansach/loginregister/read_detail.php";
    private static String URL_EDIT ="https://bansachonline.xyz/bansach/loginregister/edit_detail.php";
    private static String URL_UPLOAD ="https://bansachonline.xyz/bansach/loginregister/upload.php";
    String email,strid,name,quyen;

    private Menu action;
    Bitmap bitmap;
    CircleImageView profile_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        theme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        //Toobar đã như ActionBar

        sessionManager = new SessionManager( this);
        sessionManager.Checklogin();

        txtEmail=findViewById(R.id.txtEmail);
        txtName=findViewById(R.id.txtName);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword);
        btnLogout=findViewById(R.id.btnLogout);
        profile_image = findViewById(R.id.profile_image);

        HashMap<String,String> user = sessionManager.getUserDetail();
        email = user.get(sessionManager.EMAIL);
        name = user.get(sessionManager.NAME);
        String id = user.get(sessionManager.ID);
        quyen = user.get(sessionManager.QUYEN);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.Logout();
            }
        });
    }

        @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void getImage(View v){
        chooseFile();
    }
    private void getDetail(final String email){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonobject = new JSONObject(response);
                            JSONArray jsonArray = jsonobject.getJSONArray("users_table");
                            JSONObject data = jsonArray.getJSONObject(0);

                            int id = data.getInt("id");
                            String name = data.getString("name");
                            String email = data.getString("email");
                            String password = data.getString("password");
                            String urlImage = data.getString("photo");
                            txtEmail.setText(email);
                            txtName.setText(name);
//                            txtPassword.setText(password);
                            strid = String.valueOf(id);
                            Picasso.with(ProfileActivity.this).load(urlImage).into(profile_image) ;


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("Error reading: ", error.toString());
                        txtEmail.setText(error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDetail(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            if (quyen.equals("user")||quyen.equals("store")){
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.menu_action, menu);
                action = menu;
                action.findItem(R.id.menu_save).setVisible(false);
            }else if (quyen.equals("admin")){
                getMenuInflater().inflate(R.menu.menu_action_admin, menu);
            }
        }catch (Exception e){
            Log.e("log", e.toString());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        try {
            if (quyen.equals("user")||quyen.equals("store")){
                switch (item.getItemId()){
                    case R.id.menu_edit:
                        txtName.setFocusableInTouchMode(true);
                        txtEmail.setFocusableInTouchMode(true);

                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(txtName, InputMethodManager.SHOW_IMPLICIT);

                        action.findItem(R.id.menu_edit).setVisible(false);
                        action.findItem(R.id.menu_save).setVisible(true);
                        return  true;
                    case R.id.menu_save:
                        saveDetail();
                        action.findItem(R.id.menu_edit).setVisible(true);
                        action.findItem(R.id.menu_save).setVisible(false);

                        txtName.setFocusableInTouchMode(false);
                        txtEmail.setFocusableInTouchMode(false);
                        txtName.setFocusable(false);
                        txtEmail.setFocusable(false);
                        return true;
                    case R.id.logout:
                        startActivity(new Intent(getBaseContext(), Main2Activity.class));
                        return true;

                }
            }else if (quyen.equals("admin")){
                switch (item.getItemId()){
                    case R.id.ivQLTK:
                        startActivity(new Intent(getBaseContext(), QLTKActivity.class));
                        break;
                }
            }
        }catch (Exception e){
            Log.e("log", e.toString());
        }

        return super.onOptionsItemSelected(item);
    }
    private void saveDetail(){
        final String name = txtName.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();
        final String id = strid;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(ProfileActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Log.e("Error saveProfile on: ", e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Log.e("Error: ", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("id", strid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data !=null &&data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                profile_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadPicture(strid, getStringImage(bitmap));
        }
    }

    private void uploadPicture(final String id, final String photo) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                progressDialog.dismiss();
                                Toast.makeText(ProfileActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
//                        Toast.makeText(ProfileActivity.this, "Error ! "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                params.put("photo", photo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String endecodeImage = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

        return endecodeImage;
    }
    public  void theme(){
        if (sharedPref.loadNightModeState() == true){
            setTheme(R.style.darktheme);
        }else setTheme(R.style.AppTheme);
    }
}
