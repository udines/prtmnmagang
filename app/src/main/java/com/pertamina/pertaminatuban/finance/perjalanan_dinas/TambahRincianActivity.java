package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.pertamina.pertaminatuban.R;

import java.util.ArrayList;

public class TambahRincianActivity extends AppCompatActivity {

    private Spinner spinnerJenis;

    private LinearLayout containerAntarKota, containerTiketPesawat, containerUangHarian, containerUangMakan,
    containerTransport, containerAkomodasiLaundry;

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

        handleSpinnerJenis();
    }

    private void clearContainer() {
        containerUangHarian.setVisibility(View.GONE);
        containerUangMakan.setVisibility(View.GONE);
        containerAntarKota.setVisibility(View.GONE);
        containerTiketPesawat.setVisibility(View.GONE);
        containerTransport.setVisibility(View.GONE);
        containerAkomodasiLaundry.setVisibility(View.GONE);
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
                        break;
                    case 1:
                        clearContainer();
                        containerTransport.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        clearContainer();
                        containerAkomodasiLaundry.setVisibility(View.VISIBLE);
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

}
