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
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.service.UserClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
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

public class InputMatbalActivity extends AppCompatActivity {

    private int year, month, day;
    private Button dateButton;
    private boolean tanggalSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_matbal);
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
        dateButton = findViewById(R.id.input_matbal_tanggal);

        /*inisialisasi tanggal sesuai dengan tanggal hari ini*/
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        handleDatePicker();

        getData();
    }

    private void getData() {
        final EditText inputPertamax, inputPertalite, inputPremium, inputSolar, inputBiosolar, inputBioflame,
                inputLain, inputLainNilai;
        Button send = findViewById(R.id.input_matbal_kirim);
        inputPertamax = findViewById(R.id.input_matbal_pertamax);
        inputPertalite = findViewById(R.id.input_matbal_pertalite);
        inputPremium = findViewById(R.id.input_matbal_premium);
        inputSolar = findViewById(R.id.input_matbal_solar);
        inputBiosolar = findViewById(R.id.input_matbal_biosolar);
        inputBioflame = findViewById(R.id.input_matbal_bioflame);
        inputLain = findViewById(R.id.input_matbal_lain);
        inputLainNilai = findViewById(R.id.input_matbal_lain_nilai);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tanggalSet) {
                    ArrayList<Matbal> matbals = new ArrayList<>();
                    if (!isNull(inputPertamax))matbals.add(makeObject(inputPertamax, Matbal.PERTAMAX));
                    if (!isNull(inputPertalite))matbals.add(makeObject(inputPertalite, Matbal.PERTALITE));
                    if (!isNull(inputPremium))matbals.add(makeObject(inputPremium, Matbal.PREMIUM));
                    if (!isNull(inputSolar))matbals.add(makeObject(inputSolar, Matbal.SOLAR));
                    if (!isNull(inputBiosolar))matbals.add(makeObject(inputBiosolar, Matbal.BIOSOLAR));
                    if (!isNull(inputBioflame))matbals.add(makeObject(inputBioflame, Matbal.BIOFLAME));
                    if (!isNull(inputLain) && !isNull(inputLainNilai)) {
                        matbals.add(makeObject(inputLainNilai, inputLain.getText().toString()));
                    }

                    sendPostRequest(matbals);
                } else {
                    Toast.makeText(InputMatbalActivity.this, "Belum memilih tanggal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendPostRequest(ArrayList<Matbal> matbals) {
        Log.w("input size", String.valueOf(matbals.size()));

        SharedPreferences preferences = InputMatbalActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(matbals);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.postMatbal(matbals);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("response size", response.body().toString());
                    Toast.makeText(InputMatbalActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private float getInputFloat(EditText editText) {
        return Float.parseFloat(editText.getText().toString());
    }

    private boolean isNull(EditText editText) {
        return editText.getText().toString().isEmpty();
    }

    private Matbal makeObject(EditText editText, String fuel) {
        float nilai;
        if (!isNull(editText)) {
            nilai = getInputFloat(editText);
        } else {
            nilai = 0;
        }
        String date = String.valueOf(year + "-" + month + 1 + "-" + day);
        return new Matbal(date, fuel, nilai);
    }

    private void setDateButton(int year, int month, int day) {
        dateButton.setText(String.valueOf(year + "-" + month + 1 + "-" + day));
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

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w("button", "date button clicked");
                DatePickerDialog dialog = new DatePickerDialog(
                        InputMatbalActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

}
