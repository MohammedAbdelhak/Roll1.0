package com.example.roll;


import android.app.Activity;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.opencv.android.JavaCameraView;

import androidx.annotation.RequiresApi;

//import org.opencv.android.JavaCameraView;

public class ProximityElf extends Activity implements SensorEventListener {
    public static final String TAG = "com.example.roll";

    private static boolean openCVStarted = false ;



    KeyguardManager myKM;
    SensorManager senseManager , senseManager2;
    Context cx;
    ImageView mystep , mystep2, mystep3 , mystep4;
    static int  direction = 0 ;
    int ElfState , connected , IsHapenning ,  FlashState = 0  , Case ,Steps = 0 ,  didit =1, PackVerf , savor  , Rvalue , Lvalue  ;
    JavaCameraView myjvc;
    Intent  i2;
    float mywidth;
    long T1 , T2 ;
    ActivityCatcher ActivityMessenger;
    public String CurrentUserActivity ;
    private Handler handler = new Handler();
    long CurrentTime , StartedTime;
    Ball myball;
    Sensor ProximityMagic;

    myEye myservice;
    LinearLayout myln ;


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

    ProximityElf(Context ctx , ImageView step , ImageView step2 , ImageView step3 , ImageView step4 , JavaCameraView jvc  , LinearLayout ln){
        myln = ln ;
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

    ProximityElf(Context ctx , float width , int Rval , int Lval){
        IsHapenning =0;
        Rvalue = Rval ;
        Lvalue = Lval ;
        mywidth = width;
        Case = 2 ;
        this.cx = ctx;
        myKM = (KeyguardManager) cx.getSystemService(Context.KEYGUARD_SERVICE);
        senseManager = (SensorManager)ctx.getSystemService(SENSOR_SERVICE);
        ProximityMagic = senseManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        senseManager.registerListener(this,ProximityMagic,SensorManager.SENSOR_DELAY_NORMAL);

        i2 = new Intent(cx, myEye.class);
        ActivityMessenger = ActivityCatcher.getSharedInstance();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(IsHapenning == 0) {
            if (Case == 2) {
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    if (event.values[0] >= -4 && event.values[0] <= 4 && ElfState == 0) {
                        if (didit == 1) {
                            T1 = System.currentTimeMillis();
                            savor = 0;
                            didit = didit + 1;
                            if (ElfState == 0 && myball == null) {

                                getpackage();
                                Toast.makeText(cx , CurrentUserActivity , Toast.LENGTH_LONG).show();
                                if(CurrentUserActivity.equals("com.sec.android.app.camera") ||CurrentUserActivity.equals("com.example.camroll") ){
                                    PackVerf = 1 ;

                                }

                                if (PackVerf == 0) {
                                    PackVerf = 1;
                                    handler.post(runnable2);
                                }
                            }

                        } else {
                            if (didit == 2) {
                                T2 =  System.currentTimeMillis();
                                if(T2 - T1 < 1000 && myKM.inKeyguardRestrictedInputMode()){
                                    CameraManager cameraManager = (CameraManager) cx.getSystemService(Context.CAMERA_SERVICE);
                                    try{
                                        String cameraId = cameraManager.getCameraIdList()[0];
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M  ) {
                                            if(FlashState == 0){
                                                cameraManager.setTorchMode(cameraId, true);
                                                Toast.makeText(cx , "Roll : Flash On" , Toast.LENGTH_LONG).show();
                                                FlashState = 1 ; }
                                            else{
                                                if(FlashState == 1){
                                                cameraManager.setTorchMode(cameraId, false);
                                                Toast.makeText(cx , "Roll : Flash Off" , Toast.LENGTH_LONG).show();
                                                FlashState = 0 ;
                                            }}

                                        }




                                    }
                                    catch (Exception e){

                                    }

                                }

                            else {
                                if(T2 - T1 < 1000 && (CurrentUserActivity.equals("com.sec.android.app.camera") || CurrentUserActivity.equals("com.google.android.music") ) ){
                                    Intent ii = new Intent(Intent.ACTION_MAIN);
                                    ii.addCategory(Intent.CATEGORY_HOME);
                                    ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    cx.startActivity(ii);
                                    PackVerf = 0 ;

                                } else {

                                    if(!CurrentUserActivity.equals("com.sec.android.app.camera")){
                                    connected = 1;

                                    //set the background and activate the EYE
                                    // 01 set the background according to package switch (package name).

                                    switch (CurrentUserActivity) {
                                        case "com.sec.android.app.launcher":
                                            myball.setMyBackground2(1, Rvalue, Lvalue);
                                            break;

                                        case "com.android.systemui":
                                            myball.setMyBackground2(1, Rvalue, Lvalue);
                                            break;


                                        case "com.google.android.music":
                                            myball.setMyBackground2(3, Rvalue, Lvalue);
                                            break;

                                        case "com.samsung.android.dialer":
                                            myball.setMyBackground2(5, Rvalue, Lvalue);
                                            break;
                                        case "com.samsung.android.calendar":
                                            myball.setMyBackground2(5, Rvalue, Lvalue);
                                            break;
                                        case "com.samsung.android.messaging":
                                            myball.setMyBackground2(5, Rvalue, Lvalue);
                                            break;
                                        case "com.samsung.android.app.notes":
                                            myball.setMyBackground2(5, Rvalue, Lvalue);
                                            break;

                                        default:
                                            connected = 0;
                                            break;

                                    }
                                    if (connected == 1) {
                                        //verifiying if we are in a identified case to lanch myeye

                                        cx.bindService(i2, myconnection2, cx.BIND_AUTO_CREATE);
                                    }


                                } } }
                            didit = 1;
                        }
                        //////////////////////////////////////////////
                        //timer for 2 seconds

                        ElfState = 1;


                    }}


                    else {
                        //far
                        if (ElfState == 1) {
                            //             Toast.makeText(cx, "Off", Toast.LENGTH_SHORT).show();
                            if (connected == 1) {   // direction =  myservice.Diffrence;
                                /*cx.stopService(i2); */
                                connected = 0;
                                IsHapenning = 1 ;
                                if(myservice.Diffrence != 0 && myservice.Diffrence != 3 ){
                                myball.moveTheball(myservice.Diffrence);}
                                if(myservice.Diffrence == 0 || myservice.Diffrence == 3 ){
                                    IsHapenning = 0;
                                    myball.distroyball();
                                }
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

            } else {

                if (Case == 1) {
                    if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                        if (event.values[0] >= -4 && event.values[0] <= 4) {
                            if (Steps == 0) {
                                myln.setVisibility(View.VISIBLE);
                            }
                            //near


                            Steps = Steps + 1;
                            if (Steps == 1) {
                                mystep.setImageResource(R.drawable.validated1);
                                myjvc.enableView();

                            }
                            if (Steps == 2) {
                                mystep3.setImageResource(R.drawable.validated3);
                            }

                        } else {
                            //far
                            Toast.makeText(cx, "Off", Toast.LENGTH_SHORT).show();
                            if (Steps == 1) {
                                mystep2.setImageResource(R.drawable.validated2);
                            }
                            if (Steps == 2) {
                                mystep4.setImageResource(R.drawable.validated4);
                                Steps = 0;
                                myjvc.disableView();

                            }


                        }
                    }
                }


            }
        }}



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
            if(myball != null && myservice.Diffrence != 0){

            if(myball.myservice.isremoved == 1){
                Log.d(TAG ," isremoved ===== "+ myball.myservice.isremoved);
                myball.doSomething(myball.mydif);
                IsHapenning= 0 ;

            }
            else{
                Log.d(TAG ," isremoved ===== "+ myball.myservice.isremoved);
                handler.post(runnable3);
            }

            }


            if(myball == null && myservice.Diffrence != 0){
                handler.removeCallbacks(runnable3);
            }



        }
    };




