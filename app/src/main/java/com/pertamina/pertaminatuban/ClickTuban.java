package com.pertamina.pertaminatuban;

import android.app.Application;

import com.google.firebase.messaging.FirebaseMessaging;

public class ClickTuban extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().subscribeToTopic("featured");
    }
}
