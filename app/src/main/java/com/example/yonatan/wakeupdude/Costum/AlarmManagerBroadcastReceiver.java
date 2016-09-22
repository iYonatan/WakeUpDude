package com.example.yonatan.wakeupdude.Costum;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.yonatan.wakeupdude.Config.config;
import com.example.yonatan.wakeupdude.WakeupActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by Yonatan on 8/30/2016.
 *
 */
public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        int id = intent.getIntExtra("ID", 0);

        @SuppressWarnings("unchecked")
        ArrayList<Integer> activeRepeatDays = (ArrayList<Integer>) intent.getSerializableExtra("Active_Repeat_Days");
        System.out.println(id + ": " + activeRepeatDays);

        String alarmTime = intent.getStringExtra("Time");
        Intent in = new Intent(context, WakeupActivity.class);
        in.putExtra("Time", alarmTime);

        context.startActivity(in);

        String[] timeSplit = alarmTime.split(Pattern.quote(config.TIME_DIVIDER));
        int hour = Integer.valueOf(timeSplit[0]);
        int minute = Integer.valueOf(timeSplit[1]);

        for (int day : activeRepeatDays) {
            setAlarm(context, day, id, hour, minute);
        }
    }

    public void setAlarm(Context context, int day, int id, int hour, int minute){

        PendingIntent pi = PendingIntent.getService(context, id, new Intent(context,WakeupActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Set the alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        System.out.println("Day: " + day + " Hour: " + hour + " Minutes: " + minute + "\n");

        // setRepeating() lets you specify a precise custom interval--in this case, Once a day.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTime().getTime(), pi);
        }
    }



}
