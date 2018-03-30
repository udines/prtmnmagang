package com.pertamina.pertaminatuban.saranafasilitas;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.WilayahActivity;
import com.pertamina.pertaminatuban.saranafasilitas.models.GetBody;
import com.pertamina.pertaminatuban.saranafasilitas.models.Sarfas;
import com.pertamina.pertaminatuban.saranafasilitas.utils.SaranaAdapter;
import com.pertamina.pertaminatuban.service.SarfasClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaranaFasilitasActivity extends AppCompatActivity {

    private TextView tanggal;
    private Button tambahButton;
    private int year, month;
    private RecyclerView recyclerView;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarana_fasilitas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tanggal = findViewById(R.id.sarana_bulan_button);
        tambahButton = findViewById(R.id.sarana_tambah_button);
        recyclerView = findViewById(R.id.sarana_recycler);
        spinner = findViewById(R.id.sarana_tab_spinner);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        setTanggalButton(month, year);
        handleSpinner();
        handleTanggalButton();
        handleTambahButton();
    }

    private void handleSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sarfas_tab, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSarfasBulan(month, year, spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getSarfasBulan(int month, int year, String tab) {
        SharedPreferences preferences = SaranaFasilitasActivity.this.getSharedPreferences(
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
        SarfasClient sarfasClient = retrofit.create(SarfasClient.class);
        Call<ArrayList<Sarfas>> call = sarfasClient.getSarfasBulan(
                new GetBody(tab),
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<ArrayList<Sarfas>>() {
            @Override
            public void onResponse(Call<ArrayList<Sarfas>> call, Response<ArrayList<Sarfas>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    populateSarfas(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Sarfas>> call, Throwable t) {

            }
        });
    }

    private void populateSarfas(ArrayList<Sarfas> sarfases) {
        if (sarfases.size() > 0) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(new SaranaAdapter(sarfases));
        }
    }

    private void setTanggalButton(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        tanggal.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void handleTanggalButton() {
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        SaranaFasilitasActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setTanggalButton(month, year);
                                getSarfasBulan(month, year, spinner.getSelectedItem().toString());
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .setTitle("Pilih bulan dan tahun")
                        .setActivatedMonth(month)
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void handleTambahButton() {
        tambahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputIntent = new Intent(getApplicationContext(), InputSaranaFasilitasActivity.class);
                inputIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inputIntent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSarfasBulan(month, year, spinner.getSelectedItem().toString());
    }
}