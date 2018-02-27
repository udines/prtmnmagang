package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.pertamina.pertaminatuban.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputPerjalananDinasActivity extends AppCompatActivity {

    private EditText inputNoPegawai, inputNamaPegawai, inputNoPerjalanan, inputRangka, inputKe;
    private Button buttonWaktuMulai, buttonWaktuSelesai, buttonLanjut;
    private int yearMulai, monthMulai, dayMulai, yearSelesai, monthSelesai, daySelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_perjalanan_dinas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputNoPegawai = findViewById(R.id.input_perjalanan_dinas_no_pegawai);
        inputNamaPegawai = findViewById(R.id.input_perjalanan_dinas_nama_pegawai);
        inputNoPerjalanan = findViewById(R.id.input_perjalanan_dinas_no_perjalanan_dinas);
        inputRangka = findViewById(R.id.input_perjalanan_dinas_dalam_rangka);
        inputKe = findViewById(R.id.input_perjalanan_dinas_ke);
        buttonWaktuMulai = findViewById(R.id.input_perjalanan_dinas_waktu_mulai);
        buttonWaktuSelesai = findViewById(R.id.input_perjalanan_dinas_waktu_selesai);
        buttonLanjut = findViewById(R.id.input_perjalanan_dinas_lanjut);

        Calendar cal = Calendar.getInstance();
        yearMulai = cal.get(Calendar.YEAR);
        monthMulai = cal.get(Calendar.MONTH);
        dayMulai = cal.get(Calendar.DAY_OF_MONTH);
        yearSelesai = yearMulai;
        monthSelesai = monthMulai;
        daySelesai = dayMulai;

        setButtonDate(yearMulai, monthMulai, dayMulai, buttonWaktuMulai);
        setButtonDate(yearSelesai, monthSelesai, daySelesai, buttonWaktuSelesai);

        handleButtonLanjut();
        handleButtonDate();
    }

    private void handleButtonDate() {
        buttonWaktuMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yearMulai = i;
                        monthMulai = i1;
                        dayMulai = i2;
                        setButtonDate(yearMulai, monthMulai, dayMulai, buttonWaktuMulai);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        InputPerjalananDinasActivity.this,
                        listener,
                        yearMulai,
                        monthMulai,
                        dayMulai
                );
                dialog.show();
            }
        });

        buttonWaktuSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        yearSelesai = i;
                        monthSelesai = i1;
                        daySelesai = i2;
                        setButtonDate(yearSelesai, monthSelesai, daySelesai, buttonWaktuSelesai);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        InputPerjalananDinasActivity.this,
                        listener,
                        yearSelesai,
                        monthSelesai,
                        daySelesai
                );
                dialog.show();
            }
        });

        buttonLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputRincianActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void setButtonDate(int year, int month, int day, Button dateButton) {
        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        dateButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void handleButtonLanjut() {
        buttonLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
