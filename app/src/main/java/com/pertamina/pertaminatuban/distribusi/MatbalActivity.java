package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import com.google.gson.Gson;
import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.tables.MatbalTableAdapter;
import com.pertamina.pertaminatuban.service.UserClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    private LinearLayout tanggalButton, bulanButton, tahunButton;
    private int month;
    private int year;
    private int day;
    private RecyclerView recyclerview;
    private Spinner spinner;
    private ArrayList<Matbal> matbalSekarang, matbalLalu;

    private TextView textLalu, textNow, nilaiLalu, nilaiNow, nilaiDiff;

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
        recyclerview = findViewById(R.id.matbal_recyclerview);

        textLalu = findViewById(R.id.matbal_total_text_last);
        textNow = findViewById(R.id.matbal_total_text_now);
        nilaiLalu = findViewById(R.id.matbal_total_last);
        nilaiNow = findViewById(R.id.matbal_total);
        nilaiDiff = findViewById(R.id.matbal_total_difference);

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
        matbalLalu = new ArrayList<>();
        matbalSekarang = new ArrayList<>();

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
        Call<ArrayList<Matbal>> call = userClient.getMatbalTahun(String.valueOf(year));
        Call<ArrayList<Matbal>> callTahunLalu = userClient.getMatbalTahun(String.valueOf(year - 1));

        call.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code sekarang", String.valueOf(response.code()));
                Log.w("data sekarang", new Gson().toJson(response.body()));
                if (response.code() == 200) {
                    matbalSekarang = response.body();
                    if (matbalSekarang != null && matbalLalu != null ) {
                        cekData(matbalSekarang, matbalLalu, "year");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("error sekarang", t.getMessage());
            }
        });

        callTahunLalu.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code lalu", String.valueOf(response.code()));
                Log.w("data lalu", new Gson().toJson(response.body()));
                if (response.code() == 200) {
                    matbalLalu = response.body();
                    if (matbalSekarang != null && matbalLalu != null ) {
                        cekData(matbalSekarang, matbalLalu, "year");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("error lalu", t.getMessage());
            }
        });
    }

    private void getMatbalBulan(int month, int year) {
        matbalLalu = new ArrayList<>();
        matbalSekarang = new ArrayList<>();

        int yearLalu, monthLalu;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        cal.add(Calendar.MONTH, -1);
        yearLalu = cal.get(Calendar.YEAR);
        monthLalu = cal.get(Calendar.MONTH);
        Log.w("year month", String.valueOf(yearLalu + ", " + monthLalu));

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
        Call<ArrayList<Matbal>> callBulanIni = userClient.getMatbalBulan(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        Call<ArrayList<Matbal>> callBulanLalu = userClient.getMatbalBulan(
                String.valueOf(yearLalu),
                String.valueOf(monthLalu + 1)
        );

        callBulanIni.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code sekarang", String.valueOf(response.code()));
                Log.w("data sekarang", new Gson().toJson(response.body()));
                if (response.code() == 200) {
                    matbalSekarang = response.body();
                    if (matbalSekarang != null && matbalLalu != null ) {
                        cekData(matbalSekarang, matbalLalu, "month");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("error sekarang", t.getMessage());
            }
        });

        callBulanLalu.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code lalu", String.valueOf(response.code()));
                Log.w("data lalu", new Gson().toJson(response.body()));
                if (response.code() == 200) {
                    matbalLalu = response.body();
                    if (matbalSekarang != null && matbalLalu != null ) {
                        cekData(matbalSekarang, matbalLalu, "month");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("error lalu", t.getMessage());
            }
        });
    }

    private void getMatbalHari(int day, int month, int year) {
        matbalLalu = new ArrayList<>();
        matbalSekarang = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = new Date(cal.getTimeInMillis());
        String tanggalSekarang, tanggalLalu;
        tanggalSekarang = date.toString();
        cal.add(Calendar.DAY_OF_MONTH, - 1);
        date.setTime(cal.getTimeInMillis());
        tanggalLalu = date.toString();

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

        Call<ArrayList<Matbal>> call = userClient.getMatbalHari(tanggalSekarang);
        Call<ArrayList<Matbal>> callTanggalLalu = userClient.getMatbalHari(tanggalLalu);

        call.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code sekarang", String.valueOf(response.code()));
                Log.w("data sekarang", new Gson().toJson(response.body()));
                if (response.code() == 200) {
                    matbalSekarang = response.body();
                    if (matbalSekarang != null && matbalLalu != null ) {
                        cekData(matbalSekarang, matbalLalu, "day");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("error sekarang", t.getMessage());
            }
        });

        callTanggalLalu.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code lalu", String.valueOf(response.code()));
                Log.w("data lalu", new Gson().toJson(response.body()));
                if (response.code() == 200) {
                    matbalLalu = response.body();
                    if (matbalSekarang != null && matbalLalu != null ) {
                        cekData(matbalSekarang, matbalLalu, "day");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("error lalu", t.getMessage());
            }
        });
    }

    private void cekData(ArrayList<Matbal> matbalSekarang, ArrayList<Matbal> matbalLalu, String periode) {
        if (matbalSekarang.size() > 0 && matbalLalu.size() > 0) {
            MatbalTableAdapter adapter = new MatbalTableAdapter(
                    matbalSekarang,
                    matbalLalu,
                    periode,
                    getApplicationContext(),
                    year,
                    month,
                    day
            );
            recyclerview.setNestedScrollingEnabled(false);
            recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerview.setAdapter(adapter);

            handleGrandTotal(periode, matbalLalu, matbalSekarang);
        }
    }

    private void handleGrandTotal(
            String periodeType,
            ArrayList<Matbal> matbalLalu,
            ArrayList<Matbal> matbalSekarang) {

        SimpleDateFormat dayFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        Calendar cal = Calendar.getInstance();

        switch (periodeType) {
            case "day":
                cal.set(year, month, day);
                Date date = new Date(cal.getTimeInMillis());
                textNow.setText(dayFormat.format(date));

                cal.add(Calendar.DAY_OF_MONTH, -1);
                date = new Date(cal.getTimeInMillis());
                textLalu.setText(dayFormat.format(date));
                break;
            case "month":
                cal.set(year, month, day);
                Date dateMonth = new Date(cal.getTimeInMillis());
                textNow.setText(monthFormat.format(dateMonth));

                cal.add(Calendar.MONTH, -1);
                dateMonth = new Date(cal.getTimeInMillis());
                textLalu.setText(monthFormat.format(dateMonth));
                break;
            case "year":
                cal.set(year, month, day);
                Date dateYear = new Date(cal.getTimeInMillis());
                textNow.setText(yearFormat.format(dateYear));

                cal.add(Calendar.YEAR, -1);
                dateYear = new Date(cal.getTimeInMillis());
                textLalu.setText(yearFormat.format(dateYear));
                break;
        }

        float totalNow = 0, totalLalu = 0;
        for (int i = 0; i < matbalLalu.size(); i++) {
            totalLalu = totalLalu + matbalLalu.get(i).getNilai();
        }
        for (int i = 0; i < matbalSekarang.size(); i++) {
            totalNow = totalNow + matbalSekarang.get(i).getNilai();
        }

        nilaiNow.setText(String.valueOf(totalNow + " KL"));
        nilaiLalu.setText(String.valueOf(totalLalu + " KL"));

        float difference = totalNow - totalLalu;
        float percentage = (difference / totalLalu) * 100;

        if (totalLalu == 0 && totalNow > 0) {
            percentage = 100;
        } else if (totalLalu == 0 && totalNow == 0) {
            percentage = 0;
        }

        nilaiDiff.setText(String.valueOf(difference + " KL (" + (int)percentage + "%)"));
        if (percentage < 0) {
            nilaiDiff.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.red_800));
        } else {
            nilaiDiff.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green_800));
        }
    }
}
