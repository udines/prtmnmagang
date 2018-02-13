package com.pertamina.pertaminatuban.marine.input;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.MarineInput;
import com.pertamina.pertaminatuban.marine.models.ShipCondition;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

        assignButtonListener();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputData();
            }
        });
    }

    private void assignButtonListener() {
        addButtonListener(null, ataTime);
        addButtonListener(null, atdTime);
        addButtonListener(compReplDate, compReplTime);
        addButtonListener(comReplDate, comReplTime);
    }

    private void addButtonListener(final Button dateButton, final Button timeButton) {
        if (dateButton != null) {
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.w("date text", dateButton.getText().toString());
                    showDatePicker(dateButton);
                }
            });
        }
        if (timeButton != null) {
            timeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.w("time text", timeButton.getText().toString());
                    showTimePicker(timeButton);
                }
            });
        }
    }

    private void getInputData() {
        String bulan = getIntent().getStringExtra("bulan");

        String kapal = getIntent().getStringExtra("kapal");
        String periode = getIntent().getStringExtra("periode");
        String callTanker = getIntent().getStringExtra("call");

        ArrayList<MarineInput> data = new ArrayList<>();

        data.add(new MarineInput(
                getDataIfAvailable(inputDescription),
                getResources().getString(R.string.variable_ship_condition_description),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeOnlyToString(ataTime),
                getResources().getString(R.string.variable_ship_condition_draft_ata),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeOnlyToString(atdTime),
                getResources().getString(R.string.variable_ship_condition_draft_atd),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputGradesBunker),
                getResources().getString(R.string.variable_ship_condition_grade_bunker),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputRobLastPort),
                getResources().getString(R.string.variable_ship_condition_rob_last_port),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputRobAta),
                getResources().getString(R.string.variable_ship_condition_rob_ata),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputRobAtd),
                getResources().getString(R.string.variable_ship_condition_rob_atd),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputRepl),
                getResources().getString(R.string.variable_ship_condition_repl),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(comReplDate, comReplTime),
                getResources().getString(R.string.variable_ship_condition_com_repl),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(compReplDate, compReplTime),
                getResources().getString(R.string.variable_ship_condition_comp_repl),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputLocationRepl),
                getResources().getString(R.string.variable_ship_condition_location),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputBunkerConsPort),
                getResources().getString(R.string.variable_ship_condition_bunker_consumption_port),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputBunkerConsSeatime),
                getResources().getString(R.string.variable_ship_condition_bunker_consumption_seatime),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputSlopTankAta),
                getResources().getString(R.string.variable_ship_condition_slop_tank_ata),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputSlopTankAtd),
                getResources().getString(R.string.variable_ship_condition_slop_tank_atd),
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

    private String getDataIfAvailable(EditText inputField) {
        if (!inputField.getText().toString().isEmpty()) {
            return inputField.getText().toString();
        } else {
            return "";
        }
    }

    private String getTimeToString(Button dateButton, Button timeButton) {
        String timestamp = "";
        if (!dateButton.getText().equals("date")) {
            timestamp = timestamp + dateButton.getText().toString() + " ";
        }
        if (!timeButton.getText().equals("time")) {
            timestamp = timestamp + timeButton.getText().toString();
        }
        return timestamp;
    }

    private String getTimeOnlyToString(Button timeButton) {
        String timestamp = "";
        if (!timeButton.getText().equals("time")) {
            timestamp = timestamp + timeButton.getText().toString();
        }
        return timestamp;
    }

    private void getCurrentData() {

    }

    private boolean currentDataExist() {
        return false;
    }

    private void setDateAndTimeButton(final Button dateButton, final Button timeButton, String time) {
        if (time != null) {
            String[] hourMin = time.split(" ");
            if (hourMin[0] != null) {
                dateButton.setText(hourMin[0]);
            }
            if (hourMin[1] != null) {
                timeButton.setText(hourMin[1]);
            }
        }
    }

    private void showTimePicker(final Button timeButton) {
        final Calendar cal = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                cal.set(
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.HOUR_OF_DAY),
                        i,
                        i1
                );
                SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeButton.setText(format.format(new Date(cal.getTimeInMillis())));
            }
        };
        TimePickerDialog dialog = new TimePickerDialog(
                InputShipConditionActivity.this,
                listener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
        );
        dialog.show();
    }

    private void showDatePicker(final Button dateButton) {
        final Calendar cal = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                cal.set(i, i1, i2);
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                dateButton.setText(format.format(new Date(cal.getTimeInMillis())));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                InputShipConditionActivity.this,
                listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }
}
