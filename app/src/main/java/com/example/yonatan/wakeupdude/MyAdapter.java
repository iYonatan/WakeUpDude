package com.example.yonatan.wakeupdude;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.example.yonatan.wakeupdude.Config.config;

import java.util.ArrayList;

/**
 * Created by Yonatan on 8/17/2016.
 *
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private ArrayList<AlarmType> mDataset;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements RadialTimePickerDialogFragment.OnTimeSetListener {
        private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";
        public TextView alarmName, alarmTime;
        public Switch alarmSwitch;
        public LinearLayout editRepeatDays, displayRepeatDays;
        public ViewHolder(View v) {
            super(v);
            alarmName = (TextView) v.findViewById(R.id.alarm_name);
            alarmTime = (TextView) v.findViewById(R.id.alarm_time);
            alarmSwitch = (Switch) v.findViewById(R.id.alarm_active_switch);
            editRepeatDays = (LinearLayout) v.findViewById(R.id.edit_week_days);
            displayRepeatDays = (LinearLayout) v.findViewById(R.id.display_repeat_days);
        }




        public void onCreateRadialTime(FragmentManager fragmentManager){

            RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                    .setOnTimeSetListener(ViewHolder.this)
                    .setForced24hFormat();
            rtpd.show(fragmentManager, FRAG_TAG_TIME_PICKER);

        }


        @Override
        public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
            // Creating AlarmType object which handles the update of the selected alarm
            final AlarmType alarmInfo = MyAdapter.this.mDataset.get(getAdapterPosition());
            // Canceling current alarm
            alarmInfo.cancelAlarm();

            // Update the selected alarm time
            alarmTime.setText(hourOfDay + config.TIME_DIVIDER + minute);

            // Activates the selected alarm
            alarmInfo.setActivation(true);
            alarmSwitch.setChecked(true);

            // Update the new alarm time to the object
            alarmInfo.setAlarmTime(alarmTime.getText().toString());
            // set the selected alarm with the new time
            alarmInfo.setAlarm();
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<AlarmType> alarmInfo, Activity actv) {
        mDataset = alarmInfo;
        activity = actv;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_index_box, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final AlarmType alarmInfo = mDataset.get(position);
        holder.alarmName.setText(alarmInfo.getName());
        holder.alarmTime.setText(alarmInfo.getTime());
        holder.alarmSwitch.setChecked(true);

        alarmInfo.setAlarm();

        holder.alarmSwitch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(alarmInfo.isActive()){
                    alarmInfo.cancelAlarm();
                    alarmInfo.setActivation(false);
                } else {
                    alarmInfo.setAlarm();
                    alarmInfo.setActivation(true);
                }
            }
        });

        holder.alarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((FragmentActivity)activity).getSupportFragmentManager();
                holder.onCreateRadialTime(fragmentManager);
            }
        });
        for (int day = 0; day < holder.editRepeatDays.getChildCount(); day++) {
            final int finalDay = day;
            System.out.println(finalDay);
            holder.editRepeatDays.getChildAt(day).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    System.out.println(finalDay);
                    ((TextView)holder.displayRepeatDays.getChildAt(finalDay)).setText("Selected");
                }});
        }
        //holder.editRepeatDays.getChildAt(0).setOnClickListener(new View.OnClickListener(){@Override public void onClick(View v) {}});
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
