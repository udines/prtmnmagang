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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;
import com.pertamina.pertaminatuban.finance.costperliter.CostPerLiterActivity;
import com.pertamina.pertaminatuban.finance.models.PostRealisasiAnggaran;
import com.pertamina.pertaminatuban.finance.models.RealisasiAnggaran;
import com.pertamina.pertaminatuban.service.FinanceClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private BarChart barChart;

    private TextView actual, commitment, allotted, plan, available;

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
        recyclerView.setNestedScrollingEnabled(false);
        tahun = findViewById(R.id.realisasi_anggaran_tahun);

        actual = findViewById(R.id.realisasi_anggaran_actual);
        commitment = findViewById(R.id.realisasi_anggaran_commitment);
        allotted = findViewById(R.id.realisasi_anggaran_allotted);
        plan = findViewById(R.id.realisasi_anggaran_plan);
        available = findViewById(R.id.realisasi_anggaran_available);
        barChart = findViewById(R.id.realisasi_anggaran_chart);

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

    private void getRealisasiAnggaran(final int year) {
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
                    ArrayList<RealisasiAnggaran> realisasiAnggarans =  response.body();
                    Log.w("size", String.valueOf(realisasiAnggarans.size()));
                    if (realisasiAnggarans.size() > 0) {
                        /*for (int i = 0; i < realisasiAnggarans.size(); i++) {
                            if (realisasiAnggarans.get(i).getCost_elements().equals("Over/Underabsorption")) {
                                ArrayList<RealisasiAnggaran> overUnderAnggaran = new ArrayList<>();
                                overUnderAnggaran.add(realisasiAnggarans.get(i));
                                recyclerView.setAdapter(new RealisasiAnggaranAdapter(
                                        overUnderAnggaran, getApplicationContext()
                                ));
                                actual.setText(currencyFor(realisasiAnggarans.get(i).getActual()));
                                commitment.setText(currencyFor(realisasiAnggarans.get(i).getCommitment()));
                                allotted.setText(currencyFor(realisasiAnggarans.get(i).getAllotted()));
                                plan.setText(currencyFor(realisasiAnggarans.get(i).getPlan()));
                                available.setText(currencyFor(realisasiAnggarans.get(i).getAvailable()));
                            }
                        }*/
                        recyclerView.setAdapter(new NewRealisasiAdapter(realisasiAnggarans, year));
                        populateBarChart(realisasiAnggarans);
                    } /*else {
                        actual.setText("");
                        commitment.setText("");
                        allotted.setText("");
                        plan.setText("");
                        available.setText("");
                        ArrayList<RealisasiAnggaran> overUnderAnggaran = new ArrayList<>();
                        recyclerView.setAdapter(new RealisasiAnggaranAdapter(
                                overUnderAnggaran, getApplicationContext()
                        ));
                    }*/
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

    private void populateBarChart(ArrayList<RealisasiAnggaran> anggarans) {
        List<BarEntry> entries = new ArrayList<>();
        String[] values = new String[12];
        for (int i = 0; i < 11; i++) {
            if (i < anggarans.size()) {
                double plan, allotted;
                plan = anggarans.get(i).getPlan();
                allotted = anggarans.get(i).getAllotted();
                entries.add(new BarEntry(i + 1, (int) (allotted / plan * 100)));
            } else {
                entries.add(new BarEntry(i + 1, 0));
            }
            values[i] = String.valueOf(i + 1);
        }
        BarDataSet dataSet = new BarDataSet(entries, String.valueOf(year));
        BarData barData = new BarData(dataSet);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(values));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().setEnabled(false);
        barChart.setData(barData);
        barChart.invalidate();
    }

    private String currencyFor(double amount) {
        DecimalFormat format = new DecimalFormat("#,###");
        return format.format(amount);
    }
}
