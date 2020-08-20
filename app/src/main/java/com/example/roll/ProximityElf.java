package com.example.roll;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//import org.opencv.android.JavaCameraView;

import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;

import static android.content.Context.SENSOR_SERVICE;

public class ProximityElf implements SensorEventListener {
    public static final String TAG = "com.example.roll";

    private static boolean openCVStarted = false ;




    SensorManager senseManager , senseManager2;
    Context cx;
    ImageView mystep , mystep2, mystep3 , mystep4;
    int didit =1, PackVerf , savor  ;
    static int  direction = 0 ;
    int ElfState , connected;
    ImageView backImg;
    JavaCameraView myjvc;
    Intent i , i2;
    float mywidth;
    ActivityCatcher ActivityMessenger;
    public String CurrentUserActivity ;
    private Handler handler = new Handler();
    long CurrentTime , StartedTime;
    Ball myball;
    Sensor ProximityMagic;
    myEye myservice;
    int Case ,Steps = 0 ;

    ServiceConnection myconnection2 = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myEye.mybinder2 mylclbinder = (myEye.mybinder2) iBinder;
            myservice = mylclbinder.getService();
            //Log.d(TAG,"" + x);
          //  myservice.ball = myball;
            direction = myservice.Diffrence;



        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    ProximityElf(Context ctx , ImageView step , ImageView step2 , ImageView step3 , ImageView step4 , JavaCameraView jvc){
        Case = 1 ;
        Log.d("com.example.opencvtest" , " proximity elf is created ");
        this.cx = ctx;
        mystep = step;
        mystep2 = step2;
        mystep3 = step3;
        mystep4 = step4;

        myjvc = jvc;
        senseManager2 = (SensorManager)ctx.getSystemService(SENSOR_SERVICE); // initialization of sensorManager

        Sensor linearAcceleration2;


        // use this link to know more
        //https://developer.android.com/guide/topics/sensors/sensors_motion.html

        //linearAcceleration = senseManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); // initialization of LINEAR_ACCELERATION
        linearAcceleration2 = senseManager2.getDefaultSensor(Sensor.TYPE_PROXIMITY);


        //TYPE_LINEAR_ACCELERATION can be changed with the desired value
        //eg
        //TYPE_ACCELEROMETER
        //TYPE_GRAVITY
        //TYPE_GYROSCOPE
        //etc

        senseManager2.registerListener(this,linearAcceleration2,SensorManager.SENSOR_DELAY_NORMAL);

    }

