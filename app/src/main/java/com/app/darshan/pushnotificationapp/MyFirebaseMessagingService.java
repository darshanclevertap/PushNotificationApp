package com.app.darshan.pushnotificationapp;

import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.EventHandler;
import com.clevertap.android.sdk.NotificationInfo;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Created by darshanpania on 16/06/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String imgUrl="";
    String appImgUrl="";
    String notifTitle="";
    String notifText="";
    String notifType="";
    String sound = "";
    static int notifyID = 0;
    RemoteViews contentViewBig,contentViewSmall;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.e("PNApp","MyFCMService1");
        Intent intent = new Intent(getApplication().getBaseContext(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        try {

            if (remoteMessage.getData().size() > 0) {
                Bundle extras = new Bundle();
                for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {
//                    if(entry.getKey().equals("sound")){
//                        sound = entry.getValue();
//
//                    }
//                      if(entry.getKey().equals("wzrk_sound")){
//                          entry.setValue(sound);
//                      }
                    extras.putString(entry.getKey(), entry.getValue());
//                    if(entry.getKey().equals("nt")){
//                        intent.putExtra("title",entry.getValue());
//                    }
//                    if(entry.getKey().equals("nm")){
//                        intent.putExtra("body",entry.getValue());
//                    }
//                    if(entry.getKey().equals("wzrk_id")){
//                        Log.e("App Launch Event ",entry.getValue());
//                    }
//                    if(entry.getKey().equals("wzrk_c2a")){
//                        Log.e("wzrk_c2a",entry.getValue());
//                    }

                    //Check for your custom Key Value Pairs
                    if(entry.getKey().equals("notif_type")){
                        notifType = entry.getValue();
                    }
                    if(entry.getKey().equals("img_url")){
                        imgUrl = entry.getValue();
                    }
                    if(entry.getKey().equals("app_img")){
                        appImgUrl = entry.getValue();
                        Constants.appImgUrl=appImgUrl;
                    }
                    if(entry.getKey().equals("notif_title")){
                        notifTitle = entry.getValue();
                        Constants.notifTitle=notifTitle;
                    }
                    if(entry.getKey().equals("notif_text")){
                        notifText = entry.getValue();
                        Constants.notifText=notifText;
                    }

                }

                NotificationInfo info = CleverTapAPI.getNotificationInfo(extras);

                if (info.fromCleverTap) {
                    //Comment the code where CleverTap renders the notification for you
                    if(imgUrl.isEmpty() && appImgUrl.isEmpty() && notifText.isEmpty() && notifTitle.isEmpty()) {
                        CleverTapAPI.createNotification(getApplicationContext(), extras);
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//                            String id = "product";
//                            // The user-visible name of the channel.
//                            CharSequence name = "Product";
//                            // The user-visible description of the channel.
//                            String description = "testing o";
//                            int importance = NotificationManager.IMPORTANCE_MAX;
//                            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
//                            // Configure the notification channel.
//                            mChannel.setDescription(description);
//                            mChannel.enableLights(true);
//                            // Sets the notification light color for notifications posted to this
//                            // channel, if the device supports this feature.
//                            mChannel.setLightColor(Color.RED);
//                            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
//                            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 123, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
//                            notificationManager.createNotificationChannel(mChannel);
//                            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),"Test")
//                                    .setSmallIcon(R.drawable.flatpnicon)
//                                    .setBadgeIconType(R.drawable.flatpnicon)
//                                    .setChannelId(id)
//                                    .setContentTitle(extras.get("nt").toString())
//                                    .setAutoCancel(true).setContentIntent(pendingIntent)
//                                    .setNumber(notifyID)
//                                    .setColor(255)
//                                    .setContentText(extras.get("nm").toString())
//                                    .setWhen(System.currentTimeMillis());
//                            notificationManager.notify(notifyID, notificationBuilder.build());
//                            notifyID++;
//                        }
                    }
                    try{
                        switch (notifType){
                            case "custom" : setCustomNotification();
                                break;

                            case "rating" : setRatingNotification();
                                break;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                } else {
                    // not from CleverTap handle yourself or pass to another provider
                }
            }
        } catch (Throwable t) {
            Log.d("MYFCMLIST", "Error parsing FCM message", t);
        }
        //startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void setCustomNotification(){
        int icon = R.mipmap.ic_launcher;
        long when = System.currentTimeMillis();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        contentViewBig = new RemoteViews(getPackageName(), R.layout.custom_notification);
        contentViewSmall = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
        //Null and Empty checks for your Key Value Pairs
        try {
            if (imgUrl != null && !imgUrl.isEmpty()) {
                URL imgUrlLink = new URL(imgUrl);
                contentViewBig.setImageViewBitmap(R.id.image_pic, BitmapFactory.decodeStream(imgUrlLink.openConnection().getInputStream()));
            }
            if (appImgUrl != null && !appImgUrl.isEmpty()) {
                URL appImgUrlLink = new URL(appImgUrl);
                contentViewBig.setImageViewBitmap(R.id.image_app, BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
                contentViewSmall.setImageViewBitmap(R.id.image_app, BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
            }
        }catch (Exception e){

        }
        if (notifTitle != null && !notifTitle.isEmpty()) {
            contentViewBig.setTextViewText(R.id.title, notifTitle);
            contentViewSmall.setTextViewText(R.id.title, notifTitle);
        }

        if (notifText != null && !notifText.isEmpty()) {
            contentViewBig.setTextViewText(R.id.text, notifText);
            contentViewSmall.setTextViewText(R.id.text, notifText);
        }

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(icon)
                .setCustomContentView(contentViewSmall)
                .setCustomBigContentView(contentViewBig)
                .setContentTitle("Custom Notification")
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setWhen(when);

        mNotificationManager.notify(1, notificationBuilder.build());

    }

    public void setRatingNotification(){
        int icon = R.drawable.pnicon; //your app's icon
        long when = System.currentTimeMillis();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        contentViewBig = new RemoteViews(getPackageName(), R.layout.custom_notification_rating);
        contentViewSmall = new RemoteViews(getPackageName(), R.layout.custom_notification_small);
        try {
            if (appImgUrl != null && !appImgUrl.isEmpty()) {
                URL appImgUrlLink = new URL(appImgUrl);
                contentViewBig.setImageViewBitmap(R.id.image_app, BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
                contentViewSmall.setImageViewBitmap(R.id.image_app, BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
                Constants.image.put("AppIcon",BitmapFactory.decodeStream(appImgUrlLink.openConnection().getInputStream()));
            }
        }catch (Exception e){

        }
        if (notifTitle != null && !notifTitle.isEmpty()) {
            contentViewBig.setTextViewText(R.id.title, notifTitle);
            contentViewSmall.setTextViewText(R.id.title, notifTitle);
        }

        if (notifText != null && !notifText.isEmpty()) {
            contentViewBig.setTextViewText(R.id.text, notifText);
            contentViewSmall.setTextViewText(R.id.text, notifText);
        }

        //Set the rating stars
        contentViewBig.setImageViewResource(R.id.star1,R.drawable.outline_star_1);
        contentViewBig.setImageViewResource(R.id.star2,R.drawable.outline_star_1);
        contentViewBig.setImageViewResource(R.id.star3,R.drawable.outline_star_1);
        contentViewBig.setImageViewResource(R.id.star4,R.drawable.outline_star_1);
        contentViewBig.setImageViewResource(R.id.star5,R.drawable.outline_star_1);

        //Set Pending Intents for each star to listen to click

        Intent notificationIntent1 = new Intent(getApplicationContext(), NotificationReceiver.class);
        notificationIntent1.putExtra("click1",true);
        PendingIntent contentIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 1, notificationIntent1, 0);
        contentViewBig.setOnClickPendingIntent(R.id.star1, contentIntent1);

        Intent notificationIntent2 = new Intent(getApplicationContext(), NotificationReceiver.class);
        notificationIntent2.putExtra("click2",true);
        PendingIntent contentIntent2 = PendingIntent.getBroadcast(getApplicationContext(), 2, notificationIntent2, 0);
        contentViewBig.setOnClickPendingIntent(R.id.star2, contentIntent2);

        Intent notificationIntent3 = new Intent(getApplicationContext(), NotificationReceiver.class);
        notificationIntent3.putExtra("click3",true);
        PendingIntent contentIntent3 = PendingIntent.getBroadcast(getApplicationContext(), 3, notificationIntent3, 0);
        contentViewBig.setOnClickPendingIntent(R.id.star3, contentIntent3);

        Intent notificationIntent4 = new Intent(getApplicationContext(), NotificationReceiver.class);
        notificationIntent4.putExtra("click4",true);
        PendingIntent contentIntent4 = PendingIntent.getBroadcast(getApplicationContext(), 4, notificationIntent4, 0);
        contentViewBig.setOnClickPendingIntent(R.id.star4, contentIntent4);

        Intent notificationIntent5 = new Intent(getApplicationContext(), NotificationReceiver.class);
        notificationIntent5.putExtra("click5",true);
        PendingIntent contentIntent5 = PendingIntent.getBroadcast(getApplicationContext(), 5, notificationIntent5, 0);
        contentViewBig.setOnClickPendingIntent(R.id.star5, contentIntent5);

        //Set Pending Intent for Action Button click
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        //Set Pending Intent for Notification click
        Intent actionIntent = new Intent(getApplicationContext(), ButtonActivity.class);
        actionIntent.putExtra("ButtonAction","Submit");
        actionIntent.putExtra("notificationId",1);
        PendingIntent actionPendingIntent = PendingIntent.getActivity(getApplicationContext(),6,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        contentViewBig.setOnClickPendingIntent(R.id.submitBtn,actionPendingIntent);

        //Use the Builder to build notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(icon)
                .setCustomContentView(contentViewSmall)
                .setCustomBigContentView(contentViewBig)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setWhen(when);

        mNotificationManager.notify(1, notificationBuilder.build());

    }

}
