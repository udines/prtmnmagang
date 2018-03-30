package com.pertamina.pertaminatuban.saranafasilitas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.WilayahActivity;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SaranaFasilitasActivity extends AppCompatActivity {

    private TextView tanggal;
    private Button tambahButton;
    private int year, month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sarana_fasilitas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tanggal = findViewById(R.id.sarana_bulan_button);
        tambahButton = findViewById(R.id.sarana_tambah_button);

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        setTanggalButton(month, year);
        handleTanggalButton();
        handleTambahButton();
    }

    private void setTanggalButton(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        tanggal.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void handleTanggalButton() {
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        SaranaFasilitasActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setTanggalButton(month, year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .setTitle("Pilih bulan dan tahun")
                        .setActivatedMonth(month)
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void handleTambahButton() {
        tambahButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputIntent = new Intent(getApplicationContext(), InputSaranaFasilitasActivity.class);
                inputIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inputIntent);
            }
        });
    }

}