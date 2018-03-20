package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputTestReportActivity extends AppCompatActivity {

    private EditText inputNo, inputVessel, inputBatch, inputDestination, inputShoreTank, inputExRefinery;
    private Spinner spinnerProduct;
    private Button dateButton, lanjutButton;

    private String no, vessel, batch, destination, shoreTank, exRefinery, product, date;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_test_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initData();
        handleLanjutButton();
    }

    private void handleLanjutButton() {
        lanjutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputTerisi(inputNo) && inputTerisi(inputVessel) && inputTerisi(inputBatch)
                        && inputTerisi(inputDestination) && inputTerisi(inputShoreTank) &&
                        inputTerisi(inputExRefinery)) {

                    Intent intent = new Intent(getApplicationContext(), InputRincianTestReportActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(InputTestReportActivity.this, "Lengkapi data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean inputTerisi(EditText editText) {
        return editText.getText().length() > 0;
    }

    private void initData() {
        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setDateButton(year, month, day, dateButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pilihan_product, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(adapter);
        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                product = spinnerProduct.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setDateButton(int year, int month, int day, Button dateButton) {
        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        dateButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void initView() {
        inputNo = findViewById(R.id.input_test_report_nomor);
        inputVessel = findViewById(R.id.input_test_report_vessel);
        inputBatch = findViewById(R.id.input_test_report_batch);
        inputDestination = findViewById(R.id.input_test_report_destination);
        inputShoreTank = findViewById(R.id.input_test_report_shore_tank);
        inputExRefinery = findViewById(R.id.input_test_report_ex_refinery);
        spinnerProduct = findViewById(R.id.input_test_report_spinner);
        dateButton = findViewById(R.id.input_test_report_date_button);
        lanjutButton = findViewById(R.id.input_test_report_lanjut_button);
    }

}
