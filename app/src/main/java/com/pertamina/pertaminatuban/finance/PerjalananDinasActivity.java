package com.pertamina.pertaminatuban.finance;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class PerjalananDinasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perjalanan_dinas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.perjalanan_dinas_recycler);
        ArrayList<LaporanPerjalananDinas> laporans = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        laporans.add(new LaporanPerjalananDinas(
                "Firdan Islamy",
                "900753331",
                "020/SPD/F15432/SPD/2018-S0",
                "Fire Fighting Competition MOR V 2018",
                "Gresik & Surabaya",
                new Timestamp(cal.getTimeInMillis()),
                new Timestamp(cal.getTimeInMillis()),
                "Rp1,382,000"
        ));
        laporans.add(new LaporanPerjalananDinas(
                "Firdan Islamy",
                "900753331",
                "020/SPD/F15432/SPD/2018-S0",
                "Fire Fighting Competition MOR V 2018",
                "Gresik & Surabaya",
                new Timestamp(cal.getTimeInMillis()),
                new Timestamp(cal.getTimeInMillis()),
                "Rp1,382,000"
        ));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new LaporanPerjalananAdapter(laporans));
    }

}
