package com.pertamina.pertaminatuban.qualityquantity.bulanan;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.harian.ItemTestReport;
import com.pertamina.pertaminatuban.qualityquantity.harian.TestReportAdapter;

import java.util.ArrayList;

public class TruckingLossActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trucking_loss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displaySample();
    }

    private void displaySample() {
        RecyclerView recyclerView = findViewById(R.id.trucking_loss_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<ItemTestReport> testReports = new ArrayList<>();
        testReports.add(new ItemTestReport(
                "Januari 2018",
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Laporan%20Trucking%20Loss%20MT%20Januari%202018.pdf?alt=media&token=11d0c3ef-257e-4f09-aabf-25fedd042c46"
        ));
        testReports.add(new ItemTestReport(
                "Desember 2017",
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Laporan%20Trucking%20Loss%20MT%20Januari%202018.pdf?alt=media&token=11d0c3ef-257e-4f09-aabf-25fedd042c46"
        ));
        testReports.add(new ItemTestReport(
                "November 2017",
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Laporan%20Trucking%20Loss%20MT%20Januari%202018.pdf?alt=media&token=11d0c3ef-257e-4f09-aabf-25fedd042c46"
        ));
        recyclerView.setAdapter(new TestReportAdapter(testReports));
    }
}
