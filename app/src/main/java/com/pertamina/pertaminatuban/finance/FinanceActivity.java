package com.pertamina.pertaminatuban.finance;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.costperliter.CostPerLiterActivity;
import com.pertamina.pertaminatuban.finance.perjalanan_dinas.PerjalananDinasActivity;
import com.pertamina.pertaminatuban.finance.realisasi_anggaran.RealisasiAnggaranActivity;

public class FinanceActivity extends AppCompatActivity {

    private CardView realisasiAnggaran, perjalananDinas, costPerLiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        realisasiAnggaran = findViewById(R.id.finance_menu_realisasi_anggaran);
        perjalananDinas = findViewById(R.id.finance_menu_laporan_perjalanan);
        costPerLiter = findViewById(R.id.finance_menu_cost_per_liter);

        perjalananDinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PerjalananDinasActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        realisasiAnggaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RealisasiAnggaranActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        costPerLiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CostPerLiterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
