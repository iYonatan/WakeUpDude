package com.example.yonatan.wakeupdude.Costum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.yonatan.wakeupdude.WakeupActivity;

import java.util.Calendar;

/**
 * Created by Yonatan on 8/30/2016.
 *
 */
public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent in = new Intent(context, WakeupActivity.class);
        context.startActivity(in);
//      in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//      PendingIntent Sender = PendingIntent.getActivity(context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);




    }

    public void setAlarm(Context context, int hour, int minutes)
    {
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        // Set the alarm
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);

        // setRepeating() lets you specify a precise custom interval--in this case, 20 minutes.
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),  AlarmManager.INTERVAL_DAY, pi);
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }


}
