package com.pertamina.pertaminatuban.info_umum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.pertamina.pertaminatuban.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

public class InfoUmumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_umum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        populateInfo();

        handlePostButton();
    }

    private void handlePostButton() {
        Button postButton = findViewById(R.id.info_button_post);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PostInfoActivity.class));
            }
        });
    }

    private void populateInfo() {
        RecyclerView recyclerView = findViewById(R.id.info_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Calendar calendar = Calendar.getInstance();

        ArrayList<InfoUmum> infoUmums = new ArrayList<>();
        infoUmums.add(new InfoUmum(
                "id",
                "Buzzer",
                "Buzzers gonna buzz",
                "We are buzz lightyears, come from the toy story planet. We invade you human, we want your oil.",
                new Timestamp(calendar.getTimeInMillis())
        ));
        infoUmums.add(new InfoUmum(
                "id",
                "Buzzer",
                "Buzzers gonna buzz",
                "We are buzz lightyears, come from the toy story planet. We invade you human, we want your oil.",
                new Timestamp(calendar.getTimeInMillis())
        ));
        infoUmums.add(new InfoUmum(
                "id",
                "Buzzer",
                "Buzzers gonna buzz",
                "We are buzz lightyears, come from the toy story planet. We invade you human, we want your oil.",
                new Timestamp(calendar.getTimeInMillis())
        ));
        infoUmums.add(new InfoUmum(
                "id",
                "Buzzer",
                "Buzzers gonna buzz",
                "We are buzz lightyears, come from the toy story planet. We invade you human, we want your oil.",
                new Timestamp(calendar.getTimeInMillis())
        ));
        InfoUmumAdapter adapter = new InfoUmumAdapter(infoUmums);
        recyclerView.setAdapter(adapter);
    }

}
