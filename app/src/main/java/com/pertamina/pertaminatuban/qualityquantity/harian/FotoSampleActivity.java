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

public class FotoSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_sample);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displayFoto();
    }

    private void displayFoto() {
        ArrayList<ItemFotoSample> fotoSamples = new ArrayList<>();
        fotoSamples.add(new ItemFotoSample(
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Foto%20Sample%20Gate%20Out%2012%20Feb%202018.jpg?alt=media&token=c613f78f-69f3-44c5-a4f9-e28c15354849",
                "Berikut kami sampaikan hasil sample GO Minggu, 11 Feb 2018 pukul 05.00 WIB =\n" +
                        "P = 709/27/719.9\n" +
                        "Pt = 731/27/741.7\n" +
                        "Px = 749/28/760.4\n" +
                        "BS = 841/28/850.0 FP = 65",
                "11 - 02 - 2018"
        ));
        fotoSamples.add(new ItemFotoSample(
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Foto%20Sample%20Gate%20Out%2012%20Feb%202018.jpg?alt=media&token=c613f78f-69f3-44c5-a4f9-e28c15354849",
                "Berikut kami sampaikan hasil sample GO Minggu, 11 Feb 2018 pukul 05.00 WIB =\n" +
                        "P = 709/27/719.9\n" +
                        "Pt = 731/27/741.7\n" +
                        "Px = 749/28/760.4\n" +
                        "BS = 841/28/850.0 FP = 65",
                "10 - 02 - 2018"
        ));
        fotoSamples.add(new ItemFotoSample(
                "https://firebasestorage.googleapis.com/v0/b/click-tuban.appspot.com/o/Foto%20Sample%20Gate%20Out%2012%20Feb%202018.jpg?alt=media&token=c613f78f-69f3-44c5-a4f9-e28c15354849",
                "Berikut kami sampaikan hasil sample GO Minggu, 11 Feb 2018 pukul 05.00 WIB =\n" +
                        "P = 709/27/719.9\n" +
                        "Pt = 731/27/741.7\n" +
                        "Px = 749/28/760.4\n" +
                        "BS = 841/28/850.0 FP = 65",
                "09 - 02 - 2018"
        ));
        RecyclerView recyclerView = findViewById(R.id.foto_sample_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new FotoSampleAdapter(fotoSamples, getApplicationContext()));
    }

}
