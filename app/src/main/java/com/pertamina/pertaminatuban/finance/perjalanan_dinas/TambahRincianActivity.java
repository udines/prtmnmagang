package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.pertamina.pertaminatuban.R;

import java.util.ArrayList;

public class TambahRincianActivity extends AppCompatActivity {

    private Spinner spinnerJenis;
    private AutoCompleteTextView antarKotaDari, antarKotaKe;

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
        spinnerJenis = findViewById(R.id.tambah_rincian_jenis);
        antarKotaDari = findViewById(R.id.tambah_rincian_antar_kota_dari);
        antarKotaKe = findViewById(R.id.tambah_rincian_antar_kota_ke);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jenis_claim_trip, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJenis.setAdapter(adapter);

        ArrayList<String> dariDanKe = new ArrayList<>();
        dariDanKe.add("Tuban");
        dariDanKe.add("Surabaya");
        dariDanKe.add("Yogyakarta");
        dariDanKe.add("Semarang");
        dariDanKe.add("Jakarta");
        ArrayAdapter<String> adapterDari = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,dariDanKe);
        antarKotaDari.setAdapter(adapterDari);
        antarKotaKe.setAdapter(adapterDari);
    }

}
