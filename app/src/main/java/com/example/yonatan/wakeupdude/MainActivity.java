package com.example.yonatan.wakeupdude;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.yonatan.wakeupdude.Animations.ResizeAnimation;
import com.example.yonatan.wakeupdude.Interfaces.FragmentCommunicator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements FragmentCommunicator {

    private TextView displayTime, displayMonthYear, displayDayName;
    BroadcastReceiver _broadcastReceiver;
    private final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("HH.mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
        displayTime = (TextView) findViewById(R.id.display_time);
        displayMonthYear = (TextView) findViewById(R.id.display_month_year);
        displayDayName = (TextView) findViewById(R.id.display_day_name);

        // Setting up the day of the month
        displayTime.setText(_sdfWatchTime.format(new Date()));
        setDisplayMonthYear();
        setDisplayDayName();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final View myView = findViewById(R.id.ll_reveal);
        myView.setVisibility(View.INVISIBLE);
        /**
         *
         * When the fab is clicked
         *
         * **/
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            boolean expandOrCollapse = true; // Seeing if adding alarm panel needs to be extended or collapsed

            @Override
            public void onClick(View view) {
                if (expandOrCollapse) { // If the panel is extended
                    ResizeAnimation.expand(myView); // Expand animation
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_white_48dp, MainActivity.this.getTheme())); // Changing the fab's icon
                    } else {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_clear_white_48dp));
                    }

                    expandOrCollapse = false; // Next time the fab is clicked, it'll collapse the add alarm panel

                } else {
                    ResizeAnimation.collapse(myView);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_add_white_48dp, MainActivity.this.getTheme()));
                    } else {
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_alarm_add_white_48dp));
                    }
                    expandOrCollapse = true;
                }
            }
        });

    }
    /**
     * The system will send this broadcast event at the exact beginning of every minutes based on system clock.
     **/
    @Override
    public void onStart() {
        super.onStart();
        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    displayTime.setText(_sdfWatchTime.format(new Date()));

                if (intent.getAction().equals(Intent.ACTION_DATE_CHANGED))
                    setDisplayMonthYear();
            }
        };

        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_DATE_CHANGED));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (_broadcastReceiver != null)
            unregisterReceiver(_broadcastReceiver);
    }

    private void setDisplayMonthYear(){
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        String dateString = sdf.format(date);

        displayMonthYear.setText(dateString);
    }

    private void setDisplayDayName(){
        String Day = "";
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)){
            case 1: Day = "Sunday"; break;
            case 2: Day = "Monday"; break;
            case 3: Day = "Tuesday"; break;
            case 4: Day = "Wednesday"; break;
            case 5: Day = "Thursday"; break;
            case 6: Day = "Friday"; break;
            case 7: Day = "Saturday"; break;
        }
        displayDayName.setText(Day);

    }

    @Override
    public void response(AlarmType alarmInfo) {
        AlarmsListFragment alarmsListF = (AlarmsListFragment) getFragmentManager().findFragmentById(R.id.alarm_box_container_fragment);
        alarmsListF.onRecvData(alarmInfo);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
