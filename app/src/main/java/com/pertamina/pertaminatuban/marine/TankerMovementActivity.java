package com.pertamina.pertaminatuban.marine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.pertamina.pertaminatuban.R;

public class TankerMovementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanker_movement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        handleInputButton();
    }

    private void handleInputButton() {
        Button button = findViewById(R.id.tanker_movement_input);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inputIntent = new Intent(getApplicationContext(), InputTankerMovementActivity.class);
                inputIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inputIntent);
            }
        });
    }

}
