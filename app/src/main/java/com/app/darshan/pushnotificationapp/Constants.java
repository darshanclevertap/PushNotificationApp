package com.app.darshan.pushnotificationapp;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.LruCache;

import java.util.Timer;
import java.util.TimerTask;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;

/**
 * Created by darshanpania on 05/07/17.
 */

public class Constants {
    public static int rating=0;
    public static String appImgUrl="";
    public static String notifText="";
    public static String notifTitle="";
    public static int cartItems=0;
    final static int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

    // Use 1/8th of the available memory for this memory cache.
    final static int cacheSize = maxMemory / 8;
    public static LruCache<String, Bitmap> image = new LruCache<String, Bitmap>(cacheSize) {
        @Override
        protected int sizeOf(String key, Bitmap bitmap) {
            // The cache size will be measured in kilobytes rather than
            // number of items.
            return bitmap.getByteCount() / 1024;
        }
    };

   public static int SESSION_LENGTH_MINS = 1;
}
