package com.seiagul.laporanseiagul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seiagul.laporanseiagul.api.ApiClient;
import com.seiagul.laporanseiagul.api.ApiInterface;
import com.seiagul.laporanseiagul.model.register.Register;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUsername, etEmail, etPassword;
    Button btnRegister;
    TextView tvLogin;
    String Username, Email, Password;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etRegisUsername);
        etEmail = findViewById(R.id.etRegisEmail);
        etPassword = findViewById(R.id.etRegisPassword);

        btnRegister = findViewById(R.id.button4Regis);
        btnRegister.setOnClickListener(this);
        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button4Regis) {
            Username = etUsername.getText().toString();
            Email = etEmail.getText().toString();
            Password = etPassword.getText().toString();
            register(Username, Password, Email); // Memperbarui urutan parameter
        }

        if (v.getId() == R.id.tvLogin) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private void register(String username, String password, String email) {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<Register> call = apiInterface.registerResponse(username, password, email);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}