package com.pertamina.pertaminatuban.qualityquantity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pertamina.pertaminatuban.R;

public class DisplayPdfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_pdf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        displayPdf();
    }

    private void displayPdf() {
        String url = getIntent().getStringExtra("url");
    }

}
