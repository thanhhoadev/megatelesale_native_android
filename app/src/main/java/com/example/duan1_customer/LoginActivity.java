package com.example.duan1_customer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_customer.model.ApiClient;
import com.example.duan1_customer.model.Profile_User;
import com.example.duan1_customer.model.Response_User;
import com.example.duan1_customer.model.ServiceAPI;
import com.example.duan1_customer.model.TokenManager;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail, edtPassWord;
    CheckBox chkRemember;
    ImageButton icon_pass;
    SharedPreferences sharedPreferences;


    String email, pass;

    public void handleLogin () {
        Profile_User user = new Profile_User(email.trim(), pass.trim(), "name");

        ServiceAPI serviceAPI = ApiClient.getClient(getApplicationContext()).create(ServiceAPI.class);

        Call<Response_User> call = serviceAPI.checkLogin(user);
        call.enqueue(new Callback<Response_User>() {
            @Override
            public void onResponse(Call<Response_User> call, Response<Response_User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Response_User responseUser = response.body();
                    if (responseUser.message.equals("Validation Error")) {
                        Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);

                        String accessToken = responseUser.result.access_token;
                        new TokenManager(LoginActivity.this).saveToken(accessToken);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("email", email);
                        editor.putBoolean("isRemember", chkRemember.isChecked());
                        Gson gson1 = new Gson();
                        editor.putString("user", gson1.toJson(responseUser.result.user));
                        editor.apply();

                        startActivity(intentMain);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Response_User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Lỗi API", Toast.LENGTH_SHORT).show();
            }
        });
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = findViewById(R.id.btn_login);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassWord = findViewById(R.id.edtPassWord);
        icon_pass = findViewById(R.id.icon_pass);
        chkRemember = findViewById(R.id.chkRemember);

        icon_pass.setBackgroundResource(R.drawable.hidepass);
        icon_pass.setOnClickListener(new View.OnClickListener() {
            boolean hidePass = false;
            @Override
            public void onClick(View v) {
                if (hidePass){
                    edtPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    icon_pass.setBackgroundResource(R.drawable.showpass);
                }else{
                    edtPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    icon_pass.setBackgroundResource(R.drawable.hidepass);
                }
                hidePass =! hidePass;
                edtPassWord.setSelection(edtPassWord.getText().length());
            }
        });

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        if(sharedPreferences.getString("user", "").length() > 0) {
            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intentMain);
            finish();
        }
        boolean check = sharedPreferences.getBoolean("isRemember", false);
        if(check){
            email = sharedPreferences.getString("email","");
            chkRemember.setChecked(check);
            edtEmail.setText(email);
        };


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                email = edtEmail.getText().toString();
                pass = edtPassWord.getText().toString();

                if (email.length() > 0 && pass.length() > 0){
                    handleLogin();
                }else{
                    Toast.makeText(LoginActivity.this, "Nhập user và pass", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}