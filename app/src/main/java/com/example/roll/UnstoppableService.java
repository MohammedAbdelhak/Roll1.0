package com.example.roll;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.roll.Unstoppable.CHANNEL_ID;

public class UnstoppableService extends Service {
    String TAG = "com.example.roll";
    ProximityElf Elf1;
    float width;
    int R , L ;
    @Override
    public void onCreate() {
        super.onCreate();
    }






    @Override
    public void onDestroy() {
        Elf1.distroyElf();
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG , "started");
         width =  intent.getFloatExtra("width" , width);
         R = intent.getIntExtra("Rval" , 0);
        L = intent.getIntExtra("Lval" , 0);

        Elf1 = new ProximityElf(this ,width , R , L );
        Intent notificationintent = new Intent(this , Activation.class);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this , 0 , notificationintent , 0);
        notificationintent.putExtra("Rval" , R);
        notificationintent.putExtra("Lval" , L);

        Notification notification = new NotificationCompat.Builder(this , CHANNEL_ID)
                .setContentTitle("Roll is Running")
                .setContentText("Click to Stop!")
                .setSmallIcon(com.example.roll.R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1 , notification);


        return START_NOT_STICKY;



    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
