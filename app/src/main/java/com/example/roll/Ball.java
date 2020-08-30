package com.example.roll;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

import com.example.roll.FloatIt.mybinder;

//import static com.example.roll.R.drawable.hhhhhhh;

public class Ball extends Activity {
    FloatIt myservice;
    Context c;
    ImageView Background;
    int UserAct , mydif , myX , isconnectedtoServ;
    ImageView backImg ;
    private float halfW ;
    private AnimatorSet animatorSet;
    private ObjectAnimator lftToRgt,rgtToLft;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    int exists = 0;
    AudioManager mAudioManager, mAlarmManager;
    private static final String TAG = "com.example.roll";
    String SpecialCommand;

    ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mybinder mylclbinder = (mybinder) iBinder;
            myservice = mylclbinder.getService();
            //Log.d(TAG,"" + x);
            myX= myservice.mytransx;
            if(halfW == 2){
                myservice.BallPos = 150;
            myservice.wmpar.y = -626;
            myservice.wm.updateViewLayout(myservice.myln,myservice.wmpar);
            }
            isconnectedtoServ = 1 ;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isconnectedtoServ = 0;
        }
    };


    Ball(Context cx , float width) {
        c = cx;
        halfW = width;
        exists = 1;
        Intent i = new Intent(cx, FloatIt.class);
        c.bindService(i, myconnection, cx.BIND_AUTO_CREATE);
        backImg = new ImageView(cx);
    }

    public void distroyball() {
        exists = 0;
        if(isconnectedtoServ == 1){
        c.unbindService(myconnection);
        isconnectedtoServ = 0 ;
        }
    }












    public void setMyBackground(final ImageView myback) {
        Background = myback;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myservice.SetImageBackground(myback);
            }
        });
    }


    public void setMyBackground2(int UserAct) {
            this.UserAct = UserAct;
        switch (UserAct) {
            case 1:  /*main*/ UserAct = 1 ;
                MainMethode(0 , 1);
                break;
            case 2:             UserAct = 2 ;
                break;
            case 3: /*music */  UserAct = 3 ;
                MusicMethode(0, 1);
                break;
            case 4: /*alarm */  UserAct = 4 ;
                AlarmMethode(0, 1);
                break;


        }



    }

    public void moveTheball(int dif ){
        mydif = dif;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myservice.moveball(mydif);
            }
        });



        }


    public void MusicMethode(int diff, int step) {
        mAudioManager = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
        if (step == 1) {
            if (mAudioManager.isMusicActive()) {
                backImg.setImageResource(R.drawable.musicscenariopause);
                SpecialCommand = "pause";
            } else {
                // InActive already !
                backImg.setImageResource(R.drawable.musicscenario);
                SpecialCommand = "play";
            }

            this.setMyBackground(backImg);
        } else {
            if (step == 2) {
                if (diff == 1) {
                    Intent i = new Intent("com.android.music.musicservicecommand");
                    i.putExtra("command", "next");
                    c.sendBroadcast(i);

                } else {
                    if (diff == 2) {
                        Intent i = new Intent("com.android.music.musicservicecommand");
                        i.putExtra("command", SpecialCommand);
                        c.sendBroadcast(i);

                    }
                }
                distroyball();
            }
        }
    }


    public void AlarmMethode(int diff, int step) {

        if (step == 1) {
            backImg.setImageResource(R.drawable.alaramscenario);
            this.setMyBackground(backImg);

        } else {
            if (step == 2) {
                if (diff == 1) {
                    alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
                    Intent intent = new Intent(getBaseContext(), Ball.class);
                    PendingIntent pIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmMgr.cancel(pIntent);
                } else {
                    if (diff == 2) {


                        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), Ball.class);
                        alarmIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, intent, 0);

                        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                SystemClock.elapsedRealtime() +
                                        120 * 1000, alarmIntent);
                    }
                }
                distroyball();

            }
        }


    }


    public void MainMethode(int diff , int step){
        if(step == 1 ){
            backImg.setImageResource(R.drawable.mainscenario);
            this.setMyBackground(backImg);

        }
        else {
            if(step == 2){
                if (diff == 1) {
                    Intent phoneint = c.getPackageManager().getLaunchIntentForPackage("com.sec.android.app.camera");
                    c.startActivity(phoneint);
                } else {
                    if (diff == 2) {

                        Intent phoneint = c.getPackageManager().getLaunchIntentForPackage("com.samsung.android.dialer");
                        c.startActivity(phoneint);

                    }
                }
                distroyball();


            }
        }

    }

    public void CallMethode (int diff , int step){
             if(step == 1 ){
                 backImg.setImageResource(R.drawable.phonescenario);
                  this.setMyBackground(backImg);

        }
        else {
             if(step == 2){



                 if (diff == 1) {
                    //respond


                } else {
                    if (diff == 2) {
                    //end call



                    }
                }
                distroyball();


            }
        }




    }

    public void doSomething( int dif ){
        Log.d(TAG , "UserAct : "+ UserAct);
        Log.d(TAG , "dif : "+ dif);
        switch (UserAct) {
            case 1:  /*main*/ UserAct = 1 ;
                MainMethode(dif , 2);
                break;
            case 2:  CallMethode(dif , 2); UserAct = 2 ;
                break;
            case 3: /*music */  UserAct = 3 ;
                MusicMethode(dif, 2);
                break;
            case 4: /*alarm */  UserAct = 4 ;
                AlarmMethode(dif, 2);
                break;

        }




    }

}




