package com.example.cryptolist;

import android.app.Application;

import timber.log.Timber;

public class CryptoApp extends Application {
    @Override public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());

    }

}