    ProximityElf(Context ctx , float width ){
        mywidth = width;
        Case = 2 ;
        this.cx = ctx;
        senseManager = (SensorManager)ctx.getSystemService(SENSOR_SERVICE);
        ProximityMagic = senseManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        senseManager.registerListener(this,ProximityMagic,SensorManager.SENSOR_DELAY_NORMAL);

        i = new Intent(cx, ActivityCatcher.class);
        i2 = new Intent(cx, myEye.class);
        ActivityMessenger = ActivityCatcher.getSharedInstance();
        cx.startService(i);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
            if ( Case == 2) {
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] >= -4 && event.values[0] <= 4 && ElfState == 0) {
                        if (didit == 1) {
                            savor = 0;
                            didit = didit + 1;
                            if (ElfState == 0 && myball == null) {
                                //Toast.makeText(cx, "On", Toast.LENGTH_SHORT).show();
                                getpackage();
                                Toast.makeText(cx, CurrentUserActivity, Toast.LENGTH_LONG).show();
                                if (PackVerf == 0) {
                                    PackVerf = 1;
                                    handler.post(runnable2);
                                }
                            }

                        } else {
                            if (didit == 2) {
                                connected = 1;

                                //set the background and activate the EYE
                                // 01 set the background according to package switch (package name).

                                switch (CurrentUserActivity) {
                                    case "com.sec.android.app.launcher":
                                        myball.setMyBackground2(1);
                                        break;

                                    case "com.android.systemui":
                                        myball.setMyBackground2(1);
                                        break;

                                    case "com.samsung.android.dialer":
                                        myball.setMyBackground2(2);
                                        break;

                                    case "com.google.android.music":
                                        myball.setMyBackground2(3);
                                        break;

                                    case "com.sec.android.app.clockpackage":
                                        myball.setMyBackground2(4);
                                        break;

                                    default:
                                        connected = 0;
                                        break;

                                }
                                if (connected == 1) {
                                    //verifiying if we are in a identified case to lanch myeye

                                    cx.bindService(i2, myconnection2, cx.BIND_AUTO_CREATE);
                                }


                                didit = 1;
                            }
                        }
                        //////////////////////////////////////////////
                        //timer for 2 seconds

                        ElfState = 1;


                    } else {
                        //far
                        if (ElfState == 1) {
                            //             Toast.makeText(cx, "Off", Toast.LENGTH_SHORT).show();
                            if (connected == 1) {   // direction =  myservice.Diffrence;
                                /*cx.stopService(i2); */
                                connected = 0;
                                myball.moveTheball(myservice.Diffrence);
                                cx.unbindService(myconnection2);
                                savor = 1;
                                handler.post(runnable3);
                            }
                        }
                        ElfState = 0;
                        StartedTime = System.currentTimeMillis();
                        CurrentTime = StartedTime + 3000;
                        if (savor == 0) {
                            handler.post(runnable);
                        }
                    }
                }

            }

              else {

        if(Case == 1){
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                if (event.values[0] >= -4 && event.values[0] <= 4  ) {
                        if(Steps == 0){
                            myball = new Ball(cx , 2);
                        }
                    //near
                    Steps = Steps + 1;
                    Toast.makeText(cx, "On", Toast.LENGTH_SHORT).show();
                    if(Steps == 1){
                    mystep.setImageResource(R.drawable.validated1);}
                    if(Steps == 2){
                        mystep3.setImageResource(R.drawable.validated3);
                    }
                    myjvc.enableView();

                } else {
                    //far
                    Toast.makeText(cx, "Off", Toast.LENGTH_SHORT).show();
                    if(Steps == 1){
                        mystep2.setImageResource(R.drawable.validated2);}
                    if(Steps == 2){
                        mystep4.setImageResource(R.drawable.validated4);
                        Steps = 0 ;
                        myjvc.disableView();
                        myball = null;
                    }







                }
            }
        }


            }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }



    public void getpackage(){

        if(ActivityMessenger != null){
           if( ActivityMessenger.packagecurrent != null ) {
        CurrentUserActivity = ActivityMessenger.packagecurrent;}}
    }



    final Runnable runnable3 = new Runnable() {
        @Override
        public void run() {
            if(myball != null){

            if(myball.myservice.isremoved == 1){
                Log.d(TAG ," isremoved ===== "+ myball.myservice.isremoved);
                myball.doSomething(myball.mydif);
            }
            else{
                Log.d(TAG ," isremoved ===== "+ myball.myservice.isremoved);
                handler.post(runnable3);
            }

            }
            if(myball == null){
                handler.removeCallbacks(runnable3);
            }



        }
    };




    final Runnable  runnable = new Runnable() {
        @Override
        public void run() {
            StartedTime = System.currentTimeMillis();
            if(ElfState == 1){
                if(StartedTime == CurrentTime && didit != 1 && connected == 0){
                    if(myball.myservice.isremoved != 1){
                    myball.distroyball();}

                    myball = null ;
                    didit = 1 ;
                }
                else{
                    handler.post(runnable);
                }

            }
            else {
                if(ElfState == 0){
                    if(StartedTime == CurrentTime && myball != null) {
                        if(myball.myservice.isremoved != 1){
                            myball.distroyball();}
                        myball = null ;

                        didit = 1 ;
                    }
                    else{
                        handler.post(runnable);
                    }
                }
            }

        }




    };

    final Runnable  runnable2 = new Runnable() {
        @Override
        public void run() {
            if(CurrentUserActivity == null){

                PackVerf = 0 ;
                didit = didit - 1 ;
            }
            else {
                if (myball == null) {
                    PackVerf = 0 ;
                    myball = new Ball(cx , mywidth);
                    StartedTime = System.currentTimeMillis();
                    CurrentTime = StartedTime + 3000;
                    handler.post(runnable);
                }

            }
        }






    };



  /*  final Runnable AllRunKiller = new Runnable() {
        @Override
        public void run() {
            if(myball == null){
                handler.removeCallbacks(runnable);
                handler.removeCallbacks(runnable3);
            }


        }
    };*/




    public ProximityElf distroyElf(){
        if( myball != null){  if(myball.exists == 1 ) {myball.distroyball();  handler.removeCallbacks(runnable); handler.removeCallbacks(runnable2);}}
       if(Case == 1){ senseManager2.unregisterListener(this);}

        if(Case == 2){
            senseManager.unregisterListener(this);
        cx.stopService(i);}
        return null;
    }



}

