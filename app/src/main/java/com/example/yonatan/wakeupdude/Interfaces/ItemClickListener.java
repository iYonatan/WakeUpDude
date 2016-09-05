package com.example.yonatan.wakeupdude.Interfaces;

import android.view.View;

/**
 * Created by Yonatan on 9/1/2016.
 *
 */
public interface ItemClickListener {

    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
