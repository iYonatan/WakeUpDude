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
    private int mAlarmRequestCode, mHour, mMinute;
    private boolean mActivation;

    private Context mContext;
    private AlarmManager mAlarmMgr;
    private PendingIntent mAlarmIntent;
    private Intent mIntentToAlarmActivity;

    /**
     * Constructor.
     *
     * @param alarmRequestCode
     * @param context   main context
     * @param name      alarm name
     * @param alarmTime alarm time in String (ex: 17:27)
     */
    public AlarmType(int alarmRequestCode, Context context, String name, String alarmTime) {
        this.mAlarmRequestCode = alarmRequestCode;
        this.mName = name; // Alarm name
        this.mAlarmTime = alarmTime; // Alarm time
        this.mActivation = true; // Alarm activation
        this.mContext = context; // Main context

        // Getting alarm service.
        this.mAlarmMgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        this.mIntentToAlarmActivity = new Intent(this.mContext, AlarmManagerBroadcastReceiver.class);
        this.mAlarmIntent = PendingIntent.getBroadcast(this.mContext, this.mAlarmRequestCode, this.mIntentToAlarmActivity, 0);

        // Setting the hour and the minute of the alarm.
        this.setHourMinute();
    }

    /**
     * Get method.
     * @return the name of the alarm
     */
    public String getName(){
        return this.mName;
    }

    /**
     * Get method.
     * @return Alarm's time
     */
    public String getTime(){
        return this.mAlarmTime;
    }

    /**
     * Get method
     * @return Alarm's hour
     */
    public int getmHour(){
        return this.mHour;
    }

    /**
     * Get method.
     * @return Alarm's minute
     */
    public int getmMinute(){
        return this.mMinute;
    }

    /**
     * @return if the alarm is active or not.
     */
    public boolean isActive(){
        return this.mActivation;
    }

    /**
     * Set method.
     * setting alarm's hour and minute by splitting the alarm time.
     */
    private void setHourMinute() {
        String[] timeSplit = this.mAlarmTime.split(Pattern.quote(config.TIME_DIVIDER));
        this.mHour = Integer.valueOf(timeSplit[0]);
        this.mMinute = Integer.valueOf(timeSplit[1]);
    }

    /**
     * Set method.
     * setting different alarm time from outside
     * @param alternativeTime alternative time for alarm
     */
    public void setAlarmTime(String alternativeTime){
        this.mAlarmTime = alternativeTime;
        this.setHourMinute();

    }

    /**
     * Set method.
     * setting alarm activation
     * @param activation
     */
    public void setActivation(boolean activation){
        this.mActivation = activation;
    }

    /**
     * Set method.
     * Setting new alarm
     */
    public void setAlarm(){
        // Set the alarm time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, this.getmHour());
        calendar.set(Calendar.MINUTE, this.getmMinute());

        // setRepeating() lets you specify a precise custom interval--in this case, Once a day.
        this.mAlarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, this.mAlarmIntent);
    }

    /**
     * Canceling alarm
     */
    public void cancelAlarm(){
        this.mAlarmMgr.cancel(this.mAlarmIntent);
    }
}
