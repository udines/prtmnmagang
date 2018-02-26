package com.pertamina.pertaminatuban.finance.perjalanan_dinas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.finance.models.UraianPerjalanan;

import java.util.ArrayList;

public class UraianPerjalananActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uraian_perjalanan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.uraian_perjalanan_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<UraianPerjalanan> uraians = new ArrayList<>();
        uraians.add(new UraianPerjalanan(
                "6001008100",
                "A1502246",
                "F16430",
                "Transport antar kota *)",
                "432000",
                "TBN GRS SBY, PP"
        ));
        uraians.add(new UraianPerjalanan(
                "6001008100",
                "A1502246",
                "F16430",
                "Transport Bandara/stasiun/pelabuhan/Terminal",
                "0",
                "TBN GRS SBY, PP"
        ));
        uraians.add(new UraianPerjalanan(
                "6001008100",
                "A1502246",
                "F16430",
                "Uang Makan Harian",
                "450000",
                "P,S,M"
        ));
        recyclerView.setAdapter(new UraianPerjalananAdapter(uraians));
    }

}
