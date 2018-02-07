package com.pertamina.pertaminatuban.marine.input;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.PortCharges;

public class InputPortChargesActivity extends AppCompatActivity {

    private EditText inputRambu, inputLabuh, inputTambat, inputPandu, inputTunda, inputPup;
    private PortCharges portCharges;

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

        initData();
    }

    private void initData() {
        /*variable view*/
        Button kirim;

        /*hubungkan variable view dengan layout*/
        inputRambu = findViewById(R.id.input_port_charges_rambu);
        inputLabuh = findViewById(R.id.input_port_charges_labuh);
        inputTambat = findViewById(R.id.input_port_charges_tambat);
        inputPandu = findViewById(R.id.input_port_charges_pandu);
        inputTunda = findViewById(R.id.input_port_charges_tunda);
        inputPup = findViewById(R.id.input_port_charges_pup);
        kirim = findViewById(R.id.input_port_charges_kirim);

        if (currentDataExist()) {
            getCurrentData();
            initInputData();
        }

        /*handle pengguna klik button kirim*/
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }

    private void getCurrentData() {
        portCharges = new PortCharges(
                12,
                0,
                34,
                45,
                56,
                67
        );
    }

    private void initInputData() {
        setEditText(inputRambu, portCharges.getLightDues());
        setEditText(inputLabuh, portCharges.getHarborDues());
        setEditText(inputTambat, portCharges.getQuayDues());
        setEditText(inputPandu, portCharges.getPilotage());
        setEditText(inputTunda, portCharges.getTowage());
        setEditText(inputPup, portCharges.getPup9a2());
    }

    private boolean currentDataExist() {
        return true;
    }

    private void setEditText(EditText editText, Object value) {
        if ((Float)value != 0) {
            editText.setText(String.valueOf(value));
        }
    }
}
