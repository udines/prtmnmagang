package com.pertamina.pertaminatuban.marine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.input.InputInitialTankerActivity;
import com.pertamina.pertaminatuban.marine.models.InitialTanker;
import com.pertamina.pertaminatuban.marine.models.MarineTable;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

public class InitialTankerActivity extends AppCompatActivity {

    private int year, month, day;
    private TextView dateText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_tanker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateText = findViewById(R.id.initial_tanker_text_date);
        recyclerView = findViewById(R.id.initial_tanker_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        setDateButton(month, year);
        getData(month, year);

        handleDateButton();
        handleInputButton();
    }

    private void setDateButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        dateText.setText(text);
    }

    private void handleDateButton() {
        LinearLayout dateButton = findViewById(R.id.initial_tanker_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        InitialTankerActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(month, year);

                                //get data sesuai dengan bulan dan tahun
                                getData(month, year);
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

    private void getData(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(2018, 1, 1);
        ArrayList<InitialTanker> tankers = new ArrayList<>();
        tankers.add(new InitialTanker(
                "idAbc123",
                1,
                new Date(cal.getTimeInMillis()),
                "15/D1/P.3010/VII/2017",
                "12",
                new Date(cal.getTimeInMillis()),
                "Own Tanker",
                "Own Tanker",
                "",
                "PTM",
                "PTM",
                "Domestic",
                null,
                "Simultan",
                getResources().getString(R.string.radio_spm_35),
                "Discharge Port",
                "Discharge Port",
                "Loading Port",
                "Discharge Port"
        ));

        cal.set(2018, 1, 1);
        tankers.add(new InitialTanker(
                "idAbc124",
                2,
                new Date(cal.getTimeInMillis()),
                "15/D1/P.3010/VII/2017",
                "11",
                new Date(cal.getTimeInMillis()),
                "Charter Tanker",
                "Charger Tanker",
                "Solar",
                "PTM",
                "PTM",
                "Domestic",
                "Loading",
                "Grade by Grade",
                getResources().getString(R.string.radio_spm_35),
                "Discharge Port",
                "Discharge Port",
                "Loading Port",
                "Loading Port"
        ));

        checkData(tankers);
    }

    private void checkData(ArrayList<InitialTanker> tankers) {
        if (tankers != null && tankers.size() > 0) {
            populateData(tankers);
        } else {
            Log.w("list size", "0");
        }
    }

    private void populateData(ArrayList<InitialTanker> tankers) {
        ArrayList<MarineTable> vessels = new ArrayList<>();
        for (int i = 0; i < tankers.size(); i++) {
            MarineTable table = new MarineTable();
            table.setVariable(String.valueOf(tankers.get(i).getCall()));
            table.setValue(String.valueOf("Vessel ke " + i));
        }

        ArrayList<ArrayList<MarineTable>> tables = new ArrayList<>();
        for (int i = 0; i < tankers.size(); i++) {
            ArrayList<MarineTable> rows = new ArrayList<>();
            InitialTanker tanker = tankers.get(i);

        }
    }

    private void handleInputButton() {
        Button button = findViewById(R.id.initial_tanker_input);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputIntent = new Intent(getApplicationContext(), InputInitialTankerActivity.class);
                inputIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inputIntent);
            }
        });
    }

}
