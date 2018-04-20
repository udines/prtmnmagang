package com.pertamina.pertaminatuban.operation.supplybbm;

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

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.operation.models.Suplai;
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

public class InputSuplaiBbmActivity extends AppCompatActivity {

    private EditText inputExtankerImportPremium, inputExtankerImportPertamax, inputExtankerImportSolar,
    inputExtankerDomestikPertamax, inputExtankerDomestikPremium, inputExtankerDomestikSolar,
    inputExtppiPertamax, inputExtppiPremium, inputExtppiSolar, inputExtwuPertamax, inputExtwuPremium,
    inputExtwuSolar;
    private Button tanggalButton, kirim, hapus;
    private ProgressBar progressBar;

    private ArrayList<Suplai> suplais;
    private ArrayList<String> suplaiIds;
    private ArrayList<String> deleteIds;
    private int day, month, year;
    private boolean isUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_suplai_bbm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputExtankerImportPremium = findViewById(R.id.input_suplai_extanker_import_premium);
        inputExtankerImportPertamax = findViewById(R.id.input_suplai_extanker_import_pertamax);
        inputExtankerImportSolar = findViewById(R.id.input_suplai_extanker_import_solar);
        inputExtankerDomestikPertamax = findViewById(R.id.input_suplai_extanker_domestik_pertamax);
        inputExtankerDomestikPremium = findViewById(R.id.input_suplai_extanker_domestik_premium);
        inputExtankerDomestikSolar = findViewById(R.id.input_suplai_extanker_domestik_solar);
        inputExtppiPertamax = findViewById(R.id.input_suplai_extppi_pipeline_pertamax);
        inputExtppiPremium = findViewById(R.id.input_suplai_extppi_pipeline_premium);
        inputExtppiSolar = findViewById(R.id.input_suplai_extppi_pipeline_solar);
        inputExtwuPertamax = findViewById(R.id.input_suplai_extwu_mobiltangki_pertamax);
        inputExtwuPremium = findViewById(R.id.input_suplai_extwu_mobiltangki_premium);
        inputExtwuSolar = findViewById(R.id.input_suplai_extwu_mobiltangki_solar);
        tanggalButton = findViewById(R.id.input_suplai_tanggal);
        kirim = findViewById(R.id.input_suplai_button_kirim);
        progressBar = findViewById(R.id.input_suplai_progress);
        hapus = findViewById(R.id.input_suplai_button_hapus);

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setTanggalButton(day, month, year);
        getSuplaiTanggal(year, month, day);

