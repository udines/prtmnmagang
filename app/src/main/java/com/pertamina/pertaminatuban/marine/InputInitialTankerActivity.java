package com.pertamina.pertaminatuban.marine;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.pertamina.pertaminatuban.R;

public class InputInitialTankerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_initial_tanker);
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
        EditText inputCallTanker, inputVoyage, inputNoBill, inputHandlingAgent, inputGeneralAgent,
                inputCargoStatus, inputPortCall, inputPortCallReport, inputLastPort, inputNextPort;
        Button inputPeriode, kirim;
        RadioGroup groupStatusTanker, groupStatusOperasional, groupGrades, groupTankerActivity,
                groupPumpingMethod, groupBarthing;

        inputCallTanker = findViewById(R.id.input_initial_tanker_call_tanker);
        inputVoyage = findViewById(R.id.input_initial_tanker_voyage_tanker);
        inputNoBill = findViewById(R.id.input_initial_tanker_no_bill_of_landing);
        inputHandlingAgent = findViewById(R.id.input_initial_tanker_handling_agent);
        inputGeneralAgent = findViewById(R.id.input_initial_tanker_general_agent);
        inputCargoStatus = findViewById(R.id.input_initial_tanker_cargo_status);
        inputPortCall = findViewById(R.id.input_initial_tanker_port_of_call);
        inputPortCallReport = findViewById(R.id.input_initial_tanker_port_of_call_report);
        inputLastPort = findViewById(R.id.input_initial_tanker_last_port);
        inputNextPort = findViewById(R.id.input_initial_tanker_next_port);

        inputPeriode = findViewById(R.id.input_initial_tanker_periode);
        kirim = findViewById(R.id.input_initial_tanker_kirim);

        groupStatusOperasional = findViewById(R.id.input_initial_tanker_group_status_operasional);
        groupStatusTanker = findViewById(R.id.input_initial_tanker_group_status_tanker);
        groupGrades = findViewById(R.id.input_initial_tanker_group_grades);
        groupTankerActivity = findViewById(R.id.input_initial_tanker_group_tanker_activity);
        groupPumpingMethod = findViewById(R.id.input_initial_tanker_group_pumpung_method);
        groupBarthing = findViewById(R.id.input_initial_tanker_group_barthing);
    }
}
