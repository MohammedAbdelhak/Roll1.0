package com.example.roll;


import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;


import androidx.annotation.Nullable;
public class ActivityCatcher extends AccessibilityService {
    private static final String TAG   ="com.example.roll";
    public static String packagecurrent = null , oldpackage = null ;
    public static ActivityCatcher sSharedInstance ;


    @Override
    public void onServiceConnected() {
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        Log.d(TAG , " serivce is connected ! " );

        // Set the type of events that this service wants to listen to. Others
        // won't be passed to this service.
        info.eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED;

        // If you only want this service to work with specific applications, set their
        // package names here. Otherwise, when the service is activated, it will listen
        // to events from all applications.


        // Set the type of feedback your service will provide.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_ALL_MASK;

        // Default services are invoked only if no package-specific ones are present
        // for the type of AccessibilityEvent generated. This service *is*
        // application-specific, so the flag isn't necessary. If this was a
        // general-purpose service, it would be worth considering setting the
        // DEFAULT flag.

        info.flags = AccessibilityServiceInfo.DEFAULT;

        info.notificationTimeout = 100;

        this.setServiceInfo(info);
        sSharedInstance = this;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if(accessibilityEvent.getPackageName().toString() == packagecurrent){
            Log.d(TAG , "yeeeeeep");
        }
        packagecurrent = accessibilityEvent.getPackageName().toString();
        if ((packagecurrent.equalsIgnoreCase("com.example.roll") || packagecurrent.equalsIgnoreCase("com.android.systemui") ) || packagecurrent.equalsIgnoreCase("com.android.systemui")  && oldpackage != null) {
            packagecurrent = oldpackage;
        }
        else {
            oldpackage = packagecurrent;
        }
        Log.d(TAG , "===" + packagecurrent );
    }



    @Override
    public void onInterrupt() {
    }

    public static ActivityCatcher getSharedInstance() {
        return sSharedInstance;
    }





}


