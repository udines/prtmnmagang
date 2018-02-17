package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.tables.MatbalTableAdapter;
import com.pertamina.pertaminatuban.service.UserClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatbalActivity extends AppCompatActivity {

    private TextView tanggal, bulan, tahun;
    private LinearLayout tanggalButton, bulanButton, tahunButton, container;
    private int month;
    private int year;
    private int day;
    private TextView pertamax, pertalite, premium, solar, biosolar, bioflame, grandTotal;
    private TextView emptyText;
    private RecyclerView recyclerview;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matbal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*inisialisasi view yang digunakan lebih dari satu fungsi*/
        tanggal = findViewById(R.id.matbal_tanggal);
        bulan = findViewById(R.id.matbal_bulan);
        tahun = findViewById(R.id.matbal_tahun);
        tanggalButton = findViewById(R.id.matbal_tanggal_button);
        bulanButton = findViewById(R.id.matbal_bulan_button);
        tahunButton = findViewById(R.id.matbal_tahun_button);
        container = findViewById(R.id.matbal_container);
        grandTotal = findViewById(R.id.matbal_total);
        emptyText = findViewById(R.id.matbal_empty_text);
        recyclerview = findViewById(R.id.matbal_recyclerview);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDateButton(day, month, year);
        setMonthButton(month, year);
        setYearButton(year);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handlePeriod();
    }

    private void setDateButton(int day, int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = String.valueOf(day) + " " + symbols.getMonths()[month] + " " + String.valueOf(year);
        tanggal.setText(text);
    }

    private void setYearButton(int year) {
        tahun.setText(String.valueOf(year));
    }

    private void setMonthButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        bulan.setText(text);
    }

    private void handlePeriod() {
        spinner = findViewById(R.id.matbal_periode_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pilihan_periode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int index = adapterView.getSelectedItemPosition();
                ((TextView) spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.white));
                switch (index) {
                    case 0 :
                        tanggalButton.setVisibility(View.VISIBLE);
                        bulanButton.setVisibility(View.GONE);
                        tahunButton.setVisibility(View.GONE);
                        break;
                    case 1:
                        bulanButton.setVisibility(View.VISIBLE);
                        tanggalButton.setVisibility(View.GONE);
                        tahunButton.setVisibility(View.GONE);
                        break;
                    default:
                        tahunButton.setVisibility(View.VISIBLE);
                        tanggalButton.setVisibility(View.GONE);
                        bulanButton.setVisibility(View.GONE);
                        break;
                }

                //update ui karena ada perubahan di jenis periode
                updateUi(day, month, year, index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //handle klik ketika periode diset sebagai harian
        tanggalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        year = i;
                        month = i1;
                        day = i2;
                        setDateButton(day, month, year);
                        setMonthButton(month, year);
                        setYearButton(year);
                        updateUi(day, month, year, 0);
                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(
                        MatbalActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });

        //handle klik jika periode diset bulanan
        bulanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        MatbalActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(day, month, year);
                                setMonthButton(month, year);
                                setYearButton(year);
                                updateUi(day, month, year, 1);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .setActivatedYear(year)
                        .setActivatedMonth(month)
                        .build()
                        .show();
            }
        });

        //handle klik jika periode diset tahunan
        tahunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        MatbalActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(day, month, year);
                                setMonthButton(month, year);
                                setYearButton(year);
                                updateUi(day, month, year, 2);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .showYearOnly()
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void updateUi(int day, int month, int year, int type) {
        //type adalah 0 untuk daily, 1 untuk monthly, 2 untuk yearly

        switch (type) {
            case 0:
                getMatbalHari(day, month, year);
                break;
            case 1:
                getMatbalBulan(month, year);
                break;
            case 2:
                getMatbalTahun(year);
                break;
        }
    }

    private void getMatbalTahun(int year) {

    }

    private void getMatbalBulan(int month, int year) {

    }

    private void getMatbalHari(int day, int month, int year) {

    }

    /*private void cekData(ArrayList<Matbal> matbals) {
        Log.w("posisi", "cek data");
        *//*cek apakah data ada atau tidak*//*
        if (matbals != null && matbals.size() > 0) {

            Log.w("data", "data matbal ada");
            *//*data ada maka tampilkan tab dan isinya*//*
            populateData(matbals);
        } else {
            container.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
            Log.w("data", "data matbal tidak ada");
            *//*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*//*

        }
    }

    private void populateData(ArrayList<Matbal> matbals) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        java.util.Date date = new Date(calendar.getTimeInMillis());
        Log.w("date", date.toString());
        String today = date.toString();

        ArrayList<Matbal> matbalToday = new ArrayList<>();
        ArrayList<Matbal> matbalLast = new ArrayList<>();
        float totalToday = 0;
        float totalLast = 0;
        for (int i = 0; i < matbals.size(); i++) {
            if (matbals.get(i).getDate().equals(today)) {
                matbalToday.add(matbals.get(i));
                totalToday = totalToday + matbals.get(i).getNilai();
            }
        }
        grandTotal.setText(String.valueOf(totalToday + " KL"));
        Log.w("total", String.valueOf(totalToday));

        if (totalToday == 0) {
            container.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            container.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerview.setAdapter(new MatbalTableAdapter(
                    matbalToday,
                    getApplicationContext()
            ));
        }
    }

    private void getMatbal(int bulan) {

        SharedPreferences preferences = MatbalActivity.this.getSharedPreferences(
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

        Log.w("GET ", "start getting matbal bulan " + bulan);
        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client);

        Retrofit retrofit = builder.build();
        UserClient userClient = retrofit.create(UserClient.class);
        Call<ArrayList<Matbal>> call = userClient.getMatbal(bulan + 1);

        call.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("size", String.valueOf(response.body().size()));
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });
    }*/
}
