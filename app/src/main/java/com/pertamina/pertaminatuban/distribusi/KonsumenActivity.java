package com.pertamina.pertaminatuban.distribusi;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Konsumen;
import com.pertamina.pertaminatuban.distribusi.page.KonsumenPage;
import com.pertamina.pertaminatuban.service.UserClient;
import com.pertamina.pertaminatuban.utils.ViewPagerAdapter;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KonsumenActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView emptyText;
    private TextView tanggal;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konsumen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*inisialisasi view yang digunakan lebih dari satu fungsi*/
        tanggal = findViewById(R.id.konsumen_tanggal);
        viewPager = findViewById(R.id.konsumen_viewpager);
        tabLayout = findViewById(R.id.konsumen_tab);
        emptyText = findViewById(R.id.konsumen_empty_text);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        setDateButton(month, year);

        /*get data. jika data tidak ada maka tampilkan tulisan jika kosong. jika ada
        * maka tampilkan tab beserta isinya*/
        getKonsumen(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
    }

    private void cekData(ArrayList<Konsumen> konsumens) {

        /*cek apakah data ada atau tidak*/
        if (konsumens != null && konsumens.size() > 0) {

            Log.d("data", "data konsumen ada");
            /*data ada maka tampilkan tab dan isinya*/
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            populateTabs(konsumens);
        } else {

            Log.d("data", "data konsumen tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);

        }
    }

    private void handleDate() {
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        KonsumenActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                getKonsumen(month);
                                setDateButton(month, year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                Toast.makeText(KonsumenActivity.this, String.valueOf(month), Toast.LENGTH_SHORT).show();

                builder.setMinYear(1970)
                        .setMaxYear(today.get(Calendar.YEAR))
                        .setTitle("Pilih bulan dan tahun")
                        .setActivatedMonth(month)
                        .setActivatedYear(year)
                        .build()
                        .show();
            }
        });
    }

    private void setDateButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        tanggal.setText(text);
    }

    private void populateTabs(ArrayList<Konsumen> konsumens) {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<ArrayList<Konsumen>> kumpulanFragment = new ArrayList<>();

        titles.add(konsumens.get(0).getFuel());
        for (int i = 0; i < konsumens.size(); i++) {
            boolean ada = false;
            for (int j = 0; j < titles.size(); j++) {
                if (konsumens.get(i).getFuel().equals(titles.get(j))) {
                    ada = true;
                    break;
                } else {
                    ada = false;
                }
            }
            if (!ada) {
                titles.add(konsumens.get(i).getFuel());
            }
        }

        Log.d("tabs ada", String.valueOf(titles.size()));

        for (int i = 0; i < titles.size(); i++) {
            kumpulanFragment.add(new ArrayList<Konsumen>());
            for (int j = 0; j < konsumens.size(); j++) {
                if (titles.get(i).equals(konsumens.get(j).getFuel())) {
                    kumpulanFragment.get(i).add(konsumens.get(j));
                }
            }
            KonsumenPage page = new KonsumenPage();
            page.setKonsumens(kumpulanFragment.get(i));
            page.setFuel(titles.get(i));
            fragments.add(page);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                fragments,
                titles
        );

        viewPager.setAdapter(adapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void getKonsumen(int bulan) {
        Log.w("GET ", "start getting konsumen bulan " + bulan);
        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<ArrayList<Konsumen>> call = client.getKonsumen(bulan);

        call.enqueue(new Callback<ArrayList<Konsumen>>() {
            @Override
            public void onResponse(Call<ArrayList<Konsumen>> call, Response<ArrayList<Konsumen>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("size", String.valueOf(response.body().size()));
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Konsumen>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });

        ArrayList<Konsumen> konsumenJan = new ArrayList<>();
        ArrayList<Konsumen> konsumenFeb = new ArrayList<>();
    }
}
