package com.pertamina.pertaminatuban.distribusi;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.service.UserClient;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
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

public class InputOpersActivity extends AppCompatActivity {
    
    private boolean minTimeSet, maxTimeSet, dateSet;
    private EditText inputJumlah;
    private Button tanggalButton, minTimeButton, maxTimeButton, kirim;
    private int year, month, day, hour, min;
    private int minHour, minMins;
    private int maxHour, maxMins;
    private boolean isUpdate;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_opers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        /*inisialisasi view*/
        inputJumlah = findViewById(R.id.input_opers_jumlah_kl);
        tanggalButton = findViewById(R.id.input_opers_tanggal);
        minTimeButton = findViewById(R.id.input_opers_min_jam);
        maxTimeButton = findViewById(R.id.input_opers_max_jam);
        kirim = findViewById(R.id.input_opers_kirim);
        progressBar = findViewById(R.id.input_opers_progress);

        isUpdate = false;
        
        /*inisialisasi tanggal*/
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        
        /*handle date button dan time button*/
        handleDateButton();
        handleTimeButton();
        
        /*get data then send data if valid*/
        getOpersData();
    }

    private void handleTimeButton() {
        minTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        minTimeSet = true;
                        minHour = i;
                        minMins = i1;
                        setTimeButton(minTimeButton, minHour, minMins);
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(
                        InputOpersActivity.this,
                        listener,
                        hour,
                        min,
                        true
                );
                dialog.show();
            }
        });
        maxTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        maxTimeSet = true;
                        maxHour = i;
                        maxMins = i1;
                        setTimeButton(maxTimeButton, maxHour, maxMins);
                    }
                };
                TimePickerDialog dialog = new TimePickerDialog(
                        InputOpersActivity.this,
                        listener,
                        hour,
                        min,
                        true
                );
                dialog.show();
            }
        });
    }

    private void setTimeButton(Button button, int hour, int min) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, min);
        Date date = new Date();
        date.setTime(calendar.getTimeInMillis());
        button.setText(String.valueOf(format.format(date) + ":00"));
    }

    private void getOpersData() {
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDateSet() && dataAda() && isMinTimeSet() && isMaxTimeSet()) {
                    Calendar calendarMin = Calendar.getInstance();
                    int jumlah = Integer.parseInt(inputJumlah.getText().toString());
                    calendarMin.set(year, month, day, minHour, minMins);
                    Time minTime = new Time(calendarMin.getTimeInMillis());
                    Calendar calendarMax = Calendar.getInstance();
                    calendarMax.set(year, month, day, maxHour, maxMins);
                    Time maxTime = new Time(calendarMax.getTimeInMillis());
                    Time diffTime = new Time(calendarMax.getTimeInMillis() - calendarMin.getTimeInMillis());
                    java.sql.Date date = new java.sql.Date(calendarMin.getTimeInMillis());
                    Opers opers = new Opers(
                            date.toString(),
                            jumlah,
                            minTime.toString(),
                            maxTime.toString()
                    );

                    if (isUpdate) {
                        sendUpdateRequest(opers);
                    } else {
                        sendPostRequest(opers);
                    }
                }
            }
        });
    }

    private void sendUpdateRequest(Opers opers) {
        Log.w("update", "send update request");
        kirim.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = InputOpersActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(opers);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);
        Call<Object> call = userClient.updateOpersTanggal(opers);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.w("response ", response.body().toString());
                    Toast.makeText(InputOpersActivity.this, "Data berhasil diperbarui", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.e("error", t.getMessage());
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private boolean isMinTimeSet() {
        if (!minTimeSet) {
            Toast.makeText(this, "Pilih jam", Toast.LENGTH_SHORT).show();
        }
        return minTimeSet;
    }

    private boolean isDateSet() {
        if (!dateSet) {
            Toast.makeText(this, "Pilih tanggal", Toast.LENGTH_SHORT).show();
        }
        return dateSet;
    }
    
    private boolean isMaxTimeSet() {
        if (!maxTimeSet) {
            Toast.makeText(this, "Pilih jam", Toast.LENGTH_SHORT).show();
        }
        return maxTimeSet;
    }

    private boolean dataAda() {
        boolean ada = false;
        if (inputJumlah.getText().toString().isEmpty()) {
            Toast.makeText(this, "Masukkan jumlah KL", Toast.LENGTH_SHORT).show();
        } else {
            ada = true;
        }
        return ada;
    }

    private void handleDateButton() {
        tanggalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        dateSet = true;
                        setDateButton(year, month, day);
                        getInitOpers();
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        InputOpersActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });
    }

    private void getInitOpers() {
        clearInput();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());
        String tanggalSekarang;
        tanggalSekarang = date.toString();
        Log.w("tanggal", tanggalSekarang);

        SharedPreferences preferences = InputOpersActivity.this.getSharedPreferences(
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
        UserClient userClient = retrofit.create(UserClient.class);
        Call<Opers> call = userClient.getOpersTanggal(tanggalSekarang);
        call.enqueue(new Callback<Opers>() {
            @Override
            public void onResponse(Call<Opers> call, Response<Opers> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("body", new Gson().toJson(response.body()));
                    isUpdate = true;

                    Opers object = response.body();
                    inputJumlah.setText(String.valueOf(object.getJumlahKeluar()));
                    minTimeButton.setText(String.valueOf(object.getMinJamKeluar()));
                    maxTimeButton.setText(String.valueOf(object.getMaxJamKeluar()));

                    SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    Calendar minCal = Calendar.getInstance();
                    Calendar maxCal = Calendar.getInstance();
                    Date minDate, maxDate;
                    try {
                        minDate = parseFormat.parse(object.getMinJamKeluar());
                        maxDate = parseFormat.parse(object.getMaxJamKeluar());

                        minCal.setTimeInMillis(minDate.getTime());
                        maxCal.setTimeInMillis(maxDate.getTime());

                        minMins = minCal.get(Calendar.MINUTE);
                        minHour = minCal.get(Calendar.HOUR_OF_DAY);
                        minTimeSet = true;

                        maxMins = maxCal.get(Calendar.MINUTE);
                        maxHour = maxCal.get(Calendar.HOUR_OF_DAY);
                        maxTimeSet = true;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                Log.w("is update", String.valueOf(isUpdate));
            }

            @Override
            public void onFailure(Call<Opers> call, Throwable t) {
                Log.e("error", t.getMessage());
                isUpdate = false;
            }
        });
    }

    private void clearInput() {
        inputJumlah.setText("");
        minTimeSet = false;
        maxTimeSet = false;
        minTimeButton.setText("pilih jam");
        maxTimeButton.setText("pilih jam");
    }

    private void setDateButton(int year, int month, int day) {
        tanggalButton.setText(String.valueOf(year + " - " + month + 1 + " - " + day));
    }

    private void sendPostRequest(Opers opers) {
        kirim.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences preferences = InputOpersActivity.this.getSharedPreferences(
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

        String json = new Gson().toJson(opers);
        Log.w("json", json);

        OkHttpClient client = httpClient.build();

        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);

        Call<Object> call = userClient.postOpers(opers);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.w("code", String.valueOf(response.code()));
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    Log.w("response ", response.body().toString());
                    Toast.makeText(InputOpersActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.w("error", t.getMessage());
                kirim.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