        handleTanggalButton();
        handleKirimButton();
        handleHapus();
    }

    private void handleHapus() {
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEntries();
                Log.w("id length", String.valueOf(deleteIds.size()));
                if (deleteIds.size() > 0) {
                    for (int i = 0; i < deleteIds.size(); i++) {
                        deleteData(deleteIds.get(i));
                    }
                }
            }
        });
    }

    private void deleteData(String id) {
        SharedPreferences preferences = InputSuplaiBbmActivity.this.getSharedPreferences(
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
        Call<Object> call = operationClient.deleteSuplai(id);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void setTanggalButton(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        tanggalButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void handleKirimButton() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kirim.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                suplais = new ArrayList<>();
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String date = format.format(new Date(cal.getTimeInMillis()));
                suplais.add(makeObject(date, Suplai.SUP_EXTANKER, Suplai.TRANS_IMPORT, Matbal.PERTAMAX, inputExtankerImportPertamax));
                suplais.add(makeObject(date, Suplai.SUP_EXTANKER, Suplai.TRANS_IMPORT, Matbal.PREMIUM, inputExtankerImportPremium));
                suplais.add(makeObject(date, Suplai.SUP_EXTANKER, Suplai.TRANS_IMPORT, Matbal.SOLAR, inputExtankerImportSolar));
                suplais.add(makeObject(date, Suplai.SUP_EXTANKER, Suplai.TRANS_DOMESTIK, Matbal.PERTAMAX, inputExtankerDomestikPertamax));
                suplais.add(makeObject(date, Suplai.SUP_EXTANKER, Suplai.TRANS_DOMESTIK, Matbal.PREMIUM, inputExtankerDomestikPremium));
                suplais.add(makeObject(date, Suplai.SUP_EXTANKER, Suplai.TRANS_DOMESTIK, Matbal.SOLAR, inputExtankerDomestikSolar));
                suplais.add(makeObject(date, Suplai.SUP_EXTPPI, Suplai.TRANS_PIPE, Matbal.PERTAMAX, inputExtppiPertamax));
                suplais.add(makeObject(date, Suplai.SUP_EXTPPI, Suplai.TRANS_PIPE, Matbal.PREMIUM, inputExtppiPremium));
                suplais.add(makeObject(date, Suplai.SUP_EXTPPI, Suplai.TRANS_PIPE, Matbal.SOLAR, inputExtppiSolar));
                suplais.add(makeObject(date, Suplai.SUP_EXTWU, Suplai.TRANS_MT, Matbal.PERTAMAX, inputExtwuPertamax));
                suplais.add(makeObject(date, Suplai.SUP_EXTWU, Suplai.TRANS_MT, Matbal.PREMIUM, inputExtwuPremium));
                suplais.add(makeObject(date, Suplai.SUP_EXTWU, Suplai.TRANS_MT, Matbal.SOLAR, inputExtwuSolar));

                //set id untuk masing2 suplai untuk keperluan update/put method
                if (suplaiIds != null && suplaiIds.size() > 0) {
                    for (int i = 0; i < suplais.size(); i++) {
                        suplais.get(i).setId(suplaiIds.get(i));
                    }
                }

                if (isUpdate) {
                    sendPutRequest(suplais);
                } else {
                    sendPostRequest(suplais);
                }
            }
        });
    }

    private void sendPutRequest(ArrayList<Suplai> suplais) {
        SharedPreferences preferences = InputSuplaiBbmActivity.this.getSharedPreferences(
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
        Call<Object> call = operationClient.putSuplai(suplais);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
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

    private void getSuplaiTanggal(int year, int month, int day) {
        clearEntries();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String tanggal = format.format(new Date(cal.getTimeInMillis()));

        SharedPreferences preferences = InputSuplaiBbmActivity.this.getSharedPreferences(
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
        Call<ArrayList<Suplai>> call = operationClient.getSuplaiTanggal(tanggal);
        call.enqueue(new Callback<ArrayList<Suplai>>() {
            @Override
            public void onResponse(Call<ArrayList<Suplai>> call, Response<ArrayList<Suplai>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    setInitialInput(response.body());
                    deleteIds = new ArrayList<>();
                    for (int i = 0; i < response.body().size(); i++) {
                        deleteIds.add(response.body().get(i).getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Suplai>> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void setInitialInput(ArrayList<Suplai> suplais) {
        suplaiIds = new ArrayList<>();
        for (int s = 0; s < suplais.size(); s++) {
            suplaiIds.add("");
        }
        if (suplais.size() > 0) {
            isUpdate = true;
            for (int i = 0; i < suplais.size(); i++) {
                Suplai suplai = suplais.get(i);
                switch (suplai.getSuplai()) {
                    case Suplai.SUP_EXTANKER:
                        if (suplai.getTransaksi().equals(Suplai.TRANS_IMPORT)) {
                            switch (suplai.getFuel()) {
                                case Matbal.PERTAMAX:
                                    inputExtankerImportPertamax.setText(String.valueOf(suplai.getValue()));
                                    suplaiIds.set(0, suplai.getId());
                                    break;
                                case Matbal.PREMIUM:
                                    inputExtankerImportPremium.setText(String.valueOf(suplai.getValue()));
                                    suplaiIds.set(1, suplai.getId());
                                    break;
                                case Matbal.SOLAR:
                                    inputExtankerImportSolar.setText(String.valueOf(suplai.getValue()));
                                    suplaiIds.set(2, suplai.getId());
                                    break;
                            }
                        } else if (suplai.getTransaksi().equals(Suplai.TRANS_DOMESTIK)) {
                            switch (suplai.getFuel()) {
                                case Matbal.PERTAMAX:
                                    inputExtankerDomestikPertamax.setText(String.valueOf(suplai.getValue()));
                                    suplaiIds.set(3, suplai.getId());
                                    break;
                                case Matbal.PREMIUM:
                                    inputExtankerDomestikPremium.setText(String.valueOf(suplai.getValue()));
                                    suplaiIds.set(4, suplai.getId());
                                    break;
                                case Matbal.SOLAR:
                                    inputExtankerDomestikSolar.setText(String.valueOf(suplai.getValue()));
                                    suplaiIds.set(5, suplai.getId());
                                    break;
                            }
                        }
                        break;
                    case Suplai.SUP_EXTPPI:
                        switch (suplai.getFuel()) {
                            case Matbal.PERTAMAX:
                                inputExtppiPertamax.setText(String.valueOf(suplai.getValue()));
                                suplaiIds.set(6, suplai.getId());
                                break;
                            case Matbal.PREMIUM:
                                inputExtppiPremium.setText(String.valueOf(suplai.getValue()));
                                suplaiIds.set(7, suplai.getId());
                                break;
                            case Matbal.SOLAR:
                                inputExtppiSolar.setText(String.valueOf(suplai.getValue()));
                                suplaiIds.set(8, suplai.getId());
                                break;
                        }
                        break;
                    case Suplai.SUP_EXTWU:
                        switch (suplai.getFuel()) {
                            case Matbal.PERTAMAX:
                                inputExtwuPertamax.setText(String.valueOf(suplai.getValue()));
                                suplaiIds.set(9, suplai.getId());
                                break;
                            case Matbal.PREMIUM:
                                inputExtwuPremium.setText(String.valueOf(suplai.getValue()));
                                suplaiIds.set(10, suplai.getId());
                                break;
                            case Matbal.SOLAR:
                                inputExtwuSolar.setText(String.valueOf(suplai.getValue()));
                                suplaiIds.set(11, suplai.getId());
                                break;
                        }
                        break;
                }
            }
        }
    }

    private void sendPostRequest(ArrayList<Suplai> suplais) {
        SharedPreferences preferences = InputSuplaiBbmActivity.this.getSharedPreferences(
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
        Call<Object> call = operationClient.postSuplai(suplais);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
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
                        getSuplaiTanggal(year, month, day);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        InputSuplaiBbmActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private Suplai makeObject(String date, String suplai, String transaksi, String fuel, EditText inputValue) {
        Suplai sup = new Suplai();
        sup.setSuplai(suplai);
        sup.setFuel(fuel);
        sup.setTransaksi(transaksi);
        sup.setDate(date);
        if (inputValue.getText().length() > 0) {
            sup.setValue(Long.parseLong(inputValue.getText().toString()));
        } else {
            sup.setValue(0);
        }
        return sup;
    }

    private Suplai makeObject(String date, String suplai, String transaksi, String fuel) {
        Suplai sup = new Suplai();
        sup.setSuplai(suplai);
        sup.setFuel(fuel);
        sup.setTransaksi(transaksi);
        sup.setDate(date);
        sup.setValue(0);
        return sup;
    }

    private void clearEntries() {
        inputExtankerImportPremium.setText("");inputExtankerImportPertamax.setText("");
        inputExtankerImportSolar.setText("");
        inputExtankerDomestikPertamax.setText("");
        inputExtankerDomestikPremium.setText("");
        inputExtankerDomestikSolar.setText("");
        inputExtppiPertamax.setText("");
        inputExtppiPremium.setText("");
        inputExtppiSolar.setText("");
        inputExtwuPertamax.setText("");
        inputExtwuPremium.setText("");
        inputExtwuSolar.setText("");
        suplaiIds = new ArrayList<>();
        suplais = new ArrayList<>();
        isUpdate = false;
    }
}
