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
import com.pertamina.pertaminatuban.distribusi.models.Opers;
import com.pertamina.pertaminatuban.distribusi.page.OpersPage;
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

public class OpersActivity extends AppCompatActivity {

    private int month, year;
    private TextView tanggal;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView emptyText;

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
        viewPager = findViewById(R.id.opers_viewpager);
        tabLayout = findViewById(R.id.opers_tab);
        emptyText = findViewById(R.id.opers_empty_text);

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        setDateButton(month, year);

        getOpers(month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
    }

    private void handleDate() {
        tanggal.setOnClickListener(new View.OnClickListener() {
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
                                getOpers(month);
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

    private void getOpers(int bulan) {
        Log.w("GET ", "start getting matbal bulan " + bulan);
        String baseUrl = "http://www.api.clicktuban.com/";
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        UserClient client = retrofit.create(UserClient.class);
        Call<ArrayList<Opers>> call = client.getOpers(bulan);

        call.enqueue(new Callback<ArrayList<Opers>>() {
            @Override
            public void onResponse(Call<ArrayList<Opers>> call, Response<ArrayList<Opers>> response) {
                Log.w("code", String.valueOf(response.code()));
                Log.w("size", String.valueOf(response.body().size()));
                if (response.code() == 200 && response.body() != null) {
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Opers>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });

        /*SAMPLE DATA*/
        /*ArrayList<Opers> opers = new ArrayList<>();
        opers.add(new Opers("2017-2-1", 1608, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-2", 1592, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-3", 1440, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-4", 1568, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-5", 1064, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-6", 1132, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-7", 1836, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-8", 1596, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-9", 1456, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-10", 1512, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-11", 1604, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-12", 1172, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-13", 1208, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-14", 1888, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-15", 1728, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-16", 1044, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-17", 1728, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-18", 1648, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-2-19", 1312, "4:49 AM", "4:44 PM", "11:54"));
        cekData(opers);*/
    }

    private void cekData(ArrayList<Opers> opers) {

        /*cek apakah data ada atau tidak*/
        if (opers != null && opers.size() > 0) {

            Log.d("data", "data matbal ada");
            /*data ada maka tampilkan tab dan isinya*/
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            populateTabs(opers);
        } else {

            Log.d("data", "data matbal tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);

        }
    }

    private void populateTabs(ArrayList<Opers> opers) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        for (int i = 0; i <=3; i++) {
            OpersPage page = new OpersPage();
            page.setOpers(opers);
            page.setType(i);
            titles.add(Opers.getTitle(i));
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
}
