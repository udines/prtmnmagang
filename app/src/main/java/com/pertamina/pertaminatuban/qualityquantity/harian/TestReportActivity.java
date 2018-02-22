package com.pertamina.pertaminatuban.qualityquantity.harian;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;

import java.util.ArrayList;

public class TestReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_report);
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
        RecyclerView recyclerView = findViewById(R.id.test_report_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ArrayList<ItemTestReport> testReports = new ArrayList<>();
        testReports.add(new ItemTestReport(
                "25 - 01 - 2018",
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Test%20Report%20TPL.pdf?alt=media&token=45926ae2-ad79-4ba2-8ea3-175b93fdaf3b"
        ));
        testReports.add(new ItemTestReport(
                "24 - 01 - 2018",
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Test%20Report%20TPL.pdf?alt=media&token=45926ae2-ad79-4ba2-8ea3-175b93fdaf3b"
        ));
        testReports.add(new ItemTestReport(
                "23 - 01 - 2018",
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Test%20Report%20TPL.pdf?alt=media&token=45926ae2-ad79-4ba2-8ea3-175b93fdaf3b"
        ));
        recyclerView.setAdapter(new TestReportAdapter(testReports));
    }

}
