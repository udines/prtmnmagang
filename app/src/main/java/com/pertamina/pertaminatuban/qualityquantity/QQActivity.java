package com.pertamina.pertaminatuban.qualityquantity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.qualityquantity.bulanan.TruckingLossActivity;
import com.pertamina.pertaminatuban.qualityquantity.bulanan.WorkingLossActivity;
import com.pertamina.pertaminatuban.qualityquantity.harian.FotoSampleActivity;
import com.pertamina.pertaminatuban.qualityquantity.harian.TestReportActivity;
import com.pertamina.pertaminatuban.qualityquantity.harian.UjiSampleActivity;

public class QQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleMenuClick();
    }

    private void handleMenuClick() {
        CardView workLoss, truckLoss, ujiSample, fotoSample, testReport;
        workLoss = findViewById(R.id.qq_menu_working_loss);
        truckLoss = findViewById(R.id.qq_menu_trucking_loss);
        ujiSample = findViewById(R.id.qq_menu_uji_sample);
        fotoSample = findViewById(R.id.qq_menu_foto_sample);
        testReport = findViewById(R.id.qq_menu_test_report);

        workLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkingLossActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        truckLoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TruckingLossActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        ujiSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UjiSampleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        fotoSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FotoSampleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        testReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TestReportActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

}
