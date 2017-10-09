package com.app.darshan.pushnotificationapp;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import static com.app.darshan.pushnotificationapp.MainActivity.cleverTapAPI;

/**
 * Created by darshanpania on 10/08/17.
 */

public class WebAppInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
    }

    /** Fire event to CleverTap from WebView**/
    @JavascriptInterface
    public void fireCTEvent(String eventName){
        cleverTapAPI.event.push(eventName);
    }
}
