package com.pertamina.pertaminatuban.operation.supplybbm;

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
import com.pertamina.pertaminatuban.operation.models.Suplai;
import com.pertamina.pertaminatuban.operation.pumpable.PumpableActivity;
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

public class SuplaiBbmActivity extends AppCompatActivity {

    private TextView bulanButton;
    private Button tambahButton;
    private int month, year;

    private TextView tankerImportPertamax, tankerImportPremium, tankerImportSolar, tankerDomestikPertamax,
    tankerDomestikPremium, tankerDomestikSolar, pipePertamax, pipePremium, pipeSolar, tangkiPertamax,
    tangkiPremium, tangkiSolar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suplai_bbm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bulanButton = findViewById(R.id.suplai_bbm_bulan_button);
        tambahButton = findViewById(R.id.suplai_bbm_tambah_button);
        tankerImportPertamax = findViewById(R.id.suplai_bbm_extanker_import_pertamax);
        tankerImportPremium = findViewById(R.id.suplai_bbm_extanker_import_premium);
        tankerImportSolar = findViewById(R.id.suplai_bbm_extanker_import_solar);
        tankerDomestikPertamax = findViewById(R.id.suplai_bbm_extanker_domestik_pertamax);
        tankerDomestikPremium = findViewById(R.id.suplai_bbm_extanker_domestik_premium);
        tankerDomestikSolar = findViewById(R.id.suplai_bbm_extanker_domestik_solar);
        pipePertamax = findViewById(R.id.suplai_bbm_extppi_pertamax);
        pipePremium = findViewById(R.id.suplai_bbm_extppi_premium);
        pipeSolar = findViewById(R.id.suplai_bbm_extppi_solar);
        tangkiPertamax = findViewById(R.id.suplai_bbm_extwu_pertamax);
        tangkiPremium = findViewById(R.id.suplai_bbm_extwu_premium);
        tangkiSolar = findViewById(R.id.suplai_bbm_extwu_solar);

        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setBulanButton(month, year);
        getSuplaiBulan(month, year);

        handleBulanButton();
        handleTambahButton();
    }

    private void getSuplaiBulan(int month, int year) {
        clearEntries();

        SharedPreferences preferences = SuplaiBbmActivity.this.getSharedPreferences(
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
        Call<ArrayList<Suplai>> call = operationClient.getSuplaiBulan(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<ArrayList<Suplai>>() {
            @Override
            public void onResponse(Call<ArrayList<Suplai>> call, Response<ArrayList<Suplai>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    populateData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Suplai>> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void populateData(ArrayList<Suplai> suplais) {
        if (suplais.size() > 0) {
            for (int i = 0; i < suplais.size(); i++) {
                Suplai suplai = suplais.get(i);
                switch (suplai.getSuplai()) {
                    case Suplai.SUP_EXTANKER:
                        if (suplai.getTransaksi().equals(Suplai.TRANS_IMPORT)) {
                            switch (suplai.getFuel()) {
                                case Matbal.PERTAMAX:
                                    tankerImportPertamax.setText(String.valueOf(suplai.getValue()));
                                    break;
                                case Matbal.PREMIUM:
                                    tankerImportPremium.setText(String.valueOf(suplai.getValue()));
                                    break;
                                case Matbal.SOLAR:
                                    tankerImportSolar.setText(String.valueOf(suplai.getValue()));
                                    break;
                            }
                        } else if (suplai.getTransaksi().equals(Suplai.TRANS_DOMESTIK)) {
                            switch (suplai.getFuel()) {
                                case Matbal.PERTAMAX:
                                    tankerDomestikPertamax.setText(String.valueOf(suplai.getValue()));
                                    break;
                                case Matbal.PREMIUM:
                                    tankerDomestikPremium.setText(String.valueOf(suplai.getValue()));
                                    break;
                                case Matbal.SOLAR:
                                    tankerDomestikSolar.setText(String.valueOf(suplai.getValue()));
                                    break;
                            }
                        }
                        break;
                    case Suplai.SUP_EXTPPI:
                        switch (suplai.getFuel()) {
                            case Matbal.PERTAMAX:
                                pipePertamax.setText(String.valueOf(suplai.getValue()));
                                break;
                            case Matbal.PREMIUM:
                                pipePremium.setText(String.valueOf(suplai.getValue()));
                                break;
                            case Matbal.SOLAR:
                                pipeSolar.setText(String.valueOf(suplai.getValue()));
                                break;
                        }
                        break;
                    case Suplai.SUP_EXTWU:
                        switch (suplai.getFuel()) {
                            case Matbal.PERTAMAX:
                                tangkiPertamax.setText(String.valueOf(suplai.getValue()));
                                break;
                            case Matbal.PREMIUM:
                                tangkiPremium.setText(String.valueOf(suplai.getValue()));
                                break;
                            case Matbal.SOLAR:
                                tangkiSolar.setText(String.valueOf(suplai.getValue()));
                                break;
                        }
                        break;
                }
            }
        }
    }

    private void clearEntries() {
        tankerImportPertamax.setText("0");
        tankerImportPremium.setText("0");
        tankerImportSolar.setText("0");
        tankerDomestikPertamax.setText("0");
        tankerDomestikPremium.setText("0");
        tankerDomestikSolar.setText("0");
        pipePertamax.setText("0");
        pipePremium.setText("0");
        pipeSolar.setText("0");
        tangkiPertamax.setText("0");
        tangkiPremium.setText("0");
        tangkiSolar.setText("0");
    }

    private void handleTambahButton() {
        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("operasional") || role.equals("admin")) {
            tambahButton.setVisibility(View.VISIBLE);
        } else {
            tambahButton.setVisibility(View.GONE);
        }
        tambahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputSuplaiBbmActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void handleBulanButton() {
        bulanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        SuplaiBbmActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setBulanButton(month, year);
                                getSuplaiBulan(month, year);
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

    private void setBulanButton(int month, int year) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        bulanButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getSuplaiBulan(month, year);
    }
}
