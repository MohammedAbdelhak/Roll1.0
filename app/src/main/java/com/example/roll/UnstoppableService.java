package com.example.roll;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.example.roll.Unstoppable.CHANNEL_ID;

public class UnstoppableService extends Service {
    String TAG = "com.example.roll";
    ProximityElf Elf1;
    float width;

    @Override
    public void onCreate() {
        super.onCreate();
    }



    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            }
            else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Elf1 = new ProximityElf(getApplicationContext() ,width);

            }
            else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
                if(Elf1 != null ){
                    Elf1.distroyElf();
                }
          /*  Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
            i.putExtra(Intent.EXTRA_KEY_EVENT,new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_HEADSETHOOK));
            context.sendOrderedBroadcast(i, null);

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                Class c = Class.forName(tm.getClass().getName());
                Method m = c.getDeclaredMethod("getITelephony");
                m.setAccessible(true);
                ITelephony telephonyService = (ITelephony) m.invoke(tm);
                Bundle bundle = intent.getExtras();
                String phoneNumber = bundle.getString("incoming_number");
                Log.v(TAG, "callllll !!!!!!!!!!!!!!!!!! " + phoneNumber);
                telephonyService.answerRingingCall();

            } catch (Exception e) {
                e.printStackTrace();
            }*/
            }
        }
    };


    @Override
    public void onDestroy() {
        Elf1.distroyElf();
        super.onDestroy();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG , "started");
         width =  intent.getFloatExtra("width" , width);
        Elf1 = new ProximityElf(this ,width);
        Intent notificationintent = new Intent(this , Activation.class);
        PendingIntent pendingIntent =  PendingIntent.getActivity(this , 0 , notificationintent , 0);

        Notification notification = new NotificationCompat.Builder(this , CHANNEL_ID)
                .setContentTitle("Roll is Running")
                .setContentText("Click to Stop!")
                .setSmallIcon(R.mipmap.ic_launcher)
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
