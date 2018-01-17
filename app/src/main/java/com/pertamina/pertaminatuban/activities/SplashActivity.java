package com.pertamina.pertaminatuban.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*membuat intent android yang menuju ke halaman LoginActivity.class*/
        final Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        /*membuat handler untuk menunda eksekusi intent selama 2000ms atau 2 detik*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                /*eksekusi intent diatas untuk masuk halaman LoginActivity.class*/
                startActivity(loginIntent);
                finish();

            }
        }, 2000);
    }
}