    final Runnable  runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void run() {
            StartedTime = System.currentTimeMillis();
            if(ElfState == 1){
                if(StartedTime == CurrentTime && didit != 1 && connected == 0){
                    if(myball.myservice.isremoved != 1 && myball.exists == 1){
                    myball.distroyball();

                    }
                    ElfState = 0 ;
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
                        if(myball.myservice.isremoved != 1 && myball.exists == 1){
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








    public void distroyElf(){
       if(ActivityMessenger != null){
           ActivityMessenger.stopcathing();
       }


        if( myball != null){
            if(myball.exists == 1 ) {
                myball.distroyball();
                handler.removeCallbacks(runnable);
                handler.removeCallbacks(runnable2);
            }
        }


       if(Case == 1){
           senseManager2.unregisterListener(this);
           try {
               finalize();
           } catch (Throwable throwable) {
               throwable.printStackTrace();
           }
       }

        if(Case == 2){
            senseManager.unregisterListener(this);
            try {
                finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }


    public void distroyEverything(){
        if(myball == null || myball.exists == 1){
            myball.distroyball();
        }
        senseManager.unregisterListener(this);
        senseManager2.unregisterListener(this);
        handler.removeCallbacks(runnable); handler.removeCallbacks(runnable2); handler.removeCallbacks(runnable3);
    }



}

