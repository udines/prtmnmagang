package com.pertamina.pertaminatuban.distribusi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.distribusi.models.Matbal;
import com.pertamina.pertaminatuban.utils.ViewPagerAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MatbalActivity extends AppCompatActivity {

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

        ArrayList<Matbal> matbals = new ArrayList<>();
        matbals.add(new Matbal("2018-01-20", Matbal.PERTAMAX, 244, 900));
        matbals.add(new Matbal("2018-01-20", Matbal.PERTALITE, 426, 900));
        matbals.add(new Matbal("2018-01-20", Matbal.BIOSOLAR, 230, 900));

        populateTabs(matbals);
        setDate(matbals.get(0).getDate());
    }

    private void setDate(String stringDate) {
        TextView tanggal = findViewById(R.id.matbal_tanggal);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat bulanFormat = new SimpleDateFormat("MMMM yyyy");
        try {
            Date date = format.parse(stringDate);
            String bulanTahun = bulanFormat.format(date);
            tanggal.setText(bulanTahun);
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
