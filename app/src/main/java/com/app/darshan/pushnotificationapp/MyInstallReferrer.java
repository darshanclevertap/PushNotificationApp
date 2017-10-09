package com.app.darshan.pushnotificationapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.clevertap.android.sdk.InstallReferrerBroadcastReceiver;

/**
 * Created by darshanpania on 18/07/17.
 */

public class MyInstallReferrer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("PNApp", "MyInstallReceiver");
        final String action = intent.getAction();

        if (action != null && TextUtils.equals(action, "com.android.vending.INSTALL_REFERRER")) {
            try {
                final String referrer = intent.getStringExtra("referrer");

                // Parse parameters
                String[] params = referrer.split("&");
                for (String p : params) {
                    if (p.startsWith("utm_content=")) {
                        final String content = p.substring("utm_content=".length());

                        /**
                         * USE HERE YOUR CONTENT (i.e. configure the app based on the link the user clicked)
                         */
                        Log.i("ReferrerReceiver", content);

                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            InstallReferrerBroadcastReceiver installReferrerBroadcastReceiver = new InstallReferrerBroadcastReceiver();
            installReferrerBroadcastReceiver.onReceive(context, intent);
        }
    }
}
