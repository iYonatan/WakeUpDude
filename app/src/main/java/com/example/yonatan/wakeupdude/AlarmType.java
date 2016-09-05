package com.example.yonatan.wakeupdude;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.example.yonatan.wakeupdude.Config.config;
import com.example.yonatan.wakeupdude.Costum.AlarmManagerBroadcastReceiver;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by Yonatan on 8/29/2016.
 *
 */
public class AlarmType{

    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";


    public String mName, mAlarmTime;
    private int mHour, mMinute;
    private boolean mActivation;

    public AlarmType(String name, String alarmTime){
        this.mName = name;
        this.mAlarmTime = alarmTime;
        this.mActivation = true;
        this.setHourMinute();
    }

    private void setHourMinute() {
        String[] timeSplit = this.mAlarmTime.split(Pattern.quote(config.TIME_DIVIDER));
        this.mHour = Integer.valueOf(timeSplit[0]);
        this.mMinute = Integer.valueOf(timeSplit[1]);
    }

    public void setActivation(boolean activation){
        this.mActivation = activation;
    }

    public String getName(){
        return this.mName;
    }

    public String getTime(){
        return this.mAlarmTime;
    }

    public int getmHour(){
        return this.mHour;
    }

    public int getmMinute(){
        return this.mMinute;
    }

    public boolean isActive(){
        return this.mActivation;
    }

    public void setAlarm(Context context){

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);

        // Set the alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, this.getmHour());
        calendar.set(Calendar.MINUTE, this.getmMinute());

        // setRepeating() lets you specify a precise custom interval--in this case,
        // Once a day.
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }

    public void cancelAlarm(Context context){
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }



}
