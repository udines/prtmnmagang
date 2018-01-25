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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;
import com.pertamina.pertaminatuban.service.UserClient;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputWilayahActivity extends AppCompatActivity {

    private int year, month, day;
    private Spinner spinnerBb, spinnerKons;
    private Button tanggal;
    private Button kirim;
    private EditText inputNilai, inputWilayah;
    private boolean tanggalSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_wilayah);
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
        spinnerBb = findViewById(R.id.input_wilayah_jenis_bb_spinner);
        spinnerKons = findViewById(R.id.input_wilayah_jenis_konsumen_spinner);
        tanggal = findViewById(R.id.input_wilayah_tanggal);
        kirim = findViewById(R.id.input_wilayah_kirim);
        inputNilai = findViewById(R.id.input_wilayah_nilai);
        inputWilayah = findViewById(R.id.input_wilayah_lokasi);

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
                if (isDateSet() && lokasiAda() && nilaiAda()) {
                    String fuel = spinnerBb.getSelectedItem().toString();
                    String konsumen = spinnerKons.getSelectedItem().toString();
                    float nilai = Float.parseFloat(inputNilai.getText().toString());
                    String wilayah = inputWilayah.getText().toString();
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, month, day);
                    Date date = new Date(calendar.getTimeInMillis());
                    String sqlDate = date.toString();
                    ArrayList<Wilayah> wilayahs = new ArrayList<>();
                    wilayahs.add(new Wilayah(
                            wilayah,
                            konsumen,
                            fuel,
                            sqlDate,
                            nilai,
                            0
                    ));
                    sendPostRequest(wilayahs);
                }
            }
        });
    }

    private boolean lokasiAda() {
        boolean ada = false;
        if (inputWilayah.getText().toString().isEmpty()) {
            Toast.makeText(this, "Input lokasi pengiriman", Toast.LENGTH_SHORT).show();
        } else {
            ada = true;
        }
        return ada;
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
            }
        };

        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("button", "date button clicked");
                DatePickerDialog dialog = new DatePickerDialog(
                        InputWilayahActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void sendPostRequest(ArrayList<Wilayah> wilayahs) {
        Log.w("input size", String.valueOf(wilayahs.size()));

        SharedPreferences preferences = InputWilayahActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(wilayahs);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.postWilayah(wilayahs);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("response ", response.body().toString());
                    Toast.makeText(InputWilayahActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }
}
