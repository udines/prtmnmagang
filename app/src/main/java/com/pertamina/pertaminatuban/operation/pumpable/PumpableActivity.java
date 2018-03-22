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

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;
import com.pertamina.pertaminatuban.distribusi.WilayahActivity;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.operation.models.Pumpable;
import com.pertamina.pertaminatuban.service.OperationClient;
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
    utilPertamaxRec, utilPremiumRec, utilSolarRec;

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

        Call<Object> call = operationClient.getPumpableRaw(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("object", new Gson().toJson(response.body()));
                try {
                    JSONObject raw = new JSONObject(new Gson().toJson(response.body()));

                    ArrayList<Pumpable> totalPerFuel = new ArrayList<>();
                    ArrayList<Pumpable> totalPerTank = new ArrayList<>();
                    ArrayList<Pumpable> averagePerFuel = new ArrayList<>();
                    ArrayList<Pumpable> averagePerTank = new ArrayList<>();
                    ArrayList<Pumpable> scPerTank = new ArrayList<>();
                    ArrayList<Pumpable> scPerFuel = new ArrayList<>();
                    ArrayList<Pumpable> utilPerTank = new ArrayList<>();
                    ArrayList<Pumpable> utilPerFuel = new ArrayList<>();

                    JSONArray totalPerFuelJson = raw.getJSONArray("totalperfuel");
                    JSONArray totalPerTankJson = raw.getJSONArray("datapertank");
                    JSONArray averagePerFuelJson = raw.getJSONArray("averageperfuel");
                    JSONArray averagePerTankJson = raw.getJSONArray("averagepertank");
                    JSONArray scPerTankJson = raw.getJSONArray("scpertank");
                    JSONArray scPerFuelJson = raw.getJSONArray("scperfuel");
                    JSONArray utilPerTankJson = raw.getJSONArray("utilpertank");
                    JSONArray utilPerFuelJson = raw.getJSONArray("utilperfuel");

//                    totalPerFuel = parseJsonToPumpable(totalPerFuelJson);
                    totalPerTank = parseJsonToPumpable(totalPerTankJson);
//                    averagePerFuel = parseJsonToPumpable(averagePerFuelJson);
                    averagePerTank = parseJsonToPumpable(averagePerTankJson);
                    scPerTank = parseJsonToPumpable(scPerTankJson);
//                    scPerFuel = parseJsonToPumpable(scPerFuelJson);
                    utilPerTank = parseJsonToPumpable(utilPerTankJson);
//                    utilPerFuel = parseJsonToPumpable(utilPerFuelJson);

                    populateRecyclerView(totalPerTank, totalPertamaxRec, totalPremiumRec, totalSolarRec);
                    populateRecyclerView(averagePerTank, avPertamaxRec, avPremiumRec, avSolarRec);
                    populateRecyclerView(scPerTank, scPertamaxRec, scPremiumRec, scSolarRec);
                    populateRecyclerView(utilPerTank, utilPertamaxRec, utilPremiumRec, utilSolarRec);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
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
            pumpable.setValue(jsonArray.getJSONObject(i).getLong("value"));
            pumpable.setNoTank(jsonArray.getJSONObject(i).getString("no_tank"));
            pumpables.add(pumpable);
        }
        return pumpables;
    }

    private double getTotal(ArrayList<Pumpable> pumpables) {
        double total = 0;
        for (int i = 0; i < pumpables.size(); i++) {
            total = total + pumpables.get(i).getValue();
        }
        return total;
    }
}
