package com.pertamina.pertaminatuban.marine.input;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pertamina.pertaminatuban.R;

public class InputTemporaryStopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_temporary_stop);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getData();
    }

    private void getData() {
        EditText inputReason;
        Button inputStopDate, inputStopTime, inputResumeDate, inputResumeTime, kirim;

        inputReason = findViewById(R.id.input_temporary_stop_reason);
        inputStopDate = findViewById(R.id.input_temporary_stop_date_stop);
        inputStopTime = findViewById(R.id.input_temporary_stop_time_stop);
        inputResumeDate = findViewById(R.id.input_temporary_stop_date_resume);
        inputResumeTime = findViewById(R.id.input_temporary_stop_time_resume);
        kirim = findViewById(R.id.input_temporary_stop_kirim);
    }

}
