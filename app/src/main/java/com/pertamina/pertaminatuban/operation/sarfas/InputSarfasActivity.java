package com.pertamina.pertaminatuban.operation.sarfas;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.operation.models.LastBatch;
import com.pertamina.pertaminatuban.operation.models.TransferPipeline;
import com.pertamina.pertaminatuban.operation.tfpipeline.InputTransferPipelineActivity;
import com.pertamina.pertaminatuban.service.OperationClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class InputSarfasActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText inputBatch;
    private EditText inputQuantity;
    private Button buttonStartDate, buttonStartTime;
    private Button buttonStopDate, buttonStopTime;
    private Button kirim, bulanButton, hapus;
    private TextView textJumlah, batchTerakhir;

    private int startYear, startMonth, startDay, startHour, startMinute;
    private int stopYear, stopMonth, stopDay, stopHour, stopMinute;
    private String jumlah;

    private String id;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_sarfas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.input_sarfas_spinner_produk);
        inputBatch = findViewById(R.id.input_sarfas_batch);
        inputQuantity = findViewById(R.id.input_sarfas_quantity);
        buttonStartDate = findViewById(R.id.input_sarfas_start_date);
        buttonStartTime = findViewById(R.id.input_sarfas_start_time);
        buttonStopDate = findViewById(R.id.input_sarfas_stop_date);
        buttonStopTime = findViewById(R.id.input_sarfas_stop_time);
        textJumlah = findViewById(R.id.input_sarfas_jumlah);
        kirim = findViewById(R.id.input_sarfas_kirim);
        bulanButton = findViewById(R.id.input_sarfas_bulan_button);
        hapus = findViewById(R.id.input_sarfas_hapus);
        batchTerakhir = findViewById(R.id.input_sarfas_batch_terakhir);

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
        getLastBatch(startYear, startMonth);

        handleDateTimeButtonClick();
        handleSpinner();
        setInitBatchData();
        handleKirim();
        handleHapus();
    }

    private void getLastBatch(int year, int month) {
        SharedPreferences preferences = InputSarfasActivity.this.getSharedPreferences(
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
        Call<LastBatch> call = operationClient.getTwuLastBatch(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<LastBatch>() {
            @Override
            public void onResponse(Call<LastBatch> call, Response<LastBatch> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", new Gson().toJson(response.body()));
                    batchTerakhir.setText(String.valueOf("Batch terakhir " + response.body().getLastBatch()));
                }
            }

            @Override
            public void onFailure(Call<LastBatch> call, Throwable t) {
                batchTerakhir.setText(String.valueOf("Batch terakhir " + 0));
            }
        });
    }

    private void handleHapus() {
        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isUpdate) {
                    SharedPreferences preferences = InputSarfasActivity.this.getSharedPreferences(
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
                    Call<Object> call = operationClient.deleteTwu(id);
                    call.enqueue(new Callback<Object>() {
                        @Override
                        public void onResponse(Call<Object> call, Response<Object> response) {
                            Log.w("code", String.valueOf(response.code()));
                            if (response.code() == 200) {
                                Log.w("msg", new Gson().toJson(response.body()));
                                Toast.makeText(InputSarfasActivity.this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Object> call, Throwable t) {
                            Toast.makeText(InputSarfasActivity.this, "Data gagal dihapus", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
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
                    String jml = jumlah + ":00";

                    TransferPipeline object = new TransferPipeline(
                            quantity,
                            start,
                            stop,
                            batch,
                            jml,
                            fuel
                    );
                    Log.w("is update", String.valueOf(isUpdate));
                    if (isUpdate) {
                        Log.w("id", id);
                        object.setId(id);
                        sendUpdateRequest(object);
                    } else {
                        sendPostRequest(object);
                    }
                }
            }
        });
    }

    private void sendUpdateRequest(TransferPipeline object) {
        Log.w("put object", new Gson().toJson(object));

        SharedPreferences preferences = InputSarfasActivity.this.getSharedPreferences(
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

        Call<Object> call = operationClient.putTwu(object);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", String.valueOf(response.body()));
                    Toast.makeText(InputSarfasActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void sendPostRequest(TransferPipeline object) {
        SharedPreferences preferences = InputSarfasActivity.this.getSharedPreferences(
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

        Call<Object> call = operationClient.postTwu(object);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    Log.w("body", String.valueOf(response.body()));
                    Toast.makeText(InputSarfasActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private boolean inputTerisi(EditText editText) {
        return editText.getText().length() > 0;
    }

    private void setInitBatchData() {
        setBulanButton(startMonth, startYear, bulanButton);

        bulanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        InputSarfasActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                startDay = today.get(Calendar.DAY_OF_MONTH);
                                startMonth = selectedMonth;
                                startYear = selectedYear;
                                stopDay = today.get(Calendar.DAY_OF_MONTH);
                                stopMonth = selectedMonth;
                                stopYear = selectedYear;
                                setDateTimeButton(startYear, startMonth, startDay, startHour, startMinute, buttonStartDate, buttonStartTime);
                                setDateTimeButton(stopYear, stopMonth, stopDay, stopHour, stopMinute, buttonStopDate, buttonStopTime);
                                setBulanButton(startMonth, startYear, bulanButton);
                                calculateJumlah();
                                if (inputBatch.getText().length() > 0) {
                                    setInitData(startMonth, startYear, Integer.parseInt(inputBatch.getText().toString()));
                                }
                                getLastBatch(startYear, startMonth);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(2018)
                        .setMaxYear(2050)
                        .setTitle("Pilih bulan dan tahun")
                        .setActivatedMonth(startMonth)
                        .setActivatedYear(startYear)
                        .build()
                        .show();
            }
        });

        inputBatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    setInitData(startMonth, startYear, Integer.parseInt(charSequence.toString()));
                } else {
                    clearData();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setInitData(int initMonth, int initYear, int initBatch) {
        SharedPreferences preferences = InputSarfasActivity.this.getSharedPreferences(
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
        Call<TransferPipeline> call = operationClient.getTwu(
                String.valueOf(initYear),
                String.valueOf(initMonth + 1),
                String.valueOf(initBatch)
        );
        call.enqueue(new Callback<TransferPipeline>() {
            @Override
            public void onResponse(Call<TransferPipeline> call, Response<TransferPipeline> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    isUpdate = true;
                    setInitInput(response.body());
                }
            }

            @Override
            public void onFailure(Call<TransferPipeline> call, Throwable t) {
                Log.w("error", t.getMessage());
                isUpdate = false;
                id = "";
                clearData();
            }
        });
    }

    private void clearData() {
        inputQuantity.setText("");
        /*Calendar cal = Calendar.getInstance();
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
        calculateJumlah();*/

        jumlah = String.format(Locale.getDefault(),"%02d:%02d", 0, 0);
        textJumlah.setText(String.valueOf("Jumlah: " + jumlah));
    }

    private void setInitInput(TransferPipeline pipeline) {
        Log.w("init data", new Gson().toJson(pipeline));

        id = pipeline.getId();
        inputQuantity.setText(String.valueOf(pipeline.getQuantity()));
        jumlah = pipeline.getJumlah().substring(0, pipeline.getJumlah().length() - 3);
        textJumlah.setText(String.valueOf("Jumlah: " + jumlah));
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.operasi_product, android.R.layout.simple_spinner_item);
        spinner.setSelection(adapter.getPosition(pipeline.getFuel()));
        SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date startDate, stopDate;
        Calendar cal = Calendar.getInstance();
        try {
            startDate = parseFormat.parse(pipeline.getStart());
            stopDate = parseFormat.parse(pipeline.getStop());
            cal.setTimeInMillis(startDate.getTime());
            startYear = cal.get(Calendar.YEAR);
            startMonth = cal.get(Calendar.MONTH);
            startDay = cal.get(Calendar.DAY_OF_MONTH);
            startHour = cal.get(Calendar.HOUR_OF_DAY);
            startMinute = cal.get(Calendar.MINUTE);
            cal.setTimeInMillis(stopDate.getTime());
            stopYear = cal.get(Calendar.YEAR);
            stopMonth = cal.get(Calendar.MONTH);
            stopDay = cal.get(Calendar.DAY_OF_MONTH);
            stopHour = cal.get(Calendar.HOUR_OF_DAY);
            stopMinute = cal.get(Calendar.MINUTE);
            setDateTimeButton(startYear, startMonth, startDay, startHour, startMinute, buttonStartDate, buttonStartTime);
            setDateTimeButton(stopYear, stopMonth, stopDay, stopHour, stopMinute, buttonStopDate, buttonStopTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setBulanButton(int month, int year, TextView bulanButton) {
        SimpleDateFormat format = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        bulanButton.setText(format.format(new Date(cal.getTimeInMillis())));
    }

    private void handleSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.operasi_product, android.R.layout.simple_spinner_item);
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
                        getLastBatch(startYear, startMonth);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        InputSarfasActivity.this,
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
                        InputSarfasActivity.this,
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
                        InputSarfasActivity.this,
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
                        InputSarfasActivity.this,
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

        jumlah = String.format(Locale.getDefault(),"%02d:%02d", hour, minute);
        textJumlah.setText(String.valueOf("Jumlah: " + jumlah));
    }
}
