package com.example.roll;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class FloatIt extends Service {
    WindowManager.LayoutParams wmpar, wmpar2;
    int myX, verf = 0 , dif = 0 , isremoved = 0 ;
    private IBinder binderr = new mybinder();
    ImageView myimg ,logo;
    WindowManager wm ,wm2;
    int mytransx, destination = 0 ;
    LinearLayout myln;
    public static final String TAG = "com.example.roll";
    LinearLayout.LayoutParams llpar;
    int BallPos = 255;
    WindowManager.LayoutParams updatedParams2;
    private Handler handler2 = new Handler();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binderr;
    }


    @Override
    public void onCreate() {

        super.onCreate();

        //myimg.setImageResource(R.drawable.hhhhhhh);

        myimg =  new ImageView(this);
        logo =  new ImageView(this);

        //myimg.setImageResource(R.drawable.hhhhhhh);

        myimg.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        logo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
        logo.setImageResource(R.mipmap.ic_launcher_foreground);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        myln = new LinearLayout(this);
        llpar = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        myln.setBackgroundResource(R.drawable.circle_shape);
        myln.setLayoutParams(llpar);
        wmpar = new WindowManager.LayoutParams(100, 100, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        wmpar2 = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 245, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        myln.addView(logo);
        wmpar.x = 0;
        wmpar.y = -585;
        wmpar2.x = 0;
        wmpar2.y = -700;
        wm.addView(myimg , wmpar2);
        wm.addView(myln, wmpar);
        isremoved = 0 ;
        updatedParams2 = wmpar;





        myln.setOnTouchListener(new View.OnTouchListener() {

            WindowManager.LayoutParams updatedParams = wmpar;
            int x, y;
            float touchX;


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                AudioManager mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = updatedParams.x;
                        y = updatedParams.y;

                        touchX = motionEvent.getRawX();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        // here you work your openCV magic ! trust me its really easy !
                        updatedParams.x = x + (int) (motionEvent.getRawX() - touchX);
                        //    Log.d(TAG, " this " + updatedParams.x);
                        myX = updatedParams.x;
                        wm.updateViewLayout(myln, updatedParams);
                        //myln.setX(wmpar.x);

                        break;

                    case MotionEvent.ACTION_UP:
                        // Log.d(TAG, " x =  " + updatedParams.x);


                        if (updatedParams.x < 255) {
                            updatedParams.x = 0;
                            wm.updateViewLayout(myln, updatedParams);
                            break;
                        }
                        if(updatedParams.x > 255){

                            AlarmManager aManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            Intent intent = new Intent(getBaseContext(), FloatIt.class);
                            PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            aManager.cancel(pIntent);



                        }

                }
                return false;
            }});


   }



    public class mybinder extends Binder {
        public FloatIt getService() {
            return FloatIt.this;
        }

    }



    public void SetImageBackground(ImageView myback){

        myimg.setImageDrawable(myback.getDrawable());

    }



    @Override
    public boolean onUnbind(Intent intent) {
        destination = 0 ;
        if(isremoved == 0){
        wm.removeView(myln);
        wm.removeView(myimg);}
        return super.onUnbind(intent);
    }

    public void moveball(int dif){
       this.dif = dif ;
       handler2.post(runnable3);
    }


    final Runnable  runnable3 = new Runnable() {

        @Override
        public void run() {

            if(dif == 1 ){
                if(wmpar.x < BallPos) {
                    wmpar.x = wmpar.x + 10;

                    wm.updateViewLayout(myln, wmpar);
                    handler2.post(runnable3);

                }
                else{
                    if( wmpar.x >= BallPos){
                        if(isremoved == 0){
                            isremoved = 1 ;
                            Log.d(TAG, "isremoved on methode =  "+ isremoved);
                        wm.removeView(myln);
                        wm.removeView(myimg);
                        }
                        destination = 1 ;
                        handler2.removeCallbacks(runnable3);

                    }
                }


            }

            else {
                if(dif == 2 ){
                    if(wmpar.x > -BallPos) {
                        wmpar.x = wmpar.x - 10;
                        wm.updateViewLayout(myln, wmpar);
                        handler2.post(runnable3);

                    }
                    else{
                        if( wmpar.x <= -BallPos){
                            if(isremoved == 0){
                                isremoved = 1 ;
                            wm.removeView(myln);
                            wm.removeView(myimg);
                            }
                            destination = 1 ;
                            handler2.removeCallbacks(runnable3);

                        }
                    }
                }
            }



            }







    };

}






