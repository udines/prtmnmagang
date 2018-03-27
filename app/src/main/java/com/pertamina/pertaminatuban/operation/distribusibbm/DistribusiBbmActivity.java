package com.pertamina.pertaminatuban.operation.distribusibbm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.operation.models.DistribusiBbm;
import com.pertamina.pertaminatuban.operation.models.Suplai;
import com.pertamina.pertaminatuban.operation.supplybbm.InputSuplaiBbmActivity;
import com.pertamina.pertaminatuban.operation.supplybbm.SuplaiBbmActivity;
import com.pertamina.pertaminatuban.service.OperationClient;
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

public class DistribusiBbmActivity extends AppCompatActivity {

    private TextView bulanButton;
    private Button tambahButton;
    private int month, year;
    private TextView tankerPertamax, tankerPremium, tankerSolar, tankerPertalite, pipaPertamax,
    pipaPremium, pipaSolar, pipaPertalite, mobilPertamax, mobilPremium, mobilSolar, mobilPertalite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribusi_bbm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bulanButton = findViewById(R.id.distribusi_bbm_bulan_button);
        tambahButton = findViewById(R.id.distribusi_bbm_tambah_button);
        tankerPertamax = findViewById(R.id.distribusi_bbm_tanker_pertamax);
        tankerPremium = findViewById(R.id.distribusi_bbm_tanker_premium);
        tankerSolar = findViewById(R.id.distribusi_bbm_tanker_solar);
        tankerPertalite = findViewById(R.id.distribusi_bbm_tanker_pertalite);
        pipaPertamax = findViewById(R.id.distribusi_bbm_pipa_pertamax);
        pipaPremium = findViewById(R.id.distribusi_bbm_pipa_premium);
        pipaSolar = findViewById(R.id.distribusi_bbm_pipa_solar);
        pipaPertalite = findViewById(R.id.distribusi_bbm_pipa_pertalite);
        mobilPertamax = findViewById(R.id.distribusi_bbm_mobil_pertamax);
        mobilPremium = findViewById(R.id.distribusi_bbm_mobil_premium);
        mobilSolar = findViewById(R.id.distribusi_bbm_mobil_solar);
        mobilPertalite = findViewById(R.id.distribusi_bbm_mobil_pertalite);

        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setBulanButton(month, year);
        getDistribusiBulan(month, year);

        handleBulanButton();
        handleTambahButton();
    }

    private void handleBulanButton() {
        bulanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        DistribusiBbmActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setBulanButton(month, year);
                                getDistribusiBulan(month, year);
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

    private void getDistribusiBulan(int month, int year) {

        SharedPreferences preferences = DistribusiBbmActivity.this.getSharedPreferences(
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
        Call<ArrayList<DistribusiBbm>> call = operationClient.getDistribusiBulan(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<ArrayList<DistribusiBbm>>() {
            @Override
            public void onResponse(Call<ArrayList<DistribusiBbm>> call, Response<ArrayList<DistribusiBbm>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    populateData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DistribusiBbm>> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void populateData(ArrayList<DistribusiBbm> distribusis) {
        if (distribusis.size() > 0) {
            for (int i = 0; i < distribusis.size(); i++) {
                DistribusiBbm distribusi = distribusis.get(i);
                switch (distribusi.getDistribusi()) {
                    case DistribusiBbm.DIST_TANKER:
                        switch (distribusi.getFuel()) {
                            case Matbal.PERTAMAX:
                                tankerPertamax.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.PREMIUM:
                                tankerPremium.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.SOLAR:
                                tankerSolar.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.PERTALITE:
                                tankerPertalite.setText(String.valueOf(distribusi.getValue()));
                                break;
                        }
                        break;
                    case DistribusiBbm.DIST_PIPA:
                        switch (distribusi.getFuel()) {
                            case Matbal.PERTAMAX:
                                pipaPertamax.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.PREMIUM:
                                pipaPremium.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.SOLAR:
                                pipaSolar.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.PERTALITE:
                                pipaPertalite.setText(String.valueOf(distribusi.getValue()));
                                break;
                        }
                        break;
                    case DistribusiBbm.DIST_MOBIL:
                        switch (distribusi.getFuel()) {
                            case Matbal.PERTAMAX:
                                mobilPertamax.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.PREMIUM:
                                mobilPremium.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.SOLAR:
                                mobilSolar.setText(String.valueOf(distribusi.getValue()));
                                break;
                            case Matbal.PERTALITE:
                                mobilPertalite.setText(String.valueOf(distribusi.getValue()));
                                break;
                        }
                        break;
                }
            }
        }
    }

    private void handleTambahButton() {
        tambahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputDistribusiBbmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setBulanButton(int month, int year) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        bulanButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getDistribusiBulan(month, year);
    }
}
