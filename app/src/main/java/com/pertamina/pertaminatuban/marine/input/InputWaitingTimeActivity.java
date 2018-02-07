package com.pertamina.pertaminatuban.marine.input;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.WaitingTime;

public class InputWaitingTimeActivity extends AppCompatActivity {

    private EditText pilotHour, pilotMins, labHour, labMins, tugHour, tugMins, jettyHour, jettyMins,
            dayHour, dayMins, tideHour, tideMins, ballastHour, ballastMins, cleanHour, cleanMins,
            nomiHour, nomiMins, powerHour, powerMins, weatherHour, weatherMins, lineHour, lineMins,
            cargoHour, cargoMins, ullageHour, ullageMins, supplyBunkHour, supplyBunkMins,
            supplyFreshHour, supplyFreshMins, actLoadHour, actLoadMins, prepHour, prepMins,
            shoreOrderHour, shoreOrderMins, clearHour, clearMins, cargoDocHour, cargoDocMins,
            pumpVessHour, pumpVessMins, pumpShoreHour, pumpShoreMins, cargoCalcHour, cargoCalcMins,
            steamHour, steamMins, unreadyHour, unreadyMins;
    private WaitingTime waitingTime;

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

        if (currentDataExist()) {
            getCurrentData();
            setInitialInput();
        }
    }

    private void setInitialInput() {
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

    private void setEditText(EditText inputHour, EditText inputMinute, int minutes) {
        if (minutes > 0) {
            int hour = minutes / 60;
            int min = minutes % 60;
            inputHour.setText(String.valueOf(hour));
            inputMinute.setText(String.valueOf(min));
        }
    }

    private void getCurrentData() {
        waitingTime = new WaitingTime(
                34,
                123,
                567,
                123,
                435,
                0,
                134,
                98,
                8,
                9,
                0,
                32,
                457,
                927,
                1000,
                34,
                498,
                2183,
                45,
                128,
                485,
                34,
                23,
                2139,
                3294,
                2
        );
    }

    private boolean currentDataExist() {
        return true;
    }

}
