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
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.page.MatbalPage;
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

public class MatbalActivity extends AppCompatActivity {

    private TextView tanggal;
    private int month;
    private int year;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView emptyText;

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
        viewPager = findViewById(R.id.matbal_viewpager);
        tabLayout = findViewById(R.id.matbal_tab);
        emptyText = findViewById(R.id.matbal_empty_text);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        setDateButton(month, year);

        /*get data. jika data tidak ada maka tampilkan tulisan jika kosong. jika ada
        * maka tampilkan tab beserta isinya*/
        getMatbal(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
    }

    private void cekData(ArrayList<Matbal> matbals) {

        /*cek apakah data ada atau tidak*/
        if (matbals != null && matbals.size() > 0) {

            Log.d("data", "data matbal ada");
            /*data ada maka tampilkan tab dan isinya*/
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            populateTabs(matbals);
        } else {

            Log.d("data", "data matbal tidak ada");
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
                        MatbalActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                getMatbal(month);
                                setDateButton(month, year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

                Toast.makeText(MatbalActivity.this, String.valueOf(month), Toast.LENGTH_SHORT).show();

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

    private void populateTabs(ArrayList<Matbal> matbals) {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<ArrayList<Matbal>> kumpulanFragment = new ArrayList<>();

        titles.add(matbals.get(0).getFuel());
        for (int i = 0; i < matbals.size(); i++) {
            boolean ada = false;
            for (int j = 0; j < titles.size(); j++) {
                if (matbals.get(i).getFuel().equals(titles.get(j))) {
                    ada = true;
                    break;
                } else {
                    ada = false;
                }
            }
            if (!ada) {
                titles.add(matbals.get(i).getFuel());
            }
        }

        Log.d("tabs ada", String.valueOf(titles.size()));

        for (int i = 0; i < titles.size(); i++) {
            kumpulanFragment.add(new ArrayList<Matbal>());
            for (int j = 0; j < matbals.size(); j++) {
                if (titles.get(i).equals(matbals.get(j).getFuel())) {
                    kumpulanFragment.get(i).add(matbals.get(j));
                }
            }
            MatbalPage page = new MatbalPage();
            page.setMatbals(kumpulanFragment.get(i));
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

    private void getMatbal(int bulan) {
        Log.w("GET ", "start getting matbal bulan " + bulan);
        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<ArrayList<Matbal>> call = client.getMatbal(bulan);

        call.enqueue(new Callback<ArrayList<Matbal>>() {
            @Override
            public void onResponse(Call<ArrayList<Matbal>> call, Response<ArrayList<Matbal>> response) {
                Log.w("code", String.valueOf(response.code()));
                Log.w("size", String.valueOf(response.body().size()));
                if (response.code() == 200 && response.body() != null) {
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Matbal>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });
    }
}
