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
import com.pertamina.pertaminatuban.marine.models.ShipParticular;

public class InputShipParticularActivity extends AppCompatActivity {

    private EditText inputFlag, inputDwt, inputGrt, inputLoa, inputTypeCall, inputTypeActivity, inputRate,
            inputMaster;

    private ShipParticular shipParticular;

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

        if (currentDataExist()) {
            getCurrentData();
            setInitData();
        }
    }

    private void setInitData() {
        setEditText(inputFlag, shipParticular.getFlag());
        setEditText(inputDwt, shipParticular.getDwt());
        setEditText(inputGrt, shipParticular.getGrt());
        setEditText(inputLoa, shipParticular.getLoa());
        setEditText(inputTypeCall, shipParticular.getTypeCall());
        setEditText(inputTypeActivity, shipParticular.getTypeActivity());
        setEditText(inputRate, shipParticular.getHireRate());
        setEditText(inputMaster, shipParticular.getMaster());
    }

    private void getCurrentData() {
        shipParticular = new ShipParticular(
                "Flag",
                0,
                10,
                20,
                null,
                "Loading",
                0,
                "Master"
        );
    }

    private boolean currentDataExist() {
        return true;
    }

    private void setEditText(EditText editText, Object value) {
        if (value != null) {
            if (value instanceof Float) {
                if ((Float) value != 0) {
                    editText.setText(String.valueOf(value));
                }
            } else if (value instanceof String) {
                editText.setText((String) value);
            }
        }
    }
}
