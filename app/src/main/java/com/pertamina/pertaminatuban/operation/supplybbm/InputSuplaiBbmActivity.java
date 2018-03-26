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
    private Button tanggalButton, kirim;

    private ArrayList<Suplai> suplais;
    private int day, month, year;


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

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setTanggalButton(day, month, year);

        handleTanggalButton();
        handleKirimButton();
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
                sendPostRequest(suplais);
            }
        });
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
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
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
}
