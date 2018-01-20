package com.pertamina.pertaminatuban.distribusi;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.utils.ViewPagerAdapter;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MatbalActivity extends AppCompatActivity {

    private TextView tanggal;
    private int month;
    private int year;

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

        /*inisialisasi tanggal jika data tidak ada agar tidak error*/
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        ArrayList<Matbal> matbals = new ArrayList<>();
        matbals.add(new Matbal("2018-01-20", Matbal.PERTAMAX, 244, 900));
        matbals.add(new Matbal("2018-01-20", Matbal.PERTALITE, 426, 900));
        matbals.add(new Matbal("2018-01-20", Matbal.BIOSOLAR, 230, 900));

        /*inisialisasi teks tanggal dan data*/
        populateTabs(matbals);
        setDate(matbals.get(0).getDate());

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
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
                                month = selectedMonth + 1;
                                year = selectedYear;
                                setDate(getDate(selectedYear, selectedMonth  + 1, 1));
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

    public String getDate(int y, int m, int d) {
        String year, month, day;
        year = String.valueOf(y);
        month = String.valueOf(m);
        day = String.valueOf(d);
        return year + "-" + month + "-" + day;
    }

    private void setDate(String stringDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat bulanFormat = new SimpleDateFormat("MMMM yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = format.parse(stringDate);
            String bulanTahun = bulanFormat.format(date);
            tanggal.setText(bulanTahun);
            calendar.setTimeInMillis(date.getTime());
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void populateTabs(ArrayList<Matbal> matbals) {

        ViewPager viewPager = findViewById(R.id.matbal_viewpager);
        TabLayout tabLayout = findViewById(R.id.matbal_tab);

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();

        for (int i = 0; i < matbals.size(); i++) {
            titles.add(matbals.get(i).getFuel());
            MatbalPage page = new MatbalPage();
            page.setMatbals(matbals);
            page.setFuel(matbals.get(i).getFuel());
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
