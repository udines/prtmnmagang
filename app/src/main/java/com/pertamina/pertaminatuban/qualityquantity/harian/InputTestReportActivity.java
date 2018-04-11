package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.pertamina.pertaminatuban.operation.pumpable.PumpableActivity;
import com.pertamina.pertaminatuban.qualityquantity.models.NewTestReportHeader;
import com.pertamina.pertaminatuban.qualityquantity.models.NewTestReportItem;
import com.pertamina.pertaminatuban.service.QqClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
                String solarDensity, solarColor, solarFlash, solarDestilation, solarEvap90, solarCalcCetane, solarWater,
                        solarViscocity, solarSulfurContent, solarPourPoint, solarCopper, solarAcid, solarCarbon;

                inputSolarDensity = findViewById(R.id.input_test_report_solar_density);
                inputSolarColor = findViewById(R.id.input_test_report_solar_colour);
                inputSolarFlash = findViewById(R.id.input_test_report_solar_flash);
                inputSolarDestilation = findViewById(R.id.input_test_report_solar_destilation);
                inputSolarEvap90 = findViewById(R.id.input_test_report_solar_evap_90);
                inputSolarCalcCetane = findViewById(R.id.input_test_report_solar_calc_cetane);
                inputSolarWater = findViewById(R.id.input_test_report_solar_water_content);
                inputSolarViscocity = findViewById(R.id.input_test_report_solar_viscocity);
                inputSolarSulfurContent = findViewById(R.id.input_test_report_solar_sulfur_content);
                inputSolarPourPoint = findViewById(R.id.input_test_report_solar_pour_point);
                inputSolarCopper = findViewById(R.id.input_test_report_solar_copper_corossion);
                inputSolarAcid = findViewById(R.id.input_test_report_solar_total_acid);
                inputSolarCarbon = findViewById(R.id.input_test_report_solar_carbon_residue);

                solarDensity = getInputData(inputSolarDensity);
                solarColor = getInputData(inputSolarColor);
                solarFlash = getInputData(inputSolarFlash);
                solarDestilation = getInputData(inputSolarDestilation);
                solarEvap90 = getInputData(inputSolarEvap90);
                solarCalcCetane = getInputData(inputSolarCalcCetane);
                solarWater = getInputData(inputSolarWater);
                solarViscocity = getInputData(inputSolarViscocity);
                solarSulfurContent = getInputData(inputSolarSulfurContent);
                solarPourPoint = getInputData(inputSolarPourPoint);
                solarCopper = getInputData(inputSolarCopper);
                solarAcid = getInputData(inputSolarAcid);
                solarCarbon = getInputData(inputSolarCarbon);

                solarReportItems.get(0).setHasilUji(solarDensity);
                solarReportItems.get(1).setHasilUji(solarColor);
                solarReportItems.get(2).setHasilUji(solarFlash);
                solarReportItems.get(3).setHasilUji(solarDestilation);
                solarReportItems.get(4).setHasilUji(solarEvap90);
                solarReportItems.get(5).setHasilUji(solarCalcCetane);
                solarReportItems.get(6).setHasilUji(solarWater);
                solarReportItems.get(7).setHasilUji(solarViscocity);
                solarReportItems.get(8).setHasilUji(solarSulfurContent);
                solarReportItems.get(9).setHasilUji(solarPourPoint);
                solarReportItems.get(10).setHasilUji(solarCopper);
                solarReportItems.get(11).setHasilUji(solarAcid);
                solarReportItems.get(12).setHasilUji(solarCarbon);

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

                EditText inputNonAppearance, inputNonColor, inputNonDensity, inputNonRon, inputNonSulfurContent,
                        inputNonDestilation, inputNonIbp, inputNonEvap10, inputNonEvap50, inputNonEvap90,
                        inputNonFbp, inputNonResidu, inputNonReid, inputNonExistensi, inputNonCopper,
                        inputNonOxidation, inputNonDoctor, inputNonOlefin, inputNonSulfurMercaptan;
                String nonAppearance, nonColor, nonDensity, nonRon, nonSulfurContent, nonDestilation, nonIbp,
                        nonEvap10, nonEvap50, nonEvap90, nonFbp, nonResidu, nonReid, nonExistensi, nonCopper,
                        nonOxidation, nonDoctor, nonOlefin, nonSulfurMercaptan;

                inputNonAppearance = findViewById(R.id.input_test_report_non_appearance);
                inputNonColor = findViewById(R.id.input_test_report_non_colour);
                inputNonDensity = findViewById(R.id.input_test_report_non_density);
                inputNonRon = findViewById(R.id.input_test_report_non_ron);
                inputNonSulfurContent = findViewById(R.id.input_test_report_non_sulfur_content);
                inputNonDestilation = findViewById(R.id.input_test_report_non_destilation);
                inputNonIbp = findViewById(R.id.input_test_report_non_ibp);
                inputNonEvap10 = findViewById(R.id.input_test_report_non_10_evap);
                inputNonEvap50 = findViewById(R.id.input_test_report_non_50_evap);
                inputNonEvap90 = findViewById(R.id.input_test_report_non_90_evap);
                inputNonFbp = findViewById(R.id.input_test_report_non_fbp);
                inputNonResidu = findViewById(R.id.input_test_report_non_residu);
                inputNonReid = findViewById(R.id.input_test_report_non_reid_vapor);
                inputNonExistensi = findViewById(R.id.input_test_report_non_existensi);
                inputNonCopper = findViewById(R.id.input_test_report_non_copper);
                inputNonOxidation = findViewById(R.id.input_test_report_non_oxidation);
                inputNonDoctor = findViewById(R.id.input_test_report_non_doctor_test);
                inputNonOlefin = findViewById(R.id.input_test_report_non_olefin);
                inputNonSulfurMercaptan = findViewById(R.id.input_test_report_non_sulfur_mercaptan);

                nonAppearance = getInputData(inputNonAppearance);
                nonColor = getInputData(inputNonColor);
                nonDensity = getInputData(inputNonDensity);
                nonRon = getInputData(inputNonRon);
                nonSulfurContent = getInputData(inputNonSulfurContent);
                nonDestilation = getInputData(inputNonDestilation);
                nonIbp = getInputData(inputNonIbp);
                nonEvap10 = getInputData(inputNonEvap10);
                nonEvap50 = getInputData(inputNonEvap50);
                nonEvap90 = getInputData(inputNonEvap90);
                nonFbp = getInputData(inputNonFbp);
                nonResidu = getInputData(inputNonResidu);
                nonReid = getInputData(inputNonReid);
                nonExistensi = getInputData(inputNonExistensi);
                nonCopper = getInputData(inputNonCopper);
                nonOxidation = getInputData(inputNonOxidation);
                nonDoctor = getInputData(inputNonDoctor);
                nonOlefin = getInputData(inputNonOlefin);
                nonSulfurMercaptan = getInputData(inputNonSulfurMercaptan);

                nonSolarReportItems.get(0).setHasilUji(nonAppearance);
                nonSolarReportItems.get(1).setHasilUji(nonColor);
                nonSolarReportItems.get(2).setHasilUji(nonDensity);
                nonSolarReportItems.get(3).setHasilUji(nonRon);
                nonSolarReportItems.get(4).setHasilUji(nonSulfurContent);
                nonSolarReportItems.get(5).setHasilUji(nonDestilation);
                nonSolarReportItems.get(6).setHasilUji(nonIbp);
                nonSolarReportItems.get(7).setHasilUji(nonEvap10);
                nonSolarReportItems.get(8).setHasilUji(nonEvap50);
                nonSolarReportItems.get(9).setHasilUji(nonEvap90);
                nonSolarReportItems.get(10).setHasilUji(nonFbp);
                nonSolarReportItems.get(11).setHasilUji(nonResidu);
                nonSolarReportItems.get(12).setHasilUji(nonReid);
                nonSolarReportItems.get(13).setHasilUji(nonExistensi);
                nonSolarReportItems.get(14).setHasilUji(nonCopper);
                nonSolarReportItems.get(15).setHasilUji(nonOxidation);
                nonSolarReportItems.get(16).setHasilUji(nonDoctor);
                nonSolarReportItems.get(17).setHasilUji(nonOlefin);
                nonSolarReportItems.get(18).setHasilUji(nonSulfurMercaptan);

                NewTestReportHeader reportHeader = new NewTestReportHeader(
                        no,
                        product,
                        date,
                        vessel,
                        batch,
                        destination,
                        shoreTank,
                        exRefinery,
                        sample,
                        exSample
                );

                if (product.equals(Matbal.SOLAR)) {
                    reportHeader.setTable(solarReportItems);
                } else {
                    reportHeader.setTable(nonSolarReportItems);
                }

                /*Intent intent = new Intent(getApplicationContext(), InputRincianTestReportActivity.class);
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

                startActivity(intent);*/

                sendPostRequest(reportHeader);
            }
        });
    }

    private void sendPostRequest(NewTestReportHeader reportHeader) {
        SharedPreferences preferences = InputTestReportActivity.this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        final String key = preferences.getString("userKey", "none");

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Authorization", key)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        QqClient qqClient = retrofit.create(QqClient.class);
        Call<Object> call = qqClient.postTestReport(reportHeader);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("msg", String.valueOf(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
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
