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
import com.pertamina.pertaminatuban.marine.models.MarineInput;
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
    private PortCharges portCharges;

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

        if (currentDataExist()) {
            getCurrentData();
            initInputData();
        }

        /*handle pengguna klik button kirim*/
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputData();
            }
        });
    }

    private void getInputData() {
        Calendar cal = Calendar.getInstance();

        /*int year, month;
        year = getIntent().getIntExtra("yearPeriod", 2018);
        month = getIntent().getIntExtra("monthPeriod", 0);
        cal.set(year, month, 1);*/

        Date date = new Date(cal.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd");
        String bulan = format.format(date);

        /*String kapal = getIntent().getStringExtra("vesselName");
        String periode = getIntent().getStringExtra("periode");
        String callTanker = getIntent().getStringExtra("callTanker");*/

        String kapal = "John Caine";
        String periode = "A";
        String callTanker = "1";

        ArrayList<MarineInput> data = new ArrayList<>();
        MarineInput lightData, harborData, quayData, pilotageData, towageData, pupData;

        lightData = new MarineInput(
                getDataIfAvailable(inputRambu),
                getResources().getString(R.string.variable_port_charges_light),
                kapal,
                periode,
                bulan,
                callTanker
        );
        data.add(lightData);

        harborData = new MarineInput(
                getDataIfAvailable(inputLabuh),
                getResources().getString(R.string.variable_port_charges_harbour),
                kapal,
                periode,
                bulan,
                callTanker
        );
        data.add(harborData);

        quayData = new MarineInput(
                getDataIfAvailable(inputTambat),
                getResources().getString(R.string.variable_port_charges_quay),
                kapal,
                periode,
                bulan,
                callTanker
        );
        data.add(quayData);

        pilotageData = new MarineInput(
                getDataIfAvailable(inputPandu),
                getResources().getString(R.string.variable_port_charges_pilotages),
                kapal,
                periode,
                bulan,
                callTanker
        );
        data.add(pilotageData);

        towageData = new MarineInput(
                getDataIfAvailable(inputTunda),
                getResources().getString(R.string.variable_port_charges_towage),
                kapal,
                periode,
                bulan,
                callTanker
        );
        data.add(towageData);

        pupData = new MarineInput(
                getDataIfAvailable(inputPup),
                getResources().getString(R.string.variable_port_charges_pup),
                kapal,
                periode,
                bulan,
                callTanker
        );
        data.add(pupData);

        uploadData(data);
    }

    private void uploadData(ArrayList<MarineInput> data) {
        String json = new Gson().toJson(data);
        Log.w("json", json);
        sendPostRequest(data);
    }

    private void sendPostRequest(ArrayList<MarineInput> data) {
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

    private void getCurrentData() {

    }

    private void initInputData() {
        setEditText(inputRambu, portCharges.getLightDues());
        setEditText(inputLabuh, portCharges.getHarborDues());
        setEditText(inputTambat, portCharges.getQuayDues());
        setEditText(inputPandu, portCharges.getPilotage());
        setEditText(inputTunda, portCharges.getTowage());
        setEditText(inputPup, portCharges.getPup9a2());
    }

    private boolean currentDataExist() {
        return true;
    }

    private void setEditText(EditText editText, Object value) {
        if ((Float)value != 0) {
            editText.setText(String.valueOf(value));
        }
    }
}
