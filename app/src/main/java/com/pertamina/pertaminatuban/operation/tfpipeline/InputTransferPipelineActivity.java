package com.pertamina.pertaminatuban.operation.tfpipeline;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.KonsumenActivity;
import com.pertamina.pertaminatuban.marine.input.InputTankerMovementActivity;
import com.pertamina.pertaminatuban.operation.models.TransferPipeline;
import com.pertamina.pertaminatuban.operation.pumpable.InputPumpableActivity;
import com.pertamina.pertaminatuban.service.OperationClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputTransferPipelineActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText inputBatch;
    private EditText inputQuantity;
    private Button buttonStartDate, buttonStartTime;
    private Button buttonStopDate, buttonStopTime;
    private Button kirim;
    private TextView textJumlah;

    private int startYear, startMonth, startDay, startHour, startMinute;
    private int stopYear, stopMonth, stopDay, stopHour, stopMinute;

    private String produk, batch, start, stop, jumlah;
    private long quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_transfer_pipeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.input_transfer_pipeline_spinner_produk);
        inputBatch = findViewById(R.id.input_transfer_pipeline_batch);
        inputQuantity = findViewById(R.id.input_transfer_pipeline_quantity);
        buttonStartDate = findViewById(R.id.input_transfer_pipeline_start_date);
        buttonStartTime = findViewById(R.id.input_transfer_pipeline_start_time);
        buttonStopDate = findViewById(R.id.input_transfer_pipeline_stop_date);
        buttonStopTime = findViewById(R.id.input_transfer_pipeline_stop_time);
        textJumlah = findViewById(R.id.input_transfer_pipeline_jumlah);
        kirim = findViewById(R.id.input_transfer_pipeline_kirim);

        Calendar cal = Calendar.getInstance();
        startYear = cal.get(Calendar.YEAR);
        startMonth = cal.get(Calendar.MONTH);
        startDay = cal.get(Calendar.DAY_OF_MONTH);
        stopYear = cal.get(Calendar.YEAR);
        stopMonth = cal.get(Calendar.MONTH);
        stopDay = cal.get(Calendar.DAY_OF_MONTH);

        startHour = cal.get(Calendar.HOUR_OF_DAY);
        startMinute = 0;
        stopHour = cal.get(Calendar.HOUR_OF_DAY);
        stopMinute = 0;

        setDateTimeButton(startYear, startMonth, startDay, startHour, startMinute, buttonStartDate, buttonStartTime);
        setDateTimeButton(stopYear, stopMonth, stopDay, stopHour, stopMinute, buttonStopDate, buttonStopTime);
        calculateJumlah();

        handleDateTimeButtonClick();
        handleSpinner();
        setInitBatchData();
        handleKirim();
    }

    private void handleKirim() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputTerisi(inputBatch) && inputTerisi(inputQuantity)) {
                    String fuel = spinner.getSelectedItem().toString();
                    int batch = Integer.parseInt(inputBatch.getText().toString());
                    long quantity = Long.parseLong(inputQuantity.getText().toString());
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    Calendar cal = Calendar.getInstance();
                    cal.set(startYear, startMonth, startDay, startHour, startMinute);
                    String start  = format.format(new Date(cal.getTimeInMillis()));
                    cal.set(stopYear, stopMonth, stopDay, stopHour, stopMinute);
                    String stop = format.format(new Date(cal.getTimeInMillis()));
                    String jumlah = textJumlah.getText().toString() + ":00";

                    TransferPipeline object = new TransferPipeline(
                            quantity,
                            start,
                            stop,
                            batch,
                            jumlah,
                            fuel
                    );
                    sendPostRequest(object);
                }
            }
        });
    }

    private void sendPostRequest(TransferPipeline object) {
        SharedPreferences preferences = InputTransferPipelineActivity.this.getSharedPreferences(
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
        OperationClient operationClient = retrofit.create(OperationClient.class);

        Log.w("body", new Gson().toJson(object));
    }

    private boolean inputTerisi(EditText editText) {
        return editText.getText().length() > 0;
    }

    private void setInitBatchData() {
        Calendar cal = Calendar.getInstance();
        inputBatch.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
    }

    private void handleSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.bahan_bakar, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void handleDateTimeButtonClick() {
        buttonStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        startYear = i;
                        startMonth = i1;
                        startDay = i2;
                        setDateTimeButton(startYear, startMonth, startDay, startHour, startMinute, buttonStartDate, buttonStartTime);
                        calculateJumlah();
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        InputTransferPipelineActivity.this,
                        listener,
                        startYear,
                        startMonth,
                        startDay
                );
                dialog.show();
            }
        });

        buttonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        startHour = i;
                        startMinute = i1;
                        setDateTimeButton(startYear, startMonth, startDay, startHour, startMinute, buttonStartDate, buttonStartTime);
                        calculateJumlah();
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(
                        InputTransferPipelineActivity.this,
                        listener,
                        startHour,
                        startMinute,
                        true
                );
                dialog.show();
            }
        });

        buttonStopDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        stopYear = i;
                        stopMonth = i1;
                        stopDay = i2;
                        setDateTimeButton(stopYear, stopMonth, stopDay, stopHour, stopMinute, buttonStopDate, buttonStopTime);
                        calculateJumlah();
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        InputTransferPipelineActivity.this,
                        listener,
                        stopYear,
                        stopMonth,
                        stopDay
                );
                dialog.show();
            }
        });

        buttonStopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        stopHour = i;
                        stopMinute = i1;
                        setDateTimeButton(stopYear, stopMonth, stopDay, stopHour, stopMinute, buttonStopDate, buttonStopTime);
                        calculateJumlah();
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(
                        InputTransferPipelineActivity.this,
                        listener,
                        stopHour,
                        stopMinute,
                        true
                );
                dialog.show();
            }
        });
    }

    private void setDateTimeButton(int year, int month, int day, int hour, int minute, Button dateButton, Button timeButton) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute);
        Date date = new Date(cal.getTimeInMillis());

        SimpleDateFormat dateFormat, timeFormat;
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

        dateButton.setText(dateFormat.format(date));
        timeButton.setText(timeFormat.format(date));
    }

    private void calculateJumlah() {
        Calendar startCal, stopCal;
        startCal = Calendar.getInstance();
        stopCal = Calendar.getInstance();

        startCal.set(startYear, startMonth, startDay, startHour, startMinute);
        stopCal.set(stopYear, stopMonth, stopDay, stopHour, stopMinute);

        long diff = stopCal.getTimeInMillis() - startCal.getTimeInMillis();
        diff = diff / 1000;
        diff = diff / 60;
        long hour = diff / 60;
        long minute = diff % 60;

        textJumlah.setText(String.valueOf("Jumlah: " + String.format("%02d:%02d", hour, minute)));
    }
}
