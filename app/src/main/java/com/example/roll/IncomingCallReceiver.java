package com.example.roll;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

public class IncomingCallReceiver extends BroadcastReceiver {
    private ITelephony telephonyService;
    String TAG = "com.example.roll";
    private String blacklistednumber = "+458664455";

    @Override
  /*  public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) return;

        String extraState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (extraState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String incomingNumber =intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                //---answer the call---
                Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
                i.putExtra(Intent.EXTRA_KEY_EVENT,new KeyEvent(KeyEvent.ACTION_UP,KeyEvent.KEYCODE_HEADSETHOOK));
                context.sendOrderedBroadcast(i, null);

        }
        return;
    }*/
    public void onReceive(Context context, Intent intent) {

        if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            showToast(context,"Call started...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE)){
            showToast(context,"Call ended...");
        }
        else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
            showToast(context,"Incoming call...");
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

    void showToast(Context context,String message){
        Toast toast=Toast.makeText(context,message,Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}