package com.app.darshan.pushnotificationapp;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import com.bumptech.glide.module.AppGlideModule;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InstallReferrerBroadcastReceiver;
import com.facebook.FacebookSdk;
import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.annotations.Parser;
import com.leanplum.annotations.Variable;
import com.leanplum.callbacks.VariablesChangedCallback;
//import com.squareup.leakcanary.LeakCanary;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;

/**
 * Created by darshanpania on 16/06/17.
 */

public class MyApplication extends Application {

    @Variable
    public static String welcomeMessage = "Welcome to Leanplum!";
    private static Timer mTimer1;
    private static TimerTask mTt1;
    private static Handler mTimerHandler = new Handler();
    static int i=0;
    static long t0 = System.currentTimeMillis();
    @Override
    public void onCreate() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//        Leanplum.setApplicationContext(this);
        Parser.parseVariables(this);
        //  For session lifecyle tracking.
        LeanplumActivityHelper.enableLifecycleCallbacks(this);


        if (BuildConfig.DEBUG) {
            Leanplum.setAppIdForDevelopmentMode("app_iiE1v5g8wPisrP0A4k3G31CIHsjzWsXLASAyXmbZA1A", "dev_C077n4uFgoEDfD7arKhTjdLkPntie14Cnk60gRrXIvo");
            Log.e("Check Debug","debug");
        } else {
            Leanplum.setAppIdForProductionMode("app_iiE1v5g8wPisrP0A4k3G31CIHsjzWsXLASAyXmbZA1A", "prod_L1lc88VpL0lZjDLdrAQDSVbN9hbGcDYPGJfbeZjRXK8");
        }

        Leanplum.start(this);

        Parser.parseVariables(this);

        Leanplum.addVariablesChangedHandler(new VariablesChangedCallback() {
            @Override
            public void variablesChanged() {
                Log.i("Test", welcomeMessage);
            }
        });

        Leanplum.track("Launch");

        //CT
        //CleverTapAPI.setDebugLevel(Log.VERBOSE);
        MyApplicationLifeCycleCallback.register(this);
        //startTimer();

        super.onCreate();

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopTimer();
    }

    public static void stopTimer(){
        HashMap<String, Object> sessionTime = new HashMap<String, Object>();
        sessionTime.put("Session Time", i);
        cleverTapAPI.event.push("Session stopped",sessionTime);
        if(mTimer1 != null){
            mTimerHandler.removeCallbacks(runnable);
            mTimer1.cancel();
            mTimer1.purge();
            mTimer1 = null;

        }
    }

    public static final void startTimer(){

        mTimer1 = new Timer();
        mTt1 = new TimerTask() {

            public void run() {
                mTimerHandler.post(runnable);
                //Log.e("")

            }
        };

        mTimer1.schedule(mTt1, 0,1000);
        cleverTapAPI.event.push("Session started");
    }

    static Runnable runnable = new Runnable() {
        @Override
        public void run() {
           // cleverTapAPI.event.push("Timer Added");
            int timer = 0;
            if (i == 60 * 2) {
                stopTimer();
                Log.d("Timer","Timer Stopped");
            } else {
                //sendSamples();
                Log.d("Timer","Timer"+i++);
                //timer++;
            }

        }
    };
}
