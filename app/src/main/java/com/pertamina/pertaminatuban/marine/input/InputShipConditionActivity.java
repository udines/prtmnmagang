package com.pertamina.pertaminatuban.marine.input;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.ShipCondition;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputShipConditionActivity extends AppCompatActivity {

    private EditText inputDescription, inputGradesBunker, inputRobLastPort, inputRobAta, inputRobAtd,
            inputRepl, inputLocationRepl, inputBunkerConsPort, inputBunkerConsSeatime,
            inputSlopTankAta, inputSlopTankAtd;
    private Button ataTime, atdTime, comReplDate, comReplTime, compReplDate, compReplTime, kirim;

    private ShipCondition condition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ship_condition);
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

        inputDescription = findViewById(R.id.input_ship_condition_description_draft);
        inputGradesBunker = findViewById(R.id.input_ship_condition_grades_bunker);
        inputRobLastPort = findViewById(R.id.input_ship_condition_rob_last_port);
        inputRobAta = findViewById(R.id.input_ship_condition_rob_ata);
        inputRobAtd = findViewById(R.id.input_ship_condition_rob_atd);
        inputRepl = findViewById(R.id.input_ship_condition_repl);
        inputLocationRepl = findViewById(R.id.input_ship_condition_location_repl);
        inputBunkerConsPort = findViewById(R.id.input_ship_condition_bunker_consumption_port);
        inputBunkerConsSeatime = findViewById(R.id.input_ship_condition_bunker_consumption_seatime);
        inputSlopTankAta = findViewById(R.id.input_ship_condition_slop_tank_ata);
        inputSlopTankAtd = findViewById(R.id.input_ship_condition_slop_tank_atd);

        ataTime = findViewById(R.id.input_ship_condition_ata_time);
        atdTime = findViewById(R.id.input_ship_condition_atd_time);
        comReplDate = findViewById(R.id.input_ship_condition_com_repl_date);
        comReplTime = findViewById(R.id.input_ship_condition_com_repl_time);
        compReplDate = findViewById(R.id.input_ship_condition_comp_repl_date);
        compReplTime = findViewById(R.id.input_ship_condition_comp_repl_time);
        kirim = findViewById(R.id.input_ship_condition_kirim);

        if (currentDataExist()) {
            getCurrentData();
            setInitialInput();
        }
    }

    private void setInitialInput() {
        setDateAndTimeButton(null, ataTime, condition.getActualTimeArrival());
        setDateAndTimeButton(null, atdTime, condition.getActualTimeDeparture());
        setDateAndTimeButton(compReplDate, compReplTime, condition.getCompRepl());
        setDateAndTimeButton(comReplDate, comReplTime, condition.getComRepl());

        setEditText(inputDescription, condition.getDescriptionDraft());
        setEditText(inputGradesBunker, condition.getGradeBunker());
        setEditText(inputRobAta, condition.getRobAta());
        setEditText(inputRobAtd, condition.getRobAtd());
        setEditText(inputRobLastPort, condition.getRobLastPort());
        setEditText(inputRepl, condition.getRepl());
        setEditText(inputLocationRepl, condition.getReplLocation());
        setEditText(inputBunkerConsPort, condition.getBunkerConsumptionPort());
        setEditText(inputBunkerConsSeatime, condition.getBunkerConsumptionSeatime());
        setEditText(inputSlopTankAta, condition.getSlopTankAta());
        setEditText(inputSlopTankAtd, condition.getSlopTankAtd());
    }

    private void setEditText(EditText editText, Object content) {
        if (content != null) {
            if (content instanceof Float && (Float)content == 0) {
                Log.w("content", String.valueOf(content));
            } else {
                editText.setText(String.valueOf(content));
            }
        }
    }

    private void getCurrentData() {
        Calendar cal = Calendar.getInstance();
        Timestamp time = new Timestamp(cal.getTimeInMillis());
        condition = new ShipCondition(
                "Description draft",
                time,
                time,
                null,
                123,
                456,
                789,
                0,
                null,
                time,
                "Repl Location",
                112,
                131,
                415,
                167
        );
    }

    private boolean currentDataExist() {
        return true;
    }

    private void setDateAndTimeButton(Button dateButton, Button timeButton, Timestamp time) {
        if (time != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time.getTime());

            if (dateButton != null) {
                DateFormatSymbols symbols = new DateFormatSymbols();
                String text = symbols.getMonths()[cal.get(Calendar.MONTH)] + " " + String.valueOf(cal.get(Calendar.YEAR));
                dateButton.setText(text);
            }

            if (timeButton != null) {
                Date date = new Date(cal.getTimeInMillis());
                SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeButton.setText(format.format(date));
            }

        }
    }
}
