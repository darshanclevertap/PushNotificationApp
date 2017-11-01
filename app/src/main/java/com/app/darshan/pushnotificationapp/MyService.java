package com.app.darshan.pushnotificationapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;

/**
 * Created by darshanpania on 18/08/17.
 */

public class MyService extends Service {
    @Override
    public void onCreate() {
        //super.onCreate();
        Log.e("ServicePNApp","onCreate");
        try {
            CleverTapAPI cleverTapAPI = CleverTapAPI.getInstance(getApplicationContext());
        }catch (CleverTapMetaDataNotFoundException e){

        }catch (CleverTapPermissionsNotSatisfied e){

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        Log.e("ServicePNApp","onStart");
        return  Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        //super.onDestroy();
        Log.e("ServicePNApp","Destroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
