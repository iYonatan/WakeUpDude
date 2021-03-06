package com.example.yonatan.wakeupdude;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Yonatan on 8/30/2016.
 *
 */
public class WakeupActivity extends Activity {

    private TextView showAlarmTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wakeup_layout);
        showAlarmTime = (TextView) findViewById(R.id.show_alarm_time);

        Intent fromAlarmAdapter = getIntent();
        String alarmTime = fromAlarmAdapter.getStringExtra("Time");
        showAlarmTime.setText(alarmTime);


//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_alarm_add_white_48dp)
//                        .setContentTitle("My notification")
//                        .setSound(Uri.parse("android.resource://my.package.name/raw/notification"))
//                        .setContentText("Hello World!");
//
//
//        // Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, WakeupActivity.class);
//
//        /** The stack builder object will contain an artificial back stack for the
//         started Activity.
//         This ensures that navigating backward from the Activity leads out of
//         your application to the Home screen. **/
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        // Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(WakeupActivity.class);
//        // Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        // mId allows you to update the notification later on.
//        mNotificationManager.notify(0, mBuilder.build());
    }
}
