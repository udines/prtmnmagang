package com.pertamina.pertaminatuban.operation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;
import com.pertamina.pertaminatuban.operation.distribusibbm.DistribusiBbmActivity;
import com.pertamina.pertaminatuban.operation.pumpable.PumpableActivity;
import com.pertamina.pertaminatuban.operation.supplybbm.SuplaiBbmActivity;
import com.pertamina.pertaminatuban.operation.tfpipeline.TransferPipelineActivity;
import com.pertamina.pertaminatuban.operation.tftppi.TransferTppiActivity;

public class OperationActivity extends AppCompatActivity {

    private CardView menuPumpable, menuTfPipeline, menuTfTppi, menuSuplaiBbm, menuDistribusiBbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        menuPumpable = findViewById(R.id.operation_pumpable);
        menuTfPipeline = findViewById(R.id.operation_transfer_pipeline);
        menuTfTppi = findViewById(R.id.operation_transfer_tppi);
        menuSuplaiBbm = findViewById(R.id.operation_suplai_bbm);
        menuDistribusiBbm = findViewById(R.id.operation_distribusi_bbm);

        handleMenuClick();
    }

    private void handleMenuClick() {
        menuPumpable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pumpableIntent = new Intent(getApplicationContext(), PumpableActivity.class);
                pumpableIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pumpableIntent);
            }
        });
        menuTfPipeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pipelineIntent = new Intent(getApplicationContext(), TransferPipelineActivity.class);
                pipelineIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pipelineIntent);
            }
        });
        menuTfTppi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tppiIntent = new Intent(getApplicationContext(), TransferTppiActivity.class);
                tppiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(tppiIntent);
            }
        });
        menuSuplaiBbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent suplaiIntent = new Intent(getApplicationContext(), SuplaiBbmActivity.class);
                suplaiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(suplaiIntent);
            }
        });
        menuDistribusiBbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent distribusiIntent = new Intent(getApplicationContext(), DistribusiBbmActivity.class);
                distribusiIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(distribusiIntent);
            }
        });
    }

}
