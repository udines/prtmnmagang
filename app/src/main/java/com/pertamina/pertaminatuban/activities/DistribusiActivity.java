package com.pertamina.pertaminatuban.activities;

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
import com.pertamina.pertaminatuban.fragment.distribusi.KonsumenFragment;
import com.pertamina.pertaminatuban.fragment.distribusi.MatbalFragment;
import com.pertamina.pertaminatuban.fragment.distribusi.OpersFragment;
import com.pertamina.pertaminatuban.fragment.distribusi.RitaseFragment;
import com.pertamina.pertaminatuban.fragment.distribusi.WilayahFragment;
import com.pertamina.pertaminatuban.utils.ViewPagerAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DistribusiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribusi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleAppBar();
    }

    private void handleAppBar() {
        ViewPager viewPager = findViewById(R.id.distribusi_viewpager);
        TabLayout tabLayout = findViewById(R.id.distribusi_tab);
        TextView tanggal = findViewById(R.id.distribusi_tanggal);
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        /*inisialisasi tab*/
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MatbalFragment());
        fragments.add(new KonsumenFragment());
        fragments.add(new WilayahFragment());
        fragments.add(new OpersFragment());
        fragments.add(new RitaseFragment());

        /*inisialisasi nama tab*/
        ArrayList<String> titles = new ArrayList<>();
        titles.add("Matbal");
        titles.add("Konsumen");
        titles.add("Wilayah");
        titles.add("Opers");
        titles.add("Ritase");

        /*inisialisasi adapter dengan memasukkan daftar fragment
        * dan daftar nama tab*/
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(),
                fragments,
                titles
        );

        /*tempelkan adapter ke viewpager*/
        viewPager.setAdapter(adapter);

        /*set tab bisa discroll karena judul tab bisa panjang
        * dan hubungkan tab dengan viewpager*/
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

}
