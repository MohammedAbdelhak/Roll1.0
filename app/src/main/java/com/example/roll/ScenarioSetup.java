package com.example.roll;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ScenarioSetup extends AppCompatActivity {
        ImageView next, right ,left;
        int areaclicked ;
        int Rvalue , Lvalue ;
        Intent i7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenario_setup);
        next = findViewById(R.id.next);
        right = findViewById(R.id.right);
        next = findViewById(R.id.next);
        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        left = findViewById(R.id.left);

    }
    public void proceed2(View v ) {
        if (v.getId() == next.getId()) {
            startActivity(i7);
            finish();
        } else {

            setContentView(R.layout.scenario_setup);
            next = findViewById(R.id.next);
            right = findViewById(R.id.right);
            left = findViewById(R.id.left);
            switch (Rvalue){
                case 2 :  right.setImageResource(R.drawable.camera);    break;
                case 1 :  right.setImageResource(R.drawable.phone);  break;
                case 4 :  right.setImageResource(R.drawable.calendar);break;
                case 3 :  right.setImageResource(R.drawable.play); break;
                case 5 :  right.setImageResource(R.drawable.messenger); break;
                case 6 :  right.setImageResource(R.drawable.notes); break;
            }
            switch (Lvalue){
                case 2 :  left.setImageResource(R.drawable.camera);    break;
                case 1 :  left.setImageResource(R.drawable.phone);  break;
                case 4 :  left.setImageResource(R.drawable.calendar);break;
                case 3 :  left.setImageResource(R.drawable.play); break;
                case 5 :  left.setImageResource(R.drawable.messenger); break;
                case 6 :  left.setImageResource(R.drawable.notes); break;
            }

        }
    }
    public void proceed(View v ){
        if(v.getId() == next.getId()){/*
            Intent i = new Intent(this , Activation.class);
            if(Rvalue != 0 && Lvalue != 0){
            i.putExtra("Rval" , Rvalue);
            i.putExtra("Lval" , Lvalue);}
            startActivity(i);
            finish();*/
             i7 = new Intent(this , Activation.class);
            if(Rvalue != 0 && Lvalue != 0){
                i7.putExtra("Rval" , Rvalue);
                i7.putExtra("Lval" , Lvalue);}
            setContentView(R.layout.features);
        }
        else{
            Intent intnt = new Intent(this , scenes.class);
            startActivity(intnt);
            finish();
        }
    }

    public void clicked(View v){
            if(areaclicked == 0 && ( v.getId() == R.id.left || v.getId() == R.id.right) ){
                if(Rvalue == 0 || Lvalue == 0){
                if(v.getId() == R.id.right){
                right.setImageResource(R.drawable.rstrong);
                    areaclicked = 1 ;
                }
                else{
                    left.setImageResource(R.drawable.lstrong);
                    areaclicked = 2 ;
                }}

                if((Rvalue != 0 || Lvalue != 0 )&& areaclicked == 0){
                    if(v.getId() == R.id.right){
                        right.setImageResource(R.drawable.rlight);
                        Rvalue = 0 ;
                    }
                    else {
                        left.setImageResource(R.drawable.llight);
                        Lvalue = 0 ;
                    }
                }

            }

            else {
                if(areaclicked != 0 ){
                    switch (v.getId()){
                        case R.id.cameraapp : if(areaclicked == 1){ Rvalue = 2 ; if(Rvalue != Lvalue ){ right.setImageResource(R.drawable.camera);areaclicked =0;}} else { Lvalue = 2 ; if(Rvalue != Lvalue ){  left.setImageResource(R.drawable.camera); areaclicked =0;} }   break;
                        case R.id.phoneapp : if(areaclicked == 1){ Rvalue = 1 ;  if(Rvalue != Lvalue ){ right.setImageResource(R.drawable.phone); areaclicked =0;} } else {  Lvalue = 1 ;if(Rvalue != Lvalue ){ left.setImageResource(R.drawable.phone); areaclicked =0;}} break;
                        case R.id.calendarapp : if(areaclicked == 1){ Rvalue = 4 ; if(Rvalue != Lvalue ){  right.setImageResource(R.drawable.calendar);areaclicked =0;}} else {  Lvalue = 4 ; if(Rvalue != Lvalue ){  left.setImageResource(R.drawable.calendar);areaclicked =0; }} break;
                        case R.id.musicplayapp :if(areaclicked == 1){ Rvalue = 3; if(Rvalue != Lvalue ){  right.setImageResource(R.drawable.play);areaclicked =0;}} else {  Lvalue = 3 ; if(Rvalue != Lvalue ){  left.setImageResource(R.drawable.play); areaclicked =0;}}  break;
                        case R.id.messagesapp : if(areaclicked == 1){ Rvalue = 5 ;  if(Rvalue != Lvalue ){  right.setImageResource(R.drawable.messenger);areaclicked =0;}} else {  Lvalue = 5 ; if(Rvalue != Lvalue ){  left.setImageResource(R.drawable.messenger);areaclicked =0;}}  break;
                        case R.id.notesapp : if(areaclicked == 1){ Rvalue = 6 ; if(Rvalue != Lvalue ){ right.setImageResource(R.drawable.notes);areaclicked =0;}} else {  Lvalue = 6 ;if(Rvalue != Lvalue ){  left.setImageResource(R.drawable.notes);areaclicked =0;}} break;



                    }



                }
            }

    }
}
