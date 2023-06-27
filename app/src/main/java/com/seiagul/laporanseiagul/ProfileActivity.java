package com.seiagul.laporanseiagul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.seiagul.laporanseiagul.api.ApiClient;
import com.seiagul.laporanseiagul.api.ApiInterface;
import com.seiagul.laporanseiagul.model.ChangePasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ProfileActivity extends AppCompatActivity {

    SessionManager sessionManager;
    TextView etMainUsername, etMainEmail;
    String username, email;
    Button btnLogout, buttonUbah;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sessionManager = new SessionManager(ProfileActivity.this);





        buttonUbah = findViewById(R.id.button3);
        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://tugasaja.masuk.web.id/applogindanregisterandroid/web/resetpw.php";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });














//        buttonUbah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String newPassword = editTextPassword.getText().toString();
//
//                // Panggil metode untuk melakukan permintaan mengubah password menggunakan Retrofit
//                changePassword(newPassword);
//            }
//        });






        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Panggil method logout dari session manager
                sessionManager.logoutSession();

                // Kembali ke MainActivity
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Selesai aktivitas profil
            }
        });

        etMainUsername = findViewById(R.id.username);
        etMainEmail = findViewById(R.id.email);
        btnLogout = findViewById(R.id.btnLogout);

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

        etMainUsername.setText(username);
        etMainEmail.setText(email);






    }

//    private void changePassword(String newPassword) {
//        // Ambil email pengguna dari session manager
//        String email = sessionManager.getUserDetail().get(SessionManager.EMAIL);
//
//        // Ambil password lama dari pengguna (misalnya, dari input pengguna)
//        String oldPassword = ""; // Gantikan dengan cara mengambil password lama pengguna
//
//        // Buat objek Retrofit
//        Retrofit retrofit = ApiClient.getClient();
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//
//        // Buat permintaan mengubah password menggunakan Retrofit
//        Call<ChangePasswordResponse> call = apiInterface.changePasswordResponse(email, oldPassword, newPassword);
//        call.enqueue(new Callback<ChangePasswordResponse>() {
//            @Override
//            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
//                if (response.isSuccessful()) {
//                    ChangePasswordResponse changePasswordResponse = response.body();
//                    if (changePasswordResponse != null && changePasswordResponse.isSuccess()) {
//                        // Permintaan mengubah password berhasil, tampilkan pesan sukses
//                        Toast.makeText(ProfileActivity.this, changePasswordResponse.getMessage(), Toast.LENGTH_SHORT).show();
//                    } else {
//                        // Permintaan mengubah password gagal, tampilkan pesan error
//                        Toast.makeText(ProfileActivity.this, "Gagal mengubah password", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // Permintaan mengubah password gagal, tampilkan pesan error
//                    Toast.makeText(ProfileActivity.this, "Gagal mengubah password", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
//                // Permintaan mengubah password gagal, tampilkan pesan error
//                Toast.makeText(ProfileActivity.this, "Gagal mengubah password", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}