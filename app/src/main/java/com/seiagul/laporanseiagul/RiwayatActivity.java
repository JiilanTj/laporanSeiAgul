package com.seiagul.laporanseiagul;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RiwayatActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);


        Button btnProses = findViewById(R.id.btnProses);
        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiwayatActivity.this, ProsesActivity.class);
                startActivity(intent);
            }
        });

        Button btnDitolak = findViewById(R.id.btnDitolak);
        btnDitolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiwayatActivity.this, DitolakActivity.class);
                startActivity(intent);
            }
        });

        Button btnSelesai = findViewById(R.id.btnSelesai);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiwayatActivity.this, SelesaiActivity.class);
                startActivity(intent);
            }
        });


    }
}
