package com.seiagul.laporanseiagul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.HashMap;
import java.util.Map;

public class PengaduanActivity extends AppCompatActivity {

    String username;
    TextView etMainUsername;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 789;
    private static final int PICK_IMAGE_REQUEST = 1;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    SessionManager sessionManager;
    private Button buttonGPS;
    private TextView kordinatTextView;
    private Button btnUploadGambar;
    private Uri selectedImageUri;
    private ImageButton btnWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaduan);
        sessionManager = new SessionManager(PengaduanActivity.this);

        Spinner spinner = findViewById(R.id.spinner);
        kordinatTextView = findViewById(R.id.kordinat);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        buttonGPS = findViewById(R.id.buttonGPS);
        btnWeb = findViewById(R.id.btnWeb);

        buttonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationPermission();
            }
        });

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite();
            }
        });

        etMainUsername = findViewById(R.id.usernameAduan);
        username = sessionManager.getUserDetail().get(SessionManager.USERNAME);
        etMainUsername.setText(username);


        String[] pilihan = {"Sampah", "Lampu Jalan", "Infrastruktur", "Taman PKK", "Fasilitas (Angkut Sampah)", "Fasilitas (Masalah Selokan)", "Tumbuhan (Rumput Panjang)"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, pilihan);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = pilihan[position];
                // Lakukan tindakan yang diperlukan ketika item dipilih
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Tidak ada item yang dipilih
            }
        });

        createLocationRequest();
        createLocationCallback();

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String urlGambar = (((EditText) findViewById(R.id.urlGambar)).getText().toString());
                String keterangan = (((EditText) findViewById(R.id.tilKeterangan)).getText().toString());
                String kategori = (((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString());
                String kordinat = (((TextView) findViewById(R.id.kordinat)).getText().toString());
                String usernameAduan = (((TextView) findViewById(R.id.usernameAduan)).getText().toString());;



                // Menggunakan Volley untuk melakukan request ke PHP Endpoint
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://tugasaja.masuk.web.id/applogindanregisterandroid/upload_data.php", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Tanggapan dari server
                        Toast.makeText(PengaduanActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Kesalahan yang terjadi saat koneksi atau permintaan
                        Toast.makeText(PengaduanActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        // Mengirim data dalam bentuk parameter ke PHP Endpoint
                        Map<String, String> params = new HashMap<>();
                        params.put("urlGambar", urlGambar);
                        params.put("keterangan", keterangan);
                        params.put("kategori", kategori);
                        params.put("kordinat", kordinat);
                        params.put("username", usernameAduan);
                        return params;
                    }
                };

                // Menambahkan request ke antrian request Volley
                Volley.newRequestQueue(PengaduanActivity.this).add(stringRequest);
            }
        });
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getCurrentLocation();
        }
    }

    private void createLocationRequest() {
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String coordinates = latitude + " " + longitude;
                        kordinatTextView.setText(coordinates);

                        buttonGPS.setText("Upload Berhasil!");
                        buttonGPS.setEnabled(true);
                    }
                }
            }
        };
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        buttonGPS.setText("GET Lokasi...");
        buttonGPS.setEnabled(false);
        Toast.makeText(this, "Aplikasi akan mengambil lokasi beberapa kali untuk akurasi", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Izin lokasi ditolak", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openWebsite() {
        String url = "https://tugasaja.masuk.web.id/applogindanregisterandroid/uploadAwal.php";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
