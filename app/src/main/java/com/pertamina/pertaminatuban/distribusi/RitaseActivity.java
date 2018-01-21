package com.pertamina.pertaminatuban.distribusi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Ritase;
import com.pertamina.pertaminatuban.distribusi.page.RitasePage;
import com.pertamina.pertaminatuban.service.UserClient;
import com.pertamina.pertaminatuban.utils.ViewPagerAdapter;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.io.IOException;
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

public class RitaseActivity extends AppCompatActivity {

    private TextView tanggal;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView emptyText;
    private int month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ritase);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*inisialisasi view yang digunakan lebih dari satu fungsi*/
        tanggal = findViewById(R.id.ritase_tanggal);
        viewPager = findViewById(R.id.ritase_viewpager);
        tabLayout = findViewById(R.id.ritase_tab);
        emptyText = findViewById(R.id.ritase_empty_text);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        setDateButton(month, year);

        /*get data. jika data tidak ada maka tampilkan tulisan jika kosong. jika ada
        * maka tampilkan tab beserta isinya*/
        getRitase(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void handleDate() {
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        RitaseActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                getRitase(month);
                                setDateButton(month, year);
                            }
                        },
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH)
                );

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

    private void cekData(ArrayList<Ritase> ritases) {

        /*cek apakah data ada atau tidak*/
        if (ritases != null && ritases.size() > 0) {

            Log.d("data", "data ritase ada");
            /*data ada maka tampilkan tab dan isinya*/
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            populateTabs(ritases);
        } else {

            Log.d("data", "data ritase tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);

        }
    }

    private void populateTabs(ArrayList<Ritase> ritases) {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<ArrayList<Ritase>> kumpulanFragment = new ArrayList<>();

        titles.add(ritases.get(0).getNomobil());
        for (int i = 0; i < ritases.size(); i++) {
            boolean ada = false;
            for (int j = 0; j < titles.size(); j++) {
                if (ritases.get(i).getNomobil().equals(titles.get(j))) {
                    ada = true;
                    break;
                } else {
                    ada = false;
                }
            }
            if (!ada) {
                titles.add(ritases.get(i).getNomobil());
            }
        }

        Log.d("tabs ada", String.valueOf(titles.size()));

        for (int i = 0; i < titles.size(); i++) {
            kumpulanFragment.add(new ArrayList<Ritase>());
            for (int j = 0; j < ritases.size(); j++) {
                if (titles.get(i).equals(ritases.get(j).getNomobil())) {
                    kumpulanFragment.get(i).add(ritases.get(j));
                }
            }
            RitasePage page = new RitasePage();
            page.setRitases(kumpulanFragment.get(i));
//            page.setFuel(titles.get(i));
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

    private void getRitase(int month) {

        SharedPreferences preferences = RitaseActivity.this.getSharedPreferences(
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
        Call<ArrayList<Ritase>> call = userClient.getRitase(month);

        call.enqueue(new Callback<ArrayList<Ritase>>() {
            @Override
            public void onResponse(Call<ArrayList<Ritase>> call, Response<ArrayList<Ritase>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("size", String.valueOf(response.body().size()));
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Ritase>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });

    }

}
