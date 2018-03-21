package com.pertamina.pertaminatuban.operation.pumpable;

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
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;
import com.pertamina.pertaminatuban.distribusi.WilayahActivity;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.operation.models.Pumpable;
import com.pertamina.pertaminatuban.service.OperationClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.DecimalFormat;
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

public class PumpableActivity extends AppCompatActivity {

    private int month, year;
    private TextView bulanButton;
    private Button inputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pumpable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bulanButton = findViewById(R.id.pumpable_bulan_button);

        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setBulanButton(month, year, bulanButton);

        handleBulanButton(bulanButton);
        getPumpable(month, year);
    }

    private void handleBulanButton(TextView button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        PumpableActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setBulanButton(month, year, bulanButton);
                                getPumpable(month, year);
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

    private void setBulanButton(int month, int year, TextView bulanButton) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        bulanButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void getPumpable(int month, int year) {
        SharedPreferences preferences = PumpableActivity.this.getSharedPreferences(
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
        OperationClient operationClient = retrofit.create(OperationClient.class);
        Call<ArrayList<Pumpable>> call = operationClient.getPumpable(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<ArrayList<Pumpable>>() {
            @Override
            public void onResponse(Call<ArrayList<Pumpable>> call, Response<ArrayList<Pumpable>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    populateData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pumpable>> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void populateData(ArrayList<Pumpable> pumpables) {
        if (pumpables.size() > 0) {
            ArrayList<Pumpable> pumpablePertamax, pumpablePremium, pumpableSolar;
            pumpablePertamax = new ArrayList<>();
            pumpablePremium = new ArrayList<>();
            pumpableSolar = new ArrayList<>();

            for (int i = 0; i < pumpables.size(); i++) {
                if (pumpables.get(i).getNoTank().length() < 5) {
                    switch (pumpables.get(i).getFuel()) {
                        case Matbal.PERTAMAX:
                            pumpablePertamax.add(pumpables.get(i));
                        case Matbal.PREMIUM:
                            pumpablePremium.add(pumpables.get(i));
                        case Matbal.SOLAR:
                            pumpableSolar.add(pumpables.get(i));
                    }
                }
            }

            attachAdapter(pumpablePertamax, pumpablePremium, pumpableSolar);
        }
    }

    private void attachAdapter(ArrayList<Pumpable> pumpablePertamax, ArrayList<Pumpable> pumpablePremium, ArrayList<Pumpable> pumpableSolar) {
        RecyclerView recyclerPertamax, recyclerPremium, recyclerSolar;
        TextView totalPertamax, totalPremium, totalSolar;

        recyclerPertamax = findViewById(R.id.pumpable_recyclerview_pertamax);
        recyclerPremium = findViewById(R.id.pumpable_recyclerview_premium);
        recyclerSolar = findViewById(R.id.pumpable_recyclerview_solar);
        totalPertamax = findViewById(R.id.pumpable_total_value_pertamax);
        totalPremium = findViewById(R.id.pumpable_total_value_premium);
        totalSolar = findViewById(R.id.pumpable_total_value_solar);

        recyclerPertamax.setNestedScrollingEnabled(false);
        recyclerPremium.setNestedScrollingEnabled(false);
        recyclerSolar.setNestedScrollingEnabled(false);

        recyclerPertamax.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerPremium.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerSolar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DecimalFormat format = new DecimalFormat("#,###");

        if (pumpablePertamax.size() > 0) {
            recyclerPertamax.setAdapter(new PumpableAdapter(pumpablePertamax));
            totalPertamax.setText(format.format(getTotal(pumpablePertamax)));
        }
        if (pumpablePremium.size() > 0) {
            recyclerPremium.setAdapter(new PumpableAdapter(pumpablePremium));
            totalPremium.setText(format.format(getTotal(pumpablePremium)));
        }
        if (pumpableSolar.size() > 0) {
            recyclerSolar.setAdapter(new PumpableAdapter(pumpableSolar));
            totalSolar.setText(format.format(getTotal(pumpableSolar)));
        }
    }

    private double getTotal(ArrayList<Pumpable> pumpables) {
        double total = 0;
        for (int i = 0; i < pumpables.size(); i++) {
            total = total + pumpables.get(i).getValue();
        }
        return total;
    }
}
