package com.pertamina.pertaminatuban.operation.distribusibbm;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.operation.models.DistribusiBbm;
import com.pertamina.pertaminatuban.operation.models.Suplai;
import com.pertamina.pertaminatuban.operation.supplybbm.InputSuplaiBbmActivity;
import com.pertamina.pertaminatuban.service.OperationClient;

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

public class InputDistribusiBbmActivity extends AppCompatActivity {

    private EditText inputTankerPertamax, inputTankerPremium, inputTankerSolar, inputTankerPertalite,
    inputPipaPertamax, inputPipaPremium, inputPipaSolar, inputPipaPertalite, inputMobilPertamax,
    inputMobilPremium, inputMobilSolar, inputMobilPertalite;
    private Button tanggalButton, kirim, hapus;
    private int day, month, year;
    private ArrayList<DistribusiBbm> distribusis;
    private ArrayList<String> distribusiIds;
    private ProgressBar progressBar;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_distribusi_bbm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputTankerPertamax = findViewById(R.id.input_distribusi_bbm_tanker_pertamax);
        inputTankerPremium = findViewById(R.id.input_distribusi_bbm_tanker_premium);
        inputTankerSolar = findViewById(R.id.input_distribusi_bbm_tanker_solar);
        inputTankerPertalite = findViewById(R.id.input_distribusi_bbm_tanker_pertalite);
        inputPipaPertamax = findViewById(R.id.input_distribusi_bbm_pipe_pertamax);
        inputPipaPremium = findViewById(R.id.input_distribusi_bbm_pipe_premium);
        inputPipaSolar = findViewById(R.id.input_distribusi_bbm_pipe_solar);
        inputPipaPertalite = findViewById(R.id.input_distribusi_bbm_pipe_pertalite);
        inputMobilPertamax = findViewById(R.id.input_distribusi_bbm_mobil_pertamax);
        inputMobilPremium = findViewById(R.id.input_distribusi_bbm_mobil_premium);
        inputMobilSolar = findViewById(R.id.input_distribusi_bbm_mobil_solar);
        inputMobilPertalite = findViewById(R.id.input_distribusi_bbm_mobil_pertalite);
        tanggalButton = findViewById(R.id.input_distribusi_bbm_tanggal);
        kirim = findViewById(R.id.input_distribusi_bbm_button_kirim);
        progressBar = findViewById(R.id.input_distribusi_bbm_progress);
        hapus = findViewById(R.id.input_distribusi_bbm_button_hapus);

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setTanggalButton(day, month, year);
        getDistribusiTanggal(year, month, day);

