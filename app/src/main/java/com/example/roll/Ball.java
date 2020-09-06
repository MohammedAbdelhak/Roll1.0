package com.example.roll;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;

import com.example.roll.FloatIt.mybinder;


public class Ball extends Activity {
    FloatIt myservice;
    Context c;
    int UserAct , mydif  , isconnectedtoServ;
    ImageView backImg ;
    private float halfW ;
    int r , l ,exists = 0;
    AudioManager mAudioManager;
    String SpecialCommand;

    ServiceConnection myconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mybinder mylclbinder = (mybinder) iBinder;
            myservice = mylclbinder.getService();
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
        Log.d("myTAG" , "distroyed is called");

    }








    public void setMyBackground(final ImageView myback) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myservice.SetImageBackground(myback );
            }
        });
    }

    public void setMyBackground3(final ImageView myback , final int Rvalue , final int Lvalue) {
        r = Rvalue;
        l = Lvalue ;
        if(Rvalue == 0 && Lvalue == 0){this.setMyBackground(myback);}
        else{
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myservice.SetImageBackground2(myback , Rvalue , Lvalue);
            }
        });
    }}

    public void setMyBackground2(int UserAct , int R , int L) {
            this.UserAct = UserAct;
        switch (UserAct) {
            case 1:
                MainMethode(0 , 1 , R , L);
                break;
            case 3:
                MusicMethode(0, 1);
                break;
            case 5 :  NavigateMethod( 0 , 1);
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

    public void MainMethode(int diff , int step , int R , int L){
        if(step == 1 ){
            if(L == 0 && R == 0 ){
            backImg.setImageResource(com.example.roll.R.drawable.mainscenario ); this.setMyBackground3(backImg , R , L);}
            else {
                backImg.setImageResource(com.example.roll.R.drawable.allcontainer );
            this.setMyBackground3(backImg , R , L);}

        }
        else {
            if(step == 2){
                if (diff == 1) {
                    if(r== 0){
                    Intent phoneint = c.getPackageManager().getLaunchIntentForPackage("com.sec.android.app.camera");
                    c.startActivity(phoneint);}
                    else {
                        taskchooser(r);
                    }
                } else {
                    if (diff == 2) {
                        if(l == 0 ){
                        Intent phoneint = c.getPackageManager().getLaunchIntentForPackage("com.samsung.android.dialer");
                        c.startActivity(phoneint);}
                        else {
                            taskchooser(l);
                        }

                    }
                }
                distroyball();

            }
        }

    }

    public void doSomething( int dif ){

        switch (UserAct) {
            case 1:  /*main*/
                MainMethode(dif , 2 , 0 ,0);
                break;
            case 3: /*music */
                MusicMethode(dif, 2);
                break;
                case 5 :
                NavigateMethod(dif , 2);
                break;

        }







    }

    public void taskchooser(int t){
        switch (t){
            case 1 : Intent phoneint = c.getPackageManager().getLaunchIntentForPackage("com.samsung.android.dialer"); c.startActivity(phoneint); break;
            case 2 : Intent cameraint = c.getPackageManager().getLaunchIntentForPackage("com.sec.android.app.camera"); c.startActivity(cameraint);break;
            case 3 : Intent musicint  = c.getPackageManager().getLaunchIntentForPackage("com.google.android.music"); c.startActivity(musicint); break;
            case 4 : Intent calendarint = c.getPackageManager().getLaunchIntentForPackage("com.samsung.android.calendar"); c.startActivity(calendarint); break;
            case 5 : Intent msgint = c.getPackageManager().getLaunchIntentForPackage("com.samsung.android.messaging"); c.startActivity(msgint); break;
            case 6 : Intent noteint = c.getPackageManager().getLaunchIntentForPackage("com.samsung.android.app.notes"); c.startActivity(noteint);break;
        }
    }

    public void NavigateMethod(int diff , int step){
        if(step == 1){
            backImg.setImageResource(R.drawable.homereturnpanel);
            this.setMyBackground(backImg);
        }
        else{
            if (step == 2 ){

                if (diff == 1) {
                    Intent i = new Intent(Intent.ACTION_MAIN);
                    i.addCategory(Intent.CATEGORY_HOME);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    c.startActivity(i);
                } else {
                    if (diff == 2) {
                        Intent i = new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        c.startActivity(i);
                    }
                }
                distroyball();




        }}

   }



}




