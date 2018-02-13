package com.pertamina.pertaminatuban.marine.input;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.pertamina.pertaminatuban.R;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PilihTankerActivity extends AppCompatActivity {

    private Button buttonBulan, buttonLanjut;
    private EditText inputKapal;
    private Spinner kapalSpinner, periodeSpinner;

    private int month, year;
    private String bulan, kapal, callTanker, periode;

    private String sourceActivity;
    private boolean bulanExist = false;
    private EditText inputCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_tanker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buttonBulan = findViewById(R.id.pilih_tanker_bulan);
        buttonLanjut = findViewById(R.id.pilih_tanker_lanjut);
        inputKapal = findViewById(R.id.pilih_tanker_input_kapal_baru);
        kapalSpinner = findViewById(R.id.pilih_tanker_kapal);
        periodeSpinner = findViewById(R.id.pilih_tanker_periode);
        inputCall = findViewById(R.id.pilih_tanker_input_call);

        Calendar cal = Calendar.getInstance();
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        sourceActivity = getIntent().getStringExtra("sourceActivity");

        handleBulan();
        handlePeriode();
        handleLanjut();

    }

    private void handlePeriode() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.periode_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        periodeSpinner.setAdapter(adapter);
        periodeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                periode = periodeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleLanjut() {
        buttonLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                periode = periodeSpinner.getSelectedItem().toString();
                if (bulanExist && callTankerExist() && kapalExist() && !periode.equals("Periode")) {
                    goToActivity(sourceActivity);
                }
                Log.w("bool", String.valueOf(bulanExist + " " + callTankerExist() + " " + kapalExist() + " " + periode));
            }
        });
    }

    private boolean kapalExist() {
        boolean ada = false;
        if (!inputKapal.getText().toString().isEmpty()) {
            ada = true;
            kapal = inputKapal.getText().toString();
        }
        return ada;
    }

    private boolean callTankerExist() {
        boolean ada = false;
        if (!inputCall.getText().toString().isEmpty()) {
            ada = true;
            callTanker = inputCall.getText().toString();
        }
        return ada;
    }

    private void goToActivity(String sourceActivity) {
        Intent inputIntent = new Intent();
        switch (sourceActivity) {
            case "InitialTanker":
                inputIntent = new Intent(getApplicationContext(), InputInitialTankerActivity.class);
                break;
            case "PortCharges":
                inputIntent = new Intent(getApplicationContext(), InputPortChargesActivity.class);
                break;
            case "ShipCondition":
                inputIntent = new Intent(getApplicationContext(), InputShipConditionActivity.class);
                break;
            case "ShipParticular":
                inputIntent = new Intent(getApplicationContext(), InputShipParticularActivity.class);
                break;
            case "TankerMovement":
                inputIntent = new Intent(getApplicationContext(), InputTankerMovementActivity.class);
                break;
            case "TemporaryStop":
                inputIntent = new Intent(getApplicationContext(), InputTemporaryStopActivity.class);
                break;
            case "WaitingTime":
                inputIntent = new Intent(getApplicationContext(), InputWaitingTimeActivity.class);
                break;
        }
        inputIntent.putExtra("bulan", bulan);
        inputIntent.putExtra("kapal", kapal);
        inputIntent.putExtra("call", callTanker);
        inputIntent.putExtra("periode", periode);
        startActivity(inputIntent);
    }

    private void handleBulan() {
        buttonBulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        PilihTankerActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(month, year);
                                Calendar cal = Calendar.getInstance();
                                cal.set(year, month, 1);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
                                bulan = format.format(new Date(cal.getTimeInMillis()));
                                bulanExist = true;
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setActivatedMonth(month)
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void setDateButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        buttonBulan.setText(text);
    }

}
