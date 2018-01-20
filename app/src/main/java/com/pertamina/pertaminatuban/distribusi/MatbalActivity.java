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
import android.widget.Toast;

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

        /*get data. jika data tidak ada maka tampilkan tulisan jika kosong. jika ada
        * maka tampilkan tab beserta isinya*/
        getData(year, month);

        /*handle tanggal untuk mengubah data berdasarkan bulan*/
        handleDate();
    }

    private void getData(int year, int month) {

        Log.d("get data ", "year " + year + ",month " + month);

        /*get data*/
        ArrayList<Matbal> matbals = new ArrayList<>();
        matbals.add(new Matbal("2018-01-20", Matbal.PERTAMAX, 244, 900));
        matbals.add(new Matbal("2018-01-20", Matbal.PERTALITE, 426, 900));
        matbals.add(new Matbal("2018-01-20", Matbal.BIOSOLAR, 230, 900));

        /*cek apakah data ada atau tidak*/
        if (matbals != null && matbals.size() > 0) {

            Log.d("data", "data matbal ada");
            /*data ada maka tampilkan tab dan isinya*/
            viewPager.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
            populateTabs(matbals);
            setDate(matbals.get(0).getDate());
        } else {

            Log.d("data", "data matbal tidak ada");
            /*data tidak ada maka hilangkan tab dan tampilkan pesan peringatan
            * untuk tanggal gunakan month dan year yang sudah diinisialisasi*/
            viewPager.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);

            /*ganti format tanggal menjadi bulan dan tahun*/
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat bulanFormat = new SimpleDateFormat("MMMM yyyy");
            try {
                tanggal.setText(bulanFormat.format(format.parse(getDate(year, month, 1))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
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

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<ArrayList<Matbal>> kumpulanFragment = new ArrayList<>();

        for (int i = 0; i < matbals.size(); i++) {
            if (fuelSudahAda(matbals.get(i).getFuel(), matbals)) {
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

    private boolean fuelSudahAda(String fuel, ArrayList<Matbal> matbals) {
        boolean ada = false;
        for (int i = 0; i < matbals.size(); i++) {
            if (matbals.get(i).getFuel().equals(fuel)) {
                ada = true;
            }
        }
        return ada;
    }
}
