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

        /*call.enqueue(new Callback<ArrayList<Opers>>() {
            @Override
            public void onResponse(Call<ArrayList<Opers>> call, Response<ArrayList<Opers>> response) {
                Log.w("code", String.valueOf(response.code()));
                if (response.code() == 200 && response.body() != null) {
                    Log.w("size", String.valueOf(response.body().size()));
                    cekData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Opers>> call, Throwable t) {
                Log.e("Call", " failed " + t.getMessage());
            }
        });*/

        /*SAMPLE DATA*/
        ArrayList<Opers> opers = new ArrayList<>();
        opers.add(new Opers("2017-02-01", 1608, "4:49 AM", "4:44 PM", "11:54"));
        opers.add(new Opers("2017-02-02", 1592, "5:08 AM", "5:16 PM", "12:08"));
        opers.add(new Opers("2017-02-03", 1440, "5:11 AM", "5:04 PM", "11:53"));
        opers.add(new Opers("2017-02-04", 1568, "5:18 AM", "8:04 PM", "14:45"));
        opers.add(new Opers("2017-02-05", 1064, "5:04 AM", "3:05 PM", "10:01"));
        opers.add(new Opers("2017-02-06", 1132, "5:04 AM", "5:11 PM", "12:06"));
        opers.add(new Opers("2017-02-07", 1836, "2:50 AM", "5:56 PM", "15:06"));
        opers.add(new Opers("2017-02-08", 1596, "4:51 AM", "4:55 PM", "12:04"));
        opers.add(new Opers("2017-02-09", 1456, "5:12 AM", "4:56 PM", "11:43"));
        opers.add(new Opers("2017-02-10", 1512, "5:00 AM", "8:32 PM", "15:32"));
        opers.add(new Opers("2017-02-11", 1604, "5:05 AM", "10:53 PM", "17:48"));
        opers.add(new Opers("2017-02-12", 1172, "5:12 AM", "1:11 PM", "07:58"));
        opers.add(new Opers("2017-02-13", 1208, "5:38 AM", "4:16 PM", "10:38"));
        opers.add(new Opers("2017-02-14", 1888, "2:43 AM", "5:30 PM", "14:47"));
        opers.add(new Opers("2017-02-15", 1728, "4:53 AM", "7:00 PM", "14:06"));
        opers.add(new Opers("2017-02-16", 1044, "6:13 AM", "3:28 PM", "09:15"));
        opers.add(new Opers("2017-02-17", 1728, "5:02 AM", "6:31PM", "13:28"));
        opers.add(new Opers("2017-02-18", 1648, "5:09 AM", "7:27 PM", "14:18"));
        opers.add(new Opers("2017-02-19", 1312, "4:54 AM", "5:01 PM", "12:06"));
        cekData(opers);
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
