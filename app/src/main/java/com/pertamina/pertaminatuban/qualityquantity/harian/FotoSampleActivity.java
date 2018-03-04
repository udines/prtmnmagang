package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.bulanan.WorkingLossActivity;
import com.pertamina.pertaminatuban.qualityquantity.models.ItemFotoSample;
import com.pertamina.pertaminatuban.service.QqClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.DateFormatSymbols;
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

public class FotoSampleActivity extends AppCompatActivity {

    private TextView bulan;
    private int year, month, day;
    private RecyclerView recyclerView;
    private TextView emptyText;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bulan = findViewById(R.id.foto_sampel_bulan);
        recyclerView = findViewById(R.id.foto_sample_recyclerview);
        emptyText = findViewById(R.id.foto_sample_empty_text);
        progressBar = findViewById(R.id.foto_sample_progressbar);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        setMonthButton(month, year);

        updateUi(year, month);
        handleDateButton();
        handleUploadButton();
    }

    private void handleDateButton() {
        bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        FotoSampleActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setMonthButton(month, year);
                                updateUi(year, month);
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

    private void handleUploadButton() {
        Button upload = findViewById(R.id.foto_sampel_upload_button);
        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("distribusi") || role.equals("admin")) {
            upload.setVisibility(View.VISIBLE);
        } else {
            upload.setVisibility(View.GONE);
        }
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputFotoSampleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setMonthButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        bulan.setText(text);
    }

    private void updateUi(int year, int month) {
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = FotoSampleActivity.this.getSharedPreferences(
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

        Log.w("GET ", "start getting matbal bulan " + bulan);
        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        QqClient qqClient = retrofit.create(QqClient.class);

        Call<ArrayList<ItemFotoSample>> call = qqClient.getFotoSampleBulan(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<ArrayList<ItemFotoSample>>() {
            @Override
            public void onResponse(Call<ArrayList<ItemFotoSample>> call, Response<ArrayList<ItemFotoSample>> response) {
                Log.w("code", String.valueOf(response.code()));
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.w("size", String.valueOf(response.body().size()));
                    ArrayList<ItemFotoSample> fotoSamples = response.body();
                    if (fotoSamples != null) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        FotoSampleAdapter adapter = new FotoSampleAdapter(
                                fotoSamples,
                                getApplicationContext()
                        );
                        recyclerView.setAdapter(adapter);

                        if (adapter.getItemCount() <= 0) {
                            emptyText.setVisibility(View.VISIBLE);
                        } else {
                            emptyText.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ItemFotoSample>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
