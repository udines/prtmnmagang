package com.pertamina.pertaminatuban.operation.sarfas;

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
import com.pertamina.pertaminatuban.operation.models.TransferPipeline;
import com.pertamina.pertaminatuban.operation.tfpipeline.InputTransferPipelineActivity;
import com.pertamina.pertaminatuban.operation.tfpipeline.TransferPipelineActivity;
import com.pertamina.pertaminatuban.operation.tftppi.TransferTppiActivity;
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

public class SarfasActivity extends AppCompatActivity {

    private Button tambahButton;
    private TextView dateButton;
    private int month, year;

    private TextView quantityPertamax, startPertamax, stopPertamax, jumlahPertamax,
            quantityPremium, startPremium, stopPremium, jumlahPremium,
            quantitySolar, startSolar, stopSolar, jumlahSolar,
            utilisasiPertamax, utilisasiPremium, utilisasiSolar,
            flowratePertamax, flowratePremium, flowrateSolar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarfas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tambahButton = findViewById(R.id.sarfas_tambah_button);
        dateButton = findViewById(R.id.sarfas_bulan_button);

        quantityPertamax = findViewById(R.id.transfer_pipeline_quantity_pertamax);
        startPertamax = findViewById(R.id.transfer_pipeline_start_pertamax);
        stopPertamax = findViewById(R.id.transfer_pipeline_stop_pertamax);
        jumlahPertamax = findViewById(R.id.transfer_pipeline_jumlah_pertamax);
        quantityPremium = findViewById(R.id.transfer_pipeline_quantity_premium);
        startPremium = findViewById(R.id.transfer_pipeline_start_premium);
        stopPremium = findViewById(R.id.transfer_pipeline_stop_premium);
        jumlahPremium = findViewById(R.id.transfer_pipeline_jumlah_premium);
        quantitySolar = findViewById(R.id.transfer_pipeline_quantity_solar);
        startSolar = findViewById(R.id.transfer_pipeline_start_solar);
        stopSolar = findViewById(R.id.transfer_pipeline_stop_solar);
        jumlahSolar = findViewById(R.id.transfer_pipeline_jumlah_solar);

        utilisasiPertamax = findViewById(R.id.transfer_pipeline_utilisasi_pertamax);
        utilisasiPremium = findViewById(R.id.transfer_pipeline_utilisasi_premium);
        utilisasiSolar = findViewById(R.id.transfer_pipeline_utilisasi_solar);
        flowratePertamax = findViewById(R.id.transfer_pipeline_flowrate_pertamax);
        flowratePremium = findViewById(R.id.transfer_pipeline_flowrate_premium);
        flowrateSolar = findViewById(R.id.transfer_pipeline_flowrate_solar);

        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setBulanButton(month, year, dateButton);
        getPipelineBulan(month, year);

        handleBulanButton(dateButton);
        handleInputButton();
    }

    private void handleInputButton() {
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
                Intent intent = new Intent(getApplicationContext(), InputSarfasActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setBulanButton(int month, int year, TextView bulanButton) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        bulanButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void handleBulanButton(TextView button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        SarfasActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setBulanButton(month, year, dateButton);
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

    private void getPipelineBulan(int month, int year) {
        SharedPreferences preferences = SarfasActivity.this.getSharedPreferences(
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
        Call<Object> call = operationClient.getTwuBulanRaw(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("body", new Gson().toJson(response.body()));
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    Log.w("json", jsonObject.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("totalPumpable");
                    ArrayList<TransferPipeline> pipelines = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        TransferPipeline pipeline = new TransferPipeline(
                                object.getLong("quantity"),
                                object.getString("start"),
                                object.getString("stop"),
                                0,
                                object.getString("jumlah"),
                                object.getString("fuel")
                        );
                        pipelines.add(pipeline);
                    }

                    for (int i = 0; i < pipelines.size(); i++) {
                        if (pipelines.get(i).getFuel().equals(Matbal.PERTAMAX)) {
                            quantityPertamax.setText(String.valueOf(numberWithDot(pipelines.get(i).getQuantity()) + " KL"));
                            startPertamax.setText(pipelines.get(i).getStart());
                            stopPertamax.setText(pipelines.get(i).getStop());
                            jumlahPertamax.setText(pipelines.get(i).getJumlah());
                        } else if (pipelines.get(i).getFuel().equals(Matbal.PREMIUM)) {
                            quantityPremium.setText(String.valueOf(numberWithDot(pipelines.get(i).getQuantity()) + " KL"));
                            startPremium.setText(pipelines.get(i).getStart());
                            stopPremium.setText(pipelines.get(i).getStop());
                            jumlahPremium.setText(pipelines.get(i).getJumlah());
                        } else if (pipelines.get(i).getFuel().equals(Matbal.SOLAR)) {
                            quantitySolar.setText(String.valueOf(numberWithDot(pipelines.get(i).getQuantity()) + " KL"));
                            startSolar.setText(pipelines.get(i).getStart());
                            stopSolar.setText(pipelines.get(i).getStop());
                            jumlahSolar.setText(pipelines.get(i).getJumlah());
                        }
                    }

                    JSONArray utilisasi = jsonObject.getJSONArray("utilisasiPipeline");
                    Log.w("utilisasi size", String.valueOf(utilisasi.length()));
                    for (int i = 0; i < utilisasi.length(); i++) {
                        JSONObject object = utilisasi.getJSONObject(i);
                        String key = object.keys().next();

                        Log.w("keys", key);
                        switch (key) {
                            case Matbal.PERTAMAX:
                                handledSetText(utilisasiPertamax, object.getString(key), "%");
                                break;
                            case Matbal.PREMIUM:
                                handledSetText(utilisasiPremium, object.getString(key), "%");
                                break;
                            case Matbal.SOLAR:
                                handledSetText(utilisasiSolar, object.getString(key), "%");
                                break;
                        }
                    }

                    JSONArray flowrate = jsonObject.getJSONArray("flowrate");
                    Log.w("flowrate size", String.valueOf(flowrate.length()));
                    for (int i = 0; i < flowrate.length(); i++) {
                        JSONObject object = flowrate.getJSONObject(i);
                        String key = object.keys().next();

                        Log.w("keys", key);
                        switch (key) {
                            case Matbal.PERTAMAX:
                                handledSetText(flowratePertamax, object.getString(key), "");
                                break;
                            case Matbal.PREMIUM:
                                handledSetText(flowratePremium, object.getString(key), "");
                                break;
                            case Matbal.SOLAR:
                                handledSetText(flowrateSolar, object.getString(key), "");
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private String numberWithDot(long angka) {
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(angka).replaceAll(",",".");
    }

    private void handledSetText(TextView target, String value, String satuan) {
        if (value != null) {
            target.setText(String.valueOf(value + satuan));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getPipelineBulan(month, year);
    }
}
