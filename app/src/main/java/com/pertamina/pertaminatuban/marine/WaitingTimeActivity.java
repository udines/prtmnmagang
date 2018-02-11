package com.pertamina.pertaminatuban.marine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.input.InputWaitingTimeActivity;
import com.pertamina.pertaminatuban.marine.input.PilihTankerActivity;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class WaitingTimeActivity extends AppCompatActivity {

    private int year, month, day;
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateText = findViewById(R.id.waiting_time_text_date);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        setDateButton(month, year);
        populateData(month, year);

        handleDateButton();
        handleInputButton();
    }

    private void setDateButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        dateText.setText(text);
    }

    private void handleDateButton() {
        LinearLayout dateButton = findViewById(R.id.waiting_time_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        WaitingTimeActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(month, year);

                                //get data sesuai dengan bulan dan tahun
                                populateData(month, year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .setActivatedMonth(month)
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void populateData(int month, int year) {
        Toast.makeText(this, String.valueOf(month + 1 + " " + year), Toast.LENGTH_SHORT).show();
    }

    private void handleInputButton() {
        Button button = findViewById(R.id.waiting_time_input);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputIntent = new Intent(getApplicationContext(), PilihTankerActivity.class);
                inputIntent.putExtra("sourceActivity", "WaitingTime");
                inputIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inputIntent);
            }
        });
    }

}
