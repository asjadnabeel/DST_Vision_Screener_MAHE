package com.manipal.dst_amsler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;




public class Root_Welcome extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 8000; // splash screen delay time
    MediaPlayer mp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }*/

        setContentView(R.layout.activity_welcome);
        playMogo();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 1);
        }

        while((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED));

        new Handler().postDelayed(new Runnable() {
            public void run() {

                mp.stop();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),  Root_LoginActivity.class); //Loginz Activity

                Root_Welcome.this.startActivity(intent);
                Root_Welcome.this.finish();

                // transition from splash to main menu
                //overridePendingTransition(R.layout.activityfadein, R.layout.splashfadeout);

            }
        }, SPLASH_DISPLAY_TIME);
    }


    private void playMogo() {
        mp = MediaPlayer.create(this,
                R.raw.mumogo);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }

        });

    }
}