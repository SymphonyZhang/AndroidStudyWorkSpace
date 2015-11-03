package com.francis.clear;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceSubclass extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("---> Service的 onCreate()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        System.out.println("---> Service的 onStart()");
    }

}