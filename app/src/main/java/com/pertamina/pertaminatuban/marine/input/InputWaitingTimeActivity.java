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
import com.pertamina.pertaminatuban.marine.models.WaitingTime;
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

public class InputWaitingTimeActivity extends AppCompatActivity {

    private EditText pilotHour, pilotMins, labHour, labMins, tugHour, tugMins, jettyHour, jettyMins,
            dayHour, dayMins, tideHour, tideMins, ballastHour, ballastMins, cleanHour, cleanMins,
            nomiHour, nomiMins, powerHour, powerMins, weatherHour, weatherMins, lineHour, lineMins,
            cargoHour, cargoMins, ullageHour, ullageMins, supplyBunkHour, supplyBunkMins,
            supplyFreshHour, supplyFreshMins, actLoadHour, actLoadMins, prepHour, prepMins,
            shoreOrderHour, shoreOrderMins, clearHour, clearMins, cargoDocHour, cargoDocMins,
            pumpVessHour, pumpVessMins, pumpShoreHour, pumpShoreMins, cargoCalcHour, cargoCalcMins,
            steamHour, steamMins, unreadyHour, unreadyMins;
    private String bulan, kapal, callTanker, periode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_waiting_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
    }

    private void getData() {

        Button kirim;

        pilotHour = findViewById(R.id.input_waiting_time_pilot_hour);
        pilotMins = findViewById(R.id.input_waiting_time_pilot_mins);
        labHour = findViewById(R.id.input_waiting_time_lab_hour);
        labMins = findViewById(R.id.input_waiting_time_lab_mins);
        tugHour = findViewById(R.id.input_waiting_time_tug_hour);
        tugMins = findViewById(R.id.input_waiting_time_tug_mins);
        jettyHour = findViewById(R.id.input_waiting_time_jetty_hour);
        jettyMins = findViewById(R.id.input_waiting_time_jetty_mins);
        dayHour = findViewById(R.id.input_waiting_time_daylight_hour);
        dayMins = findViewById(R.id.input_waiting_time_daylight_mins);
        tideHour = findViewById(R.id.input_waiting_time_tide_hour);
        tideMins = findViewById(R.id.input_waiting_time_tide_mins);
        ballastHour = findViewById(R.id.input_waiting_time_ballast_hour);
        ballastMins = findViewById(R.id.input_waiting_time_ballast_mins);
        cleanHour = findViewById(R.id.input_waiting_time_cleaning_hour);
        cleanMins = findViewById(R.id.input_waiting_time_cleaning_mins);
        nomiHour = findViewById(R.id.input_waiting_time_nomination_hour);
        nomiMins = findViewById(R.id.input_waiting_time_nomination_mins);
        powerHour = findViewById(R.id.input_waiting_time_man_pow_hour);
        powerMins = findViewById(R.id.input_waiting_time_man_pow_mins);
        weatherHour = findViewById(R.id.input_waiting_time_bad_weather_hour);
        weatherMins = findViewById(R.id.input_waiting_time_bad_weather_mins);
        lineHour = findViewById(R.id.input_waiting_time_line_hour);
        lineMins = findViewById(R.id.input_waiting_time_line_mins);
        cargoHour = findViewById(R.id.input_waiting_time_cargo_hour);
        cargoMins = findViewById(R.id.input_waiting_time_cargo_mins);
        ullageHour = findViewById(R.id.input_waiting_time_ullage_hour);
        ullageMins = findViewById(R.id.input_waiting_time_ullage_mins);
        supplyBunkHour = findViewById(R.id.input_waiting_time_supply_bunker_hour);
        supplyBunkMins = findViewById(R.id.input_waiting_time_supply_bunker_mins);
        supplyFreshHour = findViewById(R.id.input_waiting_time_fresh_water_hour);
        supplyFreshMins = findViewById(R.id.input_waiting_time_fresh_water_mins);
        actLoadHour = findViewById(R.id.input_waiting_time_load_hour);
        actLoadMins = findViewById(R.id.input_waiting_time_load_mins);
        prepHour = findViewById(R.id.input_waiting_time_prep_hour);
        prepMins = findViewById(R.id.input_waiting_time_prep_mins);
        shoreOrderHour = findViewById(R.id.input_waiting_time_shore_order_hour);
        shoreOrderMins = findViewById(R.id.input_waiting_time_shore_order_mins);
        clearHour = findViewById(R.id.input_waiting_time_clearence_hour);
        clearMins = findViewById(R.id.input_waiting_time_clearence_mins);
        cargoDocHour = findViewById(R.id.input_waiting_time_cargo_doc_hour);
        cargoDocMins = findViewById(R.id.input_waiting_time_cargo_doc_mins);
        pumpVessHour = findViewById(R.id.input_waiting_time_slow_vessel_hour);
        pumpVessMins = findViewById(R.id.input_waiting_time_slow_vessel_mins);
        pumpShoreHour = findViewById(R.id.input_waiting_time_slow_shore_hour);
        pumpShoreMins = findViewById(R.id.input_waiting_time_slow_shore_mins);
        cargoCalcHour = findViewById(R.id.input_waiting_time_cargo_calc_hour);
        cargoCalcMins = findViewById(R.id.input_waiting_time_cargo_calc_mins);
        steamHour = findViewById(R.id.input_waiting_time_steaming_hour);
        steamMins = findViewById(R.id.input_waiting_time_steaming_mins);
        unreadyHour = findViewById(R.id.input_waiting_time_unready_hour);
        unreadyMins = findViewById(R.id.input_waiting_time_unready_mins);
        kirim = findViewById(R.id.input_waiting_time_kirim);

        getInitialData();

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

        Call<WaitingTime> call = userClient.getInitWaitingTime(identifier);
        call.enqueue(new Callback<WaitingTime>() {
            @Override
            public void onResponse(Call<WaitingTime> call, Response<WaitingTime> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setInitialData(response.body());
                }
            }

            @Override
            public void onFailure(Call<WaitingTime> call, Throwable t) {
                Log.w("error", t.getMessage());
            }
        });
    }

    private void getInputData() {

        ArrayList<MarineInput> data = new ArrayList<>();

        data.add(new MarineInput(
                getDurasi(pilotHour, pilotMins),
                getResources().getString(R.string.variable_waiting_time_pilot),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(labHour, labMins),
                getResources().getString(R.string.variable_waiting_time_lab_analysis),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(tugHour, tugMins),
                getResources().getString(R.string.variable_waiting_time_tug_boat),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(jettyHour, jettyMins),
                getResources().getString(R.string.variable_waiting_time_jetty),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(dayHour, dayMins),
                getResources().getString(R.string.variable_waiting_time_daylight),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(tideHour, tideMins),
                getResources().getString(R.string.variable_waiting_time_tide),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(ballastHour, ballastMins),
                getResources().getString(R.string.variable_waiting_time_ballast),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(cleanHour, cleanMins),
                getResources().getString(R.string.variable_waiting_time_tank_cleaning),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(nomiHour, nomiMins),
                getResources().getString(R.string.variable_waiting_time_nomination),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(powerHour, powerMins),
                getResources().getString(R.string.variable_waiting_time_man_power),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(weatherHour, weatherMins),
                getResources().getString(R.string.variable_waiting_time_bad_weather),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(lineHour, lineMins),
                getResources().getString(R.string.variable_waiting_time_line),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(cargoHour, cargoMins),
                getResources().getString(R.string.variable_waiting_time_cargo),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(ullageHour, ullageMins),
                getResources().getString(R.string.variable_waiting_time_ullage),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(supplyBunkHour, supplyBunkMins),
                getResources().getString(R.string.variable_waiting_time_supply_bunker),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(supplyFreshHour, supplyFreshMins),
                getResources().getString(R.string.variable_waiting_time_supply_fresh_water),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(actLoadHour, actLoadMins),
                getResources().getString(R.string.variable_waiting_time_act_load),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(prepHour, prepMins),
                getResources().getString(R.string.variable_waiting_time_preparation),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(shoreOrderHour, shoreOrderMins),
                getResources().getString(R.string.variable_waiting_time_shore_order),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(clearHour, clearMins),
                getResources().getString(R.string.variable_waiting_time_ship_clearence),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(cargoDocHour, cargoDocMins),
                getResources().getString(R.string.variable_waiting_time_cargo_doc),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(pumpVessHour, pumpVessMins),
                getResources().getString(R.string.variable_waiting_time_slow_vessel),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(pumpShoreHour, pumpShoreMins),
                getResources().getString(R.string.variable_waiting_time_slow_shore),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(cargoCalcHour, cargoCalcMins),
                getResources().getString(R.string.variable_waiting_time_cargo_calculation),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(steamHour, steamMins),
                getResources().getString(R.string.variable_waiting_time_steaming),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDurasi(unreadyHour, unreadyMins),
                getResources().getString(R.string.variable_waiting_time_unready),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        uploadData(data);
    }

    private void uploadData(ArrayList<MarineInput> data) {
        Log.w("json", new Gson().toJson(data));
    }

    private String getDurasi(EditText hour, EditText min) {
        if (!hour.getText().toString().isEmpty() && !min.getText().toString().isEmpty()) {
            if (min.getText().length() == 1) {
                return String.valueOf(hour.getText().toString() + ":0" + min.getText().toString());
            } else {
                return String.valueOf(hour.getText().toString() + ":" + min.getText().toString());
            }
        } else if (!hour.getText().toString().isEmpty() && min.getText().toString().isEmpty()) {
            return String.valueOf(hour.getText().toString() + ":00");
        } else if (hour.getText().toString().isEmpty() && !min.getText().toString().isEmpty()) {
            if (min.getText().length() == 1) {
                return String.valueOf("00:0" + min.getText().toString());
            } else {
                return String.valueOf("00:" + min.getText().toString());
            }
        } else {
            return "";
        }
    }

    private void setInitialData(WaitingTime waitingTime) {
        if (waitingTime != null) {
            setEditText(pilotHour, pilotMins, waitingTime.getPilot());
            setEditText(labHour, labMins, waitingTime.getLabAnalysis());
            setEditText(tugHour, tugMins, waitingTime.getTugBoat());
            setEditText(jettyHour, jettyMins, waitingTime.getJetty());
            setEditText(dayHour, dayMins, waitingTime.getDaylight());
            setEditText(tideHour, tideMins, waitingTime.getTide());
            setEditText(ballastHour, ballastMins, waitingTime.getBallast());
            setEditText(cleanHour, cleanMins, waitingTime.getTankCleaning());
            setEditText(nomiHour, nomiMins, waitingTime.getNomination());
            setEditText(powerHour, powerMins, waitingTime.getManPower());
            setEditText(weatherHour, weatherMins, waitingTime.getBadWeater());
            setEditText(lineHour, lineMins, waitingTime.getLine());
            setEditText(cargoHour, cargoMins, waitingTime.getCargo());
            setEditText(ullageHour, ullageMins, waitingTime.getUllage());
            setEditText(supplyBunkHour, supplyBunkMins, waitingTime.getSupplyBunker());
            setEditText(supplyFreshHour, supplyFreshMins, waitingTime.getSupplyFreshWater());
            setEditText(actLoadHour, actLoadMins, waitingTime.getActLoadDate());
            setEditText(prepHour, prepMins, waitingTime.getPreparation());
            setEditText(shoreOrderHour, shoreOrderMins, waitingTime.getShoreOrder());
            setEditText(clearHour, clearMins, waitingTime.getShipClearence());
            setEditText(cargoDocHour, cargoDocMins, waitingTime.getCargoDocument());
            setEditText(pumpVessHour, pumpVessMins, waitingTime.getSlowPumpVessel());
            setEditText(pumpShoreHour, pumpShoreMins, waitingTime.getSlowPumpShore());
            setEditText(cargoCalcHour, cargoCalcMins, waitingTime.getCargoCalculation());
            setEditText(steamHour, steamMins, waitingTime.getSteamingInOut());
            setEditText(unreadyHour, unreadyMins, waitingTime.getShipUnready());
        }
    }

    private void setEditText(EditText inputHour, EditText inputMinute, int minutes) {
        if (minutes > 0) {
            int hour = minutes / 60;
            int min = minutes % 60;
            inputHour.setText(String.valueOf(hour));
            inputMinute.setText(String.valueOf(min));
        }
    }

}
