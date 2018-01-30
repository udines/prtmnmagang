package com.pertamina.pertaminatuban.info_umum;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.pertamina.pertaminatuban.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        final RecyclerView recyclerView = findViewById(R.id.info_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseFirestore.getInstance()
                .collection("info_umum")
                .orderBy("time", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        Log.w("size", String.valueOf(documentSnapshots.size()));
                        List<InfoUmum> infos;
                        if (!documentSnapshots.isEmpty()) {
                            infos = documentSnapshots.toObjects(InfoUmum.class);
                            InfoUmumAdapter adapter = new InfoUmumAdapter(infos);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateInfo();
    }

    /*@Override
    protected void onPostResume() {
        super.onPostResume();
        populateInfo();
    }*/
}
