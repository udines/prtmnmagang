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
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.distribusi.models.Wilayah;
import com.pertamina.pertaminatuban.distribusi.page.WilayahPage;
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

public class WilayahActivity extends AppCompatActivity {

    private TextView tanggal;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView emptyText;
    private int month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wilayah);
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
        tanggal = findViewById(R.id.wilayah_tanggal);
        viewPager = findViewById(R.id.wilayah_viewpager);
        tabLayout = findViewById(R.id.wilayah_tab);
        emptyText = findViewById(R.id.wilayah_empty_text);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        setDateButton(month, year);

        /*get data. jika data tidak ada maka tampilkan tulisan jika kosong. jika ada
        * maka tampilkan tab beserta isinya*/
        getWilayah(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
    }

    private void handleDate() {
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar today = Calendar.getInstance();

                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(
                        WilayahActivity.this,
                        new MonthPickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(int selectedMonth, int selectedYear) {
                                month = selectedMonth;
                                year = selectedYear;
                                getWilayah(month);
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

    private void populateTabs(ArrayList<Wilayah> wilayahs) {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<ArrayList<Wilayah>> kumpulanFragment = new ArrayList<>();

        titles.add(wilayahs.get(0).getFuel());
        for (int i = 0; i < wilayahs.size(); i++) {
            boolean ada = false;
            for (int j = 0; j < titles.size(); j++) {
                if (wilayahs.get(i).getFuel().equals(titles.get(j))) {
                    ada = true;
                    break;
                } else {
                    ada = false;
                }
            }
            if (!ada) {
                titles.add(wilayahs.get(i).getFuel());
            }
        }

        Log.d("tabs ada", String.valueOf(titles.size()));

        for (int i = 0; i < titles.size(); i++) {
            kumpulanFragment.add(new ArrayList<Wilayah>());
            for (int j = 0; j < wilayahs.size(); j++) {
                if (titles.get(i).equals(wilayahs.get(j).getFuel())) {
                    kumpulanFragment.get(i).add(wilayahs.get(j));
                }
            }
            WilayahPage page = new WilayahPage();
            page.setWilayahs(kumpulanFragment.get(i));
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

    private void cekData(ArrayList<Wilayah> wilayahs) {

        /*cek apakah data ada atau tidak*/
        if (wilayahs != null && wilayahs.size() > 0) {

            Log.d("data", "data wilayah ada");
            /*data ada maka tampilkan tab dan isinya*/
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            populateTabs(wilayahs);
        } else {

            Log.d("data", "data wilayah tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);

        }
    }

    private void setDateButton(int month, int year) {
        DateFormatSymbols symbols = new DateFormatSymbols();
        String text = symbols.getMonths()[month] + " " + String.valueOf(year);
        tanggal.setText(text);
    }

    private void getWilayah(int bulan) {

        SharedPreferences preferences = WilayahActivity.this.getSharedPreferences(
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
        Call<ArrayList<Wilayah>> call = userClient.getWilayah(bulan + 1);

        call.enqueue(new Callback<ArrayList<Wilayah>>() {
            @Override
            public void onResponse(Call<ArrayList<Wilayah>> call, Response<ArrayList<Wilayah>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("size", String.valueOf(response.body().size()));
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Wilayah>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });

        /*ArrayList<Wilayah> wilayahs = new ArrayList<>();
        wilayahs.add(new Wilayah("Cepu", "SPBU", Matbal.PERTAMAX, 24));
        wilayahs.add(new Wilayah("Cepu", "SPBU", Matbal.PREMIUM, 368));
        wilayahs.add(new Wilayah("Lamongan", "SPBU", Matbal.PERTAMAX, 1384));
        wilayahs.add(new Wilayah("Lamongan", "SPBU", Matbal.PREMIUM, 1248));
        wilayahs.add(new Wilayah("Lamongan", "SPBU", Matbal.BIOSOLAR, 3272));
        wilayahs.add(new Wilayah("Lamongan", "SPBU", Matbal.SOLAR, 2618));
        wilayahs.add(new Wilayah("Lamongan", "TNI/Polri", Matbal.PERTAMAX, 40));
        wilayahs.add(new Wilayah("Lamongan", "TNI/Polri", Matbal.SOLAR, 8));
        wilayahs.add(new Wilayah("Lamongan", "SPDN", Matbal.BIOSOLAR, 400));
        wilayahs.add(new Wilayah("Lamongan", "SPDN", Matbal.SOLAR, 320));
        wilayahs.add(new Wilayah("Bojonegoro", "SPBU", Matbal.PERTAMAX, 892));
        wilayahs.add(new Wilayah("Bojonegoro", "SPBU", Matbal.PREMIUM, 1032));
        wilayahs.add(new Wilayah("Bojonegoro", "SPBU", Matbal.BIOSOLAR, 2280));
        wilayahs.add(new Wilayah("Bojonegoro", "SPBU", Matbal.SOLAR,  1824));
        wilayahs.add(new Wilayah("Gresik", "SPBU", Matbal.PERTAMAX,  1180));
        wilayahs.add(new Wilayah("Gresik", "SPBU", Matbal.PREMIUM,  1528));
        wilayahs.add(new Wilayah("Gresik", "SPBU", Matbal.BIOSOLAR,  4560));
        wilayahs.add(new Wilayah("Gresik", "SPBU", Matbal.SOLAR,  3658));
        wilayahs.add(new Wilayah("Gresik", "SPDN", Matbal.BIOSOLAR,  24));
        wilayahs.add(new Wilayah("Gresik", "SPDN", Matbal.SOLAR,  19));
        wilayahs.add(new Wilayah("Rembang", "SPBU", Matbal.PERTAMAX,  216));
        wilayahs.add(new Wilayah("Rembang", "SPBU", Matbal.BIOSOLAR,  1816));
        wilayahs.add(new Wilayah("Rembang", "SPBU", Matbal.SOLAR,  1453));
        wilayahs.add(new Wilayah("Rembang", "SPDN", Matbal.BIOSOLAR,  72));
        wilayahs.add(new Wilayah("Rembang", "SPDN", Matbal.SOLAR,  58));
        wilayahs.add(new Wilayah("Tuban", "SPBU", Matbal.PERTAMAX, 936));
        wilayahs.add(new Wilayah("Tuban", "SPBU", Matbal.PREMIUM, 984));
        wilayahs.add(new Wilayah("Tuban", "SPBU", Matbal.BIOSOLAR, 4056));
        wilayahs.add(new Wilayah("Tuban", "SPBU", Matbal.SOLAR,  3245));
        wilayahs.add(new Wilayah("Tuban", "SPDN", Matbal.BIOSOLAR,  64));
        wilayahs.add(new Wilayah("Tuban", "SPDN", Matbal.SOLAR,  51));
        wilayahs.add(new Wilayah("Tuban", "Own Use MT", Matbal.SOLAR,  80));
        wilayahs.add(new Wilayah("Tuban", "PLN", Matbal.SOLAR,  224));
        wilayahs.add(new Wilayah("Tuban", "TNI/Polri", Matbal.PERTAMAX, 20));
        wilayahs.add(new Wilayah("Blora", "SPBU", Matbal.PERTAMAX, 48));
        wilayahs.add(new Wilayah("Blora", "SPBU", Matbal.PREMIUM, 72));
        wilayahs.add(new Wilayah("Ngawi", "SPBU", Matbal.PERTAMAX, 1436));
        wilayahs.add(new Wilayah("Magetan", "SPBU", Matbal.PERTAMAX,  152));
        wilayahs.add(new Wilayah("Pati", "SPBU", Matbal.PERTAMAX,  24));
        wilayahs.add(new Wilayah("Semarang", "SPBU", Matbal.PREMIUM, 8));

        cekData(wilayahs);*/

    }
}
