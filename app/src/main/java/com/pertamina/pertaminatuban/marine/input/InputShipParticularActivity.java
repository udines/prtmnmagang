package com.pertamina.pertaminatuban.marine.input;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.models.ShipParticular;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputData();
            }
        });
    }

    private void getInputData() {
        Calendar cal = Calendar.getInstance();

        /*int year, month;
        year = getIntent().getIntExtra("yearPeriod", 2018);
        month = getIntent().getIntExtra("monthPeriod", 0);
        cal.set(year, month, 1);*/

        Date date = new Date(cal.getTimeInMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd", Locale.getDefault());
        String bulan = format.format(date);

        /*String kapal = getIntent().getStringExtra("vesselName");
        String periode = getIntent().getStringExtra("periode");
        String callTanker = getIntent().getStringExtra("callTanker");
        kapal = kapal.toUpperCase();*/

        String kapal = "John Caine";
        kapal = kapal.toUpperCase();
        String periode = "A";
        String callTanker = "1";

        ArrayList<MarineInput> data = new ArrayList<>();

        data.add(new MarineInput(
                getDataIfAvailable(inputFlag),
                getResources().getString(R.string.variable_ship_particular_flag),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputDwt),
                getResources().getString(R.string.variable_ship_particular_dwt),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputGrt),
                getResources().getString(R.string.variable_ship_particular_grt),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputLoa),
                getResources().getString(R.string.variable_ship_particular_loa),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputTypeCall),
                getResources().getString(R.string.variable_ship_particular_type_call),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputTypeActivity),
                getResources().getString(R.string.variable_ship_particular_type_activity),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputRate),
                getResources().getString(R.string.variable_ship_particular_hire_rate),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        data.add(new MarineInput(
                getDataIfAvailable(inputMaster),
                getResources().getString(R.string.variable_ship_particular_master),
                kapal,
                periode,
                bulan,
                callTanker
        ));

        uploadData(data);
    }

    private void uploadData(ArrayList<MarineInput> data) {
        Log.w("json", new Gson().toJson(data));
    }

    private String getDataIfAvailable(EditText inputField) {
        if (!inputField.getText().toString().isEmpty()) {
            return inputField.getText().toString();
        } else {
            return "";
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

    }

    private boolean currentDataExist() {
        return false;
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
