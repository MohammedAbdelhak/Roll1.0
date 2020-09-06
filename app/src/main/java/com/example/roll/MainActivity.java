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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView myimg  ;
    ImageView next  ;
    private ObjectAnimator lftToRgt,rgtToLft;
    private Handler handler = new Handler();

    private static String TAG = "com.example.opencvtest";
    LinearLayout myln;
    float x ;
    float touchX;
    int direction;
    private float halfW;
    private AnimatorSet animatorSet;
   private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(direction == 0){


                halfW = -halfW;
                direction = 1 ;

                lftToRgt = ObjectAnimator.ofFloat( myln,"translationX",0f,halfW )
                        .setDuration(700);

                rgtToLft = ObjectAnimator.ofFloat( myln,"translationX",halfW,0f )
                        .setDuration(700);

                animatorSet.play( lftToRgt ).before( rgtToLft ); // manage sequence
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
                direction = 0 ;
                lftToRgt = ObjectAnimator.ofFloat( myln,"translationX",0f,halfW )
                        .setDuration(700);

                rgtToLft = ObjectAnimator.ofFloat( myln,"translationX",halfW,0f )
                        .setDuration(700);

                animatorSet.play( lftToRgt ).before( rgtToLft );
                animatorSet.start();

                handler.postDelayed(runnable, 1500);



            }}
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myimg = findViewById(R.id.upframe);
        next = findViewById(R.id.next);
        animate();


    }



    public void proceed(View v ){
        if(v.getId() == next.getId()){

           handler.removeCallbacks(runnable);

           setContentView(R.layout.page2new);

        }
   else {
       Toast toast = Toast.makeText(this , "Roll : ByeBye ! " , Toast.LENGTH_LONG);
            toast.show();
            onDestroy();
   }

        }

    public void proceed2(View v ) {
        if (v.getId() == next.getId()) {
            Intent i = new Intent(this, PermisionsActivity.class);
            startActivity(i);
            finish();
        } else {

            setContentView(R.layout.activity_main);
                animate();
        }
    }


public void animate(){

    myln = findViewById(R.id.myball);
    Display display = getWindowManager().getDefaultDisplay();
    Point point=new Point();
    display.getSize(point);
    final int width = point.x; // screen width
    halfW = -(width/7.0f)  ;
    animatorSet = new AnimatorSet();
    handler.post(runnable);
}


}
