package com.seiagul.laporanseiagul;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class SelesaiActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String URL = "https://tugasaja.masuk.web.id/applogindanregisterandroid/selectTwo.php";
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<>();
    SelesaiAdapter selesaiAdapter;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selesai);
        swipe = findViewById(R.id.swipe);
        list = findViewById(R.id.list);
        sessionManager = new SessionManager(this);

        selesaiAdapter = new SelesaiAdapter(SelesaiActivity.this, itemList);
        list.setAdapter(selesaiAdapter);

        swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
            @Override
            public void run() {
                swipe.setRefreshing(true);
                itemList.clear();
                selesaiAdapter.notifyDataSetChanged();
                callVolley();
            }
        });
    }

    @Override
    public void onRefresh() {
        itemList.clear();
        selesaiAdapter.notifyDataSetChanged();
        callVolley();
    }

    private void callVolley() {
        itemList.clear();
        selesaiAdapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        String username = sessionManager.getUsername();
        String url = URL + "?username=" + username;

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Parsing json
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        Data item = new Data();

                        item.setId(obj.getString("id"));
                        item.setPelaporList(obj.getString("username"));
                        item.setKategoriList(obj.getString("kategori"));
                        item.setKordinatList(obj.getString("kordinat"));
                        item.setRincianList(obj.getString("keterangan"));
                        item.setGambarList(obj.getString("url_gambar"));

                        // menambah item ke array
                        itemList.add(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                // notifikasi adanya perubahan data pada adapter
                selesaiAdapter.notifyDataSetChanged();

                swipe.setRefreshing(false);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                swipe.setRefreshing(false);
            }
        });

        // menambah request ke request queue
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(jArr);
    }
}