        handleTanggalButton();
        handleKirimButton();
    }

    private void handleKirimButton() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirim.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                distribusis = new ArrayList<>();
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String date = format.format(new Date(cal.getTimeInMillis()));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_TANKER, DistribusiBbm.TRANS_TANKER, Matbal.PERTAMAX, inputTankerPertamax));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_TANKER, DistribusiBbm.TRANS_TANKER, Matbal.PREMIUM, inputTankerPremium));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_TANKER, DistribusiBbm.TRANS_TANKER, Matbal.SOLAR, inputTankerSolar));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_TANKER, DistribusiBbm.TRANS_TANKER, Matbal.PERTALITE, inputTankerPertalite));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_PIPA, DistribusiBbm.TRANS_PIPA, Matbal.PERTAMAX, inputPipaPertamax));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_PIPA, DistribusiBbm.TRANS_PIPA, Matbal.PREMIUM, inputPipaPremium));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_PIPA, DistribusiBbm.TRANS_PIPA, Matbal.SOLAR, inputPipaSolar));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_PIPA, DistribusiBbm.TRANS_PIPA, Matbal.PERTALITE, inputPipaPertalite));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_MOBIL, DistribusiBbm.TRANS_MOBIL, Matbal.PERTAMAX, inputMobilPertamax));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_MOBIL, DistribusiBbm.TRANS_MOBIL, Matbal.PREMIUM, inputMobilPremium));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_MOBIL, DistribusiBbm.TRANS_MOBIL, Matbal.SOLAR, inputMobilSolar));
                distribusis.add(makeObject(date, DistribusiBbm.DIST_MOBIL, DistribusiBbm.TRANS_MOBIL, Matbal.PERTALITE, inputMobilPertalite));

                if (distribusiIds != null && distribusiIds.size() > 0) {
                    for (int i = 0; i < distribusis.size(); i++) {
                        distribusis.get(i).setId(distribusiIds.get(i));
                    }
                }

                if (isUpdate) {
                    sendPutRequest(distribusis);
                } else {
                    sendPostRequest(distribusis);
                }
            }
        });
    }

    private void sendPutRequest(ArrayList<DistribusiBbm> distribusis) {
        SharedPreferences preferences = InputDistribusiBbmActivity.this.getSharedPreferences(
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
        Call<Object> call = operationClient.putDistribusi(distribusis);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    Toast.makeText(InputDistribusiBbmActivity.this, "Input berhasil disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InputDistribusiBbmActivity.this, "Input gagal disimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDistribusiTanggal(int year, int month, int day) {
        clearEntries();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggal = format.format(new Date(cal.getTimeInMillis()));

        SharedPreferences preferences = InputDistribusiBbmActivity.this.getSharedPreferences(
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
        Call<ArrayList<DistribusiBbm>> call = operationClient.getDistribusiTanggal(tanggal);
        call.enqueue(new Callback<ArrayList<DistribusiBbm>>() {
            @Override
            public void onResponse(Call<ArrayList<DistribusiBbm>> call, Response<ArrayList<DistribusiBbm>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    setInitialInput(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DistribusiBbm>> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void setInitialInput(ArrayList<DistribusiBbm> distribusis) {
        distribusiIds = new ArrayList<>();
        for (int s = 0; s < distribusis.size(); s++) {
            distribusiIds.add("");
        }

        if (distribusis.size() > 0) {
            isUpdate = true;
            for (int i = 0; i < distribusis.size(); i++) {
                DistribusiBbm distribusi = distribusis.get(i);
                switch (distribusi.getDistribusi()) {
                    case DistribusiBbm.DIST_TANKER:
                        switch (distribusi.getFuel()) {
                            case Matbal.PERTAMAX:
                                inputTankerPertamax.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(0, distribusi.getId());
                                break;
                            case Matbal.PREMIUM:
                                inputTankerPremium.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(1, distribusi.getId());
                                break;
                            case Matbal.SOLAR:
                                inputTankerSolar.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(2, distribusi.getId());
                                break;
                            case Matbal.PERTALITE:
                                inputTankerPertalite.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(3, distribusi.getId());
                                break;
                        }
                        break;
                    case DistribusiBbm.DIST_PIPA:
                        switch (distribusi.getFuel()) {
                            case Matbal.PERTAMAX:
                                inputPipaPertamax.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(4, distribusi.getId());
                                break;
                            case Matbal.PREMIUM:
                                inputPipaPremium.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(5, distribusi.getId());
                                break;
                            case Matbal.SOLAR:
                                inputPipaSolar.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(6, distribusi.getId());
                                break;
                            case Matbal.PERTALITE:
                                inputPipaPertalite.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(7, distribusi.getId());
                                break;
                        }
                        break;
                    case DistribusiBbm.DIST_MOBIL:
                        switch (distribusi.getFuel()) {
                            case Matbal.PERTAMAX:
                                inputMobilPertamax.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(8, distribusi.getId());
                                break;
                            case Matbal.PREMIUM:
                                inputMobilPremium.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(9, distribusi.getId());
                                break;
                            case Matbal.SOLAR:
                                inputMobilSolar.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(10, distribusi.getId());
                                break;
                            case Matbal.PERTALITE:
                                inputMobilPertalite.setText(String.valueOf(distribusi.getValue()));
                                distribusiIds.set(11, distribusi.getId());
                                break;
                        }
                        break;
                }
            }
        }
    }

    private void sendPostRequest(ArrayList<DistribusiBbm> distribusis) {
        SharedPreferences preferences = InputDistribusiBbmActivity.this.getSharedPreferences(
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
        Call<Object> call = operationClient.postDistribusi(distribusis);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    Toast.makeText(InputDistribusiBbmActivity.this, "Input berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InputDistribusiBbmActivity.this, "Input gagal ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTanggalButton(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        tanggalButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void handleTanggalButton() {
        tanggalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        setTanggalButton(day, month, year);
                        getDistribusiTanggal(year, month, day);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        InputDistribusiBbmActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private DistribusiBbm makeObject(String date, String distribusi, String transaksi, String fuel, EditText inputValue) {
        DistribusiBbm dist = new DistribusiBbm();
        dist.setDistribusi(distribusi);
        dist.setFuel(fuel);
        dist.setTransaksi(transaksi);
        dist.setDate(date);
        if (inputValue.getText().length() > 0) {
            dist.setValue(Long.parseLong(inputValue.getText().toString()));
        } else {
            dist.setValue(0);
        }
        return dist;
    }

    private void clearEntries() {
        inputTankerPertamax.setText("");
        inputTankerPremium.setText("");
        inputTankerSolar.setText("");
        inputTankerPertalite.setText("");
        inputPipaPertamax.setText("");
        inputPipaPremium.setText("");
        inputPipaSolar.setText("");
        inputPipaPertalite.setText("");
        inputMobilPertamax.setText("");
        inputMobilPremium.setText("");
        inputMobilSolar.setText("");
        inputMobilPertalite.setText("");
        isUpdate = false;
        distribusiIds = new ArrayList<>();
        distribusis = new ArrayList<>();
    }
}
