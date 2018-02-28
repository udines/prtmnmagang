package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.MatbalActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TambahRincianActivity extends AppCompatActivity {

    private Spinner spinnerJenis;
    private Button buttonTambahkan, buttonTanggal;
    private TextView textTotal;
    private LinearLayout containerAntarKota, containerTiketPesawat, containerUangHarian, containerUangMakan,
    containerTransport, containerAkomodasiLaundry;

    private long total = 0;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_rincian);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        containerUangHarian = findViewById(R.id.tambah_rincian_container_uang_harian);
        containerUangMakan = findViewById(R.id.tambah_rincian_container_uang_makan_harian);
        containerAntarKota = findViewById(R.id.tambah_rincian_container_antar_kota);
        containerTiketPesawat = findViewById(R.id.tambah_rincian_container_tiket_pesawat);
        containerTransport = findViewById(R.id.tambah_rincian_container_transport);
        containerAkomodasiLaundry = findViewById(R.id.tambah_rincian_container_akomodasi_laundry);

        buttonTambahkan = findViewById(R.id.tambah_rincian_button_tambahkan);
        buttonTanggal = findViewById(R.id.tambah_rincian_button_tanggal);
        textTotal = findViewById(R.id.tambah_rincian_total);

        handleSpinnerJenis();
        handleButtonTanggal();
        handleButtonTambahkan();
    }

    private void handleButtonTambahkan() {
        buttonTambahkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (spinnerJenis.getSelectedItemPosition()) {
                    case 0:
                        getDataAntarKota();
                        break;
                    case 1:
//                        getDataTransport();
                        break;
                    case 2:
                        getDataAkomodasi();
                        break;
                    case 3:
//                        getDataMakanHarian();
                        break;
                    case 4:
                        break;
                    case 5:
//                        getDataUangHarian();
                        break;
                    case 6:
//                        getDataTiketPesawat();
                        break;
                    case 7:
                        break;
                }
            }
        });
    }

    private void getDataAkomodasi() {
        EditText inputKeterangan = findViewById(R.id.tambah_rincian_akomodasi_laundry_keterangan);
    }

    private void getDataAntarKota() {
        EditText inputKeterangan = findViewById(R.id.tambah_rincian_antar_kota_keterangan);
    }

    private void handleButtonTanggal() {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        setDateButton(year, month, day);

        buttonTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        setDateButton(year, month, day);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        TambahRincianActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void setDateButton(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        buttonTanggal.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void clearContainer() {
        total = 0;
        updateTotal();

        containerUangHarian.setVisibility(View.GONE);
        containerUangMakan.setVisibility(View.GONE);
        containerAntarKota.setVisibility(View.GONE);
        containerTiketPesawat.setVisibility(View.GONE);
        containerTransport.setVisibility(View.GONE);
        containerAkomodasiLaundry.setVisibility(View.GONE);
    }

    private void updateTotal() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        textTotal.setText(String.valueOf("Total: Rp" + formatter.format(total)));
    }

    private void handleSpinnerJenis() {
        spinnerJenis = findViewById(R.id.tambah_rincian_jenis);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jenis_claim_trip, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenis.setAdapter(adapter);
        spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        clearContainer();
                        containerAntarKota.setVisibility(View.VISIBLE);
                        handleAntarKota();
                        break;
                    case 1:
                        clearContainer();
                        containerTransport.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        clearContainer();
                        containerAkomodasiLaundry.setVisibility(View.VISIBLE);
                        handleAkomodasiLaundry();
                        break;
                    case 3:
                        clearContainer();
                        containerUangMakan.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        clearContainer();
                        break;
                    case 5:
                        clearContainer();
                        containerUangHarian.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        clearContainer();
                        containerTiketPesawat.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        clearContainer();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void handleAkomodasiLaundry() {
        final EditText inputAkomodasi = findViewById(R.id.tambah_rincian_akomodasi);
        final EditText inputLaundry = findViewById(R.id.tambah_rincian_laundry);
        inputAkomodasi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                long totalLaundry = 0;
                if (inputLaundry.getText().length() > 0) {{
                    totalLaundry = Long.parseLong(inputLaundry.getText().toString());
                }}
                if (charSequence.length() > 0) {
                    total = Long.parseLong(charSequence.toString()) + totalLaundry;
                } else {
                    total = totalLaundry;
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputLaundry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                long totalAkomodasi = 0;
                if (inputAkomodasi.getText().length() > 0) {{
                    totalAkomodasi = Long.parseLong(inputAkomodasi.getText().toString());
                }}
                if (charSequence.length() > 0) {
                    total = Long.parseLong(charSequence.toString()) + totalAkomodasi;
                } else {
                    total = totalAkomodasi;
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void handleAntarKota() {
        final EditText inputJarak = findViewById(R.id.tambah_rincian_antar_kota_jarak);
        inputJarak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    total = Long.parseLong(charSequence.toString()) * 2000;
                    if (total > 750000) {
                        total = 750000;
                    }
                } else {
                    total = 0;
                }
                updateTotal();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

}
