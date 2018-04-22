package com.pertamina.pertaminatuban.operation.pumpable;

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
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;
import com.pertamina.pertaminatuban.distribusi.WilayahActivity;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.operation.models.Pumpable;
import com.pertamina.pertaminatuban.service.OperationClient;
import com.pertamina.pertaminatuban.utils.MethodCollection;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private RecyclerView totalPertamaxRec, totalPremiumRec, totalSolarRec,
    avPertamaxRec, avPremiumRec, avSolarRec, scPertamaxRec, scPremiumRec, scSolarRec,
    utilPertamaxRec, utilPremiumRec, utilSolarRec, sc90PertamaxRec, sc90PremiumRec, sc90SolarRec,
    rerataUtilPertamaxRec, rerataUtilPremiumRec, rerataUtilSolaRec;
    private TextView totalPertamax, totalPremium, totalSolar,
            avPertamax, avPremium, avSolar, scPertamax, scPremium, scSolar,
            utilPertamax, utilPremium, utilSolar, sc90Pertamax, sc90Premium, sc90Solar,
            rerataUtilPertamax, rerataUtilPremium, rerataUtilSolar;

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

        totalPertamaxRec = findViewById(R.id.pumpable_recyclerview_pertamax);
        totalPremiumRec = findViewById(R.id.pumpable_recyclerview_premium);
        totalSolarRec = findViewById(R.id.pumpable_recyclerview_solar);
        avPertamaxRec = findViewById(R.id.pumpable_average_recyclerview_pertamax);
        avPremiumRec = findViewById(R.id.pumpable_average_recyclerview_premium);
        avSolarRec = findViewById(R.id.pumpable_average_recyclerview_solar);
        scPertamaxRec = findViewById(R.id.pumpable_total_sc_100_recyclerview_pertamax);
        scPremiumRec = findViewById(R.id.pumpable_total_sc_100_recyclerview_premium);
        scSolarRec = findViewById(R.id.pumpable_total_sc_100_recyclerview_solar);
        utilPertamaxRec = findViewById(R.id.pumpable_total_utilitas_recyclerview_pertamax);
        utilPremiumRec = findViewById(R.id.pumpable_total_utilitas_recyclerview_premium);
        utilSolarRec = findViewById(R.id.pumpable_total_utilitas_recyclerview_solar);
        sc90PertamaxRec = findViewById(R.id.pumpable_total_sc_90_recyclerview_pertamax);
        sc90PremiumRec = findViewById(R.id.pumpable_total_sc_90_recyclerview_premium);
        sc90SolarRec = findViewById(R.id.pumpable_total_sc_90_recyclerview_solar);
        rerataUtilPertamaxRec = findViewById(R.id.pumpable_rerata_utilitas_recyclerview_pertamax);
        rerataUtilPremiumRec = findViewById(R.id.pumpable_rerata_utilitas_recyclerview_premium);
        rerataUtilSolaRec = findViewById(R.id.pumpable_rerata_utilitas_recyclerview_solar);

        totalPertamax = findViewById(R.id.pumpable_total_pertamax);
        totalPremium = findViewById(R.id.pumpable_total_premium);
        totalSolar = findViewById(R.id.pumpable_total_solar);
        avPertamax = findViewById(R.id.pumpable_average_pertamax);
        avPremium = findViewById(R.id.pumpable_average_premium);
        avSolar = findViewById(R.id.pumpable_average_solar);
        scPertamax = findViewById(R.id.pumpable_sc100_pertamax);
        scPremium = findViewById(R.id.pumpable_sc100_premium);
        scSolar = findViewById(R.id.pumpable_sc100_solar);
        utilPertamax = findViewById(R.id.pumpable_utilitas_pertamax);
        utilPremium = findViewById(R.id.pumpable_utilitas_premium);
        utilSolar = findViewById(R.id.pumpable_utilitas_solar);
        sc90Pertamax = findViewById(R.id.pumpable_sc90_pertamax);
        sc90Premium = findViewById(R.id.pumpable_sc90_premium);
        sc90Solar = findViewById(R.id.pumpable_sc90_solar);
        rerataUtilPertamax = findViewById(R.id.pumpable_rerata_pertamax);
        rerataUtilPremium = findViewById(R.id.pumpable_rerata_premium);
        rerataUtilSolar = findViewById(R.id.pumpable_rerata_solar);

        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setBulanButton(month, year, bulanButton);

        handleBulanButton(bulanButton);
        handleInputButton();
        getPumpable(month, year);
    }

    private void handleInputButton() {
        inputButton = findViewById(R.id.pumpable_tambah_button);
        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("operasional") || role.equals("admin")) {
            inputButton.setVisibility(View.VISIBLE);
        } else {
            inputButton.setVisibility(View.GONE);
        }
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputPumpableActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
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

                builder.setMinYear(2018)
                        .setMaxYear(2050)
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

        Call<Object> call = operationClient.getPumpableRaw(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                String json = new Gson().toJson(response.body());
                Log.w("object", json);
                if (json.length() <= 2) {
                    Toast.makeText(PumpableActivity.this, "Tidak ada data bulan ini", Toast.LENGTH_LONG).show();
                    clearTextData();
                }
                try {
                    JSONObject raw = new JSONObject(new Gson().toJson(response.body()));

                    ArrayList<Pumpable> totalPerTank = new ArrayList<>();
                    ArrayList<Pumpable> averagePerTank = new ArrayList<>();
                    ArrayList<Pumpable> scPerTank = new ArrayList<>();
                    ArrayList<Pumpable> utilPerTank = new ArrayList<>();
                    ArrayList<Pumpable> sc90 = new ArrayList<>();
                    ArrayList<Pumpable> rerataUtil = new ArrayList<>();

                    JSONArray totalPerTankJson = raw.getJSONArray("totalPumpable");
                    JSONArray averagePerTankJson = raw.getJSONArray("averagePumpable");
                    JSONArray scPerTankJson = raw.getJSONArray("totalsc100");
                    JSONArray utilPerTankJson = raw.getJSONArray("totalutil");
                    JSONArray sc90Json = raw.getJSONArray("totalsc90");
                    JSONArray rerataUtilJson = raw.getJSONArray("rerataUtilitas");

                    totalPerTank = parseJsonToPumpable(totalPerTankJson);
                    averagePerTank = parseJsonToPumpable(averagePerTankJson);
                    scPerTank = parseJsonToPumpable(scPerTankJson);
                    utilPerTank = parseJsonToPumpable(utilPerTankJson);
                    sc90 = parseJsonToPumpable(sc90Json);
                    rerataUtil = parseJsonToPumpable(rerataUtilJson);

                    populateRecyclerView(totalPerTank, totalPertamaxRec, totalPremiumRec, totalSolarRec);
                    populateRecyclerView(averagePerTank, avPertamaxRec, avPremiumRec, avSolarRec);
                    populateRecyclerView(scPerTank, scPertamaxRec, scPremiumRec, scSolarRec);
                    populateRecyclerView(utilPerTank, utilPertamaxRec, utilPremiumRec, utilSolarRec);
                    populateRecyclerView(sc90, sc90PertamaxRec, sc90PremiumRec, sc90SolarRec);
                    populateRecyclerView(rerataUtil, rerataUtilPertamaxRec, rerataUtilPremiumRec, rerataUtilSolaRec);

                    populateSummaryBulat(totalPerTank, totalPertamax, totalPremium, totalSolar, " KL");
                    populateSummaryBulat(averagePerTank, avPertamax, avPremium, avSolar, " KL");
                    populateSummaryBulat(scPerTank, scPertamax, scPremium, scSolar, " KL");
                    populateSummary(utilPerTank, utilPertamax, utilPremium, utilSolar, "%");
                    populateSummaryBulat(sc90, sc90Pertamax, sc90Premium, sc90Solar, " KL");
                    populateSummary(rerataUtil, rerataUtilPertamax, rerataUtilPremium, rerataUtilSolar, "%");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void clearTextData() {
        totalPertamax.setText("");
        totalPremium.setText("");
        totalSolar.setText("");
        avPertamax.setText("");
        avPremium.setText("");
        avSolar.setText("");
        scPertamax.setText("");
        scPremium.setText("");
        scSolar.setText("");
        utilPertamax.setText("");
        utilPremium.setText("");
        utilSolar.setText("");
        sc90Pertamax.setText("");
        sc90Premium.setText("");
        sc90Solar.setText("");
        rerataUtilPertamax.setText("");
        rerataUtilPremium.setText("");
        rerataUtilSolar.setText("");
    }

    private void populateSummaryBulat(ArrayList<Pumpable> pumpables, TextView pertamax, TextView premium, TextView solar, String satuan) {
        for (int i = 0; i < pumpables.size(); i++) {
            switch (pumpables.get(i).getFuel()) {
                case Matbal.PERTAMAX:
                    if (pumpables.get(i).getNoTank().equals("total")) {
                        pertamax.setText(String.valueOf(
                                MethodCollection.numberWithDot(pumpables.get(i).getValue()) + satuan
                        ));
                    }
                    Log.w("pertamax", new Gson().toJson(pumpables.get(i)));
                    break;
                case Matbal.PREMIUM:
                    if (pumpables.get(i).getNoTank().equals("total")) {
                        premium.setText(String.valueOf(
                                MethodCollection.numberWithDot(pumpables.get(i).getValue()) + satuan
                        ));
                    }
                    break;
                case Matbal.SOLAR:
                    if (pumpables.get(i).getNoTank().equals("total")) {
                        solar.setText(String.valueOf(
                                MethodCollection.numberWithDot(pumpables.get(i).getValue()) + satuan
                        ));
                    }
                    break;
            }
        }
    }

    private void populateSummary(
            ArrayList<Pumpable> pumpables,
            TextView pertamax,
            TextView premium,
            TextView solar,
            String satuan
    ) {
        for (int i = 0; i < pumpables.size(); i++) {
            switch (pumpables.get(i).getFuel()) {
                case Matbal.PERTAMAX:
                    if (pumpables.get(i).getNoTank().equals("total")) {
                        pertamax.setText(String.valueOf(MethodCollection.numberWithComma(pumpables.get(i).getValue()) + satuan));
                    }
                    break;
                case Matbal.PREMIUM:
                    if (pumpables.get(i).getNoTank().equals("total")) {
                        premium.setText(String.valueOf(MethodCollection.numberWithComma(pumpables.get(i).getValue()) + satuan));
                    }
                    break;
                case Matbal.SOLAR:
                    if (pumpables.get(i).getNoTank().equals("total")) {
                        solar.setText(String.valueOf(MethodCollection.numberWithComma(pumpables.get(i).getValue()) + satuan));
                    }
                    break;
            }
        }
    }

    private void populateRecyclerView(
            ArrayList<Pumpable> pumpables,
            RecyclerView recyclerPertamax,
            RecyclerView recyclerPremium,
            RecyclerView recyclerSolar
    ) {
        ArrayList<Pumpable> pumpablesPertamax = new ArrayList<>();
        ArrayList<Pumpable> pumpablesPremium = new ArrayList<>();
        ArrayList<Pumpable> pumpablesSolar = new ArrayList<>();

        for (int i = 0; i < pumpables.size(); i++) {
            switch (pumpables.get(i).getFuel()) {
                case Matbal.PERTAMAX:
                    pumpablesPertamax.add(pumpables.get(i));
                    break;
                case Matbal.PREMIUM:
                    pumpablesPremium.add(pumpables.get(i));
                    break;
                case Matbal.SOLAR:
                    pumpablesSolar.add(pumpables.get(i));
                    break;
            }
        }

        recyclerPertamax.setNestedScrollingEnabled(false);
        recyclerPremium.setNestedScrollingEnabled(false);
        recyclerSolar.setNestedScrollingEnabled(false);
        recyclerPertamax.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerPremium.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerSolar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerPertamax.setAdapter(new PumpableAdapter(pumpablesPertamax));
        recyclerPremium.setAdapter(new PumpableAdapter(pumpablesPremium));
        recyclerSolar.setAdapter(new PumpableAdapter(pumpablesSolar));
    }

    private ArrayList<Pumpable> parseJsonToPumpable(JSONArray jsonArray) throws JSONException {
        ArrayList<Pumpable> pumpables = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            Pumpable pumpable = new Pumpable();
            pumpable.setFuel(jsonArray.getJSONObject(i).getString("fuel"));
            pumpable.setValue(jsonArray.getJSONObject(i).getDouble("value"));
            pumpable.setNoTank(jsonArray.getJSONObject(i).getString("no_tank"));
            pumpables.add(pumpable);
        }
        return pumpables;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getPumpable(month, year);
    }
}
