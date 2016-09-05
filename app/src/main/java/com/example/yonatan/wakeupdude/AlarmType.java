package com.example.yonatan.wakeupdude;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.yonatan.wakeupdude.Config.config;
import com.example.yonatan.wakeupdude.Costum.AlarmManagerBroadcastReceiver;

import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Created by Yonatan on 8/29/2016.
 *
 */
public class AlarmType{

    public String mName, mAlarmTime;
    private int mHour, mMinute;
    private boolean mActivation;

    private Context mContext;
    private AlarmManager mAlarmMgr;
    private PendingIntent mAlarmIntent;
    private Intent mIntentToAlarmActivity;

    public AlarmType(Context context, String name, String alarmTime){
        this.mName = name;
        this.mAlarmTime = alarmTime;
        this.mActivation = true;
        this.mContext = context;

        this.mAlarmMgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        this.mIntentToAlarmActivity = new Intent(this.mContext, AlarmManagerBroadcastReceiver.class);
        this.mAlarmIntent = PendingIntent.getBroadcast(this.mContext, 0, this.mIntentToAlarmActivity, 0);

        this.setHourMinute();
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

    private void setHourMinute() {
        String[] timeSplit = this.mAlarmTime.split(Pattern.quote(config.TIME_DIVIDER));
        this.mHour = Integer.valueOf(timeSplit[0]);
        this.mMinute = Integer.valueOf(timeSplit[1]);
    }

    public void setAlarmTime(String alternativeTime){
        this.mAlarmTime = alternativeTime;
        this.setHourMinute();

    }

    public void setActivation(boolean activation){
        this.mActivation = activation;
    }

    public void setAlarm(){
        // Set the alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, this.getmHour());
        calendar.set(Calendar.MINUTE, this.getmMinute());

        // setRepeating() lets you specify a precise custom interval--in this case, Once a day.
        this.mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, this.mAlarmIntent);
    }

    public void cancelAlarm(){
        this.mAlarmMgr.cancel(this.mAlarmIntent);
        System.out.println("Alarm Canceled");
    }



}
