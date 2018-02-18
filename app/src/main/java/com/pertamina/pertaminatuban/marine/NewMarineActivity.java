package com.pertamina.pertaminatuban.marine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;

public class NewMarineActivity extends AppCompatActivity {

    private int month;
    private int year;
    private int day;
    private TextView periodeBulan;
    private RecyclerView recyclerView;
    private Spinner periodeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_marine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        periodeBulan = findViewById(R.id.new_marine_bulan);
        periodeSpinner = findViewById(R.id.new_marine_periode_spinner);
        recyclerView = findViewById(R.id.new_marine_recyclerview);
    }

}
