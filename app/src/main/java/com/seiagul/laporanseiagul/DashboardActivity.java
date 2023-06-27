package com.seiagul.laporanseiagul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.seiagul.laporanseiagul.MainActivity;
import com.seiagul.laporanseiagul.ProfileActivity;
import com.seiagul.laporanseiagul.PengaduanActivity;
import com.seiagul.laporanseiagul.R;
import com.seiagul.laporanseiagul.RiwayatActivity;
import com.seiagul.laporanseiagul.AboutActivity;
import com.seiagul.laporanseiagul.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

public class DashboardActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private PagerAdapter adapter;
    private int[] imageIds = {R.drawable.slideone, R.drawable.slidetwo, R.drawable.slidethree};
    SessionManager sessionManager;
    TextView etMainUsername;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        sessionManager = new SessionManager(DashboardActivity.this);
        if (!sessionManager.isLoggedIn()) {
            moveToLogin();
        }

        etMainUsername = findViewById(R.id.etMainUsername);

        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);

        etMainUsername.setText(username);

        viewPager = findViewById(R.id.viewPager);
        adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return imageIds.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(DashboardActivity.this);
                imageView.setImageResource(imageIds[position]);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((ImageView) object);
            }
        };

        viewPager.setAdapter(adapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int nextImagePosition = (viewPager.getCurrentItem() + 1) % adapter.getCount();
                        viewPager.setCurrentItem(nextImagePosition);
                    }
                });
            }
        }, 2000, 2000);

        ImageButton btnProf = findViewById(R.id.btnProf);
        ImageButton btnAdu = findViewById(R.id.btnAdu);
        ImageButton btnRiwayat = findViewById(R.id.btnRiwayat);
        ImageButton btnTentang = findViewById(R.id.btnTentang);

        btnProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnAdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PengaduanActivity.class);
                startActivity(intent);
            }
        });

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, RiwayatActivity.class);
                startActivity(intent);
            }
        });

        btnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void moveToLogin() {
        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
