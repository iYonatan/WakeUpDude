package com.example.yonatan.wakeupdude.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.yonatan.wakeupdude.Costum.AlarmManagerBroadcastReceiver;

/**
 * Created by Yonatan on 8/30/2016.
 *
 */
public class AlarmService extends Service {
    AlarmManagerBroadcastReceiver alarmManagerBroadcastReceiver = new AlarmManagerBroadcastReceiver();
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        alarmManagerBroadcastReceiver.setAlarm(this, 8, 30);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        alarmManagerBroadcastReceiver.setAlarm(this, 8, 30);
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
}
