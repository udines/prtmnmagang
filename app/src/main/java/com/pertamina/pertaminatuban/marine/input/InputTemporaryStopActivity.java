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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputTemporaryStopActivity extends AppCompatActivity {

    private EditText inputReason1, inputReason2, inputReason3, inputReason4, inputReason5;
    private Button kirim, inputStopDate1, inputStopTime1, inputResumeDate1, inputResumeTime1,
            inputStopDate2, inputStopTime2, inputResumeDate2, inputResumeTime2,
            inputStopDate3, inputStopTime3, inputResumeDate3, inputResumeTime3,
            inputStopDate4, inputStopTime4, inputResumeDate4, inputResumeTime4,
            inputStopDate5, inputStopTime5, inputResumeDate5, inputResumeTime5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_temporary_stop);
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

        inputReason1 = findViewById(R.id.input_temporary_stop_reason1);
        inputStopDate1 = findViewById(R.id.input_temporary_stop_date_stop1);
        inputStopTime1 = findViewById(R.id.input_temporary_stop_time_stop1);
        inputResumeDate1 = findViewById(R.id.input_temporary_stop_date_resume1);
        inputResumeTime1 = findViewById(R.id.input_temporary_stop_time_resume1);

        inputReason2 = findViewById(R.id.input_temporary_stop_reason2);
        inputStopDate2 = findViewById(R.id.input_temporary_stop_date_stop2);
        inputStopTime2 = findViewById(R.id.input_temporary_stop_time_stop2);
        inputResumeDate2 = findViewById(R.id.input_temporary_stop_date_resume2);
        inputResumeTime2 = findViewById(R.id.input_temporary_stop_time_resume2);

        inputReason3 = findViewById(R.id.input_temporary_stop_reason3);
        inputStopDate3 = findViewById(R.id.input_temporary_stop_date_stop3);
        inputStopTime3 = findViewById(R.id.input_temporary_stop_time_stop3);
        inputResumeDate3 = findViewById(R.id.input_temporary_stop_date_resume3);
        inputResumeTime3 = findViewById(R.id.input_temporary_stop_time_resume3);

        inputReason4 = findViewById(R.id.input_temporary_stop_reason4);
        inputStopDate4 = findViewById(R.id.input_temporary_stop_date_stop4);
        inputStopTime4 = findViewById(R.id.input_temporary_stop_time_stop4);
        inputResumeDate4 = findViewById(R.id.input_temporary_stop_date_resume4);
        inputResumeTime4 = findViewById(R.id.input_temporary_stop_time_resume4);

        inputReason5 = findViewById(R.id.input_temporary_stop_reason5);
        inputStopDate5 = findViewById(R.id.input_temporary_stop_date_stop5);
        inputStopTime5 = findViewById(R.id.input_temporary_stop_time_stop5);
        inputResumeDate5 = findViewById(R.id.input_temporary_stop_date_resume5);
        inputResumeTime5 = findViewById(R.id.input_temporary_stop_time_resume5);
        kirim = findViewById(R.id.input_temporary_stop_kirim);

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
                InputTemporaryStopActivity.this,
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
                InputTemporaryStopActivity.this,
                listener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private void assignButtonListener() {
        addButtonListener(inputStopDate1, inputStopTime1);
        addButtonListener(inputResumeDate1, inputResumeTime1);
        addButtonListener(inputStopDate2, inputStopTime2);
        addButtonListener(inputResumeDate2, inputResumeTime2);
        addButtonListener(inputStopDate3, inputStopTime3);
        addButtonListener(inputResumeDate3, inputResumeTime3);
        addButtonListener(inputStopDate4, inputStopTime4);
        addButtonListener(inputResumeDate4, inputResumeTime4);
        addButtonListener(inputStopDate5, inputStopTime5);
        addButtonListener(inputResumeDate5, inputResumeTime5);
    }

    private String getDataIfAvailable(EditText inputField) {
        if (!inputField.getText().toString().isEmpty()) {
            return inputField.getText().toString();
        } else {
            return "";
        }
    }

    private void getInputData() {
        Calendar cal = Calendar.getInstance();

        /*int year, month;
        year = getIntent().getIntExtra("yearPeriod", 2018);
        month = getIntent().getIntExtra("monthPeriod", 0);
        cal.set(year, month, 1);*/

        Date date = new Date(cal.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd", Locale.getDefault());
        String bulan = format.format(date);

        /*String kapal = getIntent().getStringExtra("vesselName");
        String periode = getIntent().getStringExtra("periode");
        String callTanker = getIntent().getStringExtra("callTanker");
        kapal = kapal.toUpperCase();*/

        String kapal = "John Caine";
        kapal = kapal.toUpperCase();
        String periode = "A";
        String callTanker = "1";

        ArrayList<MarineInput> data = new ArrayList<>();

        data.add(new MarineInput(
                getDataIfAvailable(inputReason1),
                getResources().getString(R.string.variable_temp_stop_reason_1),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputStopDate1, inputStopTime1),
                getResources().getString(R.string.variable_temp_stop_start_1),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputResumeDate1, inputResumeTime1),
                getResources().getString(R.string.variable_temp_stop_resume_1),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputReason2),
                getResources().getString(R.string.variable_temp_stop_reason_2),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputStopDate2, inputStopTime2),
                getResources().getString(R.string.variable_temp_stop_start_2),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputResumeDate2, inputResumeTime2),
                getResources().getString(R.string.variable_temp_stop_resume_2),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputReason3),
                getResources().getString(R.string.variable_temp_stop_reason_3),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputStopDate3, inputStopTime3),
                getResources().getString(R.string.variable_temp_stop_start_3),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputResumeDate3, inputResumeTime3),
                getResources().getString(R.string.variable_temp_stop_resume_3),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputReason4),
                getResources().getString(R.string.variable_temp_stop_reason_4),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputStopDate4, inputStopTime4),
                getResources().getString(R.string.variable_temp_stop_start_4),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputResumeDate4, inputResumeTime4),
                getResources().getString(R.string.variable_temp_stop_resume_4),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputReason5),
                getResources().getString(R.string.variable_temp_stop_reason_5),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputStopDate5, inputStopTime5),
                getResources().getString(R.string.variable_temp_stop_start_5),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getTimeToString(inputResumeDate5, inputResumeTime5),
                getResources().getString(R.string.variable_temp_stop_resume_5),
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

    }

    private void getCurrentData() {

    }

    private boolean currentDataExist() {
        return false;
    }

}
