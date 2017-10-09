package com.app.darshan.pushnotificationapp;

import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Date;
import java.util.HashMap;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;

public class ButtonActivity extends AppCompatActivity {

    TextView ratingNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
//        Intent intent = getIntent();
//        Uri data = intent.getData();
//        String deepLink = data.toString();
//        Log.e("DeepLink",deepLink);
//        String tag =  deepLink.substring(23);
//        HashMap<String, Object> deepLinkMap = new HashMap<String, Object>();
//        deepLinkMap.put("Tag",tag);
//        cleverTapAPI.event.push("DeepLink Captured",deepLinkMap);pwd



//        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer();
//        sanitizer.setAllowUnregisteredParamaters(true);
//        sanitizer.parseUrl(deepLink);
//        String param1 = sanitizer.getValue("param1");
//        String param2 = sanitizer.getValue("param2");
//        String param3 = sanitizer.getValue("param3");
//        HashMap<String, Object> deepLinkMap = new HashMap<String, Object>();
//        deepLinkMap.put("Param1", param1);
//        deepLinkMap.put("Param2", param2);
//        deepLinkMap.put("Param3",param3);
//        cleverTapAPI.event.push("DeepLink Captured",deepLinkMap);


//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        notificationManager.cancel(extras.getInt("notificationId"));
//        ratingNumber = (TextView)findViewById(R.id.ratingNumber);
//        if(intent.getStringExtra("ButtonAction").equals("Submit"))
//            ratingNumber.setText(Constants.rating+"");
//
//        HashMap<String, Object> rating = new HashMap<String, Object>();
//        rating.put("Rating", Constants.rating);
//
//        cleverTapAPI.event.push("Rating",rating);

    }
}
