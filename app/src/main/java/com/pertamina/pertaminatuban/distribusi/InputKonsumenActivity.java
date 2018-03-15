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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.service.UserClient;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.Calendar;

public class InputKonsumenActivity extends AppCompatActivity {

    /*button*/
    private Button kirim, tanggal;
    private ProgressBar progressBar;

    private int year, month, day;
    private boolean tanggalSet;
    private ArrayList<Konsumen> konsumens;

    private Spinner spinnerBb, spinnerKons;
    private EditText inputNilai;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_konsumen);
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
        spinnerBb = findViewById(R.id.input_konsumen_bb_spinner);
        spinnerKons = findViewById(R.id.input_konsumen_jenis_spinner);
        tanggal = findViewById(R.id.input_konsumen_tanggal);
        kirim = findViewById(R.id.input_konsumen_kirim);
        inputNilai = findViewById(R.id.input_konsumen_nilai);
        progressBar = findViewById(R.id.input_konsumen_progress);

        konsumens = new ArrayList<>();
        isUpdate = false;

        /*init tanggal*/
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        handleDatePicker();
        populateSpinner();
        getData();
    }

    private void getData() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDateSet() && nilaiAda()) {
                    String fuel = spinnerBb.getSelectedItem().toString();
                    String konsumen = spinnerKons.getSelectedItem().toString();
                    float nilai = Float.parseFloat(inputNilai.getText().toString());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    Date date = new Date(calendar.getTimeInMillis());
                    String sqlDate = date.toString();
                    ArrayList<Konsumen> konsumens = new ArrayList<>();
                    konsumens.add(new Konsumen(
                            sqlDate,
                            konsumen,
                            fuel,
                            nilai
                    ));

                    if (isUpdate) {
                        sendUpdateRequest(konsumens);
                    } else {
                        sendPostRequest(konsumens);
                    }
                }
            }
        });
    }

    private void sendUpdateRequest(ArrayList<Konsumen> konsumens) {

        kirim.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        Log.w("input size", String.valueOf(konsumens.size()));

        SharedPreferences preferences = InputKonsumenActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(konsumens);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);
        Call<Object> call = userClient.updateKonsumenTanggal(konsumens);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("response ", response.body().toString());

                    kirim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(InputKonsumenActivity.this, "Data berhasil diperbarui", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("error", t.getMessage());

                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void populateSpinner() {
        ArrayAdapter<CharSequence> adapterBb = ArrayAdapter.createFromResource(this,
                R.array.bahan_bakar, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterKons = ArrayAdapter.createFromResource(this,
                R.array.jenis_konsumen, android.R.layout.simple_spinner_item);

        adapterBb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterKons.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerBb.setAdapter(adapterBb);
        spinnerKons.setAdapter(adapterKons);

        spinnerBb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setInitialValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerKons.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setInitialValue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean isDateSet() {
        if (!tanggalSet) {
            Toast.makeText(this, "Pilih tanggal", Toast.LENGTH_SHORT).show();
        }
        return tanggalSet;
    }

    private boolean nilaiAda() {
        boolean ada = false;
        if (!inputNilai.getText().toString().isEmpty()) {
            ada = true;
        } else {
            Toast.makeText(this, "Input nilai", Toast.LENGTH_SHORT).show();
        }
        return ada;
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
                getKonsumenHari();
            }
        };

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("button", "date button clicked");
                DatePickerDialog dialog = new DatePickerDialog(
                        InputKonsumenActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void getKonsumenHari() {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = new Date(cal.getTimeInMillis());
        String tanggalSekarang;
        tanggalSekarang = date.toString();
        Log.w("tanggal", tanggalSekarang);

        SharedPreferences preferences = InputKonsumenActivity.this.getSharedPreferences(
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

        Call<ArrayList<Konsumen>> call = userClient.getKonsumenTanggal(tanggalSekarang);
        call.enqueue(new Callback<ArrayList<Konsumen>>() {
            @Override
            public void onResponse(Call<ArrayList<Konsumen>> call, Response<ArrayList<Konsumen>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    konsumens = response.body();
                    setInitialValue();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {

            }
        });
    }

    private void sendPostRequest(ArrayList<Konsumen> konsumens) {
        Log.w("input size", String.valueOf(konsumens.size()));

        kirim.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = InputKonsumenActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(konsumens);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.postKonsumen(konsumens);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("response ", response.body().toString());

                    kirim.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(InputKonsumenActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
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

    private void setInitialValue() {
        String fuel = spinnerBb.getSelectedItem().toString();
        String konsumen = spinnerKons.getSelectedItem().toString();
        Log.w("init value", fuel + " - " + konsumen);

        inputNilai.setText("");

        if (konsumens.size() > 0) {
            for (int i = 0; i < konsumens.size(); i++) {
                if (konsumens.get(i).getKonsumen().equals(konsumen) && konsumens.get(i).getFuel().equals(fuel)) {
                    inputNilai.setText(String.valueOf(konsumens.get(i).getNilai()));
                    isUpdate = true;
                    Log.w("fuel", String.valueOf(konsumens.get(i).getNilai()));
                    break;
                } else {
                    isUpdate = false;
                }
            }
        } else {
            isUpdate = false;
            Log.w("konsumens", "tidak ada");
        }
        Log.w("is update", String.valueOf(isUpdate));
    }
}
