package com.pertamina.pertaminatuban.saranafasilitas;

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
import com.pertamina.pertaminatuban.distribusi.InputOpersActivity;
import com.pertamina.pertaminatuban.saranafasilitas.models.Sarfas;
import com.pertamina.pertaminatuban.service.SarfasClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
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

public class InputSaranaFasilitasActivity extends AppCompatActivity {

    private EditText inputNama, inputNomor, inputKeterangan, inputMerk, inputTipe, inputFungsi,
    inputSpesifikasi, inputLokasi;
    private Button buttonTanggal, kirim;
    private Spinner spinner;

    int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_sarana_fasilitas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputNama = findViewById(R.id.input_sarana_nama);
        inputNomor = findViewById(R.id.input_sarana_nomor);
        inputKeterangan = findViewById(R.id.input_sarana_keterangan);
        inputMerk = findViewById(R.id.input_sarana_merk);
        inputTipe = findViewById(R.id.input_sarana_tipe);
        inputFungsi = findViewById(R.id.input_sarana_fungsi);
        inputSpesifikasi = findViewById(R.id.input_sarana_spesifikasi);
        inputLokasi = findViewById(R.id.input_sarana_lokasi);
        buttonTanggal = findViewById(R.id.input_sarana_tanggal);
        kirim = findViewById(R.id.input_sarana_kirim);
        spinner = findViewById(R.id.input_sarana_spinner);

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setTanggalButton(day, month, year);
        handleSpinner();
        handleTanggalButton();
        handleKirim();
    }

    private void handleSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sarfas_tab, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void handleKirim() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama, nomor, keterangan, merk, tipe, fungsi, spesifikasi, lokasi;
                nama = getInputData(inputNama);
                nomor = getInputData(inputNomor);
                keterangan = getInputData(inputKeterangan);
                merk = getInputData(inputMerk);
                tipe = getInputData(inputTipe);
                fungsi = getInputData(inputFungsi);
                spesifikasi = getInputData(inputSpesifikasi);
                lokasi = getInputData(inputLokasi);
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Sarfas sarfas = new Sarfas(
                        format.format(new Date(cal.getTimeInMillis())),
                        nomor,
                        nama,
                        fungsi,
                        lokasi,
                        tipe,
                        spesifikasi,
                        merk,
                        keterangan
                );
                sarfas.setTab(spinner.getSelectedItem().toString());
                sendPostRequest(sarfas);
            }

            private void sendPostRequest(Sarfas sarfas) {
                SharedPreferences preferences = InputSaranaFasilitasActivity.this.getSharedPreferences(
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

                String json = new Gson().toJson(sarfas);
                Log.w("json", json);

                OkHttpClient client = httpClient.build();

                String baseUrl = "http://www.api.clicktuban.com/";
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client);

                Retrofit retrofit = builder.build();
                SarfasClient sarfasClient = retrofit.create(SarfasClient.class);
                Call<Object> call = sarfasClient.postSarfas(sarfas);
                call.enqueue(new Callback<Object>() {
                    @Override
                    public void onResponse(Call<Object> call, Response<Object> response) {
                        Log.w("code", String.valueOf(response.code()));
                        if (response.code() == 200) {
                            Log.w("msg", String.valueOf(response.body()));
                            Toast.makeText(InputSaranaFasilitasActivity.this, "Berhasil input data", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Object> call, Throwable t) {
                        Log.w("error", t.getMessage());
                    }
                });
            }
        });
    }

    private String getInputData(EditText input) {
        if (input.getText().length() > 0) {
            return input.getText().toString();
        } else {
            return "";
        }
    }

    private void handleTanggalButton() {
        buttonTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        setTanggalButton(day, month, year);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        InputSaranaFasilitasActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void setTanggalButton(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        buttonTanggal.setText(format.format(new Date(cal.getTimeInMillis())));
    }

}
