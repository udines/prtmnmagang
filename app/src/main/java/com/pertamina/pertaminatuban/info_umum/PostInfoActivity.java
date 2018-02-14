package com.pertamina.pertaminatuban.info_umum;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pertamina.pertaminatuban.R;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class PostInfoActivity extends AppCompatActivity {

    private EditText inputJudul, inputInfo, inputDeskipsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputJudul = findViewById(R.id.info_input_judul);
        inputInfo = findViewById(R.id.info_input_info);
        inputDeskipsi = findViewById(R.id.info_input_detail);

        getData();
    }

    private void getData() {
        Button kirim = findViewById(R.id.info_input_button_kirim);
        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (judulAda() && infoAda() && deskripsiAda()) {
                    Calendar calendar = Calendar.getInstance();
                    InfoUmum infoUmum = new InfoUmum(
                            inputJudul.getText().toString(),
                            inputInfo.getText().toString(),
                            inputDeskipsi.getText().toString(),
                            false,
                            new Timestamp(calendar.getTimeInMillis()).toString()
                    );
                    sendData(infoUmum);
                }
            }
        });
    }

    private void sendData(InfoUmum info) {
        FirebaseDatabase.getInstance().getReference("messages").push().setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                finish();
            }
        });
    }

    private boolean judulAda() {
        boolean ada = false;
        if (!inputJudul.getText().toString().isEmpty()) {
            ada = true;
        } else {
            Toast.makeText(this, "Masukkan judul", Toast.LENGTH_SHORT).show();
        }
        return ada;
    }

    private boolean infoAda() {
        boolean ada = false;
        if (!inputInfo.getText().toString().isEmpty()) {
            ada = true;
        } else {
            Toast.makeText(this, "Masukkan informasi", Toast.LENGTH_SHORT).show();
        }
        return ada;
    }

    private boolean deskripsiAda() {
        boolean ada = false;
        if (!inputDeskipsi.getText().toString().isEmpty()) {
            ada = true;
        } else {
            Toast.makeText(this, "Masukkan deskripsi", Toast.LENGTH_SHORT).show();
        }
        return ada;
    }
}
