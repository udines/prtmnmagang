package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.InputMatbalActivity;
import com.pertamina.pertaminatuban.finance.models.LaporanPerjalananDinas;
import com.pertamina.pertaminatuban.service.FinanceClient;

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

public class InputPerjalananDinasActivity extends AppCompatActivity {

    private EditText inputNoPegawai, inputNamaPegawai, inputNoPerjalanan, inputRangka, inputKe;
    private String noPegawai, namaPegawai, noPerjalanan, rangka, ke;
    private Button buttonWaktuMulai, buttonWaktuSelesai, buttonLanjut;
    private int yearMulai, monthMulai, dayMulai, yearSelesai, monthSelesai, daySelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_perjalanan_dinas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputNoPegawai = findViewById(R.id.input_perjalanan_dinas_no_pegawai);
        inputNamaPegawai = findViewById(R.id.input_perjalanan_dinas_nama_pegawai);
        inputNoPerjalanan = findViewById(R.id.input_perjalanan_dinas_no_perjalanan_dinas);
        inputRangka = findViewById(R.id.input_perjalanan_dinas_dalam_rangka);
        inputKe = findViewById(R.id.input_perjalanan_dinas_ke);
        buttonWaktuMulai = findViewById(R.id.input_perjalanan_dinas_waktu_mulai);
        buttonWaktuSelesai = findViewById(R.id.input_perjalanan_dinas_waktu_selesai);
        buttonLanjut = findViewById(R.id.input_perjalanan_dinas_lanjut);

        Calendar cal = Calendar.getInstance();
        yearMulai = cal.get(Calendar.YEAR);
        monthMulai = cal.get(Calendar.MONTH);
        dayMulai = cal.get(Calendar.DAY_OF_MONTH);
        yearSelesai = yearMulai;
        monthSelesai = monthMulai;
        daySelesai = dayMulai;

        setButtonDate(yearMulai, monthMulai, dayMulai, buttonWaktuMulai);
        setButtonDate(yearSelesai, monthSelesai, daySelesai, buttonWaktuSelesai);

        handleButtonLanjut();
        handleButtonDate();
    }

    private void handleButtonDate() {
        buttonWaktuMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yearMulai = i;
                        monthMulai = i1;
                        dayMulai = i2;
                        setButtonDate(yearMulai, monthMulai, dayMulai, buttonWaktuMulai);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        InputPerjalananDinasActivity.this,
                        listener,
                        yearMulai,
                        monthMulai,
                        dayMulai
                );
                dialog.show();
            }
        });

        buttonWaktuSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yearSelesai = i;
                        monthSelesai = i1;
                        daySelesai = i2;
                        setButtonDate(yearSelesai, monthSelesai, daySelesai, buttonWaktuSelesai);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        InputPerjalananDinasActivity.this,
                        listener,
                        yearSelesai,
                        monthSelesai,
                        daySelesai
                );
                dialog.show();
            }
        });
    }

    private void setButtonDate(int year, int month, int day, Button dateButton) {
        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        dateButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void handleButtonLanjut() {
        buttonLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isi(inputNoPegawai) && isi(inputNamaPegawai) && isi(inputNoPerjalanan) &&
                        isi(inputRangka) && isi(inputKe)) {
                    noPegawai = inputNoPegawai.getText().toString();
                    namaPegawai = inputNamaPegawai.getText().toString();
                    noPerjalanan = inputNoPerjalanan.getText().toString();
                    rangka = inputRangka.getText().toString();
                    ke = inputKe.getText().toString();

                    String dateStart, dateEnd;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(yearMulai, monthMulai, dayMulai);
                    dateStart = format.format(new Date(calendar.getTimeInMillis()));
                    calendar.set(yearSelesai, monthSelesai, daySelesai);
                    dateEnd = format.format(new Date(calendar.getTimeInMillis()));

                    LaporanPerjalananDinas laporan = new LaporanPerjalananDinas(
                            namaPegawai,
                            noPegawai,
                            noPerjalanan,
                            rangka,
                            ke,
                            dateStart,
                            dateEnd
                    );
                    sendPostRequest(laporan);
                } else {
                    Toast.makeText(InputPerjalananDinasActivity.this, "Lengkapi isian data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendPostRequest(final LaporanPerjalananDinas laporan) {
        Log.w("date json", new Gson().toJson(laporan));

        SharedPreferences preferences = InputPerjalananDinasActivity.this.getSharedPreferences(
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
        FinanceClient financeClient = retrofit.create(FinanceClient.class);
        Call<Object> call = financeClient.postPerjalananDinas(laporan);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("response", response.body().toString());
                    Intent intent = new Intent(getApplicationContext(), InputRincianActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("noPekerja", laporan.getNoPekerja());
                    intent.putExtra("namaPekerja", laporan.getNama());
                    intent.putExtra("noPerjalanan", laporan.getNoPerjalanan());
                    intent.putExtra("rangka", laporan.getRangka());
                    intent.putExtra("tujuan", laporan.getTujuan());
                    intent.putExtra("waktuMulai", laporan.getWaktuMulai());
                    intent.putExtra("waktuSelesai", laporan.getWaktuSelesai());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("error", t.getMessage());
                Toast.makeText(InputPerjalananDinasActivity.this, "Gagal mengirim laporan", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isi(EditText editText) {
        return editText.getText().length() > 0;
    }
}
