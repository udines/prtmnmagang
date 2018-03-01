package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.NoPerjalanan;
import com.pertamina.pertaminatuban.finance.models.UraianPerjalanan;
import com.pertamina.pertaminatuban.service.FinanceClient;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputRincianActivity extends AppCompatActivity {

    private TextView nama, noPekerja, noPerjalanan, rangka, ke, waktuMulai, waktuSelesai;
    private String intentNama, intentNoPekerja, intentNoPerjalanan, intentRangka, intentKe, intentWaktuMulai, intentWaktuSelesai;
    private RecyclerView recyclerView;
    private Button tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rincian);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama = findViewById(R.id.input_rincian_nama);
        noPekerja = findViewById(R.id.input_rincian_no_pekerja);
        noPerjalanan = findViewById(R.id.input_rincian_no_perjalanan);
        rangka = findViewById(R.id.input_rincian_rangka);
        ke = findViewById(R.id.input_rincian_ke);
        waktuMulai = findViewById(R.id.input_rincian_waktu_mulai);
        waktuSelesai = findViewById(R.id.input_rincian_waktu_selesai);
        recyclerView = findViewById(R.id.input_rincian_recyclerview);
        tambah = findViewById(R.id.input_rincian_tambah);

        handleIntentExtras();
        handleTambah();
    }

    private void handleIntentExtras() {
        if (getIntentString("namaPekerja") != null) intentNama = getIntentString("namaPekerja");
        if (getIntentString("noPekerja") != null) intentNoPekerja = getIntentString("noPekerja");
        if (getIntentString("noPerjalanan") != null) intentNoPerjalanan = getIntentString("noPerjalanan");
        if (getIntentString("rangka") != null) intentRangka = getIntentString("rangka");
        if (getIntentString("tujuan") != null) intentKe = getIntentString("tujuan");
        if (getIntentString("waktuMulai") != null) intentWaktuMulai = getIntentString("waktuMulai");
        if (getIntentString("waktuSelesai") != null) intentWaktuSelesai = getIntentString("waktuSelesai");

        nama.setText(intentNama);
        noPekerja.setText(intentNoPekerja);
        noPerjalanan.setText(intentNoPerjalanan);
        rangka.setText(intentRangka);
        ke.setText(intentKe);
        waktuMulai.setText(intentWaktuMulai);
        waktuSelesai.setText(intentWaktuSelesai);
    }

    private String getIntentString(String key) {
        return getIntent().getStringExtra(key);
    }
    private void handleTambah() {
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TambahRincianActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("noPerjalanan", intentNoPerjalanan);
                startActivity(intent);
            }
        });
    }

    private void displayRincianList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(false);

        SharedPreferences preferences = InputRincianActivity.this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );

        final String key = preferences.getString("userKey", "none");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", key)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        FinanceClient financeClient = retrofit.create(FinanceClient.class);

        Log.w("no perjalanan", intentNoPerjalanan);
        Call<ArrayList<UraianPerjalanan>> call = financeClient.getUraianPerjalananRaw(
                new NoPerjalanan(intentNoPerjalanan)
        );
        Log.w("gson", new Gson().toJson(new NoPerjalanan(intentNoPerjalanan)));
        call.enqueue(new Callback<ArrayList<UraianPerjalanan>>() {
            @Override
            public void onResponse(Call<ArrayList<UraianPerjalanan>> call, Response<ArrayList<UraianPerjalanan>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    recyclerView.setAdapter(new UraianPerjalananAdapter(response.body()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UraianPerjalanan>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntentString("noPerjalanan") != null) intentNoPerjalanan = getIntentString("noPerjalanan");
        displayRincianList();
    }
}
