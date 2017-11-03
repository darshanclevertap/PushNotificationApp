package com.app.darshan.pushnotificationapp;

import android.content.Context;
import android.view.View;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;

/**
 * Created by darshanpania on 02/11/17.
 */

public class MyOnClickListener implements View.OnClickListener {

    String eventName;
    Context context;

    public MyOnClickListener(String eventName, Context context){
        this.eventName = eventName;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        cleverTapAPI.event.push(eventName);
    }
}
