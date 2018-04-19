package com.pertamina.pertaminatuban.operation.pumpable;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.InputKonsumenActivity;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.operation.models.Datels;
import com.pertamina.pertaminatuban.operation.models.Pumpable;
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

public class InputPumpableActivity extends AppCompatActivity {

    private int day, month, year;
    private Button dateButton, tambahButton, kirimButton;
    private RecyclerView recyclerView;
    private InputPumpableAdapter adapter;
    private int putLength = 0;

    private ArrayList<Pumpable> postPumpables, putPumpables;
    private boolean postSuccess, putSuccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pumpable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateButton = findViewById(R.id.input_pumpable_tanggal);
        recyclerView = findViewById(R.id.input_pumpable_recycler);
        tambahButton = findViewById(R.id.input_pumpable_tambah);
        kirimButton = findViewById(R.id.input_pumpable_kirim);

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        setDateButton(day, month, year, dateButton);
        handleDateButton();
        getPumpableTanggal(day, month, year);
        handleTambahButton();
        handleKirimButton();
    }

    private void handleKirimButton() {
        kirimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Pumpable> pumpables = adapter.getPumpables();
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String date = format.format(new Date(cal.getTimeInMillis()));
                for (int i = 0; i < pumpables.size(); i++) {
                    pumpables.get(i).setDate(date);
                }
                Log.w("kirim", new Gson().toJson(pumpables));

                postPumpables = new ArrayList<>();
                putPumpables = new ArrayList<>();

                Log.w("put length", String.valueOf(putLength));

                for (int i = 0; i < putLength; i++) {
                    putPumpables.add(pumpables.get(i));
                }
                for (int j = putLength; j < pumpables.size(); j++) {
                    postPumpables.add(pumpables.get(j));
                }

                Log.w("post", new Gson().toJson(postPumpables));
                Log.w("put", new Gson().toJson(putPumpables));
                sendPostRequest(postPumpables);
                sendPutRequest(putPumpables);
            }
        });
    }

    private void sendPutRequest(ArrayList<Pumpable> putPumpables) {
        SharedPreferences preferences = InputPumpableActivity.this.getSharedPreferences(
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
        Call<Object> call = operationClient.putPumpable(
                putPumpables
        );
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("msg", String.valueOf(response.body()));
                    Toast.makeText(InputPumpableActivity.this, "Input berhasil disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
                Toast.makeText(InputPumpableActivity.this, "Input gagal disimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendPostRequest(ArrayList<Pumpable> postPumpables) {
        SharedPreferences preferences = InputPumpableActivity.this.getSharedPreferences(
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
        Call<Object> call = operationClient.postPumpable(
                postPumpables
        );
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("msg", String.valueOf(response.body()));
                    Toast.makeText(InputPumpableActivity.this, "Input berhasil disimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
                Toast.makeText(InputPumpableActivity.this, "Input gagal disimpan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleTambahButton() {
        tambahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter != null && adapter.getItemCount() > 0) {
                    addInputForm(adapter.getPumpables());
                }
            }
        });
    }

    private void populateInput(ArrayList<Pumpable> pumpables) {
        adapter = new InputPumpableAdapter(pumpables, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<Pumpable> getDefaultPumpables() {
        ArrayList<Pumpable> pumpables = new ArrayList<>();
        pumpables.add(new Pumpable("1",
                Matbal.PERTAMAX,
                0));
        pumpables.add(new Pumpable("2",
                Matbal.PERTAMAX,
                0));
        pumpables.add(new Pumpable("3",
                Matbal.PERTAMAX,
                0));
        pumpables.add(new Pumpable("9",
                Matbal.PERTAMAX,
                0));
        pumpables.add(new Pumpable("11",
                Matbal.PERTAMAX,
                0));
        pumpables.add(new Pumpable("4",
                Matbal.PREMIUM,
                0));
        pumpables.add(new Pumpable("5",
                Matbal.PREMIUM,
                0));
        pumpables.add(new Pumpable("6",
                Matbal.PREMIUM,
                0));
        pumpables.add(new Pumpable("7",
                Matbal.PREMIUM,
                0));
        pumpables.add(new Pumpable("8",
                Matbal.SOLAR,
                0));
        pumpables.add(new Pumpable("10",
                Matbal.SOLAR,
                0));
        pumpables.add(new Pumpable("12",
                Matbal.SOLAR,
                0));

        return pumpables;
    }

    private void populateEmptyInput() {
        ArrayList<Pumpable> pumpables = getDefaultPumpables();

        adapter = new InputPumpableAdapter(pumpables, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void addInputForm(ArrayList<Pumpable> currentPumpables) {
        currentPumpables.add(new Pumpable(
                String.valueOf(""),
                Matbal.PERTAMAX,
                0
        ));
        adapter = new InputPumpableAdapter(currentPumpables, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
    }

    private void handleDateButton() {
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        setDateButton(day, month, year, dateButton);
                        getPumpableTanggal(day, month, year);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        InputPumpableActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void getPumpableTanggal(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggal = format.format(new Date(cal.getTimeInMillis()));

        SharedPreferences preferences = InputPumpableActivity.this.getSharedPreferences(
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
        Call<ArrayList<Pumpable>> call = operationClient.getPumpableTanggal(
                tanggal,
                new Datels("")
        );
        call.enqueue(new Callback<ArrayList<Pumpable>>() {
            @Override
            public void onResponse(Call<ArrayList<Pumpable>> call, Response<ArrayList<Pumpable>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    if (response.body().size() > 0) {
                        populateInput(response.body());
//                        putPumpables = response.body();
//                        Log.w("put pumpables", new Gson().toJson(putPumpables));
                        putLength = response.body().size();
                    } else {
                        populateEmptyInput();
//                        postPumpables.addAll(getDefaultPumpables());
//                        Log.w("post pumpables", new Gson().toJson(postPumpables));
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Pumpable>> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });

        /*Call<Object> call = operationClient.getPumpableTanggalRaw(
                tanggal,
                new Datels("")
        );
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("body", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });*/
    }

    private void setDateButton(int day, int month, int year, Button dateButton) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        dateButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

}
