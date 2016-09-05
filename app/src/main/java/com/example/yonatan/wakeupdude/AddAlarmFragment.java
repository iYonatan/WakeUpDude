package com.example.yonatan.wakeupdude;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.example.yonatan.wakeupdude.Config.config;
import com.example.yonatan.wakeupdude.Interfaces.FragmentCommunicator;

/**
 * Created by Yonatan on 8/16/2016.
 *
 */

public class AddAlarmFragment extends android.support.v4.app.Fragment implements RadialTimePickerDialogFragment.OnTimeSetListener{

    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";

    FragmentCommunicator fcomm;
    private Button onSave;
    private TextView alarmTime;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_alarm, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fcomm = (FragmentCommunicator)getActivity();
        alarmTime = (TextView) getActivity().findViewById(R.id.time_new_alarm);
        alarmTime = (TextView) getActivity().findViewById(R.id.time_new_alarm);
        alarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(AddAlarmFragment.this)
                        .setForced24hFormat();
                rtpd.show(getFragmentManager(), FRAG_TAG_TIME_PICKER);
            }
        });
        onSave = (Button) getActivity().findViewById(R.id.save_new_alarm);
        onSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) getActivity().findViewById(R.id.name_new_alarm);
                fcomm.response(new AlarmType(editText.getText().toString(),
                                             alarmTime.getText().toString())
                );
            }
        });
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        alarmTime.setText(String.valueOf(hourOfDay) + config.TIME_DIVIDER + String.valueOf(minute));
    }
}
