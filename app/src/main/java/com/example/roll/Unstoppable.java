package com.example.roll;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

public class Unstoppable extends Application {
    String TAG = "com.example.roll";
    public static final String CHANNEL_ID = "exampleServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();


    }


    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationManager manager = getSystemService(NotificationManager.class);

            Log.d(TAG , "entered");

            if(!( manager.getNotificationChannel(CHANNEL_ID).getClass().toString()).equals("Unstoppable") ){
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Roll is ON!",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
                manager.createNotificationChannel(serviceChannel);
            }

            else {
                Toast.makeText(getApplicationContext(), "deja On", Toast.LENGTH_LONG).show();
            }


        }



    }




}
