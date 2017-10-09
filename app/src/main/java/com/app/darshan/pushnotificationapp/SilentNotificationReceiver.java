package com.app.darshan.pushnotificationapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by darshanpania on 11/08/17.
 */

public class SilentNotificationReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras.containsKey("wzrk_sound"))
            Log.e("PNApp",extras.get("wzrk_sound").toString());
    }
}
