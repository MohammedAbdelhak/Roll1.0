package com.example.roll;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activation extends AppCompatActivity {
    ImageView activatebutton ;
    String TAG = "com.example.roll";
    ImageView Deactivate ;
    int onclick = 0 ;
    Intent i;
    private float halfW;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activate);



        activatebutton = findViewById(R.id.ActivateButton);
        Deactivate = findViewById(R.id.DActivateButton);
        Display display = getWindowManager().getDefaultDisplay();
        Point point=new Point();
        display.getSize(point);
        final int width = point.x; // screen width
        halfW = -(width/7.0f)  ;

        activatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onclick == 0) {
                    onclick =1;
                    i = new Intent(getApplicationContext() , UnstoppableService.class);
                    i.putExtra("width", halfW);
                    startService(i);
                    finish();
                    //start service

                }
                else{
                    Toast.makeText( getApplicationContext()  , "Deja Activé ", Toast.LENGTH_LONG).show();
                }
            }
        });

        Deactivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    i = new Intent(getApplicationContext() , UnstoppableService.class);
                    stopService(i);
                    onclick = 0;

            }
        });
    }











                public void proceed(View view){
        if(view.getId() == R.id.exit){
finish();        }
        else{
            Intent i = new Intent(this , scenes.class);
            startActivity(i);
            finish();
        }


                }
}
