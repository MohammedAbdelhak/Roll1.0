package com.example.roll;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermisionsActivity extends AppCompatActivity {
    ImageView next  ;
    Switch sw1 ,sw2 ,sw3;
    ActivityCatcher ActivityMessenger;

    private static String TAG = "com.example.opencvtest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_permisions);
        next = findViewById(R.id.next);
        sw1 = findViewById(R.id.switch1);
        sw2 = findViewById(R.id.switch2);
        sw3 = findViewById(R.id.switch3);

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && ContextCompat.checkSelfPermission(PermisionsActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(PermisionsActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
                }
            }
        });

        sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b && ContextCompat.checkSelfPermission(PermisionsActivity.this, Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions(PermisionsActivity.this, new String[]{Manifest.permission.ANSWER_PHONE_CALLS}, 1);
                }
            }
        });

        sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b && !Settings.canDrawOverlays(PermisionsActivity.this)){
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, 10101);
                    sw2.setChecked(true);
                }

            }
        });

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            sw1.setChecked(true);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ANSWER_PHONE_CALLS) == PackageManager.PERMISSION_GRANTED ){
            sw3.setChecked(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(Settings.canDrawOverlays(this)){
                sw2.setChecked(true);
            }
        }


    }

   @RequiresApi(api = Build.VERSION_CODES.M)
   public void candraw (){

       if(Settings.canDrawOverlays(this)){
           sw2.setChecked(true);
       }

   }


    public void proceed(View v ) {
        if(v.getId() == next.getId()){
            if(sw1.isChecked() && sw2.isChecked() && sw3.isChecked()){
            setContentView(R.layout.acccessibilty_permisions);
                next = findViewById(R.id.next1);


            }
            else{
                Toast.makeText(this , "il manque les permisions", Toast.LENGTH_LONG).show();
            }

        }
        else {
            Intent intnt = new Intent(this , MainActivity.class);
            startActivity(intnt); finish();        }
    }


    public void proceed2(View v ) {
        if (v.getId() == next.getId()) {
            ActivityMessenger = ActivityCatcher.getSharedInstance();

            if(ActivityMessenger == null){
                Log.d(TAG , "it has no stuff stuff ");
                Toast.makeText(this , "Permission non Accordeé !" , Toast.LENGTH_LONG).show();
            }
            else {
                Log.d(TAG , "i have it and it = "+ ActivityMessenger.packagecurrent);
                if (ActivityMessenger.packagecurrent != null) {
                    Intent intnt = new Intent(this , preview.class);
                    startActivity(intnt);
                    finish();
                }
            }
        }

        else {
            setContentView(R.layout.content_permisions);
            sw1 = findViewById(R.id.switch1);
            sw2 = findViewById(R.id.switch2);
            sw3 = findViewById(R.id.switch3);
            sw1.setChecked(true);
            sw2.setChecked(true);
            sw3.setChecked(true);
            next = findViewById(R.id.next);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {

       if(grantResults[0]  == PackageManager.PERMISSION_DENIED  && requestCode == 1){
           sw3.setChecked(false);
       }
       else{
           if(grantResults[0]  == PackageManager.PERMISSION_DENIED  && requestCode == 0)
           {
               sw1.setChecked(false);
           }
       }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
      if(requestCode == 10101 && resultCode == Activity.RESULT_CANCELED){
          sw2.setChecked(false);

      }
        else{
            sw2.setChecked(true);
        }
        candraw();
        super.onActivityResult(requestCode,resultCode,data);
    }



    public void AccessGiver(View view){
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        startActivity(intent);
        Intent i = new Intent(this, ActivityCatcher.class);
        startService(i);
        Log.d(TAG , "im here 0");

        Log.d(TAG , "im here 1");

    }



}
