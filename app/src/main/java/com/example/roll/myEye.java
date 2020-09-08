package com.example.roll;



import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import androidx.annotation.Nullable;

public class myEye extends Service implements CameraBridgeViewBase.CvCameraViewListener2 {

    public static final String TAG = "com.example.roll";

    static{ System.loadLibrary("opencv_java3"); }
    static {
        boolean openCVStarted = false;

        if(!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Unable to load OpenCV");
            openCVStarted = false;
        } else {
            Log.d(TAG , "OpenCV loaded");
            openCVStarted = true;
        }
    }

    ////////////SERVICE CONNECTION////////////////////////
    private IBinder binderrr = new myEye.mybinder2();


    /////////////////////////////////////////////
    Moments moment;
    int Diffrence = 0 ;
    public JavaCameraView mycamera ;
    WindowManager wm1;
    LinearLayout myln;
    LinearLayout.LayoutParams llpar;
    WindowManager.LayoutParams wmpar;
    public Mat  grey;
    double m_area;
    public int posx= 0 , posy = 0 ,firstX , Fshot = 0 , firstY  ;
    int gotit = 0 ;








    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "im here **********************************/*/*/*/*/*/*/*************** ");
        mycamera = new JavaCameraView(this , 1);
        mycamera.setCameraIndex(1);
        mycamera.enableView();
        mycamera.setVisibility(SurfaceView.VISIBLE);
        mycamera.setCvCameraViewListener(this);
        wm1 = (WindowManager) getSystemService(WINDOW_SERVICE);
        myln = new LinearLayout(this);
        llpar = new LinearLayout.LayoutParams(2, 2);
        myln.setLayoutParams(llpar);
        wmpar = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
        wmpar.x = 0;
        wmpar.y = -700;
        wmpar.alpha = 0 ;
        myln.addView(mycamera);
        wm1.addView(myln, wmpar);
        return binderrr;
    }

    public class mybinder2 extends Binder {
        public myEye getService() {
            return myEye.this;
        }

    }






    @Override
    public void onCameraViewStarted(int width, int height) {
        grey = new Mat();
    }

    @Override
    public void onCameraViewStopped() {

        mycamera.disableView();
        wm1.removeView(myln);
        grey.release();
        try {
            finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        grey.release();
        grey =inputFrame.gray();

        //Imgproc.GaussianBlur(grey , grey,new Size(15,15) , 7);
        //Imgproc.GaussianBlur(rgbAT , rgbAT,new Size(5,5) , 0);
        //   Imgproc.medianBlur(rgbAT,rgbAT,9);

        Imgproc.threshold( grey, grey , 190, 200,Imgproc.THRESH_BINARY );
        moment =  Imgproc.moments(grey, true);
        m_area = moment.get_m00();
        posy= (int) (moment.get_m10() / m_area);
        posx = (int) (moment.get_m01() / m_area);
        if(posx != 0 && posy != 0){
        if(Fshot == 0){
            firstX = posx;
            firstY = posy;
            Fshot = 1 ;
        }

        if(posx >= 169 && gotit == 0 ){
            Diffrence = 1 ;
            gotit = 1 ;
        }
        else {
            if(gotit == 0){
            if (posx < firstX) {
                Diffrence = 1;
            } else {
                if (posx > firstX) {
                    Diffrence = 2;
                }
            }}
        }
        }
        if(Diffrence == 2 ){
            if(posy <=7 &&  firstX - posx <10){

                Diffrence = 3 ;
            }

        }else {
        if(Diffrence == 1){

            if(posy<=7 && firstX - posx <10){
                Diffrence = 3 ; }
        }}
        moment = null ;

        return grey;
    }

    @Override
    public boolean onUnbind(Intent intent) {

            onCameraViewStopped();

        Toast.makeText(getApplicationContext(), "unbinding", Toast.LENGTH_LONG).show();
        try {
            this.finalize();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        super.onDestroy();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

