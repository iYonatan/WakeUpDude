package com.example.yonatan.wakeupdude.Costum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.yonatan.wakeupdude.WakeupActivity;

/**
 * Created by Yonatan on 8/30/2016.
 *
 */
public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in = new Intent(context, WakeupActivity.class);
        in.putExtra("Time", intent.getStringExtra("Time"));
        context.startActivity(in);

    }



}
