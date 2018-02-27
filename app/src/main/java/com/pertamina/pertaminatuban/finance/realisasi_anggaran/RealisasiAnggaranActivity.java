package com.pertamina.pertaminatuban.finance.realisasi_anggaran;

import android.content.Context;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;
import com.pertamina.pertaminatuban.finance.costperliter.CostPerLiterActivity;
import com.pertamina.pertaminatuban.finance.models.PostRealisasiAnggaran;
import com.pertamina.pertaminatuban.finance.models.RealisasiAnggaran;
import com.pertamina.pertaminatuban.service.FinanceClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RealisasiAnggaranActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private int year;
    private TextView tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realisasi_anggaran);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.realisasi_anggaran_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tahun = findViewById(R.id.realisasi_anggaran_tahun);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        setTahunButton(year);

        getRealisasiAnggaran(year);
        handleTahunButton();
    }

    private void handleTahunButton() {
        tahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        RealisasiAnggaranActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                year = selectedYear;
                                setTahunButton(year);
                                getRealisasiAnggaran(year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .showYearOnly()
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void setTahunButton(int year) {
        tahun.setText(String.valueOf(year));
    }

    private void getRealisasiAnggaran(int year) {
        SharedPreferences preferences = RealisasiAnggaranActivity.this.getSharedPreferences(
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

        Call<ArrayList<RealisasiAnggaran>> call = financeClient.getRealisasiAnggaran(
                String.valueOf(year)
        );
        Log.w("event","start enqueue");
        call.enqueue(new Callback<ArrayList<RealisasiAnggaran>>() {
            @Override
            public void onResponse(Call<ArrayList<RealisasiAnggaran>> call, Response<ArrayList<RealisasiAnggaran>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    recyclerView.setAdapter(new RealisasiAnggaranAdapter(
                            response.body(), getApplicationContext()
                    ));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RealisasiAnggaran>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });

        Call<Object> rawCall = financeClient.getRealisasiAnggaranRaw(
                new PostRealisasiAnggaran("A0903880","Terminal BBM Tuban")
        );
        rawCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    String json = gson.toJson(response.body());
                    Log.w("data", json);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

}
