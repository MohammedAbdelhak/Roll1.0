package com.example.roll;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import androidx.appcompat.app.AppCompatActivity;

public class preview extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static String TAG = "com.example.opencvtest";
    static{ System.loadLibrary("opencv_java3"); }
    static {
        boolean openCVStarted;

        if(!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Unable to load OpenCV");
            openCVStarted = false;
        } else {
            Log.d(TAG , "OpenCV loaded");
            openCVStarted = true;
        }
    }
    public Mat rgbA , rgbAT ,grey , coolestmatever ,sum;
    public Mat bin0 , bin1 ,bin2 , bin3 ,bin4;
    ImageView next ;
    ImageView firststep ,secondstep ,thirdstep , forthstep;
    ProximityElf pmelf ;
    Moments moment, moment2;
    JavaCameraView mycamera;
    Scalar minc0 , maxc0 , minc1 , maxc1, minc2 , maxc2 , minc3 , maxc3 ,minc4 , maxc4 ;
    double m_area, m_area2;
    public int posx= 0 , posy = 0 , verifi = 0 , posx2 , posy2;
    double[] hsv0 = new double[3] ,  hsv1 = new double[3],  hsv2 = new double[3],  hsv3 = new double[3],  hsv4 = new double[3];
    Ball myball;
    int firstX , Fshot , Diffrence;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);
        next = findViewById(R.id.next);
        firststep = findViewById(R.id.step1);
        secondstep = findViewById(R.id.step2);
        thirdstep = findViewById(R.id.step3);
        forthstep = findViewById(R.id.step4);
        mycamera =  findViewById(R.id.mycamera);
        pmelf = new ProximityElf(getApplicationContext(), firststep , secondstep , thirdstep,forthstep, mycamera);
        mycamera.setCameraIndex(1);
        mycamera.setVisibility(SurfaceView.VISIBLE);
        mycamera.setCvCameraViewListener(preview.this);

    }




    public void proceed2(View v ) {
        if (v.getId() == next.getId()) {
            Intent intnt = new Intent(this , scenes.class);
            if(pmelf != null){pmelf.distroyElf(); }
            startActivity(intnt);
            finish();
        } else {
            Intent intnt = new Intent(this , PermisionsActivity.class);
            startActivity(intnt);
            if(pmelf != null){pmelf.distroyElf(); }
            mycamera.disableView();

                finish();

        }
    }

        //the other implementation ....
   /*  @Override
    public void onCameraViewStarted(int width, int height) {






          rgbA = new Mat();
        grey = new Mat();
      //  coolestmatever = new Mat();
        sum = new Mat();
        rgbAT = new Mat();
        bin0 = new Mat();
        bin1 = new Mat();
        bin2 = new Mat();
        bin3 = new Mat();
        bin4 = new Mat();

    }*/

   /* @Override
    public void onCameraViewStopped() {






        rgbA.release();
        grey.release();
        rgbAT.release();
//        coolestmatever.release();
    }
*/

   /* @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {






        return inputFrame.rgba();

        sum.release();
        rgbAT.release();
        rgbA = inputFrame.rgba();
        Imgproc.cvtColor(rgbA , rgbAT , Imgproc.COLOR_RGB2HSV);
        grey =inputFrame.gray();

        Imgproc.GaussianBlur(grey , grey,new Size(5,5) , 0);
        Imgproc.GaussianBlur(rgbAT , rgbAT,new Size(5,5) , 0);
        Imgproc.medianBlur(rgbAT,rgbAT,9);

        Imgproc.threshold( grey, grey , 150, 255, Imgproc.THRESH_BINARY );

        moment =  Imgproc.moments(grey, true);
        m_area = moment.get_m00();
        posx2 = (int) (moment.get_m10() / m_area);
        posy2 = (int) (moment.get_m01() / m_area);
        Log.d(TAG ,"x == "+ posx2 + "y == "+ posy2);

        hsv0 = rgbAT.get(posx2,posy2);
        Log.d(TAG , "H = " + hsv0[0] + "S = " + hsv0[1]+ "V = " + hsv0[2]);
         Imgproc.rectangle(rgbAT, new Point(posx2 , posy2),new Point(posx2+ 2 , posy2+2) , new Scalar(0 , 0 ,255 ));
        hsv1 = rgbAT.get(posx2+10 , posy2);
        Log.d(TAG , "H = " + hsv1[0] + "S = " + hsv1[1]+ "V = " + hsv1[2]);
         Imgproc.rectangle(rgbAT, new Point(posx2+50 , posy2),new Point(posx2+ 52 , posy2) , new Scalar(0 , 0 ,255 ));
        hsv2 = rgbAT.get(posx2-10 , posy2);
        Log.d(TAG , "H = " + hsv2[0] + "S = " + hsv2[1]+ "V = " + hsv2[2]);
         Imgproc.rectangle(rgbAT, new Point(posx2-50 , posy2),new Point(posx2- 52 , posy2) , new Scalar(0 , 255 ,0 ));
        hsv3 = rgbAT.get(posx2 , posy2+10);
        Log.d(TAG , "H = " + hsv3[0] + "S = " + hsv3[1]+ "V = " + hsv3[2]);
         Imgproc.rectangle(rgbAT, new Point(posx2 , posy2+50),new Point(posx2 , posy2+52) , new Scalar(255 , 0 ,0 ));
        hsv4 = rgbAT.get(posx2 , posy2-10);
        Log.d(TAG , "H = " + hsv4[0] + "S = " + hsv4[1]+ "V = " + hsv4[2]);
         Imgproc.rectangle(rgbAT, new Point(posx2 , posy2-50),new Point(posx2 , posy2-52) , new Scalar(255 , 255 ,255 ));
            if(gotit < 5){
                gotit = gotit + 1 ;
        minc0 = new Scalar( 140, 37, 183);
        maxc0 = new Scalar(141, 35, 197);}

        Core.inRange(rgbAT , minc0, maxc0 , bin0);

        if(gotit < 5) {
            gotit = gotit + 1;
            minc1 = new Scalar(133,31,189);
            maxc1 = new Scalar(136,35,199);
        }

        Core.inRange(rgbAT , minc1, maxc1 , bin1);

        if(gotit < 5) {
            gotit = gotit + 1;
            minc2 = new Scalar(hsv2[0], hsv2[1], hsv2[2]);
            maxc2 =new Scalar(hsv2[0], hsv2[1], hsv2[2]);
        }

        Core.inRange(rgbAT , minc2, maxc2 , bin2);

        if(gotit < 5) {
            gotit = gotit + 1;
            minc3 = new Scalar(130,40,153);
            maxc3 = new Scalar(132,44, 157);
        }

        Core.inRange(rgbAT , minc3, maxc3 , bin3);

        if(gotit < 5) {
                gotit = gotit + 1;
                minc4 = new Scalar(135,31,190 );
                maxc4 = new Scalar(138,34 ,197);
            }

        Core.inRange(rgbAT , minc4, maxc4 , bin4);

        Core.add(bin0 , bin1 , sum );
        Core.add(sum , bin2 , sum);
        Core.add(sum , bin3 , sum);
        Core.add(sum , bin4 , sum);

        bin0.release();
        bin1.release();
        bin2.release();
        bin3.release();
        bin4.release();
       // rgbAT.release();
        grey.release();
        rgbA.release();



        moment2 =  Imgproc.moments(sum , true);
        m_area = moment2.get_m00();
        posx = (int) (moment2.get_m10() / m_area);
        posy= (int) (moment2.get_m01() / m_area);
        Log.d(TAG ,"x == "+ posx2 + "y == "+ posy2);
        return rgbAT;
    }*/


    @Override
    public void onCameraViewStarted(int width, int height) {
        myball = pmelf.myball;
        Log.d(TAG , "Camera Started");
        grey = new Mat();
    }

    @Override
    public void onCameraViewStopped() {
        myball.moveTheball(Diffrence);
        h1.postDelayed(r1 , 2000);

        grey.release();
    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        grey.release();


        // rgbA = inputFrame.rgba();
        //Imgproc.cvtColor(rgbA , rgbAT , Imgproc.COLOR_RGB2HSV);
        grey =inputFrame.gray();

        Imgproc.GaussianBlur(grey , grey,new Size(5,5) , 0);
        //Imgproc.GaussianBlur(rgbAT , rgbAT,new Size(5,5) , 0);
        //   Imgproc.medianBlur(rgbAT,rgbAT,9);

        Imgproc.threshold( grey, grey , 150, 255,Imgproc.THRESH_BINARY );

        moment =  Imgproc.moments(grey, true);
        m_area = moment.get_m00();
        posy= (int) (moment.get_m10() / m_area);
        posx = (int) (moment.get_m01() / m_area);
        if(posx != 0){
            if(Fshot == 0){
                firstX = posx;
                Fshot = 1 ;
            }

            Log.d(TAG ,"x == "+ posx + "y == "+ posy);

            if(posx > firstX){
                Diffrence = 2;
            }
            else {if(posx < firstX){
                Diffrence = 1 ;
            }}}


        return grey;
    }

    Handler h1 = new Handler();


    Runnable r1 = new Runnable() {
        @Override
        public void run() {
            firststep.setImageResource(R.drawable.prevstep1);
            secondstep.setImageResource(R.drawable.prevstep2);
            thirdstep.setImageResource(R.drawable.prevstep3);
            forthstep.setImageResource(R.drawable.prevstep4);
        }
    };


}
