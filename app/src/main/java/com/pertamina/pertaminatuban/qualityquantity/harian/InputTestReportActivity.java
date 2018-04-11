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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.qualityquantity.models.NewTestReportItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InputTestReportActivity extends AppCompatActivity {

    private EditText inputNo, inputVessel, inputBatch, inputDestination, inputShoreTank, inputExRefinery, inputSample, inputExSample;
    private Spinner spinnerProduct;
    private Button dateButton, lanjutButton;

    private String no, vessel, batch, destination, shoreTank, exRefinery, product, date, sample, exSample;
    private int day, month, year;
    private LinearLayout containerSolar, containerNonSolar;

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

        Calendar cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        setDateButton(year, month, day, dateButton);

        initData();
        handleLanjutButton();
    }

    private void handleLanjutButton() {
        lanjutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                no = getInputData(inputNo);
                vessel = getInputData(inputVessel);
                batch = getInputData(inputBatch);
                destination = getInputData(inputDestination);
                shoreTank = getInputData(inputShoreTank);
                exRefinery = getInputData(inputExRefinery);
                sample = getInputData(inputSample);
                exSample = getInputData(inputExSample);

                ArrayList<NewTestReportItem> solarReportItems = new ArrayList<>();
                solarReportItems.add(new NewTestReportItem("Density at 15°C", "ASTM D-1298", "kg/m3", "815-860"));
                solarReportItems.add(new NewTestReportItem("Colour", "ASTM D-1500", "", "Max. 3.0"));
                solarReportItems.add(new NewTestReportItem("Flash Point PMcc", "ASTM D-93", "°C", "Min. 52"));
                solarReportItems.add(new NewTestReportItem("Destillation", "ASTM D-86", "", ""));
                solarReportItems.add(new NewTestReportItem("Evap. Rec. at 90%", "", "°C", "Max. 370"));
                solarReportItems.add(new NewTestReportItem("Calculated Cetane Index", "ASTM D-4737", "", "Min. 45"));
                solarReportItems.add(new NewTestReportItem("Water Content", "ASTM D-6304", "mg/kg", "Max. 500"));
                solarReportItems.add(new NewTestReportItem("Viscocity Kinematic at 40°C", "ASTM D-445", "mm2/s", "2.0-4.5"));
                solarReportItems.add(new NewTestReportItem("Sulfur Content", "ASTM D-4294", "% m/m", "Max. 0.25"));
                solarReportItems.add(new NewTestReportItem("Pour Point", "ASTM D-97", "°C", "Max. 18"));
                solarReportItems.add(new NewTestReportItem("Copper Strip Corossion", "ASTM D-130", "Merit", "Max. Kelas 1"));
                solarReportItems.add(new NewTestReportItem("Total Acid Number", "ASTM D-664", "mg KOH/g", "Max. 0.6"));
                solarReportItems.add(new NewTestReportItem("Carbon Residue", "ASTM D-4530", "% m/m", "Max. 0.1"));

                EditText inputSolarDensity, inputSolarColor, inputSolarFlash, inputSolarDestilation, inputSolarEvap90,
                        inputSolarCalcCetane, inputSolarWater, inputSolarViscocity, inputSolarSulfurContent,
                        inputSolarPourPoint, inputSolarCopper, inputSolarAcid, inputSolarCarbon;

                ArrayList<NewTestReportItem> nonSolarReportItems = new ArrayList<>();
                nonSolarReportItems.add(new NewTestReportItem("Apparance", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Colour", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Density 15°C", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("RON", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Sulfur Content", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Destillation", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("I.B.P", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("10% Vol. Evap.", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("50% Vol. Evap.", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("90% Vol. Evap.", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("F.B.P (End Point)", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Residu", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Reid Vapor Pressure", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Existensi Gum", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Copper Strip Corrosion", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Oxidation Stability", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Doctor Test", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Olefin", "ASTM D-4530", "% m/m", "Max. 0.1"));
                nonSolarReportItems.add(new NewTestReportItem("Sulfur Mercaptan", "ASTM D-4530", "% m/m", "Max. 0.1"));

                EditText inputNonAppearance, inputNonColor, inputNonDensity, inputNonRon, inputRonSulfurContent,
                        inputNonDestilation, inputNonIbp, inputNonEvap10, inputNonEvap50, inputNonEvap90,
                        inputNonFbp, inputNonResidu, inputNonReid, inputNonExistensi, inputNonCopper,
                        inputNonOxidation, inputNonDoctor, inputNonOlefin, inputNonSulfurMercaptan;

                Intent intent = new Intent(getApplicationContext(), InputRincianTestReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("no", no);
                intent.putExtra("vessel", vessel);
                intent.putExtra("batch", batch);
                intent.putExtra("destination", destination);
                intent.putExtra("shoreTank", shoreTank);
                intent.putExtra("exRefinery", exRefinery);
                intent.putExtra("sample", sample);
                intent.putExtra("exSample", exSample);
                intent.putExtra("product", product);
                intent.putExtra("date", date);

                startActivity(intent);
            }
        });
    }

    private String getInputData(EditText editText) {
        if (editText.getText().length() > 0) {
            return editText.getText().toString();
        } else {
            return "";
        }
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
                if (product.equals(Matbal.SOLAR)) {
                    //display input untuk solar
                    containerSolar.setVisibility(View.VISIBLE);
                    containerNonSolar.setVisibility(View.GONE);
                } else {
                    //display input untuk non solar
                    containerSolar.setVisibility(View.GONE);
                    containerNonSolar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setDateButton(int year, int month, int day, Button dateButton) {
        SimpleDateFormat format = new SimpleDateFormat("dd - MM - yyyy", Locale.getDefault());
        SimpleDateFormat uploadFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        dateButton.setText(format.format(new Date(cal.getTimeInMillis())));
        date = uploadFormat.format(new Date(cal.getTimeInMillis()));
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
        inputSample = findViewById(R.id.input_test_report_sample);
        inputExSample = findViewById(R.id.input_test_report_exsample);
        containerNonSolar = findViewById(R.id.input_test_report_container_non_solar);
        containerSolar = findViewById(R.id.input_test_report_container_solar);
    }

}
