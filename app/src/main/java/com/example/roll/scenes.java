package com.example.roll;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class scenes extends AppCompatActivity {
        ImageView next ;
    private ObjectAnimator lftToRgt1,rgtToLft1,lftToRgt2,rgtToLft2,lftToRgt3,rgtToLft3,lftToRgt4,rgtToLft4;
    private Handler handler = new Handler();
    private float halfW , halw2;
    int direction;
    private AnimatorSet animatorSet;
    private static String TAG = "com.example.opencvtest";
    LinearLayout myln1 , myln2 , myln3 , myln4 ;


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(direction == 0){


                halfW = -halfW;
                halw2 = -halfW;
                direction = 1 ;

                lftToRgt1 = ObjectAnimator.ofFloat( myln1,"translationX",0f,halfW )
                        .setDuration(700);
                lftToRgt3 = ObjectAnimator.ofFloat( myln3,"translationX",0f,halfW )
                        .setDuration(700);
                rgtToLft1 = ObjectAnimator.ofFloat( myln1,"translationX",halfW,0f )
                        .setDuration(700);
                rgtToLft3 = ObjectAnimator.ofFloat( myln3,"translationX",halfW,0f )
                        .setDuration(700);

                lftToRgt2 = ObjectAnimator.ofFloat( myln2,"translationX",0f,halw2 )
                        .setDuration(700);
                lftToRgt4 = ObjectAnimator.ofFloat( myln4,"translationX",0f,halw2 )
                        .setDuration(700);
                rgtToLft2 = ObjectAnimator.ofFloat( myln2,"translationX",halw2,0f )
                        .setDuration(700);
                rgtToLft4 = ObjectAnimator.ofFloat( myln4,"translationX",halw2,0f )
                        .setDuration(700);

                animatorSet.play( lftToRgt1 ).before( rgtToLft1 ); // manage sequence
                animatorSet.play( lftToRgt3 ).before( rgtToLft3 ); // manage sequence
                animatorSet.play( lftToRgt2 ).before( rgtToLft2 );
                animatorSet.play( lftToRgt4 ).before( rgtToLft4 );


                animatorSet.start();
                handler.postDelayed(runnable, 1500);




               /* if(x == 397.0){
                    direction = 1 ;
                }
            x = myln.getX();
            x = x + 5;
            myln.setX(x);
            handler.postDelayed(runnable, 50);
            Log.d(TAG , "x = "+ x);
*/

            }
            else {
                if(direction == 1){
                    halfW = -halfW;

                            halw2 = -halfW;

                    direction = 0 ;

                    lftToRgt1 = ObjectAnimator.ofFloat( myln1,"translationX",0f,halfW )
                            .setDuration(700);
                    lftToRgt3 = ObjectAnimator.ofFloat( myln3,"translationX",0f,halfW )
                            .setDuration(700);
                    rgtToLft1 = ObjectAnimator.ofFloat( myln1,"translationX",halfW,0f )
                            .setDuration(700);
                    rgtToLft3 = ObjectAnimator.ofFloat( myln3,"translationX",halfW,0f )
                            .setDuration(700);

                    lftToRgt2 = ObjectAnimator.ofFloat( myln2,"translationX",0f,halw2 )
                            .setDuration(700);
                    lftToRgt4 = ObjectAnimator.ofFloat( myln4,"translationX",0f,halw2 )
                            .setDuration(700);
                    rgtToLft2 = ObjectAnimator.ofFloat( myln2,"translationX",halw2,0f )
                            .setDuration(700);
                    rgtToLft4 = ObjectAnimator.ofFloat( myln4,"translationX",halw2,0f )
                            .setDuration(700);


                    animatorSet.play( lftToRgt1 ).before( rgtToLft1 ); // manage sequence
                    animatorSet.play( lftToRgt3 ).before( rgtToLft3 ); // manage sequence
                    animatorSet.play( lftToRgt2 ).before( rgtToLft2 );
                    animatorSet.play( lftToRgt4 ).before( rgtToLft4 );



                   /* lftToRgt1 = ObjectAnimator.ofFloat( myln1,"translationX",0f,halfW )
                            .setDuration(700);

                    rgtToLft1 = ObjectAnimator.ofFloat( myln1,"translationX",halfW,0f )
                            .setDuration(700);

                    animatorSet.play( lftToRgt1 ).before( rgtToLft1 );*/
                    animatorSet.start();

                    handler.postDelayed(runnable, 1500);



                }}
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenarios);
        next = findViewById(R.id.next);
        animate();
    }

    public void proceed(View v ){
        if(v.getId() == next.getId()){
            Intent i = new Intent(this , ScenarioSetup.class);
            startActivity(i);

            handler.removeCallbacks(runnable);
            finish();
        }
        else{
            handler.removeCallbacks(runnable);
            Intent intnt = new Intent(this , preview.class);
            startActivity(intnt);
            finish();
        }
}


    public void animate(){

        myln1 = findViewById(R.id.myball);
        myln2 = findViewById(R.id.myball2);
        myln3 = findViewById(R.id.myball3);
        myln4 = findViewById(R.id.myball4);



        Display display = getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        final int width = point.x; // screen width
        halfW = -(width/7.0f)  ;
        animatorSet = new AnimatorSet();
        handler.post(runnable);
    }

}
