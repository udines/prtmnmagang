package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.service.UserClient;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormatSymbols;
import java.text.ParseException;
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

public class OpersActivity extends AppCompatActivity {

    private int month, year, day;
    private TextView tanggal, bulan, tahun;
    private TextView sum, minJam, maxJam, jamOps;
    private TextView jumlahMobil, dayaAngkut, tpHarian, ritase;
    private LinearLayout container;
    private Spinner spinnerPeriode;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opers);
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
        tanggal = findViewById(R.id.opers_tanggal);
        bulan = findViewById(R.id.opers_bulan);
        tahun = findViewById(R.id.opers_tahun);

        sum = findViewById(R.id.opers_jumlah_keluar);
        minJam = findViewById(R.id.opers_jam_keluar_min);
        maxJam = findViewById(R.id.opers_jam_keluar_max);
        jamOps = findViewById(R.id.opers_jam_operasional);

        jumlahMobil = findViewById(R.id.opers_jumlah_mobil);
        dayaAngkut = findViewById(R.id.opers_daya_angkut);
        tpHarian = findViewById(R.id.opers_tp_harian);
        ritase = findViewById(R.id.opers_ritase);

        container = findViewById(R.id.opers_container);
        spinnerPeriode = findViewById(R.id.opers_spinner_periode);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDateButton(day, month, year);
        updateUi(day, month, year, 0);

        handleSpinner();
        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
        handleNavButton();
    }

    private void handleNavButton() {
        Button next, prev;
        prev = findViewById(R.id.opers_prev_day);
        next = findViewById(R.id.opers_next_day);
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                cal.add(Calendar.DAY_OF_MONTH, -1);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
                day = cal.get(Calendar.DAY_OF_MONTH);
                setDateButton(day, month, year);
                updateUi(day, month, year, 0);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, day);
                cal.add(Calendar.DAY_OF_MONTH, 1);
                month = cal.get(Calendar.MONTH);
                year = cal.get(Calendar.YEAR);
                day = cal.get(Calendar.DAY_OF_MONTH);
                setDateButton(day, month, year);
                updateUi(day, month, year, 0);
            }
        });
    }

    private void handleSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pilihan_periode_tanggal_bulan, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeriode.setAdapter(adapter);
        spinnerPeriode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int indeks = adapterView.getSelectedItemPosition();
                ((TextView) spinnerPeriode.getSelectedView()).setTextColor(getResources().getColor(R.color.white));
                switch (i) {
                    case 0 :
                        tanggal.setVisibility(View.VISIBLE);
                        bulan.setVisibility(View.GONE);
                        tahun.setVisibility(View.GONE);
                        break;
                    case 1:
                        bulan.setVisibility(View.VISIBLE);
                        tanggal.setVisibility(View.GONE);
                        tahun.setVisibility(View.GONE);
                        break;
                    default:
                        tahun.setVisibility(View.VISIBLE);
                        tanggal.setVisibility(View.GONE);
                        bulan.setVisibility(View.GONE);
                        break;
                }

                index = indeks;
                //update ui karena ada perubahan di jenis periode
                updateUi(day, month, year, index);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void updateUi(int day, int month, int year, int index) {

        clearText();

        switch (index) {
            case 0:
                container.setVisibility(View.VISIBLE);
                getOpersHari(day, month, year);
                getRitaseHari(day, month, year);
                break;
            case 1:
                container.setVisibility(View.GONE);
                getOpersBulan(month, year);
                getRitaseBulan(month, year);
                break;
            /*case 2:
                getOpersTahun(year);
                getRitaseTahun(year);
                break;*/
        }
    }

    private void clearText() {
        sum.setText("");
        minJam.setText("");
        maxJam.setText("");
        jamOps.setText("");
        jumlahMobil.setText("");
        dayaAngkut.setText("");
        tpHarian.setText("");
        ritase.setText("");
    }

    private void setTextOpers(
            Opers opers) {
        sum.setText(String.valueOf(opers.getJumlahKeluar()));
        minJam.setText(opers.getMinJamKeluar());
        maxJam.setText(opers.getMaxJamKeluar());

        SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        java.util.Date minDate, maxDate;
        try {
            minDate = parseFormat.parse(opers.getMinJamKeluar());
            maxDate = parseFormat.parse(opers.getMaxJamKeluar());

            //diff in millis
            long diff = maxDate.getTime() - minDate.getTime();

            //diff in seconds
            diff = diff / 1000;

            //diff in minutes
            diff = diff / 60;

            long hour = diff / 60;
            long mins = diff % 60;

            jamOps.setText(String.valueOf(hour + "jam " + mins + "menit"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void setTextRitase(Ritase rit) {
        jumlahMobil.setText(String.valueOf(rit.getJumlahMobil()));
        dayaAngkut.setText(String.valueOf(rit.getDayaAngkut()));
        tpHarian.setText(String.valueOf(rit.getTpHarian()));
        ritase.setText(String.valueOf(rit.getRitase()));
    }

    private Opers getOpersMerged(ArrayList<Opers> operses) {
        int jumlahKL = 0;
        for (int i = 0; i < operses.size(); i++) {
            jumlahKL = jumlahKL + operses.get(i).getJumlahKeluar();
        }
        return new Opers(
                jumlahKL,
                "",
                "",
                ""
        );
    }

    private Ritase getRitaseMerged(ArrayList<Ritase> ritases) {
        int jumlahMobil = 0;
        int dayaAngkut = 0;
        double tpHarian = 0.0;
        double ritase = 0.0;
        for (int i = 0; i < ritases.size(); i++) {
            jumlahMobil = jumlahMobil + ritases.get(i).getJumlahMobil();
            dayaAngkut = dayaAngkut + ritases.get(i).getDayaAngkut();
            tpHarian = tpHarian + ritases.get(i).getTpHarian();
            ritase = ritase + ritases.get(i).getRitase();
        }
        return new Ritase(
                jumlahMobil,
                dayaAngkut,
                tpHarian,
                ritase
        );
    }

    private void getRitaseTahun(int year) {
        Call<ArrayList<Ritase>> call = getUserClient().getRitaseTahun(String.valueOf(year));
        call.enqueue(new Callback<ArrayList<Ritase>>() {
            @Override
            public void onResponse(Call<ArrayList<Ritase>> call, Response<ArrayList<Ritase>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setTextRitase(getRitaseMerged(response.body()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Ritase>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void getOpersTahun(int year) {
        Call<ArrayList<Opers>> call = getUserClient().getOpersTahun(String.valueOf(year));
        call.enqueue(new Callback<ArrayList<Opers>>() {
            @Override
            public void onResponse(Call<ArrayList<Opers>> call, Response<ArrayList<Opers>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setTextOpers(getOpersMerged(response.body()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Opers>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void getRitaseBulan(int month, int year) {
        Call<ArrayList<Ritase>> call = getUserClient().getRitaseBulan(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<ArrayList<Ritase>>() {
            @Override
            public void onResponse(Call<ArrayList<Ritase>> call, Response<ArrayList<Ritase>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setTextRitase(getRitaseMerged(response.body()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Ritase>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void getOpersBulan(int month, int year) {
        Call<ArrayList<Opers>> call = getUserClient().getOpersBulan(
                String.valueOf(year),
                String.valueOf(month + 1)
        );
        call.enqueue(new Callback<ArrayList<Opers>>() {
            @Override
            public void onResponse(Call<ArrayList<Opers>> call, Response<ArrayList<Opers>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setTextOpers(getOpersMerged(response.body()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Opers>> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void getRitaseHari(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = new Date(cal.getTimeInMillis());
        String tanggal = date.toString();
        Call<Ritase> call = getUserClient().getRitaseTanggal(tanggal);
        call.enqueue(new Callback<Ritase>() {
            @Override
            public void onResponse(Call<Ritase> call, Response<Ritase> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setTextRitase(response.body());
                }
            }

            @Override
            public void onFailure(Call<Ritase> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void getOpersHari(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Date date = new Date(cal.getTimeInMillis());
        String tanggal = date.toString();
        Call<Opers> call = getUserClient().getOpersTanggal(tanggal);
        call.enqueue(new Callback<Opers>() {
            @Override
            public void onResponse(Call<Opers> call, Response<Opers> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200) {
                    setTextOpers(response.body());
                }
            }

            @Override
            public void onFailure(Call<Opers> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void handleDate() {
        tanggal.setOnClickListener(new View.OnClickListener() {
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
                        updateUi(day, month, year, index);
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(
                        OpersActivity.this,
                        listener,
                        year,
                        month,
                        day
                );
                dialog.show();
            }
        });

        bulan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        OpersActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(day, month, year);
                                setMonthButton(month, year);
                                setYearButton(year);
                                updateUi(day, month, year, index);
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

        tahun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        OpersActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                setDateButton(day, month, year);
                                setMonthButton(month, year);
                                setYearButton(year);
                                updateUi(day, month, year, index);
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

    private void setYearButton(int year) {
        tahun.setText(String.valueOf(year));
    }

    private void setMonthButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        bulan.setText(text);
    }

    private void setDateButton(int day, int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = String.valueOf(day) + " " + symbols.getMonths()[month] + " " + String.valueOf(year);
        tanggal.setText(text);
    }

    private UserClient getUserClient() {
        SharedPreferences preferences = OpersActivity.this.getSharedPreferences(
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
        return retrofit.create(UserClient.class);
    }
}
