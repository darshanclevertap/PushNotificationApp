package com.app.darshan.pushnotificationapp;

import android.annotation.TargetApi;
import android.app.*;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.clevertap.android.sdk.*;
import com.clevertap.android.sdk.exceptions.CleverTapException;

import java.util.Date;
import java.util.HashMap;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;

/**
 * Created by darshanpania on 31/07/17.
 */

public class MyApplicationLifeCycleCallback  {

    private static boolean registered = false;
    private static long appLastSeen=0;
    private static Runnable sessionTimeoutRunnable = null;
    private static final Handler handlerUsingMainLooper = new Handler(Looper.getMainLooper());


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static synchronized void register(android.app.Application application) {

        if (registered) {
            Log.e("MyApplication","Lifecycle callbacks have already been registered");
            return;
        }

        final CleverTapAPI wr;

        try {
            wr = CleverTapAPI.getInstance(application.getApplicationContext());//Initialize CleverTap
        } catch (CleverTapException e) {
            return;
        }

        registered = true;
        application.registerActivityLifecycleCallbacks(
                new android.app.Application.ActivityLifecycleCallbacks() {

                    @Override
                    public void onActivityCreated(Activity activity, Bundle bundle) {
                        CleverTapAPI.setAppForeground(true);
                        try {
                            wr.event.pushNotificationEvent(activity.getIntent().getExtras());//Handle push notifcations
                        } catch (Throwable t) {
                            // Ignore
                        }
                        try {
                            Intent intent = activity.getIntent(); //Handle deep links
                            Uri data = intent.getData();
                            wr.pushDeepLink(data);
                        } catch (Throwable t) {
                            // Ignore
                        }
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                        try {
                            wr.activityResumed(activity);
                            appLastSeen = System.currentTimeMillis();
                            if(sessionTimeoutRunnable!=null)
                                handlerUsingMainLooper.removeCallbacks(sessionTimeoutRunnable);
                        } catch (Throwable t) {
                            // Ignore
                        }
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                        try {
                            wr.activityPaused(activity);
                            final long now = System.currentTimeMillis();
                            // Create the runnable, if it is null
                            if (sessionTimeoutRunnable == null)
                                sessionTimeoutRunnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        HashMap<String, Object> sessionTime = new HashMap<String, Object>();
                                        sessionTime.put("Session Time", (System.currentTimeMillis() - appLastSeen)/1000);//Convert milliseconds to seconds
                                        sessionTime.put("Session Date", new Date());
                                        cleverTapAPI.event.push("Session stopped",sessionTime);
                                        appLastSeen = 0;
                                    }
                                };
                            handlerUsingMainLooper.postDelayed(sessionTimeoutRunnable,
                                    Constants.SESSION_LENGTH_MINS * 60 * 1000);
                        } catch (Throwable t) {
                            // Ignore
                        }
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {

                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {

                    }
                }

        );
        Log.v("MyApplication","Activity lifecycle callback successfully registered");
    }
}
