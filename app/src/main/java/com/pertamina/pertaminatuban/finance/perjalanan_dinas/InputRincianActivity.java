package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class InputRincianActivity extends AppCompatActivity {

    private TextView nama, noPekerja, noPerjalanan, rangka, ke, waktuMulai, waktuSelesai;
    private RecyclerView recyclerView;
    private Button tambah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rincian);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nama = findViewById(R.id.input_rincian_nama);
        noPekerja = findViewById(R.id.input_rincian_no_pekerja);
        noPerjalanan = findViewById(R.id.input_rincian_no_perjalanan);
        rangka = findViewById(R.id.input_rincian_rangka);
        ke = findViewById(R.id.input_rincian_ke);
        waktuMulai = findViewById(R.id.input_rincian_waktu_mulai);
        waktuSelesai = findViewById(R.id.input_rincian_waktu_selesai);
        recyclerView = findViewById(R.id.input_rincian_recyclerview);
        tambah = findViewById(R.id.input_rincian_tambah);

        handleTambah();
    }

    private void handleTambah() {
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TambahRincianActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
