package com.example.yonatan.wakeupdude;

import android.app.Fragment;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yonatan.wakeupdude.Animations.ResizeAnimation;
import com.example.yonatan.wakeupdude.Costum.DividerItemDecoration;
import com.example.yonatan.wakeupdude.Interfaces.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by Yonatan on 8/17/2016.
 *
 */

public class AlarmsListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<AlarmType> alarmInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alarm_box_container, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        alarmInfo = new ArrayList<>();
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_alarm);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
       // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(alarmInfo,  getActivity());
        mRecyclerView.setAdapter(mAdapter);


//
//        final View extendEditBox = getActivity().findViewById(R.id.extend_edit_box);
//        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), mRecyclerView,
//                new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                ResizeAnimation.expand(extendEditBox);
////                AlarmType alarm = alarmInfo.get(position);
////                Toast.makeText(getActivity().getApplicationContext(), alarm.getName() + " is selected!", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
    }


    public void onRecvData(AlarmType alarmName){
        alarmInfo.add(0, alarmName);
        mAdapter.notifyDataSetChanged();

    }
}
