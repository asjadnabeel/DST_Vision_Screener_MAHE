package com.manipal.dst_amsler;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;



import java.util.Locale;

public class Ams_StaticAmsler extends AppCompatActivity {
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amsler_static);


        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if(status == TextToSpeech.SUCCESS){
                    int result=tts.setLanguage(Locale.US);
                    tts.setSpeechRate(0.5f);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This Language is not supported");
                    }
                    else{
                        ConvertTextToSpeech();
                    }
                }
                else
                    Log.e("error", "Initilization Failed!");
            }
        });










        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Ams_DefectList.class);
                Ams_StaticAmsler.this.startActivity(intent);
                Ams_StaticAmsler.this.finish();

                // transition from splash to main menu
                //overridePendingTransition(R.layout.activityfadein, R.layout.splashfadeout);

            }
        }, 10000);



    }



    private void ConvertTextToSpeech() {
        // TODO Auto-generated method stub
        tts.speak("        Please close one eye         and            look into the center Red dot for    10 seconds ", TextToSpeech.QUEUE_FLUSH, null);

    }



}
