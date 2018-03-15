package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.service.UserClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputRitaseActivity extends AppCompatActivity {

    private int year, month, day;
    private boolean tanggalSet;
    private Button tanggal, kirim;
    private EditText inputJumlahMobil, inputDayaAngkut, inputTpHarian;
    private ProgressBar progressBar;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ritase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         /*inisialisasi view yang digunakan lebih dari satu fungsi*/
        tanggal = findViewById(R.id.input_ritase_tanggal);
        inputJumlahMobil = findViewById(R.id.input_ritase_jumlah_mobil);
        inputDayaAngkut = findViewById(R.id.input_ritase_daya_angkut);
        inputTpHarian = findViewById(R.id.input_ritase_tp_harian);
        kirim = findViewById(R.id.input_ritase_kirim);
        progressBar = findViewById(R.id.input_ritase_progress);

        isUpdate = false;

        /*init tanggal*/
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        handleDatePicker();
        handleKirimButton();
    }

    private void handleKirimButton() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDateSet()) {
                    if (inputJumlahMobil.getText().length() > 0 &&
                            inputDayaAngkut.getText().length() > 0 &&
                            inputTpHarian.getText().length() > 0) {

                        int jumlahMobil, dayaAngkut;
                        double tpHarian;
                        String tanggal;

                        jumlahMobil = Integer.parseInt(inputJumlahMobil.getText().toString());
                        dayaAngkut = Integer.parseInt(inputDayaAngkut.getText().toString());
                        tpHarian = Double.parseDouble(inputTpHarian.getText().toString());

                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        tanggal = format.format(new Date(cal.getTimeInMillis()));

                        ArrayList<Ritase> ritases = new ArrayList<>();
                        ritases.add(new Ritase(
                                jumlahMobil,
                                dayaAngkut,
                                tpHarian,
                                tanggal
                        ));

                        if (isUpdate) {
                            sendUpdateRequest(ritases);
                        } else {
                            sendPostRequest(ritases);
                        }
                    } else {
                        Toast.makeText(InputRitaseActivity.this, "Lengkapi data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void sendUpdateRequest(ArrayList<Ritase> ritases) {
        Log.w("input size", String.valueOf(ritases.size()));

        kirim.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = InputRitaseActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(ritases);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.updateRitaseTanggal(ritases);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("response ", response.body().toString());

                    kirim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(InputRitaseActivity.this, "Data berhasil diperbarui", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());

                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private boolean isDateSet() {
        if (!tanggalSet) {
            Toast.makeText(this, "Pilih tanggal", Toast.LENGTH_SHORT).show();
        }
        return tanggalSet;
    }

    private void setDateButton(int year, int month, int day) {
        tanggal.setText(String.valueOf(year + " - " + month + 1 + " - " + day));
    }

    private void handleDatePicker() {
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1;
                day = i2;
                setDateButton(year, month, day);
                tanggalSet = true;
                getInitialData();
            }
        };

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("button", "date button clicked");
                DatePickerDialog dialog = new DatePickerDialog(
                        InputRitaseActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void getInitialData() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        String tanggalSekarang;
        tanggalSekarang = date.toString();
        Log.w("tanggal", tanggalSekarang);

        SharedPreferences preferences = InputRitaseActivity.this.getSharedPreferences(
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
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Ritase> call = userClient.getRitaseTanggal(tanggalSekarang);
        call.enqueue(new Callback<Ritase>() {
            @Override
            public void onResponse(Call<Ritase> call, Response<Ritase> response) {
                Log.w("code", String.valueOf(response.code()));
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200 && response.body() != null) {
                    Log.w("body", new Gson().toJson(response.body()));
                    isUpdate = true;

                    Ritase object = response.body();
                    inputDayaAngkut.setText(String.valueOf(object.getDayaAngkut()));
                    inputJumlahMobil.setText(String.valueOf(object.getJumlahMobil()));
                    inputTpHarian.setText(String.valueOf(object.getTpHarian()));
                }
                Log.w("is update", String.valueOf(isUpdate));
            }

            @Override
            public void onFailure(Call<Ritase> call, Throwable t) {
                Log.e("error", t.getMessage());
                isUpdate = false;
                Log.w("is update", String.valueOf(isUpdate));
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void sendPostRequest(ArrayList<Ritase> ritases) {
        Log.w("input size", String.valueOf(ritases.size()));

        kirim.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = InputRitaseActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(ritases);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.postRitase(ritases);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("response ", response.body().toString());

                    kirim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(InputRitaseActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());

                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
