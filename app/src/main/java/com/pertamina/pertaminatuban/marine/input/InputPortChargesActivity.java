package com.pertamina.pertaminatuban.marine.input;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.MarineIdentifier;
import com.pertamina.pertaminatuban.marine.models.MarineInput;
import com.pertamina.pertaminatuban.marine.models.NewMarineInput;
import com.pertamina.pertaminatuban.marine.models.PortCharges;
import com.pertamina.pertaminatuban.service.UserClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputPortChargesActivity extends AppCompatActivity {

    private EditText inputRambu, inputLabuh, inputTambat, inputPandu, inputTunda, inputPup;
    private String bulan, kapal, periode, callTanker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_port_charges);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
    }

    private void initData() {
        /*variable view*/
        Button kirim;

        /*hubungkan variable view dengan layout*/
        inputRambu = findViewById(R.id.input_port_charges_rambu);
        inputLabuh = findViewById(R.id.input_port_charges_labuh);
        inputTambat = findViewById(R.id.input_port_charges_tambat);
        inputPandu = findViewById(R.id.input_port_charges_pandu);
        inputTunda = findViewById(R.id.input_port_charges_tunda);
        inputPup = findViewById(R.id.input_port_charges_pup);
        kirim = findViewById(R.id.input_port_charges_kirim);

        getInitialData();

        /*handle pengguna klik button kirim*/
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputData();
            }
        });
    }

    private void getInitialData() {

        bulan = getIntent().getStringExtra("bulan");
        kapal = getIntent().getStringExtra("kapal");
        periode = getIntent().getStringExtra("periode");
        callTanker = getIntent().getStringExtra("call");

        MarineIdentifier identifier = new MarineIdentifier(
                bulan, callTanker, kapal, periode
        );

        SharedPreferences preferences = getSharedPreferences(
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

        Call<PortCharges> call = userClient.getInitPortCharges(identifier);
        call.enqueue(new Callback<PortCharges>() {
            @Override
            public void onResponse(Call<PortCharges> call, Response<PortCharges> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setInitialData(response.body());
                }
            }

            @Override
            public void onFailure(Call<PortCharges> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void getInputData() {

        String nomorBl = getIntent().getStringExtra("nomorBl");
        String namaKapal = getIntent().getStringExtra("namaKapal");
        String berthingDate = getIntent().getStringExtra("berthingDate");

        ArrayList<NewMarineInput> data = new ArrayList<>();
        NewMarineInput lightData, harborData, quayData, pilotageData, towageData, pupData;

        lightData = new NewMarineInput(
                getResources().getString(R.string.variable_port_charges_light),
                getDataIfAvailable(inputRambu),
                nomorBl,
                namaKapal,
                berthingDate
        );
        data.add(lightData);

        harborData = new NewMarineInput(
                getDataIfAvailable(inputLabuh),
                getResources().getString(R.string.variable_port_charges_harbour),
                nomorBl,
                namaKapal,
                berthingDate
        );
        data.add(harborData);

        quayData = new NewMarineInput(
                getDataIfAvailable(inputTambat),
                getResources().getString(R.string.variable_port_charges_quay),
                nomorBl,
                namaKapal,
                berthingDate
        );
        data.add(quayData);

        pilotageData = new NewMarineInput(
                getDataIfAvailable(inputPandu),
                getResources().getString(R.string.variable_port_charges_pilotages),
                nomorBl,
                namaKapal,
                berthingDate
        );
        data.add(pilotageData);

        towageData = new NewMarineInput(
                getDataIfAvailable(inputTunda),
                getResources().getString(R.string.variable_port_charges_towage),
                nomorBl,
                namaKapal,
                berthingDate
        );
        data.add(towageData);

        pupData = new NewMarineInput(
                getDataIfAvailable(inputPup),
                getResources().getString(R.string.variable_port_charges_pup),
                nomorBl,
                namaKapal,
                berthingDate
        );
        data.add(pupData);

        uploadData(data);
    }

    private void uploadData(ArrayList<NewMarineInput> data) {
        String json = new Gson().toJson(data);
        Log.w("json", json);
        sendPostRequest(data);
    }

    private void sendPostRequest(ArrayList<NewMarineInput> data) {
        SharedPreferences preferences = InputPortChargesActivity.this.getSharedPreferences(
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

        Log.w("json", String.valueOf(data.size()));

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.postPortCharges(data);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Log.w("message", response.message());
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private String getDataIfAvailable(EditText inputField) {
        if (!inputField.getText().toString().isEmpty()) {
            return inputField.getText().toString();
        } else {
            return "";
        }
    }

    private void setInitialData(PortCharges portCharges) {
        if (portCharges != null) {
            setEditText(inputRambu, portCharges.getLightDues());
            setEditText(inputLabuh, portCharges.getHarborDues());
            setEditText(inputTambat, portCharges.getQuayDues());
            setEditText(inputPandu, portCharges.getPilotage());
            setEditText(inputTunda, portCharges.getTowage());
            setEditText(inputPup, portCharges.getPup9a2());
        }
    }

    private void setEditText(EditText editText, Object value) {
        if ((Float)value != 0) {
            editText.setText(String.valueOf(value));
        }
    }
}
