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

public class InputPortChargesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_port_charges);
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
        /*variable view*/
        EditText inputRambu, inputLabuh, inputTambat, inputPandu, inputTunda, inputPup;
        Button kirim;

        /*hubungkan variable view dengan layout*/
        inputRambu = findViewById(R.id.input_port_charges_rambu);
        inputLabuh = findViewById(R.id.input_port_charges_labuh);
        inputTambat = findViewById(R.id.input_port_charges_tambat);
        inputPandu = findViewById(R.id.input_port_charges_pandu);
        inputTunda = findViewById(R.id.input_port_charges_tunda);
        inputPup = findViewById(R.id.input_port_charges_pup);
        kirim = findViewById(R.id.input_port_charges_kirim);

        /*variable data*/
        long rambu, labuh, tambat, pandu, tunda, pup;

        /*handle pengguna klik button kirim*/
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

}
