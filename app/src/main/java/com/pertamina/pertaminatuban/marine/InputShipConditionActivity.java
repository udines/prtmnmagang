package com.pertamina.pertaminatuban.marine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pertamina.pertaminatuban.R;

public class InputShipConditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ship_condition);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
    }

    private void getData() {
        EditText inputDescription, inputGradesBunker, inputRobLastPort, inputRobAta, inputRobAtd,
                inputRepl, inputLocationRepl, inputBunkerConsPort, inputBunkerConsSeatime,
                inputSlopTankAta, inputSlopTankAtd;
        Button ataTime, atdTime, comReplDate, comReplTime, compReplDate, compReplTime, kirim;

        inputDescription = findViewById(R.id.input_ship_condition_description_draft);
        inputGradesBunker = findViewById(R.id.input_ship_condition_grades_bunker);
        inputRobLastPort = findViewById(R.id.input_ship_condition_rob_last_port);
        inputRobAta = findViewById(R.id.input_ship_condition_rob_ata);
        inputRobAtd = findViewById(R.id.input_ship_condition_rob_atd);
        inputRepl = findViewById(R.id.input_ship_condition_repl);
        inputLocationRepl = findViewById(R.id.input_ship_condition_location_repl);
        inputBunkerConsPort = findViewById(R.id.input_ship_condition_bunker_consumption_port);
        inputBunkerConsSeatime = findViewById(R.id.input_ship_condition_bunker_consumption_seatime);
        inputSlopTankAta = findViewById(R.id.input_ship_condition_slop_tank_ata);
        inputSlopTankAtd = findViewById(R.id.input_ship_condition_slop_tank_atd);

        ataTime = findViewById(R.id.input_ship_condition_ata_time);
        atdTime = findViewById(R.id.input_ship_condition_atd_time);
        comReplDate = findViewById(R.id.input_ship_condition_com_repl_date);
        comReplTime = findViewById(R.id.input_ship_condition_com_repl_time);
        compReplDate = findViewById(R.id.input_ship_condition_comp_repl_date);
        compReplTime = findViewById(R.id.input_ship_condition_comp_repl_time);
        kirim = findViewById(R.id.input_ship_condition_kirim);
    }

}
