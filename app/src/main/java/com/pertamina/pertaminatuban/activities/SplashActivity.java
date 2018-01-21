package com.pertamina.pertaminatuban.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

                /*cek auth, mengecek apakah user sudah pernah login, jika sudah pernah
                * langsung menuju ke menu activity*/
                String userKey;
                SharedPreferences preferences = SplashActivity.this.getSharedPreferences(
                        "login",
                        Context.MODE_PRIVATE
                );
                userKey = preferences.getString("userKey", "none");
                if (!userKey.equals("none")) {
                    Intent menuIntent = new Intent(getApplicationContext(), MenuActivity.class);
                    menuIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(menuIntent);
                } else {

                /*eksekusi intent diatas untuk masuk halaman LoginActivity.class*/
                    startActivity(loginIntent);
                }

                finish();

            }
        }, 2000);
    }
}
