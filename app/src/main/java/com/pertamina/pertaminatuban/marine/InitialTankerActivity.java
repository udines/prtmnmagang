package com.pertamina.pertaminatuban.marine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.marine.input.InputInitialTankerActivity;
import com.pertamina.pertaminatuban.marine.input.PilihTankerActivity;
import com.pertamina.pertaminatuban.marine.models.InitialTanker;
import com.pertamina.pertaminatuban.marine.models.MarineTable;
import com.pertamina.pertaminatuban.marine.utils.InitialTankerAdapter;
import com.pertamina.pertaminatuban.service.UserClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.text.DateFormatSymbols;
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

public class InitialTankerActivity extends AppCompatActivity {

    private int year, month, day;
    private TextView dateText;
    private RecyclerView recyclerView;
    private InitialTankerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_tanker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateText = findViewById(R.id.initial_tanker_text_date);
        recyclerView = findViewById(R.id.initial_tanker_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        setDateButton(month, year);
        getMarineData(month, year);

        handleDateButton();
        handleInputButton();
        handleSpinner();
    }

    private void handleSpinner() {
        final Spinner spinner = findViewById(R.id.initial_tanker_no_bill_spinner);

        ArrayList<String> noBill = getIntent().getStringArrayListExtra("noBill");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, noBill
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.white));

                //update ui sesuai dengan no bill
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setDateButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        dateText.setText(text);
    }

    private void handleDateButton() {
        LinearLayout dateButton = findViewById(R.id.initial_tanker_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        InitialTankerActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(month, year);

                                //get data sesuai dengan bulan dan tahun
                                getMarineData(month, year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR) + 1)
                        .setActivatedMonth(month)
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void getMarineData(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        String bulan = format.format(new Date(cal.getTimeInMillis()));

        Log.w("get data bulan", bulan);

        SharedPreferences preferences = InitialTankerActivity.this.getSharedPreferences(
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
        Call<ArrayList<InitialTanker>> call = userClient.getInitialTanker(bulan);

        Log.w("call", "start enqueueing");
        call.enqueue(new Callback<ArrayList<InitialTanker>>() {
            @Override
            public void onResponse(Call<ArrayList<InitialTanker>> call, Response<ArrayList<InitialTanker>> response) {
                Log.w("code", String.valueOf(response.code()));
                recyclerView.setVisibility(View.VISIBLE);
                if (response.code() == 200) {
                    Log.w("msg", response.message());
                    Log.w("data", new Gson().toJson(response.body()));
                    checkData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<InitialTanker>> call, Throwable t) {
                Log.e("error", t.getMessage());
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(InitialTankerActivity.this, "Tidak ditemukan data", Toast.LENGTH_SHORT).show();
            }
        });

        Call<Object> experimentCall = userClient.getInitialTankerExperiment(bulan);
        experimentCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.code() == 200) {
                    Log.w("data", new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    private void checkData(ArrayList<InitialTanker> tankers) {
        if (tankers != null && tankers.size() > 0) {
            populateData(tankers);
        } else {
            Log.w("list size", "0");
        }
    }

    private void populateData(ArrayList<InitialTanker> tankers) {
        adapter = new InitialTankerAdapter(tankers);
        recyclerView.setAdapter(adapter);
    }

    private void handleInputButton() {
        Button button = findViewById(R.id.initial_tanker_input);

        SharedPreferences preferences = this.getSharedPreferences(
                "login",
                Context.MODE_PRIVATE
        );
        String role = preferences.getString("userRole", "none");
        if (role.equals("marine") || role.equals("admin")) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputIntent = new Intent(getApplicationContext(), InputInitialTankerActivity.class);
                inputIntent.putExtra("sourceActivity", "InitialTanker");
                inputIntent.putExtra("month", month);
                inputIntent.putExtra("year", year);
                inputIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inputIntent);
            }
        });
    }
}
