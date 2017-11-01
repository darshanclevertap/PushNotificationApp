package com.app.darshan.pushnotificationapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.net.URL;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by darshanpania on 29/06/17.
 */

public class NotificationReceiver extends BroadcastReceiver {
    boolean clicked1=true,clicked2=true,clicked3=true,clicked4=true,clicked5 = true;
    RemoteViews contentViewBig;
    RemoteViews contentViewSmall;
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        int icon = R.drawable.pnicon; //your app's icon
        long when = System.currentTimeMillis();

        if(intent.getExtras()!=null) {

            //Set RemoteViews again
            contentViewBig = new RemoteViews(context.getPackageName(), R.layout.custom_notification_rating);
            contentViewSmall = new RemoteViews(context.getPackageName(), R.layout.custom_notification_small);

            //Set the App Icon again
            try {
                if (Constants.appImgUrl != null && !Constants.appImgUrl.isEmpty()) {
                    contentViewBig.setImageViewBitmap(R.id.image_app, Constants.image.get("AppIcon"));
                    contentViewSmall.setImageViewBitmap(R.id.image_app, Constants.image.get("AppIcon"));
                }
            }catch (Exception e){

            }

            //Set Notification Title
            if (Constants.notifTitle != null && !Constants.notifTitle.isEmpty()) {
                contentViewBig.setTextViewText(R.id.title, Constants.notifTitle);
                contentViewSmall.setTextViewText(R.id.title, Constants.notifTitle);
            }

            //Set Notification Text
            if (Constants.notifText != null && !Constants.notifText.isEmpty()) {
                contentViewBig.setTextViewText(R.id.text, Constants.notifText);
                contentViewSmall.setTextViewText(R.id.text, Constants.notifText);
            }

            //Check which star is clicked and calculate rating
            if(clicked1 == intent.getBooleanExtra("click1",false)) {
                contentViewBig.setImageViewResource(R.id.star1, R.drawable.filled_star_1);
                Constants.rating=1;
                clicked1 = false;
            }else{
                contentViewBig.setImageViewResource(R.id.star1, R.drawable.outline_star_1);
            }
            if(clicked2 == intent.getBooleanExtra("click2",false)) {
                contentViewBig.setImageViewResource(R.id.star1, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star2, R.drawable.filled_star_1);
                Constants.rating=2;
                clicked2 = false;
            }else{
                contentViewBig.setImageViewResource(R.id.star2, R.drawable.outline_star_1);
            }
            if(clicked3 == intent.getBooleanExtra("click3",false)) {
                contentViewBig.setImageViewResource(R.id.star1, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star2, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star3, R.drawable.filled_star_1);
                Constants.rating=3;
                clicked3 = false;
            }else{
                contentViewBig.setImageViewResource(R.id.star3, R.drawable.outline_star_1);
            }
            if(clicked4 == intent.getBooleanExtra("click4",false)) {
                contentViewBig.setImageViewResource(R.id.star1, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star2, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star3, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star4, R.drawable.filled_star_1);
                Constants.rating=4;
                clicked4 = false;
            }else{
                contentViewBig.setImageViewResource(R.id.star4, R.drawable.outline_star_1);
            }
            if(clicked5 == intent.getBooleanExtra("click5",false)) {
                contentViewBig.setImageViewResource(R.id.star1, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star2, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star3, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star4, R.drawable.filled_star_1);
                contentViewBig.setImageViewResource(R.id.star5, R.drawable.filled_star_1);
                Constants.rating=5;
                clicked5 = false;
            }else{
                contentViewBig.setImageViewResource(R.id.star5, R.drawable.outline_star_1);
            }

            ////Set Pending Intents for each star to listen to click
            Intent notificationIntent1 = new Intent(context, NotificationReceiver.class);
            notificationIntent1.putExtra("click1",true);
            PendingIntent contentIntent1 = PendingIntent.getBroadcast(context, 1, notificationIntent1, 0);
            contentViewBig.setOnClickPendingIntent(R.id.star1, contentIntent1);

            Intent notificationIntent2 = new Intent(context, NotificationReceiver.class);
            notificationIntent2.putExtra("click2",true);
            PendingIntent contentIntent2 = PendingIntent.getBroadcast(context, 2, notificationIntent2, 0);
            contentViewBig.setOnClickPendingIntent(R.id.star2, contentIntent2);

            Intent notificationIntent3 = new Intent(context, NotificationReceiver.class);
            notificationIntent3.putExtra("click3",true);
            PendingIntent contentIntent3 = PendingIntent.getBroadcast(context, 3, notificationIntent3, 0);
            contentViewBig.setOnClickPendingIntent(R.id.star3, contentIntent3);

            Intent notificationIntent4 = new Intent(context, NotificationReceiver.class);
            notificationIntent4.putExtra("click4",true);
            PendingIntent contentIntent4 = PendingIntent.getBroadcast(context, 4, notificationIntent4, 0);
            contentViewBig.setOnClickPendingIntent(R.id.star4, contentIntent4);

            Intent notificationIntent5 = new Intent(context, NotificationReceiver.class);
            notificationIntent5.putExtra("click5",true);
            PendingIntent contentIntent5 = PendingIntent.getBroadcast(context, 5, notificationIntent5, 0);
            contentViewBig.setOnClickPendingIntent(R.id.star5, contentIntent5);

            //Set Pending intent for the Notification
            Intent notificationIntent = new Intent(context, MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

            //Set Pending Intent for Action Button click
            Intent actionIntent = new Intent(context, ButtonActivity.class);
            actionIntent.putExtra("ButtonAction","Submit");
            actionIntent.putExtra("notificationId",1);
            PendingIntent actionPendingIntent = PendingIntent.getActivity(context,6,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            contentViewBig.setOnClickPendingIntent(R.id.submitBtn,actionPendingIntent);

            //Use the Builder to build notification
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(icon)
                    .setCustomBigContentView(contentViewBig)
                    .setCustomContentView(contentViewSmall)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setWhen(when);

            mNotificationManager.notify(1, notificationBuilder.build());
        }
    }
}
