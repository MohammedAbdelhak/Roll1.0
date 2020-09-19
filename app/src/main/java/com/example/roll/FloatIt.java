package com.example.roll;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class FloatIt extends Service {
    WindowManager.LayoutParams wmpar, wmpar2 , wmpar3;
    int   dif = 0 , isremoved = 0 ;
    boolean mruning ;
    ImageView Right , Left ;
    private IBinder binderr = new mybinder();
    ImageView myimg ,logo;
    WindowManager wm ;
    int  destination = 0 ;
    LinearLayout myln;
    public static final String TAG = "com.example.roll";
    LinearLayout.LayoutParams llpar;
    int BallPos = 255;
    WindowManager.LayoutParams updatedParams2;
    private Handler handler2 = new Handler();
    int Rightvalue, Leftvalue;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mruning = true ;
        myimg =  new ImageView(this);
        logo =  new ImageView(this);
        Right = new ImageView(this);
        Left = new ImageView(this);
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
        wmpar3 = new WindowManager.LayoutParams(90, 90, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);

        myln.addView(logo);

        wmpar.x = 0;
        wmpar.y = -585;

        wmpar2.x = 0;
        wmpar2.y = -700;

        wmpar3.y = -585;
        wmpar3.x = 255;


        wm.addView(myimg , wmpar2);
        wm.addView(Right,wmpar3);
        wmpar3.x = -255 ;
        wm.addView(Left,wmpar3);
        wm.addView(myln, wmpar);
        isremoved = 0 ;
        updatedParams2 = wmpar;







        handler2.post(verificator);
        return binderr;
    }





    public class mybinder extends Binder {
        public FloatIt getService() {
            return FloatIt.this;
        }

    }



    public void SetImageBackground(ImageView myback ){

        myimg.setImageDrawable(myback.getDrawable());

    }

    public void SetImageBackground2(ImageView myback , int R , int L ){
        Rightvalue = R ;
        Leftvalue = L ;



        switch(R){
            case 1 : Right.setImageResource(com.example.roll.R.drawable.phone); break ;
            case 2 : Right.setImageResource(com.example.roll.R.drawable.camera);  break ;
            case 3 : Right.setImageResource(com.example.roll.R.drawable.play); break ;
            case 4 : Right.setImageResource(com.example.roll.R.drawable.calendar);  break ;
            case 5 : Right.setImageResource(com.example.roll.R.drawable.messenger);  break ;
            case 6 : Right.setImageResource(com.example.roll.R.drawable.notes);  break;
            case 7 : Right.setImageResource(com.example.roll.R.drawable.camrollmini);  break;

        }

        switch(L){
            case 1 : Left.setImageResource(com.example.roll.R.drawable.phone); break ;
            case 2 : Left.setImageResource(com.example.roll.R.drawable.camera);  break ;
            case 3 : Left.setImageResource(com.example.roll.R.drawable.play); break ;
            case 4 : Left.setImageResource(com.example.roll.R.drawable.calendar);  break ;
            case 5 : Left.setImageResource(com.example.roll.R.drawable.messenger);  break ;
            case 6 : Left.setImageResource(com.example.roll.R.drawable.notes);  break;
            case 7 : Left.setImageResource(com.example.roll.R.drawable.camrollmini);  break;

        }






        myimg.setImageDrawable(myback.getDrawable());

    }


    @Override
    public void onDestroy(){
        Log.d("myTAG" , "distoyed ! ");



        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        destination = 0 ;
        if(isremoved == 0){
        wm.removeView(myln);
        wm.removeView(myimg);}
        if(Rightvalue != 0 && Leftvalue != 0){
            wm.removeView(Right);
            wm.removeView(Left);
        }

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

    Runnable    verificator = new Runnable() {
        @Override
        public void run() {
            Log.d("myTAG " , "Floatit is : "+ mruning);
            handler2.post(verificator);
        }



    };

}






