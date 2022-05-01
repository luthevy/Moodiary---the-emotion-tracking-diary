package com.example.moodiary;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class UploaddtbService extends Service {
    @Override
    public IBinder onBind(Intent arg0) { return null; }
    @Override
    public void onCreate() { super.onCreate(); }
    @Override
    public void onStart(Intent intent, int startId) {
        MoodInfo.addToDatabase();

    }
    @Override
    public void onDestroy() { }
//MyService1{
}
