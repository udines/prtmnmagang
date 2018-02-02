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

public class InputShipParticularActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_ship_particular);
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
        EditText inputFlag, inputDwt, inputGrt, inputLoa, inputTypeCall, inputTypeActivity, inputRate,
                inputMaster;
        Button kirim;
        inputFlag = findViewById(R.id.input_ship_particular_flag);
        inputDwt = findViewById(R.id.input_ship_particular_dwt);
        inputGrt = findViewById(R.id.input_ship_particular_grt);
        inputLoa = findViewById(R.id.input_ship_particular_loa);
        inputTypeCall = findViewById(R.id.input_ship_particular_type_call);
        inputTypeActivity = findViewById(R.id.input_ship_particular_type_activity);
        inputRate = findViewById(R.id.input_ship_particular_rate);
        inputMaster = findViewById(R.id.input_ship_particular_master);
        kirim = findViewById(R.id.input_ship_particular_kirim);
    }

}
